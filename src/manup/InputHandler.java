package manup;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {
    public static final String ANSI_GREEN = "\u001B[32m";
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
    private int id;
    private String column;

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

                //exit eingabe -- help?
                System.out.println(ANSI_GREEN + "Erstellen wurde ausgewählt." + ANSI_RESET);
                sleep();
                System.out.println("Bitte achten Sie bei Ihrer Eingabe auf folgendes Format:");
                sleep();
                System.out.println("Vorname, Nachname, E-Mail, Herkunftsland, Geburtstag, Jahreseinkommen, jährlicher Bonus");
                sleep();
                System.out.println(ANSI_RED + "Erinnerung: Ein Datum wird im Format YYYY-MM-DD eingegeben" + ANSI_RESET);
                sleep();
                System.out.println(ANSI_YELLOW + "Hinweis: Nach jeder Eingabe bitte mit Enter bestätigen." + ANSI_RESET);


                firstName = scanner.nextLine().trim();
                lastName = scanner.nextLine().trim();
                email = scanner.nextLine().trim();
                country = scanner.nextLine().trim();


                scanner.nextLine();

                try {
                    birthday = scanner.nextLine().trim();

                    if (!birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        throw new IllegalArgumentException(ANSI_RED + "Ungültiges Datum. Format muss YYYY-MM-DD sein." + ANSI_RESET);
                    }

                    salary = scanner.nextInt();
                    bonus = scanner.nextInt();
                } catch (InputMismatchException i) {
                    System.out.println(ANSI_RED + "Bitte die Eingaben an das Schema anpassen." + ANSI_RESET);
                    input = 1;
                }
                sleep();
                query.insertData(firstName, lastName, email, country, birthday, salary, bonus);
                query.selectAll();
                query.selectAll();
                break;
            case 2:
                System.out.println(ANSI_GREEN + "Lesen wurde ausgewählt." + ANSI_RESET);
                sleep();
                System.out.print(". ");
                sleep();
                System.out.print(". ");
                sleep();
                System.out.print("." + "\n");
                sleep();
                query.selectAll();
                break;
            case 3:
                //aktualisieren
                sleep();
                System.out.println(ANSI_GREEN + "Aktualisieren ausgewählt." + ANSI_RESET);
                sleep();
                System.out.println("Bitte in folgender Reihenfolge eingeben, nach jeder Eingabe mit Enter bestätigen:");
                sleep();
                System.out.println("ID der zu verändernden Zeile, zu veränderndes Attribut(Spalte), neuer Wert");
                sleep();
                System.out.println(ANSI_YELLOW + "Hinweis: Ein Datum wird im Format YYYY-MM-DD eingegeben." + ANSI_YELLOW);

                try {
                    scanner.nextLine();
                    id = scanner.nextInt();
                    scanner.nextLine();
                    column = scanner.nextLine();
                    scanner.nextLine();
                } catch (InputMismatchException i) {
                    System.out.println(ANSI_RED + "Bitte die Eingaben an das Schema anpassen." + ANSI_RESET);
                    input = 3;
                }

                //? DATE ?
                scanner.nextLine();
                sleep();
                //query.updateData(id, column, );


                System.out.println("Datensatz aktualisiert.");
                break;
            case 4:
                //löschen
                sleep();
                System.out.println(ANSI_GREEN + "Löschen ausgewählt" + ANSI_RESET);
                sleep();
                System.out.println("Bitte die ID eingeben und mit Enter bestätigen:");
                sleep();
                System.out.println(ANSI_YELLOW + "Hinweis: Die ganze Zeile wird gelöscht!" + ANSI_RESET);
                sleep();
                scanner.nextLine();
                sleep();
                try {
                    id = scanner.nextInt();
                    query.deleteData(id);
                } catch (InputMismatchException i) {
                    System.out.println(ANSI_RED + "Bitte die ID der zu löschenden Zeile eingeben." + ANSI_RESET);
                    input = 4;
                }
                System.out.println("Datensatz gelöscht.");
                break;
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

