<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <title><fmt:message key="projeto.titulo"/></title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/estilo.css">
  <script src="${pageContext.request.contextPath}/resources/js/projetos.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
  <c:choose>
    <c:when test="${sessionScope.usuario == null}">
      <div class="error">
        <fmt:message key="erro.acesso.nao.autenticado"/>
      </div>
    </c:when>

    <c:otherwise>
      <h2><fmt:message key="projeto.titulo"/></h2>

      <div class="filtros">
        <label><fmt:message key="projeto.ordenar.por"/>:</label>
        <select id="ordenacao" onchange="atualizarListagem()">
          <option value="nome"><fmt:message key="projeto.ordenar.nome"/></option>
          <option value="data_criacao"><fmt:message key="projeto.ordenar.data"/></option>
        </select>
      </div>

      <table id="tabela-projetos">
        <thead>
        <tr>
          <th><fmt:message key="projeto.nome"/></th>
          <th><fmt:message key="projeto.descricao"/></th>
          <th><fmt:message key="projeto.data.criacao"/></th>
          <c:if test="${sessionScope.usuario.admin}">
            <th><fmt:message key="acoes"/></th>
          </c:if>
        </tr>
        </thead>
        <tbody>
        <!-- Conteúdo dinâmico via AJAX -->
        </tbody>
      </table>

      <div id="sem-projetos" class="info" style="display: none;">
        <fmt:message key="projeto.nenhum.encontrado"/>
      </div>
    </c:otherwise>
  </c:choose>
</div>

<script>
  function atualizarListagem() {
    const ordenacao = document.getElementById('ordenacao').value;

    fetch('${pageContext.request.contextPath}/projetos?ordenarPor=' + ordenacao)
            .then(response => response.json())
            .then(projetos => {
              const tbody = document.querySelector('#tabela-projetos tbody');
              const infoDiv = document.getElementById('sem-projetos');

              tbody.innerHTML = '';
              infoDiv.style.display = 'none';

              if (projetos.length === 0) {
                infoDiv.style.display = 'block';
                return;
              }

              projetos.forEach(projeto => {
                const tr = document.createElement('tr');

                tr.innerHTML = `
                            <td>${projeto.nome}</td>
                            <td>${projeto.descricao}</td>
                            <td>${new Date(projeto.dataCriacao).toLocaleDateString()}</td>
                            ${sessionScope.usuario.admin ? `
                                <td>
                                    <a href="editar-projeto?id=${projeto.id}" class="btn-edit">
                                        <fmt:message key="botao.editar"/>
                                    </a>
                                </td>
                            ` : ''}
                        `;

                tbody.appendChild(tr);
              });
            });
  }

  // Carregar projetos ao iniciar
  document.addEventListener('DOMContentLoaded', atualizarListagem);
</script>
</body>
</html>

