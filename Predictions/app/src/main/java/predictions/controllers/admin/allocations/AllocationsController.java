package predictions.controllers.admin.allocations;

import dto.response.admin.allocations.RequestsInfoDTO;
import dto.response.admin.general.ClientNamesResponseDTO;
import dto.response.mutual.simulations.SimulationsListResponseDTO;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import predictions.controllers.admin.allocations.enums.AllocationsSortChoices;
import predictions.controllers.admin.allocations.enums.AllocationsStatusItems;
import predictions.controllers.admin.allocations.items.ClientRequest;
import predictions.controllers.admin.allocations.items.ClientRequestCellFactory;
import predictions.controllers.admin.allocations.items.ClientRequestController;
import predictions.httpclient.admin.allocations.ApproveRequest;
import predictions.httpclient.admin.allocations.DeclineRequest;
import predictions.httpclient.admin.allocations.GetRequestsInfo;
import predictions.httpclient.admin.general.ClientsNamesRequest;
import predictions.httpclient.mutual.simulations.SimulationsListRequest;
import predictions.models.Model;

import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AllocationsController implements Initializable {
    @FXML private MenuButton clientNamesMenuButton;
    @FXML private MenuButton simulationNameMenuButton;
    @FXML private MenuButton statusMenuButton;
    @FXML private MenuButton sortMenuButton;
    @FXML private Button approveButton;
    @FXML private Button declineButton;
    @FXML private Button filterButton;
    @FXML private ListView<ClientRequest> requestsListView;

    private final CopyOnWriteArrayList<ClientRequest> fullRequestList = new CopyOnWriteArrayList<>();
    private final ObservableList<ClientRequest> filteredRequests = FXCollections.observableArrayList();

    private final SimpleStringProperty selectedClient = new SimpleStringProperty("");
    private final SimpleStringProperty selectedRequestID = new SimpleStringProperty("");
    private final SimpleStringProperty clientChoice = new SimpleStringProperty("All");
    private final SimpleStringProperty simulationChoice = new SimpleStringProperty("All");
    private final SimpleStringProperty statusChoice = new SimpleStringProperty("All");
    private final SimpleIntegerProperty requestsVersion = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty namesVersion = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty simulationsVersion = new SimpleIntegerProperty(0);

    private ClientRequestController currentlySelectedItemController;

    public AllocationsController() {
        Model.getInstance().getControllerFactory().setAllocationsController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeClientsNames();
        initializeSimulationsNames();
        initializeStatusButtons();
        initializeSortOptions();
        initializeFilterButton();
        initializeApproveButton();
        initializeDeclineButton();
        updateRequestsList();
    }

    private void initializeClientsNames() {
        MenuItem allButton = new MenuItem("All");
        allButton.setOnAction(event -> setClientButtonText("All"));
        clientNamesMenuButton.getItems().add(allButton);
        initializeNamesTask();
    }

    private void initializeNamesTask() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            ClientNamesResponseDTO clientNames = new ClientsNamesRequest().getClientsNames(namesVersion.get());

            if (clientNames.getVersion() > namesVersion.get()) {
                clientNames.getClientNames().forEach(name -> {
                    MenuItem newItem = new MenuItem(name);
                    newItem.setOnAction(event -> setClientButtonText(name));
                    Platform.runLater(() -> clientNamesMenuButton.getItems().add(newItem));
                });

                namesVersion.set(clientNames.getVersion());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void initializeSimulationsNames() {
        MenuItem allButton = new MenuItem("All");
        allButton.setOnAction(event -> setSimulationButtonText("All"));
        simulationNameMenuButton.getItems().add(allButton);
        initializeSimulationsTask();
    }

    private void initializeSimulationsTask() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            SimulationsListResponseDTO simulationsList = new SimulationsListRequest()
                    .getSimulationsList(simulationsVersion.get());

            if (simulationsList.getVersion() > simulationsVersion.get()) {
                simulationsList.getSimulationsList().forEach(simulation -> {
                    MenuItem newItem = new MenuItem(simulation.getSimulationName());
                    newItem.setOnAction(event -> setSimulationButtonText(simulation.getSimulationName()));
                    Platform.runLater(() -> simulationNameMenuButton.getItems().add(newItem));
                });

                simulationsVersion.set(simulationsList.getVersion());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void initializeStatusButtons() {
        for (AllocationsStatusItems choice : AllocationsStatusItems.values()) {
            MenuItem menuItem = new MenuItem(choice.getValue());

            menuItem.setOnAction(event -> {
                statusMenuButton.setText(choice.getValue());
                statusChoice.set(choice.getValue());
            });

            statusMenuButton.getItems().add(menuItem);
        }
    }

    private void setClientButtonText(String buttonText) {
        Platform.runLater(() -> clientNamesMenuButton.setText(buttonText));
        clientChoice.set(buttonText);
    }

    private void setSimulationButtonText(String buttonText) {
        Platform.runLater(() -> simulationNameMenuButton.setText(buttonText));
        simulationChoice.set(buttonText);
    }

    private void initializeSortOptions() {
        for (AllocationsSortChoices choice : AllocationsSortChoices.values()) {
            MenuItem menuItem = new MenuItem(choice.getValue());

            menuItem.setOnAction(event -> {
                sortMenuButton.setText(choice.getValue());
                sortObservableList(choice.getValue());
            });

            sortMenuButton.getItems().add(menuItem);
        }
    }

    private void sortObservableList(String sortBy) {
        switch (sortBy) {
            case "Client Name":
                this.filteredRequests.sort(Comparator.comparing(request -> request.clientNameProperty().get()));
                break;
            case "Request ID":
                this.filteredRequests.sort(Comparator.comparing(request -> request.requestIDProperty().get()));
                break;
            case "Request date":
                this.filteredRequests.sort(Comparator.comparing(request -> request.dateProperty().get()));
                break;
            case "Simulation Name":
                this.filteredRequests.sort(Comparator.comparing(request -> request.simulationNameProperty().get()));
                break;
            case "Amount":
                this.filteredRequests.sort(Comparator.comparing(request -> Integer.parseInt(request.amountProperty().get())));
                break;
            case "Status":
                this.filteredRequests.sort(Comparator.comparing(request -> getStatusPriority(request.requestStatusProperty().get())));
                break;
            default:
                break;
        }
    }

    private void initializeFilterButton() {
        requestsListView.setItems(filteredRequests);
        ObjectProperty<Callback<ListView<ClientRequest>, ListCell<ClientRequest>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new ClientRequestCellFactory(this));
        requestsListView.cellFactoryProperty().bind(cellFactoryProperty);

        this.filterButton.setOnAction(event -> filterClientRequests());
    }

    private void filterClientRequests() {
        new Thread(() -> {
            ObservableList<ClientRequest> newFiltered = FXCollections.observableArrayList();
            newFiltered.addAll(fullRequestList.stream()
                    .filter(this::matchesCurrentFilter)
                    .collect(Collectors.toList()));

            Platform.runLater(() -> filteredRequests.setAll(newFiltered));
        }).start();
    }

    private void updateRequestsList() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            RequestsInfoDTO requestsInfo = new GetRequestsInfo().getRequestsInfo(requestsVersion.get());

            requestsInfo.getRequests().forEach(request -> {
                Optional<ClientRequest> sameRequest = fullRequestList.stream().filter(clientRequest ->
                        clientRequest.clientNameProperty().get().equals(request.getClientName())
                                && clientRequest.requestIDProperty().get().equals(request.getRequestID())).findFirst();

                if (sameRequest.isPresent()) {
                    if (!sameRequest.get().requestStatusProperty().get().equals(request.getRequestStatus())) {
                        Platform.runLater(() -> sameRequest.get().setRequestStatus(request.getRequestStatus()));
                    }
                } else {
                    ClientRequest newRequest = new ClientRequest(request);
                    Platform.runLater(() -> fullRequestList.add(newRequest));

                    if (matchesCurrentFilter(newRequest)) {
                        Platform.runLater(() -> {
                            filteredRequests.add(newRequest);
                            sortObservableList(sortMenuButton.getText());
                        });
                    }
                }
            });
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void initializeApproveButton() {
        approveButton.setOnAction(event -> {
            new ApproveRequest().approveRequest(selectedClient.get(), Integer.parseInt(selectedRequestID.get()));
            disableButtons();
            unFillAllRequests();
            filterClientRequests();
            currentlySelectedItemController.disableSelectionButton();
        });
    }

    private void initializeDeclineButton() {
        declineButton.setOnAction(event -> {
            new DeclineRequest().declineRequest(selectedClient.get(), Integer.parseInt(selectedRequestID.get()));
            disableButtons();
            unFillAllRequests();
            filterClientRequests();
            currentlySelectedItemController.disableSelectionButton();
        });
    }

    public void unFillAllRequests() {
        filteredRequests.forEach(ClientRequest::unFillCircle);
    }

    public void setSelectedClient(String selectedClient) {
        this.selectedClient.set(selectedClient);
    }

    public void setSelectedRequestID(String selectedRequestID) {
        this.selectedRequestID.set(selectedRequestID);
    }

    public void disableButtons() {
        declineButton.setDisable(true);
        approveButton.setDisable(true);
    }

    public void enableButtons() {
        declineButton.setDisable(false);
        approveButton.setDisable(false);
    }

    private boolean matchesCurrentFilter(ClientRequest request) {
        return ((request.clientNameProperty().get().equals(clientChoice.get()) || clientChoice.get().startsWith("All"))
        && (request.simulationNameProperty().get().equals(simulationChoice.get()) || simulationChoice.get().startsWith("All"))
        && (request.requestStatusProperty().get().equals(statusChoice.get()) || statusChoice.get().startsWith("All")));
    }

    private int getStatusPriority(String status) {
        switch (status) {
            case "Approved":
                return 1;
            case "Waiting":
                return 2;
            case "Declined":
                return 3;
            default:
                return -1;
        }
    }

    public void setCurrentlySelectedItemController(ClientRequestController currentlySelectedItemController) {
        this.currentlySelectedItemController = currentlySelectedItemController;
    }
}
