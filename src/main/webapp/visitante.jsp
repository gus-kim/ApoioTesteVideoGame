<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Visitante</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/visitante.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1>Modo Visitante</h1>
    <p>Você está acessando como visitante.</p>
    <a href="${pageContext.request.contextPath}/index" class="btn btn-secondary">Voltar</a>
</div>

</body>
</html>
