package poke.api.models;

import java.util.List;

public class PokemonDetalle extends Pokemon {

	private List<String> listaMovimientos;
	private List<String> listaTipos;

	public List<String> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<String> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public List<String> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<String> listaTipos) {
		this.listaTipos = listaTipos;
	}

}
