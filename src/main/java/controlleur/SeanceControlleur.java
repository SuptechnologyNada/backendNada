package controlleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Seance;
import service.SeanceService;

@WebServlet("/seances")
public class SeanceControlleur extends HttpServlet {
    private SeanceService seanceService;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        seanceService = new SeanceService();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        List<Seance> seances = seanceService.getAllSeances();
        String jsonSeances = mapper.writeValueAsString(seances);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonSeances);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonSeance = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonSeance.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonSeance.toString());

        try {
            Seance seance = mapper.readValue(jsonSeance.toString(), Seance.class);

            // Validate that all required fields are present
            if (seance.getId() == null || 
                seance.getNom() == null || seance.getDate() == null || seance.getHeure() == null || seance.getEnfant_id() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Seance addedSeance = seanceService.addSeance(seance);
            if (addedSeance != null) {
                PrintWriter response = resp.getWriter();
                resp.setContentType("application/json");
                response.write(mapper.writeValueAsString(addedSeance));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonSeance = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonSeance.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonSeance.toString());

        try {
            Seance seance = mapper.readValue(jsonSeance.toString(), Seance.class);

            // Validate that all required fields are present
            if (seance.getId() == null || 
                seance.getNom() == null || seance.getDate() == null || seance.getHeure() == null || seance.getEnfant_id() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Seance updatedSeance = seanceService.updateSeance(seance);
            if (updatedSeance != null) {
                PrintWriter response = resp.getWriter();
                resp.setContentType("application/json");
                response.write(mapper.writeValueAsString(updatedSeance));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonSeance = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonSeance.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonSeance.toString());

        try {
            Seance seanceToDelete = mapper.readValue(jsonSeance.toString(), Seance.class);
            seanceService.deleteSeance(seanceToDelete);
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
