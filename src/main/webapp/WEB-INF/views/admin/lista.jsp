<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administradores</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1>Administradores</h1>

    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Voltar</a>
    <a href="${pageContext.request.contextPath}/admin/administradores/novo" class="btn btn-primary">Novo Administrador</a>

    <table class="table">
        <thead>
        <tr><th>Nome</th><th>E-mail</th><th>Ações</th></tr>
        </thead>
        <tbody>
        <!-- Itera sobre a lista de administradores e exibe os dados -->
        <c:forEach items="${admins}" var="admin">
            <tr>
                <td>${admin.nome}</td>
                <td>${admin.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/administradores/editar?id=${admin.id}" class="btn btn-primary">Editar</a>
                    <a href="${pageContext.request.contextPath}/admin/administradores/remover?id=${admin.id}" class="btn btn-danger">Remover</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
