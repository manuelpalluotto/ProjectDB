package manup;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.mail.internet.InternetAddress;

public class InputHandler {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\033[0;31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
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
    private String[] columns;
    private String answer;

    public InputHandler(Queries query) {
        this.query = query;
    }

    public void CRUDselection() {
        Scanner scanner = new Scanner(System.in);

        columns = new String[]{"ID", "Vorname", "Nachname", "E-Mail", "Land", "Geburtstag", "Jahreseinkommen", "jährlicher Bonus"};

        System.out.println("Willkommen bei Ihrem Datenmanagement-Tool! Möchten Sie Datensätze erstellen, lesen, aktualisieren oder löschen?");

        while (true) {
            System.out.println("Bitte wählen Sie dementsprechend: \n1. Erstellen\n2. Lesen\n3. Aktualisieren\n4. Löschen\n5. Programm beenden");
            selectionLoop(scanner);
            switch (input) {
                case 1:
                    create(scanner);
                    continue;
                case 2:
                    read(scanner);
                    continue;
                case 3:
                    update(scanner);
                    continue;
                case 4:
                    delete(scanner);
                    continue;
                case 5:
                    System.exit(0);
            }
        }
    }

    public void selectionLoop(Scanner scanner) {
        while (true) {
            try {
                input = scanner.nextInt();
                if (input > 0 && input < 6) {
                    scanner.nextLine();
                    break;
                }
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "Eingaben bitte entsprechend der Auswahl eingeben." + ANSI_RESET);
                scanner.nextLine();
            }
        }
    }

    public void delete(Scanner scanner) {
        deletePrompt();
        query.selectAll();
        findIDtoDelete(scanner);
        query.deleteData(id);
        System.out.println("Datensatz gelöscht.");
    }

    public void update(Scanner scanner) {
        updatePrompt();
        query.selectAll();
        findIDtoUpdate(scanner);
        findCol(scanner);
        System.out.println("Bitte den neuen Wert der Spalte angeben.");
        sortingCols(scanner);
        sleep();
        System.out.println("Datensatz aktualisiert.");
        sleep();
    }

    public void read(Scanner scanner) {
        readPrompt();
        query.selectAll();

        System.out.println("Möchten Sie die Einträge nach einer im Nachnamen enthaltenen Zeichenkette filtern? (Y/N)");
        while (true) {
            sleep();
            String answer = scanner.nextLine().trim();

        }
    }


    public boolean replyValidation(String answer, Scanner scanner) {
        String check = scanner.nextLine().trim();
        System.out.println("Eingabe: \"" + answer + "\". Korrekt?(Y/N)");
        while (true) {
            if (check.equalsIgnoreCase("Y")) {
                return true;
            } else if (check.trim().equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Valide Möglichkeiten sind nur \"Y\" oder \"N\".");
            }
        }
    }

    public void create(Scanner scanner) {
        createPrompt();
        while (true) {
            try {
                System.out.println("Vorname: ");
                firstName = scanner.nextLine().trim();
                System.out.println("Vorname: " + firstName);
                if (!firstName.matches("^[a-zA-Z]+$")) {
                    System.out.println(ANSI_RED + "Mindestens ein Buchstabe notwendig. Ausschließlich Buchstaben möglich. Bitte wieder beim Vornamen beginnen");
                    continue;
                }
                System.out.println("Nachname: ");
                lastName = scanner.nextLine().trim();
                System.out.println("Nachname: " + lastName);
                if (!lastName.trim().matches("^[a-zA-Z]+$")) {
                    System.out.println(ANSI_RED + "Mindestens ein Buchstabe notwendig. Ausschließlich Buchstaben möglich. Bitte wieder beim Vornamen beginnen" + ANSI_RESET);
                    continue;
                }
                System.out.println("E-Mail: ");
                email = scanner.nextLine().trim();
                System.out.println("E-Mail: " + email);
                if (!mailValidation(email)) {
                    System.out.println(ANSI_RED + "Ungültige E-Mail Adresse. Bitte wieder beim Vornamen beginnen." + ANSI_RESET);
                    continue;
                }
                System.out.println("Land: ");
                country = scanner.nextLine().trim();
                System.out.println("Herkunftsland: " + country);
                break;
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "Bitte die Eingaben an das Schema anpassen. Erneut beim Vornamen beginnen." + ANSI_RESET);
            }
        }
        while (true) {
            try {
                System.out.println("Geburtstag: ");
                birthday = scanner.nextLine().trim();
                System.out.println("Geburtstag: " + birthday);
                if (!birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    System.out.println(ANSI_RED + "Ungültiges Datum. Format muss YYYY-MM-DD sein. Bitte erneut eingeben." + ANSI_RESET);
                    continue;
                }
                System.out.println("Jahreseinkommen: ");
                salary = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Jahreseinkommen: " + salary);
                System.out.println("Bonus: ");
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
    }

    public void findIDtoDelete(Scanner scanner) {
        while (true) {
            System.out.println("Welche Zeile soll gelöscht werden? Bitte passende ID angeben.");
            try {
                id = scanner.nextInt();
                if (!query.getIDs().contains(id)) {
                    System.out.println(ANSI_RED + "Diese ID existiert nicht.");
                    continue;
                }
                break;
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "IDs können nur aus Zahlen bestehen." + ANSI_RESET);
                scanner.nextLine();
            }
        }
    }

    public void findIDtoUpdate(Scanner scanner) {
        while (true) {
            System.out.println("Zu welcher Zeile soll eine Spalte aktualisiert werden? Bitte passende ID angeben.");
            try {
                id = scanner.nextInt();
                if (!query.getIDs().contains(id)) {
                    System.out.println(ANSI_RED + "Diese ID existiert nicht.");
                    scanner.nextLine();
                    continue;
                }
                scanner.nextLine();
                break;
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "IDs können nur aus Zahlen bestehen." + ANSI_RESET);
                scanner.nextLine();
            }
        }
    }

    public void findCol(Scanner scanner) {
        while (true) {
            query.selectById(id);
            System.out.println("Welche Spalte zu diesem Eintrag soll aktualisiert werden?");
            try {
                column = scanner.nextLine();
                if (!validateColumn(columns, column)) {
                    continue;
                }

                break;
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "Bitte die Eingaben an das Schema anpassen." + ANSI_RESET);
            }
        }
    }

    public void createPrompt() {
        System.out.println(ANSI_GREEN + "Erstellen wurde ausgewählt." + ANSI_RESET);
        sleep();
        System.out.println("Bitte achten Sie bei Ihrer Eingabe auf folgendes Format:");
        sleep();
        System.out.println("Vorname, Nachname, E-Mail, Herkunftsland, Geburtstag, Jahreseinkommen, jährlicher Bonus");
        sleep();
        System.out.println(ANSI_RED + "Erinnerung: Ein Datum wird im Format YYYY-MM-DD eingegeben" + ANSI_RESET);
        sleep();
        System.out.println(ANSI_YELLOW + "Hinweis: Nach jeder Eingabe bitte mit Enter bestätigen." + ANSI_RESET);
    }

    public void readPrompt() {
        System.out.println(ANSI_GREEN + "Lesen wurde ausgewählt." + ANSI_RESET);
        sleep();
        System.out.print(". ");
        sleep();
        System.out.print(". ");
        sleep();
        System.out.print("." + "\n");
        sleep();
    }

    public void updatePrompt() {
        sleep();
        System.out.println(ANSI_GREEN + "Aktualisieren ausgewählt." + ANSI_RESET);
        sleep();
    }

    public void deletePrompt() {
        sleep();
        System.out.println(ANSI_GREEN + "Löschen ausgewählt" + ANSI_RESET);
        sleep();
        System.out.println("Bitte die ID eingeben und mit Enter bestätigen:");
        sleep();
        System.out.println(ANSI_YELLOW + "Hinweis: Die ganze Zeile wird gelöscht!" + ANSI_RESET);
        sleep();
    }

    public boolean validateColumn(String[] col, String input) {
        for (String column : col) {
            if (column.equals(input)) {
                return true;
            }
        }
        System.out.println(ANSI_RED + "Diese Spalte existiert nicht." + ANSI_RESET);
        return false;
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

    public void sortingCols(Scanner scanner) {
        switch (column.trim()) {
            case "Geburtstag" -> {
                while (true) {
                    try {
                        birthday = scanner.nextLine();
                        query.updateData(id, Date.valueOf(birthday));
                        if (!birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            System.out.println(ANSI_RED + "Ungültiges Datum. Format muss YYYY-MM-DD sein. Bitte erneut eingeben." + ANSI_RESET);
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        scanner.nextLine();
                    }
                }
            }
            case "jährlicher Bonus" -> {
                while (true) {
                    try {
                        bonus = scanner.nextInt();
                        scanner.nextLine();
                        String bonusCol = "bonus";
                        query.updateData(id, bonusCol, bonus);
                        break;
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        scanner.nextLine();
                    }
                }
            }
            case "Jahreseinkommen" -> {
                while (true) {
                    try {
                        salary = scanner.nextInt();
                        scanner.nextLine();
                        String salaryCol = "salary";
                        query.updateData(id, salaryCol, salary);
                        break;
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        scanner.nextLine();
                    }
                }
            }
            case "Vorname" -> {
                while (true) {
                    try {
                        firstName = scanner.nextLine();
                        String firstNameCol = "first_name";
                        query.updateData(id, firstNameCol, firstName);
                        break;
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        scanner.nextLine();
                    }
                }
            }
            case "Nachname" -> {
                while (true) {
                    try {
                        lastName = scanner.nextLine();
                        String lastNameCol = "last_name";
                        query.updateData(id, lastNameCol, lastName);
                        break;
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        scanner.nextLine();
                    }
                }
            }
            case "E-Mail" -> {
                while (true) {
                    try {
                        email = scanner.nextLine();
                        String emailCol = "email";
                        query.updateData(id, emailCol, email);
                        break;
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        scanner.nextLine();
                    }
                }
            }
            case "Land" -> {
                while (true) {
                    try {
                        country = scanner.nextLine();
                        String countryCol = "country";
                        query.updateData(id, countryCol, country);
                        break;
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        scanner.nextLine();
                    }
                }
            }

        }
    }
}


