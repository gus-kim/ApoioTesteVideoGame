<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Projetos</title>
  <script>
    function atualizarListagem(sort) {
      fetch('/admin/projetos?ajax=true&sort=' + sort)
              .then(response => response.json())
              .then(data => {
                const tbody = document.getElementById('projetos-body');
                tbody.innerHTML = '';
                data.forEach(projeto => {
                  const row = `
                            <tr>
                                <td>${projeto.nome}</td>
                                <td>${projeto.descricao}</td>
                                <td>${projeto.dataCriacao}</td>
                                <td>
                                    <a href="/admin/projetos/editar?id=${projeto.id}">Editar</a>
                                    <a href="/admin/projetos/remover?id=${projeto.id}">Remover</a>
                                </td>
                            </tr>
                        `;
                  tbody.insertAdjacentHTML('beforeend', row);
                });
              });
    }
  </script>
</head>
<body>
<h1>Gerenciar Projetos</h1>
<a href="/admin/projetos/novo">Novo Projeto</a>

<select onchange="atualizarListagem(this.value)">
  <option value="nome">Ordenar por Nome</option>
  <option value="dataCriacao">Ordenar por Data</option>
</select>

<table>
  <thead>
  <tr>
    <th>Nome</th>
    <th>Descrição</th>
    <th>Data de Criação</th>
    <th>Ações</th>
  </tr>
  </thead>
  <tbody id="projetos-body">
  <c:forEach items="${projetos}" var="projeto">
    <tr>
      <td>${projeto.nome}</td>
      <td>${projeto.descricao}</td>
      <td>${projeto.dataCriacao}</td>
      <td>
        <a href="/admin/projetos/editar?id=${projeto.id}">Editar</a>
        <a href="/admin/projetos/remover?id=${projeto.id}">Remover</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
