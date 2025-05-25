package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.ProjetoDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.Projeto;
import br.ufscar.dc.dsw.model.Usuario;
import br.ufscar.dc.dsw.util.Erro;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "ProjetoController", urlPatterns = { "/admin/projetos/*" })
public class ProjetoController extends HttpServlet {

    private ProjetoDAO projetoDAO;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        projetoDAO = new ProjetoDAO();
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null) action = "";

        try {
            switch (action) {
                case "/novo":
                    showNewForm(request, response);
                    break;
                case "/editar":
                    showEditForm(request, response);
                    break;
                case "/remover":
                    deleteProjeto(request, response);
                    break;
                default:
                    listProjetos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null) action = "";

        try {
            switch (action) {
                case "/inserir":
                    insertProjeto(request, response);
                    break;
                case "/atualizar":
                    updateProjeto(request, response);
                    break;
                default:
                    listProjetos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProjetos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Projeto> projetos = projetoDAO.listarTodos();
        String sort = request.getParameter("sort");

        if (sort != null) {
            switch (sort) {
                case "nome":
                    projetos.sort(Comparator.comparing(Projeto::getNome));
                    break;
                case "dataCriacao":
                    projetos.sort(Comparator.comparing(Projeto::getDataCriacao));
                    break;
                default:
                    break;
            }
        }

        if ("true".equals(request.getParameter("ajax"))) {
            response.setContentType("application/json");
            response.getWriter().print(convertToJson(projetos));
        } else {
            request.setAttribute("projetos", projetos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/list-projetos.jsp");
            dispatcher.forward(request, response);
        }
    }

    private String convertToJson(List<Projeto> projetos) {
        StringBuilder json = new StringBuilder("[");
        for (Projeto p : projetos) {
            json.append(String.format(
                    "{\"id\":%d,\"nome\":\"%s\",\"descricao\":\"%s\",\"dataCriacao\":\"%s\"},",
                    p.getId(), p.getNome(), p.getDescricao(), p.getDataCriacao()
            ));
        }
        if (!projetos.isEmpty()) json.deleteCharAt(json.length() - 1);
        json.append("]");
        return json.toString();
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Usuario> membros = usuarioDAO.listarTesters();
        request.setAttribute("membros", membros);
        request.getRequestDispatcher("/WEB-INF/views/admin/projeto.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Projeto projeto = projetoDAO.buscarPorId(id);
        List<Usuario> membros = usuarioDAO.listarTesters();
        request.setAttribute("projeto", projeto);
        request.setAttribute("membros", membros);
        request.getRequestDispatcher("/WEB-INF/views/admin/projeto.jsp").forward(request, response);
    }

    private void insertProjeto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        Projeto projeto = extractProjetoFromRequest(request);
        projetoDAO.inserir(projeto);
        response.sendRedirect(request.getContextPath() + "/admin/projetos");
    }

    private void updateProjeto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        Projeto projeto = extractProjetoFromRequest(request);
        projeto.setId(Long.parseLong(request.getParameter("id")));
        projetoDAO.atualizar(projeto);
        response.sendRedirect(request.getContextPath() + "/admin/projetos");
    }

    private Projeto extractProjetoFromRequest(HttpServletRequest request) {
        Projeto projeto = new Projeto();
        projeto.setNome(request.getParameter("nome"));
        projeto.setDescricao(request.getParameter("descricao"));
        String[] membros = request.getParameterValues("membros");
        List<Long> membrosIds = new ArrayList<>();
        if (membros != null) {
            for (String id : membros) {
                membrosIds.add(Long.parseLong(id));
            }
        }
        projeto.setMembros(membrosIds);
        return projeto;
    }

    private void deleteProjeto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        projetoDAO.excluir(id);
        response.sendRedirect(request.getContextPath() + "/admin/projetos");
    }
}