package Servlets.admin.activity;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.activity.SetThreadSizeRequestDTO;
import dto.response.admin.activity.SetThreadSizeResponseDTO;
import engine.services.admin.activity.SetThreadPoolSizeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/SetThreadPoolSize")
public class SetThreadPoolSizeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        SetThreadSizeResponseDTO requestResult = new SetThreadPoolSizeService().provideService(
                new Gson().fromJson(jsonObject, SetThreadSizeRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
