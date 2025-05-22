package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.ConexaoBD;
import br.ufscar.dc.dsw.model.Estrategia;
import jakarta.servlet.annotation.WebServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "EstrategiaController", urlPatterns = {"/estrategias", "/admin/estrategias/*"})
public class SessionsController {
    String sql = "INSERT INTO estrategias (nome) VALUES (?)";
}


