package modelo;
/**
 * Clase diseñada para representar un intervalo con un mínimo y un máximo 
 * como estructura básica para la función de distribución de probabilidad 
 * global a generar.
 * @author Marcos
 *
 */
public class ProbabilisticInterval {
	private Double min;
	private Double max;
	/**
	 * Constructor de la clase
	 * @param min Valor mínimo del intervalo
	 * @param max Valor máximo del intervalo
	 */
	public ProbabilisticInterval(Double min, Double max) {
		super();
		this.min = min;
		this.max = max;
		
	}
	/**
	 * @return the min
	 */
	public Double getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(Double min) {
		this.min = min;
	}
	/**
	 * @return the max
	 */
	public Double getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(Double max) {
		this.max = max;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((max == null) ? 0 : max.hashCode());
		result = prime * result + ((min == null) ? 0 : min.hashCode());
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
		if (!(obj instanceof ProbabilisticInterval))
			return false;
		ProbabilisticInterval other = (ProbabilisticInterval) obj;
		if (max == null) {
			if (other.max != null)
				return false;
		} else if (!max.equals(other.max))
			return false;
		if (min == null) {
			if (other.min != null)
				return false;
		} else if (!min.equals(other.min))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProbabilisticInterval [min=" + min + ", max=" + max + "]";
	}
}
