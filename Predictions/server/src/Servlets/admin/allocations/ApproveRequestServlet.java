package Servlets.admin.allocations;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.allocations.ApproveRequestDTO;
import dto.response.mutual.general.EmptyResponseDTO;
import engine.services.admin.allocations.ApproveRequestService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ApproveClientRequest")
public class ApproveRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        EmptyResponseDTO requestResult = new ApproveRequestService().approveRequest(
                new Gson().fromJson(jsonObject, ApproveRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
