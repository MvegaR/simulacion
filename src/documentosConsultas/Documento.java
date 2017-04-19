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
	private ArrayList<Double> codigos; //.C
	private String infoPublicacion; //.N
	private ArrayList<Referencia> referencias; //.X

	public Documento() {
		// TODO Auto-generated constructor stub
	}

	public Documento(Integer id, String titulo, String cuerpo, String fecha, 
			ArrayList<String> tags, ArrayList<Double> codigos,
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
	public ArrayList<Double> getCodigos() {
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
	public void setCodigos(ArrayList<Double> codigos) {
		this.codigos = codigos;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return this.id.toString();
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
			
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				
				if(doc != null && line.contains(".X")  && line.substring(0, 1).equals(".")){
					ArrayList<Referencia> referencias = new ArrayList<Referencia>();
					while(!line.substring(0, 1).equals(".") && !sc.hasNextLine()){
						line = sc.nextLine();
						referencias.add(new Referencia(Integer.parseInt(line.split("\t")[0]), Integer.parseInt(line.split("\t")[1]), Integer.parseInt(line.split("\t")[2])));
					}
					doc.setReferencias(referencias);
				
				}
				
				if(doc != null && line.contains(".A") && line.substring(0, 1).equals(".")){
						ArrayList<String> autores = new ArrayList<String>();
						while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
							autores.add(sc.nextLine());
						}
						doc.setAutores(autores);
				}
				
				if(doc != null && line.contains(".W")  && line.substring(0, 1).equals(".")){
					String cuerpo = "";
					line = sc.nextLine();
					while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
						cuerpo +=line+" ";
						line = sc.nextLine();
					}
					doc.setCuerpo(cuerpo);
				}
				if(doc != null && line .contains(".K") && line.substring(0,1).equals(".")){
					ArrayList<String> t = new ArrayList<String>();
					while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
						String tags[] = sc.nextLine().split(", ");
						for(String s: tags){
							t.add(s);
						}
					}
					doc.setTags(t);			
				}
				
				
				if(line.contains(".I") && line.substring(0, 1).equals(".")){
					doc = new Documento();
					Integer id = Integer.parseInt(line.substring(3, line.length()));
					doc.setId(id);
					documentos.add(doc);
				}else if(line.contains(".T") && line.substring(0, 1).equals(".")){
					doc.setTitulo(sc.nextLine());
				}else if(line.contains(".B")  && line.substring(0, 1).equals(".")){
					doc.setFecha(sc.nextLine());
				}else if(line.contains(".N")  && line.substring(0, 1).equals(".")){
					doc.setInfoPublicacion(sc.nextLine());
				}
				
				
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}


}
