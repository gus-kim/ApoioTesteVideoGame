<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="titulo.sistema"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <h1><fmt:message key="index.titulo"/></h1>
    <div class="menu">
        <%-- Links para login e acesso visitante --%>
        <a href="${pageContext.request.contextPath}/login" class="btn"><fmt:message key="botao.login"/></a>
        <a href="${pageContext.request.contextPath}/visitante" class="btn"><fmt:message key="botao.visitante"/></a>
    </div>
</div>

</body>
</html>
