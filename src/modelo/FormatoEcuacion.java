package modelo;
/**
 * Clase para formatear tabla de la interfaz gráfica para
 * desplegar los intervalos de la ecuación
 * @author Marcos
 *
 */
public class FormatoEcuacion {
	/** Número de intervalo*/
	private Integer numero;
	/** Valor mínimo del intervalo incluyente */
	private Double min;
	/** Valor máximo del intervalo excluyente */
	private Double max;
	/** Valor de la probabilidad del intervalo*/
	private Double valor;
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((max == null) ? 0 : max.hashCode());
		result = prime * result + ((min == null) ? 0 : min.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		if (!(obj instanceof FormatoEcuacion))
			return false;
		FormatoEcuacion other = (FormatoEcuacion) obj;
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
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	/**
	 * Constructor de la clase
	 * @param numero Número del intervalo
	 * @param min Valor mínimo incluyente
	 * @param max Valor máximo excluyente
	 * @param valor Valor de la probabilidad
	 */
	public FormatoEcuacion(Integer numero, Double min, Double max, Double valor) {
		super();
		this.numero = numero;
		this.min = min;
		this.max = max;
		this.valor = valor;
	}
	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
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
	/**
	 * @return the valor
	 */
	public Double getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}
}
