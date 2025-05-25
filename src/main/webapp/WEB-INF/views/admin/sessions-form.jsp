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
            <label for="projectInput">Projeto:</label>
            <input id="projectInput" list="projectList" required />
            <input type="hidden" id="projectId" name="projectId" value="${session.projectId}" />
            <datalist id="projectList">
                <c:forEach var="project" items="${projects}">
                    <option value="${project.nome}" data-id="${project.id}" />
                </c:forEach>
            </datalist>
        </div>


        <div class="form-group">
            <label for="testerInput">Testador:</label>
            <input id="testerInput" list="testerList" required />
            <input type="hidden" id="testerId" name="testerId" value="${session.testerId}" />
            <datalist id="testerList">
                <c:forEach var="tester" items="${testers}">
                    <option value="${tester.nome}" data-id="${tester.id}" />
                </c:forEach>
            </datalist>
        </div>


        <div class="form-group">
            <label for="strategyInput">Estratégia:</label>
            <input id="strategyInput" list="strategyList" required />
            <input type="hidden" id="strategyId" name="strategyId" value="${session.strategyId}" />
            <datalist id="strategyList">
                <c:forEach var="strategy" items="${strategies}">
                    <option value="${strategy.nome}" data-id="${strategy.id}" />
                </c:forEach>
            </datalist>
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

        <p>Mantenha as datas vazias caso não queira alterá-las</p>

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
<script>
    function bindDatalistInput(visibleInputId, datalistId, hiddenInputId) {
        const visibleInput = document.getElementById(visibleInputId);
        const hiddenInput = document.getElementById(hiddenInputId);
        const datalist = document.getElementById(datalistId);
        const options = datalist.options;

        // Atualiza o hidden com o ID correspondente ao nome
        visibleInput.addEventListener("change", () => {
            const inputValue = visibleInput.value;
            let found = false;
            for (let option of options) {
                if (option.value === inputValue) {
                    hiddenInput.value = option.getAttribute("data-id");
                    found = true;
                    break;
                }
            }
            if (!found) {
                hiddenInput.value = "";
                alert("Selecione uma opção válida da lista.");
            }
        });

        // Quando carregar a página, preencher o campo visível com base no hidden (modo edição)
        window.addEventListener("DOMContentLoaded", () => {
            const currentId = hiddenInput.value;
            for (let option of options) {
                if (option.getAttribute("data-id") === currentId) {
                    visibleInput.value = option.value;
                    break;
                }
            }
        });
    }

    // Bind para os 3 campos
    bindDatalistInput("projectInput", "projectList", "projectId");
    bindDatalistInput("testerInput", "testerList", "testerId");
    bindDatalistInput("strategyInput", "strategyList", "strategyId");
</script>

</body>
</html>