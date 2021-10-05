package poke.api.util;


public class TransformacionCadenas {

	public static String CapitalizarCadena(String cadena) {
		
        String firstLetStr = cadena.substring(0, 1);
        String remLetStr = cadena.substring(1);
 
        firstLetStr = firstLetStr.toUpperCase();
 
        String firstLetterCapitalizedName = firstLetStr + remLetStr;
        
        
        return firstLetterCapitalizedName;
	}
	
	public static String QuitarComillas(String cadena) {
		
		String sinComillas = cadena.replace("\"", "");
		
		return sinComillas;
	}
}
