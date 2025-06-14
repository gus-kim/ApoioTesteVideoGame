package br.ufscar.dc.dsw.util;

import br.ufscar.dc.dsw.dao.EstrategiaDAO;
import br.ufscar.dc.dsw.dao.ProjetoDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.model.DetailSession;
import br.ufscar.dc.dsw.model.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionService {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final EstrategiaDAO estrategiaDAO = new EstrategiaDAO();
    private static final ProjetoDAO projetoDAO = new ProjetoDAO();


    public static List<DetailSession> getSessionsInformation(List<Session> sessions) throws SQLException {
        List<DetailSession> sessionsInformation = new ArrayList<DetailSession>();
        for (Session session : sessions) {
            String user = usuarioDAO.buscarPorId(session.getTesterId()).getNome();
            String strategy = estrategiaDAO.buscarPorId(session.getStrategyId()).getNome();
            String project = projetoDAO.buscarPorId(session.getProjectId()).getNome();
            DetailSession detailSession = new DetailSession();
            detailSession.setProjectName(project);
            detailSession.setTesterName(user);
            detailSession.setStrategyName(strategy);
            sessionsInformation.add(detailSession);
        }
        return sessionsInformation;
    };

}
