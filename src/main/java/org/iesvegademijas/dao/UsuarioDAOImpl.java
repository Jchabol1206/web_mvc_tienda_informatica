package org.iesvegademijas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Fabricante;
import org.iesvegademijas.model.Usuario;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO{

	@Override
	public void create(Usuario usuario) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
        	conn = connectDB();


        	//1 alternativas comentadas:       
        	//ps = conn.prepareStatement("INSERT INTO fabricante (nombre) VALUES (?)", new String[] {"codigo"});        	
        	//Ver también, AbstractDAOImpl.executeInsert ...
        	//Columna fabricante.codigo es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente. 
        	ps = conn.prepareStatement("INSERT INTO usuario (nombre, contrasena, rol) VALUES (?,SHA2(?, 256),?)", Statement.RETURN_GENERATED_KEYS);
            
            int idx = 1;
            ps.setString(idx++, usuario.getNombre());
            ps.setString(idx++, usuario.getContrasena());
            ps.setString(idx, usuario.getRol());
                   
            int rows = ps.executeUpdate();
            if (rows == 0) 
            	System.out.println("INSERT de usuario con 0 filas insertadas.");
            
            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) 
            	usuario.setCodigo(rsGenKeys.getInt(1));
                      
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
	}
		
	

	@Override
	public List<Usuario> getAll() {
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
        List<Usuario> listUsu = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	s = conn.createStatement();
            		
        	rs = s.executeQuery("SELECT * FROM usuario");          
            while (rs.next()) {
            	Usuario usu = new Usuario();
            	int idx = 1;
            	usu.setCodigo(rs.getInt(idx++));
            	usu.setNombre(rs.getString(idx));
            	usu.setRol(rs.getString("rol"));
            	listUsu.add(usu);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
        return listUsu;
	}

	@Override
	public Optional<Usuario> find(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT * FROM usuario WHERE codigo = ?");
        	
        	int idx =  1;
        	ps.setInt(idx, id);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		Usuario usu = new Usuario();
        		idx = 1;
        		usu.setCodigo(rs.getInt(idx++));
        		usu.setNombre(rs.getString(idx++));
        		usu.setRol(rs.getString("rol"));
        		
        		return Optional.of(usu);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
        return Optional.empty();
	}

	@Override
	public void update(Usuario usuario) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("UPDATE usuario SET nombre = ?, contrasena = SHA2(?, 256), rol = ?    WHERE codigo = ?");
        	int idx = 1;
        	
        	ps.setString(idx++, usuario.getNombre());
        	ps.setString(idx++, usuario.getContrasena());
        	ps.setString(idx++, usuario.getRol());
        	ps.setInt(idx, usuario.getCodigo());
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Update de usuario con 0 registros actualizados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
	}

	@Override
	public void delete(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("DELETE FROM usuario WHERE codigo = ?");
        	int idx = 1;        	
        	ps.setInt(idx, id);
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Delete de usuario con 0 registros eliminados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
	}



	@Override
	public boolean login(Usuario usuario) {
		boolean response=false;
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("select * from usuario where usuario.nombre= ? and usuario.contrasena=SHA2(?, 256)");
        	int idx = 1;
        
        	
        	ps.setString(idx++, usuario.getNombre());
        	ps.setString(idx++, usuario.getContrasena());
        	
        	rs = ps.executeQuery();
        	
        	if(rs.next()){
        		response=true;
        	} 
        
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
		return response;
		
	}

}
