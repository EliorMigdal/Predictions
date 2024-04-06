package engine.service.mutual;

import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;

public interface MutualRequest extends Request {
    Object generateResponse(Object... args) throws AdminAlreadyLogged, UserAlreadyExists;
}
