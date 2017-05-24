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
		/**
		 * Archivos palabras comunes
		 */
		File palabrasComunesFile = new File("files/cacm/common_words");
		
		//Archivos BD CACM
		
		File documentosFileCACM = new File("files/cacm/cacm.all");
		File consultasFileCACM = new File("files/cacm/query.text");
		File relevanciasFileCACM = new File("files/cacm/qrels.text");
		
		ArrayList<Precision> precisionCACM = getPrecisiones(null,documentosFileCACM, consultasFileCACM, relevanciasFileCACM, palabrasComunesFile, "CACM");
	
		
		//Archivos BD MED
		
		File documentosFileMED = new File("files/med/MED.ALL");
		File consultasFileMED = new File("files/med/MED.QRY");
		File relevanciasFileMED = new File("files/med/MED.REL");
		ArrayList<Precision> precisionMED = getPrecisiones(null,documentosFileMED, consultasFileMED, relevanciasFileMED, palabrasComunesFile, "MED");
		
		// Archivos BD CRAN
		 
		
		File documentosFileCRAN = new File("files/cran/cran.all.1400");
		File consultasFileCRAN = new File("files/cran/cran.qry");
		File relevanciasFileCRAN = new File("files/cran/cranFix.rel");
		ArrayList<Precision> precisionCRAN = getPrecisiones(null, documentosFileCRAN, consultasFileCRAN, relevanciasFileCRAN, palabrasComunesFile, "CRAN");
		

		
			
	}
	
	/**
	 * Obtiene las palabras comunes para omitir en el procesamiento
	 * @param file Archivo con palabras a omitir
	 * @param palabras Lista de palabras a omitir, no nulo, puede tener con anterioridad más palabras, para el uso de multiples archivos a través de más llamadas.
	 */
	
	public static ArrayList<Precision> getPrecisiones(ArrayList<File> documentosFiles, File documentosFile, File consultasFile, File relevanciasFile, File palabrasComunesFile, String nombreDB){
		ArrayList<Documento> documentos = new ArrayList<>();
		ArrayList<Consulta> consultas = new ArrayList<>();
		ArrayList<String> palabrasComunes = new ArrayList<>();
		ArrayList<Relevancia> relevancias = new ArrayList<>();
		
		
		if(nombreDB.equals("LISA")){
			Documento.generarDocumentosLisa(documentosFiles, documentos);
			Consulta.generarConsultasLisa(consultasFile, consultas);
		}else{
			Documento.generarDocumentos(documentosFile, documentos);
			Consulta.generarConsultas(consultasFile, consultas);
		}
		
		
		if(nombreDB.equals("MED")){
			Relevancia.getRelevancia(relevanciasFile, relevancias, 2);
		}else if(nombreDB.equals("LISA")){
			Relevancia.getRelevanciaLisa(relevanciasFile, relevancias);
		}else{
			Relevancia.getRelevancia(relevanciasFile, relevancias, 1);
		}
		
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
		/*FIX REL FILE CRAN

		if(nombreDB.equals("CRAN")){
			matriz.fixFileRel();
			System.exit(0);
		}
		//*/
		ArrayList<Precision> precisiones = new ArrayList<>();

		//matriz.obtenerPrecision();
		System.out.println("Inicio"+nombreDB);
		for(Consulta q: consultas){
			matriz.obtenerPrecision(q, 20, precisiones);
		}
		System.out.println("Fin "+nombreDB);
		
		
		return precisiones;
		
	}
	
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
