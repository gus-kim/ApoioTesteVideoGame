<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Testadores</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f2f2f2; }
        .actions { white-space: nowrap; }
        .button { padding: 5px 10px; text-decoration: none; color: white; border-radius: 3px; }
        .button.add { background-color: #4CAF50; }
        .button.edit { background-color: #2196F3; }
        .button.delete { background-color: #f44336; }
    </style>
</head>
<body>
<h1>Lista de Testadores</h1>

<a href="${pageContext.request.contextPath}/admin/testers/new" class="button add">Novo Testador</a>

<table>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Email</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="tester" items="${testers}">
        <tr>
            <td>${tester.id}</td>
            <td>${tester.nome}</td>
            <td>${tester.email}</td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/admin/testers/edit?id=${tester.id}" class="button edit">Editar</a>
                <a href="${pageContext.request.contextPath}/admin/testers/delete?id=${tester.id}" class="button delete" onclick="return confirm('Tem certeza que deseja excluir este testador?')">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>