package poke.api.models;
import java.util.List;

public class PaginaPokemon {

	private String paginaPrevia;
	private String paginaSiguiente;
	private List<Pokemon> listaPokemon;
	private int pagina;
	private int cantidad;
	public String getPaginaPrevia() {
		return paginaPrevia;
	}
	public void setPaginaPrevia(String paginaPrevia) {
		this.paginaPrevia = paginaPrevia;
	}
	public String getPaginaSiguiente() {
		return paginaSiguiente;
	}
	public void setPaginaSiguiente(String paginaSiguiente) {
		this.paginaSiguiente = paginaSiguiente;
	}
	public List<Pokemon> getListaPokemon() {
		return listaPokemon;
	}
	public void setListaPokemon(List<Pokemon> listaPokemon) {
		this.listaPokemon = listaPokemon;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	
}
