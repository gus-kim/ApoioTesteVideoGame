<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Cadastro de Projeto</title>
  <style>
    .error { color: red; }
    .checkbox-group { margin: 10px 0; }
  </style>
  <script>
    function validarForm() {
      const nome = document.getElementById("nome").value;
      const descricao = document.getElementById("descricao").value;

      if (!nome || !descricao) {
        alert("Nome e Descrição são obrigatórios!");
        return false;
      }
      return true;
    }
  </script>
</head>
<body>
<h1>
  <c:choose>
    <c:when test="${not empty projeto.id}">Editar Projeto</c:when>
    <c:otherwise>Novo Projeto</c:otherwise>
  </c:choose>
</h1>

<c:if test="${not empty mensagens}">
  <div class="error">
    <ul>
      <c:forEach items="${mensagens.erros}" var="erro">
        <li>${erro}</li>
      </c:forEach>
    </ul>
  </div>
</c:if>

<form
        action="/admin/projetos/<c:choose><c:when test="${not empty projeto.id}">atualizar</c:when><c:otherwise>inserir</c:otherwise></c:choose>"
        method="post"
        onsubmit="return validarForm()"
>
  <c:if test="${not empty projeto.id}">
    <input type="hidden" name="id" value="${projeto.id}">
  </c:if>

  <div>
    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" value="${projeto.nome}" required>
  </div>

  <div>
    <label for="descricao">Descrição:</label>
    <textarea id="descricao" name="descricao" required>${projeto.descricao}</textarea>
  </div>

  <div class="checkbox-group">
    <h3>Membros do Projeto</h3>
    <c:forEach items="${membros}" var="membro">
      <label>
        <input
                type="checkbox"
                name="membros"
                value="${membro.id}"
                <c:if test="${projeto.membros.contains(membro.id)}">checked</c:if>
        >
          ${membro.nome} (${membro.email})
      </label><br>
    </c:forEach>
  </div>

  <button type="submit">Salvar</button>
  <a href="/admin/projetos">Cancelar</a>
</form>
</body>
</html>
