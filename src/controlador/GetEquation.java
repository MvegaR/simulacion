package controlador;

import javafx.scene.control.ProgressBar;
import modelo.DistributionEquation;
import modelo.ProbabilisticInterval;
import modelo.ResultadoDataSet;
import modelo.ResultadoDoc;
import modelo.ResultadoQuery;
/**
 * Clase controladora que realiza la generación de la función de distribución probabilistica global
 * @author Marcos
 *
 */
public class GetEquation {

	/**
	 * Ecuación de distribución a generar
	 */
	private DistributionEquation equation;
	/**
	 * Barra de carga de la interfaz gráfica
	 */
	private ProgressBar barraDeCarga = null;
	
	/**
	 * Constructor para la generación de la función de distribución de probabilidad gobal
	 * @param cantidadIntervalos La cantidad de intervalos de la función a generarar
	 * @param dataSet Puntero al data set a procesar
	 */
	
	public GetEquation(Integer cantidadIntervalos, ResultadoDataSet dataSet){
		if(cantidadIntervalos == null || cantidadIntervalos <= 0){
			System.out.println("Cantidad intervalos invalido, se usarÃ¡ 100 por defecto");
			cantidadIntervalos = 100;
		}
		equation = new DistributionEquation(cantidadIntervalos, dataSet);
		
	}
	/**
	 * Constructor de la clase
	 * @param cantidadIntervalos La cantidad de interbalos de la función a generarar
	 * @param dataSet Puntero al data set a procesar
	 * @param barraDeCarga Barra de carga de la interfaz gráfica
	 */
	public GetEquation(Integer cantidadIntervalos, ResultadoDataSet dataSet, ProgressBar barraDeCarga){
		if(cantidadIntervalos == null || cantidadIntervalos <= 0){
			System.out.println("Cantidad intervalos invalido, se usarÃ¡ 100 por defecto");
			cantidadIntervalos = 100;
		}
		equation = new DistributionEquation(cantidadIntervalos, dataSet);
		this.barraDeCarga = barraDeCarga;
		
	}
	
	/**
	 * Método para la generación de la función de distribución de probabilidad gobal
	 * @return Retorna una función de distribución de probabilidad Ver: {@link DistributionEquation}
	 */
	
	public DistributionEquation generarEquation(){
		
		generarIntervalos();
		generarProbabilidades();
		
		return equation;
		
	}
	
	/**
	 * Método privado para la generación de los intervalos 
	 */
	private void generarIntervalos(){
		Double intervalo = 1.0/equation.getCantidadIntervalos();
		//incluir primero, excluir ï¿½ltimo
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
	 * Método privado para la generación de las probabilidades de cada intervalo
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
	 * @return Barra de carga de la gráfica
	 */
	public ProgressBar getBarraDeCarga() {
		return barraDeCarga;
	}
		
	
}
