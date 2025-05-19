<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:choose><c:when test="${tester != null}">Editar Testador</c:when><c:otherwise>Criar Novo Testador</c:otherwise></c:choose></title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 100px; }
        input[type="text"], input[type="email"], input[type="password"] { padding: 5px; width: 300px; }
        .button { padding: 5px 10px; text-decoration: none; color: white; border-radius: 3px; border: none; cursor: pointer; }
        .button.save { background-color: #4CAF50; }
        .button.cancel { background-color: #f44336; }
        .error { color: red; margin-top: 10px; }
    </style>
</head>
<body>
<h1><c:choose><c:when test="${tester != null}">Editar Testador</c:when><c:otherwise>Criar Novo Testador</c:otherwise></c:choose></h1>

<c:if test="${error != null}">
    <div class="error">${error}</div>
</c:if>

<form action="${pageContext.request.contextPath}/admin/testers/<c:choose><c:when test="${tester != null}">update</c:when><c:otherwise>create</c:otherwise></c:choose>" method="post">
    <c:if test="${tester != null}">
        <input type="hidden" name="id" value="${tester.id}" />
    </c:if>

    <div class="form-group">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="${tester.nome}" required />
    </div>

    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${tester.email}" required />
    </div>

    <div class="form-group">
        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" <c:if test="${tester == null}">required</c:if> />
        <c:if test="${tester != null}"><small>(Deixe em branco para manter a senha atual)</small></c:if>
    </div>

    <div class="form-group">
        <button type="submit" class="button save">Salvar</button>
        <a href="${pageContext.request.contextPath}/admin/testers" class="button cancel">Cancelar</a>
    </div>
</form>
</body>
</html>