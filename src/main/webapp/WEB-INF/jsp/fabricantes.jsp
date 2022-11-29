<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.FabricanteDto"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fabricantes</title>
<style>
.clearfix::after {
	content: "";
	display: block;
	clear: both;
}
<style>
		.head1 {
			font-size:40px;
			color:#009900;
			font-weight:bold;
		}
		.head2 {
			font-size:17px;
			margin-left:10px;
			margin-bottom:15px;
		}
		body {
			margin: 0 auto;
			background-position:center;
			background-size: contain;
		}
	
		.menu {
			position: sticky;
			top: 0;
			background-color: #009900;
			padding:10px 0px 10px 0px;
			color:white;
			margin: 0 auto;
			overflow: hidden;
		}
		.menu a {
			float: left;
			color: white;
			text-align: center;
			padding: 14px 16px;
			text-decoration: none;
			font-size: 20px;
		}
		.menu-log {
			right: auto;
			float: right;
		}
		footer {
			width: 100%;
			bottom: 0px;
			background-color: #000;
			color: #fff;
			position: absolute;
			padding-top:20px;
			padding-bottom:50px;
			text-align:center;
			font-size:30px;
			font-weight:bold;
		}
		.body_sec {
			margin-left:20px;
		}
	</style>

</style>
</head>
<body>
	<%@ include file="header.jspf" %>
	<%@ include file="nav.jspf" %>

	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Fabricantes</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
					<form action="/tienda_informatica/fabricantes/">
						<select name="ordenar-por">
							<option value="nombre">Ordenar por nombre</option>
							<option value="cod">Ordenar por codigo</option>
							</select>
							<select name="modo-ordenar">
							<option value="asc">Ordenar ascendente</option>
							<option value="des">Ordenar descentente</option>
							</select>
							
							<input type="submit" value="Ordenar">
					</form>
				<div style="position: absolute; left: 39%; top : 39%;">
						
						<%
					Usuario usuario2 = (Usuario)request.getSession().getAttribute("usuario-logado");
					if(usuario2 != null //Seteo inline de usuario
							&& "administrador".equals(usuario1.getRol())){
						%>
						<form action="/tienda_informatica/productos/crear">
							<input type="submit" value="Crear">
						</form>
					<%}%>
					</div>
				
			</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 25%">Código</div>
			<div style="float: left;width: 25%">Nombre</div>
			<div style="float: left;width: 25%">#Productos</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
        if (request.getAttribute("listaFabricantes") != null) {
            List<FabricanteDto> listaFabricante = (List<FabricanteDto>)request.getAttribute("listaFabricantes");
            
            for (FabricanteDto fabricante : listaFabricante) {
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%"><%= fabricante.getCodigo()%></div>
			<div style="float: left;width: 25%"><%= fabricante.getNombre()%></div>
			<div style="float: left;width: 25%"><%= fabricante.getNumProds().get()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/fabricantes/<%= fabricante.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<% 
			if(usuario2 != null //Seteo inline de usuario
					&& "administrador".equals(usuario1.getRol())){
				%>
				<form action="/tienda_informatica/productos/editar/<%= fabricante.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/productos/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="codigo" value="<%= fabricante.getCodigo()%>"/>
    				<input type="submit" value="Eliminar" />
				</form>
			<% }
			
			%>
			</div>
		</div>

	<% 
            }
        } else { 
    %>
		No hay registros de fabricante
	<% } %>
	</div>
	<%@ include file="footer.jspf"%>
</body>
</html>