package repository;

import model.Course;
import model.Grade;
import model.Student;

import java.util.ArrayList;
import java.util.List;

// Stores grades in a sorted array, ordered by (student UID, course name, grade)
public class GradeBook {
    private Grade[] grades;
    public GradeBook() {
        this.grades = new Grade[0];
    }
    public void addGrade(Grade grade) {
        Student student = grade.getStudent();
        Course course = grade.getExam().getCourse();
        int insertionIndex;
        for (insertionIndex = 0; insertionIndex < grades.length; insertionIndex++) {
            Grade nextGrade = grades[insertionIndex];
            Student nextStudent = nextGrade.getStudent();
            Course nextCourse = nextGrade.getExam().getCourse();
            if (student.getUID() < nextStudent.getUID()) {
                break;
            }
            if (student.getUID() > nextStudent.getUID()) {
                continue;
            }
            int courseComparison = course.getName().compareTo(nextCourse.getName());
            if (courseComparison < 0) {
                break;
            }
            if (courseComparison > 0) {
                continue;
            }
            if (grade.getGrade() < nextGrade.getGrade()) {
                break;
            }
        }
        Grade[] newGrades = new Grade[grades.length + 1];
        System.arraycopy(grades, 0, newGrades, 0, insertionIndex);
        newGrades[insertionIndex] = grade;
        System.arraycopy(grades, insertionIndex, newGrades, insertionIndex + 1, grades.length - insertionIndex);
        grades = newGrades;
    }
    // Null course means all courses
    public List<Grade> lookupGrades(Student student, Course course) {
        int begin, end;
        boolean found = false;
        for (begin = 0; begin < grades.length; begin++) {
            Grade grade = grades[begin];
            if (grade.getStudent().getUID() == student.getUID() && (course == null || grade.getExam().getCourse().getName().equals(course.getName()))) {
                found = true;
                break;
            }
        }
        if (!found) {
            return new ArrayList<>();
        }
        for (end = begin; end <= grades.length; end++) {
            if (end == grades.length) {
                break;
            }
            Grade grade = grades[end];
            if (grade.getStudent().getUID() != student.getUID() && (course == null || !grade.getExam().getCourse().getName().equals(course.getName()))) {
                break;
            }
        }
        int len = end - begin;
        Grade[] result = new Grade[len];
        System.arraycopy(grades, begin, result, 0, len);
        return List.of(result);
    }
    public List<Grade> lookupGrades(Student student) {
        return lookupGrades(student, null);
    }
    public List<Grade> getAllGrades() {
        return List.of(grades);
    }
    public void eraseAllData() {
        grades = new Grade[0];
    }
}
