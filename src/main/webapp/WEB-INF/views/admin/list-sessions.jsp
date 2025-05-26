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
    <a href="${pageContext.request.contextPath}/sessions" class="btn btn-secondary">Voltar</a>
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
            <th>Status</th>
            <th>Ações</th>
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
                    <span class="status-badge ${session.status.name().toLowerCase()}">
                            ${session.status}
                    </span>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${session.status == 'FINISHED'}">
                            <button class="btn btn-secondary" onclick="alert('Esta sessão já foi finalizada e não pode ser editada.')">
                                Editar
                            </button>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/admin/sessions/editar?id=${session.id}"
                               class="btn btn-primary">Editar</a>
                        </c:otherwise>
                    </c:choose>

                    <a href="${pageContext.request.contextPath}/admin/sessions/remover?id=${session.id}"
                       class="btn btn-danger">Remover</a>

                    <a href="${pageContext.request.contextPath}/bugs?sessao_id=${session.id}"
                       class="btn btn-primary">Bugs</a>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>