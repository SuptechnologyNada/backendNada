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

import model.Groupe;
import service.GroupeService;

@WebServlet("/groupes")
public class GroupeControlleur extends HttpServlet {
    private GroupeService groupeService;

    @Override
    public void init() throws ServletException {
        groupeService = new GroupeService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        List<Groupe> groupes = groupeService.getAllGroupe();
        ObjectMapper mapper = new ObjectMapper();
        String jsonGroupes = mapper.writeValueAsString(groupes);
        resp.getWriter().write(jsonGroupes);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonGroupe = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonGroupe.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonGroupe.toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Groupe groupe = mapper.readValue(jsonGroupe.toString(), Groupe.class);

            // Validate that all required fields are present
            if (groupe.getId() == null || 
                groupe.getNom() == null || groupe.getDescription() == null || groupe.getEnfant_id() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Groupe addedGroupe = groupeService.addGroupe(groupe);
            if (addedGroupe != null) {
                PrintWriter response = resp.getWriter();
                resp.setContentType("application/json");
                response.write(mapper.writeValueAsString(addedGroupe));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonGroupe = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonGroupe.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonGroupe.toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Groupe groupe = mapper.readValue(jsonGroupe.toString(), Groupe.class);

            // Validate that all required fields are present
            if (groupe.getId() == null || 
                groupe.getNom() == null || groupe.getDescription() == null || groupe.getEnfant_id() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Groupe updatedGroupe = groupeService.updateGroupe(groupe);
            if (updatedGroupe != null) {
                PrintWriter response = resp.getWriter();
                resp.setContentType("application/json");
                response.write(mapper.writeValueAsString(updatedGroupe));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonGroupe = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonGroupe.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonGroupe.toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Groupe groupeToDelete = mapper.readValue(jsonGroupe.toString(), Groupe.class);
            groupeService.deleteGroupe(groupeToDelete);
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
