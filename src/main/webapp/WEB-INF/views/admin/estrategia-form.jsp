<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${estrategia.id == null}"><fmt:message key="estrategia.novo"/></c:when>
            <c:otherwise><fmt:message key="estrategia.editar"/></c:otherwise>
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
            <c:when test="${estrategia.id == null}">N<fmt:message key="estrategia.novo"/></c:when>
            <c:otherwise><fmt:message key="estrategia.editar"/></c:otherwise>
        </c:choose>
    </h1>

    <a href="${pageContext.request.contextPath}/admin/estrategias" class="btn btn-secondary"><fmt:message key="botao.voltar"/></a>

    <form action="${pageContext.request.contextPath}/admin/estrategias/${estrategia.id == null ? 'inserir' : 'atualizar'}" method="post" novalidate>

        <c:if test="${estrategia.id != null}">
            <input type="hidden" name="id" value="${estrategia.id}" />
        </c:if>

        <div class="form-group">
            <label for="nome"><fmt:message key="estrategia.nome"/>:</label>
            <input id="nome" name="nome" type="text" value="${estrategia.nome}" required />
        </div>

        <div class="form-group">
            <label for="descricao"><fmt:message key="estrategia.descricao"/>:</label>
            <textarea id="descricao" name="descricao">${estrategia.descricao}</textarea>
        </div>

        <div class="form-group">
            <label for="exemplos"><fmt:message key="estrategia.exemplos"/>:</label>
            <textarea id="exemplos" name="exemplos">${estrategia.exemplos}</textarea>
        </div>

        <div class="form-group">
            <label for="dicas"><fmt:message key="estrategia.dicas"/>:</label>
            <textarea id="dicas" name="dicas">${estrategia.dicas}</textarea>
        </div>

        <div class="form-group">
            <label for="imagemUrl"><fmt:message key="estrategia.imagem"/>:</label>
            <input id="imagemUrl" name="imagemUrl" type="text" value="${estrategia.imagemUrl}" />
        </div>

        <button type="submit" class="btn">
            <c:choose>
                <c:when test="${estrategia.id == null}"><fmt:message key="botao.criar"/></c:when>
                <c:otherwise><fmt:message key="botao.atualizar"/></c:otherwise>
            </c:choose>
        </button>
    </form>
</div>
</body>
</html>
