import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardReaderView {

    /**
     * TextArea for show the results
     */
    TextArea result = new TextArea("...");
    /**
     * File chooser for pick the text file with the math expressions
     */
    final FileChooser fileChooser = new FileChooser();

    private final String [] arrayData = {"HashMap", "TreeMap", "LinkedHashMap"};
    private List<String> dialogData, dialogDataList;

    /**
     * To return a cleaned String of the math expression
     * @param input receives a String, with the full math operation
     * @param acceptedInputs ArrayList that have the valid operators
     * @return
     */
    private static ArrayList<String> cleanInput(String input, ArrayList<String> acceptedInputs) {
        ArrayList<String> stringsArrayList = new ArrayList<>(Arrays.asList(input.trim().split("")));
        ArrayList<String> cleanedInputArrayList = new ArrayList<>();
        for (String s : stringsArrayList) {
            if (acceptedInputs.contains(s)) {
                cleanedInputArrayList.add(s);
            }
        }
        return cleanedInputArrayList;
    }

    /**
     * To show the JavFX UI
     * @param stage Stage of JavaFX where we're gonna render the UI
     */
    public void show(Stage stage){

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        BorderPane border = new BorderPane();
        HBox hbox = addHBox(stage);
        border.setTop(hbox);
        border.setLeft(addVBox());

        Scene scene = new Scene(border, 400, 400);
        stage.setTitle("Calculadora");
        //scene.getStylesheets().add(Calculadora.class.getResource("estilo.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    /**
     * To return a HBox with two buttons for read file and clear TextArea
     * @param stage Stage of JavaFX where we're gonna render the UI
     * @return a filled HBox to add to the UI
     */
    public HBox addHBox(Stage stage) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(6);
        hbox.setStyle("-fx-background-color: #455a64;");

        //Button for load the text file
        Button buttonCurrent = new Button("Open File");
        buttonCurrent.setPrefSize(100, 20);
        buttonCurrent.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile != null){
                ArrayList<String> myLines = readFile(selectedFile);
                for(String line: myLines){
                    //TODO: Process each line
                    //String resultStr = operate(line);
                    //result.appendText("\n" + line + " tiene como resultado: " + resultStr);
                }
            }

        });

        //Button for clear the input data
        Button buttonProjected = new Button("Clear");
        buttonProjected.setPrefSize(100, 20);
        buttonProjected.setOnAction(e -> {
            result.clear();
        });

        hbox.getChildren().addAll(buttonCurrent, buttonProjected);

        return hbox;
    }

    /**
     * For read a file line by line
     * @param file File object which have the math operation
     * @return an ArrayList of String lines of the file
     */
    private ArrayList<String> readFile(File file){
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                lines.add(text);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CardReaderView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CardReaderView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(CardReaderView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lines;
    }

    public void process(){

    }

    /**
     * For add a TextArea to the screen, and show the result
     * @return a filled HBox to add to the UI
     */
    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        //Adding the TextArea to the VBox
        result.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(result);

        return vbox;
    }




}
