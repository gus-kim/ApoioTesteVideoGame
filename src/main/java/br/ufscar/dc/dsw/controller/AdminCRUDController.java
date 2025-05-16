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
// Controlador responsável pelo CRUD de usuários com papel ADMIN
public class AdminCRUDController extends HttpServlet {

    private UsuarioDAO dao;

    @Override
    public void init() {
        dao = new UsuarioDAO(); // Instancia o DAO na inicialização do servlet
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        verificarPermissoes(request, response);

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
        verificarPermissoes(request, response);

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
        List<Usuario> admins = dao.listarAdmins();
        request.setAttribute("admins", admins);
        request.getRequestDispatcher("/WEB-INF/views/admin/lista.jsp")
                .forward(request, response);
    }

    private void exibirFormulario(HttpServletRequest request, HttpServletResponse response, Usuario admin)
            throws ServletException, IOException {
        request.setAttribute("admin", admin);
        request.getRequestDispatcher("/WEB-INF/views/admin/formulario.jsp")
                .forward(request, response);
    }

    private void carregarAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Usuario admin = dao.buscarPorId(id);
        exibirFormulario(request, response, admin);
    }

    private void inserirAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Erro erros = new Erro();
        String nome  = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (nome == null || nome.isBlank()) {
            erros.add("Nome não informado!");
        }
        if (email == null || email.isBlank()) {
            erros.add("E-mail não informado!");
        } else if (!email.contains("@") || !email.contains(".")) {
            erros.add("E-mail inválido!");
        }
        if (senha == null || senha.isBlank()) {
            erros.add("Senha não informada!");
        }

        if (!erros.isExisteErros()) {
            try {
                Usuario admin = new Usuario();
                admin.setNome(nome);
                admin.setEmail(email);
                admin.setSenha(senha);
                admin.setPapel("ADMIN");
                dao.criarAdmin(admin);
                response.sendRedirect(request.getContextPath() + "/admin/administradores");
                return;
            } catch (SQLException e) {
                erros.add(e.getMessage()); // Aqui captura o erro "E-mail já cadastrado!"
            }
        }

        // Se houver erros, reenviar dados preenchidos e mensagens para o formulário
        request.setAttribute("mensagens", erros);
        Usuario admin = new Usuario();
        admin.setNome(nome);
        admin.setEmail(email);
        exibirFormulario(request, response, admin);
    }

    private void atualizarAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Erro erros = new Erro();
        Long   id    = Long.parseLong(request.getParameter("id"));
        String nome  = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (nome == null || nome.isBlank()) {
            erros.add("Nome não informado!");
        }
        if (email == null || email.isBlank()) {
            erros.add("E-mail não informado!");
        } else if (!email.contains("@") || !email.contains(".")) {
            erros.add("E-mail inválido!");
        }
        if (senha == null || senha.isBlank()) {
            erros.add("Senha não informada!");
        }

        if (!erros.isExisteErros()) {
            try {
                Usuario admin = new Usuario();
                admin.setId(id);
                admin.setNome(nome);
                admin.setEmail(email);
                admin.setSenha(senha);
                admin.setPapel("ADMIN");
                dao.atualizarAdmin(admin);
                response.sendRedirect(request.getContextPath() + "/admin/administradores");
                return;
            } catch (SQLException e) {
                erros.add(e.getMessage()); // Captura o erro de e-mail duplicado
            }
        }

        request.setAttribute("mensagens", erros);
        Usuario admin = new Usuario();
        admin.setId(id);
        admin.setNome(nome);
        admin.setEmail(email);
        exibirFormulario(request, response, admin);
    }


    private void removerAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.excluirAdmin(id);
        response.sendRedirect(request.getContextPath() + "/admin/administradores");
    }

    private void verificarPermissoes(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        // Verifica se o usuário da sessão está autenticado e tem papel ADMIN
        if (usuario == null || !"ADMIN".equals(usuario.getPapel())) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}