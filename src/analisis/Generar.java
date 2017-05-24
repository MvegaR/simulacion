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
		
		String fsp = System.getProperty("file.separator").toString();
		
		//palabras comunes
		
		File palabrasComunesFile = new File("files"+fsp+"cacm"+fsp+"common_words");
		/*
		//Archivos BD CACM
		
		File documentosFileCACM = new File("files"+fsp+"cacm"+fsp+"cacm.all");
		File consultasFileCACM = new File("files"+fsp+"cacm"+fsp+"query.text");
		File relevanciasFileCACM = new File("files"+fsp+"cacm"+fsp+"qrels.text");
		
		ArrayList<Precision> precisionesCACM = getPrecisiones(null,documentosFileCACM, consultasFileCACM, relevanciasFileCACM, palabrasComunesFile, "CACM");
	
		
		//Archivos BD MED
		
		File documentosFileMED = new File("files"+fsp+"med"+fsp+"MED.ALL");
		File consultasFileMED = new File("files"+fsp+"med"+fsp+"MED.QRY");
		File relevanciasFileMED = new File("files"+fsp+"med"+fsp+"MED.REL");
		ArrayList<Precision> precisionesMED = getPrecisiones(null,documentosFileMED, consultasFileMED, relevanciasFileMED, palabrasComunesFile, "MED");
		
		// Archivos BD CRAN
		 
		
		File documentosFileCRAN = new File("files"+fsp+"cran"+fsp+"cran.all.1400");
		File consultasFileCRAN = new File("files"+fsp+"cran"+fsp+"cran.qry");
		File relevanciasFileCRAN = new File("files"+fsp+"cran"+fsp+"cranFix.rel");
		ArrayList<Precision> precisionesCRAN = getPrecisiones(null, documentosFileCRAN, consultasFileCRAN, relevanciasFileCRAN, palabrasComunesFile, "CRAN");
		//*/
		
		//Archivos BD LISA
		ArrayList<File> documentosFilesLisa = new ArrayList<>();
		documentosFilesLisa.add(new File("files"+fsp+"lisa"+fsp+"LISA0.501"));
		documentosFilesLisa.add(new File("files"+fsp+"lisa"+fsp+"LISA1.501"));
		documentosFilesLisa.add(new File("files"+fsp+"lisa"+fsp+"LISA2.501"));
		documentosFilesLisa.add(new File("files"+fsp+"lisa"+fsp+"LISA3.501"));
		documentosFilesLisa.add(new File("files"+fsp+"lisa"+fsp+"LISA4.501"));
		documentosFilesLisa.add(new File("files"+fsp+"lisa"+fsp+"LISA5.501"));
		documentosFilesLisa.add(new File("files"+fsp+"lisa"+fsp+"LISA5.627"));
		documentosFilesLisa.add(new File("files"+fsp+"lisa"+fsp+"LISA5.850"));
		File ConsultasFileLISA = new File("files"+fsp+"lisa"+fsp+"LISA.QUE");
		File RelevanciasFileLisa = new File("files"+fsp+"lisa"+fsp+"LISA.REL");
		ArrayList<Precision> PrecisionesLISA = getPrecisiones(documentosFilesLisa, null, ConsultasFileLISA, RelevanciasFileLisa, palabrasComunesFile, "LISA");
		
		
		

		
			
	}
	
	/**
	 * Obtiene las palabras comunes para omitir en el procesamiento
	 * @param file Archivo con palabras a omitir
	 * @param palabras Lista de palabras a omitir, no nulo, puede tener con anterioridad m�s palabras, para el uso de multiples archivos a trav�s de m�s llamadas.
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
