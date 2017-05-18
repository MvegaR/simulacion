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
	
	private ArrayList<Precision> relevante(File documentosFile, File consultasFile, File palabrasComunesFile, File relevanciasFile, String nombreDB){
		ArrayList<Documento> documentos = new ArrayList<>();
		ArrayList<Consulta> consultas = new ArrayList<>();
		ArrayList<String> palabrasComunes = new ArrayList<>();
		ArrayList<Relevancia> relevancias = new ArrayList<>();
		
		Documento.generarDocumentos(documentosFile, documentos);
		Consulta.generarConsultas(consultasFile, consultas);
		Relevancia.getRelevancia(relevanciasFile, relevancias);
		Generar.getPalabrasComunes(palabrasComunesFile, palabrasComunes);

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
			//System.out.println("Documento "+ d.getId()+" tiene: "+ d.getPalabrasValidas().size() + " palabras Validas");
			for(String s: d.getPalabrasValidas()){
				setDePalabras.add(s);
			}
		}
		System.out.println("Palabras Totales sin repetir: " + setDePalabras.size());

		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, relevancias);
		ArrayList<Precision> precisiones = new ArrayList<>();

		//matriz.obtenerPrecision();
		System.out.println("Inicio"+nombreDB);
		for(Consulta q: consultas){
			matriz.obtenerPrecision(q, 20, precisiones);
		}
		System.out.println("Fin "+nombreDB);
		
		
		return precisiones;
		
	}
	

	public static void main(String[] args) {
		
		/**
		 * Ficheros BD CACM
		 */
		File documentosFileCACM = new File("files/cacm/cacm.all");
		File consultasFileCACM = new File("files/cacm/query.text");
		File palabrasComunesFileCACM = new File("files/cacm/common_words");
		File relevanciasFileCACM = new File("files/cacm/qrels.text");
		

		
			
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
