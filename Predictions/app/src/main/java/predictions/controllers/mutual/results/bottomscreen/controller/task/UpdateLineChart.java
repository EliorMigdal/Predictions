package predictions.Controllers.Mutual.results.bottomscreen.controller.task;

import firewall.FireWallService;
import firewall.request.UIRequest;
import firewall.request.exception.LoadSimulationError;
import firewall.request.exception.RequestException;
import firewall.request.type.result.PopulationHistogramRequest;
import firewall.utility.exception.LoadException;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Map;

public class UpdateLineChart implements Runnable {
    private final FireWallService firewall;
    private final LineChart<Object, Object> populationChart;
    private final Integer simulationID;

    public UpdateLineChart(FireWallService firewall, LineChart<Object, Object> populationChart, Integer simulationID) {
        this.firewall = firewall;
        this.populationChart = populationChart;
        this.simulationID = simulationID;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300);
                UIRequest generatePopulationGraph = new PopulationHistogramRequest(simulationID);
                Object requestResponse = firewall.handleRequest(generatePopulationGraph);
                Map<String, Map<Integer, Integer>> dataMap = null;
                boolean updateNeeded = false;

                if (requestResponse instanceof Map) {
                    dataMap = (Map<String, Map<Integer, Integer>>) requestResponse;
                }

                if (dataMap != null) {
                    double maxXValue = Double.NEGATIVE_INFINITY;

                    for (XYChart.Series<Object, Object> series : populationChart.getData()) {
                        for (XYChart.Data<Object, Object> data : series.getData()) {
                            Integer parsed = Integer.parseInt(data.getXValue().toString());
                            if (parsed > maxXValue) {
                                maxXValue = parsed.doubleValue();
                            }
                        }
                    }

                    ArrayList<XYChart.Series<Object, Object>> seriesArrayList = new ArrayList<>();

                    for (String entityName : dataMap.keySet()) {
                        if (dataMap.get(entityName).size() > maxXValue) {
                            updateNeeded = true;
                            XYChart.Series<Object, Object> series = new XYChart.Series<>();
                            series.setName(entityName);
                            int partition = dataMap.get(entityName).size() / 10, counter = 0;
                            for (Integer tick : dataMap.get(entityName).keySet()) {
                                if (partition < 10 || counter % partition == 0
                                        || counter == dataMap.get(entityName).size() - 1) {
                                    Integer count = dataMap.get(entityName).get(tick);
                                    series.getData().add(new XYChart.Data<>(tick.toString(), count));
                                }
                                counter++;
                            }
                            seriesArrayList.add(series);
                        }
                    }

                    if (updateNeeded) {
                        Platform.runLater(() -> {
                            populationChart.setAnimated(false);
                            populationChart.getData().clear();
                            populationChart.getData().addAll(seriesArrayList);
                        });
                    }

                }
            } catch (RequestException | LoadSimulationError | LoadException | InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private Map<String, Map<Integer, Integer>> extractPopulationData(UIRequest request)
            throws LoadSimulationError, RequestException, LoadException {
        Object requestResponse = firewall.handleRequest(request);
        return (requestResponse instanceof Map) ? (Map<String, Map<Integer, Integer>>) requestResponse : null;
    }

    private void populatePopulationChart(Map<String, Map<Integer, Integer>> dataMap) {
        Platform.runLater(
                () ->{
                    for (String entityName : dataMap.keySet()) {
                        XYChart.Series<Object, Object> series = createPopulationSeries(entityName, dataMap.get(entityName));
                        populationChart.getData().add(series);
                    }
                    populationChart.getXAxis().setTickLabelGap(20);
                }
        );

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
}