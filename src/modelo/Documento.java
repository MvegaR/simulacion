package modelo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Realiza la lectura de un archivo de texto que entrega identificador, título y cuerpo del documento, 
 * el resto de atributos no se utilizaron en el proyecto pero estaban presentes en la base de datos,
 * se asegura una lectura total del archivo documentos.
 *  @author Marcos
 */
public class Documento {
	/** Identificador del documento */
	private Integer id; //.I
	/** Titulo del documento */
	private String titulo; //.T
	/** Cuerpo del documento */
	private String cuerpo; //.W
	/** Fecha del documento */
	private String fecha; //.B
	/** Autores del documento */
	private ArrayList<String> autores; //.A
	/** Tags para indexar el documento */
	private ArrayList<String> tags; //.K
	/** Códigos de identificación del documento */
	private ArrayList<String> codigos; //.C
	/** Información de la publicación del documento */
	private String infoPublicacion; //.N
	/** Referencias presentes en el documento */
	private ArrayList<Referencia> referencias; //.X
	/** Palabras válidas en una lista enlazada */
	private LinkedList<String> palabrasValidas;
	/**
	 * Constructor: inicializador de atributos
	 */
	public Documento() {
		id = 0;
		titulo = "";
		cuerpo = "";
		fecha = "";
		autores = new ArrayList<>();
		tags = new ArrayList<>();
		codigos = new ArrayList<>();
		infoPublicacion = "";
		referencias = new ArrayList<>();
		palabrasValidas = new LinkedList<>();
	}
	/**
	 * Constructor, inicializador de atributos
	 * @param id Identificador documento
	 * @param titulo Título del documento
	 * @param cuerpo Cuerpo del documento
	 * @param fecha fecha publicación del documento
	 * @param tags Palabras claves para indexar el documento
	 * @param codigos Codigos de la publicación
	 * @param autores Autores del documento
	 * @param infoPubliacion Información de la publicación
	 * @param referencias Referencias a otros documentos
	 */
	public Documento(Integer id, String titulo, String cuerpo, String fecha, 
			ArrayList<String> tags, ArrayList<String> codigos,
			ArrayList<String> autores, String infoPubliacion, 
			ArrayList<Referencia> referencias) {
		this.id = id;
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.fecha = fecha;
		this.autores = autores;
		this.tags = tags;
		this.codigos = codigos;
		this.infoPublicacion = infoPubliacion;
		this.referencias = referencias;
		this.palabrasValidas = new LinkedList<>();
	}
	/**
	 * Para generar contenido de la lista this.palabrasComunes, se quitan 
	 * todas las palabras comunes y se deja en una lista 
	 * enlazada todas las palabras en minúsculas.
	 * Se asume que todas palabras no válidas (comunes) 
	 * se encuentran en minúsculas.
	 * @param palabrasComunes Lista de palabras comunes para no 
	 * agregar a la lista de this.palabrasValidas
	 */
	public void generarSetPalabras(ArrayList<String> palabrasComunes){
		if(palabrasValidas != null){
			String[] palabras = (cuerpo + " " + titulo).split("[\\W\\d]+");
			// \W = no word character, \d digit character, \D no digit
			palabrasValidas.clear();
			for(String s: palabras){
				if(s.length() > 1 && !palabrasComunes.contains(s.toLowerCase())){ 
					//eliminar palabras de dos letras y comunes
					this.palabrasValidas.add(s.toLowerCase()); //Solo palabras minúsculas
				}
			}
		}
	}
	/**
	 * Obtener identificador documento
	 * @return El identificador
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Obtener el título del documento
	 * @return El título
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Obtener cuerpo documento
	 * @return El cuerpo
	 */
	public String getCuerpo() {
		return cuerpo;
	}
	/**
	 * Obtiene fecha publicación documento
	 * @return Fecha
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * Obtiene autores del documento
	 * @return Lista de autores
	 */
	public ArrayList<String> getAutores() {
		return autores;
	}
	/**
	 * Obtiene información de la publicación del documento 
	 * @return La información de la publicación
	 */
	public String getInfoPublicacion() {
		return infoPublicacion;
	}
	/**
	 * Obtiene códigos de la publicación del documento
	 * @return Lista de códigos
	 */
	public ArrayList<String> getCodigos() {
		return codigos;
	}
	/**
	 * Obtiene lista de palabras indexadoras del documento
	 * @return Lista de palabras/tags
	 */
	public ArrayList<String> getTags() {
		return tags;
	}
	/**
	 * Obtiene referencias del documento.
	 * @return Lista de referencias
	 */
	public ArrayList<Referencia> getReferencias() {
		return referencias;
	}
	/**
	 * Modifica el identificador del documento
	 * @param id Nuevo id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Modifica el título del documento
	 * @param titulo Nuevo título
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * Modifica el cuerpo del documento
	 * @param cuerpo Nuevo cuerpo
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	/**
	 * Modifica la fecha del documento
	 * @param fecha Nueva fecha
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	/**
	 * Modifica la lista de autores
	 * @param autores Nueva lista de autores
	 */
	public void setAutores(ArrayList<String> autores) {
		this.autores = autores;
	}
	/**
	 * Modifica la información de la publicación del documento
	 * @param infoPublicacion Nueva información del documento
	 */
	public void setInfoPublicacion(String infoPublicacion) {
		this.infoPublicacion = infoPublicacion;
	}
	/**
	 * Modifica la lista de referencias del documento
	 * @param referencias Nueva lista de referencias
	 */
	public void setReferencias(ArrayList<Referencia> referencias) {
		this.referencias = referencias;
	}
	/**
	 * Modifica la lista de códigos de la publicación del documento
	 * @param codigos Nueva lista
	 */
	public void setCodigos(ArrayList<String> codigos) {
		this.codigos = codigos;
	}
	/**
	 * Modifica la lista de tags del documento
	 * @param tags Nueva lista de tags
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	/**
	 * Entrega la lista de palabras válidas presentes en el documento, luego del filtrado de palabras comunes.
	 * @return Lista de palabras válidas
	 */
	public LinkedList<String> getPalabrasValidas() {
		return palabrasValidas;
	}
	@Override
	public String toString() {
		return "Documento [id=" + id + ", titulo=" + titulo + ", cuerpo=" + cuerpo + ", fecha=" + fecha + ", autores="
				+ autores + ", tags=" + tags + ", codigos=" + codigos + ", infoPublicacion=" + infoPublicacion
				+ ", referencias=" + referencias + "]";
	}
	@Override
	public int hashCode() {
		return id;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Documento other = (Documento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	/**
	 * Obtiene los documentos desde un archivo de texto y rellena 
	 * una lista entregada por parámetro de documentos generados.
	 * @param origen Archivo de texto con los documentos
	 * @param documentos Lista de documentos a rellenar
	 */
	public static void generarDocumentos(File origen, ArrayList<Documento> documentos){
		try {
			Scanner sc = new Scanner(origen);
			Documento doc = null;
			String line = "";
			Boolean bloqueo = false; //variable para controlar el caso de que exista una etiqueta sin contenido
			while (sc.hasNextLine()) {
				if(!bloqueo){
					line = sc.nextLine();
					bloqueo = false;
				}
				if(doc != null && line.contains(".X")  && line.substring(0, 1).equals(".")){
					ArrayList<Referencia> referencias = new ArrayList<Referencia>();
					line = sc.nextLine();
					while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
						if(!line.equals("None")){
							referencias.add(new Referencia(Integer.parseInt(line.split("[\t ]")[0]), 
									Integer.parseInt(line.split("[\t ]")[1]), 
									Integer.parseInt(line.split("[\t ]")[2])));
						}
						line = sc.nextLine();
					}
					bloqueo = false;
					doc.setReferencias(referencias);
				}
				if(doc != null && line.contains(".A") && line.substring(0, 1).equals(".")){
					ArrayList<String> autores = new ArrayList<String>();
					line = sc.nextLine();
					while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
						if(!line.equals("None")){
							autores.add(line);
						}
						line = sc.nextLine();
					}
					bloqueo = false;
					doc.setAutores(autores);
				}
				if(doc != null && line.contains(".W")  && line.substring(0, 1).equals(".")){
					String cuerpo = "";
					line = sc.nextLine();
					while(line.length() > 1 && !line.substring(0, 1).equals(".") && sc.hasNextLine()){
						cuerpo +=line+" ";
						line = sc.nextLine();
					}
					if(!sc.hasNextLine() && cuerpo.equals("")){
						cuerpo += line;
					}
					bloqueo = false;
					if(!cuerpo.equals("None")){
						doc.setCuerpo(cuerpo);
					}
				}
				if(doc != null && line .contains(".K") && line.substring(0,1).equals(".")){
					ArrayList<String> t = new ArrayList<String>();
					line = sc.nextLine();
					while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
						String tags[] = line.split(", ");
						for(String s: tags){
							if(!s.equals("None")){
								t.add(s);
							}
						}
						line = sc.nextLine();
					}
					bloqueo = false;
					doc.setTags(t);			
				}
				if(doc != null && line .contains(".C") && line.substring(0,1).equals(".")){
					ArrayList<String> c = new ArrayList<String>();
					line = sc.nextLine();
					while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
						String tags[] = line.split("(, )|( )");
						for(String s: tags){
							if(!s.equals("None")){
								c.add(s);
							}
						}
						line = sc.nextLine();
					}
					doc.setCodigos(c);
					bloqueo = false;
				}
				if(line.contains(".I") && line.substring(0, 1).equals(".")){
					doc = new Documento();
					Integer id = Integer.parseInt(line.substring(3, line.length()));
					doc.setId(id);
					documentos.add(doc);
					bloqueo = false;
				}else if(line.contains(".T") && line.substring(0, 1).equals(".")){
					String title = sc.nextLine();
					title = ""+title;
					line = sc.nextLine();
					while(sc.hasNextLine() && !line.substring(0, 1).equals(".")){

						title+=line;
						line = sc.nextLine();
					}
					if(!line.substring(0, 1).equals(".") && !line.equals("None")){
						doc.setTitulo(title);
						bloqueo = false;
					}else{
						if(doc != null && doc.getTitulo().equals("") && !title.equals("")){
							doc.setTitulo(title);
						}
						bloqueo = true;
					}
				}else if(line.contains(".B")  && line.substring(0, 1).equals(".")){
					line = sc.nextLine();
					if(!line.substring(0, 1).equals(".") && !line.equals("None")){
						doc.setFecha(line);
						bloqueo = false;
					}else{
						bloqueo = true;
					}
				}else if(line.contains(".N")  && line.substring(0, 1).equals(".")){
					line = sc.nextLine();
					if(!line.substring(0, 1).equals(".") && !line.equals("None")){
						doc.setInfoPublicacion(line);
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
	 * Solo para BD LISA: Obtiene los documentos desde un archivo de texto 
	 * y rellena una lista entregada por parámetro de documentos generados.
	 * @param origenes Archivo de texto con los documentos
	 * @param documentos Lista de documentos a rellenar
	 */
	public static void generarDocumentosLisa(ArrayList<File> origenes, 
			ArrayList<Documento> documentos){
		try {
			for(File origen: origenes){
				Scanner sc = new Scanner(origen);
				while (sc.hasNextLine()) {
					Documento doc = null;
					String line = "";
					line = sc.nextLine();
					if(doc == null && line.contains("Document") && line.split("[ ]+").length == 2){
						doc = new Documento();
						Integer id = Integer.parseInt(line.split("[ ]+")[1]);
						doc.setId(id);
						documentos.add(doc);
						//titulo:
						line = sc.nextLine();
						doc.setTitulo(line);
					}
					if(doc != null && !line.equals("********************************************")){
						String cuerpo = "";
						line = sc.nextLine();
						while(!line.equals("********************************************") 
								&& sc.hasNextLine()){
							cuerpo +=line+" ";
							line = sc.nextLine();
						}
						doc.setCuerpo(cuerpo);
					}
				}
				sc.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Solo para BD Time: Obtiene los documentos desde un archivo de texto 
	 * y rellena una lista entregada por parámetro de documentos generados.
	 * @param origen Archivo de texto con los documentos
	 * @param documentos Lista de documentos a rellenar
	 */
	public static void generarDocumentosTime(File origen, ArrayList<Documento> documentos){
		try {
			Scanner sc = new Scanner(origen);
			String line = "";
			String cuerpo = null;
			Documento doc = null;
			while(sc.hasNextLine()){
				line = sc.nextLine();
				if(line.contains("*STOP")){
					if(cuerpo != null && doc != null){
						doc.setCuerpo(cuerpo);
						cuerpo = null;
						documentos.add(doc);
					}

					break;
				}
				if(line.contains("*TEXT")){
					if(cuerpo != null && doc != null){
						doc.setCuerpo(cuerpo);
						cuerpo = null;
						documentos.add(doc);
					}
					String id[] = line.split("[ ]+");

					doc = new Documento();
					doc.setId(Integer.parseInt(id[1]));
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
}