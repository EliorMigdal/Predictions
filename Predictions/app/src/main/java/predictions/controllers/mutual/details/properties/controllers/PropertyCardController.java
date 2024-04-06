package predictions.controllers.mutual.details.properties.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PropertyCardController implements Initializable {
    @FXML private Label topLeftLabel;
    @FXML private Label middleLeftLabel;
    @FXML private Label bottomLeftLabel;
    @FXML private Label topRightLabel;
    @FXML private Label middleRightLabel;
    @FXML private Label bottomRightLabel;

    private final SimpleStringProperty topLeftProperty;
    private final SimpleStringProperty middleLeftProperty;
    private final SimpleStringProperty bottomLeftProperty;
    private final SimpleStringProperty topRightProperty;
    private final SimpleStringProperty middleRightProperty;
    private final SimpleStringProperty bottomRightProperty;

    public PropertyCardController() {
        this.topLeftProperty = new SimpleStringProperty("");
        this.middleLeftProperty = new SimpleStringProperty("");
        this.bottomLeftProperty = new SimpleStringProperty("");
        this.topRightProperty = new SimpleStringProperty("");
        this.middleRightProperty = new SimpleStringProperty("");
        this.bottomRightProperty = new SimpleStringProperty("");
    }

    public void setTopLeftProperty(String topLeftProperty) {
        this.topLeftProperty.set(topLeftProperty);
    }

    public void setMiddleLeftProperty(String middleLeftProperty) {
        this.middleLeftProperty.set(middleLeftProperty);
    }

    public void setBottomLeftProperty(String bottomLeftProperty) {
        this.bottomLeftProperty.set(bottomLeftProperty);
    }

    public void setTopRightProperty(String topRightProperty) {
        this.topRightProperty.set(topRightProperty);
    }

    public void setMiddleRightProperty(String middleRightProperty) {
        this.middleRightProperty.set(middleRightProperty);
    }

    public void setBottomRightProperty(String bottomRightProperty) {
        this.bottomRightProperty.set(bottomRightProperty);
    }
    public void setProperty(int section, String key, String value) {
        switch (section) {
            case 0:
                setTopLeftProperty(key);
                setTopRightProperty(value);
                break;
            case 1:
                setMiddleLeftProperty(key);
                setMiddleRightProperty(value);
                break;
            case 2:
                setBottomLeftProperty(key);
                setBottomRightProperty(value);
                break;
            default:
                // Handle invalid section index
                break;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.topLeftLabel.textProperty().bind(topLeftProperty);
        this.middleLeftLabel.textProperty().bind(middleLeftProperty);
        this.bottomLeftLabel.textProperty().bind(bottomLeftProperty);
        this.topRightLabel.textProperty().bind(topRightProperty);
        this.middleRightLabel.textProperty().bind(middleRightProperty);
        this.bottomRightLabel.textProperty().bind(bottomRightProperty);
    }
}
