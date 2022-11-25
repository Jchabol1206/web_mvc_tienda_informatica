package org.iesvegademijas.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.iesvegademijas.dao.FabricanteDAO;
import org.iesvegademijas.dao.FabricanteDAOImpl;
import org.iesvegademijas.dao.ProductoDAO;
import org.iesvegademijas.dao.ProductoDAOimpl;
import org.iesvegademijas.dao.UsuarioDAO;
import org.iesvegademijas.dao.UsuarioDAOImpl;
import org.iesvegademijas.model.Producto;
import org.iesvegademijas.model.Usuario;

@WebServlet("/usuarios/*")
public class UsuariosServlet extends HttpServlet{
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
    public UsuariosServlet() {
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
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
		
				
	

			request.setAttribute("listaUsuarios",usuDAO.getAll());
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
			        		       
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
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				// GET
				// /fabricantes/create
				request.setAttribute("listaUsuarios", usuDAO.getAll());	
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-usuario.jsp");
        												
			
			} else if (pathParts.length == 2) {
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				// GET
				// /fabricantes/{id}
				try {
					if (pathParts.length== 2 && "login".equals(pathParts[1])) {
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
					}
					else {
						request.setAttribute("usuario",usuDAO.find(Integer.parseInt(pathParts[1])));
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-usuario.jsp");	
					}
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}
				
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				
				// GET
				// /fabricantes/edit/{id}
				try {
					request.setAttribute("usuario",usuDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-usuario.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}
				
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
			
			}
			
		}
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	//toDO
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		HttpSession session=null;
		boolean logout=false;
		
		if (__method__ == null) {
			// Crear uno nuevo
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
			String nombre = request.getParameter("nombre");
			String contrasena = request.getParameter("contrasena");
			String rol = request.getParameter("rol");
			Usuario nuevoUsu = new Usuario();
			nuevoUsu.setNombre(nombre);		//toDO Poner mas cosas para que pueda modificarse
			nuevoUsu.setContrasena(contrasena);
			nuevoUsu.setRol(rol);
			usuDAO.create(nuevoUsu);			
			
		} else if(__method__ != null && "log".equalsIgnoreCase(__method__)) {
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
			String nombre = request.getParameter("nombre");
			String contrasena = request.getParameter("contrasena");
			Usuario usu = new Usuario();
			usu.setNombre(nombre);
			usu.setContrasena(contrasena);
			boolean sesion = usuDAO.login(usu);
			
			if(sesion) {
				session=request.getSession(true);
				List<Usuario> usuList = usuDAO.getAll();
				for(Usuario usuario:usuList) {
					if(usuario.getNombre().equals(usu.getNombre())) {
						usu.setRol(usuario.getRol());
						}
					}	
				session.setAttribute("usuario-logado", usu);
				System.out.println(session.getAttribute("usuario-logado"));
			}
			
		}	else if(__method__!=null && "logout".equalsIgnoreCase(__method__)){
			HttpSession session2=request.getSession();
			session2.invalidate();
			logout=true;
			
			
		}
			else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {			
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
		
		if(session!=null || logout==true) {
			response.sendRedirect("/tienda_informatica/index.jsp");
			
		}else {
			response.sendRedirect("/tienda_informatica/usuarios");
		}
		
		//response.sendRedirect("/tienda_informatica/fabricantes");
	}
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		String contrasena = request.getParameter("contrasena");
		String rol = request.getParameter("rol");
		Usuario usu = new Usuario();
		
		try {
			
			int id = Integer.parseInt(codigo);
			usu.setCodigo(id);
			usu.setNombre(nombre);
			usu.setContrasena(contrasena);
			usu.setRol(rol);
			usuDAO.update(usu);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		String codigo = request.getParameter("codigo");
		
		try {
			
			int id = Integer.parseInt(codigo);
		
		usuDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	

}
