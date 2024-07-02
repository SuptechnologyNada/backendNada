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

import model.Absence;
import service.AbsenceService;

@WebServlet("/absences")
public class AbsenceControlleur extends HttpServlet {

    private AbsenceService absenceService;

    @Override
    public void init() throws ServletException {
        absenceService = new AbsenceService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        List<Absence> absences = absenceService.getAllAbsences();
        ObjectMapper mapper = new ObjectMapper();
        String jsonAbsence = mapper.writeValueAsString(absences);
        resp.getWriter().write(jsonAbsence);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
        StringBuilder jsonAbsence = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonAbsence.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonAbsence.toString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Absence absence = mapper.readValue(jsonAbsence.toString(), Absence.class);

            // Validate that all required fields are present
            if (absence.getId() == null || 
                absence.getDate() == null || absence.getEnfant_Id() == null || 
                absence.getRaison() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
                return;
            }

            Absence abs = absenceService.addAbsence(absence);
            if (abs != null) {
                PrintWriter response = resp.getWriter();
                response.write(mapper.writeValueAsString(abs));
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
        StringBuilder jsonAbsence = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            jsonAbsence.append(line.trim());
        }
        System.out.println("Received JSON: " + jsonAbsence.toString());
        ObjectMapper mapper = new ObjectMapper();
        Absence absence = mapper.readValue(jsonAbsence.toString(), Absence.class);
        absenceService.deleteAbsence(absence);
    }

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
    }
}
