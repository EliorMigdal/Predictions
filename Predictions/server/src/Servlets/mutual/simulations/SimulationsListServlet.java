package Servlets.mutual.simulations;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.simulations.SimulationsListRequestDTO;
import dto.response.mutual.simulations.SimulationsListResponseDTO;
import engine.services.mutual.simulations.SimulationsListService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetSimulationsList")
public class SimulationsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        SimulationsListResponseDTO requestResult = new SimulationsListService().provideService(
                new Gson().fromJson(jsonObject, SimulationsListRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
