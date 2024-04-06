package predictions;

import javafx.application.Application;

import javafx.stage.Stage;
import predictions.models.Model;

public class App extends Application {
    @Override
    public void start(Stage primaryStage)  {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}