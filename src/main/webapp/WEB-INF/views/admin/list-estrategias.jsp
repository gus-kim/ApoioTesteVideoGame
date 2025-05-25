<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gerenciar Estratégias</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <h1>Gerenciar Estratégias</h1>
    <a href="${pageContext.request.contextPath}/estrategias" class="btn btn-secondary">Voltar</a>
    <a href="${pageContext.request.contextPath}/admin/estrategias/novo" class="btn btn-primary">Nova Estratégia</a>

    <table class="table">
        <thead>
        <tr>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Exemplos</th>
            <th>Dicas</th>
            <th>Imagem</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="estrategia" items="${estrategias}">
            <tr>
                <td>${estrategia.nome}</td>
                <td>${estrategia.descricao}</td>
                <td><pre>${estrategia.exemplos}</pre></td>
                <td><pre>${estrategia.dicas}</pre></td>
                <td>
                    <c:if test="${not empty estrategia.imagemUrl}">
                        <img src="${estrategia.imagemUrl}" alt="Imagem da estratégia" class="imagem-estrategia" />
                    </c:if>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/estrategias/editar?id=${estrategia.id}" class="btn btn-primary">Editar</a>
                    <a href="${pageContext.request.contextPath}/admin/estrategias/remover?id=${estrategia.id}" class="btn btn-danger"
                       onclick="return confirm('Tem certeza que deseja remover esta estratégia?');" >Remover</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
