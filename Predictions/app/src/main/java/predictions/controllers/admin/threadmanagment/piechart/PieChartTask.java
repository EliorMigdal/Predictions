package predictions.controllers.admin.threadmanagment.piechart;

import dto.response.admin.activity.ThreadPoolStatusResponseDTO;
import javafx.application.Platform;
import javafx.scene.chart.PieChart;
import predictions.httpclient.admin.activity.ThreadPoolStatusRequest;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class PieChartTask implements Runnable {
    private final PieChart pieChart;

    public PieChartTask(PieChart pieChart) {
        this.pieChart = pieChart;
        initializePieChart();
    }

    @Override
    public void run() {
        AtomicReference<Integer> latestActiveValue = new AtomicReference<>(0);
        AtomicReference<Integer> latestQueueValue = new AtomicReference<>(0);
        AtomicReference<Integer> latestFinishedValue = new AtomicReference<>(0);

        ThreadPoolStatusResponseDTO poolStatus = new ThreadPoolStatusRequest().getThreadPoolStatus();

        if (!Objects.equals(latestQueueValue.get().toString(), poolStatus.getQueueSize())) {
            pieChart.getData().stream().filter(pieData -> pieData.getName().startsWith("Queued"))
                    .findFirst().ifPresent(pieData -> Platform.runLater(() -> {
                        pieData.setPieValue(Integer.parseInt(poolStatus.getQueueSize()));
                        latestQueueValue.set(Integer.parseInt(poolStatus.getQueueSize()));
                        pieData.setName("Queued: " + poolStatus.getQueueSize());
                    }));
        }

        if (!Objects.equals(latestActiveValue.get().toString(), poolStatus.getCurrentlyRunning())) {
            pieChart.getData().stream().filter(pieData -> pieData.getName().startsWith("Active"))
                    .findFirst().ifPresent(pieData -> Platform.runLater(() -> {
                        pieData.setPieValue(Integer.parseInt(poolStatus.getCurrentlyRunning()));
                        latestActiveValue.set(Integer.parseInt(poolStatus.getCurrentlyRunning()));
                        pieData.setName("Active: " + poolStatus.getCurrentlyRunning());
                    }));
        }

        if (!Objects.equals(latestFinishedValue.get().toString(), poolStatus.getFinished())) {
            pieChart.getData().stream().filter(pieData -> pieData.getName().startsWith("Finished"))
                    .findFirst().ifPresent(pieData -> Platform.runLater(() -> {
                        pieData.setPieValue(Integer.parseInt(poolStatus.getFinished()));
                        latestFinishedValue.set(Integer.parseInt(poolStatus.getFinished()));
                        pieData.setName("Finished: " + poolStatus.getFinished());
                    }));
        }
    }

    public void initializePieChart() {
        pieChart.getStylesheets().add("predictions/controllers/admin/threadmanagment/piechart/css/pieChart.css");
        pieChart.getData().add(new PieChart.Data("Active: 0", 1));
        pieChart.getData().add(new PieChart.Data("Queued: 0", 1));
        pieChart.getData().add(new PieChart.Data("Finished: 0", 1));
    }
}
