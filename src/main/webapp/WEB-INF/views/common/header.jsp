<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
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

<c:if test="${!esconderHeader}">
    <div class="top-bar">
        <c:choose>
            <%-- Se usuário logado, mostra nome e papel --%>
            <c:when test="${not empty sessionScope.usuarioLogado}">
                Usuario: <strong>${sessionScope.usuarioLogado.nome}</strong> (${sessionScope.usuarioLogado.papel}) |
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:when>
            <%-- Se visitante --%>
            <c:otherwise>
                Usuario: <strong>Visitante</strong> |
                <a href="${pageContext.request.contextPath}/login">Login</a>
            </c:otherwise>
        </c:choose>
    </div>
</c:if>
