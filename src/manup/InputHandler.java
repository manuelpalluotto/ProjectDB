package manup;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {
    public static final String ANSI_RED = "\033[0;31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private int input = 0;
    private Queries query;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String birthday;
    private int salary;
    private int bonus;

    public InputHandler(Queries query) {
        this.query = query;
    }

    public void CRUDselection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Willkommen bei Ihrem Datenmanagement-Tool! Möchten Sie Datensätze erstellen, lesen, aktualisieren oder löschen?");
        System.out.println("Bitte wählen Sie dementsprechend: \n1. Erstellen\n2. Lesen\n3. Aktualisieren\n4. Löschen");

        while (true) {
            try {
                input = scanner.nextInt();
                if (input > 0 && input < 5) {
                    break;
                } else {
                    throw new InputMismatchException(ANSI_RED + "Eingaben bitte entsprechend der Auswahl eingeben." + ANSI_RESET);
                }
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "Eingaben bitte entsprechend der Auswahl eingeben." + ANSI_RESET);
                scanner.nextLine();
            }
        }
        switch (input) {
            case 1:
                try {
                    //exit eingabe -- help?
                    System.out.println("Erstellen wurde gewählt.");
                    Thread.sleep(1700);

                    System.out.println("Bitte achten Sie bei Ihrer Eingabe auf folgendes Format:");
                    Thread.sleep(1700);

                    System.out.println("Vorname, Nachname, E-Mail, Herkunftsland, Geburtstag, Jahreseinkommen, jährlicher Bonus");
                    Thread.sleep(1700);

                    System.out.println(ANSI_RED + "Erinnerung: Ein Datum wird im Format YYYY-MM-DD eingegeben" + ANSI_RESET);
                    Thread.sleep(1700);

                    System.out.println(ANSI_YELLOW + "Hinweis: Nach jeder Eingabe bitte die Eingabetaste betätigen." + ANSI_RESET);
                    Thread.sleep(1700);

                    firstName = scanner.nextLine().trim();
                    lastName = scanner.nextLine().trim();
                    email = scanner.nextLine().trim();
                    country = scanner.nextLine().trim();

                    scanner.nextLine();

                    birthday = scanner.nextLine().trim();

                    if (!birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        throw new IllegalArgumentException("Ungültiges Datum. Format muss YYYY-MM-DD sein.");
                    }

                    salary = scanner.nextInt();
                    bonus = scanner.nextInt();
                    query.insertData(firstName, lastName, email, country, birthday, salary, bonus);
                    query.selectAll();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            case 2:
                System.out.println("Lesen wurde gewählt.");

                sleep();
                System.out.print(". ");
                sleep();
                System.out.print(". ");
                sleep();
                System.out.print("." + "\n");
                sleep();
                query.selectAll();

            case 3:
                //aktualisieren
                sleep();
                System.out.println("Aktualisieren ausgewählt.");
                sleep();
                System.out.println("Bitte in folgender Reihenfolge eingeben, nach jeder Eingabe die Enter taste betätigen:");
                sleep();
                System.out.println("ID der zu verändernden Zeile, zu veränderndes Attribut(Spalte), neuer Wert");

            case 4:
                //löschen
        }
    }


    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

