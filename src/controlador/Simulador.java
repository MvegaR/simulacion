package controlador;

import java.util.ArrayList;
import javafx.scene.control.ProgressBar;
import modelo.DistributionEquation;
import modelo.FormatoResumenSimulacion;
import modelo.FormatoSimulacion;
import modelo.ProbabilisticInterval;
import modelo.ResultadoDataSet;
import modelo.ResultadoDoc;
import modelo.ResultadoQuery;

public class Simulador {

	private ResultadoDataSet dataSetOriginal;
	private ResultadoDataSet dataSetSimulado;
	private DistributionEquation equation;
	private Double tolerancia;
	private ProgressBar bar;
	private ArrayList<ArrayList<FormatoSimulacion>> resultados;
	private ArrayList<FormatoResumenSimulacion> resultadosResumen;
	private Integer totalGlobalFallados;
	private Integer totalGlobalAcertados;
	private Integer totalRelevantesFallados;
	private Integer totalGlobalRealesYSimulados;
	private Integer TtotalRelevantesDesplegados;
	private Integer TtotalRelevantesSimuladosDesplegados;
	
	

	/**
	 * @param dataSetOriginal Información del data set original {@link ResultadoDataSet}
	 * @param equation Ecuación de distribución probabilistica global a utilizar en la simualación
	 */
	public Simulador(ResultadoDataSet dataSetOriginal, DistributionEquation equation) {
		super();
		this.dataSetOriginal = dataSetOriginal;
		this.equation = equation;
		this.resultados = new ArrayList<>();
		this.resultadosResumen = new ArrayList<>();
		this.totalGlobalFallados = 0;
		this.totalGlobalAcertados = 0;
		this.totalRelevantesFallados = 0;
		this.totalGlobalRealesYSimulados = 0;
		this.TtotalRelevantesDesplegados = 0;
		this.TtotalRelevantesSimuladosDesplegados = 0;
	}
	
	/**
	 * Método que realiza la simulación de relevancia
	 * @param tolerancia Tolerencia mínima para considerar un documento como relevante
	 * @return {@link ResultadoDataSet} Copia de resultados del dataset original con relevancias simuladas
	 */
	
	public ResultadoDataSet simular(Double tolerancia){
		this.tolerancia = tolerancia;
		dataSetSimulado = new ResultadoDataSet(dataSetOriginal.getSetName(), dataSetOriginal.getTotalConsultas(), 
				dataSetOriginal.getTotalConsultas(), dataSetOriginal.getTotalPalabrasComunes(), 
				dataSetOriginal.getTotalpalabrasValidasNoComunes() );
		Integer count = 0;
		for(ResultadoQuery resQ: dataSetOriginal.getResultadosConsultas()){
			ResultadoQuery resSimQ = new ResultadoQuery(resQ.getpIn(), resQ.getIdQuery(), resQ.getPrecisionPromedio(), 
					resQ.getRecallPromedio(), -1, 0); //falta documentos relevantes totales y desplegados
			Integer docRelDesplegados = 0;
			for(ResultadoDoc resD: resQ.getResultadosDocumentos()){
				
				ResultadoDoc resSimD = new ResultadoDoc(resD.getIdQuery(), resD.getIdDoc(), resD.getDisCos(), 
						isRelSim(resD.getDisCos(), tolerancia), -1.0, -1.0, resD.getTWords()); 
				//precision y recall dependen de los jucios reales, que no se tienen en una simualción
				if(isRelSim(resD.getDisCos(), tolerancia)){
					docRelDesplegados++;
				}
				resSimQ.getResultadosDocumentos().add(resSimD);
			}
			resSimQ.setTotalDocReleventesDesplegados(docRelDesplegados);
			docRelDesplegados = 0;
			dataSetSimulado.getResultadosConsultas().add(resSimQ);
			count++;
			if(getBar()!=null){
				getBar().setProgress((count/(double)dataSetOriginal.getResultadosConsultas().size())/0.25);
			}
		}
		
		return dataSetSimulado;
	}
	

	/**
	 * Método para generar los datos para mostrarlos en tablas
	 */
	public void generarResultados(){
		for(Integer i = 0; i < getDataSetOriginal().getResultadosConsultas().size(); i++){
			ArrayList<FormatoSimulacion> formatoSim = new ArrayList<>();
			getResultados().add(formatoSim);
			Integer totalRelevantesRealesSimulados = 0;
			Integer totalFallados = 0;
			Integer totalAcertados = 0;
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
				formatoSim.add(new FormatoSimulacion(igual, idQ, idDoc, disCos, relReal, relSimu));
				if(resD.getIsRel() && resSimD.getIsRel()){
					totalRelevantesRealesSimulados++;
					totalGlobalRealesYSimulados++;
				}
				if(igual){
					totalAcertados++;
					this.totalGlobalAcertados++;
				}else{
					totalFallados++;
					this.totalGlobalFallados++;
				}
				if(!igual && resD.getIsRel()){
					this.totalRelevantesFallados++;
				}
			}
			Integer totalDesplegadosOriginales = getDataSetOriginal().getResultadosConsultas().get(i).getTotalDocReleventesDesplegados();
			Integer totalDesplegadosSimulados = getDataSetSimulado().getResultadosConsultas().get(i).getTotalDocReleventesDesplegados();
			this.TtotalRelevantesDesplegados+=totalDesplegadosOriginales;
			this.TtotalRelevantesSimuladosDesplegados+=totalRelevantesRealesSimulados;
			getResultadosResumen().add(new FormatoResumenSimulacion(totalDesplegadosOriginales, 
					totalDesplegadosSimulados, totalRelevantesRealesSimulados, 
					totalAcertados, totalFallados, tolerancia));
			getBar().setProgress(0.25+ (double)i/(double)getDataSetOriginal().getResultadosConsultas().size());
		}
		
		
	}
	

	/**
	 * Método para Imprimir y comparar simulacion para copiar a un excel
	 */
	public void imprimirParaExcelComparacion(){
		Integer TtotalRelevantesDesplegados = 0;
		Integer TtotalRelevantesSimuladosDesplegados = 0;
		for(Integer i = 0; i < getDataSetOriginal().getResultadosConsultas().size(); i++){
			Integer totalRelevantesRealesSimulados = 0;
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
				if(resD.getIsRel() && resSimD.getIsRel()){
					totalRelevantesRealesSimulados++;
				}
			}
			Integer totalDesplegadosOriginales = getDataSetOriginal().getResultadosConsultas().get(i).getTotalDocReleventesDesplegados();
			Integer totalDesplegadosSimulados = getDataSetSimulado().getResultadosConsultas().get(i).getTotalDocReleventesDesplegados();
			System.out.println("Total desplegados originales"+"\t" + totalDesplegadosOriginales);
		
			System.out.println("Total desplegados simulados"+"\t"+ totalDesplegadosSimulados);
			System.out.println("Total relevantes originales y simulados\t"+totalRelevantesRealesSimulados);
			System.out.println("Tolerancia utilizada"+"\t"+tolerancia);
			TtotalRelevantesDesplegados+=totalDesplegadosOriginales;
			TtotalRelevantesSimuladosDesplegados+=totalRelevantesRealesSimulados;
			System.out.println("");
			System.out.println("");
		}
		System.out.println(TtotalRelevantesSimuladosDesplegados*100/TtotalRelevantesDesplegados);
	}
	
	/**
	 * Determina la relevancia del documento para la similitud entregada
	 * @param sim Distancia conseno o similitud
	 * @param tol Tolerancia mínima para decir que es relevante
	 * @return Si es o no relevante (Boolean)
	 */
	
	private Boolean isRelSim(Double sim, Double tol){
		Boolean isRel = false;
		Integer con = 0;
		for(ProbabilisticInterval p: getEquation().getIntervalos()){
			if(p.getMin().compareTo(sim) <= 0 && p.getMax().compareTo(sim) > 0){
				if(getEquation().getProbabilidades().get(con).compareTo(tol) >= 0 ){
					isRel = true;
					return isRel;
				}
			}
			con++;
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
	/**
	 * @return the tolerancia
	 */
	public Double getTolerancia() {
		return tolerancia;
	}

	/**
	 * @param tolerancia the tolerancia to set
	 */
	public void setTolerancia(Double tolerancia) {
		this.tolerancia = tolerancia;
	}

	/**
	 * @return the bar
	 */
	public ProgressBar getBar() {
		return bar;
	}

	/**
	 * @param bar the bar to set
	 */
	public void setBar(ProgressBar bar) {
		this.bar = bar;
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

	/**
	 * @return the resultados
	 */
	public ArrayList<ArrayList<FormatoSimulacion>> getResultados() {
		return resultados;
	}

	/**
	 * @return the resultadosResumen
	 */
	public ArrayList<FormatoResumenSimulacion> getResultadosResumen() {
		return resultadosResumen;
	}

	/**
	 * @param resultadosResumen the resultadosResumen to set
	 */
	public void setResultadosResumen(ArrayList<FormatoResumenSimulacion> resultadosResumen) {
		this.resultadosResumen = resultadosResumen;
	}

	/**
	 * @param resultados the resultados to set
	 */
	public void setResultados(ArrayList<ArrayList<FormatoSimulacion>> resultados) {
		this.resultados = resultados;
	}

	/**
	 * @return the totalGlobalFallados
	 */
	public Integer getTotalGlobalFallados() {
		return totalGlobalFallados;
	}

	/**
	 * @param totalGlobalFallados the totalGlobalFallados to set
	 */
	public void setTotalGlobalFallados(Integer totalGlobalFallados) {
		this.totalGlobalFallados = totalGlobalFallados;
	}

	/**
	 * @return the totalGlobalAcertados
	 */
	public Integer getTotalGlobalAcertados() {
		return totalGlobalAcertados;
	}

	/**
	 * @param totalGlobalAcertados the totalGlobalAcertados to set
	 */
	public void setTotalGlobalAcertados(Integer totalGlobalAcertados) {
		this.totalGlobalAcertados = totalGlobalAcertados;
	}
	/**
	 * @return the totalRelevantesFallados
	 */
	public Integer getTotalRelevantesFallados() {
		return totalRelevantesFallados;
	}

	/**
	 * @param totalRelevantesFallados the totalRelevantesFallados to set
	 */
	public void setTotalRelevantesFallados(Integer totalRelevantesFallados) {
		this.totalRelevantesFallados = totalRelevantesFallados;
	}

	/**
	 * @return the totalGlobalRealesYSimulados
	 */
	public Integer getTotalGlobalRealesYSimulados() {
		return totalGlobalRealesYSimulados;
	}

	/**
	 * @param totalGlobalRealesYSimulados the totalGlobalRealesYSimulados to set
	 */
	public void setTotalGlobalRealesYSimulados(Integer totalGlobalRealesYSimulados) {
		this.totalGlobalRealesYSimulados = totalGlobalRealesYSimulados;
	}
}
