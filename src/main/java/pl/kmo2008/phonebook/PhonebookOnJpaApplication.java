package pl.kmo2008.phonebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.kmo2008.phonebook.Entity.Person;
import pl.kmo2008.phonebook.Service.PersonService;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.StreamSupport;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("pl.kmo2008.phonebook.Repo")
public class PhonebookOnJpaApplication implements CommandLineRunner {

    /**
     * Initialize scanner for getting strings from console
     */
    private Scanner scanner = new Scanner(System.in);


    /**
     * Autowired to person Service
     */
@Autowired
    private PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(PhonebookOnJpaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length != 0) {
            if (args[0].equals("-add")) {
                add();
            } else if (args[0].equals("-update")) {
                if (!args[1].isEmpty()) {
                    update(args[1]);
                } else {
                    System.out.println("Niepoprawne dane wejściowe. Przykład: java -jar phonebook.jar -update Jan");
                }
            } else if (args[0].equals("-delete")) {
                if (!args[1].isEmpty()) {
                    delete(args[1]);
                } else {
                    System.out.println("Niepoprawne dane wejściowe. Przykład: java -jar phonebook.jar -delete Jan");
                }
            } else {
                show(args[0]);
            }
        } else {
            show();
        }

        System.out.println("For close press CTRL+C");
    }

    /**
     * This method show all contacts in ASC order
     */
    private void show() {
        StreamSupport.stream(personService.findAll().spliterator(), true)
                .sorted(Comparator.comparing(Person::getName)).forEachOrdered(person -> System.out.println(person.toStringView()));
    }

    /**
     * This method show chosen contacts in ASC order
     * @param name Full or part of the name
     */
    private void show(String name) {
        StreamSupport.stream(personService.findAll().spliterator(), true)
                .sorted(Comparator.comparing(Person::getName))
                .filter(person -> person.getName().contains(name))
                .forEach(person -> System.out.println(person.toStringView()));
    }

    /**
     * This method deleting chosen contact
     * @param name
     */
    private void delete(String name) {
        personService.deleteAllByNameContains(name);
            System.out.println("Usunięto :" + name);

    }

    /**
     * This methon update chosen contact
     * @param name Full of the name
     */
    private void update(String name) {
        String newName = inputName();
        String number = inputNumber();
        if (!number.equals("-1"))
        {
            delete(name);
            personService.save(new Person(newName, number));
            System.out.println("Zaktualizowano " + name + " -> " + number);
        } else System.out.println("Wprowadz dane jescze raz.");
    }

    /**
     * This method add new contact
     */
    private void add() {
        String name = inputName();
        String number = inputNumber();
        if (!number.equals("-1")) {
            personService.save(new Person(name, number));
            System.out.println("Dodano " + name + " -> " + number);
        } else System.out.println("Wprowadz dane jescze raz.");
    }

    /**
     * This method takes name from user.
     *
     * @return String of name
     */
    public String inputName() {
        System.out.print("Podaj personalia: ");
        String name = scanner.next();
        return name;
    }

    /**
     * This method takes telephone number from user.
     * Working formats:
     * +48 123 456 789
     * 123456789
     * 123 456 789
     * 123-456-789
     * (+48) 123 456 789
     * +48123456789
     * 0048123456789
     * <p>
     * Things not to capture:
     * 12 345 67 89
     * 1234567899876543211
     * 654564654654654654
     * spam
     * 1231312asdasdf1231231
     * 123321
     * +4863227124
     *
     * @return String of number or "-1" when wrong input
     */
    public String inputNumber() {

        String pattern = "(?<!\\w)(\\(?(\\+|00)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)";
        System.out.print("Podaj numer: ");
        try {
            String number = scanner.next(pattern);
            return number;
        } catch (InputMismatchException e) {
            System.out.println("Podane dane nie są numerem telefonu.");
        }
        return "-1";
    }
}
