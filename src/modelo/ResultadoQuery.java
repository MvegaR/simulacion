package modelo;
import java.util.ArrayList;
/**
 * Clase diseñada para almacenar los resultados de cada una de las consultas luego del análisis de relevancia
 * con el valor p@ (pIn) utilizado, el identificador de la consulta, la lista de resultados por cada documento 
 * , precisión promedio resultante, valor de sensibilidad promedio y total de documentos
 * relevantes para esa consulta conocidos y total de documentos relevantes desplegados en el resultado.
 * @author Marcos
 *
 */
public class ResultadoQuery {
	
	private Integer pIn;
	private Integer idQuery;
	private ArrayList<ResultadoDoc> resultadosDocumentos;
	private Double precisionPromedio;
	private Double recallPromedio;
	private Integer totalDocRelevantesTotales;
	private Integer totalDocReleventesDesplegados;
	
	/**
	 * Constructor de la clase.
	 * @param pIn Valor p@, es la cantidad de documentos entregados como resultado (los mejores para la consulta)
	 * @param idQuery Identificador de la consulta
	 * @param precisionPromedio Precisión promedio resultante
	 * @param recallPromedio Sensibilidad promedio resultante
	 * @param totalDocRelevantesTotales Total de documentos relevantes para esta consulta existentes.
	 * @param totalDocumentosRelevantesDesplegados Total de documentos relevantes que se mostraron en los resultados
	 */
	
	public ResultadoQuery(Integer pIn, Integer idQuery,
			Double precisionPromedio, Double recallPromedio, Integer totalDocRelevantesTotales, 
			Integer totalDocumentosRelevantesDesplegados) {
		super();
		this.pIn = pIn;
		this.idQuery = idQuery;
		this.resultadosDocumentos = new ArrayList<ResultadoDoc>();
		this.precisionPromedio = precisionPromedio;
		this.recallPromedio = recallPromedio;
		this.totalDocRelevantesTotales = totalDocRelevantesTotales;
		this.totalDocReleventesDesplegados = totalDocumentosRelevantesDesplegados;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultadoQuery [pIn=" + pIn + ", idQuery=" + idQuery + ", resultadosDocumentos=" + resultadosDocumentos
				+ ", precisionPromedio=" + precisionPromedio + ", recallPromedio=" + recallPromedio
				+ ", totalDocRelevantesTotales=" + totalDocRelevantesTotales + ", totalDocReleventesDesplegados="
				+ totalDocReleventesDesplegados + "]";
	}
	/**
	 * @return the totalDocReleventesDesplegados
	 */
	public Integer getTotalDocReleventesDesplegados() {
		return totalDocReleventesDesplegados;
	}
	/**
	 * @param totalDocReleventesDesplegados the totalDocReleventesDesplegados to set
	 */
	public void setTotalDocReleventesDesplegados(Integer totalDocReleventesDesplegados) {
		this.totalDocReleventesDesplegados = totalDocReleventesDesplegados;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idQuery == null) ? 0 : idQuery.hashCode());
		result = prime * result + ((pIn == null) ? 0 : pIn.hashCode());
		result = prime * result + ((precisionPromedio == null) ? 0 : precisionPromedio.hashCode());
		result = prime * result + ((recallPromedio == null) ? 0 : recallPromedio.hashCode());
		result = prime * result + ((resultadosDocumentos == null) ? 0 : resultadosDocumentos.hashCode());
		result = prime * result + ((totalDocRelevantesTotales == null) ? 0 : totalDocRelevantesTotales.hashCode());
		result = prime * result
				+ ((totalDocReleventesDesplegados == null) ? 0 : totalDocReleventesDesplegados.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultadoQuery other = (ResultadoQuery) obj;
		if (idQuery == null) {
			if (other.idQuery != null)
				return false;
		} else if (!idQuery.equals(other.idQuery))
			return false;
		if (pIn == null) {
			if (other.pIn != null)
				return false;
		} else if (!pIn.equals(other.pIn))
			return false;
		if (precisionPromedio == null) {
			if (other.precisionPromedio != null)
				return false;
		} else if (!precisionPromedio.equals(other.precisionPromedio))
			return false;
		if (recallPromedio == null) {
			if (other.recallPromedio != null)
				return false;
		} else if (!recallPromedio.equals(other.recallPromedio))
			return false;
		if (resultadosDocumentos == null) {
			if (other.resultadosDocumentos != null)
				return false;
		} else if (!resultadosDocumentos.equals(other.resultadosDocumentos))
			return false;
		if (totalDocRelevantesTotales == null) {
			if (other.totalDocRelevantesTotales != null)
				return false;
		} else if (!totalDocRelevantesTotales.equals(other.totalDocRelevantesTotales))
			return false;
		if (totalDocReleventesDesplegados == null) {
			if (other.totalDocReleventesDesplegados != null)
				return false;
		} else if (!totalDocReleventesDesplegados.equals(other.totalDocReleventesDesplegados))
			return false;
		return true;
	}
	/**
	 * @return the pIn
	 */
	public Integer getpIn() {
		return pIn;
	}
	/**
	 * @param pIn the pIn to set
	 */
	public void setpIn(Integer pIn) {
		this.pIn = pIn;
	}
	/**
	 * @return the idQuery
	 */
	public Integer getIdQuery() {
		return idQuery;
	}
	/**
	 * @param idQuery the idQuery to set
	 */
	public void setIdQuery(Integer idQuery) {
		this.idQuery = idQuery;
	}
	/**
	 * @return the resultadosDocumentos
	 */
	public ArrayList<ResultadoDoc> getResultadosDocumentos() {
		return resultadosDocumentos;
	}
	/**
	 * @param resultadosDocumentos the resultadosDocumentos to set
	 */
	public void setResultadosDocumentos(ArrayList<ResultadoDoc> resultadosDocumentos) {
		this.resultadosDocumentos = resultadosDocumentos;
	}
	/**
	 * @return the precisionPromedio
	 */
	public Double getPrecisionPromedio() {
		return precisionPromedio;
	}
	/**
	 * @param precisionPromedio the precisionPromedio to set
	 */
	public void setPrecisionPromedio(Double precisionPromedio) {
		this.precisionPromedio = precisionPromedio;
	}
	/**
	 * @return the recallPromedio
	 */
	public Double getRecallPromedio() {
		return recallPromedio;
	}
	/**
	 * @param recallPromedio the recallPromedio to set
	 */
	public void setRecallPromedio(Double recallPromedio) {
		this.recallPromedio = recallPromedio;
	}
	/**
	 * @return the totalDocRelevantesTotales
	 */
	public Integer getTotalDocRelevantesTotales() {
		return totalDocRelevantesTotales;
	}
	/**
	 * @param totalDocRelevantesTotales the totalDocRelevantesTotales to set
	 */
	public void setTotalDocRelevantesTotales(Integer totalDocRelevantesTotales) {
		this.totalDocRelevantesTotales = totalDocRelevantesTotales;
	}
	
	
	
	
	
	
	
}
