package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.EstrategiaDAO;
import br.ufscar.dc.dsw.dao.ProjetoDAO;
import br.ufscar.dc.dsw.dao.SessionDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.*;
import br.ufscar.dc.dsw.util.SessionService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "SessionsController", urlPatterns = {"/sessions", "/admin/sessions/*"})
public class SessionsController extends HttpServlet {

    private SessionDAO sessionDAO;
    private ProjetoDAO projetoDAO;
    private UsuarioDAO usuarioDAO;
    private EstrategiaDAO estrategiaDAO;

    public void init() {
        sessionDAO = new SessionDAO();
        projetoDAO = new ProjetoDAO();
        usuarioDAO = new UsuarioDAO();
        estrategiaDAO = new EstrategiaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) pathInfo = "";

        if ("/sessions".equals(servletPath)) {
            try {
                listSessions(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("/admin/sessions".equals(servletPath)) {
            switch (pathInfo) {
                case "/novo":
                    try {
                        showNewForm(request, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/editar":
                    showEditForm(request, response);
                    break;
                case "/remover": //todo: alterar para método DELETE
                    deleteSession(request, response);
                    break;
                default:
                    try {
                        listSessionsAdmin(request, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
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
                    try {
                        listSessions(request, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listSessions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Session> sessions = sessionDAO.getAll();
        List<DetailSession> detailSessions = SessionService.getSessionsInformation(sessions);
        request.setAttribute("sessions", sessions);
        request.setAttribute("detailSessions", detailSessions);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/list-sessions.jsp");
        dispatcher.forward(request, response);
    }

    private void listSessionsAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Session> sessions = sessionDAO.getAll();
        List<DetailSession> detailSessions = SessionService.getSessionsInformation(sessions);
        request.setAttribute("sessions", sessions);
        request.setAttribute("detailSessions", detailSessions);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/list-sessions.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Projeto> projects = projetoDAO.listarTodos();
        request.setAttribute("projects", projects);
        List<Usuario> testers = usuarioDAO.listarTesters();
        request.setAttribute("testers", testers);
        List<Estrategia> strategies = estrategiaDAO.listarTodos();
        request.setAttribute("strategies", strategies);
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

        Session updatedSession = new Session();
        updatedSession.setId(existingSession.getId());
        updatedSession.setProjectId(projectId);
        updatedSession.setTesterId(testerId);
        updatedSession.setStrategyId(strategyId);
        updatedSession.setMinutesDuration(minutesDuration);
        updatedSession.setDescription(description);
        updatedSession.setStatus(status);
        updatedSession.setCreationDate(existingSession.getCreationDate());
        updatedSession.setStartDate(startDate != null ? startDate : existingSession.getStartDate());
        updatedSession.setEndDate(endDate != null ? endDate : existingSession.getEndDate());

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


