package repository;

import model.Person;
import model.Student;
import model.Teacher;

import java.util.List;
import java.util.Objects;

public class PersonRepository {
    private Person[] persons;
    public PersonRepository() {
        this.persons = new Person[0];
    }
    public Student getStudent(String name) {
        return LookupUtility.findByNameInMixedArray(name, persons, Student::getName, Student.class);
    }
    public List<Student> getAllStudents() {
        return LookupUtility.findAllInMixedArray(persons, Student.class);
    }
    public List<Teacher> getAllTeachers() {
        return LookupUtility.findAllInMixedArray(persons, Teacher.class);
    }
    public Teacher getTeacher(String name) {
        return LookupUtility.findByNameInMixedArray(name, persons, Teacher::getName, Teacher.class);
    }
    public Person getPerson(String name) {
        return LookupUtility.findByName(name, persons, Person::getName);
    }
    public void addPerson(Person person) {
        Objects.requireNonNull(person);
        Person[] newPersons = new Person[persons.length + 1];
        System.arraycopy(persons, 0, newPersons, 0, persons.length);
        newPersons[persons.length] = person;
        persons = newPersons;
    }
}
