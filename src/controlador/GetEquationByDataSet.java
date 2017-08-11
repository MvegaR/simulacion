package controlador;

import modelo.DistributionEquationByDataSet;
import modelo.ProbabilisticInterval;
import modelo.ResultadoDataSet;
import modelo.ResultadoDoc;
import modelo.ResultadoQuery;

public class GetEquationByDataSet {


	private DistributionEquationByDataSet equation;
	
	
	public GetEquationByDataSet(Integer cantidadIntervalos, ResultadoDataSet dataSet){
		if(cantidadIntervalos == null || cantidadIntervalos <= 0){
			System.out.println("Cantidad intervalos invalido, se usará 20 por defecto");
			cantidadIntervalos = 20;
		}
		equation = new DistributionEquationByDataSet(cantidadIntervalos, dataSet);
		
	}
	
	public DistributionEquationByDataSet generarEquation(){
		
		generarIntervalos();
		generarProbabilidades();
		
		return equation;
		
	}
	
	private void generarIntervalos(){
		Double intervalo = 1.0/equation.getCantidadIntervalos();
		//incluir primero, excluir último
		for(Double i = 0.0; i < 1.0; i+= intervalo){
			equation.getIntervalos().add(new ProbabilisticInterval(i, (i+intervalo)));
		}
	}
	
	private void generarProbabilidades(){
		for(ProbabilisticInterval inte: equation.getIntervalos()){
			/*
			  buscar por cada consulta la cantidad de documentos que tienen similitud
			  dentro del intervalo "inte", incluyendo el min y excluyendo el max
			*/
			Integer contadorDeDoc = 0;
			Integer contadorDeDocRelevantes = 0;
			 System.out.println(( equation.getDataSet().getResultadosConsultas()));
			for(ResultadoQuery resQ: equation.getDataSet().getResultadosConsultas()){
				for(ResultadoDoc resD: resQ.getResultadosDocumentos()){
					if(resD.getDisCos().compareTo(inte.getMin()) >= 0 
							&& resD.getDisCos().compareTo(inte.getMax()) < 0){
						contadorDeDoc++;
						if(resD.getIsRel()){
							contadorDeDocRelevantes++;
							System.out.println(resD.getIdQuery() +"es relevante con" + resD.getIdDoc());
						}
					}
				}
			}
			if(contadorDeDocRelevantes!= 0.0){
				equation.getProbabilidades().add( (contadorDeDoc * 1.0)/(contadorDeDocRelevantes * 1.0));
			}else{
				equation.getProbabilidades().add(0.0);
			}
			System.out.println("Prob int "+"min="+ inte.getMin()+" y max ="+ inte.getMax() +" es:"+equation.getProbabilidades().get(equation.getProbabilidades().size()-1).toString());
			
			//reniciar contadores:
			contadorDeDoc = 0;
			contadorDeDocRelevantes = 0;
		}
	}
	
	

	/**
	 * @return the equation
	 */
	public DistributionEquationByDataSet getEquation() {
		return equation;
	}

	/**
	 * @param equation the equation to set
	 */
	public void setEquation(DistributionEquationByDataSet equation) {
		this.equation = equation;
	}
		
	
}
