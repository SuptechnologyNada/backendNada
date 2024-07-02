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

import model.Enfants;
import service.EnfantsService;

@WebServlet("/enfants")
public class EnfantsControlleur extends HttpServlet {
    private EnfantsService enfantsService;

    @Override
    public void init() throws ServletException {
        enfantsService = new EnfantsService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        List<Enfants> enfants = enfantsService.getAllEnfants();
        ObjectMapper mapper = new ObjectMapper();
        String jsonEnfants = mapper.writeValueAsString(enfants);
        resp.getWriter().write(jsonEnfants);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonEnfant = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonEnfant.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonEnfant.toString());
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Enfants enfant = mapper.readValue(jsonEnfant.toString(), Enfants.class);

            // Validate that all required fields are present
            if (enfant.getId() == 0 || 
                enfant.getNom() == null || enfant.getPrenom() == null || enfant.getDate_naissance() == null || enfant.getClass_id()==0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Enfants addedEnfant = enfantsService.addEnfant(enfant);
            if (addedEnfant != null) {
                PrintWriter response = resp.getWriter();
                response.write(mapper.writeValueAsString(addedEnfant));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
       
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonEnfant = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonEnfant.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonEnfant.toString());

        ObjectMapper mapper = new ObjectMapper();
        Enfants enfantToDelete = mapper.readValue(jsonEnfant.toString(), Enfants.class);
        enfantsService.deleteEnfant(enfantToDelete);
    }

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
    }
}
