package engine;

import engine.client.Client;
import engine.client.request.Request;
import engine.exception.SimulationAlreadyExists;
import engine.jaxb.generated.PRDWorld;
import engine.manager.UserManager;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.exception.InitException;
import engine.simulation.Simulation;
import engine.simulation.exception.creation.EntityCreationException;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.exception.creation.RuleCreationException;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.threadpool.CustomThreadPool;
import engine.version.VersionManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface EngineService extends Serializable {
    void loadSimulationDetails(PRDWorld prdWorld)
            throws EnvironmentCreationException, EntityCreationException, RuleCreationException,
            SimulationAlreadyExists;
    Object handleRequest(engine.service.Request request, Object... args)
            throws InitException, SimulationRuntimeException, UserAlreadyExists, AdminAlreadyLogged;
    CustomThreadPool getThreadPool();
    UserManager getUserManager();
    void runSimulation(Simulation simulation, Client client);
    Collection<Client> getClients();
    Optional<Client> getClientData(String clientName);
    Optional<Request> getRequestData(String clientName, Integer requestID);
    Optional<Simulation> getRequestSimulation(String clientName, Integer requestID, Integer simulationID);
    Map<PRDWorld, LocalDate> getSimulationsCollection();
    VersionManager getVersionManager();
    void runSimulationForTestings() throws EnvironmentCreationException, EntityCreationException, RuleCreationException, SimulationRuntimeException;
}