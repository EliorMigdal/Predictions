package engine.service.mutual.logout;

import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.mutual.MutualRequest;

public interface LogoutRequest extends MutualRequest {
    Object generateResponse(Object... args) throws AdminAlreadyLogged, UserAlreadyExists;
}
