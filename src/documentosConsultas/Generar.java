package documentosConsultas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Generar {
	
	public static Integer contador = 0;

	public static void main(String[] args) {
		File file = new File("files/cacm.all");
		ArrayList<Documento> documentos = new ArrayList<>();
		Documento.generarDocumentos(file, documentos);
		System.out.println(documentos.size());
		for(Documento d: documentos){
			System.out.println(d);
		}
		
	}
	
	
	public static HashMap<Consulta, Documento> generarArhivoRelevancia(ArrayList<Documento> documentos, ArrayList<Consulta> consultas, ArrayList<String> palabrasComunes){
		
		return null;
	}
	
	
	
}
