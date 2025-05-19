package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "TestadoresController", urlPatterns = { "/admin/testers/*" })
public class TestadoresController extends HttpServlet {

    private UsuarioDAO dao;

    @Override
    public void init() {
        dao = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/create":
                    createTester(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateTester(request, response);
                    break;
                case "/delete":
                    deleteTester(request, response);
                    break;
                default:
                    listTesters(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTesters(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Usuario> testers = dao.listarTesters();
        request.setAttribute("testers", testers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-testers.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/tester-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Usuario tester = dao.buscarPorId(id);
        request.setAttribute("tester", tester);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/tester-form.jsp");
        dispatcher.forward(request, response);
    }

    private void createTester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario novoTester = new Usuario();
        novoTester.setNome(nome);
        novoTester.setEmail(email);
        novoTester.setSenha(senha);

        try {
            dao.criarTester(novoTester);
            response.sendRedirect(request.getContextPath() + "/admin/testers");
        } catch (SQLException e) {
            request.setAttribute("error", "Erro ao criar tester: " + e.getMessage());
            request.getRequestDispatcher("/admin/tester-form.jsp").forward(request, response);
        }
    }

    private void updateTester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario tester = new Usuario();
        tester.setId(id);
        tester.setNome(nome);
        tester.setEmail(email);
        tester.setSenha(senha);

        try {
            dao.atualizarUsuario(tester);
            response.sendRedirect(request.getContextPath() + "/admin/testers");
        } catch (SQLException e) {
            request.setAttribute("error", "Erro ao atualizar tester: " + e.getMessage());
            request.setAttribute("tester", tester);
            request.getRequestDispatcher("/admin/tester-form.jsp").forward(request, response);
        }
    }

    private void deleteTester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.excluirUsuario(id);
        response.sendRedirect(request.getContextPath() + "/admin/testers");
    }
}