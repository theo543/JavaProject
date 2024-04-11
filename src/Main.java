import repository.GradeBook;
import repository.PersonRepository;
import repository.SchoolRepository;
import runner.UniversityManagementRunner;
import service.UniversityService;

public class Main {
    public static void main(String[] args) {
        var personRepository = new PersonRepository();
        var schoolRepository = new SchoolRepository();
        var gradeBook = new GradeBook();
        var uniService = new UniversityService(personRepository, schoolRepository, gradeBook);
        var runner = new UniversityManagementRunner(uniService);
        runner.loop();
        System.out.println("Goodbye!");
    }
}
