package controlleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Salles;
import service.SallesService;

@WebServlet("/salles")
public class SallesControlleur extends HttpServlet {
    private SallesService sallesService;

    @Override
    public void init() throws ServletException {
        sallesService = new SallesService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        List<Salles> salles = sallesService.getAllSalles();
        ObjectMapper mapper = new ObjectMapper();
        String jsonSalles = mapper.writeValueAsString(salles);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonSalles);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonSalle = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonSalle.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonSalle.toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Salles salle = mapper.readValue(jsonSalle.toString(), Salles.class);

            // Validate that all required fields are present
            if (salle.getId() == null || salle.getNom() == null || salle.getCapacite() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Salles addedSalle = sallesService.addSalle(salle);
            if (addedSalle != null) {
                PrintWriter response = resp.getWriter();
                resp.setContentType("application/json");
                response.write(mapper.writeValueAsString(addedSalle));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonSalle = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonSalle.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonSalle.toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Salles salle = mapper.readValue(jsonSalle.toString(), Salles.class);

            // Validate that all required fields are present
            if (salle.getId() == null || salle.getNom() == null || salle.getCapacite() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Salles updatedSalle = sallesService.updateSalle(salle);
            if (updatedSalle != null) {
                PrintWriter response = resp.getWriter();
                resp.setContentType("application/json");
                response.write(mapper.writeValueAsString(updatedSalle));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonSalle = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonSalle.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonSalle.toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Salles salleToDelete = mapper.readValue(jsonSalle.toString(), Salles.class);
            sallesService.deleteSalle(salleToDelete);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
    }
}
