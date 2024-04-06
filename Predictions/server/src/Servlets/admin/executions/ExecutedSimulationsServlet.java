package Servlets.admin.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.executions.ExecutedSimulationsRequestDTO;
import dto.response.admin.execution.ExecutedSimulationsResponseDTO;
import engine.services.admin.executions.ExecutedSimulationsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetExecutedSimulations")
public class ExecutedSimulationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        ExecutedSimulationsResponseDTO requestResult = new ExecutedSimulationsService().getExecutedSimulations(
                new Gson().fromJson(jsonObject, ExecutedSimulationsRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
