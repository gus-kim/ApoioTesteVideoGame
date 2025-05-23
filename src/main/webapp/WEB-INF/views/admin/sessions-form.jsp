<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${session.id == null}">Nova Sessão de Testes</c:when>
            <c:otherwise>Editar Sessão de Testes</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estrategia-form.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">

    <h1>
        <c:choose>
            <c:when test="${session.id == null}">Nova Sessão de Testes</c:when>
            <c:otherwise>Editar Sessão de Testes</c:otherwise>
        </c:choose>
    </h1>

    <a href="${pageContext.request.contextPath}/admin/sessions" class="btn btn-secondary">Voltar</a>

    <form action="${pageContext.request.contextPath}/admin/sessions/${session.id == null ? 'inserir' : 'atualizar'}" method="post" novalidate>

        <c:if test="${session.id != null}">
            <input type="hidden" name="id" value="${session.id}" />
        </c:if>

        //ADICIONAR AJAX
        <div class="form-group">
            <label for="projectId">ID do Projeto:</label>
            <input id="projectId" name="projectId" type="number" value="${session.projectId}" required />
        </div>

        <div class="form-group">
            <label for="testerId">ID do Testador:</label>
            <input id="testerId" name="testerId" type="number" value="${session.testerId}" required />
        </div>

        <div class="form-group">
            <label for="strategyId">ID da Estratégia:</label>
            <input id="strategyId" name="strategyId" type="number" value="${session.strategyId}" required />
        </div>

        <div class="form-group">
            <label for="minutesDuration">Duração (minutos):</label>
            <input id="minutesDuration" name="minutesDuration" type="number" value="${session.minutesDuration}" required />
        </div>

        <div class="form-group">
            <label for="description">Descrição:</label>
            <textarea id="description" name="description">${session.description}</textarea>
        </div>

        <div class="form-group">
            <label for="status">Status:</label>
            <select id="status" name="status" required>
                <option value="">Selecione...</option>
                <option value="CREATED" ${session.status == 'CREATED' ? 'selected' : ''}>Criada</option>
                <option value="IN_PROGRESS" ${session.status == 'IN_PROGRESS' ? 'selected' : ''}>Em Progresso</option>
                <option value="FINISHED" ${session.status == 'FINISHED' ? 'selected' : ''}>Finalizada</option>
            </select>
        </div>

        <div class="form-group">
            <label for="startDate">Data de Início:</label>
            <input id="startDate" name="startDate" type="datetime-local"
                   value="<fmt:formatDate value="${session.startDate}" pattern="yyyy-MM-dd'T'HH:mm" />" />
        </div>

        <div class="form-group">
            <label for="endDate">Data de Término:</label>
            <input id="endDate" name="endDate" type="datetime-local"
                   value="<fmt:formatDate value="${session.endDate}" pattern="yyyy-MM-dd'T'HH:mm" />" />
        </div>

        <button type="submit" class="btn">
            <c:choose>
                <c:when test="${session.id == null}">Criar</c:when>
                <c:otherwise>Atualizar</c:otherwise>
            </c:choose>
        </button>
    </form>
</div>
</body>
</html>