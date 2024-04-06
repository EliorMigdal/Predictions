package Servlets.mutual.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.executions.EntityPropertiesRequestDTO;
import dto.response.mutual.executions.EntityPropertiesResponseDTO;
import engine.services.mutual.executions.EntityPropertiesService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetEntityProperties")
public class EntityPropertiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        EntityPropertiesResponseDTO requestResult = new EntityPropertiesService().getEntityProperties(
                new Gson().fromJson(jsonObject, EntityPropertiesRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
