package Servlets.client.requests;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.requests.AllClientRequestsDTO;
import dto.response.client.requests.RequestsListDTO;
import engine.services.client.requests.RequestsListService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetRequestsList")
public class RequestsListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        RequestsListDTO requestResult = new RequestsListService().getRequestsList(
                new Gson().fromJson(jsonObject, AllClientRequestsDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
