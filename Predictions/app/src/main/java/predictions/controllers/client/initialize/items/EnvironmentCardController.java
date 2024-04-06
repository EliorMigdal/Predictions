package predictions.controllers.client.initialize.items;

import dto.response.client.init.SetEnvValueResponseDTO;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import predictions.httpclient.client.init.SetEnvironmentValue;

import java.net.URL;
import java.util.ResourceBundle;

public class EnvironmentCardController implements Initializable {
    @FXML private Label nameValue;
    @FXML private Label typeValue;
    @FXML private Label rangeValue;
    @FXML private Label messageLabel;
    @FXML private Label rangeLabel;
    @FXML private TextField propertyValue;

    private final SimpleStringProperty messageProperty = new SimpleStringProperty("");
    private final SimpleStringProperty environmentNameProperty = new SimpleStringProperty("");
    private final SimpleStringProperty environmentTypeProperty = new SimpleStringProperty("");
    private final SimpleStringProperty rangeValueProperty = new SimpleStringProperty("");
    private final SimpleStringProperty envValueProperty = new SimpleStringProperty("0");

    private ExecutionInfo executionItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
    }

    private void bindAll() {
        this.nameValue.textProperty().bind(environmentNameProperty);
        this.messageLabel.textProperty().bind(messageProperty);
        this.rangeValue.textProperty().bind(rangeValueProperty);
        this.typeValue.textProperty().bind(environmentTypeProperty);
        this.propertyValue.textProperty().bind(envValueProperty);
        this.rangeLabel.visibleProperty().bind(Bindings.createObjectBinding(() ->
                rangeValue.textProperty().get().equals("")));
    }

    public void clearMessageInfo() {
        messageProperty.set("");
    }

    public void clearEnvValueProperty() {
        envValueProperty.set("");
    }

    @FXML private void updateEnvironmentProperty(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleEnter();
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            handleBackSpace();
        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            handleLeftRight();
        } else if (event.getCode() == KeyCode.PERIOD) {
            handleDot(event);
        } else {
            handleDigitInput(event);
        }
    }

    private void handleEnter() {
        SetEnvValueResponseDTO setValueResponse = new SetEnvironmentValue().setEnvironmentValue(
                executionItem.getClientName(), executionItem.getRequestID(),
                executionItem.getSimulationID(), environmentNameProperty.get(), envValueProperty.get(),
                false);

        if (setValueResponse.getSuccessfullySet()) {
            setMessageInfo(setValueResponse.getMessage(), "-fx-text-fill: green");
        } else {
            setMessageInfo(setValueResponse.getMessage(), "-fx-text-fill: red");
        }

        handleAnimation();
        clearEnvValueProperty();
    }

    private void handleAnimation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), messageLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    private void handleBackSpace() {
        clearMessageInfo();
        int caretPosition = propertyValue.getCaretPosition();
        String currentValue = envValueProperty.get();

        if (!currentValue.isEmpty() && caretPosition > 0) {
            StringBuilder updatedValue = new StringBuilder(currentValue);
            updatedValue.deleteCharAt(caretPosition - 1);
            envValueProperty.set(updatedValue.toString());
            propertyValue.positionCaret(caretPosition - 1);
        } else {
            envValueProperty.set("0");
            propertyValue.positionCaret(0);
        }
    }

    private void handleLeftRight() {
        clearMessageInfo();
        propertyValue.positionCaret(propertyValue.getCaretPosition());
    }

    private void handleDigitInput(KeyEvent event) {
        clearMessageInfo();

        if (envValueProperty.get().equals("0")) {
            envValueProperty.set("");
        }

        String appender = envValueProperty.get() + event.getText();
        envValueProperty.set(appender);
        propertyValue.positionCaret(envValueProperty.get().length());
    }

    private void handleDot(KeyEvent event) {
        String appender = envValueProperty.get() + event.getText();
        envValueProperty.set(appender);
        propertyValue.positionCaret(envValueProperty.get().length());
    }

    @FXML private void setOnMouseClicked() {
        int clickPosition = propertyValue.getCaretPosition();
        propertyValue.positionCaret(clickPosition);
    }

    private void setMessageInfo(String message, String style) {
        messageProperty.set(message);
        messageLabel.setStyle(style);
    }

    public void setExecutionItem(ExecutionInfo executionItem) {
        this.executionItem = executionItem;
    }

    public void setEnvironmentNameProperty(String environmentNameProperty) {
        this.environmentNameProperty.set(environmentNameProperty);
    }

    public void setEnvironmentTypeProperty(String environmentTypeProperty) {
        this.environmentTypeProperty.set(environmentTypeProperty);
    }

    public void setRangeValueProperty(String rangeValueProperty) {
        this.rangeValueProperty.set(rangeValueProperty);
    }

    public void resetEnvironmentValue() {
        new SetEnvironmentValue().setEnvironmentValue(executionItem.getClientName(), executionItem.getRequestID(),
                executionItem.getSimulationID(), environmentNameProperty.get(), envValueProperty.get(),
                true);
        setMessageInfo("Successfully reset!", "-fx-text-fill: green");
        handleAnimation();
    }
}
