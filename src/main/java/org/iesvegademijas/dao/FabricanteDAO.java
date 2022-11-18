package org.iesvegademijas.dao;

import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Fabricante;
import org.iesvegademijas.model.FabricanteDto;

public interface FabricanteDAO {
		
	public void create(Fabricante fabricante);
	
	public List<Fabricante> getAll();
	public List<FabricanteDto> getAllDTOPlusCountProductos();
	public List<FabricanteDto> getAllSorted(String por, String mod);
	public Optional<Fabricante>  find(int id);
	
	public void update(Fabricante fabricante);
	
	public void delete(int id);
	
	//ampliacion del crud
	
	public Optional<Integer> getCountProductos(int id);

}
