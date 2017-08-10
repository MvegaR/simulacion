package controlador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import modelo.Consulta;
import modelo.Documento;
import modelo.Precision;
import modelo.Relevancia;
import modelo.ResultadoDataSet;
/**
 * Clase principal con método main, y algoritmo para la ejecución 
 * de cada módulo del proyecto paso a paso.
 * Incluye método para la lectura del fichero de palabras comunes
 * @author Marcos
 */
public class Generar {
	/**
	 * Lista de resultados @see {@link ResultadoDataSet}
	 */
	
	private static ArrayList<ResultadoDataSet> resultadosDataSet = new ArrayList<>();
	
	/**
	 * Método main
	 * @param args Sin uso.
	 */
	
	public static void main(String[] args) {
		String fsp = System.getProperty("file.separator").toString();
		//palabras comunes (se uso en todas ya que mejora la precisión, pero es de cran)
		File palabrasComunesFile = new File("files"+fsp+"cacm"+fsp+"common_words");
		//Archivos BD CACM
		//*/
		File documentosFileCACM = new File("files"+fsp+"cacm"+fsp+"cacm.all");
		File consultasFileCACM = new File("files"+fsp+"cacm"+fsp+"query.text");
		File relevanciasFileCACM = new File("files"+fsp+"cacm"+fsp+"qrels.text");
		
		getPrecisiones(null,documentosFileCACM, 
				consultasFileCACM, relevanciasFileCACM, palabrasComunesFile, "CACM");
		//*/
		//Archivos BD MED
		//*/
		File documentosFileMED = new File("files"+fsp+"med"+fsp+"MED.ALL");
		File consultasFileMED = new File("files"+fsp+"med"+fsp+"MED.QRY");
		File relevanciasFileMED = new File("files"+fsp+"med"+fsp+"MED.REL");

		getPrecisiones(null,documentosFileMED, 
				consultasFileMED, relevanciasFileMED, palabrasComunesFile, "MED");
		//*/
		// Archivos BD CRAN
		//*/
		File documentosFileCRAN = new File("files"+fsp+"cran"+fsp+"cran.all.1400");
		File consultasFileCRAN = new File("files"+fsp+"cran"+fsp+"cran.qry");
		File relevanciasFileCRAN = new File("files"+fsp+"cran"+fsp+"cranFix.rel");
		getPrecisiones(null, documentosFileCRAN, 
				consultasFileCRAN, relevanciasFileCRAN, palabrasComunesFile, "CRAN");
		//*/
		// Archivos DB CISI
		//*/
		File documentosFileCISI = new File("files"+fsp+"cisi"+fsp+"CISI.all");
		File consultasFileCISI = new File("files"+fsp+"cisi"+fsp+"CISI.qry");
		File relevanciasFileCISI = new File("files"+fsp+"cisi"+fsp+"CISI.rel");
		getPrecisiones(null, documentosFileCISI, 
				consultasFileCISI, relevanciasFileCISI, palabrasComunesFile, "CISI");
		//*/
		//Archivos BD LISA
		//*/
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
		getPrecisiones(documentosFilesLisa, null, 
				ConsultasFileLISA, RelevanciasFileLisa, palabrasComunesFile, "LISA");
		//*/
		//Encontradas en internet
		//Archivos BD ADI
		//*/
		File documentosFileADI = new File("files"+fsp+"adi"+fsp+"ADI.ALL");
		File consultasFileADI = new File("files"+fsp+"adi"+fsp+"ADI.QRY");
		File relevanciasFileADI = new File("files"+fsp+"adi"+fsp+"ADI.REL");

		getPrecisiones(null,documentosFileADI, 
				consultasFileADI, relevanciasFileADI, palabrasComunesFile, "ADI");
		//*/
		
		//Archivos BD NPL (No usar, muy grande)
		/*/
		File documentosFileNPL = new File("files"+fsp+"npl"+fsp+"doc-text");
		File consultasFileNPL= new File("files"+fsp+"npl"+fsp+"query-text");
		File relevanciasFileNPL = new File("files"+fsp+"npl"+fsp+"rlv-ass");

		getPrecisiones(null,documentosFileNPL, 
				consultasFileNPL, relevanciasFileNPL, palabrasComunesFile, "NPL");
		//*/
		
		
		//Archivos BD TIME
		//*/
		File documentosFileTIME = new File("files"+fsp+"time"+fsp+"TIME.ALL");
		File consultasFileTIME= new File("files"+fsp+"time"+fsp+"TIME.QUE");
		File relevanciasFileTIME = new File("files"+fsp+"time"+fsp+"TIME.REL");
		//File palabrasComunesFileTIME = new File("files"+fsp+"time"+fsp+"TIME.STP");//No es buena
	
		getPrecisiones(null,documentosFileTIME, 
				consultasFileTIME, relevanciasFileTIME, palabrasComunesFile, "TIME");
		//*/
		
		//Archivos BD ISWC2015
		//*/
		File documentosFileISWC2015 = new File("files"+fsp+"iswc2015"+fsp+"docs.txt");
		File consultasFileISWC2015= new File("files"+fsp+"iswc2015"+fsp+"qrys.txt");
		File relevanciasFileISWC2015 = new File("files"+fsp+"iswc2015"+fsp+"rel.txt");
	
		getPrecisiones(null,documentosFileISWC2015, 
				consultasFileISWC2015, relevanciasFileISWC2015, palabrasComunesFile, "ISWC2015");
		//*/
		
		
	}
	/**
	 * Método que realiza la ejecución del algoritmo para obtener matriz frecuencia, 
	 * matriz frecuencia inversa, similitud y precisión.
	 * @param documentosFiles Lista de Archivo con los documentos, si es null,
	 *  el siguiente parámetro no puede serlo.
	 * @param documentosFile Archivo con los documentos
	 * @param consultasFile Archivo con las consultas
	 * @param relevanciasFile Archivo con la relevancia de los documentos por consulta
	 * @param palabrasComunesFile Archivo con las palabras comunes
	 * @param nombreDB Cadena de texto con el nombre de la base de datos.
	 * @return Lista de precisiones para ser utilizada para posibles procesos siguientes
	 */
	public static ArrayList<Precision> getPrecisiones(ArrayList<File> documentosFiles,
			File documentosFile, File consultasFile, File relevanciasFile, 
			File palabrasComunesFile, String nombreDB){
		ArrayList<Documento> documentos = new ArrayList<>();
		ArrayList<Consulta> consultas = new ArrayList<>();
		ArrayList<String> palabrasComunes = new ArrayList<>();
		ArrayList<Relevancia> relevancias = new ArrayList<>();
		if(nombreDB.equals("LISA")){
			Documento.generarDocumentosLisa(documentosFiles, documentos);
			Consulta.generarConsultasLisa(consultasFile, consultas);
		}else if(nombreDB.equals("TIME")){
			Documento.generarDocumentosTime(documentosFile, documentos);
			Consulta.generarConsultasTime(consultasFile, consultas);
		}else{
			Documento.generarDocumentos(documentosFile, documentos);
			Consulta.generarConsultas(consultasFile, consultas);
		}
		if(nombreDB.equals("MED")){
			Relevancia.getRelevancia(relevanciasFile, relevancias, 2);
		}else if(nombreDB.equals("LISA")){
			Relevancia.getRelevanciaLisa(relevanciasFile, relevancias);
		}else if(nombreDB.equals("NPL")){
			Relevancia.getRelevanciaNPL(relevanciasFile, relevancias);
		}else if(nombreDB.equals("TIME")  || nombreDB.equals("ISWC2015")){
			Relevancia.getRelevanciaTIMEyISWC2015(relevanciasFile, relevancias);
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
		/*
		ArrayList<Documento> documentosFil = new ArrayList<>();
		for(Documento d: documentos){
			if(d.getPalabrasValidas().size() > 20){
				documentosFil.add(d);
			}
		}
		documentos = documentosFil;
		//*/
		System.out.println(nombreDB);
		System.out.println("Documentos:\t"+documentos.size());
		System.out.println("Consultas:\t"+consultas.size());
		System.out.println("Palabras Comunes:\t"+palabrasComunes.size());
		SortedSet<String> setDePalabras = new TreeSet<String>();
		for(Documento d: documentos){
			//System.out.println("Documento "+ d.getId()+" tiene: "+ d.getPalabrasValidas().size() + " palabras Validas");
			for(String s: d.getPalabrasValidas()){
				setDePalabras.add(s);
			}
		}
		/*for(Consulta c: consultas){
			for(String s: c.getPalabrasValidas()){
				setDePalabras.add(s);
			}
		}*/
		System.out.println("Palabras Totales sin repetir no comunes:\t" + setDePalabras.size());
		ResultadoDataSet dataSet = new ResultadoDataSet(nombreDB, consultas.size(), documentos.size(), 
				palabrasComunes.size(),  setDePalabras.size());

		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, relevancias, dataSet.getResultadosConsultas());
		/*FIX REL FILE CRAN
		if(nombreDB.equals("CRAN")){
			matriz.fixFileRel();
			System.exit(0);
		}
		//*/
		ArrayList<Precision> precisiones = new ArrayList<>();
		//matriz.obtenerPrecision();
		for(Consulta q: consultas){
			matriz.obtenerPrecision(q, 30, precisiones);
		}
		resultadosDataSet.add(dataSet); //guardando en la lista
		System.out.println("Fin "+nombreDB);
		
		
/*
		System.out.println(consultas.get(0).getId());
		System.out.println(consultas.get(0).getQuery());
		System.out.println(consultas.get(0).getPalabrasValidas());
		System.out.println(Matrices.getDocumento(334, documentos).getId());
		System.out.println(Matrices.getDocumento(334, documentos).getCuerpo());
		System.out.println(Matrices.getDocumento(334, documentos).getPalabrasValidas());
		*/
		return precisiones;
	}
	/**
	 * Método que obtiene la lista de palabras comunes, como pronombres personales, conectores etc
	 * @param file Archivo con las palabras
	 * @param palabras lista de palabras a rellenar con las palabras del archivo
	 */
	public static void getPalabrasComunes(File file, ArrayList<String> palabras){
		if(file != null){
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
}