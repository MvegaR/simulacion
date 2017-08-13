package controlador;

import modelo.DistributionEquation;
import modelo.ProbabilisticInterval;
import modelo.ResultadoDataSet;
import modelo.ResultadoDoc;
import modelo.ResultadoQuery;

public class GetEquation {


	private DistributionEquation equation;
	
	
	public GetEquation(Integer cantidadIntervalos, ResultadoDataSet dataSet){
		if(cantidadIntervalos == null || cantidadIntervalos <= 0){
			System.out.println("Cantidad intervalos invalido, se usar� 100 por defecto");
			cantidadIntervalos = 100;
		}
		equation = new DistributionEquation(cantidadIntervalos, dataSet);
		
	}
	
	public DistributionEquation generarEquation(){
		
		generarIntervalos();
		generarProbabilidades();
		
		return equation;
		
	}
	
	private void generarIntervalos(){
		Double intervalo = 1.0/equation.getCantidadIntervalos();
		//incluir primero, excluir �ltimo
		for(Double i = 0.0; i < 1.0; i+= intervalo){
			equation.getIntervalos().add(new ProbabilisticInterval(i, (i+intervalo)));
		}
	}
	
	private void generarProbabilidades(){
		
		Boolean altamenteRelevane = false;
		for(ProbabilisticInterval inte: equation.getIntervalos()){
			/*
			  buscar por cada consulta la cantidad de documentos que tienen similitud
			  dentro del intervalo "inte", incluyendo el min y excluyendo el max
			*/
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
				if((contadorDeDocRelevantes * 1.0)/(contadorDeDoc * 1.0) >= 1.0){ 
					//mayor por que es double, se tiene que considerar el "epsilon de m�quina".
					altamenteRelevane = true;
				}
			}else if(altamenteRelevane){
				equation.getProbabilidades().add(1.0);
			}else{
				equation.getProbabilidades().add(0.0);
			}
			System.out.println("F(x) para "+inte.getMin().floatValue()+" <= X < "+ inte.getMax().floatValue() +" es: "
			+equation.getProbabilidades().get(equation.getProbabilidades().size()-1).toString());
			
			//reniciar contadores:
			contadorDeDoc = 0;
			contadorDeDocRelevantes = 0;
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
		
	
}