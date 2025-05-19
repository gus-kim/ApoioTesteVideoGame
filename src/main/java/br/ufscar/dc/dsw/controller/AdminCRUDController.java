package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.Usuario;
import br.ufscar.dc.dsw.util.Erro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminController", urlPatterns = { "/admin/administradores/*" })
public class AdminCRUDController extends HttpServlet {

    private UsuarioDAO dao;

    @Override
    public void init() {
        dao = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) action = "";

        try {
            switch (action) {
                case "/novo":
                    exibirFormulario(request, response, new Usuario());
                    break;
                case "/editar":
                    carregarAdmin(request, response);
                    break;
                case "/remover":
                    removerAdmin(request, response);
                    return;
                default:
                    listarAdmins(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
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
                    inserirAdmin(request, response);
                    return;
                case "/atualizar":
                    atualizarAdmin(request, response);
                    return;
                default:
                    listarAdmins(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarAdmins(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // Carrega lista de administradores para exibir na página
        List<Usuario> admins = dao.listarAdmins();
        request.setAttribute("admins", admins);
        request.getRequestDispatcher("/WEB-INF/views/admin/lista.jsp").forward(request, response);
    }

    private void exibirFormulario(HttpServletRequest request, HttpServletResponse response, Usuario admin)
            throws ServletException, IOException {
        request.setAttribute("admin", admin);
        request.getRequestDispatcher("/WEB-INF/views/admin/formulario.jsp").forward(request, response);
    }

    private void carregarAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // Busca admin pelo id para preencher formulário de edição
        Long id = Long.parseLong(request.getParameter("id"));
        Usuario admin = dao.buscarPorId(id);
        exibirFormulario(request, response, admin);
    }

    private void inserirAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Erro erros = validarFormulario(request);

        if (!erros.isExisteErros()) {
            Usuario admin = new Usuario(request.getParameter("nome"), request.getParameter("email"),
                    request.getParameter("senha"), "ADMIN");
            dao.criarAdmin(admin);
            response.sendRedirect(request.getContextPath() + "/admin/administradores");
            return;
        }

        // Em caso de erro, mostra mensagens e mantém dados preenchidos no formulário
        request.setAttribute("mensagens", erros);
        Usuario admin = new Usuario(request.getParameter("nome"), request.getParameter("email"),
                request.getParameter("senha"), "ADMIN");
        exibirFormulario(request, response, admin);
    }

    private void atualizarAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Erro erros = validarFormulario(request);
        Long id = Long.parseLong(request.getParameter("id"));

        if (!erros.isExisteErros()) {
            Usuario admin = new Usuario(request.getParameter("nome"), request.getParameter("email"),
                    request.getParameter("senha"), "ADMIN");
            admin.setId(id);
            dao.atualizarAdmin(admin);
            response.sendRedirect(request.getContextPath() + "/admin/administradores");
            return;
        }

        // Em caso de erro, mantém dados e mensagens para correção no formulário
        request.setAttribute("mensagens", erros);
        Usuario admin = new Usuario(request.getParameter("nome"), request.getParameter("email"),
                request.getParameter("senha"), "ADMIN");
        admin.setId(id);
        exibirFormulario(request, response, admin);
    }

    private void removerAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.excluirAdmin(id);
        response.sendRedirect(request.getContextPath() + "/admin/administradores");
    }

    private Erro validarFormulario(HttpServletRequest request) {
        Erro erros = new Erro();
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Validação básica dos campos
        if (nome == null || nome.isBlank()) erros.add("Nome não informado!");
        if (email == null || email.isBlank()) erros.add("E-mail não informado!");
        else if (!email.contains("@") || !email.contains(".")) erros.add("E-mail inválido!");
        if (senha == null || senha.isBlank()) erros.add("Senha não informada!");

        return erros;
    }
}
