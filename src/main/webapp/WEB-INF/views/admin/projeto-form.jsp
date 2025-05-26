<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Gerenciar Projeto</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
</head>
<body>
<div class="container">
  <h1>
    <c:if test="${empty projeto.id}">Novo Projeto</c:if>
    <c:if test="${not empty projeto.id}">Editar Projeto</c:if>
  </h1>

  <form action="${pageContext.request.contextPath}/admin/projetos${not empty projeto.id ? '/atualizar' : '/inserir'}" method="POST">
    <input type="hidden" name="id" value="${projeto.id}">

    <div class="form-group">
      <label>Nome:</label>
      <input type="text" name="nome" value="${projeto.nome}" required>
    </div>

    <div class="form-group">
      <label>Descrição:</label>
      <textarea name="descricao" rows="4" required>${projeto.descricao}</textarea>
    </div>

    <div class="form-group">
      <label>Membros (Testers):</label>
      <div class="checkbox-group">
        <c:forEach items="${membros}" var="membro">
          <label>
            <input type="checkbox" name="membros" value="${membro.id}"
                   <c:if test="${projeto.membros.contains(membro.id)}">checked</c:if>
            >
              ${membro.nome} (${membro.email})
          </label><br>
        </c:forEach>
      </div>
    </div>

    <button type="submit" class="btn">Salvar</button>
    <a href="${pageContext.request.contextPath}/admin/projetos" class="btn-cancelar">Cancelar</a>
  </form>
</div>
</body>
</html>