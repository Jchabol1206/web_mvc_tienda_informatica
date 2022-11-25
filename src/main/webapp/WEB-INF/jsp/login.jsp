<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Producto"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
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
<body>
	<%@include file="header.jspf" %>
	<%@include file="nav.jspf" %>
	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Login</h1>
			</div>
		</div>
		
	

		<div style="margin-top: 6px;" class="clearfix">
			<form action="/tienda_informatica/usuarios/login/" method="post">
			<input type="hidden" name="__method__" value="log" />
			<div style="float: left;width: 50%">
				<label>Nombre</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="nombre"/>
			</div>
			<div style="float: left;width: 50%">
				<label>Contraseña</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="contrasena"/>
				<br>
				<input type="submit" value="Login"/>		
			</div>
			</form>
		</div>


	</div>
<%@include file="footer.jspf" %>
</body>
</html>