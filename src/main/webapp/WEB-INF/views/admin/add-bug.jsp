<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Adicionar Bug</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estrategia-form.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <h1>Adicionar Bug à Sessão ${session.id}</h1>

    <form action="${pageContext.request.contextPath}/bugs/inserir" method="post">
        <input type="hidden" name="sessaoId" value="${session.id}"/>
        <div class="form-group">
            <label for="descricao">Descrição:</label><br>
            <textarea id="descricao" name="descricao" rows="4" cols="50" required></textarea><br><br>
        </div>
        <button class="btn btn-primary" type="submit">Salvar Bug</button>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/bugs?sessao_id=${session.id}" class="btn btn-secondary">Voltar à lista</a>
</div>
</body>
</html>
