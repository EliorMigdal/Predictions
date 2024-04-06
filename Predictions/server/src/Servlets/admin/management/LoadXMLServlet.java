package Servlets.admin.management;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.management.LoadFileRequestDTO;
import dto.response.admin.management.LoadFileResponseDTO;
import engine.services.admin.management.LoadXMLService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/LoadSimulation")
public class LoadXMLServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        LoadXMLService loadXMLService = new LoadXMLService();
        LoadFileResponseDTO requestResult = loadXMLService.provideService(
                new Gson().fromJson(jsonObject, LoadFileRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
