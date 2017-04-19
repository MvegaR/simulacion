package documentosConsultas;

import java.io.File;
import java.util.ArrayList;

public class Consulta {

	private Integer id;
	private String query;
	private ArrayList<String> autores;
	private String fullId;
	
	public Consulta(Integer id, String query, ArrayList<String> autores, String fullId) {
		this.id = id;
		this.query = query;
		this.autores = autores;
		this.fullId = fullId;
		
	}
	
	public static void generarConsultas(File orgien, ArrayList<Consulta> consultas){
		
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
