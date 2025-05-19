package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.Usuario;
import br.ufscar.dc.dsw.util.Erro;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controla rotas básicas: /index, /login, /logout e /dashboard
 */
public class    IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getServletPath();

        switch (action) {
            case "/logout":
                req.getSession().invalidate(); // Encerra sessão do usuário
                resp.sendRedirect(req.getContextPath() + "/index");
                return;
            case "/index":
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                return;
            case "/login":
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            case "/dashboard":
                req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
                return;
            default:
                resp.sendRedirect(req.getContextPath() + "/index");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("/login".equals(req.getServletPath())) {
            Erro erros = new Erro();
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            if (email == null || email.isBlank()) erros.add("E-mail não informado!");
            if (senha == null || senha.isBlank()) erros.add("Senha não informada!");

            if (!erros.isExisteErros()) {
                try {
                    UsuarioDAO dao = new UsuarioDAO();
                    Usuario u = dao.buscarPorEmail(email);
                    if (u != null && u.getSenha().equals(senha)) {
                        req.getSession().setAttribute("usuarioLogado", u); // Salva usuário na sessão
                        resp.sendRedirect(req.getContextPath() + "/dashboard");
                        return;
                    } else {
                        erros.add("Credenciais inválidas!");
                    }
                } catch (SQLException e) {
                    erros.add("Erro ao conectar ao banco.");
                }
            }
            req.setAttribute("mensagens", erros);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index");
        }
    }
}
