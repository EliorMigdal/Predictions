package Servlets.admin.general;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.general.RequestsSizeRequestDTO;
import dto.response.admin.general.RequestsSizeResponseDTO;
import engine.services.admin.general.RequestsSizeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetNumOfRequests")
public class NumOfClientRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        RequestsSizeResponseDTO requestResult = new RequestsSizeService().getNumOfRequests(
                new Gson().fromJson(jsonObject, RequestsSizeRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
