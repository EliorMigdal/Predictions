package predictions.Controllers.Mutual.results.bottomscreen.controller;

import firewall.FireWallService;
import firewall.request.UIRequest;
import firewall.request.exception.LoadSimulationError;
import firewall.request.exception.RequestException;
import firewall.request.type.result.PopulationHistogramRequest;
import firewall.request.type.result.PropertyAverageRequest;
import firewall.request.type.result.PropertyConsistencyRequest;
import firewall.request.type.result.PropertyHistogramRequest;
import firewall.request.type.treeview.EntitiesNamesRequest;
import firewall.request.type.treeview.EntityPropertiesListRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import predictions.Controllers.Mutual.results.bottomscreen.controller.task.UpdateLineChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class SimulationEndedResultsController implements Initializable {
    @FXML private MenuButton entityNameDropDown;
    @FXML private Label averageLabel;
    @FXML private Label averageValue;
    @FXML private BarChart<Object, Object> barChart;
    @FXML private Label consistencyLabel;
    @FXML private Label consistencyValue;
    @FXML private LineChart<Object, Object> populationChart;
    @FXML private MenuButton propertyNameDropDown;

    private FireWallService firewall;
    private Integer simulationID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearDropdowns();
        initializeLabels();


        this.firewall = (FireWallService) resources.getObject("fireWall");
        this.simulationID = (Integer) resources.getObject("simulationID");
        showEndedResults();
    }
    public void clearBarChart(){
        this.barChart.getData().clear();
        entityNameDropDown.setText("Entities");
        propertyNameDropDown.setText("Properties");
    }
    public void clearDropdowns() {
        entityNameDropDown.getItems().clear();
        propertyNameDropDown.getItems().clear();
        propertyNameDropDown.setDisable(true);
    }

    public void initializeLabels() {
        setLabelOpacity(
                0,consistencyLabel, consistencyValue, averageLabel, averageValue);
    }

    private void setLabelOpacity(Integer value,Label... labels) {
        for (Label label : labels) {
            label.setOpacity(value);
        }
    }
    public void updatePopulationChart() {
        new Thread(new UpdateLineChart(firewall, populationChart, simulationID)).start();
    }

    public void showEndedResults() {
        try {
            initializePopulationChart();
            initializeEntityDropdown();
        } catch (LoadSimulationError | RequestException | LoadException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializePopulationChart()
            throws LoadSimulationError, RequestException, LoadException {
        UIRequest generatePopulationGraph = new PopulationHistogramRequest(simulationID);
        Map<String, Map<Integer, Integer>> dataMap = extractPopulationData(generatePopulationGraph);

        if (dataMap != null && !dataMap.isEmpty()) {
            populatePopulationChart(dataMap);
        }
    }

    private Map<String, Map<Integer, Integer>> extractPopulationData(UIRequest request)
            throws LoadSimulationError, RequestException, LoadException {
        Object requestResponse = firewall.handleRequest(request);
        return (requestResponse instanceof Map) ? (Map<String, Map<Integer, Integer>>) requestResponse : null;
    }

    private void populatePopulationChart(Map<String, Map<Integer, Integer>> dataMap) {
        for (String entityName : dataMap.keySet()) {
            XYChart.Series<Object, Object> series = createPopulationSeries(entityName, dataMap.get(entityName));
            populationChart.getData().add(series);
        }
        populationChart.getXAxis().setTickLabelGap(20);
    }

    private XYChart.Series<Object, Object> createPopulationSeries(String entityName, Map<Integer, Integer> data) {
        XYChart.Series<Object, Object> series = new XYChart.Series<>();
        series.setName(entityName);

        int partition = data.size() / 10, counter = 0;

        for (Integer tick : data.keySet()) {
            if (partition < 10 || counter % partition == 0 || counter == data.size() - 1) {
                Integer count = data.get(tick);
                series.getData().add(new XYChart.Data<>(tick.toString(), count));
            }
            counter++;
        }
        return series;
    }

    private void initializeEntityDropdown()
            throws LoadSimulationError, RequestException, LoadException {
        populateEntityDropdown(Boolean.FALSE);
    }

    public void populateEntityDropdown(Boolean stepForward)
            throws LoadSimulationError, RequestException, LoadException {
        UIRequest getEntityNames = new EntitiesNamesRequest();
        Object requestResult = firewall.handleRequest(getEntityNames);
        if(stepForward){

            entityNameDropDown.getItems().clear();
        }

        if (requestResult instanceof ArrayList) {
            ArrayList<String> entityNames = (ArrayList<String>) requestResult;
            for (String entityName : entityNames) {
                MenuItem entityButton = createEntityMenuItem(entityName,stepForward);
                entityNameDropDown.getItems().add(entityButton);
            }
        }
    }

    private MenuItem createEntityMenuItem(String entityName,Boolean stepForward) {
        MenuItem entityButton = new MenuItem(entityName);
        entityButton.setOnAction(event -> handleEntitySelection(entityName,stepForward));
        return entityButton;
    }

    private void handleEntitySelection(String entityName,Boolean stepForward) {
        barChart.setAnimated(false);
        barChart.getData().clear();
        entityNameDropDown.setText(entityName);
        propertyNameDropDown.setDisable(false);
        setLabelOpacity(
                0,consistencyLabel, consistencyValue, averageLabel, averageValue);

        populatePropertyDropdown(entityName,stepForward);
    }

    private void populatePropertyDropdown(String entityName,Boolean stepForward) {
        UIRequest getEntityProperties = new EntityPropertiesListRequest(entityName);

        try {
            Object entityProperties = firewall.handleRequest(getEntityProperties);

            if (entityProperties instanceof ArrayList) {
                ArrayList<String> entityPropertiesNames = (ArrayList<String>) entityProperties;
                if(stepForward) {
                    propertyNameDropDown.getItems().clear();
                }
                propertyNameDropDown.setText("Properties");

                for (String propertyName : entityPropertiesNames) {

                    MenuItem propertyButton = createPropertyMenuItem(entityName, propertyName,stepForward);
                    propertyNameDropDown.getItems().add(propertyButton);
                }
            }
        } catch (RequestException | LoadSimulationError | LoadException exception) {
            throw new RuntimeException(exception);
        }
    }

    private MenuItem createPropertyMenuItem(String entityName, String propertyName,Boolean stepForward) {
        MenuItem propertyButton = new MenuItem(propertyName);
        propertyButton.setOnAction(event -> handlePropertySelection(entityName, propertyName,stepForward));
        return propertyButton;
    }

    private void handlePropertySelection(String entityName, String propertyName,Boolean stepForward) {
        barChart.setAnimated(false);
        barChart.getData().clear();
        setLabelOpacity(
                1,averageLabel, averageValue,consistencyLabel,consistencyValue);

        UIRequest generatePropertyHistogram = new PropertyHistogramRequest(simulationID, entityName, propertyName);
        propertyNameDropDown.setText(propertyName);

        try {
            Object histogramResult = firewall.handleRequest(generatePropertyHistogram);

            if (histogramResult instanceof Map) {
                if (((Map<Object, Integer>) histogramResult).size() > 0)
                populatePropertyChart((Map<Object, Integer>) histogramResult,stepForward);
            }
        } catch (RequestException | LoadSimulationError | LoadException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void populatePropertyChart(Map<Object, Integer> data,Boolean stepForward)
            throws LoadSimulationError, RequestException, LoadException {
        XYChart.Series<Object, Object> series = new XYChart.Series<>();
        if(stepForward){
            barChart.getData().clear();
        }
        for (Map.Entry<Object, Integer> entry : data.entrySet()) {
            String category = entry.getKey().toString();
            Integer count = entry.getValue();

            series.getData().add(new XYChart.Data<>(category, count));
        }

        if (data.keySet().iterator().next() instanceof Integer) {
            setAverageValue(entityNameDropDown.getText(), propertyNameDropDown.getText());
        } else {
            averageValue.setText("");
            averageLabel.setText("");
        }

        setConsistencyValue(entityNameDropDown.getText(), propertyNameDropDown.getText());

        barChart.getData().add(series);
    }

    private void setAverageValue(String entityName, String propertyName)
            throws LoadSimulationError, RequestException, LoadException {
        UIRequest propertyAverage = new PropertyAverageRequest(simulationID, entityName, propertyName);
        averageValue.setText(firewall.handleRequest(propertyAverage).toString());
    }

    private void setConsistencyValue(String entityName, String propertyName)
            throws LoadSimulationError, RequestException, LoadException {
        UIRequest propertyConsistency = new PropertyConsistencyRequest(simulationID, entityName, propertyName);
        Object requestResult = firewall.handleRequest(propertyConsistency);
        consistencyValue.setText(requestResult.toString());
    }

}