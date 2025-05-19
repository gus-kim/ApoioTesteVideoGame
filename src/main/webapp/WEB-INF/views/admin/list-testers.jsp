<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Testadores</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1>Testadores</h1>

    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Voltar</a>
    <a href="${pageContext.request.contextPath}/admin/testers/novo" class="btn btn-primary">Novo Testador</a>

    <table class="table">
        <thead>
        <tr>
            <th>Nome</th>
            <th>E-mail</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tester" items="${testers}">
            <tr>
                <td>${tester.nome}</td>
                <td>${tester.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/testers/editar?id=${tester.id}" class="btn btn-primary">Editar</a>
                    <a href="${pageContext.request.contextPath}/admin/testers/remover?id=${tester.id}" class="btn btn-danger"
                       onclick="return confirm('Tem certeza que deseja remover este testador?');">Remover</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
