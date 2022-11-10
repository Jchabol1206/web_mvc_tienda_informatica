package org.iesvegademijas.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Producto {
	private int codigo;
	private String nombre;
	private BigDecimal precio;
	private int codigoFabricante;
	
	public String getPrecioToString() {
		String precioStr="";
		precioStr=this.getPrecio().toString();
		return precioStr;
	}

	
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCodigoFabricante() {
		return codigoFabricante;
	}
	public void setCodigoFabricante(int codFab) {
		this.codigoFabricante = codFab;
	}
	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + ", codigoFabricante="
				+ codigoFabricante + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(codigo, codigoFabricante, nombre, precio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return codigo == other.codigo && codigoFabricante == other.codigoFabricante
				&& Objects.equals(nombre, other.nombre) && Objects.equals(precio, other.precio);
	}
	
	
}
