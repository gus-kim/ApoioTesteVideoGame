<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1>Dashboard</h1>
    <ul class="menu">

        <%-- Link para ADMIN ativo, para outros em vermelho --%>
        <c:choose>
            <c:when test="${not empty sessionScope.usuarioLogado && sessionScope.usuarioLogado.papel == 'ADMIN'}">
                <li><a href="${pageContext.request.contextPath}/admin/administradores" class="botao-verde">Gerenciar Administradores</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/testers" class="botao-verde">Gerenciar Testadores</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/admin/administradores" class="botao-vermelho">Gerenciar Administradores (restrito)</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/testers" class="botao-vermelho">Gerenciar Testadores (restrito)</a></li>
            </c:otherwise>
        </c:choose>

        <%-- Link público para todos --%>
        <li><a href="${pageContext.request.contextPath}/estrategias" class="botao-verde">Explorar Estratégias</a></li>

        <%-- Link para ADMIN ou TESTADOR ativo, vermelho para outros --%>
        <c:choose>
            <c:when test="${not empty sessionScope.usuarioLogado && (sessionScope.usuarioLogado.papel == 'ADMIN' || sessionScope.usuarioLogado.papel == 'TESTADOR')}">
                <li><a href="${pageContext.request.contextPath}/testador/criarSessao" class="botao-verde">Criar Sessão de Teste</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/testador/criarSessao" class="botao-vermelho">Criar Sessão de Teste (restrito)</a></li>
            </c:otherwise>
        </c:choose>

    </ul>
</div>

</body>
</html>
