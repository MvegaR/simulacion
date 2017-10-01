package modelo;
/**
 * Clase modelo para desplegar en la tabla resultandos de la simulación
 * @author Marcos
 *
 */
public class FormatoSimulacion {
	/**Si es igual o no el jucio de usuario del archivo de relevancia con la simulación */
	private Boolean igual;
	/**Identificador de la consulta */
	private Integer idq;
	/**Identificador del documento */
	private Integer iddoc;
	/**Valor de similitud o distancia conseno */
	private Double sim;
	/**Relevancia o jucio de usuario del fichero de relevancia */
	private Boolean userRelReal;
	/**Relevencia o jucio de usuario simulado */
	private Boolean userRelSim;
	
	/**
	 * Constructor de la clase
	 * @param igual Si es igual o no el jucio de usuario del archivo de relevancia con la simulación
	 * @param idq Identificador de la consulta
	 * @param iddoc Identificador del documento
	 * @param sim Valor de similitud o distancia conseno
	 * @param userRelReal Relevancia o jucio de usuario del fichero de relevancia
	 * @param userRelSim Relevencia o jucio de usuario simulado
	 */
	public FormatoSimulacion(Boolean igual, Integer idq, Integer iddoc, Double sim, Boolean userRelReal,
			Boolean userRelSim) {
		super();
		this.igual = igual;
		this.idq = idq;
		this.iddoc = iddoc;
		this.sim = sim;
		this.userRelReal = userRelReal;
		this.userRelSim = userRelSim;
	}
	/**
	 * @return the igual
	 */
	public Boolean getIgual() {
		return igual;
	}
	/**
	 * @param igual the igual to set
	 */
	public void setIgual(Boolean igual) {
		this.igual = igual;
	}
	/**
	 * @return the idq
	 */
	public Integer getIdq() {
		return idq;
	}
	/**
	 * @param idq the idq to set
	 */
	public void setIdq(Integer idq) {
		this.idq = idq;
	}
	/**
	 * @return the iddoc
	 */
	public Integer getIddoc() {
		return iddoc;
	}
	/**
	 * @param iddoc the iddoc to set
	 */
	public void setIddoc(Integer iddoc) {
		this.iddoc = iddoc;
	}
	/**
	 * @return the sim
	 */
	public Double getSim() {
		return sim;
	}
	/**
	 * @param sim the sim to set
	 */
	public void setSim(Double sim) {
		this.sim = sim;
	}
	/**
	 * @return the userRelReal
	 */
	public Boolean getUserRelReal() {
		return userRelReal;
	}
	/**
	 * @param userRelReal the userRelReal to set
	 */
	public void setUserRelReal(Boolean userRelReal) {
		this.userRelReal = userRelReal;
	}
	/**
	 * @return the userRelSim
	 */
	public Boolean getUserRelSim() {
		return userRelSim;
	}
	/**
	 * @param userRelSim the userRelSim to set
	 */
	public void setUserRelSim(Boolean userRelSim) {
		this.userRelSim = userRelSim;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iddoc == null) ? 0 : iddoc.hashCode());
		result = prime * result + ((idq == null) ? 0 : idq.hashCode());
		result = prime * result + ((igual == null) ? 0 : igual.hashCode());
		result = prime * result + ((sim == null) ? 0 : sim.hashCode());
		result = prime * result + ((userRelReal == null) ? 0 : userRelReal.hashCode());
		result = prime * result + ((userRelSim == null) ? 0 : userRelSim.hashCode());
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
		if (!(obj instanceof FormatoSimulacion))
			return false;
		FormatoSimulacion other = (FormatoSimulacion) obj;
		if (iddoc == null) {
			if (other.iddoc != null)
				return false;
		} else if (!iddoc.equals(other.iddoc))
			return false;
		if (idq == null) {
			if (other.idq != null)
				return false;
		} else if (!idq.equals(other.idq))
			return false;
		if (igual == null) {
			if (other.igual != null)
				return false;
		} else if (!igual.equals(other.igual))
			return false;
		if (sim == null) {
			if (other.sim != null)
				return false;
		} else if (!sim.equals(other.sim))
			return false;
		if (userRelReal == null) {
			if (other.userRelReal != null)
				return false;
		} else if (!userRelReal.equals(other.userRelReal))
			return false;
		if (userRelSim == null) {
			if (other.userRelSim != null)
				return false;
		} else if (!userRelSim.equals(other.userRelSim))
			return false;
		return true;
	}
	
	

}
