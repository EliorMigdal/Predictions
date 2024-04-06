package Servlets.admin.activity;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.activity.ClientUsageRequestDTO;
import dto.response.admin.activity.ClientUsageResponseDTO;
import engine.services.admin.activity.ClientUsageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetUsageInfo")
public class UsageInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        ClientUsageResponseDTO requestResult = new ClientUsageService().provideService(
                new Gson().fromJson(jsonObject, ClientUsageRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
