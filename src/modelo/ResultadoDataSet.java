package modelo;

import java.util.ArrayList;

/**
 * Clase diseñada para almacenar los datos de la base de datos y los resultados del procesamiento de similitud
 * contiene nombre de la base de datos, lista de resultados de cada consulta, el total de consultas, el total de 
 * documentos, total de palabras comunes y total de palabras únicas. 
 * @author Marcos
 *
 */

public class ResultadoDataSet {
	

	private String setName;
	private ArrayList<ResultadoQuery> resultadosConsultas;
	private Integer totalConsultas;
	private Integer totalDocumentos;
	private Integer totalPalabrasComunes;
	private Integer totalpalabrasValidasNoComunes;
	
	/**
	 * Constructor de la clase
	 * @param setName Nombre de la base de datos
	 * @param totalConsultas Total de consultas de la base de datos
	 * @param totalDocumentos Total de documentos de la base de datos
	 * @param totalPalabrasComunes Palabras comunes para no considerar
	 * @param totalpalabrasValidasNoComunes Total de palabras únicas no comunes
	 */
	public ResultadoDataSet(String setName, Integer totalConsultas, Integer totalDocumentos,
			Integer totalPalabrasComunes, Integer totalpalabrasValidasNoComunes) {
		super();
		this.setName = setName;
		this.resultadosConsultas = new ArrayList<>();
		this.totalConsultas = totalConsultas;
		this.totalDocumentos = totalDocumentos;
		this.totalPalabrasComunes = totalPalabrasComunes;
		this.totalpalabrasValidasNoComunes = totalpalabrasValidasNoComunes;
	}
	
	/**
	 * @return the setName
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * @param setName the setName to set
	 */
	public void setSetName(String setName) {
		this.setName = setName;
	}

	/**
	 * @return the resultadosConsultas
	 */
	public ArrayList<ResultadoQuery> getResultadosConsultas() {
		return resultadosConsultas;
	}

	/**
	 * @param resultadosConsultas the resultadosConsultas to set
	 */
	public void setResultadosConsultas(ArrayList<ResultadoQuery> resultadosConsultas) {
		this.resultadosConsultas = resultadosConsultas;
	}

	/**
	 * @return the totalConsultas
	 */
	public Integer getTotalConsultas() {
		return totalConsultas;
	}

	/**
	 * @param totalConsultas the totalConsultas to set
	 */
	public void setTotalConsultas(Integer totalConsultas) {
		this.totalConsultas = totalConsultas;
	}

	/**
	 * @return the totalDocumentos
	 */
	public Integer getTotalDocumentos() {
		return totalDocumentos;
	}

	/**
	 * @param totalDocumentos the totalDocumentos to set
	 */
	public void setTotalDocumentos(Integer totalDocumentos) {
		this.totalDocumentos = totalDocumentos;
	}

	/**
	 * @return the totalPalabrasComunes
	 */
	public Integer getTotalPalabrasComunes() {
		return totalPalabrasComunes;
	}

	/**
	 * @param totalPalabrasComunes the totalPalabrasComunes to set
	 */
	public void setTotalPalabrasComunes(Integer totalPalabrasComunes) {
		this.totalPalabrasComunes = totalPalabrasComunes;
	}

	/**
	 * @return the totalpalabrasValidasNoComunes
	 */
	public Integer getTotalpalabrasValidasNoComunes() {
		return totalpalabrasValidasNoComunes;
	}

	/**
	 * @param totalpalabrasValidasNoComunes the totalpalabrasValidasNoComunes to set
	 */
	public void setTotalpalabrasValidasNoComunes(Integer totalpalabrasValidasNoComunes) {
		this.totalpalabrasValidasNoComunes = totalpalabrasValidasNoComunes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resultadosConsultas == null) ? 0 : resultadosConsultas.hashCode());
		result = prime * result + ((setName == null) ? 0 : setName.hashCode());
		result = prime * result + ((totalConsultas == null) ? 0 : totalConsultas.hashCode());
		result = prime * result + ((totalDocumentos == null) ? 0 : totalDocumentos.hashCode());
		result = prime * result + ((totalPalabrasComunes == null) ? 0 : totalPalabrasComunes.hashCode());
		result = prime * result
				+ ((totalpalabrasValidasNoComunes == null) ? 0 : totalpalabrasValidasNoComunes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ResultadoDataSet))
			return false;
		ResultadoDataSet other = (ResultadoDataSet) obj;
		if (resultadosConsultas == null) {
			if (other.resultadosConsultas != null)
				return false;
		} else if (!resultadosConsultas.equals(other.resultadosConsultas))
			return false;
		if (setName == null) {
			if (other.setName != null)
				return false;
		} else if (!setName.equals(other.setName))
			return false;
		if (totalConsultas == null) {
			if (other.totalConsultas != null)
				return false;
		} else if (!totalConsultas.equals(other.totalConsultas))
			return false;
		if (totalDocumentos == null) {
			if (other.totalDocumentos != null)
				return false;
		} else if (!totalDocumentos.equals(other.totalDocumentos))
			return false;
		if (totalPalabrasComunes == null) {
			if (other.totalPalabrasComunes != null)
				return false;
		} else if (!totalPalabrasComunes.equals(other.totalPalabrasComunes))
			return false;
		if (totalpalabrasValidasNoComunes == null) {
			if (other.totalpalabrasValidasNoComunes != null)
				return false;
		} else if (!totalpalabrasValidasNoComunes.equals(other.totalpalabrasValidasNoComunes))
			return false;
		return true;
	}
	
	
	
	
	
	
}
