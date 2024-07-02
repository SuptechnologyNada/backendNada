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

import model.Classe;
import service.ClasseService;

@WebServlet("/classes")
public class ClasseControlleur extends HttpServlet{
	private ClasseService classeService;

    @Override
    public void init() throws ServletException {
        classeService = new ClasseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	addCorsHeaders(resp);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        List<Classe> classes = classeService.getAllClasses();
        ObjectMapper mapper = new ObjectMapper();
        String jsonClasse = mapper.writeValueAsString(classes);
        resp.getWriter().write(jsonClasse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        StringBuilder jsonClasse = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonClasse.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonClasse.toString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Classe classe = mapper.readValue(jsonClasse.toString(), Classe.class);

            // Validate that all required fields are present
            if (classe.getId() == null || 
               classe.getNiveau() == null || classe.getNom() == null  ) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Classe cls = classeService.addClasse(classe);
            if (cls != null) {
                PrintWriter response = resp.getWriter();
                response.write(mapper.writeValueAsString(cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse JSON");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	addCorsHeaders(resp);
        // Implement update logic here
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	addCorsHeaders(resp);
        resp.setHeader("Access-Control-Allow-Origin", "*"); // Allow requests from any origin
        StringBuilder jsonClasse = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonClasse.append(line.trim());
        }
        System.out.println(jsonClasse.toString());
        ObjectMapper mapper = new ObjectMapper();
        Classe classe = mapper.readValue(jsonClasse.toString(), Classe.class);
        classeService.deleteClasse(classe);
    }
    
    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
    }

}
