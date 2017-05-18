package analisis;
/**
 * Atributos: (Integer) IDConsulta, (Double) precision, (Integer) DocumentosRelevantes, (Integer) PIn.
 * 
 * @author Marcos
 *
 */

public class Precision {
	
	Integer idConsulta;
	Double precision;
	Integer documentosRelevantes;
	Integer pIn;
	
	public Precision(Integer idConsulta, Double precision, Integer documentosRelevantes, Integer pIn) {
		this.idConsulta = idConsulta;
		this.precision = precision;
		this.documentosRelevantes = documentosRelevantes;
		this.pIn = pIn;
	}
	public Integer getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(Integer idConsulta) {
		this.idConsulta = idConsulta;
	}
	public Double getPrecision() {
		return precision;
	}
	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	public Integer getDocumentosRelevantes() {
		return documentosRelevantes;
	}
	public void setDocumentosRelevantes(Integer documentosRelevantes) {
		this.documentosRelevantes = documentosRelevantes;
	}
	public Integer getpIn() {
		return pIn;
	}
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
