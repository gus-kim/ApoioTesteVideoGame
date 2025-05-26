<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${tester.id == null}"><fmt:message key="tester.novo"/></c:when>
            <c:otherwise><fmt:message key="tester.editar"/></c:otherwise>
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
            <c:when test="${tester.id == null}"><fmt:message key="tester.novo"/></c:when>
            <c:otherwise><fmt:message key="tester.editar"/></c:otherwise>
        </c:choose>
    </h1>

    <c:if test="${not empty mensagens}">
        <div class="alert">
            <c:forEach items="${mensagens.erros}" var="erro">
                ${erro}<br/>
            </c:forEach>
        </div>
    </c:if>

    <a href="${pageContext.request.contextPath}/admin/testers" class="btn btn-secondary"><fmt:message key="botao.voltar"/></a>

    <form id="adminForm"
          action="${pageContext.request.contextPath}/admin/testers/${tester.id == null ? 'inserir' : 'atualizar'}"
          method="post" novalidate>

        <c:if test="${tester.id != null}">
            <input type="hidden" name="id" value="${tester.id}" />
        </c:if>

        <div class="form-group">
            <label><fmt:message key="admin.nome"/>:</label>
            <input id="nome" type="text" name="nome" value="${tester.nome}" required >
            <div id="nomeError" class="error-message"></div>
        </div>

        <div class="form-group">
            <label><fmt:message key="admin.email"/>:</label>
            <input id="email" type="email" name="email" value="${tester.email}" required
                   placeholder="usuario@dominio.com">
            <div id="emailError" class="error-message"></div>
        </div>

        <div class="form-group">
            <label><fmt:message key="admin.senha"/>:</label>
            <input id="senha" type="password" name="senha" minlength="6" required
                   placeholder="<fmt:message key="login.senha.minimo"/>">
            <div id="senhaError" class="error-message"></div>
        </div>

        <button type="submit" class="btn btn-primary">
            <c:choose>
                <c:when test="${tester.id == null}"><fmt:message key="botao.criar"/></c:when>
                <c:otherwise><fmt:message key="botao.atualizar"/></c:otherwise>
            </c:choose>
        </button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/assets/js/validation.js"></script>

</body>
</html>
