package manup;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {
    public static final String ANSI_RED = "\033[0;31m";     // RED
    public static final String ANSI_RESET = "\u001B[0m";
    private int input = 0;


    public void CRUDselection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Willkommen bei Ihrem Datenmanagement-Tool! Möchten Sie Datensätze erstellen, lesen, aktualisieren oder löschen?");
        System.out.println("Bitte wählen Sie dementsprechend: \n1. Erstellen\n2. Lesen\n3. Aktualisieren\n4. Löschen");

        while (true) {
            try {
                input = scanner.nextInt();
                if (input > 0 && input < 5) {
                    input = 1;
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
                System.out.println("");
        }
    }
}

