package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.Usuario;
import br.ufscar.dc.dsw.util.Erro;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "IndexController", urlPatterns = { "/index", "/login", "/logout", "/dashboard" })
// Controlador responsável por rotas principais: login, logout, dashboard e página inicial
public class IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/logout":
                // Invalida a sessão e redireciona para a página inicial
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/index");
                return;
            case "/visitante":
                request.getRequestDispatcher("/visitante.jsp").forward(request, response);
                return;
            case "/index":
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return;
            case "/login":
                // Apenas exibe o formulário de login
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            case "/dashboard":
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
                return;
            default:
                response.sendRedirect(request.getContextPath() + "/index");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Apenas a rota /login realiza POST (processa o formulário de login)
        if ("/login".equals(request.getServletPath())) {
            Erro erros = new Erro();
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            if (email == null || email.isBlank()) erros.add("E-mail não informado!");
            if (senha == null || senha.isBlank()) erros.add("Senha não informada!");

            if (!erros.isExisteErros()) {
                try {
                    UsuarioDAO dao = new UsuarioDAO();
                    Usuario usuario = dao.buscarPorEmail(email);
                    // Verifica se as credenciais estão corretas
                    if (usuario != null && usuario.getSenha().equals(senha)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("usuarioLogado", usuario);
                        response.sendRedirect(request.getContextPath() + "/dashboard");
                        return;
                    } else {
                        erros.add("Credenciais inválidas!");
                    }
                } catch (SQLException e) {
                    erros.add("Erro de conexão com o banco de dados.");
                }
            }

            // Se houver erros, reexibe o formulário com mensagens
            request.setAttribute("mensagens", erros);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            // Qualquer outro POST redireciona para a página inicial
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
}