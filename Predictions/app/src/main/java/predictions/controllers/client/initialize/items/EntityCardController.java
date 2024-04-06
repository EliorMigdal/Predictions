package predictions.controllers.client.initialize.items;

import dto.response.client.init.SetPopulationResponseDTO;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import predictions.httpclient.client.init.SetPopulation;

import java.net.URL;
import java.util.ResourceBundle;

public class EntityCardController implements Initializable {
    @FXML private Label entityName;
    @FXML private TextField populationValue;
    @FXML private Label messageLabel;

    private final SimpleStringProperty messageProperty = new SimpleStringProperty("");
    private final SimpleStringProperty nameProperty = new SimpleStringProperty("");
    private final SimpleStringProperty populationProperty = new SimpleStringProperty("0");

    private ExecutionInfo executionItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
    }

    private void bindAll() {
        this.entityName.textProperty().bind(nameProperty);
        this.messageLabel.textProperty().bind(messageProperty);
        this.populationValue.textProperty().bind(populationProperty);
    }

    public String getEntityName() {
        return nameProperty.get();
    }

    public void setMessageInfo(String message, String style) {
        messageProperty.set(message);
        messageLabel.setStyle(style);
    }

    public void clearMessageInfo() {
        setMessageInfo("", "");
    }

    public void setEntityName(String entityName) {
        nameProperty.set(entityName);
    }

    public void setPopulationValue(String populationValue) {
        this.populationProperty.set(populationValue);
    }

    public void clearPopulationValue() {
        setPopulationValue("0");
    }

    public int getCurrentPopulation() {
        return Integer.parseInt(populationProperty.get());
    }

    private void setCurrentPopulation(int population) {
        populationProperty.set(Integer.toString(population));
    }

    @FXML private void decreasePopulation() {
        setCurrentPopulation(getCurrentPopulation() - 1);
    }

    @FXML private void increasePopulation() {
        setCurrentPopulation(getCurrentPopulation() + 1);
    }

    @FXML private void updatePopulation(KeyEvent event) {
        entityName.getParent().setStyle("-fx-border-color: white");

        if (event.getCode() == KeyCode.ENTER) {
            handleEnter();
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            handleBackSpace();
        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            clearMessageInfo();
            populationValue.positionCaret(populationValue.getCaretPosition());
        } else if (event.getCode().isDigitKey()) {
            clearMessageInfo();

            if (getCurrentPopulation() == 0) {
                setPopulationValue("");
            }

            String appender = populationProperty.get() + event.getText();
            setPopulationValue(appender);
            populationValue.positionCaret(populationProperty.get().length());
        } else if (event.getCode().isLetterKey()) {
            setMessageInfo("Please enter digits only!", "-fx-text-fill: red");
        }
    }

    private void handleEnter() {
        int newPopulation = getCurrentPopulation();
        boolean isPositive = newPopulation >= 0;

        if (!isPositive) {
            setMessageInfo("Only positive numbers!", "-fx-text-fill: red");
        } else {
            SetPopulationResponseDTO setPopulation = new SetPopulation().setPopulationSize(
                    executionItem.getClientName(), executionItem.getRequestID(), executionItem.getSimulationID(),
                    entityName.getText(), newPopulation);

            if (setPopulation.getSuccessfullySet()) {
                setMessageInfo("Successfully updated!", "-fx-text-fill: green");
            } else {
                setMessageInfo("Too much instances!", "-fx-text-fill: red");
            }

            handleAnimation();
            clearPopulationValue();
        }
    }

    private void handleAnimation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), messageLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    private void handleBackSpace() {
        clearMessageInfo();
        int caretPosition = populationValue.getCaretPosition();
        String currentPopulation = populationProperty.get();

        if (!currentPopulation.isEmpty() && caretPosition > 0) {
            String updatedPopulation = currentPopulation.substring(0, caretPosition - 1)
                    + currentPopulation.substring(caretPosition);

            updatedPopulation = updatedPopulation.isEmpty() ? "0" : updatedPopulation;
            setPopulationValue(updatedPopulation);
            populationValue.positionCaret(caretPosition - 1);
        }
    }

    @FXML private void setOnMouseClicked() {
        int clickPosition = this.populationValue.getCaretPosition();
        this.populationValue.positionCaret(clickPosition);
    }

    public void setExecutionItem(ExecutionInfo executionItem) {
        this.executionItem = executionItem;
    }

    public void resetPopulationValue() {
        new SetPopulation().setPopulationSize(executionItem.getClientName(), executionItem.getRequestID(),
                executionItem.getSimulationID(), entityName.getText(), 0);
        setMessageInfo("Successfully reset!", "-fx-text-fill: green");
        handleAnimation();
    }
}