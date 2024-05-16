package repository;

import model.Course;
import model.Exam;
import model.Classroom;

import java.util.List;
import java.util.Objects;

// Stores data about the school's affairs: courses, exams, classrooms
// Persons are stored separately in PersonRepository
public class SchoolRepository {
    private Course[] courses;
    private Exam[] exams;
    private Classroom[] classrooms;

    public SchoolRepository() {
        this.courses = new Course[0];
        this.exams = new Exam[0];
        this.classrooms = new Classroom[0];
    }

    public Course getCourse(String name) {
        return LookupUtility.findByNameInMixedArray(name, courses, Course::getName, Course.class);
    }

    public Exam getExam(String name) {
        return LookupUtility.findByNameInMixedArray(name, exams, Exam::getName, Exam.class);
    }

    public Classroom getClassroom(String roomId) {
        return LookupUtility.findByNameInMixedArray(roomId, classrooms, Classroom::getRoomId, Classroom.class);
    }

    public void addCourse(Course course) {
        Objects.requireNonNull(course);
        Course[] newCourses = new Course[courses.length + 1];
        System.arraycopy(courses, 0, newCourses, 0, courses.length);
        newCourses[courses.length] = course;
        courses = newCourses;
    }

    public void addExam(Exam exam) {
        Objects.requireNonNull(exam);
        Exam[] newExams = new Exam[exams.length + 1];
        System.arraycopy(exams, 0, newExams, 0, exams.length);
        newExams[exams.length] = exam;
        exams = newExams;
    }

    public void addClassroom(Classroom classroom) {
        Objects.requireNonNull(classroom);
        Classroom[] newClassrooms = new Classroom[classrooms.length + 1];
        System.arraycopy(classrooms, 0, newClassrooms, 0, classrooms.length);
        newClassrooms[classrooms.length] = classroom;
        classrooms = newClassrooms;
    }
    public List<Course> getAllCourses() {
        return List.of(courses);
    }
    public List<Exam> getAllExams() {
        return List.of(exams);
    }
    public List<Classroom> getAllClassrooms() {
        return List.of(classrooms);
    }
    public void eraseAllData() {
        courses = new Course[0];
        exams = new Exam[0];
        classrooms = new Classroom[0];
    }
}
