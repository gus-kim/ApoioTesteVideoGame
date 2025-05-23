<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sessões Cadastradas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <h1>Sessões Cadastradas</h1>

    <p>
        Papel do usuário logado: <strong>${sessionScope.usuarioLogado != null ? sessionScope.usuarioLogado.papel : "Nenhum usuário logado"}</strong>
    </p>

    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Voltar</a>
    <c:if test="${sessionScope.usuarioLogado != null && sessionScope.usuarioLogado.papel == 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/admin/estrategias" class="btn btn-primary modo-admin-btn">
            Modo Administração
        </a>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>ID do Projeto</th>
            <th>ID do Testador</th>
            <th>ID da Estratégia</th>
            <th>Duração (minutos)</th>
            <th>Descrição</th>
            <th>Status</th>
            <th>Data de Criação</th>
            <th>Data de Início</th>
            <th>Data de Término</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="session" items="${sessions}">
            <tr>
                <td>${session.id}</td>
                <td>${session.projectId}</td>
                <td>${session.testerId}</td>
                <td>${session.strategyId}</td>
                <td>${session.minutesDuration}</td>
                <td>${session.description}</td>
                <td>${session.status}</td>
                <td>
                    <fmt:formatDate value="${session.creationDate}" pattern="dd/MM/yyyy HH:mm" />
                </td>
                <td>
                    <c:if test="${not empty session.startDate}">
                        <fmt:formatDate value="${session.startDate}" pattern="dd/MM/yyyy HH:mm" />
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty session.endDate}">
                        <fmt:formatDate value="${session.endDate}" pattern="dd/MM/yyyy HH:mm" />
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
