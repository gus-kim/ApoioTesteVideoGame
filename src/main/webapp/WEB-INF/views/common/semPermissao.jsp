<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="acesso.negado"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1><fmt:message key="acesso.negado"/></h1>

    <c:choose>
        <c:when test="${not empty sessionScope.usuarioLogado}">
            <p>
                <fmt:message key="voce.esta.logado.como"/>:
                <strong>${sessionScope.usuarioLogado.nome}</strong>
                (${sessionScope.usuarioLogado.papel})
            </p>
        </c:when>
        <c:otherwise>
            <p>
                <fmt:message key="voce.esta.navegando.como"/>:
                <strong><fmt:message key="visitante"/></strong>
            </p>
        </c:otherwise>
    </c:choose>

    <p><fmt:message key="voce.nao.tem.permissao"/></p>

    <p>
        <strong><fmt:message key="permissao.necessaria"/>:</strong>
        <c:out value="${permissaoNecessaria}" default="Não identificada" />
    </p>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/dashboard" class="botao-verde">
            <fmt:message key="voltar.ao.dashboard"/>
        </a>
        <a href="${pageContext.request.contextPath}/login" class="botao-verde">
            <fmt:message key="fazer.login"/>
        </a>
    </div>
</div>

</body>
</html>
