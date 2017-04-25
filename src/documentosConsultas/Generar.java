package documentosConsultas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
//import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Generar {
	
	public static Integer contador = 0;

	public static void main(String[] args) {
		File documentosFIle = new File("files/cacm.all");
		File consultasFile = new File("files/query.text");
		File palabrasComunesFile1 = new File("files/common_words");
		File palabrasComunesFile2 = new File("files/stopwords.txt");

		ArrayList<Documento> documentos = new ArrayList<>();
		ArrayList<Consulta> consultas = new ArrayList<>();
		ArrayList<String> palabrasComunes = new ArrayList<>();
		ArrayList<Relevancia> relevancias = new ArrayList<>();
		
		Documento.generarDocumentos(documentosFIle, documentos);
		Consulta.generarConsultas(consultasFile, consultas);
		
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
		
		System.out.println("PalabrasTotales sin repetir: " + setDePalabras.size() );
		/*
		for(String d: setDePalabras){
			System.out.println(d);
		}//*/
		
		
		
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
	
	/*
	public static HashMap<Consulta, ArrayList<Documento>> detectarRelevancia(ArrayList<Documento> documentos, 
			ArrayList<Consulta> consultas, ArrayList<String> palabrasComunes){
		
		HashMap<Consulta, ArrayList<Documento>> mapRelevancia = new HashMap<Consulta,ArrayList<Documento>>();
		
		//query -> titulo documento, query -> cuerpo documento, && -PalabrasComunes
		
		//autores?, Tipo Referencias?, peso?
		for(Consulta q: consultas){
			String[] querySplit = q.getQuery().split("[ ,.\n]+");	
			ArrayList<Documento> valorList = new ArrayList<Documento>();
			mapRelevancia.put(q, valorList);
			System.out.println("Analizando consulta:" + q.getFullId());
			for(Documento d: documentos){
				String[] tituloSplit = null;
				String[] cuerpoSplit = null;
				if(d.getTitulo() != null){
					tituloSplit = d.getTitulo().split("[ ,.\n]+");
				}
				if(d.getCuerpo() != null){
					cuerpoSplit = d.getCuerpo().split("[ ,.\n]+");
				}

				for(String qs: querySplit){
					if(!palabrasComunes.contains(qs) && qs.length()>3){
						if(tituloSplit != null){
							for(String ts: tituloSplit){
								if(qs.equals(ts) && !mapRelevancia.get(q).contains(d)){
									mapRelevancia.get(q).add(d);
								}
							}
						}
						if(cuerpoSplit != null){
							for(String ts: cuerpoSplit){
								if(qs.equals(ts) && !mapRelevancia.get(q).contains(d)){
									mapRelevancia.get(q).add(d);
								}
							}
						}
					}
					
				}
				
			}
		}
		
		
		return mapRelevancia;
	}
	//*/
	

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
	
	/*for(String s:documentos.get(1061).getCuerpo().split("")){
		System.out.println(s);
	}*/
	
	/*
	HashMap<Consulta, ArrayList<Documento>> mapRevelancia = Generar.detectarRelevancia(documentos, consultas, palabrasComunes);
	for(Consulta c: mapRevelancia.keySet()){
		System.out.print(c.getId()+" -> { ");
		for(Documento d: mapRevelancia.get(c)){
			System.out.print(d.getId()+", ");
		}
		System.out.println("}");
	}
	//*/
	
	
	
	
	
	
	
}
