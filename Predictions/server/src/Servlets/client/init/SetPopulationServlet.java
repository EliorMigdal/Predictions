package Servlets.client.init;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.init.SetPopulationDTO;
import dto.response.client.init.SetPopulationResponseDTO;
import engine.services.client.initialize.SetPopulationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/SetPopulation")
public class SetPopulationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        SetPopulationResponseDTO requestResult = new SetPopulationService().setPopulation(
                new Gson().fromJson(jsonObject, SetPopulationDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
