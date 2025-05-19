package br.ufscar.dc.dsw.util;

import br.ufscar.dc.dsw.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Filtra requisições bloqueando acesso a URLs restritas conforme papel do usuário.
 */
@WebFilter("/*")
public class PermissaoFilter implements Filter {

    private final Map<String, String[]> restricoes = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        // Define quais papéis têm acesso a cada rota restrita
        restricoes.put("/admin/administradores", new String[]{"ADMIN"});
        restricoes.put("/admin/testers",         new String[]{"ADMIN"});
        restricoes.put("/admin/projetos",        new String[]{"ADMIN"});
        restricoes.put("/admin/estrategias",     new String[]{"ADMIN"});
        restricoes.put("/testador/sessoes",      new String[]{"ADMIN","TESTADOR"});
        restricoes.put("/testador/criarSessao",  new String[]{"ADMIN","TESTADOR"});
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        String papel = (usuario != null) ? usuario.getPapel() : "VISITANTE";

        // Verifica se a URL acessada está restrita e se o papel do usuário tem permissão
        boolean autorizado = true;
        for (String rota : restricoes.keySet()) {
            if (path.startsWith(rota)) {
                autorizado = false;
                for (String permitido : restricoes.get(rota)) {
                    if (permitido.equals(papel)) {
                        autorizado = true;
                        break;
                    }
                }
                break;
            }
        }

        if (!autorizado) {
            // Se não autorizado, encaminha para página de acesso negado
            request.setAttribute("uriOriginal", request.getRequestURI());
            request.getRequestDispatcher("/WEB-INF/views/common/semPermissao.jsp")
                    .forward(request, response);
        } else {
            // Se autorizado, segue com a requisição
            chain.doFilter(req, resp);
        }
    }
}
