package analisis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;

import documentosConsultas.Consulta;
import documentosConsultas.Documento;
import documentosConsultas.Relevancia;

/**
 * 
 * Matriz de frecuencias, por cada palabra del SortedSet "palabras", se registrará su frecuencia por cada documento, 
 * luego se utiliza para crear la matriz tf-idf las frecuencias por su valor calculado, considerando primero los documentos relevante
 * de la lista "relevancias", obtienendo el log de la divición del total de frecuencia entre el total de documentos chequeados.
 * La sumatoria es la precisión.
 * Los valores se almacenan en una lista de listas de double.
 * La clase Matriz incluye heuristicas definidas por el profesor para obtener precision de consultas.
 */

public class MatrizFrecuencia {
	
	/**
	 * 
	 * Clase que relaciona un documento con un determinado valor de similitud, 
	 * para poder ordenar de mayor a menor por similitud sin perder la referencia del documento
	 *
	 */
	class Similitud{
		private Double valor;
		private Integer idDocumento;
		public Similitud(Double valor, Integer idDocumento) {
			this.valor = valor;
			this.idDocumento = idDocumento;
		}
		public Double getValor() {
			return valor;
		}
		public Integer getIdDocumento() {
			return idDocumento;
		}
	
	}

	private SortedSet<String> palabras; //Conjunto de palabras sin repetir, minusculas, ordenadas por alfabeto
	private ArrayList<ArrayList<Integer>> matrizFrecuncias;
	private ArrayList<ArrayList<Double>> matrizFrecunciasInversas;
	private ArrayList<Documento> documentos;
	private ArrayList<Consulta> consultas;
	private ArrayList<Relevancia> relevancias;
	
	
	
	public MatrizFrecuencia(SortedSet<String> palabras, ArrayList<Documento> documentos, ArrayList<Consulta> consultas, ArrayList<Relevancia> relevancias) {
		this.palabras = palabras;
		this.matrizFrecuncias = new ArrayList<>();
		this.matrizFrecunciasInversas = new ArrayList<>();
		this.documentos = documentos;
		this.consultas = consultas;
		this.relevancias = relevancias;
	}
	
	public void obtenerPrecision(){
		
		//ArrayList<Double> precisiones = new ArrayList<>();
		this.obtenerFrecuencias(); //se guardan en matrizFrecuencias
		System.out.println("Matriz de frecuencias calculada");
		this.obtenerFrecuenciasInversas(); // se guardan en matrizFrecuencaisInversa
		System.out.println("matriz de frecuencias inversas calculadas");
		for(Consulta q: consultas){
			System.out.println("Inicio consulta :"+q.getId());
			ArrayList<Similitud> similitudes = calculoSimilitud(q);
			similitudes.sort(new Comparator<Similitud>() { //Ordenando por valor mayor primero.
				@Override
				public int compare(Similitud o1, Similitud o2) {
					return o2.getValor().compareTo(o1.getValor());
				}
			});
			Double precision = 0.0;
			Integer contadorDocumentosRelevantes = 0;
			Integer contadorTotalDocumentos = 0;
			Integer mostrarPrimeros = 20;
			for(Similitud s: similitudes){
				contadorTotalDocumentos++;
				if(isRelevante(q.getId(), s.getIdDocumento())){
					contadorDocumentosRelevantes++;
					precision += (contadorDocumentosRelevantes *1.0)/(contadorTotalDocumentos*1.0);
				}else{
					precision += (contadorDocumentosRelevantes *1.0)/(contadorTotalDocumentos*1.0);
				}
				if(mostrarPrimeros > 0){
					System.out.println("\tQ"+q.getId()+" Documento: " + s.getIdDocumento() + " Distancia:"+ s.getValor() + " Relevante: " +isRelevante(q.getId(), s.getIdDocumento()));
					mostrarPrimeros--;
				}
			}
			precision/=contadorTotalDocumentos;
			System.out.println("Pricisión consulta Q"+q.getId()+" es: "+ String.format("%.10f", precision)+" Docs Relevantes: "+contadorDocumentosRelevantes);
						
		}
		
		
	}
	
	private Boolean isRelevante(Integer idConsulta, Integer idDocumento){
		for(Relevancia r: relevancias){
			if(r.getQueryID().equals(idConsulta) && r.getDocID().equals(idDocumento)){
				return true;
			}
		}
		return false;
	}
	

	
	/**
	 * Metodo que rellena la matriz con las frecuencias de las palabras (set de palabras), de cada documento.
	 */
	public void obtenerFrecuencias(){
		//quitar documentos sin cuerpo
		matrizFrecuncias.clear();
		for(Documento d: documentos){
			if(!d.getPalabrasValidas().isEmpty()){ //quitando documentos sin cuerpo
				ArrayList<Integer> lista = new ArrayList<>();
				matrizFrecuncias.add(lista);
				lista.add(d.getId()); //agregando id documento al inicio de cada lista
				for(String p: palabras){
					lista.add(Collections.frequency(d.getPalabrasValidas(), p));
				}
			}
		}
	}
	/**
	 * Metodo que rellena la matriz con las frecuencias inversa de las palabras (set de palabras), de cada documento.
	 */
	public void obtenerFrecuenciasInversas(){
		// log(totalDocumentos/cantidadOcurrenciasEnTodosLosDocumentos)
		matrizFrecunciasInversas.clear();
		for(Documento d: documentos){
			System.out.println("Frecuencia inversa documento: "+d.getId());
			if(!d.getPalabrasValidas().isEmpty()){//quitando documentos sin cuerpo
				ArrayList<Double> lista = new ArrayList<>();
				matrizFrecunciasInversas.add(lista);
				lista.add(d.getId()*1.0);  //agregando id documento al inicio de cada lista (como double)
				Integer contador = 0;
				for(String s: palabras){
					contador++;
					if(matrizFrecuncias.get(matrizFrecunciasInversas.size()-1).get(contador) != 0){ //no contador-1 por que tiene el id al inicio
						lista.add(Math.log10(matrizFrecuncias.size()*1.0/ totalDocumentos(s)*1.0));
					}else{
						lista.add(0.0);
					}
				}
			}
		}
	}
	/**
	 * Metodo principal que realiza el calculo de similitud de la consulta entregada por parametro
	 * @param q Consulta a calcular similitud
	 * @return Lista de similitudes de cada documento 
	 */
	public ArrayList<Similitud> calculoSimilitud(Consulta q){
		ArrayList<Double> vectorQ = new ArrayList<>();
		ArrayList<Similitud> vectorSimilitud = new ArrayList<>();
		for(String s: palabras){
			if(q.getPalabrasValidas().contains(s)){
				vectorQ.add(Math.log10(matrizFrecuncias.size()*1.0/ totalDocumentos(s)*1.0));
			}else{
				vectorQ.add(0.0);
			}
			
		}
		
		for(ArrayList<Double> list: matrizFrecunciasInversas){
			Double sumatoria = 0.0;
			Integer contador = 0;
			for(Double d: vectorQ){ //vectorQ es menos largo en una unidad que list actual
				sumatoria += d*list.get(contador+1); // +1 por que matrizFrecunciasInversas tiene el id del documento en primer lugar
				contador++;
			}
			vectorSimilitud.add(new Similitud(sumatoria, list.get(0).intValue()));
		}
		
		
		return vectorSimilitud;
		
		
		
	}
	
	/**
	 * Metodo que obtiene la cantidad de documentos que tiene la palabra entregada por parametro
	 * @param palabra String a buscar en los documentos
	 * @return Retorna la cantidad calculadora
	 */
	
	public Integer totalDocumentos(String palabra){
		Integer posPalabrasEnSet = -1;
		for(String s: palabras){
			posPalabrasEnSet++;
			if(s.equals(palabra)){
				break;
			}
		}
		Integer contador = 0;
		for(ArrayList<Integer> frecuencia: matrizFrecuncias){
			if(frecuencia.get(posPalabrasEnSet+1) > 0){ //+1 por que la primera pos es el id del documento
				contador++;
			}
		}
		return contador;
	}
	
	/**
	 * Metodo para imprimir matriz por pantalla
	 */
	
	public void imprimirMatrizFrecuencias(){
		
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				for(ArrayList<Integer> l: matrizFrecuncias ){
					for(Integer s: l){
						System.out.print(s+"\t");
					}
					System.out.println();
				}
				
			}
		});
		hilo.start();
		
		
	}
	
public void imprimirMatrizFrecuenciasInversas(){
		
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				for(ArrayList<Double> l: matrizFrecunciasInversas ){
					for(Double s: l){
						System.out.print(s+"\t");
					}
					System.out.println();
				}
				
			}
		});
		hilo.start();
		
		
	}
	
}

/*
public void obtenerPrecision(){
	ArrayList<Double> precisiones = new ArrayList<>();
	for(Consulta q: consultas){
		Double precisionAcumulada = 0.0;
		ArrayList<Documento> documentosEnOrdenDeRelevancia = new ArrayList<>();
		//agregando los documentos con relevancia primero
		for(Relevancia r: relevancias){
			if(r.getQueryID().equals(q.getId())){
				//se asume que documentos esta ordenado por id, lo estan. 
				documentosEnOrdenDeRelevancia.add(documentos.get(r.getDocID()-1));
			}	
		}
		//agregando el resto de documentos
		for(Documento d: documentos){
			if(!documentosEnOrdenDeRelevancia.contains(d)){
				documentosEnOrdenDeRelevancia.add(d);
			}
		}
		//Por cada palabra no comun de la consulta, obtener su frecuencia por cada documento.
		ArrayList<Integer> frecuencias = new ArrayList<>();
		for(Documento d: documentosEnOrdenDeRelevancia){
			Integer frecuenciaAcumuladaDocumento = 0; //sumatoria de frecuencias de cada palabra en el documento d
			for(String palabraNoCumunQuery: q.getPalabrasValidas()){
				frecuenciaAcumuladaDocumento += Collections.frequency(d.getPalabrasValidas(), palabraNoCumunQuery);
			}
			frecuencias.add(frecuenciaAcumuladaDocumento);
		}
		
		//por cada frecuencia, si es distinto de 0, dividir frecuenciaAcumulada entre cantidad de documentos revisados, 
		//si es cero, no incrementar frecuencia y dividir
		Integer contadorDocumento = 0;
		Integer frecuenciaAcumulada = 0;
		for(Integer i: frecuencias){
			contadorDocumento++;
			frecuenciaAcumulada+=i;
			//System.out.println(frecuenciaAcumulada+"/"+contadorDocumento);
			precisionAcumulada += (double)frecuenciaAcumulada/(double)contadorDocumento;
		}
		precisiones.add(precisionAcumulada);
	}
	Integer idQuery = 0;
	for(Double d: precisiones){
		System.out.println("QueryID: "+ (++idQuery) +" precisión: "+String.format("%.16f", d));
	}
	
}//*/
