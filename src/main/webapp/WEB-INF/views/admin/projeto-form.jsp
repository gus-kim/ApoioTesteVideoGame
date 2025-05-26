<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Gerenciar Projeto</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
  <h1>
    <c:choose>
      <c:when test="${empty projeto.id}">Novo Projeto</c:when>
      <c:otherwise>Editar Projeto</c:otherwise>
    </c:choose>
  </h1>

  <form action="${pageContext.request.contextPath}/admin/projetos${not empty projeto.id ? '/atualizar' : '/inserir'}" method="POST">
    <c:if test="${not empty projeto.id}">
      <input type="hidden" name="id" value="${projeto.id}">
    </c:if>

    <div class="form-group">
      <label for="nome">Nome:</label>
      <input type="text" id="nome" name="nome" value="${projeto.nome}"
             class="form-control" required>
    </div>

    <div class="form-group">
      <label for="descricao">Descrição:</label>
      <textarea id="descricao" name="descricao" rows="4"
                class="form-control" required>${projeto.descricao}</textarea>
    </div>

    <div class="form-group">
      <label>Membros (Testers):</label>
      <div class="checkbox-list"> <!-- Classe modificada -->
        <c:forEach items="${membros}" var="membro">
          <div class="checkbox-item"> <!-- Item como bloco -->
            <label>
              <input type="checkbox" name="membros" value="${membro.id}"
                ${projeto.membros.contains(membro.id) ? 'checked' : ''}>
                ${membro.nome} (${membro.email})
            </label>
          </div>
        </c:forEach>
      </div>
    </div>

    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Salvar</button>
      <a href="${pageContext.request.contextPath}/admin/projetos"
         class="btn btn-secondary">Cancelar</a>
    </div>
  </form>
</div>
</body>
</html>