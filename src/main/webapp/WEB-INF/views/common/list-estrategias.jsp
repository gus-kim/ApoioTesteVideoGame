<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Estratégias Disponíveis</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <h1>Estratégias Disponíveis</h1>

    <p>
        Papel do usuário logado: <strong>${sessionScope.usuarioLogado != null ? sessionScope.usuarioLogado.papel : "Nenhum usuário logado"}</strong>
    </p>

    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Voltar</a>
    <c:if test="${sessionScope.usuarioLogado != null && sessionScope.usuarioLogado.papel == 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/admin/estrategias" class="btn btn-primary modo-admin-btn">
            Modo Administração
        </a>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Exemplos</th>
            <th>Dicas</th>
            <th>Imagem</th>
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
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
