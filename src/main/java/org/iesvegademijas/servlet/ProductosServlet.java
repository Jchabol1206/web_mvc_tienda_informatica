package org.iesvegademijas.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.FabricanteDAO;
import org.iesvegademijas.dao.FabricanteDAOImpl;
import org.iesvegademijas.dao.ProductoDAO;
import org.iesvegademijas.dao.ProductoDAOimpl;
import org.iesvegademijas.model.Fabricante;
import org.iesvegademijas.model.Producto;

/**
 * Servlet implementation class ProductosServlet
 */
@WebServlet("/productos/*")
public class ProductosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	
	/**
	 * HTTP Method: GET
	 * Paths: 
	 * 		/productos/{index} -muestra listado principal con operaciones CRUD
	 * 		/productos/{id}	- ver detalle de producto con id
	 * 		/productos/edit/{id}	- editar producto con {id}
	 * 		/productos/create	- crear un producto nuevo
	 */		
    public ProductosServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher;
		
		String pathInfo = request.getPathInfo(); //
			
		if (pathInfo == null || "/".equals(pathInfo)) {
			ProductoDAO prodDAO = new ProductoDAOimpl();
			
			//List<Producto> lista = prodDAO.getAll();
			String comp= request.getParameter("filtrar-por-nombre");
			
			
//			if(comp!=null) {
//				lista=lista.stream().filter(p->p.getNombre().toLowerCase().contains(comp)).collect(Collectors.toList());	
//			}
//			
				
	
			//GET 
			//	/fabricantes/
			//	/fabricantes
			if(comp==null) {
				request.setAttribute("listaProductos", prodDAO.getAll());
			}
			else {
				request.setAttribute("listaProductos", prodDAO.getAllFiltFT(comp));	
			}
			
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			        		       
		} else {
			// GET
			// 		/fabricantes/{id}
			// 		/fabricantes/{id}/
			// 		/fabricantes/edit/{id}
			// 		/fabricantes/edit/{id}/
			// 		/fabricantes/create
			// 		/fabricantes/create/
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				FabricanteDAO fabDAO = new FabricanteDAOImpl();
				// GET
				// /fabricantes/create
				request.setAttribute("listaFabricantes", fabDAO.getAll());	
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-producto.jsp");
        												
			
			} else if (pathParts.length == 2) {
				ProductoDAO prodDAO = new ProductoDAOimpl();
				// GET
				// /fabricantes/{id}
				try {
					request.setAttribute("producto",prodDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-producto.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
				}
				
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				ProductoDAO prodDAO = new ProductoDAOimpl();
				
				// GET
				// /fabricantes/edit/{id}
				try {
					request.setAttribute("producto",prodDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-producto.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
				}
				
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			
			}
			
		}
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		
		if (__method__ == null) {
			// Crear uno nuevo
			ProductoDAO prodDAO = new ProductoDAOimpl();
			
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			String codFab = request.getParameter("codigo_fabricante");
			Producto nuevoProd = new Producto();
			nuevoProd.setNombre(nombre);		//toDO Poner mas cosas para que pueda modificarse
			nuevoProd.setPrecio(BigDecimal.valueOf(Double.valueOf(precio)));
			nuevoProd.setCodigoFabricante(Integer.parseInt(codFab));
			prodDAO.create(nuevoProd);			
			
		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización PUT.
			doPut(request, response);
			
		
		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);
			
			
			
		} else {
			
			System.out.println("Opción POST no soportada.");
			
		}
		
		response.sendRedirect("/tienda_informatica/productos");
		//response.sendRedirect("/tienda_informatica/fabricantes");
	}
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ProductoDAO prodDAO = new ProductoDAOimpl();
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		String precio = request.getParameter("precio");
		String codFab = request.getParameter("codigo_fabricante");
		Producto prod = new Producto();
		
		try {
			
			int id = Integer.parseInt(codigo);
			prod.setCodigo(id);
			prod.setNombre(nombre);
			prod.setPrecio(BigDecimal.valueOf(Double.valueOf(precio)));
			prod.setCodigoFabricante(Integer.parseInt(codFab));
			prodDAO.update(prod);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		ProductoDAO ProdDAO = new ProductoDAOimpl();
		String codigo = request.getParameter("codigo");
		
		try {
			
			int id = Integer.parseInt(codigo);
		
		ProdDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	

}
