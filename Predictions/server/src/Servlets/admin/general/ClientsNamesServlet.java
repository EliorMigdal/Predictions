package Servlets.admin.general;

import Model.Model;
import com.google.gson.Gson;
import dto.request.admin.general.ClientNamesRequestDTO;
import dto.response.admin.general.ClientNamesResponseDTO;
import engine.services.admin.general.ClientsNamesService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetClientsNames")
public class ClientsNamesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        ClientNamesResponseDTO requestResult = new ClientsNamesService().getClientsNames(
                new Gson().fromJson(jsonObject, ClientNamesRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
