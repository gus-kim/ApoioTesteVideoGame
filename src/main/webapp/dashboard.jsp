<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="dashboard.titulo"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1><fmt:message key="dashboard.titulo"/></h1>
    <ul class="menu">

        <%-- Link para ADMIN ativo, para outros em vermelho --%>
        <c:choose>
            <c:when test="${not empty sessionScope.usuarioLogado && sessionScope.usuarioLogado.papel == 'ADMIN'}">
                <li><a href="${pageContext.request.contextPath}/admin/administradores" class="botao-verde"><fmt:message key="dashboard.gerenciar.admin"/></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/testers" class="botao-verde"><fmt:message key="dashboard.gerenciar.testadores"/></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/admin/administradores" class="botao-vermelho"><fmt:message key="dashboard.gerenciar.admin"/> <fmt:message key="dashboard.restrito"/></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/testers" class="botao-vermelho"><fmt:message key="dashboard.gerenciar.testadores"/> <fmt:message key="dashboard.restrito"/></a></li>
            </c:otherwise>
        </c:choose>

        <%-- Link público para todos --%>
        <li><a href="${pageContext.request.contextPath}/estrategias" class="botao-verde"><fmt:message key="dashboard.explorar.estrategias"/></a></li>

            <li><a href="${pageContext.request.contextPath}/sessions" class="botao-verde"><fmt:message key="dashboard.explorar.sessoes"/></a></li>

    </ul>
</div>

</body>
</html>
