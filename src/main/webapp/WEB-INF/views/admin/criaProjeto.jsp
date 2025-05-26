<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <title><fmt:message key="projeto.novo"/></title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/estilo.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
  <c:if test="${sessionScope.usuario == null || !sessionScope.usuario.admin}">
    <div class="error">
      <fmt:message key="erro.acesso.negado"/>
    </div>
  </c:if>

  <c:if test="${sessionScope.usuario.admin}">
    <h2><fmt:message key="projeto.novo"/></h2>

    <c:if test="${not empty param.error}">
      <div class="error">
        <fmt:message key="${param.error}"/>
      </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/criar-projeto" method="post">
      <div class="form-group">
        <label for="nome">
          <fmt:message key="projeto.nome"/> *
        </label>
        <input type="text" id="nome" name="nome" required
               maxlength="200" value="${param.nome}">
      </div>

      <div class="form-group">
        <label for="descricao">
          <fmt:message key="projeto.descricao"/>
        </label>
        <textarea id="descricao" name="descricao" rows="4">${param.descricao}</textarea>
      </div>

      <div class="form-group">
        <label for="membros">
          <fmt:message key="projeto.membros"/> *
        </label>
        <select id="membros" name="membros" multiple required
                class="select-multiplo">
          <c:forEach items="${testadores}" var="testador">
            <option value="${testador.id}">${testador.nome}</option>
          </c:forEach>
        </select>
      </div>

      <button type="submit" class="btn-primary">
        <fmt:message key="botao.salvar"/>
      </button>
    </form>
  </c:if>
</div>
</body>
</html>
