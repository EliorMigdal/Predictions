package Servlets.admin.allocations;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.allocations.GetRequestsInfoDTO;
import dto.response.admin.allocations.RequestsInfoDTO;
import engine.services.admin.allocations.ClientsRequestsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/GetClientsRequests")
public class GetClientRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        RequestsInfoDTO requestResult = new ClientsRequestsService().getRequestsInfo(
                new Gson().fromJson(jsonObject, GetRequestsInfoDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
