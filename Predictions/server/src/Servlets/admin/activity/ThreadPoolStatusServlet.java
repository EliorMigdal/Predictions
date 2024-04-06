package Servlets.admin.activity;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.general.EmptyRequestDTO;
import dto.response.admin.activity.ThreadPoolStatusResponseDTO;
import engine.services.admin.activity.ThreadPoolStatusService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetThreadPoolStatus")
public class ThreadPoolStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        ThreadPoolStatusResponseDTO requestResult = new ThreadPoolStatusService().provideService(
                new Gson().fromJson(jsonObject, EmptyRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
