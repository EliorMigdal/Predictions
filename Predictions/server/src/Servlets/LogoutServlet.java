package Servlets;

import Model.Model;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.exception.InitException;
import engine.service.mutual.logout.type.AdminLogout;
import engine.service.mutual.logout.type.UserLogout;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String userName = req.getParameter("username");
        boolean isClient = req.getParameter("isClient").equals("true");
        try {
            final Object o = isClient ? Model.getInstance().getEngine().handleRequest(new UserLogout(), userName) :
                    Model.getInstance().getEngine().handleRequest(new AdminLogout(), userName);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (InitException | SimulationRuntimeException | UserAlreadyExists | AdminAlreadyLogged e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(e.getMessage());
        }
    }
}
