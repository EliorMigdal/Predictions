package predictions.controllers.admin.threadmanagment.items;

import javafx.beans.property.*;

public class ClientUsage {
    private final StringProperty clientName;
    private final StringProperty waitingThreads;
    private final StringProperty runningThreads;
    private final StringProperty finishedThreads;

    private ClientUsageCellController myController;

    public ClientUsage(String clientName, String waitingThreads, String runningThreads, String finishedThreads) {
        this.clientName = new SimpleStringProperty(this, "ClientName", clientName);
        this.waitingThreads = new SimpleStringProperty(this, "WaitingThreads", waitingThreads);
        this.runningThreads = new SimpleStringProperty(this, "RunningThreads", runningThreads);
        this.finishedThreads = new SimpleStringProperty(this, "FinishedThreads", finishedThreads);
    }

    public StringProperty clientNameProperty() { return this.clientName; }

    public StringProperty waitingThreadsProperty() { return this.waitingThreads; }

    public StringProperty runningThreadsProperty() { return this.runningThreads; }

    public StringProperty finishedThreadsProperty() { return this.finishedThreads; }

    public void setMyController(ClientUsageCellController myController) {
        this.myController = myController;
    }

    public void unFillCircle() {
        this.myController.unFillCircle();
    }
}
