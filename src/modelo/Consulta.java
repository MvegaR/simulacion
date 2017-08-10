package modelo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Clase de consultas, con identificador, query (cuerpo consulta),
 * autores, identificador detallado, palabras válidas (no comunes)
 *
 * @author Marcos
 */
public class Consulta {
	/** Identificador de la consulta */
	private Integer id; //I.
	/** Texto de la consulta */
	private String query; //.W
	/** Autores de la consulta, en el proyecto no se utilizó pero se encuentra presente en el archivo */
	private ArrayList<String> autores; //.A ?
	/** Identificador más detallado de la consulta, no se utilizó 
	 * en el proyecto pero se encuentra presente en el archivo */
	private String fullId; //.N ?
	/** Palabras válidas presentes en la consulta en una lista enlazada */
	private LinkedList<String> palabrasValidas;
	/**
	 * Constructor consulta, inicialización de variables.
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
	 * Constructor de consulta
	 * @param id Identificador consulta
	 * @param query Cuerpo consulta
	 * @param autores Lista de autores de la consulta
	 * @param fullId Identificador largo de la consulta
	 */
	public Consulta(Integer id, String query, ArrayList<String> autores, String fullId) {
		this.id = id;
		this.query = query;
		this.autores = autores;
		this.fullId = fullId;
		this.palabrasValidas = new LinkedList<>();
	}
	/**
	 * Para generar contenido de la lista this.palabrasComunes, se quitan todas las palabras
	 *  comunes y se deja en una lista enlazada todas las palabras en minúsculas.
	 * Se asume que todas palabras no válidas (comunes) se encuentran en minúsculas.
	 * @param palabrasComunes Lista de palabras comunes para no agregar a la lista de this.palabrasValidas
	 */
	public void generarSetPalabras(ArrayList<String> palabrasComunes){
		if(palabrasValidas != null && !this.query.equals("")){
			String[] palabras = this.query.split("[\\W\\d]+");
			// \W = no word character, \d digit character, \D no digit
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
	 * @param origen Archivo de texto con consultas, no nula.
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
				if(con != null && line.contains(".W")  && line.substring(0, 1).equals(".")){
					String cuerpo = "";
					line = sc.nextLine();
					while(line.length() > 1 && !line.substring(0, 1).equals(".") && sc.hasNextLine()){
						cuerpo +=line+" ";
						line = sc.nextLine();
					}
					bloqueo = false;
					if(!sc.hasNextLine() && cuerpo.equals("") && !line.equals("")){
						cuerpo+=line;
					}
					if(!cuerpo.equals("None")){
						con.setQuery(cuerpo);
					}
				}
				if(line.contains(".I") && line.substring(0, 1).equals(".")){
					con = new Consulta();
					Integer id = Integer.parseInt(line.substring(3, line.length()));
					con.setId(id);
					consultas.add(con);
					bloqueo = false;
				}
				if(line.contains(".N") && line.substring(0, 1).equals(".")){
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
	 * Solo para LISA DB: Realiza la lectura de archivo de consultas y los inserta en una lista
	 * @param origen Archivo de texto con consultas, no nula.
	 * @param consultas Colección de consultas a rellenar, no nula.
	 */
	public static void generarConsultasLisa(File origen, ArrayList<Consulta> consultas){
		try {
			Scanner sc = new Scanner(origen);
			Consulta con = null;
			String line = "";
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				con = new Consulta();
				Integer id = Integer.parseInt(line);
				con.setId(id);
				consultas.add(con);
				String cuerpo = "";
				line = sc.nextLine();
				while(sc.hasNextLine()){
					cuerpo +=line+" ";
					if(line.contains("#")){
						break;
					}
					line = sc.nextLine();
				}
				if(!sc.hasNextLine() && cuerpo.equals("") && !line.equals("")){
					cuerpo+=line;
				}
				con.setQuery(cuerpo);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Solo para BD Time: Obtiene las consultas desde un archivo de texto 
	 * y rellena una lista entregada por parámetro de consultas generadas.
	 * @param origenes Archivo de texto con las consultas
	 * @param consultas Lista de consultas a rellenar
	 */
	public static void generarConsultasTime(File origen, ArrayList<Consulta> consultas){
		try {
			Scanner sc = new Scanner(origen);
			String line = "";
			String cuerpo = null;
			Consulta con = null;
			while(sc.hasNextLine()){
				line = sc.nextLine();
				if(line.contains("*STOP")){
					if(cuerpo != null && con != null){
						con.setQuery(cuerpo);
						cuerpo = null;
						consultas.add(con);
					}
					
					break;
				}
				if(line.contains("*FIND")){
					if(cuerpo != null && con != null){
						con.setQuery(cuerpo);
						cuerpo = null;
						consultas.add(con);
					}
					String[] id = line.split("[ ]+");
					System.out.println(id[1]);
					con = new Consulta();
					con.setId(Integer.parseInt(id[1]));
				}else{
					if(cuerpo == null){
						cuerpo = "";
					}
					cuerpo += line;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Obtiene el ID de la consulta
	 * @return Identificador de la consulta
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Obtiene el texto de la consulta
	 * @return Cadena de texto, cuerpo de la consulta
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * Obtiene la lista de autores
	 * @return Arreglo de String de autores
	 */
	public ArrayList<String> getAutores() {
		return autores;
	}
	/**
	 * Obtiene el identificador largo de la consulta
	 * @return Cadena de texto identifiacador de la consulta
	 */
	public String getFullId() {
		return fullId;
	}
	/**
	 * Cambia el ID de la consulta
	 * @param id Nuevo ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Cambia el texto de la consulta
	 * @param query Nuevo texto
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * Cambia la lista de autores
	 * @param autores Nueva lista
	 */
	public void setAutores(ArrayList<String> autores) {
		this.autores = autores;
	}
	/**
	 * Cambia el identificador largo de la consulta
	 * @param fullId Nuevo identificador largo
	 */
	public void setFullId(String fullId) {
		this.fullId = fullId;
	}
	@Override
	public int hashCode() {
		return id;
	}
	/**
	 * Retorna la colección de palabras válidas, se necesita usar con el método generar 
	 * @return Lista de palabras válidas
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