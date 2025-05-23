package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/admin/lista-projetos")
public class ListaProjetoAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !usuario.isAdmin()) {
            response.sendRedirect("../login?error=acesso.negado");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/lista-projetos.jsp")
                .forward(request, response);
    }
}
