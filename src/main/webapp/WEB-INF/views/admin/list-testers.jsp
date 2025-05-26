<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="tester.titulo"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1><fmt:message key="tester.titulo"/></h1>

    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary"><fmt:message key="botao.voltar"/></a>
    <a href="${pageContext.request.contextPath}/admin/testers/novo" class="btn btn-primary"><fmt:message key="tester.novo"/></a>

    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="admin.nome"/></th>
            <th><fmt:message key="admin.email"/></th>
            <th><fmt:message key="acoes"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tester" items="${testers}">
            <tr>
                <td>${tester.nome}</td>
                <td>${tester.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/testers/editar?id=${tester.id}" class="btn btn-primary"><fmt:message key="botao.editar"/></a>
                    <a href="${pageContext.request.contextPath}/admin/testers/remover?id=${tester.id}" class="btn btn-danger"
                       onclick="return confirm('Tem certeza que deseja remover este testador?');"><fmt:message key="botao.remover"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
