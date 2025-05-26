<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="estrategia.titulo"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <h1><fmt:message key="estrategia.titulo"/></h1>
    <a href="${pageContext.request.contextPath}/estrategias" class="btn btn-secondary"><fmt:message key="botao.voltar"/></a>
    <a href="${pageContext.request.contextPath}/admin/estrategias/novo" class="btn btn-primary"><fmt:message key="estrategia.novo"/></a>

    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="estrategia.nome"/></th>
            <th><fmt:message key="estrategia.descricao"/></th>
            <th><fmt:message key="estrategia.exemplos"/></th>
            <th><fmt:message key="estrategia.dicas"/></th>
            <th><fmt:message key="estrategia.imagem"/></th>
            <th><fmt:message key="acoes"/></th>
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
                    <a href="${pageContext.request.contextPath}/admin/estrategias/editar?id=${estrategia.id}" class="btn btn-primary"><fmt:message key="botao.editar"/></a>
                    <a href="${pageContext.request.contextPath}/admin/estrategias/remover?id=${estrategia.id}" class="btn btn-danger"
                       onclick="return confirm('<fmt:message key="estrategia.confirmar.remover"/>');" ><fmt:message key="botao.remover"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
