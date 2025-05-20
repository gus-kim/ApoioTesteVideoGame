<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${estrategia.id == null}">Nova Estratégia</c:when>
            <c:otherwise>Editar Estratégia</c:otherwise>
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
            <c:when test="${estrategia.id == null}">Nova Estratégia</c:when>
            <c:otherwise>Editar Estratégia</c:otherwise>
        </c:choose>
    </h1>

    <a href="${pageContext.request.contextPath}/admin/estrategias" class="btn btn-secondary">Voltar</a>

    <form action="${pageContext.request.contextPath}/admin/estrategias/${estrategia.id == null ? 'inserir' : 'atualizar'}" method="post" novalidate>

        <c:if test="${estrategia.id != null}">
            <input type="hidden" name="id" value="${estrategia.id}" />
        </c:if>

        <div class="form-group">
            <label for="nome">Nome:</label>
            <input id="nome" name="nome" type="text" value="${estrategia.nome}" required />
        </div>

        <div class="form-group">
            <label for="descricao">Descrição:</label>
            <textarea id="descricao" name="descricao">${estrategia.descricao}</textarea>
        </div>

        <div class="form-group">
            <label for="exemplos">Exemplos:</label>
            <textarea id="exemplos" name="exemplos">${estrategia.exemplos}</textarea>
        </div>

        <div class="form-group">
            <label for="dicas">Dicas:</label>
            <textarea id="dicas" name="dicas">${estrategia.dicas}</textarea>
        </div>

        <div class="form-group">
            <label for="imagemUrl">Imagem (URL):</label>
            <input id="imagemUrl" name="imagemUrl" type="text" value="${estrategia.imagemUrl}" />
        </div>

        <button type="submit" class="btn">
            <c:choose>
                <c:when test="${estrategia.id == null}">Criar</c:when>
                <c:otherwise>Atualizar</c:otherwise>
            </c:choose>
        </button>
    </form>
</div>
</body>
</html>
