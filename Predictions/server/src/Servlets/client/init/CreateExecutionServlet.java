package Servlets.client.init;

import Model.Model;
import com.google.gson.Gson;
import dto.request.client.init.CreateExecutionDTO;
import dto.response.client.init.NewExecutionDTO;
import engine.services.client.initialize.CreateNewExecution;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/CreateNewExecution")
public class CreateExecutionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String jsonObject = request.getParameter("json");
        NewExecutionDTO requestResult = new CreateNewExecution().createExecution(
                new Gson().fromJson(jsonObject, CreateExecutionDTO.class),
                Model.getInstance().getEngine());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(requestResult));
    }
}
