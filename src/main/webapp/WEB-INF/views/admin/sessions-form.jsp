<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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

    <c:if test="${not empty error}">
        <div class="alert alert-danger">
            <c:choose>
                <c:when test="${error == 'missing_fields'}">Preencha todos os campos obrigatórios!</c:when>
                <c:when test="${error == 'invalid_numbers'}">IDs e duração devem ser números válidos!</c:when>
                <c:when test="${error == 'invalid_status'}">Status inválido selecionado!</c:when>
                <c:when test="${error == 'invalid_dates'}">Datas de início e término são obrigatórias!</c:when>
                <c:otherwise>Ocorreu um erro ao processar a solicitação.</c:otherwise>
            </c:choose>
        </div>
    </c:if>

    <a href="${pageContext.request.contextPath}/admin/sessions" class="btn btn-secondary">Voltar</a>

    <form action="${pageContext.request.contextPath}/admin/sessions/${session.id == null ? "inserir" : "atualizar"}" method="post" novalidate>

        <div>
            <label for="id"></label>
            <input id="id" name="id" type="number" hidden="hidden" value="${session.id}">
        </div>

        <div class="form-group">
            <label for="projectId">ID do Projeto:</label>
            <input id="projectId" name="projectId" type="number" min="1"
                   value="${empty session.projectId ? '0' : session.projectId}" required />
        </div>

        <div class="form-group">
            <label for="testerId">ID do Testador:</label>
            <input id="testerId" name="testerId" type="number" min="1"
                   value="${empty session.testerId ? '0' : session.testerId}" required />
        </div>

        <div class="form-group">
            <label for="strategyId">ID da Estratégia:</label>
            <input id="strategyId" name="strategyId" type="number" min="1"
                   value="${empty session.strategyId ? '0' : session.strategyId}" required />
        </div>

        <div class="form-group">
            <label for="minutesDuration">Duração (minutos):</label>
            <input id="minutesDuration" name="minutesDuration" type="number" min="1"
                   value="${empty session.minutesDuration ? '0' : session.minutesDuration}" required />
        </div>

        <div class="form-group">
            <label for="description">Descrição:</label>
            <textarea id="description" name="description">${empty session.description ? '' : session.description}</textarea>
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
            <input id="startDate" name="startDate" type="datetime-local" required/>

        </div>

        <div class="form-group">
            <label for="endDate">Data de Término:</label>
            <input id="endDate" name="endDate" type="datetime-local" required/>
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