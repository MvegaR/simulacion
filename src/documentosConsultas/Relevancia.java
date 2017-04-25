package documentosConsultas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Relevancia {

	
	private Integer queryID;
	private Integer docID;
	
	public Relevancia() {
		this.queryID = 0;
		this.docID = 0;
	}
	
	public Relevancia(Integer queryID, Integer docID){
		this.queryID = queryID;
		this.docID = docID;
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
	 */

	public static void getRelevancia(File file, ArrayList<Relevancia> relevancias){

		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] lineSplit = line.split("[ ]+");
				Relevancia r = new Relevancia(Integer.parseInt(lineSplit[0]),Integer.parseInt(lineSplit[1]));
				relevancias.add(r);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}
