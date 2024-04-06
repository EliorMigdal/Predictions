package predictions.controllers.client.details;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import predictions.controllers.mutual.details.DetailsController;
import predictions.controllers.mutual.details.tree.node.TreeNode;
import predictions.controllers.mutual.simulations.SimulationsListController;
import predictions.controllers.mutual.simulations.items.Simulation;
import predictions.controllers.mutual.simulations.items.SimulationsCellFactory;
import predictions.models.Model;

import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class SimulationDetailsController extends ResourceBundle implements Initializable {
    @FXML public TreeView<TreeNode> elementTreeView;
    @FXML public VBox propertiesLayout;
    @FXML public ListView<Simulation> simulationsListView;

    private final ObservableList<Simulation> simulationsList = FXCollections.observableArrayList();
    private final SimpleIntegerProperty simulationsVersion = new SimpleIntegerProperty(0);

    private final SimulationsListController listController = new SimulationsListController();
    private final DetailsController detailsController = new DetailsController();

    public SimulationDetailsController() {
        Model.getInstance().getControllerFactory().setSimulationDetailsController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();
        listController.setSimulationsList(simulationsList);
        listController.setSimulationsVersion(simulationsVersion);
        initializeSimulationsTask();
        initializeTreeView();
    }

    private void initializeListView() {
        simulationsListView.setItems(simulationsList);
        ObjectProperty<Callback<ListView<Simulation>, ListCell<Simulation>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new SimulationsCellFactory(detailsController, listController));
        simulationsListView.cellFactoryProperty().bind(cellFactoryProperty);
    }

    private void initializeSimulationsTask() {
        listController.initializeSimulationsTask();
    }

    private void initializeTreeView() {
        detailsController.setElementTreeView(elementTreeView);
        detailsController.setPropertiesLayout(propertiesLayout);
        detailsController.initializeTreeView();
    }

    @Override
    protected Object handleGetObject(String key) {
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }
}
