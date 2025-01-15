package manup;

import java.sql.*;
import java.time.LocalDate;

public class Main {
    private Queries query;
    private InputHandler inputHandler;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        query = new Queries();
        inputHandler = new InputHandler();
        inputHandler.CRUDselection();
    }
}
