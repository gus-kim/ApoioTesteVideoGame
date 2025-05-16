<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Exibe a barra superior apenas se o usuário estiver logado -->
<c:if test="${not empty sessionScope.usuarioLogado}">
    <div class="top-bar">
        Usuario: <strong>${sessionScope.usuarioLogado.nome}</strong> |
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</c:if>
