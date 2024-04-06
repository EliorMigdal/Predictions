package predictions.controllers.client.requests;

import dto.request.client.requests.TerminationInfoDTO;
import dto.response.client.requests.RequestDTO;
import dto.response.client.requests.RequestsListDTO;
import dto.response.mutual.simulations.SimulationsListResponseDTO;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import predictions.controllers.client.requests.items.ClientRequest;
import predictions.controllers.client.requests.items.ClientRequestsCellFactory;
import predictions.controllers.client.requests.items.SimulationItem;
import predictions.httpclient.client.requests.GetRequestsList;
import predictions.httpclient.client.requests.SubmitRequest;
import predictions.httpclient.mutual.simulations.SimulationsListRequest;
import predictions.models.Model;
import predictions.views.Enums.SortBy;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RequestsController implements Initializable {
    @FXML private ChoiceBox<SimulationItem> simulations_choiceBox;
    @FXML private TextField amount_fld;
    @FXML private CheckBox time_chbox;
    @FXML private TextField time_term_fld;
    @FXML private CheckBox ticks_chbox;
    @FXML private TextField ticks_term_fld;
    @FXML private CheckBox user_chbox;
    @FXML private ListView<ClientRequest> requests_listview;
    @FXML private ChoiceBox<SortBy> sortby_choicebox;

    private final ListProperty<SimulationItem> simulationItemListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObservableList<ClientRequest> clientRequestObservableList = FXCollections.observableArrayList();

    private final SimpleStringProperty amountProperty = new SimpleStringProperty("0");
    private final SimpleStringProperty timeProperty = new SimpleStringProperty("0");
    private final SimpleStringProperty ticksProperty = new SimpleStringProperty("0");
    private final String clientName = Model.getInstance().getCurrConnectedEntity();
    private final SimpleIntegerProperty simulationsVersion = new SimpleIntegerProperty(0);

    public RequestsController() {
        Model.getInstance().getControllerFactory().setRequestsController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
        sortby_choicebox.setItems(FXCollections.observableArrayList(SortBy.values()));
        initializeRequestsList();
        initializeRequestsTask();
        simulations_choiceBox.setItems(simulationItemListProperty);
        simulations_choiceBox.setConverter(getConverter());
        simulations_choiceBox.itemsProperty().bind(simulationItemListProperty);
        setEvents();
    }

    private void bindAll() {
        this.amount_fld.textProperty().bind(amountProperty);
        this.time_term_fld.textProperty().bind(timeProperty);
        this.ticks_term_fld.textProperty().bind(ticksProperty);
    }

    private void initializeRequestsList() {
        requests_listview.setItems(clientRequestObservableList);
        ObjectProperty<Callback<ListView<ClientRequest>, ListCell<ClientRequest>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new ClientRequestsCellFactory());
        requests_listview.cellFactoryProperty().bind(cellFactoryProperty);
    }

    private void initializeRequestsTask() {
        ScheduledExecutorService requestsTask = Executors.newSingleThreadScheduledExecutor();
        requestsTask.scheduleAtFixedRate(() -> {
            RequestsListDTO requestsList = new GetRequestsList().getRequestsList(clientName);
            requestsList.getRequests().forEach(this::addNewRequest);
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void addNewRequest(RequestDTO newRequest) {
        Optional<ClientRequest> matchingRequest = this.clientRequestObservableList.stream()
                .filter(request -> request.requestIDProperty().get().equals(newRequest.getRequestID()))
                .findFirst();

        if (matchingRequest.isPresent()) {
            if (!matchingRequest.get().total_RemainingProperty().get().equals(newRequest.getTotal_Remaining())) {
                Platform.runLater(() -> matchingRequest.get().total_RemainingProperty().set(newRequest.getTotal_Remaining()));
            }

            if (!matchingRequest.get().reqStatusProperty().get().equals(newRequest.getReqStatus())) {
                Platform.runLater(() -> matchingRequest.get().reqStatusProperty().set(newRequest.getReqStatus()));
            }
        } else {
            ClientRequest newItem = new ClientRequest(newRequest.getRequestID(), newRequest.getSimulationName(),
                    newRequest.getTotal_Remaining(), newRequest.getDate(), newRequest.getTerminationInfoDTO(),
                    newRequest.getReqStatus());
            Platform.runLater(() -> this.clientRequestObservableList.add(newItem));
        }
    }

    public void setAmountProperty(String amountProperty) {
        this.amountProperty.set(amountProperty);
    }

    public int getAmountProperty() {
        return Integer.parseInt(amountProperty.get());
    }

    public void setTimeProperty(String timeProperty) {
        this.timeProperty.set(timeProperty);
    }

    public void setTicksProperty(String ticksProperty) {
        this.ticksProperty.set(ticksProperty);
    }

    public int getTicksProperty() {
        return Integer.parseInt(ticksProperty.get());
    }

    public int getTimeProperty() {
        return Integer.parseInt(timeProperty.get());
    }

    @FXML
    void CheckAmountOnChange(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            boolean isNaturalPositive = Integer.parseInt(amount_fld.getText()) > 0;

            if (!isNaturalPositive) {
                amount_fld.setStyle("-fx-border-color: red");
            } else {
                amount_fld.setStyle("-fx-border-color: green");
            }
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            int caretPosition = amount_fld.getCaretPosition();
            String currentAmount = amountProperty.get();

            if (!currentAmount.isEmpty() && caretPosition > 0) {
                String updatedAmount = currentAmount.substring(0, caretPosition - 1)
                        + currentAmount.substring(caretPosition);

                updatedAmount = updatedAmount.isEmpty() ? "0" : updatedAmount;
                setAmountProperty(updatedAmount);
                amount_fld.positionCaret(caretPosition - 1);
            }
        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            amount_fld.positionCaret(amount_fld.getCaretPosition());
        } else if (event.getCode().isDigitKey()) {

            if (getAmountProperty() == 0) {
                setAmountProperty("");
            }

            String appender = amountProperty.get() + event.getText();
            setAmountProperty(appender);
            amount_fld.positionCaret(amountProperty.get().length());
        } else if (event.getCode().isLetterKey()) {
            amount_fld.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void CheckTicksTermOnChange(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            boolean isNaturalPositive = Integer.parseInt(ticks_term_fld.getText()) > 0;

            if (!isNaturalPositive) {
                ticks_term_fld.setStyle("-fx-border-color: red");
            } else {
                ticks_term_fld.setStyle("-fx-border-color: green");
            }
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            int caretPosition = ticks_term_fld.getCaretPosition();
            String currentAmount = ticksProperty.get();

            if (!currentAmount.isEmpty() && caretPosition > 0) {
                String updatedAmount = currentAmount.substring(0, caretPosition - 1)
                        + currentAmount.substring(caretPosition);

                updatedAmount = updatedAmount.isEmpty() ? "0" : updatedAmount;
                setTicksProperty(updatedAmount);
                ticks_term_fld.positionCaret(caretPosition - 1);
            }
        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            ticks_term_fld.positionCaret(ticks_term_fld.getCaretPosition());
        } else if (event.getCode().isDigitKey()) {

            if (getTicksProperty() == 0) {
                setTicksProperty("");
            }

            String appender = ticksProperty.get() + event.getText();
            setTicksProperty(appender);
            ticks_term_fld.positionCaret(ticksProperty.get().length());
        } else if (event.getCode().isLetterKey()) {
            ticks_term_fld.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void CheckTimeTermOnChange(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            boolean isNaturalPositive = Integer.parseInt(time_term_fld.getText()) > 0;

            if (!isNaturalPositive) {
                time_term_fld.setStyle("-fx-border-color: red");
            } else {
                time_term_fld.setStyle("-fx-border-color: green");
            }
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            int caretPosition = time_term_fld.getCaretPosition();
            String currentAmount = timeProperty.get();

            if (!currentAmount.isEmpty() && caretPosition > 0) {
                String updatedAmount = currentAmount.substring(0, caretPosition - 1)
                        + currentAmount.substring(caretPosition);

                updatedAmount = updatedAmount.isEmpty() ? "0" : updatedAmount;
                setTimeProperty(updatedAmount);
                time_term_fld.positionCaret(caretPosition - 1);
            }
        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            time_term_fld.positionCaret(time_term_fld.getCaretPosition());
        } else if (event.getCode().isDigitKey()) {

            if (getTimeProperty() == 0) {
                setTimeProperty("");
            }

            String appender = timeProperty.get() + event.getText();
            setTimeProperty(appender);
            time_term_fld.positionCaret(timeProperty.get().length());
        } else if (event.getCode().isLetterKey()) {
            time_term_fld.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void setOnMouseClicked() {
        int clickPosition = this.amount_fld.getCaretPosition();
        this.amount_fld.positionCaret(clickPosition);
    }

    @FXML
    void OnMouseClickTicks() {
        int clickPosition = this.ticks_term_fld.getCaretPosition();
        this.ticks_term_fld.positionCaret(clickPosition);
    }

    @FXML
    void onMouseClickTime() {
        int clickPosition = this.time_term_fld.getCaretPosition();
        this.time_term_fld.positionCaret(clickPosition);
    }

    private void submitFormChecker(){
        CheckAmountOnChange(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER,
                false, false, false, false));
        if (time_chbox.isSelected()) {
            CheckTimeTermOnChange(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER,
                    false, false, false, false));
        }
        if (ticks_chbox.isSelected()) {
            CheckTicksTermOnChange(new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
                    KeyCode.ENTER, false, false, false, false));
        }
    }

    @FXML private void sendRequest() {
        submitFormChecker();
        if (time_term_fld.getStyle().contains("-fx-border-color: red") || time_term_fld.getStyle().contains("-fx-border-color: red")
        || amount_fld.getStyle().contains("-fx-border-color: red")) {
        }
        else {
            String clientName = Model.getInstance().getCurrConnectedEntity();
            String simulationName = simulations_choiceBox.getValue().getName();
            String numOfExecutions = amountProperty.get();
            ArrayList<TerminationInfoDTO> terminationInfoDTOArrayList = new ArrayList<>();
            if (timeTermChecker()) {
                terminationInfoDTOArrayList.add(new TerminationInfoDTO("By Time", timeProperty.get()));
            }
            if (ticksTermChecker()) {
                terminationInfoDTOArrayList.add(new TerminationInfoDTO("By Ticks", ticksProperty.get()));
            }

            new SubmitRequest().submitNewRequest(clientName, simulationName,
                    Integer.parseInt(numOfExecutions), terminationInfoDTOArrayList);
            resetForm();
        }
    }

    private void resetForm(){
        amountProperty.set("0");
        amount_fld.setStyle(null);
        timeProperty.set("0");
        time_term_fld.setStyle(null);
        ticksProperty.set("0");
        ticks_term_fld.setStyle(null);

        time_chbox.setSelected(false);
        ticks_chbox.setSelected(false);
        user_chbox.setSelected(false);
        user_chbox.setDisable(false);
        time_term_fld.setDisable(true);
        ticks_term_fld.setDisable(true);

    }

    private boolean timeTermChecker(){
        return (time_chbox.isSelected() && !timeProperty.get().isEmpty() &&
                time_term_fld.getStyle().contains("-fx-border-color: green"));
    }

    private boolean ticksTermChecker(){
        return (ticks_chbox.isSelected() && !ticksProperty.get().isEmpty() &&
                ticks_term_fld.getStyle().contains("-fx-border-color: green"));
    }

    @FXML private void updateSimulationNameList() {
        SimulationsListResponseDTO simulationsList = new SimulationsListRequest()
                .getSimulationsList(simulationsVersion.get());

        if (simulationsList.getVersion() > this.simulationsVersion.get()) {
            simulationsList.getSimulationsList().forEach(simulation ->
                    simulationItemListProperty.add(new SimulationItem(simulation.getSimulationName())));
            this.simulationsVersion.set(simulationsList.getVersion());
        }
    }

    private StringConverter<SimulationItem> getConverter() {
         final StringConverter<SimulationItem> simulationItemStringConverter;
         simulationItemStringConverter = new StringConverter<SimulationItem>() {
            @Override
            public String toString(SimulationItem object) {
                return object.getName();
            }

            @Override
            public SimulationItem fromString(String string) {
                return null;
            }
        };
        return simulationItemStringConverter;
    }

    private void setEvents(){
        ticks_chbox.setOnAction(event -> {
            ticks_term_fld.clear();
            ticks_term_fld.setDisable(!ticks_chbox.isSelected());
            user_chbox.setDisable(time_chbox.isSelected() || ticks_chbox.isSelected());
        });
        time_chbox.setOnAction(event -> {
            time_term_fld.clear();
            time_term_fld.setDisable(!time_chbox.isSelected());
            user_chbox.setDisable(time_chbox.isSelected() || ticks_chbox.isSelected());
        });
        user_chbox.setOnAction(event -> {
            if(user_chbox.isSelected()) {
                time_chbox.setSelected(!user_chbox.isSelected());
                ticks_chbox.setSelected(!user_chbox.isSelected());
                time_term_fld.setDisable(user_chbox.isSelected());
                ticks_term_fld.setDisable(user_chbox.isSelected());
            }
        });
    }
}
