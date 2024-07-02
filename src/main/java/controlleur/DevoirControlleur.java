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

import model.Devoir;
import service.DevoirService;

@WebServlet("/devoirs")
public class DevoirControlleur extends HttpServlet {
    private DevoirService devoirService;

    @Override
    public void init() throws ServletException {
        devoirService = new DevoirService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        List<Devoir> devoirs = devoirService.getAllDevoirs();
        ObjectMapper mapper = new ObjectMapper();
        String jsonDevoirs = mapper.writeValueAsString(devoirs);
        resp.getWriter().write(jsonDevoirs);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonDevoir = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonDevoir.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonDevoir.toString());
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Devoir devoir = mapper.readValue(jsonDevoir.toString(), Devoir.class);

            // Validate that all required fields are present
            if (devoir.getId() == null || 
                devoir.getNom() == null || devoir.getDescription() == null || devoir.getEnfants_id() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Devoir addedDevoir = devoirService.addDevoir(devoir);
            if (addedDevoir != null) {
                PrintWriter response = resp.getWriter();
                response.write(mapper.writeValueAsString(addedDevoir));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonDevoir = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonDevoir.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonDevoir.toString());

        ObjectMapper mapper = new ObjectMapper();
        Devoir devoirToDelete = mapper.readValue(jsonDevoir.toString(), Devoir.class);
        devoirService.deleteDevoir(devoirToDelete);
    }

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
    }
}
