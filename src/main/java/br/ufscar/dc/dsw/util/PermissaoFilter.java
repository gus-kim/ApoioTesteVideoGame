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

@WebFilter("/*")
public class PermissaoFilter implements Filter {

    private final Map<String, String[]> restricoes = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        restricoes.put("/admin/administradores", new String[]{"ADMIN"});
        restricoes.put("/admin/testers", new String[]{"ADMIN"});
        restricoes.put("/admin/projetos", new String[]{"ADMIN"});
        restricoes.put("/admin/estrategias", new String[]{"ADMIN"});
        restricoes.put("/testador/sessoes", new String[]{"ADMIN","TESTADOR"});
        restricoes.put("/testador/criarSessao", new String[]{"ADMIN","TESTADOR"});
        // A lista pública de estratégias é livre (não restrita)
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

        // Permitir acesso público para lista de estratégias e outras páginas públicas
        if (path.equals("/estrategias") ||
                path.equals("/index") ||
                path.equals("/login") ||
                path.equals("/logout") ||
                path.equals("/") ) {
            chain.doFilter(req, resp);
            return;
        }

        // Verifica se a URL acessada está restrita e se o papel tem permissão
        boolean autorizado = true;
        String permissaoNecessaria = null;

        for (String rota : restricoes.keySet()) {
            if (path.startsWith(rota)) {
                autorizado = false;
                permissaoNecessaria = String.join(" ou ", restricoes.get(rota));
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
            // Define atributo para JSP usar na mensagem de permissão necessária
            request.setAttribute("permissaoNecessaria", permissaoNecessaria != null ? permissaoNecessaria : "Não identificada");
            request.setAttribute("uriOriginal", request.getRequestURI());
            request.getRequestDispatcher("/WEB-INF/views/common/semPermissao.jsp").forward(request, response);
            return;
        }

        // Se autorizado, segue normalmente
        chain.doFilter(req, resp);
    }
}
