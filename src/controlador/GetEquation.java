package controlador;

import javafx.scene.control.ProgressBar;
import modelo.DistributionEquation;
import modelo.ProbabilisticInterval;
import modelo.ResultadoDataSet;
import modelo.ResultadoDoc;
import modelo.ResultadoQuery;
/**
 * Clase controladora que realiza la generaci�n de la funci�n de distribuci�n probabilistica global
 * @author Marcos
 *
 */
public class GetEquation {

	/**
	 * Ecuaci�n de distribuci�n a generar
	 */
	private DistributionEquation equation;
	/**
	 * Barra de carga de la interfaz gr�fica
	 */
	private ProgressBar barraDeCarga = null;
	
	/**
	 * Constructor para la generaci�n de la funci�n de distribuci�n de probabilidad gobal
	 * @param cantidadIntervalos La cantidad de intervalos de la funci�n a generarar
	 * @param dataSet Puntero al data set a procesar
	 */
	
	public GetEquation(Integer cantidadIntervalos, ResultadoDataSet dataSet){
		if(cantidadIntervalos == null || cantidadIntervalos <= 0){
			System.out.println("Cantidad intervalos invalido, se usará 100 por defecto");
			cantidadIntervalos = 100;
		}
		equation = new DistributionEquation(cantidadIntervalos, dataSet);
		
	}
	/**
	 * Constructor de la clase
	 * @param cantidadIntervalos La cantidad de interbalos de la funci�n a generarar
	 * @param dataSet Puntero al data set a procesar
	 * @param barraDeCarga Barra de carga de la interfaz gr�fica
	 */
	public GetEquation(Integer cantidadIntervalos, ResultadoDataSet dataSet, ProgressBar barraDeCarga){
		if(cantidadIntervalos == null || cantidadIntervalos <= 0){
			System.out.println("Cantidad intervalos invalido, se usará 100 por defecto");
			cantidadIntervalos = 100;
		}
		equation = new DistributionEquation(cantidadIntervalos, dataSet);
		this.barraDeCarga = barraDeCarga;
		
	}
	
	/**
	 * M�todo para la generaci�n de la funci�n de distribuci�n de probabilidad gobal
	 * @return Retorna una funci�n de distribuci�n de probabilidad Ver: {@link DistributionEquation}
	 */
	
	public DistributionEquation generarEquation(){
		
		generarIntervalos();
		generarProbabilidades();
		
		return equation;
		
	}
	
	/**
	 * M�todo privado para la generaci�n de los intervalos 
	 */
	private void generarIntervalos(){
		Double intervalo = 1.0/equation.getCantidadIntervalos();
		//incluir primero, excluir �ltimo
		for(Double i = 0.0; i < 1.0; i+= intervalo){
			equation.getIntervalos().add(new ProbabilisticInterval(i, (i+intervalo)));
			if(barraDeCarga != null){
				barraDeCarga.setProgress(i/0.33);
			}
		}
		if(barraDeCarga != null){
			barraDeCarga.setProgress(0.33);
		}
	}
	/**
	 * M�todo privado para la generaci�n de las probabilidades de cada intervalo
	 */
	private void generarProbabilidades(){
		
		Double maxSim = 0.0;
		for(ResultadoQuery q: equation.getDataSet().getResultadosConsultas()){
			for(ResultadoDoc d: q.getResultadosDocumentos()){
				if(d.getDisCos() > maxSim){
					maxSim = d.getDisCos();
				}
			}
		}
		for(ProbabilisticInterval inte: equation.getIntervalos()){
			/*
			  buscar por cada consulta la cantidad de documentos que tienen similitud
			  dentro del intervalo "inte", incluyendo el min y excluyendo el max
			*/
			int coun = 0;
			Integer contadorDeDoc = 0;
			Integer contadorDeDocRelevantes = 0;
			for(ResultadoQuery resQ: equation.getDataSet().getResultadosConsultas()){
				for(ResultadoDoc resD: resQ.getResultadosDocumentos()){
					if(resD.getDisCos().compareTo(inte.getMin()) >= 0 
							&& resD.getDisCos().compareTo(inte.getMax()) < 0){
						contadorDeDoc++;
						if(resD.getIsRel()){
							contadorDeDocRelevantes++;
						}
					}
				}
			}
			if(contadorDeDocRelevantes!= 0.0){
				equation.getProbabilidades().add( (contadorDeDocRelevantes * 1.0)/(contadorDeDoc * 1.0));
			}
			else{
				equation.getProbabilidades().add(0.0);
			}
			System.out.println("F(x) para "+inte.getMin().floatValue()+" <= X < "+ inte.getMax().floatValue() +" es: "
			+equation.getProbabilidades().get(equation.getProbabilidades().size()-1).toString());
			
			//reniciar contadores:
			contadorDeDoc = 0;
			contadorDeDocRelevantes = 0;
			//para la barra de carga
			coun+=1;
			if(barraDeCarga != null){
				barraDeCarga.setProgress(0.33 + (coun/equation.getIntervalos().size())/0.66);
			}
		}
		if(barraDeCarga != null){
			barraDeCarga.setProgress(1.0);
		}
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
	 * 
	 * @param barraDeCarga La barra de carga
	 */
	public void setBarraDeCarga(ProgressBar barraDeCarga) {
		this.barraDeCarga = barraDeCarga;
	}
	
	/**
	 * 
	 * @return Barra de carga de la gr�fica
	 */
	public ProgressBar getBarraDeCarga() {
		return barraDeCarga;
	}
		
	
}
