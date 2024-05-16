package service;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class PersistenceService {
    private final DatabaseConnectionService connector;
    private Connection connection;
    private final IdentityHashMap<Object, Integer> primaryKeyMap;
    private final Map<Integer, Object> reversePrimaryKeyMap;
    private boolean saveSession;
    private boolean loadSession;
    private void checkNoSession() {
        if (saveSession) {
            throw new IllegalStateException("Already in save session");
        }
        if (loadSession) {
            throw new IllegalStateException("Already in load session");
        }
    }
    private void checkSaveSession() {
        if (!saveSession) {
            throw new IllegalStateException("Not in save session");
        }
    }
    private void checkLoadSession() {
        if (!loadSession) {
            throw new IllegalStateException("Not in load session");
        }
    }
    private void beginSession() throws SQLException {
        checkNoSession();
        connection = connector.connect();
    }
    public void beginLoadSession() throws SQLException {
        beginSession();
        loadSession = true;
    }
    public void beginSaveSession() throws SQLException {
        beginSession();
        resetDatabase();
        saveSession = true;
    }
    public void endSession() throws SQLException {
        if (saveSession) {
            saveSession = false;
        } else if (loadSession) {
            loadSession = false;
        } else {
            throw new IllegalStateException("No session to end");
        }
        connection.commit();
        connection.close();
        connection = null;
    }
    private void addObj(Object obj) {
        primaryKeyMap.putIfAbsent(obj, primaryKeyMap.size() + 1);
    }
    private Integer getKey(Object obj) {
        if (obj == null) {
            return null;
        }
        Integer result = primaryKeyMap.get(obj);
        if (result == null) {
            throw new IllegalArgumentException("Objects saved in wrong order, dependency not found");
        }
        return result;
    }
    private Object getObj(int id) {
        if (id == 0) {
            return null;
        }
        Object result = reversePrimaryKeyMap.get(id);
        if (result == null) {
            throw new IllegalArgumentException("Objects loaded in wrong order, dependency not found");
        }
        return result;
    }
    private void exec(String sql, Object... params) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.execute();
        }
    }
    private interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
    private <T> List<T> query(String sql, ResultSetMapper<T> mapper) throws SQLException {
        try (Statement s = connection.createStatement()) {
            ArrayList<T> result = new ArrayList<>();
            try (ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) {
                    result.add(mapper.map(rs));
                }
            }
            return result;
        }
    }
    private <T> List<T> addingQuery(String sql, ResultSetMapper<T> mapper) throws SQLException {
        ResultSetMapper<T> addingMapper = rs -> {
            T result = mapper.map(rs);
            reversePrimaryKeyMap.put(rs.getInt("id"), result);
            return result;
        };
        return query(sql, addingMapper);
    }
    public PersistenceService(DatabaseConnectionService connector) throws SQLException {
        this.connector = connector;
        this.primaryKeyMap = new IdentityHashMap<>();
        this.reversePrimaryKeyMap = new IdentityHashMap<>();
        this.saveSession = false;
        this.loadSession = false;
    }
    public void resetDatabase() throws SQLException {
        try (Statement s = connection.createStatement()) {
            s.execute("DROP TABLE IF EXISTS grants");
            s.execute("DROP TABLE IF EXISTS students");
            s.execute("DROP TABLE IF EXISTS teachers");
            s.execute("DROP TABLE IF EXISTS fail_grades");
            s.execute("DROP TABLE IF EXISTS general_grades");
            s.execute("DROP TABLE IF EXISTS classrooms");
            s.execute("DROP TABLE IF EXISTS courses");
            s.execute("DROP TABLE IF EXISTS exams");
            s.execute("CREATE TABLE grants (id INT PRIMARY KEY, description TEXT, amount INT)");
            s.execute("CREATE TABLE students (id INT PRIMARY KEY, name TEXT, birth_day DATE, emergency_phone_contact TEXT, grant INT)");
            s.execute("CREATE TABLE teachers (id INT PRIMARY KEY, name TEXT, birth_day DATE, salary INT)");
            s.execute("CREATE TABLE fail_grades (id INT PRIMARY KEY, student INT, course INT, grade TEXT)");
            s.execute("CREATE TABLE general_grades (id INT PRIMARY KEY, student INT, course INT, grade INT)");
            s.execute("CREATE TABLE classrooms (id INT PRIMARY KEY, room_id TEXT, capacity INT, safety_inspection DATE)");
            s.execute("CREATE TABLE courses (id INT PRIMARY KEY, name TEXT, teacher INT, classroom INT, grade_sum INT, grade_count INT, fail_count INT, exam_count INT)");
            s.execute("CREATE TABLE exams (id INT PRIMARY KEY, name TEXT, course INT, total_grades INT, grades_sum INT, fails INT)");
        }
    }
    public void saveGrant(Grant grant) throws SQLException {
        addObj(grant);
        exec("INSERT INTO grants (id, description, amount) VALUES (?, ?, ?)", getKey(grant), grant.getDescription(), grant.getAmountInCents());
    }
    public void saveStudent(Student student) throws SQLException {
        addObj(student);
        exec("INSERT INTO students (id, name, birth_day, emergency_phone_contact, grant) VALUES (?, ?, ?, ?, ?)",
                getKey(student), student.getName(), student.getBirthDay(), student.getEmergencyPhoneContact(), getKey(student.getGrant()));
    }
    public void saveTeacher(Teacher teacher) throws SQLException {
        addObj(teacher);
        exec("INSERT INTO teachers (id, name, birth_day, salary) VALUES (?, ?, ?, ?)", getKey(teacher), teacher.getName(), teacher.getBirthDay(), teacher.getSalaryInCents());
    }
    public void saveFailGrade(FailGrade failGrade) throws SQLException {
        addObj(failGrade);
        exec("INSERT INTO fail_grades (id, student, course, grade) VALUES (?, ?, ?, ?)", getKey(failGrade), getKey(failGrade.getStudent()), getKey(failGrade.getExam()), failGrade.getCause().toString());
    }
    public void saveGeneralGrade(GeneralGrade generalGrade) throws SQLException {
        addObj(generalGrade);
        exec("INSERT INTO general_grades (id, student, course, grade) VALUES (?, ?, ?, ?)", getKey(generalGrade), getKey(generalGrade.getStudent()), getKey(generalGrade.getExam()), generalGrade.getGrade());
    }
    public void saveGrade(Grade grade) throws SQLException {
        if (grade instanceof FailGrade) {
            saveFailGrade((FailGrade) grade);
        } else if (grade instanceof GeneralGrade) {
            saveGeneralGrade((GeneralGrade) grade);
        } else {
            throw new IllegalArgumentException("Serialization of grade class '" + grade.getClass().getName() + "' not supported");
        }
    }
    public void saveClassroom(Classroom classroom) throws SQLException {
        addObj(classroom);
        exec("INSERT INTO classrooms (id, room_id, capacity) VALUES (?, ?, ?, ?)", getKey(classroom), classroom.getRoomId(), classroom.getCapacity(), classroom.getSafetyInspection());
    }
    public void saveCourse(Course course) throws SQLException {
        addObj(course);
        exec("INSERT INTO courses (id, name, teacher, classroom, grade_sum, grade_count, fail_count, exam_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                getKey(course), course.getName(), getKey(course.getTeacher()), getKey(course.getClassroom()), course.getGradeSum(), course.getGradeCount(), course.getFailCount(), course.getExamCount());
    }
    public void saveExam(Exam exam) throws SQLException {
        addObj(exam);
        exec("INSERT INTO exams (id, name, course) VALUES (?, ?, ?, ?, ?, ?)", getKey(exam), exam.getName(), getKey(exam.getCourse()), exam.getTotalGrades(), exam.getGradesSum(), exam.getFails());
    }
    public List<Grant> loadGrants() throws SQLException {
        return addingQuery("SELECT id, description, amount FROM grants", rs -> new Grant(rs.getInt("amount"), rs.getString("description")));
    }
    public List<Student> loadStudents() throws SQLException {
        return addingQuery("SELECT id, name, birth_day, emergency_phone_contact, grant FROM students", rs -> {
            Grant grant = (Grant) getObj(rs.getInt("grant"));
            return new Student(rs.getString("name"), rs.getDate("birth_day"), rs.getString("emergency_phone_contact"), grant);
        });
    }
    public List<Teacher> loadTeachers() throws SQLException {
        return addingQuery("SELECT id, name, birth_day, salary FROM teachers", rs -> new Teacher(rs.getString("name"), rs.getDate("birth_day"), rs.getInt("salary")));
    }
    public List<FailGrade> loadFailGrades() throws SQLException {
        return addingQuery("SELECT id, student, course, grade FROM fail_grades", rs -> {
            Student student = (Student) getObj(rs.getInt("student"));
            Exam exam = (Exam) getObj(rs.getInt("course"));
            return new FailGrade(student, exam, FailCause.valueOf(rs.getString("grade")));
        });
    }
    public List<GeneralGrade> loadGeneralGrades() throws SQLException {
        return addingQuery("SELECT id, student, course, grade FROM general_grades", rs -> {
            Student student = (Student) getObj(rs.getInt("student"));
            Exam exam = (Exam) getObj(rs.getInt("course"));
            return new GeneralGrade(student, exam, rs.getInt("grade"));
        });
    }
    public List<Classroom> loadClassrooms() throws SQLException {
        return addingQuery("SELECT id, room_id, capacity, safety_inspection FROM classrooms", rs -> new Classroom(rs.getString("room_id"), rs.getInt("capacity"), rs.getDate("safety_inspection")));
    }
    public List<Course> loadCourses() throws SQLException {
        return addingQuery("SELECT id, name, teacher, classroom, grade_sum, grade_count, fail_count, exam_count FROM courses", rs -> {
            Teacher teacher = (Teacher) getObj(rs.getInt("teacher"));
            Classroom classroom = (Classroom) getObj(rs.getInt("classroom"));
            return new Course(teacher, rs.getString("name"), classroom, rs.getInt("grade_sum"), rs.getInt("grade_count"), rs.getInt("fail_count"), rs.getInt("exam_count"));
        });
    }
    public List<Exam> loadExams() throws SQLException {
        return addingQuery("SELECT id, name, course, total_grades, grades_sum, fails FROM exams", rs -> {
            Course course = (Course) getObj(rs.getInt("course"));
            return new Exam(rs.getString("name"), course, rs.getInt("total_grades"), rs.getInt("grades_sum"), rs.getInt("fails"));
        });
    }
}
