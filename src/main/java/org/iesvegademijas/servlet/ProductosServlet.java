package org.iesvegademijas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.FabricanteDAO;
import org.iesvegademijas.dao.FabricanteDAOImpl;

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
		RequestDispatcher dispatcher =null;
		
		String pathInfo = request.getPathInfo(); //
		if (pathInfo == null || "/".equals(pathInfo)) {
			//FabricanteDAO fabDAO = new FabricanteDAOImpl();
			
			//GET 
			//	/productos/
			//	/productos
			
			//request.setAttribute("listaFabricantes", fabDAO.getAll());		
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			
			}
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
