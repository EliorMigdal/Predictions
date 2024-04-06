package Servlets.admin.activity;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.activity.CurrentlyRunningRequestDTO;
import dto.response.admin.activity.CurrentlyRunningResponseDTO;
import engine.services.admin.activity.CurrentlyRunningService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetClientActivity")
public class CurrentlyRunningServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        CurrentlyRunningResponseDTO requestResult = new CurrentlyRunningService().provideService(
                new Gson().fromJson(jsonObject, CurrentlyRunningRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
