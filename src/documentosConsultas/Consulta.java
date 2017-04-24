package documentosConsultas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Consulta {

	private Integer id; //I.
	private String query; //.W
	private ArrayList<String> autores; //.A ?
	private String fullId; //.N ?
	
	public Consulta() {
	
	}
	
	@Override
	public String toString() {
		return "Consulta [id=" + id + ", query=" + query + ", autores=" + autores + ", fullId=" + fullId + "]";
	}

	public Consulta(Integer id, String query, ArrayList<String> autores, String fullId) {
		this.id = id;
		this.query = query;
		this.autores = autores;
		this.fullId = fullId;
		
	}
	
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
						while(!line.substring(0, 1).equals(".") && sc.hasNextLine()){
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
