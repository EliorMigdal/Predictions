package Servlets.client.init;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.init.InitialValuesDTO;
import dto.response.client.init.InitialPopulationResponseDTO;
import engine.services.client.initialize.InitialPopulationsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetInitialPopulations")
public class InitialPopulationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        InitialPopulationResponseDTO requestResult = new InitialPopulationsService().getInitialPopulation(
                new Gson().fromJson(jsonObject, InitialValuesDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
