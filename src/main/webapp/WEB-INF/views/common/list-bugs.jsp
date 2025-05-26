<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Lista de Bugs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sessions.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<c:set var="usuario" value="${sessionScope.usuarioLogado}" />

<div class="container">
    <h1>Bugs da Sessão ${session.id}</h1>
    <a href="${pageContext.request.contextPath}/admin/sessions" class="btn btn-secondary">Voltar</a>

    <c:if test="${usuario.papel == 'ADMIN' || usuario.papel == 'TESTADOR'}">
        <a href="${pageContext.request.contextPath}/bugs/add?sessao_id=${session.id}" class="btn btn-primary">
            Registrar Bug
        </a>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Descrição</th>
            <th>Data de Registro</th>
            <c:if test="${usuario.papel == 'ADMIN' || usuario.papel == 'TESTADOR'}">
                <th>Ações</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bug" items="${bugs}">
            <tr>
                <td>${bug.id}</td>
                <td>${bug.descricao}</td>
                <td>
                    <tags:localDate date="${bug.dataRegistro}" />
                </td>
                <c:if test="${usuario.papel == 'ADMIN' || usuario.papel == 'TESTADOR'}">
                    <td>
                        <button class="btn btn-danger"
                                onclick="confirmarRemocao(${bug.id})">
                            Remover
                        </button>

                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
<script>
    function confirmarRemocao(bugId) {
        if (confirm("Tem certeza que deseja remover este bug?")) {
            window.location.href = `${pageContext.request.contextPath}/bugs/remover?id=${bugId}`;
        }
    }
</script>

</html>
