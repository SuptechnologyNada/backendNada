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

import model.Recommandations;
import service.RecommandationsService;

@WebServlet("/recommandations")
public class RecommandationsControlleur extends HttpServlet {
    private RecommandationsService recommandationsService;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        recommandationsService = new RecommandationsService();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        String idParam = req.getParameter("id");
        if (idParam != null) {
            Long id = Long.parseLong(idParam);
            Recommandations recommandation = recommandationsService.getRecommandationById(id);
            if (recommandation != null) {
                String jsonRecommandation = mapper.writeValueAsString(recommandation);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonRecommandation);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Recommandation not found");
            }
        } else {
            List<Recommandations> recommandations = recommandationsService.getAllRecommandations();
            String jsonRecommandations = mapper.writeValueAsString(recommandations);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonRecommandations);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonRecommandation = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonRecommandation.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonRecommandation.toString());

        try {
            Recommandations recommandation = mapper.readValue(jsonRecommandation.toString(), Recommandations.class);

            // Validate that all required fields are present
            if (recommandation.getId() == null || 
                recommandation.getEnfant_id() == null || recommandation.getDescription() == null
                || recommandation.getNom()==null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Recommandations addedRecommandation = recommandationsService.addRecommandation(recommandation);
            if (addedRecommandation != null) {
                PrintWriter response = resp.getWriter();
                resp.setContentType("application/json");
                response.write(mapper.writeValueAsString(addedRecommandation));
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
        StringBuilder jsonRecommandation = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonRecommandation.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonRecommandation.toString());

        try {
            Recommandations recommandationToDelete = mapper.readValue(jsonRecommandation.toString(), Recommandations.class);
            recommandationsService.deleteRecommandation(recommandationToDelete);
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
