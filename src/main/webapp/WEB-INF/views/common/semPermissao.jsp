<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    String uriOriginal = (String) request.getAttribute("uriOriginal");
    String contextPath = request.getContextPath();
    String pathInterno = (uriOriginal != null)
            ? uriOriginal.substring(contextPath.length())
            : "";

    String permissaoNecessaria;
    if (pathInterno.contains("admin")) {
        permissaoNecessaria = "ADMIN";
    } else if (pathInterno.contains("testador")) {
        permissaoNecessaria = "ADMIN ou TESTADOR";
    } else {
        permissaoNecessaria = "Não identificada";
    }

    request.setAttribute("pathInterno", pathInterno);
    request.setAttribute("permissaoNecessaria", permissaoNecessaria);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Acesso Negado</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1>Acesso Negado</h1>

    <c:choose>
        <%-- Se usuário logado, mostra nome e papel --%>
        <c:when test="${not empty sessionScope.usuarioLogado}">
            <p>
                Você está logado como:
                <strong>${sessionScope.usuarioLogado.nome}</strong>
                (${sessionScope.usuarioLogado.papel})
            </p>
        </c:when>
        <%-- Se visitante --%>
        <c:otherwise>
            <p>
                Você está navegando como:
                <strong>Visitante</strong>
            </p>
        </c:otherwise>
    </c:choose>

    <p>Você não tem permissão para acessar este recurso.</p>
    <p>
        <strong>Permissão necessária:</strong>
        ${permissaoNecessaria}
    </p>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/dashboard" class="botao-verde">
            Voltar ao Dashboard
        </a>
        <a href="${pageContext.request.contextPath}/login" class="botao-verde">
            Fazer Login
        </a>
    </div>
</div>

</body>
</html>
