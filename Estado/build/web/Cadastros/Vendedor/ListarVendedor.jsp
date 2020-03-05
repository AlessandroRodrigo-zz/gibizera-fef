<%-- 
    Document   : ListarPessoa
    Created on : 29/05/2019, 10:08:12
    Author     : Flavio Prado
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
                <!--     Fonts and icons     -->
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
        <!-- CSS Files -->        
        <link href="./assets/css/material-dashboard.css?v=2.1.1" rel="stylesheet" />
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link href="./assets/demo/demo.css" rel="stylesheet" />

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consulta de Vendedor</title>
    </head>
    <body>
        <div class="container">
        <center><h3>Vendedores</h3></center>
        </div>
        <br><br>
    <center><div class="col-md-5">
            <div class="form-group">
                <input type="text" class="form-control" name="Sucesso" disabled placeholder="${Sucesso}">
            </div>
        </div></center>
    <center><a href="${pageContext.request.contextPath}/NovoVendedor" class="btn">Voltar</a></center>

    <center>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th hidden scope="col">ID Pessoa</th>
                    <th hidden scope="col">ID Cidade</th>
                    <th hidden scope="col">ID Estado</th>
                    <th scope="col">Vendedor</th>
                    <th scope="col">Situação</th>
                    <th scope="col">Observação</th>
                    <th scope="col">Cidade</th>
                    <th scope="col">CPF/CNPJ</th>
                    <th scope="col">Endereço</th>
                    <th scope="col">Número</th>
                    <th scope="col">Bairro</th>
                    <th scope="col">Telefone</th>
                    <th scope="col">Celular</th>
                    <th scope="col">Email</th>
                    <th scope="col">Excluir</th>
                    <th scope="col">Alterar</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="Vendedor" items="${Vendedors}">
                    <tr>
                        <td>${Vendedor.idVendedor}</td>
                        <td hidden >${Vendedor.idPessoa}</td>
                        <td hidden >${Vendedor.modelCidade.idCidade}</td>
                        <td hidden >${Vendedor.modelCidade.estado.idEstado}</td>
                        
                        <td>${Vendedor.nomePessoa}</td>
                        <td>${Vendedor.situacaoVendedor}</td>
                        <td>${Vendedor.observacaoVendedor}</td>
                        <td>${Vendedor.modelCidade.nomeCidade}</td>
                        <td>${Vendedor.cpfCnpjPessoa}</td>
                        <td>${Vendedor.enderecoPessoa}</td>
                        <td>${Vendedor.nroEnderecoPessoa}</td>
                        <td>${Vendedor.bairroPessoa}</td>
                        <td>${Vendedor.telefonePessoa}</td>
                        <td>${Vendedor.celularPessoa}</td>
                        <td>${Vendedor.emailPessoa}</td>
                        <td><a href="${pageContext.request.contextPath}/ExcluirVendedor?idVendedor=${Vendedor.idVendedor}">Ativar/Inativar</a></td>
                        <td><a href="${pageContext.request.contextPath}/CarregarVendedor?idVendedor=${Vendedor.idVendedor}">Alterar</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </center>
</body>
</html>
