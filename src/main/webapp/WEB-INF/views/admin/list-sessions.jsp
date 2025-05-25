<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gerenciar Sessões</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sessions.css" />
    <script>
        function deleteSession(sessionId) {
            if (confirm('Tem certeza que deseja remover esta sessão?')) {
                fetch('${pageContext.request.contextPath}/admin/sessions/remover' + sessionId, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            window.location.reload(); // Recarrega a página após exclusão
                        } else {
                            alert('Erro ao remover a sessão');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Erro ao remover a sessão');
                    });
            }
        }
    </script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <h1>Gerenciar Sessões</h1>
    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Voltar</a>
    <a href="${pageContext.request.contextPath}/admin/sessions/novo" class="btn btn-primary">Nova Sessão</a>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>ID do Projeto</th>
            <th>ID do Testador</th>
            <th>ID da Estratégia</th>
            <th>Duração (minutos)</th>
            <th>Descrição</th>
            <th>Data de Criação</th>
            <th>Data de Início</th>
            <th>Data de Término</th>
            <th>Status</th>
            <th>Ações</th>
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
                <td>
                    <tags:localDate date="${session.creationDate}" pattern="dd/MM/yyyy HH:mm"/>
                </td>
                <td>
                    <c:if test="${not empty session.startDate}">
                        <tags:localDate date="${session.creationDate}" pattern="dd/MM/yyyy HH:mm"/>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty session.endDate}">
                        <tags:localDate date="${session.creationDate}" pattern="dd/MM/yyyy HH:mm"/>
                    </c:if>
                </td>
                <td>
                    <span class="status-badge ${session.status.name().toLowerCase()}">
                            ${session.status}
                    </span>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/sessions/editar?id=${session.id}"
                       class="btn btn-primary">Editar</a>
                    <a href="${pageContext.request.contextPath}/admin/sessions/remover?id=${session.id}"
                            class="btn btn-danger" >Remover</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>