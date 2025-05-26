<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lista de Projetos</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/list.css">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
  <h1>Projetos</h1>
  <div class="button-group">
    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Voltar</a>
    <a href="${pageContext.request.contextPath}/admin/projetos/novo" class="btn btn-primary">Novo Projeto</a>
  </div>

  <select class="sort-select">
    <option value="">Ordenar por</option>
    <option value="nome">Nome</option>
    <option value="dataCriacao">Data de Criação</option>
  </select>

  <table class="table">
    <thead>
    <tr>
      <th>Nome</th>
      <th>Descrição</th>
      <th>Data de Criação</th>
      <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${projetos}" var="projeto">
      <tr>
        <td>${projeto.nome}</td>
        <td>${projeto.descricao}</td>
        <td><fmt:formatDate value="${projeto.dataCriacao}" pattern="dd/MM/yyyy HH:mm"/></td>
        <td>
          <a href="${pageContext.request.contextPath}/admin/projetos/editar?id=${projeto.id}"
             class="btn btn-secondary">Editar</a>
          <a href="${pageContext.request.contextPath}/admin/projetos/remover?id=${projeto.id}"
             class="btn btn-danger"
             onclick="return confirm('Tem certeza?')">Excluir</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<script>
  $(document).ready(function() {
    $(".sort-select").change(function() {
      const sort = $(this).val();
      if (sort) {
        $.ajax({
          url: "${pageContext.request.contextPath}/admin/projetos",
          data: { sort: sort, ajax: "true" },
          success: function(data) {
            const projetos = JSON.parse(data);
            let html = '';
            projetos.forEach(projeto => {
              const dataCriacao = new Date(projeto.dataCriacao).toLocaleString('pt-BR');
              html += `
              <tr>
                  <td>${projeto.nome}</td>
                  <td>${projeto.descricao}</td>
                  <td>${dataCriacao}</td>
                  <td>
                      <a href="${pageContext.request.contextPath}/admin/projetos/editar?id=${projeto.id}"
                         class="btn btn-secondary">Editar</a>
                      <a href="${pageContext.request.contextPath}/admin/projetos/remover?id=${projeto.id}"
                         class="btn btn-danger"
                         onclick="return confirm('Tem certeza?')">Excluir</a>
                  </td>
              </tr>`;
            });
            $(".table tbody").html(html);
          }
        });
      }
    });
  });
</script>
</body>
</html>