package engine;

import engine.client.Client;
import engine.client.request.Request;
import engine.exception.SimulationAlreadyExists;
import engine.jaxb.generated.PRDWorld;
import engine.manager.UserManager;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.exception.InitException;
import engine.service.handler.MainRequestsHandler;
import engine.service.handler.RequestHandler;
import engine.simulation.Simulation;
import engine.simulation.exception.creation.EntityCreationException;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.exception.creation.RuleCreationException;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.simulation.task.RunSimulationTask;
import engine.simulation.world.entity.exception.InvalidPopulation;
import engine.threadpool.CustomThreadPool;
import engine.verifier.type.exception.XMLException;
import engine.version.VersionManager;

import java.time.LocalDate;
import java.util.*;

public final class Engine implements EngineService {
    private final RequestHandler requestHandler = new MainRequestsHandler(this);
    private final CustomThreadPool threadPool = new CustomThreadPool();
    private final Collection<Client> clientsCollection = new ArrayList<>();
    private final UserManager userManager = new UserManager();
    private final Map<PRDWorld, LocalDate> simulationsCollection = new LinkedHashMap<>();
    private final VersionManager versionManager = new VersionManager();

    @Override
    public void loadSimulationDetails(PRDWorld prdWorld) throws SimulationAlreadyExists {
        if (simulationsCollection.keySet().stream().anyMatch(simulation ->
                simulation.getName().equals(prdWorld.getName()))) {
            throw new SimulationAlreadyExists(prdWorld.getName());
        } else {
            simulationsCollection.put(prdWorld, LocalDate.now());
        }
    }

    @Override
    public CustomThreadPool getThreadPool() {
        return this.threadPool;
    }

    @Override
    public UserManager getUserManager() {
        return userManager;
    }

    @Override
    public void runSimulation(Simulation simulation, Client client) {
        RunSimulationTask nextTask = new RunSimulationTask(simulation, versionManager, client);
        threadPool.increaseNumOfQueued();
        threadPool.execute(simulation.getSimulationID(), nextTask);
    }

    @Override
    public Collection<Client> getClients() {
        return clientsCollection;
    }

    @Override
    public Optional<Client> getClientData(String clientName) {
        return this.clientsCollection.stream().filter(clientData -> clientData.getClientName().equals(clientName))
                .findFirst();
    }

    @Override
    public Optional<Request> getRequestData(String clientName, Integer requestID) {
        Optional<Client> desiredClient = getClientData(clientName);
        return desiredClient.flatMap(clientData -> clientData.getClientRequests().stream()
                .filter(clientRequestData -> clientRequestData.getRequestID().equals(requestID)).findFirst());
    }

    @Override
    public Optional<Simulation> getRequestSimulation(String clientName, Integer requestID, Integer simulationID) {
        Optional<Request> optionalRequestData = getRequestData(clientName, requestID);
        return optionalRequestData.flatMap(requestData -> requestData.getSimulations().stream()
                .filter(simulation -> simulation.getSimulationID().equals(simulationID)).findFirst());
    }

    @Override
    public Map<PRDWorld, LocalDate> getSimulationsCollection() {
        return this.simulationsCollection;
    }

    @Override
    public VersionManager getVersionManager() {
        return this.versionManager;
    }

    @Override
    public void runSimulationForTestings() throws EnvironmentCreationException, EntityCreationException, RuleCreationException, SimulationRuntimeException {
        PRDWorld prdWorld = simulationsCollection.keySet().stream()
                .filter(world -> world.getName().equals("virus")).findFirst().orElse(null);
        Simulation toRun = new Simulation(1, prdWorld);
        toRun.getWorld().getEntities().forEach(entity -> {
            try {
                entity.setEntityPopulation(35);
            } catch (InvalidPopulation e) {
                throw new RuntimeException(e);
            }
        });

        RunSimulationTask nextTask = new RunSimulationTask(toRun, versionManager, new Client("testing"));
        threadPool.execute(1 ,nextTask);
    }

    @Override
    public Object handleRequest(engine.service.Request request, Object... args)
            throws InitException, SimulationRuntimeException, UserAlreadyExists, AdminAlreadyLogged {
        try {
            return requestHandler.handleRequest(request, args);
        } catch (XMLException exception) {
            throw new InitException(exception.getMessage());
        }
    }


}