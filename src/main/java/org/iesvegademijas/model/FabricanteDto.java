package org.iesvegademijas.model;

import java.util.Optional;

public class FabricanteDto extends Fabricante{
	private Optional<Integer> numProds;

	public Optional<Integer> getNumProds() {
		return numProds;
	}

	public void setNumProds(Optional<Integer> numProds) {
		this.numProds = numProds;
	}
}
