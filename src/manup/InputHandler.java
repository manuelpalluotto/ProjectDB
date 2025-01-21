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

    public InputHandler(Queries query) {
        this.query = query;
    }

    public void CRUDselection() {
        Scanner scanner = new Scanner(System.in);

        columns = new String[]{"ID", "Vorname", "Nachname", "E-Mail", "Land", "Geburtstag", "Jahreseinkommen", "jährlicher Bonus"};

        System.out.println("Willkommen bei Ihrem Datenmanagement-Tool! Möchten Sie Datensätze erstellen, lesen, aktualisieren oder löschen?");

        while (true) {
            System.out.println("Bitte wählen Sie dementsprechend: \n1. Erstellen\n2. Lesen\n3. Aktualisieren\n4. Löschen\n5. Programm beenden");
            selectionValidation(scanner);
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

    public void selectionValidation(Scanner scanner) {
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input > 0 && input < 6) {
                    break;
                }
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "Eingaben bitte entsprechend der Auswahl eingeben." + ANSI_RESET);
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
                salary = Integer.parseInt(scanner.nextLine());
                System.out.println("Jahreseinkommen: " + salary);
                System.out.println("Bonus: ");
                bonus = Integer.parseInt(scanner.nextLine());
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

    public void read(Scanner scanner) {

        switch (readPrompt(scanner)) {
            case 1 -> query.selectAll();
            case 2 -> {
                sleep();
                System.out.println();
                System.out.println(ANSI_GREEN + "Nach welcher Spalte möchten Sie suchen?" + ANSI_RESET);
                System.out.println();
                System.out.printf("| %-3s | %-13s | %-13s | %-23s | %-13s | %-12s | %-15s | %-20s |%n",
                        "ID", "Vorname", "Nachname", "E-Mail", "Land", "Geburtstag", "Jahreseinkommen", "Bonus");
                System.out.println("+-----+---------------+---------------+-------------------------+---------------+--------------+-----------------+----------------------+");
                String columnFilter = scanner.nextLine();
                sortingColsForSelection(columnFilter);
            }
            case 3 -> {
                String substring = "";
                System.out.println("Wie lautet die Zeichenkette nach der gesucht werden soll?");
                while (true) {
                    System.out.println("Bitte geben Sie die Zeichenkette an.");
                    substring = scanner.nextLine();
                    if (replyValidation(substring, scanner)) {
                        query.selectFilteredByLastName(substring);
                        break;
                    }
                }
            }
        }
    }

    public int readPrompt(Scanner scanner) {
        System.out.println(ANSI_GREEN + "Lesen wurde ausgewählt." + ANSI_RESET);
        sleep();
        System.out.println("In welcher Form möchten Sie die Tabelle auslesen?");
        sleep();
        System.out.println("1. Gesamte Tabelle\n2. Gefiltert nach einer bestimmten Spalte\n3. Gefiltert nach einer im Nachnamen enthaltenen Zeichenkette");
        int i = Integer.parseInt(scanner.nextLine());
        while (true) {
            try {
                if (i > 0 || i < 4) {
                    break;
                } else {
                    System.out.println("Bitte nur Zahlen von 1 bis 3 angeben und mit Enter bestätigen.");
                }
            } catch (InputMismatchException f) {
                System.out.println("Bitte nur Zahlen von 1 bis 3 angeben und mit Enter bestätigen.");
            }
        }
        return i;
    }

    public void sortingColsForSelection(String column) {
        switch (column.trim()) {
            case "Geburtstag" -> query.selectByColumnSorted("birthday");

            case "jährlicher Bonus" -> query.selectByColumnSorted("bonus");

            case "Jahreseinkommen" -> query.selectByColumnSorted("salary");

            case "Vorname" -> query.selectByColumnSorted("first_name");

            case "Nachname" -> query.selectByColumnSorted("last_name");

            case "E-Mail" -> query.selectByColumnSorted("email");

            case "Land" -> query.selectByColumnSorted("country");

            case "ID" -> query.selectByColumnSorted("id");
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
                        bonus = Integer.parseInt(scanner.nextLine());
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
                        salary = Integer.parseInt(scanner.nextLine());
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

    public boolean replyValidation(String answer, Scanner scanner) {
        System.out.println("Eingabe: \"" + answer + "\". Korrekt?(Y/N)");
        String confirmation = scanner.nextLine().trim();
        while (true) {
            if (confirmation.equalsIgnoreCase("Y")) {
                return true;
            } else if (confirmation.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Valide Möglichkeiten sind nur \"Y\" oder \"N\".");
            }
        }
    }

    public void updatePrompt() {
        sleep();
        System.out.println(ANSI_GREEN + "Aktualisieren ausgewählt." + ANSI_RESET);
        sleep();
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

    public void findIDtoUpdate(Scanner scanner) {
        while (true) {
            System.out.println("Zu welcher Zeile soll eine Spalte aktualisiert werden? Bitte passende ID angeben.");
            try {
                id = Integer.parseInt(scanner.nextLine());
                if (!query.getIDs().contains(id)) {
                    System.out.println(ANSI_RED + "Diese ID existiert nicht.");
                    continue;
                }
                break;
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "IDs können nur aus Zahlen bestehen." + ANSI_RESET);
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

    public boolean validateColumn(String[] col, String input) {
        for (String column : col) {
            if (column.equals(input)) {
                return true;
            }
        }
        System.out.println(ANSI_RED + "Diese Spalte existiert nicht." + ANSI_RESET);
        return false;
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

    public void delete(Scanner scanner) {
        deletePrompt();
        query.selectAll();
        findIDtoDelete(scanner);
        query.deleteData(id);
        System.out.println("Datensatz gelöscht.");
    }

    public void findIDtoDelete(Scanner scanner) {
        while (true) {
            System.out.println("Welche Zeile soll gelöscht werden? Bitte passende ID angeben.");
            try {
                id = Integer.parseInt(scanner.nextLine());
                if (!query.getIDs().contains(id)) {
                    System.out.println(ANSI_RED + "Diese ID existiert nicht.");
                    continue;
                }
                break;
            } catch (InputMismatchException i) {
                System.out.println(ANSI_RED + "IDs können nur aus Zahlen bestehen." + ANSI_RESET);
            }
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

    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


