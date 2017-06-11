package documentosConsultas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Relevancia {

	/** Identificador de la consulta */
	private Integer queryID;
	/** Identificador del documento relevante para la consulta */
	private Integer docID;
	
	public Relevancia() {
		this.queryID = 0;
		this.docID = 0;
	}
	
	public Relevancia(Integer queryID, Integer docID){
		this.queryID = queryID;
		this.docID = docID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docID == null) ? 0 : docID.hashCode());
		result = prime * result + ((queryID == null) ? 0 : queryID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relevancia other = (Relevancia) obj;
		if (docID == null) {
			if (other.docID != null)
				return false;
		} else if (!docID.equals(other.docID))
			return false;
		if (queryID == null) {
			if (other.queryID != null)
				return false;
		} else if (!queryID.equals(other.queryID))
			return false;
		return true;
	}

	public Integer getDocID() {
		return docID;
	}
	public Integer getQueryID() {
		return queryID;
	}
	public void setDocID(Integer docID) {
		this.docID = docID;
	}
	public void setQueryID(Integer queryID) {
		this.queryID = queryID;
	}
	
	
	/**
	 * Obtiene desde un archivo la información de relevancia de una consuta y un documento, rellenandolo en una lista con esa información
	 * @param file Archivo con la relevancia en formato: qid did 0 0                                          
	 * @param relevancias Listado de Relevancias a rellenar
	 * @param number Indica la columna a leer el asociado en el fichero en el archivo de relevancia
	 */

	public static void getRelevancia(File file, ArrayList<Relevancia> relevancias, Integer number){

		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] lineSplit = line.replaceFirst("^[ ]+","").split("[\\D\\t]+");
				Relevancia r = new Relevancia(Integer.parseInt(lineSplit[0]),Integer.parseInt(lineSplit[number]));
				relevancias.add(r);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	/**
	 * ONLY LISA DB: Obtiene desde un archivo la información de relevancia de una consuta y un documento, rellenandolo en una lista con esa información
	 * @param file Archivo con la relevancia en formato: qid did 0 0                                          
	 * @param relevancias Listado de Relevancias a rellenar
	 */

	public static void getRelevanciaLisa(File file, ArrayList<Relevancia> relevancias){

		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				
				String line = sc.nextLine(); //id
				String[] lineSplit = line.split("Query ");
				Integer idQuery = Integer.parseInt(lineSplit[1]);
				
				line = sc.nextLine(); //info contador relevantes
				while(!line.equals("") && sc.hasNextLine()){
					line = sc.nextLine();
					for(String s: line.split("[ ]")){
						if(s.equals("-1") || s.equals("") ){
							break;
						}
						Relevancia r = new Relevancia(idQuery,Integer.parseInt(s));
						relevancias.add(r);
					}
					
				}
			
				
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	


}
