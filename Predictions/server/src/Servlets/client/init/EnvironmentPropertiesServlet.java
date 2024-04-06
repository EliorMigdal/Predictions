package Servlets.client.init;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.init.EnvironmentPropertiesDTO;
import dto.response.client.init.AllEnvPropertiesDTO;
import engine.services.client.initialize.EnvironmentPropertiesService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetEnvironmentProperties")
public class EnvironmentPropertiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        AllEnvPropertiesDTO requestResult = new EnvironmentPropertiesService().getEnvironmentProperties(
                new Gson().fromJson(jsonObject, EnvironmentPropertiesDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
