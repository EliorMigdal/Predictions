package predictions.controllers.admin.executions;

import dto.response.admin.execution.ExecutedSimulationsResponseDTO;
import dto.response.admin.general.ClientNamesResponseDTO;
import dto.response.admin.general.RequestsSizeResponseDTO;
import dto.response.mutual.executions.EntityCountDTO;
import dto.response.mutual.executions.EntityPopulationDTO;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import predictions.controllers.admin.executions.items.ExecutedSimulation;
import predictions.controllers.admin.executions.items.ExecutedSimulationCellFactory;
import predictions.controllers.mutual.executions.items.EntityCountCellController;
import predictions.httpclient.admin.executions.ExecutedSimulationsRequest;
import predictions.httpclient.admin.general.ClientsNamesRequest;
import predictions.httpclient.admin.general.RequestsSizeRequest;
import predictions.models.Model;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutionHistoryController implements Initializable {
    @FXML private MenuButton clientNameMenuButton;
    @FXML private MenuButton requestIDMenuButton;
    @FXML private Button showAllButton;
    @FXML private ProgressBar ticksProgressBar;
    @FXML private ProgressBar timeProgressBar;
    @FXML private Label ticksProgressLabel;
    @FXML private Label timeProgressLabel;
    @FXML private Label errorTitle;
    @FXML private VBox entityCountLayout;
    @FXML private ListView<ExecutedSimulation> finishedListView;
    @FXML private TextArea simulationErrorLabel;
    @FXML private Label averageLabel;
    @FXML private Label averageValue;
    @FXML private Label consistencyLabel;
    @FXML private Label consistencyValue;
    @FXML private MenuButton entitiesMenuButton;
    @FXML private MenuButton propertiesMenuButton;
    @FXML private LineChart<Integer,Integer> populationLineChart;
    @FXML private BarChart<Object,Object> propertiesBarChart;

    private final ObservableList<ExecutedSimulation> filteredList = FXCollections.observableArrayList();
    private final CopyOnWriteArrayList<ExecutedSimulation> fullExecutedList = new CopyOnWriteArrayList<>();
    private final SimpleStringProperty currentlySelectedClient = new SimpleStringProperty("");
    private final SimpleStringProperty currentlySelectedID = new SimpleStringProperty("");
    private final SimpleStringProperty ticksValue = new SimpleStringProperty("");
    private final SimpleStringProperty timeValue = new SimpleStringProperty("");
    private final SimpleDoubleProperty ticksPercentage = new SimpleDoubleProperty(0D);
    private final SimpleDoubleProperty timePercentage = new SimpleDoubleProperty(0D);
    private final SimpleStringProperty errorProperty = new SimpleStringProperty("");
    private final SimpleStringProperty averageProperty = new SimpleStringProperty("");
    private final SimpleStringProperty averageLabelProperty = new SimpleStringProperty("");
    private final SimpleStringProperty consistencyLabelProperty = new SimpleStringProperty("");
    private final SimpleStringProperty consistencyProperty = new SimpleStringProperty("");
    private final SimpleBooleanProperty showAllProperty = new SimpleBooleanProperty(true);
    private final SimpleIntegerProperty simulationsVersion = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty clientsVersion = new SimpleIntegerProperty(0);

    public ExecutionHistoryController() {
        Model.getInstance().getControllerFactory().setExecutionHistoryController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();
        initializeMenuButtons();
        initializeShowAllButton();
        bindAll();
        errorTitle.textProperty().set("");
        initializeGraphs();
    }

    private void initializeListView() {
        finishedListView.setItems(filteredList);
        ObjectProperty<Callback<ListView<ExecutedSimulation>, ListCell<ExecutedSimulation>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new ExecutedSimulationCellFactory());
        finishedListView.cellFactoryProperty().bind(cellFactoryProperty);
        initializeListUpdateTask();
    }

    private void initializeListUpdateTask() {
        ScheduledExecutorService scheduledThread = Executors.newSingleThreadScheduledExecutor();
        scheduledThread.scheduleAtFixedRate(() -> {
            ExecutedSimulationsResponseDTO executedSimulations = new ExecutedSimulationsRequest()
                    .getExecutedSimulations(simulationsVersion.get());

            if (executedSimulations.getVersion() > simulationsVersion.get()) {
                executedSimulations.getExecutedSimulations().forEach(simulation -> {
                    ExecutedSimulation newTile = new ExecutedSimulation(simulation);
                    Platform.runLater(() -> fullExecutedList.add(newTile));

                    if (matchesCurrentFilter(newTile)) {
                        Platform.runLater(() -> filteredList.add(newTile));
                    }
                });

                simulationsVersion.set(executedSimulations.getVersion());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void initializeMenuButtons() {
        clientNameMenuButton.setDisable(true);
        requestIDMenuButton.setDisable(true);
        entitiesMenuButton.setDisable(true);
        propertiesMenuButton.setDisable(true);
        initializeClientNamesTask();
    }

    private void initializeClientNamesTask() {
        ScheduledExecutorService clientUpdater = Executors.newSingleThreadScheduledExecutor();
        clientUpdater.scheduleAtFixedRate(() -> {
            ClientNamesResponseDTO clientNames = new ClientsNamesRequest().getClientsNames(clientsVersion.get());

            if (clientNames.getVersion() > clientsVersion.get()) {
                clientNames.getClientNames().forEach(name -> {
                    MenuItem newItem = new MenuItem(name);
                    newItem.setOnAction(event -> {
                        Platform.runLater(() -> {
                            clientNameMenuButton.setText(name);
                            requestIDMenuButton.setDisable(false);
                        });
                        initializeRequestIDs(name);
                        currentlySelectedClient.set(name);
                        filterSimulations();
                    });

                    Platform.runLater(() -> clientNameMenuButton.getItems().add(newItem));
                    clientsVersion.set(clientNames.getVersion());
                });
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void initializeRequestIDs(String clientName) {
        Platform.runLater(() -> requestIDMenuButton.getItems().clear());
        RequestsSizeResponseDTO requestsSize = new RequestsSizeRequest().getNumOfRequests(clientName);

        IntStream.range(0, requestsSize.getNumOfRequests()).forEach(index -> {
            MenuItem newItem = new MenuItem("Request#" + (index + 1));
            newItem.setOnAction(event -> {
                Platform.runLater(() -> requestIDMenuButton.setText(newItem.getText()));
                currentlySelectedID.set(String.valueOf(index));
                filterSimulations();
            });

            Platform.runLater(() -> requestIDMenuButton.getItems().add(newItem));
        });
    }

    private boolean matchesCurrentFilter(ExecutedSimulation tile) {
        return (currentlySelectedClient.get().equals(tile.clientNameProperty().get())
                && currentlySelectedID.get().equals(tile.requestIDProperty().get()))
                ||
                (currentlySelectedClient.get().equals(tile.clientNameProperty().get())
                && currentlySelectedID.get().equals(""))
                ||
                (currentlySelectedClient.get().equals("") && currentlySelectedID.get().equals(""));
    }

    private void filterSimulations() {
        new Thread(() -> {
            ObservableList<ExecutedSimulation> filteredList = FXCollections.observableArrayList();
            filteredList.addAll(fullExecutedList.stream()
                    .filter(this::matchesCurrentFilter)
                    .collect(Collectors.toList()));

            Platform.runLater(() -> this.filteredList.setAll(filteredList));
        }).start();
    }

    private void initializeShowAllButton() {
        showAllButton.setStyle("-fx-background-color: #00a100; -fx-border-color: rgb(180,180,180); -fx-text-fill: White");

        showAllButton.setOnAction(event -> {
            if (showAllProperty.get()) {
                showAllButton.setStyle("-fx-background-color: tranparent; -fx-border-color: rgb(180,180,180); -fx-text-fill: Black");
                clientNameMenuButton.setDisable(false);
                showAllProperty.set(false);
            } else {
                showAllButton.setStyle("-fx-background-color: #00a100; -fx-border-color: rgb(180,180,180); -fx-text-fill: White");
                clientNameMenuButton.setDisable(true);
                requestIDMenuButton.setDisable(true);
                clientNameMenuButton.setText("Client Name");
                requestIDMenuButton.setText("Request ID");
                showAllProperty.set(true);
            }
        });
    }

    private void bindAll() {
        ticksProgressBar.progressProperty().bind(ticksPercentage);
        timeProgressBar.progressProperty().bind(timePercentage);
        ticksProgressLabel.textProperty().bind(ticksValue);
        timeProgressLabel.textProperty().bind(timeValue);
        simulationErrorLabel.textProperty().bind(errorProperty);
        consistencyLabel.textProperty().bind(consistencyLabelProperty);
        consistencyValue.textProperty().bind(consistencyProperty);
        averageLabel.textProperty().bind(averageLabelProperty);
        averageValue.textProperty().bind(averageProperty);
    }

    public void setCurrentlySelectedClient(String currentlySelectedClient) {
        this.currentlySelectedClient.set(currentlySelectedClient);
    }

    public void setCurrentlySelectedID(String currentlySelectedID) {
        this.currentlySelectedID.set(currentlySelectedID);
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
        } else {
            errorTitle.textProperty().set("Runtime error");
        }
    }

    public void clearEntitiesLayout() {
        entityCountLayout.getChildren().clear();
    }

    public void generateEntityCell(EntityCountDTO entityInfo) {
        BorderPane newPane = new BorderPane();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Mutual/results/upperscreen" +
                "/entitypropertyDetailsCard.fxml"));
        try {
            Node loadComponent = loader.load();
            EntityCountCellController controller = loader.getController();
            controller.setEntityName(entityInfo.getEntityName());
            controller.setEntityCount(entityInfo.getEntityCount().toString());
            newPane.getChildren().add(loadComponent);
            Platform.runLater(() -> entityCountLayout.getChildren().add(newPane));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void initializeGraphs() {
        populationLineChart.getData().clear();
        populationLineChart.setAnimated(false);
        propertiesBarChart.getData().clear();
        populationLineChart.setAnimated(false);
    }

    public void resetMenuItems() {
        this.entitiesMenuButton.getItems().clear();
        this.entitiesMenuButton.setText("Entities");
        this.propertiesMenuButton.getItems().clear();
        this.propertiesMenuButton.setText("Properties");
        this.propertiesMenuButton.setDisable(true);
        this.averageProperty.set("");
        this.consistencyProperty.set("");
        this.averageLabelProperty.set("");
        this.consistencyLabelProperty.set("");
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

    public void generateLineChart(EntityPopulationDTO entityHistory) {
        XYChart.Series<Integer, Integer> chartSeries = new XYChart.Series<>();
        chartSeries.setName(entityHistory.getEntityName());
        entityHistory.getPopulationHistory().forEach(tick ->
                chartSeries.getData().add(new XYChart.Data<>(tick.getTickValue(), tick.getPopulationValue())));
        Platform.runLater(() -> {
            this.populationLineChart.getData().add(chartSeries);
            this.populationLineChart.getXAxis().setAutoRanging(false);
        });
    }

    public void generateBarChart(Map<Object, Integer> propertyHistogram) {
        XYChart.Series<Object, Object> series = new XYChart.Series<>();
        propertyHistogram.keySet().forEach(object ->
                series.getData().add(new XYChart.Data<>(object.toString(), propertyHistogram.get(object))));
        Platform.runLater(() -> {
            this.propertiesBarChart.setAnimated(false);
            this.propertiesBarChart.getData().add(series);
        });
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
}
