package documentosConsultas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Documento {
	
	private Integer id; //.I
	private String titulo; //.T
	private String cuerpo; //.W
	private String fecha; //.B
	private ArrayList<String> autores; //.A
	private ArrayList<String> tags; //.K
	private ArrayList<String> codigos; //.C
	private String infoPublicacion; //.N
	private ArrayList<Referencia> referencias; //.X

	public Documento() {
		// TODO Auto-generated constructor stub
	}

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
	}
	
	
	

	public Integer getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public String getFecha() {
		return fecha;
	}
	public ArrayList<String> getAutores() {
		return autores;
	}
	public String getInfoPublicacion() {
		return infoPublicacion;
	}
	public ArrayList<String> getCodigos() {
		return codigos;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	
	public ArrayList<Referencia> getReferencias() {
		return referencias;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setAutores(ArrayList<String> autores) {
		this.autores = autores;
	}

	public void setInfoPublicacion(String infoPublicacion) {
		this.infoPublicacion = infoPublicacion;
	}

	public void setReferencias(ArrayList<Referencia> referencias) {
		this.referencias = referencias;
	}
	public void setCodigos(ArrayList<String> codigos) {
		this.codigos = codigos;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
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
				}else{
					//System.out.println(line);
				}

				if(doc != null && line.contains(".X")  && line.substring(0, 1).equals(".")){
					ArrayList<Referencia> referencias = new ArrayList<Referencia>();
					line = sc.nextLine();
					while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
						if(!line.equals("None")){
							referencias.add(new Referencia(Integer.parseInt(line.split("[\t ]")[0]), Integer.parseInt(line.split("[\t ]")[1]), Integer.parseInt(line.split("[\t ]")[2])));
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
					while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
						cuerpo +=line+" ";
						line = sc.nextLine();
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
					line = sc.nextLine();
					if(!line.substring(0, 1).equals(".") && !line.equals("None")){
						doc.setTitulo(line);
						bloqueo = false;
					}else{
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


}
