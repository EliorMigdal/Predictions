package engine.service.mutual.login;

import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.mutual.MutualRequest;

public interface LoginRequest extends MutualRequest {
    Object generateResponse(Object... args) throws AdminAlreadyLogged, UserAlreadyExists;
}
