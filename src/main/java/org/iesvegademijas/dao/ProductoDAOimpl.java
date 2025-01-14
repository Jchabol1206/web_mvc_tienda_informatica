package org.iesvegademijas.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Fabricante;
import org.iesvegademijas.model.Producto;

public class ProductoDAOimpl extends AbstractDAOImpl implements ProductoDAO{
	
	/**
	 * Inserta en base de datos el nuevo fabricante, actualizando el id en el bean fabricante.
	 */
	@Override	
	public synchronized void create(Producto producto) {
		
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
        	ps = conn.prepareStatement("INSERT INTO producto (nombre, precio, codigo_fabricante) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
        	//, 
            int idx = 1;
            ps.setString(idx++, producto.getNombre());
            ps.setBigDecimal(idx++, producto.getPrecio());
            ps.setInt(idx++, producto.getCodigoFabricante());
            
            
                   
            int rows = ps.executeUpdate();
            if (rows == 0) 
            	System.out.println("INSERT de producto con 0 filas insertadas.");
            
            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) 
            	producto.setCodigo(rsGenKeys.getInt(1));
                      
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
	}

	/**
	 * Devuelve lista con todos loa fabricantes.
	 */
	@Override
	public List<Producto> getAll() {
		
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
        List<Producto> listProd = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	s = conn.createStatement();
            		
        	rs = s.executeQuery("SELECT * FROM producto");          
            while (rs.next()) {
            	Producto prod = new Producto();
            	int idx = 1;
            	prod.setCodigo(rs.getInt(idx++));
            	prod.setNombre(rs.getString(idx++));
            	prod.setPrecio(BigDecimal.valueOf(rs.getDouble(idx)));
            	listProd.add(prod);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
        return listProd;
        
	}

	/**
	 * Devuelve Optional de fabricante con el ID dado.
	 */
	@Override
	public Optional<Producto> find(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT * FROM producto WHERE codigo = ?");
        	
        	int idx =  1;
        	ps.setInt(idx, id);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		Producto prod = new Producto();
        		idx = 1;
        		prod.setCodigo(rs.getInt(idx++));
        		prod.setNombre(rs.getString(idx++));
        		prod.setPrecio(rs.getBigDecimal(idx++));
        		prod.setCodigoFabricante(rs.getInt(idx));
        		
        		return Optional.of(prod);
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
	/**
	 * Actualiza fabricante con campos del bean fabricante según ID del mismo.
	 */
	@Override
	public void update(Producto producto) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("UPDATE producto SET nombre = ?, precio = ?, codigo_fabricante = ?    WHERE codigo = ?");
        	int idx = 1;
        	
        	ps.setString(idx++, producto.getNombre());
        	ps.setDouble(idx++, Double.valueOf(producto.getPrecioToString()));
        	ps.setInt(idx++, producto.getCodigoFabricante());
        	ps.setInt(idx, producto.getCodigo());
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Update de producto con 0 registros actualizados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
    
	}

	/**
	 * Borra fabricante con ID proporcionado.
	 */
	@Override
	public void delete(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("DELETE FROM producto WHERE codigo = ?");
        	int idx = 1;        	
        	ps.setInt(idx, id);
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Delete de producto con 0 registros eliminados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
	}

	@Override
	public List<Producto> getAllFilt(String busq) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Producto> listProd = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	ps = conn.prepareStatement("select * from producto where producto.nombre like '%' ? '%';");
        	int idx =  1;
        	ps.setString(idx, busq);
            		
        	rs = ps.executeQuery();          
            while (rs.next()) {
            	int idz=1;
            	Producto prod = new Producto();
            	prod.setCodigo(rs.getInt(idz++));
            	prod.setNombre(rs.getString(idz++));
            	prod.setPrecio(BigDecimal.valueOf(rs.getDouble(idz)));
            	listProd.add(prod);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        return listProd;
        
	}

	@Override
	public List<Producto> getAllFiltFT(String busq) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Producto> listProd = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	ps = conn.prepareStatement("select * from producto where match(nombre) against( ?  in boolean mode);");
        	int idx =  1;
        	busq+="*";
        	ps.setString(idx, busq);
            		
        	rs = ps.executeQuery();          
            while (rs.next()) {
            	int idz=1;
            	Producto prod = new Producto();
            	prod.setCodigo(rs.getInt(idz++));
            	prod.setNombre(rs.getString(idz++));
            	prod.setPrecio(BigDecimal.valueOf(rs.getDouble(idz)));
            	listProd.add(prod);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        return listProd;
	}

}
