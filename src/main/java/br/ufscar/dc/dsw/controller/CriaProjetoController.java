package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.ProjetoDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.Projeto;
import br.ufscar.dc.dsw.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/criaProjeto")
public class CriaProjetoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Carrega testadores para o select
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> testadores = null;
        try {
            testadores = usuarioDAO.listarTesters();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("testadores", testadores);

        request.getRequestDispatcher("/WEB-INF/views/admin/criar-projeto.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Coleta dados do formulário
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String[] membros = request.getParameterValues("membros");

        // Validação básica
        if (nome == null || membros == null) {
            response.sendRedirect("criar-projeto?error=campos.obrigatorios");
            return;
        }

        // Converte membros para Long
        List<Long> membrosIds = new ArrayList<>();
        try {
            for (String m : membros) {
                membrosIds.add(Long.parseLong(m));
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("criar-projeto?error=ids.invalidos");
            return;
        }

        // Cria projeto
        Projeto projeto = new Projeto(nome, descricao);
        ProjetoDAO projetoDAO = new ProjetoDAO();
        boolean sucesso = projetoDAO.createProjeto(projeto, membrosIds);

        if (sucesso) {
            response.sendRedirect("lista-projetos?success=projeto.criado");
        } else {
            response.sendRedirect("criar-projeto?error=erro.banco");
        }
    }

}
