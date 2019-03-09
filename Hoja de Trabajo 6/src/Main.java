import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * For init the app
     * @param args the default instance args of the Main
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * For show the JavaFX Stage
     * @param primaryStage unique JavaFX Stage displayed on screen
     */
    @Override
    public void start(Stage primaryStage) {
        CardReaderView cardReaderView = new CardReaderView();
        cardReaderView.show(primaryStage);
    }


}
