package analisis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;

import documentosConsultas.Consulta;
import documentosConsultas.Documento;
import documentosConsultas.Relevancia;

/**
 * Matriz de frecuencias, por cada palabra del SortedSet "palabras", se registrará su frecuencia por cada documento, 
 * luego se utiliza para reemplazar las frecuencias por su valor calculado, considerando primero los documentos relevante
 * de la lista "relevancias", obtienendo el log de la divición del total de frecuencia entre el total de documentos chequeados.
 * La sumatoria es la precisión.
 * Los valores se almacenan en una lista  de listas de double.
 *
 */

public class MatrizFrecuencia {

	private SortedSet<String> palabras;
	private ArrayList<ArrayList<Double>> matriz;
	private ArrayList<Documento> documentos;
	private ArrayList<Consulta> consultas;
	private ArrayList<Relevancia> relevancias;
	
	
	public MatrizFrecuencia(SortedSet<String> palabras, ArrayList<Documento> documentos, ArrayList<Consulta> consultas, ArrayList<Relevancia> relevancias) {
		this.palabras = palabras;
		this.matriz = new ArrayList<>();
		this.documentos = documentos;
		this.consultas = consultas;
		this.relevancias = relevancias;
	}
	
	public void euristicaProfesor(){
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
			
			//por cada frecuencia, si es distinto de 0, dividir frecuenciaAcumulada entre cantidad de documentos revisados, si es cero, no incrementar frecuencia y dividir
			Integer contadorDocumento = 0;
			Integer frecuenciaAcumulada = 0;
			for(Integer i: frecuencias){
				contadorDocumento++;
				frecuenciaAcumulada+=i;
				System.out.println(frecuenciaAcumulada+"/"+contadorDocumento);
				precisionAcumulada += ((double)frecuenciaAcumulada/(double)contadorDocumento);
			}
			precisiones.add(precisionAcumulada);
		}
		Integer idQuery = 0;
		for(Double d: precisiones){
			System.out.println("QueryID: "+ (++idQuery) +" precisión: "+String.format("%.32f", d));
		}
		
	}
	
	/**
	 * Metodo que rellena la matriz con las frecuencias de las palabras (set de palabras), en cada documento.
	 */
	public void obtenerFrecuencias(){
		//quitar documentos sin cuerpo
		matriz.clear();
		for(Documento d: documentos){
			if(!d.getPalabrasValidas().isEmpty()){ //quitando documentos sin cuerpo
				ArrayList<Double> lista = new ArrayList<>();
				matriz.add(lista);
				lista.add(d.getId()*1.0); //agregando id docuemnto al inicio de cada lista (como double)
				for(String p: palabras){
					lista.add(Collections.frequency(d.getPalabrasValidas(), p)*1.0);
				}
			}
		}
	}
	
	/**
	 * Metodo para imprimir matriz por pantalla
	 */
	
	public void imprimirMatriz(){
		
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				for(ArrayList<Double> l: matriz ){
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
