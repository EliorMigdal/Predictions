package Servlets.mutual.executions;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.executions.EntitiesCountRequestDTO;
import dto.response.mutual.executions.EntitiesCountResponseDTO;
import engine.services.mutual.executions.EntitiesCountService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetEntitiesCount")
public class EntitiesCountServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        EntitiesCountResponseDTO requestResult = new EntitiesCountService().getEntitiesCount(
                new Gson().fromJson(jsonObject, EntitiesCountRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
