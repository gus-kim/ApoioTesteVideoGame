package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.ProjetoDAO;
import br.ufscar.dc.dsw.model.Projeto;
import br.ufscar.dc.dsw.model.Usuario;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/projetos")
public class ListaProjetoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Parâmetros de ordenação
        String ordenarPor = request.getParameter("ordenarPor");
        ordenarPor = (ordenarPor != null) ? ordenarPor : "nome";

        // Busca projetos
        ProjetoDAO projetoDAO = new ProjetoDAO();
        List<Projeto> projetos = projetoDAO.listAllProjetos(
                ordenarPor,
                usuario.getId(),
                usuario.isAdmin()
        );

        // Retorna JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new Gson().toJson(projetos, response.getWriter());
    }
}