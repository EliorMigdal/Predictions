package Servlets.client.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.executions.RemainingExecutionsDTO;
import dto.response.client.executions.RemainingExecutionsResponseDTO;
import engine.services.client.executions.RemainingExecutionsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetRemainingExecutions")
public class RemainingExecutionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        RemainingExecutionsResponseDTO requestResult = new RemainingExecutionsService().getNumOfRemainingExecutions(
                new Gson().fromJson(jsonObject, RemainingExecutionsDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
