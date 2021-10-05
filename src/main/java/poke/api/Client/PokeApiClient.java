package poke.api.Client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import poke.api.models.PaginaPokemon;
import poke.api.models.Pokemon;
import poke.api.models.PokemonDetalle;
import poke.api.util.TransformacionCadenas;

@Service
public class PokeApiClient {

	public PaginaPokemon obtenerPaginaPokemon(int pagina, int cantidad) {

		pagina--;

		PaginaPokemon paginaPokemon = new PaginaPokemon();

		try {
			List<Pokemon> listaPokemon = new ArrayList<Pokemon>();

			int posicionInicial = pagina * cantidad;

			String url = "https://pokeapi.co/api/v2/pokemon-species?offset=" + posicionInicial + "&limit=" + cantidad;

			JsonObject jsonObjectResponse = new JsonObject();

			Client client = ClientBuilder.newClient();

			WebTarget target = client.target(url);

			Invocation.Builder solicitud = target.request();

			Response get = solicitud.get();

			String jsonStringResponse = get.readEntity(String.class);

			jsonObjectResponse = (JsonObject) JsonParser.parseString(jsonStringResponse);

			JsonArray jsonArray = (JsonArray) jsonObjectResponse.get("results");

			for (int i = 0; i < jsonArray.size(); i++) {

				Pokemon pokemon = new Pokemon();

				JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

				int id = pagina * cantidad + i + 1;
				String rutaImagen = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id
						+ ".png";
				String nombre = obtenerNombreDeJson(jsonObject);
				pokemon.setNombre(nombre);
				pokemon.setId(id);
				pokemon.setRutaImagen(rutaImagen);
				listaPokemon.add(pokemon);
			}

			paginaPokemon.setCantidad(cantidad);
			paginaPokemon.setPagina(pagina);
			paginaPokemon.setListaPokemon(listaPokemon);
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return paginaPokemon;
	}

	public PokemonDetalle obtenerPokemonDetalle(int id) {

		PokemonDetalle pokemonDetalle = new PokemonDetalle();

		try {
			String url = "https://pokeapi.co/api/v2/pokemon/" + id;

			JsonObject jsonObjectResponse = new JsonObject();

			Client client = ClientBuilder.newClient();

			WebTarget target = client.target(url);

			Invocation.Builder solicitud = target.request();

			Response get = solicitud.get();

			String jsonStringResponse = get.readEntity(String.class);

			jsonObjectResponse = (JsonObject) JsonParser.parseString(jsonStringResponse);

			JsonArray jsonArrayMovimientos = (JsonArray) jsonObjectResponse.get("moves");

			List<String> listaMovimientos = new ArrayList<String>();

			for (int i = 0; i < jsonArrayMovimientos.size(); i++) {

				JsonObject jsonObject = jsonArrayMovimientos.get(i).getAsJsonObject();

				String movimiento = jsonObject.getAsJsonObject("move").get("name").toString();
				movimiento = TransformacionCadenas.QuitarComillas(movimiento);
				movimiento = TransformacionCadenas.CapitalizarCadena(movimiento);
				listaMovimientos.add(movimiento);
			}

			JsonArray jsonArrayTipos = (JsonArray) jsonObjectResponse.get("types");

			List<String> listaTipos = new ArrayList<String>();

			for (int i = 0; i < jsonArrayTipos.size(); i++) {

				JsonObject jsonObject = jsonArrayTipos.get(i).getAsJsonObject();

				String tipo = jsonObject.getAsJsonObject("type").get("name").toString();
				tipo = TransformacionCadenas.QuitarComillas(tipo);
				tipo = TransformacionCadenas.CapitalizarCadena(tipo);
				listaTipos.add(tipo);
			}

			String rutaImagen = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id
					+ ".png";
			String nombre = obtenerNombreDeJson(jsonObjectResponse);

			pokemonDetalle.setId(id);
			pokemonDetalle.setNombre(nombre);
			pokemonDetalle.setRutaImagen(rutaImagen);
			pokemonDetalle.setListaMovimientos(listaMovimientos);
			pokemonDetalle.setListaTipos(listaTipos);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return pokemonDetalle;
	}

	public List<Pokemon> listaCadenaEvolutiva(String url) {

		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();

		try {
			JsonObject jsonObjectResponse = new JsonObject();

			Client client = ClientBuilder.newClient();

			WebTarget target = client.target(url);

			Invocation.Builder solicitud = target.request();

			Response get = solicitud.get();

			String jsonStringResponse = get.readEntity(String.class);

			jsonObjectResponse = (JsonObject) JsonParser.parseString(jsonStringResponse);

			JsonObject jsonCadena = jsonObjectResponse.getAsJsonObject("chain");

			JsonObject jsonEtapa1 = jsonCadena.getAsJsonObject("species");

			Pokemon etapa1 = new Pokemon();
			int id = obtenerIdDeRutaJson(jsonEtapa1);
			etapa1.setNombre(obtenerNombreDeJson(jsonEtapa1));
			etapa1.setId(id);
			etapa1.setRutaImagen(llenarRutaImagen(id));

			listaPokemon.add(etapa1);

			JsonArray jsonArrayEtapa2 = (JsonArray) jsonCadena.get("evolves_to");

			for (int i = 0; i < jsonArrayEtapa2.size(); i++) {

				Pokemon etapa2 = new Pokemon();

				JsonObject jsonObjectEtapa2 = jsonArrayEtapa2.get(i).getAsJsonObject();
				JsonObject jsonEtapa2 = jsonObjectEtapa2.getAsJsonObject("species");
				String nombreEtapa2 = obtenerNombreDeJson(jsonEtapa2);
				etapa2.setNombre(nombreEtapa2);
				int id2 = obtenerIdDeRutaJson(jsonEtapa2);
				etapa2.setId(id2);
				etapa2.setRutaImagen(llenarRutaImagen(id2));
				listaPokemon.add(etapa2);

				JsonArray jsonArrayEtapa3 = (JsonArray) jsonObjectEtapa2.get("evolves_to");

				for (int j = 0; j < jsonArrayEtapa3.size(); j++) {

					Pokemon etapa3 = new Pokemon();

					JsonObject jsonObjectEtapa3 = jsonArrayEtapa3.get(j).getAsJsonObject();
					JsonObject jsonEtapa3 = jsonObjectEtapa3.getAsJsonObject("species");
					String nombreEtapa3 = obtenerNombreDeJson(jsonEtapa3);
					etapa3.setNombre(nombreEtapa3);
					int id3 = obtenerIdDeRutaJson(jsonEtapa3);
					etapa3.setId(id3);
					etapa3.setRutaImagen(llenarRutaImagen(id3));
					listaPokemon.add(etapa3);

				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaPokemon;
	}

	public String obtenerRutaCadenaEvolutiva(int id) {

		String urlCadenaEvolutiva = "";
		try {
			String url = "https://pokeapi.co/api/v2/pokemon-species/" + id;

			JsonObject jsonObjectResponse = new JsonObject();

			Client client = ClientBuilder.newClient();

			WebTarget target = client.target(url);

			Invocation.Builder solicitud = target.request();

			Response get = solicitud.get();

			String jsonStringResponse = get.readEntity(String.class);

			jsonObjectResponse = (JsonObject) JsonParser.parseString(jsonStringResponse);

			urlCadenaEvolutiva = jsonObjectResponse.getAsJsonObject("evolution_chain").get("url").toString();
			urlCadenaEvolutiva = TransformacionCadenas.QuitarComillas(urlCadenaEvolutiva);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return urlCadenaEvolutiva;
	}

	public String obtenerNombreDeJson(JsonObject json) {

		String nombre = json.get("name").toString();
		nombre = TransformacionCadenas.QuitarComillas(nombre);
		nombre = TransformacionCadenas.CapitalizarCadena(nombre);

		return nombre;
	}

	public int obtenerIdDeRutaJson(JsonObject json) {
		String url = json.get("url").toString();
		url = TransformacionCadenas.QuitarComillas(url);
		url = url.replace("https://pokeapi.co/api/v2/pokemon-species/", "");
		String id = url.replace("/", "");
		return Integer.parseInt(id);
	}

	public String llenarRutaImagen(int id) {
		return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";

	}
}
