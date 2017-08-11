package controlador;


import modelo.DistributionEquation;
import modelo.ProbabilisticInterval;
import modelo.ResultadoDataSet;
import modelo.ResultadoDoc;
import modelo.ResultadoQuery;

public class Simulador {

	private ResultadoDataSet dataSetOriginal;
	private ResultadoDataSet dataSetSimulado;
	private DistributionEquation equation;
	
	/**
	 * @param dataSetOriginal Información del data set original {@link ResultadoDataSet}
	 * @param equation Ecuación de distribución probabilistica global a utilizar en la simualación
	 */
	public Simulador(ResultadoDataSet dataSetOriginal, DistributionEquation equation) {
		super();
		this.dataSetOriginal = dataSetOriginal;
		this.equation = equation;
	}
	
	/**
	 * Método que realiza la simulación de relevancia
	 * @param tolerancia Tolerencia mínima para considerar un documento como relevante
	 * @return {@link ResultadoDataSet} Copia de resultados del dataset original con relevancias simuladas
	 */
	
	public ResultadoDataSet simular(Double tolerancia){
		dataSetSimulado = new ResultadoDataSet(dataSetOriginal.getSetName(), dataSetOriginal.getTotalConsultas(), 
				dataSetOriginal.getTotalConsultas(), dataSetOriginal.getTotalPalabrasComunes(), 
				dataSetOriginal.getTotalpalabrasValidasNoComunes() );
		for(ResultadoQuery resQ: dataSetOriginal.getResultadosConsultas()){
			ResultadoQuery resSimQ = new ResultadoQuery(resQ.getpIn(), resQ.getIdQuery(), resQ.getPrecisionPromedio(), 
					resQ.getRecallPromedio(), -1, 0); //falta documentos relevantes totales y desplegados
			Integer docRelDesplegados = 0;
			for(ResultadoDoc resD: resQ.getResultadosDocumentos()){
				
				ResultadoDoc resSimD = new ResultadoDoc(resD.getIdQuery(), resD.getIdDoc(), resD.getDisCos(), 
						isRelSim(resD.getDisCos(), tolerancia), -1.0, -1.0, resD.gettWords()); 
				//precision y recall dependen de los jucios reales, que no se tienen en una simualción
				if(isRelSim(resD.getDisCos(), tolerancia)){
					docRelDesplegados++;
				}
				resSimQ.getResultadosDocumentos().add(resSimD);
			}
			resSimQ.setTotalDocReleventesDesplegados(docRelDesplegados);
			docRelDesplegados = 0;
			dataSetSimulado.getResultadosConsultas().add(resSimQ);
		}
		
		return dataSetSimulado;
	}
	
	/**
	 * Método para Imprimir y comparar simulacion para copiar a un excel
	 */
	
	public void imprimirParaExcelComparacion(){
		for(Integer i = 0; i < getDataSetOriginal().getResultadosConsultas().size(); i++){
			for(Integer k = 0; 
					k < getDataSetOriginal().getResultadosConsultas().get(i).getResultadosDocumentos().size(); k++){
				ResultadoDoc resD = getDataSetOriginal().getResultadosConsultas().get(i).getResultadosDocumentos().get(k);
				ResultadoDoc resSimD = getDataSetSimulado().getResultadosConsultas().get(i).getResultadosDocumentos().get(k);
				Boolean igual = resD.getIsRel().equals(resSimD.getIsRel());
				Integer idQ = resD.getIdQuery();
				Integer idDoc = resD.getIdDoc();
				Double disCos = resD.getDisCos();
				Boolean relReal = resD.getIsRel();
				Boolean relSimu = resSimD.getIsRel();
				System.out.println(igual+"\t"+idQ+"\t"+idDoc+"\t"+disCos+"\t"+relReal+"\t"+relSimu);
			}
			Integer totalDesplegadosOriginales = getDataSetOriginal().getResultadosConsultas().get(i).getTotalDocReleventesDesplegados();
			Integer totalDesplegadosSimulados = getDataSetSimulado().getResultadosConsultas().get(i).getTotalDocReleventesDesplegados();
			System.out.println("Total desplegados originales:" + totalDesplegadosOriginales);
			System.out.println("Total desplegados simulados"+ totalDesplegadosSimulados);
			System.out.println("");
		}
	}
	
	/**
	 * Determina la relevancia del documento para la similitud entregada
	 * @param sim Distancia conseno o similitud
	 * @param tol Tolerancia mínima para decir que es relevante
	 * @return Si es o no relevante (Boolean)
	 */
	
	private Boolean isRelSim(Double sim, Double tol){
		Boolean isRel = false;
		for(ProbabilisticInterval p: getEquation().getIntervalos()){
			if(p.getMin().compareTo(sim) >= 0 && p.getMax().compareTo(sim) < 0){
				if(getEquation().getProbabilidades().get(getEquation().getIntervalos().indexOf(p)).compareTo(tol) >= 0 ){
					isRel = true;
					return isRel;
				}
			}
		}
		return isRel;
	}
	

	/**
	 * @return the dataSetOriginal
	 */
	public ResultadoDataSet getDataSetOriginal() {
		return dataSetOriginal;
	}
	/**
	 * @param dataSetOriginal the dataSetOriginal to set
	 */
	public void setDataSetOriginal(ResultadoDataSet dataSetOriginal) {
		this.dataSetOriginal = dataSetOriginal;
	}
	/**
	 * @return the dataSetSimulado
	 */
	public ResultadoDataSet getDataSetSimulado() {
		return dataSetSimulado;
	}
	/**
	 * @param dataSetSimulado the dataSetSimulado to set
	 */
	public void setDataSetSimulado(ResultadoDataSet dataSetSimulado) {
		this.dataSetSimulado = dataSetSimulado;
	}
	/**
	 * @return the equation
	 */
	public DistributionEquation getEquation() {
		return equation;
	}
	/**
	 * @param equation the equation to set
	 */
	public void setEquation(DistributionEquation equation) {
		this.equation = equation;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Simulador [dataSetOriginal=" + dataSetOriginal + ", dataSetSimulado=" + dataSetSimulado + ", equation="
				+ equation + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataSetOriginal == null) ? 0 : dataSetOriginal.hashCode());
		result = prime * result + ((dataSetSimulado == null) ? 0 : dataSetSimulado.hashCode());
		result = prime * result + ((equation == null) ? 0 : equation.hashCode());
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
		if (!(obj instanceof Simulador))
			return false;
		Simulador other = (Simulador) obj;
		if (dataSetOriginal == null) {
			if (other.dataSetOriginal != null)
				return false;
		} else if (!dataSetOriginal.equals(other.dataSetOriginal))
			return false;
		if (dataSetSimulado == null) {
			if (other.dataSetSimulado != null)
				return false;
		} else if (!dataSetSimulado.equals(other.dataSetSimulado))
			return false;
		if (equation == null) {
			if (other.equation != null)
				return false;
		} else if (!equation.equals(other.equation))
			return false;
		return true;
	}
	
	
	
	
	
	
}
