<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sistema de Testes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1>Bem-vindo!</h1>
    <div class="menu">
        <%-- Links para login e acesso visitante --%>
        <a href="${pageContext.request.contextPath}/login" class="btn">Login</a>
        <a href="${pageContext.request.contextPath}/visitante" class="btn">Visitante</a>
    </div>
</div>

</body>
</html>
