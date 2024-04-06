package Servlets.mutual.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.executions.PropertyResultsRequestDTO;
import dto.response.mutual.executions.PropertyResultResponseDTO;
import engine.services.mutual.executions.PropertyResultsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetPropertyResults")
public class PropertyResultsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        PropertyResultResponseDTO requestResult = new PropertyResultsService().getPropertyResult(
                new Gson().fromJson(jsonObject, PropertyResultsRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
