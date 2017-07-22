package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;

import documentosConsultas.Consulta;
import documentosConsultas.Documento;
import documentosConsultas.Relevancia;
import junit.framework.JUnit4TestAdapter;
import analisis.*;

public class Tests {

	private ArrayList<Documento> documentos;
	private ArrayList<Consulta> consultas;
	private ArrayList<String> pComunes;
	private ArrayList<Relevancia> rels;
	private SortedSet<String> setDePalabras;
	@Before
	public void iniVars(){
		//Ejemplo descrito en el proyecto, se elimino tíldes por problemas con el juego de caracteres del proyecto.
		Documento doc1 = new Documento(1, "", "Las piramides de Egipto se hallan en: El Cairo Egipto.", "", null, null, null, "", null);
		Documento doc2 = new Documento(2, "", "La Torre de Tokio se localiza en: Tokio, Japon.", "", null, null, null, "", null);
		Documento doc3 = new Documento(3, "", "Las piramides mayas se hallan en: Latinoamerica.", "", null, null, null, "", null);
		Documento doc4 = new Documento(4, "", "La Torre Eiffel se localiza en: Paris.", "", null, null, null, "", null);
		
		Consulta con1 = new Consulta(1, "Donde se localiza la Torre Eiffel?", null, "");
			
		rels = new ArrayList<>();
		
		rels.add(new Relevancia(1, 4));
		
		pComunes = new ArrayList<>();
		pComunes.add("las");
		pComunes.add("de");
		pComunes.add("se");
		pComunes.add("en");
		pComunes.add("el");
		pComunes.add("la");
		doc1.generarSetPalabras(pComunes);
		doc2.generarSetPalabras(pComunes);
		doc3.generarSetPalabras(pComunes);
		doc4.generarSetPalabras(pComunes);
		con1.generarSetPalabras(pComunes);
		documentos = new ArrayList<>();
		documentos.add(doc1);
		documentos.add(doc2);
		documentos.add(doc3);
		documentos.add(doc4);
		consultas = new ArrayList<>();
		consultas.add(con1);
		
		System.out.println(con1.getPalabrasValidas());
		
		setDePalabras = new TreeSet<String>();
		for(Documento d: documentos){
			//System.out.println("Documento "+ d.getId()+" tiene: "+ d.getPalabrasValidas().size() + " palabras Validas");
			for(String s: d.getPalabrasValidas()){
				setDePalabras.add(s);
			}
		}
				
	
	}
	
	
	@Test
	public void testFuncionalidadBasica() { //bien
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels);
		ArrayList<Precision> precisiones = new ArrayList<>();

		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test funcionalidad básica");
		matriz.obtenerPrecision(consultas.get(0), 4, precisiones);
		assertTrue(precisiones.get(0).getPrecision()-0.52 <= 0.009); //comparacion de solo dos decimales
		
		System.out.println("Fin "+"Test funcionalidad básica");
	}
	
	@Test
	public void testSinDocumentos() { //error
		documentos.clear();
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels);
		ArrayList<Precision> precisiones = new ArrayList<>();

		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test sin documentos");
		matriz.obtenerPrecision(consultas.get(0), 4, precisiones);
		assertTrue(precisiones.get(0).getPrecision() == 0.0f); 
		
		System.out.println("Fin "+"Test sin documentos");
	}
	
	@Test
	public void testSinPalabras() { //Nan en similitud
		setDePalabras.clear();
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels);
		ArrayList<Precision> precisiones = new ArrayList<>();

		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test sin palabras");
		matriz.obtenerPrecision(consultas.get(0), 4, precisiones);
		System.out.println(precisiones.get(0).getPrecision());
		assertTrue(precisiones.get(0).getPrecision().equals(0.0) );
		
		System.out.println("Fin "+"Test sin palabras");
	}

	@Test
	public void testDocumentoNulo() { //Puntero nulo
		documentos.add(null);
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels);
		ArrayList<Precision> precisiones = new ArrayList<>();

		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test documento nulo");
		matriz.obtenerPrecision(consultas.get(0), 4, precisiones);
		
		assertTrue(precisiones.get(0).getPrecision()-0.52 <= 0.009); //comparacion de solo dos decimales
		
		
		System.out.println("Fin "+"Test Documento nulo");
	}
	
	@Test
	public void testConsultaNula() { //Puntero nulo
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels);
		ArrayList<Precision> precisiones = new ArrayList<>();

		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test Consulta nula");
		matriz.obtenerPrecision(null, 4, precisiones);
		assertTrue(precisiones.get(0).getPrecision()-0.52 <= 0.009); //comparacion de solo dos decimales
		
		System.out.println("Fin "+"Test Consulta nula");
	}
	//probar lectura de archivos !!!!!
	
	@Test
	public void testLecturaDocumento(){
		
	}
	
	
	
}
