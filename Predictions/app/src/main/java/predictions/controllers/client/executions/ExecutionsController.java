package predictions.controllers.client.executions;

import dto.request.client.executions.enums.ControlOptions;
import dto.response.client.executions.RemainingExecutionsResponseDTO;
import dto.response.client.executions.SimulationStatusResponseDTO;
import dto.response.client.init.NewExecutionDTO;
import dto.response.mutual.executions.EntitiesCountResponseDTO;
import dto.response.mutual.executions.EntityPopulationDTO;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import predictions.controllers.client.executions.items.SimulationItem;
import predictions.controllers.client.executions.items.SimulationItemFactory;
import predictions.controllers.client.executions.manager.ExecutionsTaskManager;
import predictions.controllers.client.initialize.InitializationController;
import predictions.controllers.client.initialize.items.ExecutionInfo;
import predictions.controllers.mutual.executions.items.EntityItem;
import predictions.httpclient.client.executions.ControlSimulation;
import predictions.httpclient.client.executions.RemainingExecutions;
import predictions.httpclient.client.executions.SimulationStatus;
import predictions.httpclient.client.init.NewExecution;
import predictions.httpclient.mutual.executions.EntitiesCountRequest;
import predictions.models.Model;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ExecutionsController implements Initializable {
    @FXML private ListView<SimulationItem> runningSimulationsListView;
    @FXML private ListView<SimulationItem> finishedSimulationsListView;

    @FXML private ProgressBar ticksProgressBar;
    @FXML private Label ticksProgressLabel;
    @FXML private ProgressBar timeProgressBar;
    @FXML private Label timeProgressLabel;

    @FXML private AnchorPane entityCountLayout;

    @FXML private Label errorTitle;
    @FXML private TextArea simulationErrorLabel;

    @FXML private Button reRunButton;
    @FXML private Button startButton;
    @FXML private Button pauseButton;
    @FXML private Button stopButton;

    @FXML private MenuButton entitiesMenuButton;
    @FXML private MenuButton propertiesMenuButton;
    @FXML private Label averageLabel;
    @FXML private Label averageValue;
    @FXML private Label consistencyLabel;
    @FXML private Label consistencyValue;
    @FXML private LineChart<Object,Object> populationLineChart;
    @FXML private BarChart<Object,Object> propertiesBarChart;

    private final ExecutionsTaskManager taskManager = new ExecutionsTaskManager(this);
    private final String clientName = Model.getInstance().getCurrConnectedEntity();

    private final ObservableList<SimulationItem> allSimulationItems = FXCollections.observableArrayList();
    private final ObservableList<SimulationItem> runningSimulations = FXCollections.observableList(new CopyOnWriteArrayList<>());
    private final ObservableList<SimulationItem> finishedSimulations = FXCollections.observableList(new CopyOnWriteArrayList<>());

    private final SimpleStringProperty ticksValue = new SimpleStringProperty("");
    private final SimpleStringProperty timeValue = new SimpleStringProperty("");
    private final SimpleDoubleProperty ticksPercentage = new SimpleDoubleProperty(0D);
    private final SimpleDoubleProperty timePercentage = new SimpleDoubleProperty(0D);

    private final SimpleStringProperty errorProperty = new SimpleStringProperty("");

    private final SimpleStringProperty averageProperty = new SimpleStringProperty("");
    private final SimpleStringProperty averageLabelProperty = new SimpleStringProperty("");
    private final SimpleStringProperty consistencyLabelProperty = new SimpleStringProperty("");
    private final SimpleStringProperty consistencyProperty = new SimpleStringProperty("");

    private SimulationItem currentlySelectedItem = null;
    private final SimpleIntegerProperty currentlySelectedID = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty simulationsVersion = new SimpleIntegerProperty(0);

    private final CustomBooleanProperty currentlySelectedRunningProperty = new CustomBooleanProperty();
    private static class CustomBooleanProperty extends SimpleBooleanProperty {
        public void set(boolean newValue) {
            if (get() != newValue) {
                super.set(newValue);
                fireValueChangedEvent();
            }
        }
    }

    public ExecutionsController() {
        Model.getInstance().getControllerFactory().setExecutionsController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
        initializeRunningList();
        initializeFinishedList();
        initializeReRunButtonAction();
        initializeControlButtonActions();
    }

    private void initializeRunningList() {
        runningSimulationsListView.setItems(runningSimulations);
        ObjectProperty<Callback<ListView<SimulationItem>, ListCell<SimulationItem>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new SimulationItemFactory());
        runningSimulationsListView.cellFactoryProperty().bind(cellFactoryProperty);
    }

    private void initializeFinishedList() {
        finishedSimulationsListView.setItems(finishedSimulations);
        ObjectProperty<Callback<ListView<SimulationItem>, ListCell<SimulationItem>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new SimulationItemFactory());
        finishedSimulationsListView.cellFactoryProperty().bind(cellFactoryProperty);
    }

    private void initializeReRunButtonAction() {
        reRunButton.setOnAction(event -> {
            stopAllTasks();
            Model.getInstance().getControllerFactory().getClientMenuController().showSimulationExecute();
            InitializationController initializationController = Model.getInstance()
                    .getControllerFactory().getExecutionController();

            new Thread(() -> {
                NewExecutionDTO newExecution = new NewExecution().createNewExecution(
                        clientName,
                        currentlySelectedItem.getExecutionInfo().getRequestID(),
                        currentlySelectedItem.getExecutionInfo().getSimulationName());

                ExecutionInfo newExecutionItem = new ExecutionInfo(
                        clientName,
                        currentlySelectedItem.getExecutionInfo().getRequestID(),
                        currentlySelectedItem.getExecutionInfo().getSimulationName(),
                        newExecution.getSimulationID());

                Platform.runLater(() -> {
                    initializationController.setExecutionItem(newExecutionItem);
                    initializationController.generateEntitiesLayout();
                    initializationController.generateEnvironmentLayout();
                });
            }).start();
        });
    }

    private void initializeControlButtonActions() {
        initializePauseButtonAction();
        initializeStartButtonAction();
        initializeStopButtonAction();
    }

    private void initializePauseButtonAction() {
        pauseButton.setOnAction(event -> {
            new ControlSimulation().controlSimulation(
                    currentlySelectedItem.getExecutionInfo().getClientName(),
                    currentlySelectedItem.getExecutionInfo().getRequestID(),
                    currentlySelectedItem.getExecutionInfo().getSimulationID(),
                    ControlOptions.PAUSE);

            pauseButton.setDisable(true);
            startButton.setDisable(false);
            stopButton.setDisable(false);
        });
    }

    private void initializeStartButtonAction() {
        startButton.setOnAction(event -> {
            new ControlSimulation().controlSimulation(
                    currentlySelectedItem.getExecutionInfo().getClientName(),
                    currentlySelectedItem.getExecutionInfo().getRequestID(),
                    currentlySelectedItem.getExecutionInfo().getSimulationID(),
                    ControlOptions.START);

            pauseButton.setDisable(false);
            startButton.setDisable(true);
            stopButton.setDisable(false);
        });
    }

    private void initializeStopButtonAction() {
        stopButton.setOnAction(event -> {
            new ControlSimulation().controlSimulation(
                    currentlySelectedItem.getExecutionInfo().getClientName(),
                    currentlySelectedItem.getExecutionInfo().getRequestID(),
                    currentlySelectedItem.getExecutionInfo().getSimulationID(),
                    ControlOptions.STOP);

            pauseButton.setDisable(true);
            startButton.setDisable(true);
            stopButton.setDisable(true);
        });
    }

    private void bindAll() {
        bindProgressItems();
        bindResultsItems();
        bindErrorItems();
        bindAllButtons();
    }

    private void bindProgressItems() {
        ticksProgressBar.progressProperty().bind(ticksPercentage);
        timeProgressBar.progressProperty().bind(timePercentage);
        ticksProgressLabel.textProperty().bind(ticksValue);
        timeProgressLabel.textProperty().bind(timeValue);
    }

    private void bindResultsItems() {
        consistencyLabel.textProperty().bind(consistencyLabelProperty);
        consistencyValue.textProperty().bind(consistencyProperty);
        averageLabel.textProperty().bind(averageLabelProperty);
        averageValue.textProperty().bind(averageProperty);
    }

    private void bindErrorItems() {
        simulationErrorLabel.textProperty().bind(errorProperty);

        simulationErrorLabel.visibleProperty().bind(Bindings.createObjectBinding(() ->
                !simulationErrorLabel.getText().equals("")));
    }

    private void bindAllButtons() {
        currentlySelectedRunningProperty.addListener((observable, oldValue, newValue) -> {
            updateReRunButtonState();
            updateMenuItemsState();
            updateControlButtonsState();
        });

        runningSimulationsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> updateIsRunningProperty(newValue));

        finishedSimulationsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> updateIsRunningProperty(newValue));

        updateReRunButtonState();
        updateMenuItemsState();
        updateControlButtonsState();
    }

    private void updateIsRunningProperty(SimulationItem selectedItem) {
        if (selectedItem != null) {
            currentlySelectedRunningProperty.bind(selectedItem.isRunningProperty());
        } else {
            currentlySelectedRunningProperty.unbind();
            currentlySelectedRunningProperty.set(false);
        }
    }

    private void updateReRunButtonState() {
        SimulationItem finishedSelectedItem = finishedSimulationsListView.getSelectionModel().getSelectedItem();

        reRunButton.setDisable(currentlySelectedRunningProperty.get() ||
                (finishedSelectedItem != null && remainingExecutionsAreZero(finishedSelectedItem)));
    }

    private boolean remainingExecutionsAreZero(SimulationItem item) {
        RemainingExecutionsResponseDTO remainingExecutions = new RemainingExecutions().getNumOfRemaining(
                item.getExecutionInfo().getClientName(),
                item.getExecutionInfo().getRequestID());
        return remainingExecutions.getRemainingExecutions() <= 0;
    }

    private void updateMenuItemsState() {
        if (!currentlySelectedRunningProperty.get()) {
            entitiesMenuButton.setDisable(false);
            propertiesMenuButton.setDisable(true);
        } else {
            entitiesMenuButton.setDisable(true);
            propertiesMenuButton.setDisable(true);
        }
    }

    private void updateControlButtonsState() {
        SimulationItem runningSelectedItem = runningSimulationsListView.getSelectionModel().getSelectedItem();

        if (!currentlySelectedRunningProperty.get()) {
            disableAllControlButtons();
        } else if (runningSelectedItem != null) {
            new Thread(() -> {
                SimulationStatusResponseDTO statusDTO = new SimulationStatus().getSimulationStatus(
                        runningSelectedItem.getExecutionInfo().getClientName(),
                        runningSelectedItem.getExecutionInfo().getRequestID(),
                        runningSelectedItem.getExecutionInfo().getSimulationID());

                Platform.runLater(() -> {
                    if (statusDTO.getPaused()) {
                        startButton.setDisable(false);
                        pauseButton.setDisable(true);
                    } else {
                        startButton.setDisable(true);
                        pauseButton.setDisable(false);
                    }

                    stopButton.setDisable(false);
                });
            }).start();
        }
    }

    public void setSelectedID(Integer simulationID) {
        this.currentlySelectedID.set(simulationID);
    }

    public int getCurrentlySelectedID() {
        return currentlySelectedID.get();
    }

    private void disableAllControlButtons() {
        startButton.setDisable(true);
        stopButton.setDisable(true);
        pauseButton.setDisable(true);
    }

    public void updateEntitiesCount() {
        EntitiesCountResponseDTO entitiesCount = new EntitiesCountRequest().getEntitiesCount(
                currentlySelectedItem.getExecutionInfo().getClientName(),
                currentlySelectedItem.getExecutionInfo().getRequestID(),
                currentlySelectedItem.getExecutionInfo().getSimulationID());

        currentlySelectedItem.getExecutionInfo().getEntityItems().forEach(entityItem ->
                entitiesCount.getEntitiesCount().forEach(entityCountDTO -> {
                    if (entityCountDTO.getEntityName().equals(entityItem.entityNameProperty().get())) {
                        Platform.runLater(() -> entityItem.setEntityCount(entityCountDTO.getEntityCount().toString()));
                    }
        }));
    }

    public void clearEntitiesLayout() {
        entityCountLayout.getChildren().clear();
    }

    public void updateEntitiesCells() {
        int cardCounts = 0;
        HBox currHbox = new HBox();
        initializeHBox(currHbox);

        for (EntityItem entityItem : currentlySelectedItem.getExecutionInfo().getEntityItems()) {
            if (cardCounts % 3 == 0) {
                currHbox = new HBox();
                initializeHBox(currHbox);
            }

            if (currHbox.getChildren().size() == 3 || currHbox.getChildren().isEmpty()) {
                entityCountLayout.getChildren().add(currHbox);
            }

            currHbox.getChildren().add(entityItem.getEntityBox());
            cardCounts++;
        }
    }

    public void setTicksValue(String ticksValue) {
        this.ticksValue.set(ticksValue);
    }

    public void setTimeValue(String timeValue) {
        this.timeValue.set(timeValue);
    }

    public void setTicksPercentage(double ticksPercentage) {
        this.ticksPercentage.set(ticksPercentage);
    }

    public void setTimePercentage(double timePercentage) {
        this.timePercentage.set(timePercentage);
    }

    public void setErrorProperty(String errorProperty) {
        this.errorProperty.set(errorProperty);

        if (errorProperty.equals("")) {
            errorTitle.textProperty().set("");
            simulationErrorLabel.setDisable(true);
        } else {
            errorTitle.textProperty().set("Runtime error");
            simulationErrorLabel.setDisable(false);
        }
    }

    private void initializeHBox(HBox hbox) {
        hbox.setMaxWidth(542);
        hbox.setMinHeight(59);
        hbox.setSpacing(45);
        HBox.setMargin(hbox, new Insets(0, 50, 0, 30));
    }

    public void initializeGraphs() {
        populationLineChart.getData().clear();
        populationLineChart.setAnimated(false);
        populationLineChart.setStyle("/predictions/controllers/client/executions/styles/lineChart.css");

        propertiesBarChart.getData().clear();
        propertiesBarChart.setAnimated(false);
        propertiesBarChart.setStyle("/predictions/controllers/client/executions/styles/barChart.css");
    }

    public void resetMenuItems() {
        this.entitiesMenuButton.getItems().clear();
        this.entitiesMenuButton.setText("Entities");

        this.propertiesMenuButton.getItems().clear();
        this.propertiesMenuButton.setText("Properties");

        clearPropertyDetails();
    }

    public void clearPropertyDetails() {
        this.averageProperty.set("");
        this.consistencyProperty.set("");
        this.averageLabelProperty.set("");
        this.consistencyLabelProperty.set("");
    }

    public void enableEntitiesMenuButton() {
        entitiesMenuButton.setDisable(false);
    }

    public void enablePropertyMenuButton() {
        propertiesMenuButton.setDisable(false);
    }

    public void addNewEntityMenuItem(MenuItem entityItem) {
        this.entitiesMenuButton.getItems().add(entityItem);
    }

    public void addNewPropertyMenuItem(MenuItem propertyItem) {
        this.propertiesMenuButton.getItems().add(propertyItem);
    }

    public void setEntityMenuText(String entityName) {
        this.entitiesMenuButton.setText(entityName);
    }

    public void setPropertyMenuText(String propertyName) {
        this.propertiesMenuButton.setText(propertyName);
    }

    public void clearPropertyMenuItems() {
        this.propertiesMenuButton.getItems().clear();
        this.propertiesMenuButton.setText("Properties");
    }

    public void clearPropertyBarChart() {
        this.propertiesBarChart.getData().clear();
    }

    public void generateLineChart(EntityPopulationDTO entityHistory) {
        XYChart.Series<Object, Object> chartSeries = new XYChart.Series<>();
        chartSeries.setName(entityHistory.getEntityName());
        int difference = (entityHistory.getPopulationHistory().size() / 10) + 1;
        entityHistory.getPopulationHistory().forEach(tick -> {
            if (tick.getTickValue() == 1 || tick.getTickValue() % difference == 0 ||
                    tick.getTickValue().equals(entityHistory.getPopulationHistory().size())) {
                chartSeries.getData().add(new XYChart.Data<>(tick.getTickValue().toString(),
                        tick.getPopulationValue()));
            }
        });

        this.populationLineChart.getData().add(chartSeries);
    }

    public void generateBarChart(Map<Object, Integer> propertyHistogram) {
        XYChart.Series<Object, Object> series = new XYChart.Series<>();

        List<Object> sortedKeys = propertyHistogram.keySet()
                .stream()
                .sorted((key1, key2) -> {
                    if (key1 instanceof Integer && key2 instanceof Integer) {
                        return Integer.compare((Integer) key1, (Integer) key2);
                    } else {
                        return key1.toString().compareTo(key2.toString());
                    }
                })
                .collect(Collectors.toList());

        for (Object key : sortedKeys) {
            series.getData().add(new XYChart.Data<>(key.toString(), propertyHistogram.get(key)));
        }

        this.propertiesBarChart.setAnimated(false);
        this.propertiesBarChart.getData().add(series);
    }

    public void setAverageProperty(String averageProperty) {
        this.averageProperty.set(averageProperty);
    }

    public void setAverageLabelProperty(String averageLabelProperty) {
        this.averageLabelProperty.set(averageLabelProperty);
    }

    public void setConsistencyLabelProperty(String consistencyLabelProperty) {
        this.consistencyLabelProperty.set(consistencyLabelProperty);
    }

    public void setConsistencyProperty(String consistencyProperty) {
        this.consistencyProperty.set(consistencyProperty);
    }

    public ObservableList<SimulationItem> getAllSimulationItems() {
        return allSimulationItems;
    }

    public ObservableList<SimulationItem> getRunningSimulations() {
        return runningSimulations;
    }

    public ObservableList<SimulationItem> getFinishedSimulations() {
        return finishedSimulations;
    }

    public int getSimulationsVersion() {
        return simulationsVersion.get();
    }

    public SimulationItem getCurrentlySelectedItem() {
        return currentlySelectedItem;
    }

    public SimulationItem getCurrentlySelectedRunningItem() {
        return runningSimulationsListView.getSelectionModel().getSelectedItem();
    }

    public void setCurrentlySelectedRunningItem(SimulationItem simulationItem) {
        finishedSimulationsListView.getSelectionModel().select(null);
        runningSimulationsListView.getSelectionModel().select(simulationItem);
        this.currentlySelectedItem = simulationItem;
        clearEntitiesLayout();
        updateEntitiesCells();
    }

    public void setCurrentlySelectedFinishedItem(SimulationItem simulationItem) {
        runningSimulationsListView.getSelectionModel().select(null);
        finishedSimulationsListView.getSelectionModel().select(simulationItem);
        this.currentlySelectedItem = simulationItem;
        clearEntitiesLayout();
        updateEntitiesCells();
    }

    public String getClientName() {
        return clientName;
    }

    public void stopAllTasks() {
        this.taskManager.stopAllTasks();
    }

    public void startAllTasks() {
        this.taskManager.startAllTasks();
    }
}
