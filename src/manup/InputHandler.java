package manup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class InputHandler {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\033[0;31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private ArrayList<Integer> idsList;
    private int idCounter = 0;
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

        idsList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Willkommen bei Ihrem Datenmanagement-Tool! Möchten Sie Datensätze erstellen, lesen, aktualisieren oder löschen?");
        System.out.println("Bitte wählen Sie dementsprechend: \n1. Erstellen\n2. Lesen\n3. Aktualisieren\n4. Löschen");

        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                if (input > 0 && input < 5) {
                    break;
                } else {
                    throw new InputMismatchException(ANSI_RED + "Eingaben bitte entsprechend der Auswahl eingeben." + ANSI_RESET);
                }
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "Eingaben bitte entsprechend der Auswahl eingeben." + ANSI_RESET);
            }
        }
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        switch (input) {
            case 1:
                idCounter++;
                idsList.add(idCounter);
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

                while (true) {
                    try {
                        firstName = scanner.nextLine().trim();
                        System.out.println("Vorname: " + firstName);
                        if (!firstName.matches("^[a-zA-Z]+$")) {
                            System.out.println(ANSI_RED + "Mindestens ein Buchstabe notwendig. Ausschließlich Buchstaben möglich. Bitte wieder beim Vornamen beginnen");
                            continue;
                        }
                        lastName = scanner.nextLine().trim();
                        System.out.println("Nachname: " + lastName);
                        if (!lastName.trim().matches("^[a-zA-Z]+$")) {
                            System.out.println(ANSI_RED + "Mindestens ein Buchstabe notwendig. Ausschließlich Buchstaben möglich. Bitte wieder beim Vornamen beginnen");
                            continue;
                        }
                        email = scanner.nextLine().trim();
                        System.out.println("E-Mail: " + email);
                        if (!mailValidation(email)) {
                            System.out.println(ANSI_RED + "Ungültige E-Mail Adresse. Bitte wieder beim Vornamen beginnen." + ANSI_RESET);
                            continue;
                        }
                        country = scanner.nextLine().trim();
                        System.out.println("Herkunftsland: " + country);
                        break;
                    } catch (InputMismatchException i) {
                        System.out.println(ANSI_RED + "Bitte die Eingaben an das Schema anpassen. Erneut beim Vornamen beginnen." + ANSI_RESET);
                    }
                }
                while (true) {
                    try {
                        birthday = scanner.nextLine().trim();
                        System.out.println("Geburtstag: " + birthday);
                        if (!birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            System.out.println(ANSI_RED + "Ungültiges Datum. Format muss YYYY-MM-DD sein. Bitte erneut eingeben." + ANSI_RESET);
                            continue;

                        }
                        salary = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Jahreseinkommen: " + salary);
                        bonus = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Jährlicher Bonus: " + bonus);
                        break;
                    } catch (InputMismatchException i) {
                        System.out.println(ANSI_RED + "Bitte die Eingaben an das Schema anpassen." + ANSI_RESET);
                    }
                }
                sleep();
                query.insertData(firstName, lastName, email, country, birthday, salary, bonus);
                query.selectAll();
                break;


            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


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

            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


            case 3:
                //aktualisieren
                sleep();
                System.out.println(ANSI_GREEN + "Aktualisieren ausgewählt." + ANSI_RESET);
                sleep();

                while (true) {
                    System.out.println("Welche Zeile soll aktualisiert werden? Bitte passende ID angeben.");
                    try {
                        id = scanner.nextInt();
                        scanner.nextLine();
                        if (!idsList.contains(id)) {
                            System.out.println(ANSI_RED + "Diese ID existiert noch nicht.");
                            continue;
                        }
                        break;
                    } catch (InputMismatchException i) {
                        System.out.println(ANSI_RED + "IDs können nur aus Zahlen bestehen." + ANSI_RESET);
                    }
                }


                while (true) {
                    query.selectAfterID(id);
                    System.out.println("Welche Spalte zu diesem Eintrag soll aktualisiert werden?");
                    try {
                        column = scanner.nextLine();
                        scanner.nextLine();
                        break;
                    } catch (InputMismatchException i) {
                        System.out.println(ANSI_RED + "Bitte die Eingaben an das Schema anpassen." + ANSI_RESET);
                    }
                }


                //? DATE ?
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

    public boolean mailValidation(String email) {
        try {
            InternetAddress mail = new InternetAddress(email);
            mail.validate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


