package Servlets.mutual.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.executions.PopulationResultsRequestDTO;
import dto.response.mutual.executions.PopulationResultsResponseDTO;
import engine.services.mutual.executions.PopulationResultsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetPopulationResults")
public class PopulationResultsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        PopulationResultsResponseDTO requestResult = new PopulationResultsService().getPopulationHistory(
                new Gson().fromJson(jsonObject, PopulationResultsRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
