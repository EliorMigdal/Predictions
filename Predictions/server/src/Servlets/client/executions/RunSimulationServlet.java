package Servlets.client.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.executions.RunSimulationDTO;
import dto.response.mutual.general.EmptyResponseDTO;
import engine.services.client.executions.RunSimulationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/RunSimulation")
public class RunSimulationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        EmptyResponseDTO requestResult = new RunSimulationService().runSimulation(
                new Gson().fromJson(jsonObject, RunSimulationDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
