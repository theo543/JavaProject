package service;

import model.*;
import repository.GradeBook;
import repository.PersonRepository;
import repository.SchoolRepository;

import java.util.List;

public class UniversityService {
    private final PersonRepository personRepository;
    private final SchoolRepository schoolRepository;
    private final GradeBook gradeBook;
    public UniversityService(PersonRepository personRepository, SchoolRepository schoolRepository, GradeBook gradeBook) {
        this.personRepository = personRepository;
        this.schoolRepository = schoolRepository;
        this.gradeBook = gradeBook;
    }
    public void addStudent(Student student) {
        personRepository.addPerson(student);
    }
    public Student getStudent(String name) {
        return personRepository.getStudent(name);
    }
    public List<Student> getAllStudents() {
        return personRepository.getAllStudents();
    }
    public void addTeacher(Teacher teacher) {
        personRepository.addPerson(teacher);
    }
    public Teacher getTeacher(String name) {
        return personRepository.getTeacher(name);
    }
    public List<Teacher> getAllTeachers() {
        return personRepository.getAllTeachers();
    }
    public void addCourse(Course course) {
        schoolRepository.addCourse(course);
    }
    public Course getCourse(String name) {
        return schoolRepository.getCourse(name);
    }
    public List<Course> getAllCourses() {
        return schoolRepository.getAllCourses();
    }
    public void addClassroom(Classroom classroom) {
        schoolRepository.addClassroom(classroom);
    }
    public Classroom getClassroom(String roomId) {
        return schoolRepository.getClassroom(roomId);
    }
    public List<Classroom> getAllClassrooms() {
        return schoolRepository.getAllClassrooms();
    }
    public void changeCourseTeacher(Course course, Teacher teacher) {
        course.setTeacher(teacher);
    }
    public void addExamToCourse(Course course, String examName) {
        Exam exam = new Exam(examName, course);
        course.recordExam(exam);
        schoolRepository.addExam(exam);
    }
    public void addGrade(Grade grade) {
        gradeBook.addGrade(grade);
        grade.getExam().getCourse().recordGrade(grade);
        grade.getExam().recordGrade(grade);
    }
    public void addGeneralGrade(Student student, Exam exam, int grade) {
        Grade generalGrade = new GeneralGrade(student, exam, grade);
        addGrade(generalGrade);
    }
    public void addFail(Student student, Exam exam, FailCause cause) {
        Grade failGrade = new FailGrade(student, exam, cause);
        addGrade(failGrade);
    }
    public List<Grade> getStudentGrades(Student student, Course course) {
        return gradeBook.lookupGrades(student, course);
    }
    public CourseStats getCourseStats(Course course) {
        return course.getStats();
    }
    public ExamStats getExamStats(Exam exam) {
        return exam.getStats();
    }
}
