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
        Course course = grade.getCourse();
        int insertionIndex;
        for (insertionIndex = 0; insertionIndex <= grades.length; insertionIndex++) {
            Grade nextGrade = grades[insertionIndex];
            Student nextStudent = nextGrade.getStudent();
            Course nextCourse = nextGrade.getCourse();
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
    public List<Grade> lookupGrades(Student student, Course course) {
        List<Grade> studentGrades = new ArrayList<>();
        for (Grade grade : grades) {
            if (grade.getStudent().getUID() == student.getUID() && grade.getCourse().getName().equals(course.getName())) {
                studentGrades.add(grade);
            }
        }
        return studentGrades;
    }
}
