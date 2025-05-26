package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.BugDAO;
import br.ufscar.dc.dsw.dao.SessionDAO;
import br.ufscar.dc.dsw.model.Bug;
import br.ufscar.dc.dsw.model.Session;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BugController", urlPatterns = {"/bugs/*"})
public class BugController extends HttpServlet {

    private BugDAO bugDAO;
    private SessionDAO sessionDAO;

    @Override
    public void init() {
        bugDAO = new BugDAO();
        sessionDAO = new SessionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        switch (pathInfo) {
            case "/remover":
                deleteBug(request, response);
                break;
            case "/add":
                showAddForm(request, response);
                break;

            default:
                listBugsBySessionId(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        if ("/inserir".equals(pathInfo)) {
            insertBug(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void insertBug(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long sessaoId = Long.parseLong(request.getParameter("sessaoId"));
        String descricao = request.getParameter("descricao");

        Bug bug = new Bug();
        bug.setSessaoId(sessaoId);
        bug.setDescricao(descricao);

        bugDAO.insert(bug);
        response.sendRedirect(request.getContextPath() + "/bugs?sessao_id=" + sessaoId);
    }

    private void listBugsBySessionId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sessaoIdParam = request.getParameter("sessao_id");
        if (sessaoIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "sessao_id é obrigatório.");
            return;
        }

        Session session = sessionDAO.getById(Long.parseLong(sessaoIdParam));
        List<Bug> bugs = bugDAO.getBySessaoId(Long.parseLong(sessaoIdParam));

        request.setAttribute("session", session);
        request.setAttribute("bugs", bugs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/list-bugs.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteBug(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Bug bug = bugDAO.getById(id);
        if (bug != null) {
            bugDAO.delete(bug);
            response.sendRedirect(request.getContextPath() + "/bugs?sessao_id=" + bug.getSessaoId());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bug não encontrado.");
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessaoIdParam = request.getParameter("sessao_id");
        if (sessaoIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "sessao_id é obrigatório.");
            return;
        }

        Long sessaoId = Long.parseLong(sessaoIdParam);
        Session session = sessionDAO.getById(sessaoId);
        request.setAttribute("session", session);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/add-bug.jsp");
        dispatcher.forward(request, response);
    }

}
