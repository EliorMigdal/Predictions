package Servlets.mutual.details.tree;

import Model.Model;
import com.google.gson.Gson;
import dto.request.mutual.details.tree.TreeDetailsRequestDTO;
import dto.response.mutual.details.tree.TreeDetailsResponseDTO;
import engine.services.mutual.details.tree.TreeDetailsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GetTreeDetails")
public class TreeDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        TreeDetailsResponseDTO requestResult = new TreeDetailsService().provideService(
                new Gson().fromJson(jsonObject, TreeDetailsRequestDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
