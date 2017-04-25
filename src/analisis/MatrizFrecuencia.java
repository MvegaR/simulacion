package analisis;

import java.util.ArrayList;
import java.util.Collection;
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
	
	/**
	 * Metodo que rellena la matriz con las frecuencias de las palabras (set de palabras), en cada documento.
	 */
	public void obtenerFrecuencias(){
		//quitar documentos sin cuerpo
		matriz.clear();
		for(Documento d: documentos){
			ArrayList<Double> lista = new ArrayList<>();
			matriz.add(lista);
			for(String p: palabras){
				lista.add(Collections.frequency(d.getPalabrasValidas(), p)*1.0);
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
