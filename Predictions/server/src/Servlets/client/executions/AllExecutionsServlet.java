package Servlets.client.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.executions.AllExecutionsDTO;
import dto.response.client.executions.AllExecutionsResponseDTO;
import engine.services.client.executions.AllExecutionsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetAllExecutions")
public class AllExecutionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        AllExecutionsResponseDTO requestResult = new AllExecutionsService().getAllExecutions(
                new Gson().fromJson(jsonObject, AllExecutionsDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
