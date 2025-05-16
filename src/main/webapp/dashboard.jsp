<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1>Dashboard</h1>

    <!-- Menu principal com links para as funcionalidades -->
    <ul class="menu">
        <li><a href="${pageContext.request.contextPath}/admin/administradores" class="btn">Administradores</a></li>
    </ul>
</div>

</body>
</html>
