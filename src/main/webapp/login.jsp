<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="login.titulo"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>

<div class="container">
    <h1><fmt:message key="login.titulo"/></h1>

    <%-- Mensagens de erro vindas do backend --%>
    <c:if test="${not empty mensagens}">
        <div class="alert">
            <c:forEach items="${mensagens.erros}" var="erro">
                ${erro}<br>
            </c:forEach>
        </div>
    </c:if>

    <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post" novalidate>
        <div class="form-group">
            <label><fmt:message key="login.email"/>:</label>
            <input type="email"
                   name="email"
                   id="email"
                   value="${param.email}"
                   placeholder="usuario@dominio.com"
                   required>
            <div id="emailError" class="error-message"></div>
        </div>

        <div class="form-group">
            <label><fmt:message key="login.senha"/>:</label>
            <input type="password"
                   name="senha"
                   id="senha"
                   minlength="6"
                   placeholder="mínimo 6 caracteres"
                   required>
            <div id="senhaError" class="error-message"></div>
        </div>

        <button type="submit" class="btn btn-primary"><fmt:message key="botao.entrar"/></button>
        <a href="${pageContext.request.contextPath}/index" class="btn btn-secondary"><fmt:message key="botao.voltar"/></a>
    </form>
</div>

<script src="${pageContext.request.contextPath}/assets/js/validation.js"></script>

</body>
</html>
