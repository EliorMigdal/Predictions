package Servlets.client.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.executions.SimulationStatusDTO;
import dto.response.client.executions.SimulationStatusResponseDTO;
import engine.services.client.executions.SimulationStatusService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetSimulationStatus")
public class SimulationStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        SimulationStatusResponseDTO requestResult = new SimulationStatusService().getSimulationStatus(
                new Gson().fromJson(jsonObject, SimulationStatusDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
