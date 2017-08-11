package modelo;

import java.util.ArrayList;

public class DistributionEquationByDataSet {
	
	private ArrayList<ProbabilisticInterval> intervalos;
	private ArrayList<Double> probabilidades;
	private Integer cantidadIntervalos;
	private ResultadoDataSet dataSet;
	
	public DistributionEquationByDataSet(Integer cantidadIntervalos, ResultadoDataSet dataSet) {
		super();
		this.cantidadIntervalos = cantidadIntervalos;
		this.dataSet = dataSet;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidadIntervalos == null) ? 0 : cantidadIntervalos.hashCode());
		result = prime * result + ((dataSet == null) ? 0 : dataSet.hashCode());
		return result;
	}

	/**
	 * @return the intervalos
	 */
	public ArrayList<ProbabilisticInterval> getIntervalos() {
		return intervalos;
	}

	/**
	 * @param intervalos the intervalos to set
	 */
	public void setIntervalos(ArrayList<ProbabilisticInterval> intervalos) {
		this.intervalos = intervalos;
	}

	/**
	 * @return the probabilidades
	 */
	public ArrayList<Double> getProbabilidades() {
		return probabilidades;
	}

	/**
	 * @param probabilidades the probabilidades to set
	 */
	public void setProbabilidades(ArrayList<Double> probabilidades) {
		this.probabilidades = probabilidades;
	}

	/**
	 * @return the cantidadIntervalos
	 */
	public Integer getCantidadIntervalos() {
		return cantidadIntervalos;
	}

	/**
	 * @param cantidadIntervalos the cantidadIntervalos to set
	 */
	public void setCantidadIntervalos(Integer cantidadIntervalos) {
		this.cantidadIntervalos = cantidadIntervalos;
	}

	/**
	 * @return the dataSet
	 */
	public ResultadoDataSet getDataSet() {
		return dataSet;
	}

	/**
	 * @param dataSet the dataSet to set
	 */
	public void setDataSet(ResultadoDataSet dataSet) {
		this.dataSet = dataSet;
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
		if (!(obj instanceof DistributionEquationByDataSet))
			return false;
		DistributionEquationByDataSet other = (DistributionEquationByDataSet) obj;
		if (cantidadIntervalos == null) {
			if (other.cantidadIntervalos != null)
				return false;
		} else if (!cantidadIntervalos.equals(other.cantidadIntervalos))
			return false;
		if (dataSet == null) {
			if (other.dataSet != null)
				return false;
		} else if (!dataSet.equals(other.dataSet))
			return false;
		return true;
	}
	
		
	

}
