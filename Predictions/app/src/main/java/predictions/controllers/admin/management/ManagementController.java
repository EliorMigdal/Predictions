package predictions.controllers.admin.management;

import dto.response.admin.management.LoadFileResponseDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import predictions.controllers.mutual.details.DetailsController;
import predictions.controllers.mutual.details.tree.node.TreeNode;
import predictions.controllers.mutual.simulations.SimulationsListController;
import predictions.controllers.mutual.simulations.items.Simulation;
import predictions.controllers.mutual.simulations.items.SimulationsCellFactory;
import predictions.httpclient.admin.management.LoadXMLRequest;
import predictions.models.Model;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class ManagementController extends ResourceBundle implements Initializable {
    @FXML public ImageView loadFileButton;
    @FXML public TextArea messageLabel;
    @FXML public TreeView<TreeNode> elementTreeView;
    @FXML public VBox propertiesLayout;
    @FXML public ListView<Simulation> simulationsListView;

    private final ObservableList<Simulation> simulationsList = FXCollections.observableArrayList();
    private final SimpleIntegerProperty simulationsVersion = new SimpleIntegerProperty(0);
    private final SimpleStringProperty messageProperty = new SimpleStringProperty("Please load XML file");

    private final SimulationsListController listController = new SimulationsListController();
    private final DetailsController detailsController = new DetailsController();

    public ManagementController() {
        Model.getInstance().getControllerFactory().setManagementController(this);
    }

    @Override
    protected Object handleGetObject(String key) {
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeLoadButton();
        initializeListView();
        initializeSimulationsTask();
        initializeTreeView();
    }

    private void initializeLoadButton() {
        messageLabel.textProperty().bind(messageProperty);
        loadFileButton.setOnMousePressed(event -> {
            File selectedFile = chooseXMLFile();
            if (selectedFile == null) {
                return;
            }

            String path = selectedFile.getAbsolutePath();
            LoadFileResponseDTO loadResponse = new LoadXMLRequest().loadXMLRequest(path);
            handleMessageLabel(loadResponse);
        });
    }

    private void handleMessageLabel(LoadFileResponseDTO responseDTO) {
        if (responseDTO.isLoadedSuccessfully()) {
            messageLabel.setStyle("-fx-text-fill: green");
        } else {
            messageLabel.setStyle("-fx-text-fill: red");
        }

        messageProperty.set(responseDTO.getMessage());
    }

    private void initializeListView() {
        simulationsListView.setItems(simulationsList);
        ObjectProperty<Callback<ListView<Simulation>, ListCell<Simulation>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new SimulationsCellFactory(detailsController, listController));
        simulationsListView.cellFactoryProperty().bind(cellFactoryProperty);
        listController.setSimulationsList(simulationsList);
        listController.setSimulationsVersion(simulationsVersion);
    }

    private void initializeSimulationsTask() {
        listController.initializeSimulationsTask();
    }

    private void initializeTreeView() {
        detailsController.setElementTreeView(elementTreeView);
        detailsController.setPropertiesLayout(propertiesLayout);
        detailsController.initializeTreeView();
    }

    private File chooseXMLFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select simulation XML");
        fileChooser.getExtensionFilters().add(new FileChooser
                .ExtensionFilter("xml files", "*.xml"));
        Stage currStage = (Stage) loadFileButton.getScene().getWindow();
        return fileChooser.showOpenDialog(currStage);
    }
}
