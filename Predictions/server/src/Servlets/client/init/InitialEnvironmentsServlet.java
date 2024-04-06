package Servlets.client.init;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.init.InitialValuesDTO;
import dto.response.client.init.InitialEnvironmentsResponseDTO;
import engine.services.client.initialize.InitialEnvironmentsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetInitialEnvironmentValues")
public class InitialEnvironmentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        InitialEnvironmentsResponseDTO requestResult = new InitialEnvironmentsService().getInitialEnvironments(
                new Gson().fromJson(jsonObject, InitialValuesDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
