<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages" var="lang"/>
<%-- Verifica se as mensagens estão carregadas --%>
<c:if test="${empty lang}">
    <c:set var="error" value="Arquivos de mensagens não encontrados" scope="request"/>
</c:if>

<%
    // Decide quando esconder a barra superior (index e raiz)
    String uri = request.getRequestURI();
    String ctxPath = request.getContextPath();
    boolean esconderHeader =
            uri.equals(ctxPath + "/")
                    || uri.equals(ctxPath + "/index")
                    || uri.equals(ctxPath + "/index.jsp");
    request.setAttribute("esconderHeader", esconderHeader);
%>

<div class="language-selector">
    <a href="?lang=pt_BR">Portugues</a> |
    <a href="?lang=en_US">English</a>
</div>

<c:if test="${!esconderHeader}">
    <div class="top-bar">
        <div class="language-selector">
            <a href="?lang=pt_BR">Português</a> |
            <a href="?lang=en_US">English</a>
        </div>
        <c:choose>
            <%-- Se usuário logado, mostra nome e papel --%>
            <c:when test="${not empty sessionScope.usuarioLogado}">
                <fmt:message key="usuario"/>: <strong>${sessionScope.usuarioLogado.nome}</strong> (${sessionScope.usuarioLogado.papel}) |
                <a href="${pageContext.request.contextPath}/logout"><fmt:message key="botao.logout"/></a>
            </c:when>
            <%-- Se visitante --%>
            <c:otherwise>
                <fmt:message key="usuario"/>: <strong><fmt:message key="visitante"/></strong> |
                <a href="${pageContext.request.contextPath}/login"><fmt:message key="botao.login"/></a>
            </c:otherwise>
        </c:choose>
    </div>
</c:if>
