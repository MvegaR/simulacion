package analisis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import documentosConsultas.Consulta;
import documentosConsultas.Documento;
import documentosConsultas.Relevancia;

public class Generar {
	

	public static void main(String[] args) {
		File documentosFIle = new File("files/cacm.all");
		File consultasFile = new File("files/query.text");
		File palabrasComunesFile1 = new File("files/common_words");
		File palabrasComunesFile2 = new File("files/stopwords.txt");
		File relevanciasFile = new File("files/qrels.text");

		ArrayList<Documento> documentos = new ArrayList<>();
		ArrayList<Consulta> consultas = new ArrayList<>();
		ArrayList<String> palabrasComunes = new ArrayList<>();
		ArrayList<Relevancia> relevancias = new ArrayList<>();
		
		Documento.generarDocumentos(documentosFIle, documentos);
		Consulta.generarConsultas(consultasFile, consultas);
		Relevancia.getRelevancia(relevanciasFile, relevancias);
		
		Generar.getPalabrasComunes(palabrasComunesFile1, palabrasComunes);
		Generar.getPalabrasComunes(palabrasComunesFile2, palabrasComunes);
		
		
		
		for(Documento d: documentos){
			d.generarSetPalabras(palabrasComunes);
		}
		for(Consulta c: consultas){
			c.generarSetPalabras(palabrasComunes);
		}
			
		
		System.out.println("Documentos: "+documentos.size());
		System.out.println("Consultas: "+consultas.size());
		System.out.println("PalabrasComunes: "+palabrasComunes.size());
		SortedSet<String> setDePalabras = new TreeSet<String>();
		for(Documento d: documentos){
			System.out.println("Documento "+ d.getId()+" tiene: "+ d.getPalabrasValidas().size() + " palabras Validas");
			
			for(String s: d.getPalabrasValidas()){
				setDePalabras.add(s);
			}
		}
		
		System.out.println("Palabras Totales sin repetir: " + setDePalabras.size());
		
		MatrizFrecuencia matriz = new MatrizFrecuencia(setDePalabras, documentos, consultas, relevancias);
		
		
		
	
		matriz.obtenerPrecision();
		
		System.out.println("Palabras Inicio \n"+setDePalabras+"\nFin palabras");
		/*
		for(String d: setDePalabras){
			System.out.println(d);
		}//*/
			
	}
	
	/**
	 * Obtiene las palabras comunes para omitir en el procesamiento
	 * @param file Archivo con palabras a omitir
	 * @param palabras Lista de palabras a omitir, no nulo, puede tener con anterioridad más palabras, para el uso de multiples archivos a través de más llamadas.
	 */
	
	public static void getPalabrasComunes(File file, ArrayList<String> palabras){
		
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(!palabras.contains(line)){
					palabras.add(line);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
	
}
