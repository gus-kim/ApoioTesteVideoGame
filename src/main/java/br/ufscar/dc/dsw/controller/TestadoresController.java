package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.Usuario;
import br.ufscar.dc.dsw.util.Erro;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "/novo":
                    exibirFormulario(request, response, new Usuario());
                    break;
                case "/editar":
                    carregarTester(request, response);
                    break;
                case "/remover":
                    removerTester(request, response);
                    break;
                default:
                    listarTesters(request, response);
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
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "/inserir":
                    inserirTester(request, response);
                    return;
                case "/atualizar":
                    atualizarTester(request, response);
                    return;
                default:
                    listarTesters(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarTesters(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Usuario> testers = dao.listarTesters();
        request.setAttribute("testers", testers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/list-testers.jsp");
        dispatcher.forward(request, response);
    }

    private void exibirFormulario(HttpServletRequest request, HttpServletResponse response, Usuario tester)
            throws ServletException, IOException {

        request.setAttribute("tester", tester);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/tester-form.jsp");
        dispatcher.forward(request, response);
    }

    private void carregarTester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Usuario tester = dao.buscarPorId(id);
        exibirFormulario(request, response, tester);
    }

    private void inserirTester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        Erro erros = new Erro();
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (nome == null || nome.isBlank()) erros.add("Nome não informado!");
        if (email == null || email.isBlank()) erros.add("E-mail não informado!");
        else if (!email.contains("@") || !email.contains(".")) erros.add("E-mail inválido!");
        if (senha == null || senha.isBlank()) erros.add("Senha não informada!");

        if (!erros.isExisteErros()) {
            try {
                Usuario tester = new Usuario(nome, email, senha, "TESTADOR");
                dao.criarTester(tester);
                response.sendRedirect(request.getContextPath() + "/admin/testers");
                return;
            } catch (SQLException e) {
                erros.add(e.getMessage());
            }
        }

        request.setAttribute("mensagens", erros);
        Usuario tester = new Usuario(nome, email, senha, "TESTADOR");
        exibirFormulario(request, response, tester);
    }

    private void atualizarTester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        Erro erros = new Erro();
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (nome == null || nome.isBlank()) erros.add("Nome não informado!");
        if (email == null || email.isBlank()) erros.add("E-mail não informado!");
        else if (!email.contains("@") || !email.contains(".")) erros.add("E-mail inválido!");
        if (senha == null || senha.isBlank()) erros.add("Senha não informada!");

        if (!erros.isExisteErros()) {
            try {
                Usuario tester = new Usuario(nome, email, senha, "TESTADOR");
                tester.setId(id);
                dao.atualizarUsuario(tester);
                response.sendRedirect(request.getContextPath() + "/admin/testers");
                return;
            } catch (SQLException e) {
                erros.add(e.getMessage());
            }
        }

        request.setAttribute("mensagens", erros);
        Usuario tester = new Usuario(nome, email, senha, "TESTADOR");
        tester.setId(id);
        exibirFormulario(request, response, tester);
    }

    private void removerTester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        dao.excluirUsuario(id);

        response.sendRedirect(request.getContextPath() + "/admin/testers");
    }

    private Erro validarFormulario(HttpServletRequest request, boolean isInsert) {
        Erro erros = new Erro();

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (nome == null || nome.isBlank()) erros.add("Nome não informado!");
        if (email == null || email.isBlank()) erros.add("E-mail não informado!");
        else if (!email.contains("@") || !email.contains(".")) erros.add("E-mail inválido!");

        if (isInsert) {
            if (senha == null || senha.isBlank()) erros.add("Senha não informada!");
            else if (senha.length() < 6) erros.add("Senha deve ter no mínimo 6 caracteres.");
        } else {
            if (senha != null && !senha.isBlank() && senha.length() < 6) {
                erros.add("Senha deve ter no mínimo 6 caracteres.");
            }
        }

        return erros;
    }

    private Usuario montarTesterDoRequest(HttpServletRequest request, boolean isInsert) {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (!isInsert && (senha == null || senha.isBlank())) {
            senha = "";
        }

        return new Usuario(nome, email, senha, "TESTADOR");
    }
}
