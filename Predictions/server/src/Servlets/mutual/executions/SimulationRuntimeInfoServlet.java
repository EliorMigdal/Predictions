package Servlets.mutual.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.executions.SimulationRuntimeInfoRequestDTO;
import dto.response.mutual.executions.SimulationRuntimeInfoResponseDTO;
import engine.services.mutual.executions.SimulationRuntimeInfoService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetRuntimeInfo")
public class SimulationRuntimeInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        SimulationRuntimeInfoResponseDTO requestResult = new SimulationRuntimeInfoService().getSimulationInfo(
                new Gson().fromJson(jsonObject, SimulationRuntimeInfoRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
