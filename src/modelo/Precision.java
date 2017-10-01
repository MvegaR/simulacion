package modelo;
/**
 * 
 * Clase diseñada para almacenar la precisión de una determinada consulta,
 * cantidad de documentos relevantes de la consulta, valor p@ utilizado.
 * @author Marcos
 *
 */
public class Precision {
	/** Identificador de la consulta */
	Integer idConsulta;
	/** Precisión calculada de la consulta */
	Double precision;
	/** Cantidad de documentos relevantes para la consulta */
	Integer documentosRelevantes;
	/** Número de documentos máximos a desplegar y a incluir en el cálculo de precisión */
	Integer pIn;
	/**
	 * Constructor clase Precision
	 * @param idConsulta Identificador de la consulta
	 * @param precision precision de la consulta
	 * @param documentosRelevantes cantidad de documentos relevantes en la consulta
	 * @param pIn Número de documentos máximos a desplegar y a incluir en el cálculo de precisión 
	 */
	public Precision(Integer idConsulta, Double precision, Integer documentosRelevantes, Integer pIn) {
		this.idConsulta = idConsulta;
		this.precision = precision;
		this.documentosRelevantes = documentosRelevantes;
		this.pIn = pIn;
	}
	/** 
	 * Entrega el identificador de la consulta
	 * @return Identificador valor entero
	 */
	public Integer getIdConsulta() {
		return idConsulta;
	}
	/** 
	 * Modifica el identificador de la consulta 
	 * @param idConsulta nuevo identificador
	 */
	public void setIdConsulta(Integer idConsulta) {
		this.idConsulta = idConsulta;
	}
	/**
	 * Entrega la precisión
	 * @return Precisión, valor Real.
	 */
	public Double getPrecision() {
		return precision;
	}
	/**
	 * Cambia el valor de la precisión
	 * @param precision nuevo valor
	 */
	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	/**
	 * Entrega la cantidad de documentos relevantes
	 * @return cantidad de documentos relevantes
	 */
	public Integer getDocumentosRelevantes() {
		return documentosRelevantes;
	}
	/**
	 * Modifica el valor de la cantidad de documentos relevantes
	 * @param documentosRelevantes Nuevo valor
	 */
	public void setDocumentosRelevantes(Integer documentosRelevantes) {
		this.documentosRelevantes = documentosRelevantes;
	}
	/**
	 * Entrega el valor PIn utilizado
	 * @return valor Pin
	 */
	public Integer getpIn() {
		return pIn;
	}
	/**
	 * Modifica el valor pIn
	 * @param pIn nuevo valor
	 */
	public void setpIn(Integer pIn) {
		this.pIn = pIn;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentosRelevantes == null) ? 0 : documentosRelevantes.hashCode());
		result = prime * result + ((idConsulta == null) ? 0 : idConsulta.hashCode());
		result = prime * result + ((pIn == null) ? 0 : pIn.hashCode());
		result = prime * result + ((precision == null) ? 0 : precision.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Precision other = (Precision) obj;
		if (documentosRelevantes == null) {
			if (other.documentosRelevantes != null)
				return false;
		} else if (!documentosRelevantes.equals(other.documentosRelevantes))
			return false;
		if (idConsulta == null) {
			if (other.idConsulta != null)
				return false;
		} else if (!idConsulta.equals(other.idConsulta))
			return false;
		if (pIn == null) {
			if (other.pIn != null)
				return false;
		} else if (!pIn.equals(other.pIn))
			return false;
		if (precision == null) {
			if (other.precision != null)
				return false;
		} else if (!precision.equals(other.precision))
			return false;
		return true;
	}
}