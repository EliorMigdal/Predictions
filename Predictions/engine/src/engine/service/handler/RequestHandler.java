package engine.service.handler;

import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;
import engine.service.exception.InitException;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.verifier.type.exception.XMLException;

public interface RequestHandler {
    Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException, AdminAlreadyLogged, UserAlreadyExists, XMLException;
}