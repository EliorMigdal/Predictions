package Servlets.client.requests;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.requests.SubmitRequestDTO;
import dto.response.mutual.general.EmptyResponseDTO;
import engine.services.client.requests.SubmitRequestService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/SubmitRequest")
public class SubmitRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        EmptyResponseDTO requestResult = new SubmitRequestService().submitRequest(
                new Gson().fromJson(jsonObject, SubmitRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
