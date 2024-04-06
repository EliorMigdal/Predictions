package predictions.controllers.admin.threadmanagment;

import dto.response.admin.activity.ClientUsageDTO;
import dto.response.admin.activity.ClientUsageResponseDTO;
import dto.response.admin.activity.CurrentlyRunningResponseDTO;
import dto.response.admin.activity.SetThreadSizeResponseDTO;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import predictions.controllers.admin.threadmanagment.enums.SortChoices;
import predictions.controllers.admin.threadmanagment.items.ClientUsage;
import predictions.controllers.admin.threadmanagment.items.ClientUsageCellFactory;
import predictions.controllers.admin.threadmanagment.items.CurrentlyRunning;
import predictions.controllers.admin.threadmanagment.items.CurrentlyRunningCellFactory;
import predictions.controllers.admin.threadmanagment.piechart.PieChartTask;
import predictions.httpclient.admin.activity.ClientUsageRequest;
import predictions.httpclient.admin.activity.CurrentlyRunningRequest;
import predictions.httpclient.admin.activity.SetThreadPoolSizeRequest;
import predictions.models.Model;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadManagementController implements Initializable {
    @FXML private ListView<ClientUsage> clientsListView;
    @FXML private ListView<CurrentlyRunning> currentlyRunningListView;
    @FXML private PieChart pieChart;
    @FXML private TextField poolSizeField;
    @FXML private MenuButton sortChoice;
    @FXML private Label errorLabel;
    @FXML private Text sizeLabel;

    private final SimpleStringProperty poolSizeProperty = new SimpleStringProperty("0");
    private final SimpleStringProperty errorLabelProperty = new SimpleStringProperty("");
    private final SimpleStringProperty sizeProperty = new SimpleStringProperty("0");
    private final SimpleStringProperty currentlySelectedClient = new SimpleStringProperty("");
    private final SimpleIntegerProperty usageVersion = new SimpleIntegerProperty(0);
    private final ObservableList<ClientUsage> clientUsageList = FXCollections.observableArrayList();
    private final ObservableList<CurrentlyRunning> currentlyRunningList = FXCollections.observableArrayList();

    public ThreadManagementController() {
        Model.getInstance().getControllerFactory().setThreadManagementController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeChartTask();
        initializeUsageList();
        initializeUsageTask();
        initializeRunningList();
        initializeRunningTask();
        bindAll();
        initializeTextField();
        initializeSortItems();
    }

    private void initializeChartTask() {
        PieChartTask chartTask = new PieChartTask(pieChart);
        ScheduledExecutorService chartUpdater = Executors.newSingleThreadScheduledExecutor();
        chartUpdater.scheduleAtFixedRate(chartTask, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void initializeUsageList() {
        clientsListView.setItems(clientUsageList);
        ObjectProperty<Callback<ListView<ClientUsage>, ListCell<ClientUsage>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new ClientUsageCellFactory());
        clientsListView.cellFactoryProperty().bind(cellFactoryProperty);
    }

    private void initializeUsageTask() {
        ScheduledExecutorService usageUpdater = Executors.newSingleThreadScheduledExecutor();
        usageUpdater.scheduleAtFixedRate(() -> {
            ClientUsageResponseDTO clientUsage = new ClientUsageRequest().getClientUsage(usageVersion.get());

            if (clientUsage.getVersion() > usageVersion.get()) {
                clientUsage.getUsageDTOS().forEach(this::addItemToUsageList);
                this.usageVersion.set(clientUsage.getVersion());
                sortObservableList(sortChoice.getText());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void initializeRunningList() {
        currentlyRunningListView.setItems(currentlyRunningList);
        ObjectProperty<Callback<ListView<CurrentlyRunning>, ListCell<CurrentlyRunning>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new CurrentlyRunningCellFactory());
        currentlyRunningListView.cellFactoryProperty().bind(cellFactoryProperty);
    }

    private void initializeRunningTask() {
        ScheduledExecutorService currentlyRunningTasker = Executors.newSingleThreadScheduledExecutor();
        currentlyRunningTasker.scheduleAtFixedRate(() -> {
            if (!currentlySelectedClient.get().equals("")) {
                CurrentlyRunningResponseDTO currentlyRunning = new CurrentlyRunningRequest()
                        .getCurrentlyRunning(currentlySelectedClient.get());
                addItemsToRunningList(currentlyRunning);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void addItemsToRunningList(CurrentlyRunningResponseDTO responseDTO) {
        ObservableList<CurrentlyRunning> filteredList = FXCollections.observableArrayList();
        responseDTO.getCurrentlyRunning().forEach(currentlyRunningDTO -> {
            CurrentlyRunning newItem = new CurrentlyRunning(currentlyRunningDTO);
            filteredList.add(newItem);
        });

        Platform.runLater(() -> currentlyRunningList.setAll(filteredList));
    }

    private void bindAll() {
        this.poolSizeField.textProperty().bind(poolSizeProperty);
        this.errorLabel.textProperty().bind(errorLabelProperty);
        this.sizeLabel.textProperty().bind(sizeProperty);
    }

    private void initializeTextField() {
        this.poolSizeField.setOnKeyPressed(this::updatePoolSize);
    }

    private void initializeSortItems() {
        for (SortChoices choice : SortChoices.values()) {
            MenuItem menuItem = new MenuItem(choice.getValue());

            menuItem.setOnAction(event -> {
                sortChoice.setText(choice.getValue());
                sortObservableList(choice.getValue());
            });

            sortChoice.getItems().add(menuItem);
        }
    }

    private void addItemToUsageList(ClientUsageDTO usageItem) {
        boolean foundClient = clientUsageList.stream()
                .anyMatch(clientItem -> clientItem.clientNameProperty().get().equals(usageItem.getClientName()));

        if (!foundClient) {
            ClientUsage newItem = new ClientUsage(usageItem.getClientName(), usageItem.getNumOfWaiting().toString(),
                    usageItem.getNumOfRunning().toString(), usageItem.getNumOfFinished().toString());
            Platform.runLater(() -> clientUsageList.add(newItem));
        } else {
            clientUsageList.stream()
                    .filter(clientUsage -> clientUsage.clientNameProperty().get().equals(usageItem.getClientName()))
                    .findFirst().ifPresent(clientUsage -> {
                        if (!clientUsage.runningThreadsProperty().get()
                                .equals(usageItem.getNumOfRunning().toString())) {
                            Platform.runLater(() -> clientUsage.runningThreadsProperty().set(usageItem.getNumOfRunning().toString()));
                        }

                        if (!clientUsage.waitingThreadsProperty().get()
                                .equals(usageItem.getNumOfWaiting().toString())) {
                            Platform.runLater(() -> clientUsage.waitingThreadsProperty().set(usageItem.getNumOfWaiting().toString()));
                        }

                        if (!clientUsage.finishedThreadsProperty().get()
                                .equals(usageItem.getNumOfFinished().toString())) {
                            Platform.runLater(() -> clientUsage.finishedThreadsProperty().set(usageItem.getNumOfFinished().toString()));
                        }
                    });
        }
    }

    private void updatePoolSize(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleEnter();
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            handleBackSpace();
        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            errorLabelProperty.set("");
            poolSizeField.positionCaret(poolSizeField.getCaretPosition());
        } else if (event.getCode().isDigitKey()) {
            errorLabelProperty.set("");

            if (poolSizeProperty.get().equals("0")) {
                poolSizeProperty.set("");
            }

            String appender = poolSizeProperty.get() + event.getText();
            poolSizeProperty.set(appender);
            poolSizeField.positionCaret(poolSizeProperty.get().length());
        } else {
            errorLabelProperty.set("Please enter digits only!");
            errorLabel.setStyle("-fx-text-fill: red");
        }
    }

    private void handleEnter() {
        int newPoolSize = Integer.parseInt(poolSizeProperty.get());
        boolean isPositive = newPoolSize >= 0;

        if (!isPositive) {
            errorLabelProperty.set("Only positive numbers!");
            errorLabel.setStyle("-fx-text-fill: red");
        } else {
            SetThreadSizeResponseDTO responseDTO = new SetThreadPoolSizeRequest().setThreadPoolSize(
                    Integer.toString(newPoolSize));

            errorLabel.setOpacity(1D);
            errorLabelProperty.set(responseDTO.getMessage());

            if (responseDTO.isSuccessfullySet()) {
                errorLabel.setStyle("-fx-text-fill: green");
                this.sizeProperty.set(Integer.toString(newPoolSize));
            } else {
                errorLabel.setStyle("-fx-text-fill: red");
            }

            handleAnimation();
        }

        this.poolSizeProperty.set("0");
    }

    private void handleAnimation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), errorLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    private void handleBackSpace() {
        errorLabelProperty.set("");
        int caretPosition = poolSizeField.getCaretPosition();
        String currentPopulation = poolSizeProperty.get();

        if (!currentPopulation.isEmpty() && caretPosition > 0) {
            String updatedInput = currentPopulation.substring(0, caretPosition - 1)
                    + currentPopulation.substring(caretPosition);

            updatedInput = updatedInput.isEmpty() ? "0" : updatedInput;
            this.poolSizeProperty.set(updatedInput);
            poolSizeField.positionCaret(caretPosition - 1);
        }
    }

    private void sortObservableList(String sortBy) {
        switch (sortBy) {
            case "A-B":
                this.clientUsageList.sort(Comparator.comparing(object -> object.clientNameProperty().get()));
                break;
            case "Running":
                this.clientUsageList.sort(Comparator.comparing(object -> object.runningThreadsProperty().get()));
                break;
            case "Waiting":
                this.clientUsageList.sort(Comparator.comparing(object -> object.waitingThreadsProperty().get()));
                break;
            case "Finished":
                this.clientUsageList.sort(Comparator.comparing(object -> object.finishedThreadsProperty().get()));
                break;
            default:
                break;
        }
    }

    public void setCurrentlySelectedClient(String client) {
        this.currentlySelectedClient.set(client);
    }

    public void unFillAllClients() {
        this.clientUsageList.forEach(ClientUsage::unFillCircle);
    }
}
