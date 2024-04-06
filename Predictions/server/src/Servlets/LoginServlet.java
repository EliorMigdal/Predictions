package Servlets;

import Model.Model;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.exception.InitException;
import engine.service.mutual.login.type.AdminLogin;
import engine.service.mutual.login.type.ClientLogin;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String userName = req.getParameter("username");
        boolean isClient = req.getParameter("isClient").equals("true");
        try {
            final Object o = isClient ?
                    Model.getInstance().getEngine().handleRequest(new ClientLogin(), userName):
                    Model.getInstance().getEngine().handleRequest(new AdminLogin(), userName);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UserAlreadyExists | InitException | SimulationRuntimeException | AdminAlreadyLogged exception) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(exception.getMessage());
        }
    }
}
