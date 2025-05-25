package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.SessionDAO;
import br.ufscar.dc.dsw.model.Session;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SessionsController", urlPatterns = {"/sessions", "/admin/sessions/*"})
public class SessionsController extends HttpServlet {

    private SessionDAO sessionDAO;

    public void init() {
        sessionDAO = new SessionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) pathInfo = "";

        if ("/sessions".equals(servletPath)) {
            listSessions(request, response);
        } else if ("/admin/sessions".equals(servletPath)) {
            switch (pathInfo) {
                case "/novo":
                    showNewForm(request, response);
                    break;
                case "/editar":
                    showEditForm(request, response);
                    break;
                case "/remover": //todo: alterar para método DELETE
                    deleteSession(request, response);
                    break;
                default:
                    listSessionsAdmin(request, response);
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) pathInfo = "";

        if ("/admin/sessions".equals(servletPath)) {
            switch (pathInfo) {
                case "/inserir":
                    insertSession(request, response);
                    break;
                case "/atualizar":
                    editSession(request, response);
                    break;
                default:
                    listSessions(request, response);
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listSessions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Session> sessions = sessionDAO.getAll();
        request.setAttribute("sessions", sessions);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/list-sessions.jsp");
        dispatcher.forward(request, response);
    }

    private void listSessionsAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Session> sessions = sessionDAO.getAll();
        request.setAttribute("sessions", sessions);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/list-sessions.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/sessions-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Session sessionToEdit = sessionDAO.getById(id);

        request.setAttribute("session", sessionToEdit);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/sessions-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long projectId = (long) Integer.parseInt(request.getParameter("projectId"));
        Long testerId = Long.parseLong(request.getParameter("testerId"));
        Long strategyId = Long.parseLong(request.getParameter("strategyId"));
        int minutesDuration = Integer.parseInt(request.getParameter("minutesDuration"));
        String description = request.getParameter("description");
        Session.SessionStatus status = Session.SessionStatus.valueOf(request.getParameter("status"));

        LocalDateTime startDate = null;
        String startDateParam = request.getParameter("startDate");
        if (startDateParam != null && !startDateParam.isEmpty()) {
            startDate = LocalDateTime.parse(startDateParam.replace("T", " "),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        LocalDateTime endDate = null;
        String endDateParam = request.getParameter("endDate");
        if (endDateParam != null && !endDateParam.isEmpty()) {
            endDate = LocalDateTime.parse(endDateParam.replace("T", " "),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        assert startDate != null;
        assert endDate != null;
        Session session = new Session();
        session.setProjectId(projectId);
        session.setTesterId(testerId);
        session.setStrategyId(strategyId);
        session.setMinutesDuration(minutesDuration);
        session.setDescription(description);
        session.setStatus(status);
        session.setStartDate(startDate);
        session.setEndDate(endDate);

        sessionDAO.insert(session);
        response.sendRedirect(request.getContextPath() + "/admin/sessions");
    }

    private void editSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Long projectId = Long.parseLong(request.getParameter("projectId"));
        Long testerId = Long.parseLong(request.getParameter("testerId"));
        Long strategyId = Long.parseLong(request.getParameter("strategyId"));
        int minutesDuration = Integer.parseInt(request.getParameter("minutesDuration"));
        String description = request.getParameter("description");
        Session.SessionStatus status = Session.SessionStatus.valueOf(request.getParameter("status"));

        LocalDateTime startDate = null;
        String startDateParam = request.getParameter("startDate");
        if (startDateParam != null && !startDateParam.isEmpty()) {
            startDate = LocalDateTime.parse(startDateParam.replace("T", " "),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        LocalDateTime endDate = null;
        String endDateParam = request.getParameter("endDate");
        if (endDateParam != null && !endDateParam.isEmpty()) {
            endDate = LocalDateTime.parse(endDateParam.replace("T", " "),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        Session existingSession = sessionDAO.getById(id);

        assert startDate != null;
        assert endDate != null;
        Session updatedSession = new Session();
        updatedSession.setId(existingSession.getId());
        updatedSession.setProjectId(projectId);
        updatedSession.setTesterId(testerId);
        updatedSession.setStrategyId(strategyId);
        updatedSession.setMinutesDuration(minutesDuration);
        updatedSession.setDescription(description);
        updatedSession.setStatus(status);
        updatedSession.setCreationDate(existingSession.getCreationDate());
        updatedSession.setStartDate(startDate);
        updatedSession.setEndDate(endDate);

        sessionDAO.update(updatedSession);
        response.sendRedirect(request.getContextPath() + "/admin/sessions");
    }

    private void deleteSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Session sessionToDelete = sessionDAO.getById(id);
        sessionDAO.delete(sessionToDelete);
        response.sendRedirect(request.getContextPath() + "/admin/sessions");
    }

}


