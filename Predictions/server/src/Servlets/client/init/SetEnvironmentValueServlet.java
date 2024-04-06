package Servlets.client.init;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.init.SetEnvValueDTO;
import dto.response.client.init.SetEnvValueResponseDTO;
import engine.services.client.initialize.SetEnvValueService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/SetEnvironmentValue")
public class SetEnvironmentValueServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        SetEnvValueResponseDTO requestResult = new SetEnvValueService().setEnvironmentValue(
                new Gson().fromJson(jsonObject, SetEnvValueDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
