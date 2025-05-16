package br.ufscar.dc.dsw.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "VisitanteController", urlPatterns = { "/visitante" })
// Controlador simples para exibir a página do visitante
public class VisitanteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/visitante.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Redireciona POST para o mesmo comportamento do GET
        doGet(req, resp);
    }
}