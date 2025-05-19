<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${tester.id == null}">Novo Testador</c:when>
            <c:otherwise>Editar Testador</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1>
        <c:choose>
            <c:when test="${tester.id == null}">Novo Testador</c:when>
            <c:otherwise>Editar Testador</c:otherwise>
        </c:choose>
    </h1>

    <c:if test="${not empty mensagens}">
        <div class="alert">
            <c:forEach items="${mensagens.erros}" var="erro">
                ${erro}<br/>
            </c:forEach>
        </div>
    </c:if>

    <a href="${pageContext.request.contextPath}/admin/testers" class="btn btn-secondary">Voltar</a>

    <form id="adminForm"
          action="${pageContext.request.contextPath}/admin/testers/${tester.id == null ? 'inserir' : 'atualizar'}"
          method="post" novalidate>

        <c:if test="${tester.id != null}">
            <input type="hidden" name="id" value="${tester.id}" />
        </c:if>

        <div class="form-group">
            <label>Nome:</label>
            <input id="nome" type="text" name="nome" value="${tester.nome}" required >
            <div id="nomeError" class="error-message"></div>
        </div>

        <div class="form-group">
            <label>E-mail:</label>
            <input id="email" type="email" name="email" value="${tester.email}" required
                   placeholder="usuario@dominio.com">
            <div id="emailError" class="error-message"></div>
        </div>

        <div class="form-group">
            <label>Senha:</label>
            <input id="senha" type="password" name="senha" minlength="6" required
                   placeholder="mínimo 6 caracteres">
            <div id="senhaError" class="error-message"></div>
        </div>

        <button type="submit" class="btn btn-primary">
            <c:choose>
                <c:when test="${tester.id == null}">Criar</c:when>
                <c:otherwise>Atualizar</c:otherwise>
            </c:choose>
        </button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/assets/js/validation.js"></script>

</body>
</html>
