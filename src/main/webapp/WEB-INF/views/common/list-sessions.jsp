<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="sessao.titulo"/></title>
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
    <h1><fmt:message key="sessao.titulo"/></h1>
    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary"><fmt:message key="botao.voltar"/></a>
    <a href="${pageContext.request.contextPath}/admin/sessions/" class="btn btn-primary"><fmt:message key="sessao.gerenciar"/></a>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th><fmt:message key="sessao.projeto"/></th>
            <th><fmt:message key="sessao.testador"/></th>
            <th><fmt:message key="sessao.estrategia"/></th>
            <th><fmt:message key="sessao.duracao"/></th>
            <th><fmt:message key="sessao.descricao"/></th>
            <th><fmt:message key="sessao.data.criacao"/></th>
            <th><fmt:message key="sessao.data.inicio"/></th>
            <th><fmt:message key="sessao.data.termino"/></th>
            <th><fmt:message key="sessao.status"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="session" items="${sessions}" varStatus="loop">
            <tr>
                <td>${session.id}</td>
                <td>${detailSessions[loop.index].projectName}</td>
                <td>${detailSessions[loop.index].testerName}</td>
                <td>${detailSessions[loop.index].strategyName}</td>
                <td>${session.minutesDuration}</td>
                <td>${session.description}</td>
                <td>
                    <tags:localDate date="${session.creationDate}" pattern="dd/MM/yyyy HH:mm"/>
                </td>
                <td>
                    <c:if test="${session.endDate != null}">
                        <tags:localDate date="${session.startDate}" pattern="dd/MM/yyyy HH:mm"/>
                    </c:if>
                </td>
                <td>
                    <c:if test="${session.endDate != null}">
                        <tags:localDate date="${session.endDate}" pattern="dd/MM/yyyy HH:mm"/>
                    </c:if>
                </td>
                <td>
                    <span class="status-badge ${session.status.name().toLowerCase()}">
                            ${session.status}
                    </span>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>