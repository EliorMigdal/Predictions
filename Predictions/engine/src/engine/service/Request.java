package engine.service;

import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.exception.InitException;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.verifier.type.exception.XMLException;

public interface Request {
    Object generateResponse(Object... args)
            throws InitException, SimulationRuntimeException, AdminAlreadyLogged, UserAlreadyExists, XMLException;
}
