package modelo;
/**
 * Clase diseñada para desplegar el resumen de la simulación de una consulta
 * @author Marcos
 *
 */
public class FormatoResumenSimulacion {
	/** Total relevantes desplegados */
	private Integer totalDesplegadosOriginales;
	/** Total relevantes simulados */
	private Integer totalDesplegadosSimulados;
	/** Total relevantes originales y simulados */
	private Integer totalSimuladosYOriginales;
	/** Total acertados */
	private Integer totalAcertados;
	/** Total fallados*/
	private Integer totalFallados;
	/** Tolerancia utilizada */
	private Double tolerancia;
	/**
	 *
	 * @param totalDesplegadosOriginales Total relevantes desplegados en el ranking original
	 * @param totalDesplegadosSimulados Total relevantes simulados
	 * @param totalSimuladosYOriginales Total de relevantes originales y simulados
	 * @param totalAcertados Total acertartados
	 * @param totalFallados Total fallados
	 * @param tolerancia Tolerancia utilizada en la simulación
	 */
	public FormatoResumenSimulacion(Integer totalDesplegadosOriginales, Integer totalDesplegadosSimulados,
			Integer totalSimuladosYOriginales, Integer totalAcertados, Integer totalFallados, Double tolerancia) {
		super();
		this.totalDesplegadosOriginales = totalDesplegadosOriginales;
		this.totalDesplegadosSimulados = totalDesplegadosSimulados;
		this.totalSimuladosYOriginales = totalSimuladosYOriginales;
		this.totalAcertados = totalAcertados;
		this.totalFallados = totalFallados;
		this.tolerancia = tolerancia;
	}
	/**
	 * @return the totalDesplegadosOriginales
	 */
	public Integer getTotalDesplegadosOriginales() {
		return totalDesplegadosOriginales;
	}
	/**
	 * @param totalDesplegadosOriginales the totalDesplegadosOriginales to set
	 */
	public void setTotalDesplegadosOriginales(Integer totalDesplegadosOriginales) {
		this.totalDesplegadosOriginales = totalDesplegadosOriginales;
	}
	/**
	 * @return the totalDesplegadosSimulados
	 */
	public Integer getTotalDesplegadosSimulados() {
		return totalDesplegadosSimulados;
	}
	/**
	 * @param totalDesplegadosSimulados the totalDesplegadosSimulados to set
	 */
	public void setTotalDesplegadosSimulados(Integer totalDesplegadosSimulados) {
		this.totalDesplegadosSimulados = totalDesplegadosSimulados;
	}
	/**
	 * @return the totalSimuladosYOriginales
	 */
	public Integer getTotalSimuladosYOriginales() {
		return totalSimuladosYOriginales;
	}
	/**
	 * @param totalSimuladosYOriginales the totalSimuladosYOriginales to set
	 */
	public void setTotalSimuladosYOriginales(Integer totalSimuladosYOriginales) {
		this.totalSimuladosYOriginales = totalSimuladosYOriginales;
	}
	/**
	 * @return the totalAcertados
	 */
	public Integer getTotalAcertados() {
		return totalAcertados;
	}
	/**
	 * @param totalAcertados the totalAcertados to set
	 */
	public void setTotalAcertados(Integer totalAcertados) {
		this.totalAcertados = totalAcertados;
	}
	/**
	 * @return the totalFallados
	 */
	public Integer getTotalFallados() {
		return totalFallados;
	}
	/**
	 * @param totalFallados the totalFallados to set
	 */
	public void setTotalFallados(Integer totalFallados) {
		this.totalFallados = totalFallados;
	}
	/**
	 * @return the tolerencia
	 */
	public Double getTolerancia() {
		return tolerancia;
	}
	/**
	 *
	 * @param tolerancia Tolerancia de la simulación
	 */
	public void setTolerancia(Double tolerancia) {
		this.tolerancia = tolerancia;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tolerancia == null) ? 0 : tolerancia.hashCode());
		result = prime * result + ((totalAcertados == null) ? 0 : totalAcertados.hashCode());
		result = prime * result + ((totalDesplegadosOriginales == null) ? 0 : totalDesplegadosOriginales.hashCode());
		result = prime * result + ((totalDesplegadosSimulados == null) ? 0 : totalDesplegadosSimulados.hashCode());
		result = prime * result + ((totalFallados == null) ? 0 : totalFallados.hashCode());
		result = prime * result + ((totalSimuladosYOriginales == null) ? 0 : totalSimuladosYOriginales.hashCode());
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
		if (!(obj instanceof FormatoResumenSimulacion))
			return false;
		FormatoResumenSimulacion other = (FormatoResumenSimulacion) obj;
		if (tolerancia == null) {
			if (other.tolerancia != null)
				return false;
		} else if (!tolerancia.equals(other.tolerancia))
			return false;
		if (totalAcertados == null) {
			if (other.totalAcertados != null)
				return false;
		} else if (!totalAcertados.equals(other.totalAcertados))
			return false;
		if (totalDesplegadosOriginales == null) {
			if (other.totalDesplegadosOriginales != null)
				return false;
		} else if (!totalDesplegadosOriginales.equals(other.totalDesplegadosOriginales))
			return false;
		if (totalDesplegadosSimulados == null) {
			if (other.totalDesplegadosSimulados != null)
				return false;
		} else if (!totalDesplegadosSimulados.equals(other.totalDesplegadosSimulados))
			return false;
		if (totalFallados == null) {
			if (other.totalFallados != null)
				return false;
		} else if (!totalFallados.equals(other.totalFallados))
			return false;
		if (totalSimuladosYOriginales == null) {
			if (other.totalSimuladosYOriginales != null)
				return false;
		} else if (!totalSimuladosYOriginales.equals(other.totalSimuladosYOriginales))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormatoResumenSimulacion [totalDesplegadosOriginales=" + totalDesplegadosOriginales
				+ ", totalDesplegadosSimulados=" + totalDesplegadosSimulados + ", totalSimuladosYOriginales="
				+ totalSimuladosYOriginales + ", totalAcertados=" + totalAcertados + ", totalFallados=" + totalFallados
				+ ", tolerancia=" + tolerancia + "]";
	}
}
