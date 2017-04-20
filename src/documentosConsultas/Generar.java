package documentosConsultas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Generar {
	
	public static Integer contador = 0;

	public static void main(String[] args) {
		File documentosFIle = new File("files/cacm.all");
		File consultasFile = new File("files/query.text");
		File palabrasComunesFile = new File("files/common_words");
		
		ArrayList<Documento> documentos = new ArrayList<>();
		ArrayList<Consulta> consultas = new ArrayList<>();
		ArrayList<String> palabrasComunes = new ArrayList<>();
		
		Documento.generarDocumentos(documentosFIle, documentos);
		Consulta.generarConsultas(consultasFile, consultas);
		
		
		System.out.println("Documentos: "+documentos.size());
		System.out.println("Consultas:"+consultas.size());
		/*//Para Imprimir todos los documentos, agregar un '/' al inicio de esta linea para descomentar codigo.
		for(Documento d: documentos){
			System.out.println(d);
		}
		//*/
		
		/*//Para imprimir todas las consultas, agregar un '/' al inicio de esta linea para descomentar codigo.
		for(Consulta c: consultas){
			System.out.println(c);
		}
		//*/
		
	}
	
	
	public static HashMap<Consulta, Documento> generarArhivoRelevancia(ArrayList<Documento> documentos, ArrayList<Consulta> consultas, ArrayList<String> palabrasComunes){
		
		return null;
	}
	
	
	
	
	
}
