package predictions.models;

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import predictions.models.logic.fxml.requests.handler.MainFXMLRequestHandler;
import predictions.views.Factories.ControllerFactory;
import predictions.views.Factories.ViewFactory;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final ControllerFactory controllerFactory;
    private final PoolingHttpClientConnectionManager httpClientManager;
    private final MainFXMLRequestHandler mainFXMLRequestHandler;
    private String currConnectedEntity;


    private Model() {
        this.viewFactory = new ViewFactory();
        this.httpClientManager = new PoolingHttpClientConnectionManager();
        this.mainFXMLRequestHandler = new MainFXMLRequestHandler();
        this.controllerFactory = new ControllerFactory();
    }

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    public void setCurrConnectedEntity(String currConnectedEntity) {
        this.currConnectedEntity = currConnectedEntity;
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    public MainFXMLRequestHandler getMainFXMLRequestHandler() {
        return mainFXMLRequestHandler;
    }

    public String getCurrConnectedEntity() {
        return currConnectedEntity;
    }

    public PoolingHttpClientConnectionManager getHttpClientManager() {
        return httpClientManager;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public void stopAllTasks() {
        if (this.controllerFactory.getExecutionsController() != null) {
            this.controllerFactory.getExecutionsController().stopAllTasks();
        }
    }
}
