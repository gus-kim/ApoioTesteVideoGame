package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.EstrategiaDAO;
import br.ufscar.dc.dsw.model.Estrategia;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EstrategiaController", urlPatterns = {"/estrategias", "/admin/estrategias/*"})
public class EstrategiaController extends HttpServlet {

    private EstrategiaDAO dao;

    @Override
    public void init() {
        dao = new EstrategiaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath(); // "/estrategias" ou "/admin/estrategias"
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) pathInfo = "";

        try {
            if ("/estrategias".equals(servletPath)) {
                listarPublico(request, response);
            } else if ("/admin/estrategias".equals(servletPath)) {
                switch (pathInfo) {
                    case "/novo":
                        exibirFormularioNovo(request, response);
                        break;
                    case "/editar":
                        exibirFormularioEditar(request, response);
                        break;
                    case "/remover":
                        removerEstrategia(request, response);
                        break;
                    default:
                        listarAdmin(request, response);
                        break;
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) pathInfo = "";

        try {
            if ("/admin/estrategias".equals(servletPath)) {
                switch (pathInfo) {
                    case "/inserir":
                        inserirEstrategia(request, response);
                        break;
                    case "/atualizar":
                        atualizarEstrategia(request, response);
                        break;
                    default:
                        listarAdmin(request, response);
                        break;
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarPublico(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Estrategia> estrategias = dao.listarTodos();
        request.setAttribute("estrategias", estrategias);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/list-estrategias.jsp");
        dispatcher.forward(request, response);
    }

    private void listarAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Estrategia> estrategias = dao.listarTodos();
        request.setAttribute("estrategias", estrategias);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/list-estrategias.jsp");
        dispatcher.forward(request, response);
    }

    private void exibirFormularioNovo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/estrategia-form.jsp");
        dispatcher.forward(request, response);
    }

    private void exibirFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Estrategia estrategia = dao.buscarPorId(id);
        request.setAttribute("estrategia", estrategia);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/estrategia-form.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirEstrategia(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String exemplos = request.getParameter("exemplos");
        String dicas = request.getParameter("dicas");
        String imagemUrl = request.getParameter("imagemUrl");

        Estrategia estrategia = new Estrategia(null, nome, descricao, exemplos, dicas, imagemUrl);
        dao.inserir(estrategia);
        response.sendRedirect(request.getContextPath() + "/admin/estrategias");
    }

    private void atualizarEstrategia(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String exemplos = request.getParameter("exemplos");
        String dicas = request.getParameter("dicas");
        String imagemUrl = request.getParameter("imagemUrl");

        Estrategia estrategia = new Estrategia(id, nome, descricao, exemplos, dicas, imagemUrl);
        dao.atualizar(estrategia);
        response.sendRedirect(request.getContextPath() + "/admin/estrategias");
    }

    private void removerEstrategia(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.excluir(id);
        response.sendRedirect(request.getContextPath() + "/admin/estrategias");
    }
}
