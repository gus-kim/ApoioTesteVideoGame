package br.ufscar.dc.dsw.util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

@WebFilter("/*")
public class I18nFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        // 1. Verifica parâmetro 'lang' na URL
        String langParam = httpRequest.getParameter("lang");

        // 2. Define o locale baseado na preferência
        Locale locale;
        if (langParam != null) {
            // Usa o parâmetro da URL
            if ("en_US".equals(langParam)) {
                locale = Locale.US;
            } else {
                locale = new Locale("pt", "BR");
            }
            session.setAttribute("lang", langParam);
        } else if (session.getAttribute("lang") != null) {
            // Usa o valor da sessão se existir
            String sessionLang = (String) session.getAttribute("lang");
            locale = "en_US".equals(sessionLang) ? Locale.US : new Locale("pt", "BR");
        } else {
            // Usa o locale do navegador como fallback
            Locale browserLocale = httpRequest.getLocale();
            locale = "pt".equals(browserLocale.getLanguage()) ?
                    new Locale("pt", "BR") : Locale.US;
        }

        // Configura o locale para JSTL
        Config.set(session, Config.FMT_LOCALE, locale);

        // Continua a cadeia de filtros
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialização se necessário
    }

    @Override
    public void destroy() {
        // Cleanup se necessário
    }
}