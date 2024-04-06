package predictions.views.Factories;

import predictions.controllers.admin.allocations.AllocationsController;
import predictions.controllers.admin.executions.ExecutionHistoryController;
import predictions.controllers.admin.management.ManagementController;
import predictions.controllers.admin.threadmanagment.ThreadManagementController;
import predictions.controllers.client.ClientMenuController;
import predictions.controllers.client.DashboardController;
import predictions.controllers.client.details.SimulationDetailsController;
import predictions.controllers.client.executions.ExecutionsController;
import predictions.controllers.client.initialize.InitializationController;
import predictions.controllers.client.requests.RequestsController;

public class ControllerFactory {
    // Client views
    private ClientMenuController clientMenuController;
    private DashboardController dashboardController;
    private SimulationDetailsController simulationDetailsController;
    private RequestsController requestsController;
    private InitializationController initializationController;
    private ExecutionsController executionsController;
    // Admin views
    private ManagementController managementController;
    private AllocationsController allocationsController;
    private ExecutionHistoryController executionHistoryController;
    private ThreadManagementController threadManagementController;

    //Client Controllers setters
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setSimulationDetailsController(SimulationDetailsController simulationDetailsController) {
        this.simulationDetailsController = simulationDetailsController;
    }

    public void setRequestsController(RequestsController requestsController) {
        this.requestsController = requestsController;
    }

    public void setExecutionController(InitializationController initializationController) {
        this.initializationController = initializationController;
    }

    public void setExecutionsController(ExecutionsController executionsController) {
        this.executionsController = executionsController;
    }

    public DashboardController getDashboardController() {
        return dashboardController;
    }

    public SimulationDetailsController getSimulationDetailsController() {
        return simulationDetailsController;
    }

    public RequestsController getRequestsController() {
        return requestsController;
    }

    public InitializationController getExecutionController() {
        return initializationController;
    }

    public ExecutionsController getExecutionsController() {
        return executionsController;
    }

    //Admin Controllers setters


    public void setManagementController(ManagementController managementController) {
        this.managementController = managementController;
    }

    public void setAllocationsController(AllocationsController allocationsController) {
        this.allocationsController = allocationsController;
    }

    public void setExecutionHistoryController(ExecutionHistoryController executionHistoryController) {
        this.executionHistoryController = executionHistoryController;
    }

    public void setThreadManagementController(ThreadManagementController threadManagementController) {
        this.threadManagementController = threadManagementController;
    }

    public ManagementController getManagementController() {
        return managementController;
    }

    public AllocationsController getAllocationsController() {
        return allocationsController;
    }

    public ExecutionHistoryController getExecutionHistoryController() {
        return executionHistoryController;
    }

    public ThreadManagementController getThreadManagementController() {
        return threadManagementController;
    }

    public ClientMenuController getClientMenuController() {
        return clientMenuController;
    }

    public void setClientMenuController(ClientMenuController clientMenuController) {
        this.clientMenuController = clientMenuController;
    }
}
