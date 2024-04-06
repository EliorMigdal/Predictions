package predictions.controllers.mutual.details.properties.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.*;

public class ActionPropertyCardController implements Initializable {
    @FXML private Label firstRowLeft;
    @FXML private Label secondRowLeft;
    @FXML private Label thirdRowLeft;
    @FXML private Label fourthRowLeft;
    @FXML private Label fifthRowLeft;
    @FXML private Label sixthRowLeft;
    @FXML private Label seventhRowLeft;
    @FXML private Label eighthRowLeft;
    @FXML private Label ningthRowLeft;
    @FXML private Label tenthRowLeft;
    @FXML private Label firstRowRight;
    @FXML private Label secondRowRight;
    @FXML private Label thirdRowRight;
    @FXML private Label fourthRowRight;
    @FXML private Label fifthRowRight;
    @FXML private Label sixthRowRight;
    @FXML private Label seventhRowRight;
    @FXML private Label eighthRowRight;
    @FXML private Label ninghtRowRight;
    @FXML private Label tenthRowRight;

    private ArrayList<Label> labelArray;
    private final Map<String,SimpleStringProperty> labelPropertyRows;

    public ActionPropertyCardController() {
        labelPropertyRows = new HashMap<>(20);
    }

    public void setPropertyID(String propID, String value){
        labelPropertyRows.get(propID).set(value);
    }

    public ArrayList<Label> getLabelArray() {
        return labelArray;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.labelArray = new ArrayList<>(20);
        Label[] labels = {
                firstRowLeft, firstRowRight,
                secondRowLeft, secondRowRight,
                thirdRowLeft, thirdRowRight,
                fourthRowLeft, fourthRowRight,
                fifthRowLeft, fifthRowRight,
                sixthRowLeft, sixthRowRight,
                seventhRowLeft, seventhRowRight,
                eighthRowLeft, eighthRowRight,
                ningthRowLeft, ninghtRowRight,
                tenthRowLeft, tenthRowRight
        };

        labelArray.addAll(Arrays.asList(labels));

        for (Label lab : labelArray) {
            String id = lab.getId();
            labelPropertyRows.put(id, new SimpleStringProperty(""));
            lab.textProperty().bind(labelPropertyRows.get(id));
        }
    }
}
