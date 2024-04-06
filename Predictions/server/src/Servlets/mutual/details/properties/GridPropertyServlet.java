package Servlets.mutual.details.properties;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.details.properties.GridPropertyRequestDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import engine.services.mutual.details.properties.GridPropertyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetGridProperty")
public class GridPropertyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        PropertyResponseDTO requestResult = new GridPropertyService().provideService(
                new Gson().fromJson(jsonObject, GridPropertyRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}