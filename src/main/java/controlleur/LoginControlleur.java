package controlleur;

import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Login;
import service.LoginService;

@WebServlet("/loginServlet")
public class LoginControlleur extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final LoginService loginService = new LoginService();
   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);

        // Lire le corps de la requête
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Échec de la lecture du corps de la requête");
            return;
        }

        // Parser les données JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(requestBody.toString());
        } catch (IOException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Échec de l'analyse du JSON");
            return;
        }

        // Extraire le nom d'utilisateur et le mot de passe
        String username = rootNode.path("username").asText().trim();
        String password = rootNode.path("password").asText().trim();

        System.out.println("username: " + username);  // Journaliser le nom d'utilisateur
        System.out.println("Password: " + password);  // Journaliser le mot de passe

        // Vérifier si l'utilisateur est authentifié
        Login login = loginService.authenticate(username, password);

        if (login != null) {
            // Authentification réussie
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": true, \"type\": \"utilisateur\"}");
        } else {
            // Échec de l'authentification
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Échec de l'authentification");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
        response.setHeader("Access-Control-Max-Age", "3600");
    }
}
