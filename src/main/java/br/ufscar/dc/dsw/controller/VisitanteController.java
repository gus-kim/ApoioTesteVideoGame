package br.ufscar.dc.dsw.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Redireciona requisições de /visitante para /dashboard,
 * onde o visitante vê opções disponíveis ou desabilitadas.
 */
public class VisitanteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/dashboard"); // Redireciona visitante ao dashboard
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp); // Trata POST igual GET
    }
}
