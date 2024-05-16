import repository.GradeBook;
import repository.PersonRepository;
import repository.SchoolRepository;
import runner.UniversityManagementRunner;
import service.PersistenceService;
import service.SQLiteService;
import service.UniversityService;

public class Main {
    public static void main(String[] args) {
        var personRepository = new PersonRepository();
        var schoolRepository = new SchoolRepository();
        var gradeBook = new GradeBook();
        var uniService = new UniversityService(personRepository, schoolRepository, gradeBook);
        var sqliteService = new SQLiteService("university.db");
        PersistenceService persistenceService;
        try {
            persistenceService = new PersistenceService(sqliteService);
        } catch (Exception e) {
            System.out.println("Failed to initialize persistence service: " + e.getMessage());
            persistenceService = null;
        }
        var runner = new UniversityManagementRunner(uniService, persistenceService);
        runner.loop();
        System.out.println("Goodbye!");
    }
}
