package documentosConsultas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Clase de consultas, con identificador, query (cuerpo consulta), autores, identificador detallado, palabras validas (no comunes)
 *
 */
public class Consulta {

	private Integer id; //I.
	private String query; //.W
	private ArrayList<String> autores; //.A ?
	private String fullId; //.N ?
	private LinkedList<String> palabrasValidas;
	/**
	 * Contructor consulta, inicialización de variables.
	 */
	public Consulta() {
		this.id = 0;
		this.query = "";
		this.autores = new ArrayList<>();
		this.fullId = "";
		this.palabrasValidas = new LinkedList<>();
	}
	/**
	 * Formato en String de la clase Consulta
	 */
	@Override
	public String toString() {
		return "Consulta [id=" + id + ", query=" + query + ", autores=" + autores + ", fullId=" + fullId + "]";
	}
	
	/**
	 * Contructor de consulta
	 * @param id Identificador consulta
	 * @param query Cuerpo consulta
	 * @param autores Lista de autores de la consulta
	 * @param fullId Identificador largo de la consuta
	 */

	public Consulta(Integer id, String query, ArrayList<String> autores, String fullId) {
		this.id = id;
		this.query = query;
		this.autores = autores;
		this.fullId = fullId;
		this.palabrasValidas = new LinkedList<>();
		
		
	}
	
	/**
	 * Para generar contenido de la lista this.palabrasComunes,se quitan todas las palabras comunes y se deja en una lista enlazada todas las palabras en minisculas.
	 * Se asume que todas palabras no validas (comunes) se encuentran en minusculas.
	 * @param palabrasComunes Lista de palabras comunes para no agregar a la lista de this.palabrasValidas
	 */
	public void generarSetPalabras(ArrayList<String> palabrasComunes){
		if(palabrasValidas != null && !this.query.equals("")){
			String[] palabras = this.query.split("[\\W\\d]+");// \W = no word character, \d digit character, \D no digit
			//System.out.println("test:"+palabras);
			palabrasValidas.clear();
			for(String s: palabras){
				if(s.length() > 1 && !palabrasComunes.contains(s.toLowerCase())){
					this.palabrasValidas.add(s.toLowerCase());
				}
			}
			
		}
	}
	
	/**
	 * Realiza la lectura de archivo de consultas y los inserta en una lista
	 * @param origen Archivo de texto con querys, no nula.
	 * @param consultas Colección de consultas a rellenar, no nula.
	 */
	
	public static void generarConsultas(File origen, ArrayList<Consulta> consultas){

			try {
				Scanner sc = new Scanner(origen);
				Consulta con = null;
				String line = "";
				Boolean bloqueo = false; //variable para controlar el caso de que exista una etiqueta sin contenido
				
				while (sc.hasNextLine()) {
					if(!bloqueo){
						line = sc.nextLine();
						bloqueo = false;
					}else{
						//System.out.println(line);
					}

					
					if(con != null && line.contains(".W")  && line.substring(0, 1).equals(".")){
						String cuerpo = "";
						line = sc.nextLine();
						while(line.length() > 1 && !line.substring(0, 1).equals(".") && sc.hasNextLine()){
							cuerpo +=line+" ";
							line = sc.nextLine();
						}
						bloqueo = false;
						if(!cuerpo.equals("None")){
							con.setQuery(cuerpo);
						}
					}
					
					if(con != null && line.contains(".A") && line.substring(0, 1).equals(".")){
						ArrayList<String> autores = new ArrayList<String>();
						line = sc.nextLine();
						while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
							if(!line.equals("None")){
								autores.add(line);
							}
							line = sc.nextLine();
						}
						bloqueo = false;
						con.setAutores(autores);
					}
					
					
					if(line.contains(".I") && line.substring(0, 1).equals(".")){
						con = new Consulta();
						Integer id = Integer.parseInt(line.substring(3, line.length()));
						con.setId(id);
						consultas.add(con);
						bloqueo = false;
					}else if(line.contains(".N") && line.substring(0, 1).equals(".")){
						line = sc.nextLine();
						if(!line.substring(0, 1).equals(".") && !line.equals("None")){
							con.setFullId(line);
							bloqueo = false;
						}else{
							bloqueo = true;
						}
					}
					
					
				}
				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}
	
	/**
	 * ONLY LISA DB: Realiza la lectura de archivo de consultas y los inserta en una lista
	 * @param origen Archivo de texto con querys, no nula.
	 * @param consultas Colección de consultas a rellenar, no nula.
	 */
	
	public static void generarConsultasLisa(File origen, ArrayList<Consulta> consultas){

			try {
				Scanner sc = new Scanner(origen);
				Consulta con = null;
				String line = "";
				Boolean bloqueo = false; //variable para controlar el caso de que exista una etiqueta sin contenido
				
				while (sc.hasNextLine()) {
					
						con = new Consulta();
						Integer id = Integer.parseInt(line);
						con.setId(id);
						consultas.add(con);
						bloqueo = false;
						String cuerpo = "";
						line = sc.nextLine();
						while(sc.hasNextLine()){
							cuerpo +=line+" ";
							if(line.contains("#")){
								break;
							}
							line = sc.nextLine();
						}
						con.setQuery(cuerpo);
					
				}
				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}
	
	public Integer getId() {
		return id;
	}
	public String getQuery() {
		return query;
	}
	public ArrayList<String> getAutores() {
		return autores;
	}
	public String getFullId() {
		return fullId;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setAutores(ArrayList<String> autores) {
		this.autores = autores;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	@Override
	public int hashCode() {
		return id;
	}
	/**
	 * Retorna la colección de palabras validas, se nececita generar con el metodo generar 
	 * @return
	 */
	public LinkedList<String> getPalabrasValidas() {
		return palabrasValidas;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consulta other = (Consulta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
