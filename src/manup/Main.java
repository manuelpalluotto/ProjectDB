package manup;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
    private Queries query;
    private InputHandler inputHandler;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        query = new Queries();
        inputHandler = new InputHandler(query);
        inputHandler.CRUDselection();
    }
}
//    @Override
//    public void start(Stage stage) throws Exception {
//
//    }
//}
