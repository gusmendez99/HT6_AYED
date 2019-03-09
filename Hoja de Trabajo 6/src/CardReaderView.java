import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardReaderView {

    /**
     * TextArea for show the results
     */
    ComboBox<String> dropDownMapType = new ComboBox<>();
    Button addButton = new Button("Agregar >");


    //For GlobalListView purpose only
    Button buttonSortGlobalByName = new Button("A-Z");
    Button buttonSortGlobalByType = new Button("Por tipo");
    ComboBox<String> dropDownGlobalCardType = new ComboBox<>();

    //For PlayerListView purpose only
    Button buttonSortPlayerByName = new Button("A-Z");
    Button buttonSortPlayerByType = new Button("Por tipo");
    ComboBox<String> dropDownPlayerCardType = new ComboBox<>();

    /**
     * File chooser for pick the text file with the math expressions
     */
    final FileChooser fileChooser = new FileChooser();

    private final String [] arrayData = {"HashMap", "TreeMap", "LinkedHashMap"};
    private final String [] arrayDataOfCardType = {Deck.MAGIC_CARD_TYPE, Deck.TRAP_CARD_TYPE, Deck.MONSTER_CARD_TYPE, Deck.ALL_CARD_TYPE};
    //private List<String> dialogData, dialogDataList;
    //ObservableList<Card> cardItems;
    ObservableMap<Integer, Card> observableGlobalCards ;
    ObservableMap<Integer, Card> observablePlayerCards ;

    Deck deck = null;
    ListView<Card> globalListView = new ListView<>();
    ListView<Card> playerListView = new ListView<>();


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
        border.setTop(addHBox(stage));
        border.setLeft(addVBoxGlobal());
        border.setCenter(addHBoxCenter());
        border.setRight(addVBoxUser());

        Scene scene = new Scene(border, 800, 500);
        stage.setTitle("Card Reader");
        //scene.getStylesheets().add(CardReader.class.getResource("estilo.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    private HBox addHBoxCenter() {
        //Starting with the last hBox
        HBox hBoxBottom = new HBox();
        hBoxBottom.setPadding(new Insets(15, 12, 15, 12));
        hBoxBottom.setSpacing(6);
        //hBoxBottom.setStyle("-fx-background-color: #455a64;");

        //Button for load the text file

        addButton.setDisable(true);
        addButton.setPrefSize(70, 20);
        addButton.setOnAction(e -> {
            Card card = globalListView.getSelectionModel().getSelectedItem();
            if(card != null){
                int id = deck.getId();
                deck.add(id, card, false);
                updatePlayerListView(null);
                globalListView.getSelectionModel().clearSelection();
                System.out.println("Carta agregada al jugador exitosamente");

            } else {

                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);

                Button myButton = new Button("Ok.");
                myButton.setOnAction(event -> dialogStage.close());

                VBox vbox = new VBox(new Text("Primero, selecciona un item..."), myButton);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(15));

                dialogStage.setScene(new Scene(vbox));
                dialogStage.show();
            }

        });



        hBoxBottom.getChildren().add(addButton);
        return hBoxBottom;
    }


    /**
     * To return a HBox with two buttons for read file and clear TextArea
     * @param stage Stage of JavaFX where we're gonna render the UI
     * @return a filled HBox to add to the UI
     */
    public VBox addHBox(Stage stage) {

        //Then, with the first hBox
        HBox hBoxTop = new HBox();
        hBoxTop.setPadding(new Insets(15, 12, 15, 12));
        hBoxTop.setSpacing(6);
        hBoxTop.setStyle("-fx-background-color: #455a64;");

        //Button for load the text file
        Button buttonCurrent = new Button("Abrir archivo");
        buttonCurrent.setDisable(true);
        buttonCurrent.setPrefSize(70, 20);
        buttonCurrent.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile != null){
                processFile(selectedFile);
                updateListViews();
            }

        });

        dropDownMapType.getItems().addAll(arrayData);

        dropDownMapType.setPromptText("Selecciona tipo de Map...");
        dropDownMapType.setPrefSize(150, 20);
        dropDownMapType.setOnAction(e -> {
            //TODO: generate Map with MyMapFactory
            String type = dropDownMapType.getValue();
            System.out.println("Has elegido el tipo: " + type);
            deck = new Deck(type);
            buttonCurrent.setDisable(false);
            globalListView.setDisable(false);
            playerListView.setDisable(false);




        });
        buttonCurrent.setPrefSize(180, 20);

        /*Button for clear the input data
        Button buttonProjected = new Button("Clear");
        buttonProjected.setPrefSize(100, 20);
        buttonProjected.setOnAction(e -> {
            result.clear();
        });*/

        hBoxTop.getChildren().addAll(buttonCurrent, dropDownMapType);



        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(hBoxTop);


        return vBox;
    }

    private void updateListViews() {
        playerListView.getItems().setAll(deck.getPlayerDeck().values());
        globalListView.getItems().setAll(deck.getUnusedDeck().values());
        addButton.setDisable(false);
        //For GlobalListView purpose only
        buttonSortGlobalByName.setDisable(false);
        buttonSortGlobalByType.setDisable(false);
        dropDownGlobalCardType.setDisable(false);

        //For PlayerListView purpose only
        buttonSortPlayerByName.setDisable(false);
        buttonSortPlayerByType.setDisable(false);
        dropDownPlayerCardType.setDisable(false);
    }

    private void updatePlayerListView(Map map) {
        playerListView.getItems().clear();
        if(map != null){
            playerListView.getItems().addAll(map.values());
        } else {
            playerListView.getItems().addAll(deck.getPlayerDeck().values());
        }
    }

    private void updateGlobalListView(Map map) {
        globalListView.getItems().clear();
        if(map != null){
            globalListView.getItems().addAll(map.values());
        } else {
            globalListView.getItems().addAll(deck.getUnusedDeck().values());
        }
    }

    /**
     * For add a TextArea to the screen, and show the result
     * @return a filled HBox to add to the UI
     */
    public VBox addVBoxUser() {
        HBox hBoxTop = new HBox();
        hBoxTop.setPadding(new Insets(15, 12, 15, 12));
        hBoxTop.setSpacing(6);
        hBoxTop.setStyle("-fx-background-color: #455a64;");

        //Button for load the text file

        buttonSortPlayerByName.setDisable(true);
        buttonSortPlayerByName.setPrefSize(70, 20);
        buttonSortPlayerByName.setOnAction(e -> {
            Map mapSorted = deck.sortDeck(false, Deck.NAME_SORT, "");
            updatePlayerListView(mapSorted);
        });



        buttonSortPlayerByType.setDisable(true);
        buttonSortPlayerByType.setPrefSize(70, 20);
        buttonSortPlayerByType.setOnAction(e -> {
            Map mapSorted = deck.sortDeck(false, Deck.TYPE_SORT, "");
            updatePlayerListView(mapSorted);
        });


        dropDownPlayerCardType.getItems().addAll(arrayDataOfCardType);

        dropDownPlayerCardType.setPromptText("Tipo...");
        dropDownPlayerCardType.setPrefSize(150, 20);
        dropDownPlayerCardType.setOnAction(e -> {
            //TODO: Filter by CardType
            String type = dropDownPlayerCardType.getValue();
            Map mapSorted = deck.sortDeck(false, "", type);
            updatePlayerListView(mapSorted);

        });


        hBoxTop.getChildren().addAll(buttonSortPlayerByName, buttonSortPlayerByType, dropDownPlayerCardType);


        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);


        playerListView.setDisable(false);

        vbox.getChildren().addAll(hBoxTop, playerListView);

        return vbox;
    }

    public VBox addVBoxGlobal() {

        HBox hBoxTop = new HBox();
        hBoxTop.setPadding(new Insets(15, 12, 15, 12));
        hBoxTop.setSpacing(6);
        hBoxTop.setStyle("-fx-background-color: #455a64;");

        //Button for load the text file

        buttonSortGlobalByName.setDisable(true);
        buttonSortGlobalByName.setPrefSize(70, 20);
        buttonSortGlobalByName.setOnAction(e -> {
            Map mapSorted = deck.sortDeck(true, Deck.NAME_SORT, "");
            updateGlobalListView(mapSorted);
        });



        buttonSortGlobalByType.setDisable(true);
        buttonSortGlobalByType.setPrefSize(70, 20);
        buttonSortGlobalByType.setOnAction(e -> {
            Map mapSorted = deck.sortDeck(true, Deck.TYPE_SORT, "");
            updateGlobalListView(mapSorted);
        });


        dropDownGlobalCardType.getItems().addAll(arrayDataOfCardType);

        dropDownGlobalCardType.setPromptText("Tipo...");
        dropDownGlobalCardType.setPrefSize(150, 20);
        dropDownGlobalCardType.setOnAction(e -> {
            //TODO: Filter by CardType
            String type = dropDownGlobalCardType.getValue();
            Map mapSorted = deck.sortDeck(true, "", type);
            updateGlobalListView(mapSorted);
        });


        hBoxTop.getChildren().addAll(buttonSortGlobalByName, buttonSortGlobalByType, dropDownGlobalCardType);


        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(4);

        //Adding the TextArea to the VBox

        globalListView.setDisable(true);

        vbox.getChildren().addAll(hBoxTop, globalListView);

        return vbox;
    }


    /**
     * For read a file line by line
     * @param file File object which have the math operation
     * @return an ArrayList of String lines of the file
     */
    private void processFile(File file){
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                Card currentCard = getStringAsCard(text);
                deck.add(deck.getId(), currentCard, true);
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



    }

    private Card getStringAsCard(String text) {
        String[] parts = text.split("\\|");
        return new Card(parts[0], parts[1]);

    }






}
