package poke.api.models;

public class ListaPaginacionType {

	private int numeroPagina;
	private String textoPagina;
	private String classPaginacion;

	public String getClassPaginacion() {
		return classPaginacion;
	}

	public void setClassPaginacion(String classPaginacion) {
		this.classPaginacion = classPaginacion;
	}

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public String getTextoPagina() {
		return textoPagina;
	}

	public void setTextoPagina(String textoPagina) {
		this.textoPagina = textoPagina;
	}

}
