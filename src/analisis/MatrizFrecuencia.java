package analisis;

import java.util.ArrayList;
import java.util.SortedSet;

import documentosConsultas.Consulta;
import documentosConsultas.Documento;
import documentosConsultas.Relevancia;

/**
 * Matriz de frecuencias, por cada palabra del SortedSet "palabras", se registrará su frecuencia por cada documento, 
 * luego se utiliza para reemplazar las frecuencias por su valor calculado, considerando primero los documentos relevante
 * de la lista "relevancias", obtienendo el log de la divición del total de frecuencia entre el total de documentos chequeados.
 * La sumatoria es la precisión.
 * @author Marcos
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
	
	
		
	
	
	
	
}
