package runner;

import model.*;

import service.ScannerService;
import service.UniversityService;

import java.util.*;

public class UniversityManagementRunner {
    private static class NoChoicesException extends IllegalArgumentException {
        private String choice;
        public NoChoicesException(String choice) {
            super("No choices available for '" + choice + "' prompt.");
        }
    }
    private record Command(String name, Runnable action) {
        @Override
        public String toString() {
            return name;
        }
    }
    private final UniversityService uniService;
    private final Command[] supportedCommands;
    public UniversityManagementRunner(UniversityService uniService) {
        this.uniService = Objects.requireNonNull(uniService);
        this.supportedCommands = new Command[] {
                new Command("Add a student", this::addStudent),
                new Command("Add a teacher", this::addTeacher),
                new Command("Add a course", this::addCourse),
                new Command("Add a classroom", this::addClassroom),
                new Command("Change course teacher", this::changeCourseTeacher),
                new Command("Add an exam to a course", this::addExamToCourse),
                new Command("Add a grade", this::addGrade),
                new Command("Add a fail", this::addFail),
                new Command("Query report card", this::queryReportCard),
                new Command("Query exam stats", this::queryExamStats),
                new Command("Query course stats", this::queryCourseStats),
                new Command("Exit", null)
        };
    }
    public void loop() {
        while(true) {
            Command choice = promptChoice(supportedCommands, "Choose an action:");
            if (choice.action() == null) {
                break;
            }
            try {
                choice.action().run();
            } catch (NoChoicesException e) {
                System.out.println("An error occurred due to lack of choices for a prompt: " + e.getMessage());
            }
        }
    }
    private static <T> T promptChoice(List<T> choices, String message) {
        if (choices.isEmpty()) {
            throw new NoChoicesException(message);
        }
        System.out.println(message);
        for (int i = 0; i < choices.size(); i++) {
            System.out.println(i + 1 + ": " + choices.get(i));
        }
        while(true) {
            String input = ScannerService.getInstance().nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= choices.size()) {
                    return choices.get(choice - 1);
                }
                System.out.println("Choice must be between 1 and " + choices.size() + ".");
            } catch (NumberFormatException e) {
                System.out.println("'" + input + "' is not a valid number.");
            }
        }
    }
    private static <T> T promptChoice(T[] choices, String message) {
        return promptChoice(List.of(choices), message);
    }
    private Student promptStudent() {
        return promptChoice(uniService.getAllStudents(), "Choose a student:");
    }
    private Teacher promptTeacher() {
        return promptChoice(uniService.getAllTeachers(), "Choose a teacher:");
    }
    private Course promptCourse() {
        return promptChoice(uniService.getAllCourses(), "Choose a course:");
    }
    private Classroom promptClassroom() {
        return promptChoice(uniService.getAllClassrooms(), "Choose a classroom:");
    }
    private Exam promptExam() {
        return promptChoice(uniService.getAllExams(), "Choose an exam:");
    }
    private String promptString(String message) {
        System.out.println(message);
        while(true) {
            String input = ScannerService.getInstance().nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }
            return input;
        }
    }
    private Date promptDate(String message) {
        System.out.println(message);
        Calendar dateParser = Calendar.getInstance();
        dateParser.setLenient(false);
        while(true) {
            System.out.println("Please use DD/MM/YYYY format, in your local timezone.");
            String date = ScannerService.getInstance().nextLine().trim();
            String pattern = "\\d{2}/\\d{2}/\\d{4}";
            if (!date.matches(pattern)) {
                System.out.println("Invalid date format.");
                continue;
            }
            String[] parts = date.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            dateParser.clear();
            try {
                dateParser.set(year, month - 1, day);
                return dateParser.getTime();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date: " + e.getMessage());
            }
        }
    }
    private int promptNaturalNumber(String message, int lowerBound, int upperBound) {
        System.out.println(message);
        while(true) {
            String input = ScannerService.getInstance().nextLine().trim();
            int number;
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("'" + input + "' is not a valid number.");
                continue;
            }
            if (number < 0) {
                System.out.println("Number must be non-negative.");
                continue;
            }
            if (number < lowerBound || number > upperBound) {
                System.out.println("Number must be between " + lowerBound + " and " + upperBound + ".");
                continue;
            }
            return number;
        }
    }
    private int promptNaturalNumber(String message, int lowerBound) {
        return promptNaturalNumber(message, lowerBound, Integer.MAX_VALUE);
    }
    private boolean promptBoolean(String message) {
        System.out.println(message);
        while(true) {
            System.out.println("Yes/No?");
            String input = ScannerService.getInstance().nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            }
            if (input.equals("no") || input.equals("n")) {
                return false;
            }
        }
    }
    private Grant promptGrant() {
        long moneyInCents = promptNaturalNumber("Grant amount in cents", 1);
        String description = promptString("Grant description");
        return new Grant(moneyInCents, description);
    }
    private void addStudent() {
        System.out.println("Adding a new student:");
        String name = promptString("Student's name");
        Date birthDate = promptDate("Student's birth date");
        String emergencyPhone = promptString("Student's emergency phone number");
        Grant grant = promptBoolean("Does the student have a grant?") ? promptGrant() : null;

        Student student = new Student(name, birthDate, emergencyPhone, grant);
        uniService.addStudent(student);
    }
    private void addTeacher() {
        System.out.println("Adding a new teacher:");
        String name = promptString("Teacher's name");
        Date birthDate = promptDate("Teacher's birth date");
        int salaryCents = promptNaturalNumber("Teacher's salary in cents", 1);
        Teacher teacher = new Teacher(name, birthDate, salaryCents);
        uniService.addTeacher(teacher);
    }
    private void addCourse() {
        System.out.println("Adding a new course:");
        Teacher teacher = promptTeacher();
        String name = promptString("Course's name");
        Classroom classroom = promptClassroom();
        Course course = new Course(teacher, name, classroom);
        uniService.addCourse(course);
    }
    private void addClassroom() {
        System.out.println("Adding a new classroom:");
        String roomId = promptString("Classroom's room ID");
        int capacity = promptNaturalNumber("Classroom's capacity", 1);
        Date latestSafetyCheck = promptDate("Classroom's latest safety inspection date");
        Classroom classroom = new Classroom(roomId, capacity, latestSafetyCheck);
        uniService.addClassroom(classroom);
    }
    private void changeCourseTeacher() {
        System.out.println("Assigning a new teacher to a course:");
        Course course = promptCourse();
        Teacher teacher = promptTeacher();
        uniService.changeCourseTeacher(course, teacher);
    }
    private void addExamToCourse() {
        System.out.println("Adding an exam to a course:");
        Course course = promptCourse();
        String name = promptString("Exam's name");
        uniService.addExamToCourse(course, name);
    }
    private void addGrade() {
        System.out.println("Adding a grade:");
        Student student = promptStudent();
        Exam exam = promptExam();
        int grade = promptNaturalNumber("Grade", 1, 10);
        uniService.addGeneralGrade(student, exam, grade);
    }
    private void addFail() {
        System.out.println("Adding a fail:");
        Student student = promptStudent();
        Exam exam = promptExam();
        FailCause cause = promptChoice(FailCause.values(), "Fail cause:");
        uniService.addFail(student, exam, cause);
    }
    private void queryReportCard() {
        System.out.println("Querying a student's report card:");
        Student student = promptStudent();
        System.out.println("Report card for " + student.getName() + ":");
        for (Grade grade : uniService.getStudentGrades(student)) {
            System.out.println(grade);
        }
    }
    private void queryExamStats() {
        System.out.println("Querying exam statistics:");
        Exam exam = promptExam();
        System.out.println("Statistics for " + exam + ":");
        System.out.println(exam.getStats());
    }
    private void queryCourseStats() {
        System.out.println("Querying course statistics:");
        Course course = promptCourse();
        System.out.println("Statistics for " + course + ":");
        System.out.println(course.getStats());
    }
}
