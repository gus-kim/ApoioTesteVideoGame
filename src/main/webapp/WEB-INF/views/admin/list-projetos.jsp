<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lista de Projetos</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container">
  <h1>Projetos</h1>
  <a href="${pageContext.request.contextPath}/dashboard" class="btn">Voltar</a>
  <a href="${pageContext.request.contextPath}/admin/projetos/novo" class="btn">Novo Projeto</a>

  <!-- Dropdown básico usando select -->
  <select class="sort-select" style="margin: 15px 0; padding: 5px;">
    <option value="">Ordenar por</option>
    <option value="nome">Nome</option>
    <option value="dataCriacao">Data de Criação</option>
  </select>

  <!-- Tabela de Projetos -->
  <table id="tabela-projetos">
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
          <a href="${pageContext.request.contextPath}/admin/projetos/editar?id=${projeto.id}" class="btn-editar">Editar</a>
          <a href="${pageContext.request.contextPath}/admin/projetos/remover?id=${projeto.id}"
             class="btn-excluir" onclick="return confirm('Tem certeza?')">Excluir</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<script>
  $(document).ready(function() {
    // Ordenação via select
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
                      <a href="${pageContext.request.contextPath}/admin/projetos/editar?id=${projeto.id}">Editar</a>
                      <a href="${pageContext.request.contextPath}/admin/projetos/remover?id=${projeto.id}"
                         onclick="return confirm('Tem certeza?')">Excluir</a>
                  </td>
              </tr>`;
            });
            $("#tabela-projetos tbody").html(html);
          }
        });
      }
    });
  });
</script>
</body>
</html>