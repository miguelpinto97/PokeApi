package poke.api.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poke.api.Client.PokeApiClient;
import poke.api.models.ListaPaginacionType;
import poke.api.models.Pokemon;
import poke.api.models.PokemonDetalle;

@Controller
@RequestMapping("/")

public class PokeApiController {

	@Autowired
	private PokeApiClient pokeApiClient;

	private List<ListaPaginacionType> listaPaginacion = new ArrayList<ListaPaginacionType>();

	private int ultimaPagina = 5;
	private int primeraPagina = 1;
	private int paginaActual = 1;
	private String rutaCadenaEvolutiva="";
	@GetMapping("/cambiarPagina")
	public String cambiarPaginaPokemon(Model map, @RequestParam(value = "pagina", required = true) int pagina,
			@RequestParam(value = "cantidad", required = true) int cantidad) {

		paginaActual = pagina;
		if (paginaActual < 1 || cantidad < 0) {
			paginaActual = 1;
			cantidad = 20;
			reiniciarVariablesPaginacion();
		} else if (paginaActual > ultimaPagina) {
			primeraPagina = paginaActual - (ultimaPagina - primeraPagina);

			if (primeraPagina < 1) {
				reiniciarVariablesPaginacion();
			} else {
				ultimaPagina++;

			}

		} else if (paginaActual < primeraPagina) {
			ultimaPagina = paginaActual + (ultimaPagina - primeraPagina);
			primeraPagina = paginaActual;
		}


		llenarListaPaginacion(map);

		map.addAttribute("PaginaPokemon", pokeApiClient.obtenerPaginaPokemon(paginaActual, cantidad));
		map.addAttribute("pagina", paginaActual);
		map.addAttribute("cantidad", cantidad);
		return "/index";
	}

	@GetMapping
	public String vacio(Model map) {

		reiniciarVariablesPaginacion();
		llenarListaPaginacion(map);

		int pagina = 1;
		int cantidad = 20;
		map.addAttribute("PaginaPokemon", pokeApiClient.obtenerPaginaPokemon(pagina, cantidad));
		map.addAttribute("pagina", pagina);
		map.addAttribute("cantidad", cantidad);
		return "/index";
	}

	public void llenarListaPaginacion(Model map) {
		listaPaginacion.clear();

		ListaPaginacionType itemAnterior = new ListaPaginacionType();
		itemAnterior.setNumeroPagina(paginaActual - 1);
		itemAnterior.setTextoPagina("Anterior");
		itemAnterior.setClassPaginacion("page-item");

		listaPaginacion.add(itemAnterior);

		for (int i = primeraPagina; i <= ultimaPagina; i++) {
			ListaPaginacionType item = new ListaPaginacionType();

			item.setNumeroPagina(i);
			item.setTextoPagina(i + "");

			if (i == paginaActual) {
				item.setClassPaginacion("page-item active");
			} else {
				item.setClassPaginacion("page-item");
			}
			listaPaginacion.add(item);
		}

		ListaPaginacionType itemSiguiente = new ListaPaginacionType();

		itemSiguiente.setNumeroPagina(paginaActual + 1);
		itemSiguiente.setTextoPagina("Siguiente");
		itemSiguiente.setClassPaginacion("page-item");

		listaPaginacion.add(itemSiguiente);

		map.addAttribute("listaPaginacion", listaPaginacion);

	}

	public void reiniciarVariablesPaginacion() {
		primeraPagina = 1;
		ultimaPagina = 5;
	}

	@GetMapping("/obtenerDetallePokemon")
	public String obtenerDetallePokemon(Model map, @RequestParam(value = "id", required = true) int id) {


		PokemonDetalle pokemonDetalle = pokeApiClient.obtenerPokemonDetalle(id);
		rutaCadenaEvolutiva = pokeApiClient.obtenerRutaCadenaEvolutiva(id);
		List<Pokemon> listaCadenaEvolutiva = pokeApiClient.listaCadenaEvolutiva(rutaCadenaEvolutiva);
		
		map.addAttribute("Pokemon", pokemonDetalle);
		map.addAttribute("listaCadenaEvolutiva", listaCadenaEvolutiva);

		return "/detallePokemon";
	}
	
	@GetMapping("/cadenaEvolutiva")
	public String obtenerCadenaEvolutiva(Model map) {

		reiniciarVariablesPaginacion();
		llenarListaPaginacion(map);

		int pagina = 1;
		int cantidad = 20;
		map.addAttribute("PaginaPokemon", pokeApiClient.obtenerPaginaPokemon(pagina, cantidad));
		map.addAttribute("pagina", pagina);
		map.addAttribute("cantidad", cantidad);
		
		System.out.println(rutaCadenaEvolutiva);
		return "/index";
	}

}
