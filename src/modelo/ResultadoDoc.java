package modelo;

import java.util.ArrayList;
/**
 * Clase diseñada para almacenar los resultados de los documentos de una consulta, con id de la consulta, id documento
 * distancia conseno, precisión, sensibilidad (recall), número total de palabras y una lista con las palabras
 * presentes en la consulta y documento
 * @author Marcos
 *
 */
public class ResultadoDoc {

	private Integer idQuery;
	private Integer idDoc;
	private Double disCos;
	private Boolean isRel;
	private Double precision;
	private Double recall;
	private Integer tWords;
	private ArrayList<String> words;
	/**
	 * Constructor de la clase.
	 * @param idQuery ID consulta
	 * @param idDoc ID documento
	 * @param disCos Distancia del coseno o similitud
	 * @param isRel Relevencia (jucio de usuario, puede ser null)
	 * @param precision Precisión de la posición o raking del documento para la consulta
	 * @param recall sensibilidad o recall, documentosRelevantesDesplegados / documentosRelevantesTotales
	 * @param tWords total de palabras presentes en la consulta y documento
	 *
	 */
	public ResultadoDoc(Integer idQuery, Integer idDoc, Double disCos, Boolean isRel, Double precision,
			Double recall, Integer tWords) {
		super();
		this.idQuery = idQuery;
		this.idDoc = idDoc;
		this.disCos = disCos;
		this.isRel = isRel;
		this.precision = precision;
		this.recall = recall;
		this.tWords = tWords;
		this.words = new ArrayList<String>();
	}
	@Override
	public String toString() {
		return "ResultadoDocumento [idQuery=" + idQuery + ", idDoc=" + idDoc + ", disCos=" + disCos + ", isRel=" + isRel
				+ ", precision=" + precision + ", recall=" + recall + ", tWords=" + tWords + ", words=" + words + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDoc == null) ? 0 : idDoc.hashCode());
		result = prime * result + ((idQuery == null) ? 0 : idQuery.hashCode());
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
		ResultadoDoc other = (ResultadoDoc) obj;
		if (idDoc == null) {
			if (other.idDoc != null)
				return false;
		} else if (!idDoc.equals(other.idDoc))
			return false;
		if (idQuery == null) {
			if (other.idQuery != null)
				return false;
		} else if (!idQuery.equals(other.idQuery))
			return false;
		return true;
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
	 * @return the idDoc
	 */
	public Integer getIdDoc() {
		return idDoc;
	}
	/**
	 * @param idDoc the idDoc to set
	 */
	public void setIdDoc(Integer idDoc) {
		this.idDoc = idDoc;
	}
	/**
	 * @return the disCos
	 */
	public Double getDisCos() {
		return disCos;
	}
	/**
	 * @param disCos the disCos to set
	 */
	public void setDisCos(Double disCos) {
		this.disCos = disCos;
	}
	/**
	 * @return the isRel
	 */
	public Boolean getIsRel() {
		return isRel;
	}
	/**
	 * @param isRel the isRel to set
	 */
	public void setIsRel(Boolean isRel) {
		this.isRel = isRel;
	}
	/**
	 * @return the precision
	 */
	public Double getPrecision() {
		return precision;
	}
	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	/**
	 * @return the recall
	 */
	public Double getRecall() {
		return recall;
	}
	/**
	 * @param recall the recall to set
	 */
	public void setRecall(Double recall) {
		this.recall = recall;
	}
	/**
	 * @return the tWords
	 */
	public Integer getTWords() {
		return tWords;
	}
	/**
	 * @param tWords the tWords to set
	 */
	public void setTWords(Integer tWords) {
		this.tWords = tWords;
	}
	/**
	 * @return the words
	 */
	public ArrayList<String> getWords() {
		return words;
	}
	/**
	 * @param words the words to set
	 */
	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	
	
	
	
}
