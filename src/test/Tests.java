package Test;
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import controlador.*;
import modelo.Consulta;
import modelo.Documento;
import modelo.Precision;
import modelo.Relevancia;
import modelo.ResultadoDataSet;
public class Tests {
	private ArrayList<Documento> documentos;
	private ArrayList<Consulta> consultas;
	private ArrayList<String> pComunes;
	private ArrayList<Relevancia> rels;
	private SortedSet<String> setDePalabras;
	private String fsp = System.getProperty("file.separator").toString();
	private ResultadoDataSet resultadosDataset;
	/*
	 * Prueba unitaria de lectura de archivo de documentos, 
	 * se espera lectura de id=1, titulo=”Titulo test” y cuerpo=”Cuerpo Test”
	 */
	@Test
	public void testLecturaDocumento(){
		File docTest = new File("files"+fsp+"test"+fsp+"testDocumento.txt");
		ArrayList<Documento> docs = new ArrayList<>();
		Documento.generarDocumentos(docTest, docs);
		
		
		assertTrue(docs.get(0).getId().equals(1));
		System.out.println("Titulo:" +docs.get(0).getTitulo() );
		assertTrue(docs.get(0).getTitulo().equals("Titulo Test"));
		assertTrue(docs.get(0).getCuerpo().equals("Cuerpo Test"));
	}
	/*
	 * Prueba unitaria de lectura de archivo de documentos vacío, 
	 * se espera que la colección tenga tamaño 0
	 */
	@Test
	public void testLecturaDocumentoVacio(){
		File docTest = new File("files"+fsp+"test"+fsp+"testDocumentoVacio.txt");
		ArrayList<Documento> docs = new ArrayList<>();
		Documento.generarDocumentos(docTest, docs);
		assertTrue(docs.size() == 0);
	}
	/*
	 * Prueba unitaria de lectura de lectura de archivo de consultas, 
	 * se espera de id=1, cuerpo=”Test consulta”
	 */
	@Test
	public void testLecturaConsulta(){
		File conTest = new File("files"+fsp+"test"+fsp+"testConsulta.txt");
		ArrayList<Consulta> cons = new ArrayList<>();
		Consulta.generarConsultas(conTest, cons);
		assertTrue(cons.get(0).getId().equals(1));
		assertTrue(cons.get(0).getQuery().equals("Test consulta"));
	}
	/*
	 * Prueba unitaria de lectura de archivo de consultas vacío, 
	 * se espera que la colección tenga tamaño 0.
	 */
	@Test
	public void testLecturaConsultaVacia(){
		File conTest = new File("files"+fsp+"test"+fsp+"testConsultaVacia.txt");
		ArrayList<Consulta> cons = new ArrayList<>();
		Consulta.generarConsultas(conTest, cons);
		assertTrue(cons.size() == 0);
	}
	/*
	 * Prueba unitaria de lectura de archivo de relevancias, 
	 * se espera un queryId de 3 y un DocID de 30 para el primer elemento 
	 * de la colección.
	 */
	@Test
	public void testLecturaRelevancia(){
		File relTest = new File("files"+fsp+"test"+fsp+"testRelevancia.txt");
		ArrayList<Relevancia> rls = new ArrayList<>();
		Relevancia.getRelevancia(relTest, rls, 1);
		assertTrue(rls.get(0).getQueryID().equals(3) && rls.get(0).getDocID().equals(30));
	}
	/*
	 * Prueba unitaria de lectura de archivo de relevancias vacío, 
	 * se espera la colección tenga tamaño 0
	 */
	@Test
	public void testLecturaRelevanciaVacia(){
		File relTest = new File("files"+fsp+"test"+fsp+"testRelevanciaVacia.txt");
		ArrayList<Relevancia> rls = new ArrayList<>();
		Relevancia.getRelevancia(relTest, rls, 1);
		assertTrue(rls.size() == 0);
	}
	/*
	 * Método para la Inicialización de variables para las pruebas unitarias.
	 */
	@Before
	public void iniVars(){
		//Ejemplo descrito en el proyecto, se elimino tíldes por problemas con el juego de caracteres del proyecto.
		Documento doc1 = new Documento(1, "", 
				"Las piramides de Egipto se hallan en: El Cairo Egipto.", "", null, null, null, "", null);
		Documento doc2 = new Documento(2, "", 
				"La Torre de Tokio se localiza en: Tokio, Japon.", "", null, null, null, "", null);
		Documento doc3 = new Documento(3, "", 
				"Las piramides mayas se hallan en: Latinoamerica.", "", null, null, null, "", null);
		Documento doc4 = new Documento(4, "", 
				"La Torre Eiffel se localiza en: Paris.", "", null, null, null, "", null);
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
		this.resultadosDataset = new ResultadoDataSet("test", 1, 4, 6, 11);
		
	}
	/*
	 * Prueba unitaria de módulo de cálculo procesando 
	 * un ejemplo realizado de forma manual documentado 
	 * como ejemplo en el informe en el capítulo uno, 
	 * se espera el valor 0.52 (ignorar siguientes decimales).
	 */
	@Test
	public void testPrecisionCorrecta() { //bien
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels, resultadosDataset.getResultadosConsultas());
		ArrayList<Precision> precisiones = new ArrayList<>();
		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test funcionalidad básica");
		matriz.obtenerPrecision(consultas.get(0), 4, precisiones, resultadosDataset,true);
		assertTrue(precisiones.get(0).getPrecision()-0.52 <= 0.009); //comparacion de solo dos decimales
		System.out.println("Fin "+"Test funcionalidad básica");
	}
	/*
	 * Prueba de unitaria al módulo de cálculo 
	 * en el caso de que no existan documentos, 
	 * se espera un valor de 0 en la precisión
	 */
	@Test
	public void testSinDocumentos() {
		documentos.clear();
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels, resultadosDataset.getResultadosConsultas());
		ArrayList<Precision> precisiones = new ArrayList<>();
		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test sin documentos");
		matriz.obtenerPrecision(consultas.get(0), 4, precisiones, resultadosDataset, true);
		assertTrue(precisiones.get(0).getPrecision() == 0.0f); 
		System.out.println("Fin "+"Test sin documentos");
	}
	/*
	 * Prueba de unitaria al módulo de cálculo en el caso que no existan palabras, 
	 * se espera un valor de 0 en la precisión
	 */
	@Test
	public void testSinPalabras() {
		setDePalabras.clear();
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels, resultadosDataset.getResultadosConsultas());
		ArrayList<Precision> precisiones = new ArrayList<>();
		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test sin palabras");
		matriz.obtenerPrecision(consultas.get(0), 4, precisiones, resultadosDataset, true );
		System.out.println(precisiones.get(0).getPrecision());
		assertTrue(precisiones.get(0).getPrecision().equals(0.0) );
		System.out.println("Fin "+"Test sin palabras");
	}
	/*
	 * Prueba de unitaria al módulo de cálculo en el caso de que exista un documento 
	 * no inicializado, se espera que continúe normalmente al controlar 
	 * la situación y que entregue el resultado 0.52.
	 */
	@Test
	public void testDocumentoNulo() { //Puntero nulo
		documentos.add(null);
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels, resultadosDataset.getResultadosConsultas());
		ArrayList<Precision> precisiones = new ArrayList<>();
		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test documento nulo");
		matriz.obtenerPrecision(consultas.get(0), 4, precisiones, resultadosDataset, true);
		assertTrue(precisiones.get(0).getPrecision()-0.52 <= 0.009); //comparacion de solo dos decimales
		System.out.println("Fin "+"Test Documento nulo");
	}
	/*
	 * Prueba de unitaria al módulo de cálculo en caso de que exista una consulta no inicializada, 
	 * se espera que continúe normalmente al controlar la situación y que entregue el resultado 0.52.
	 */
	@Test
	public void testConsultaNula() { //Puntero nulo
		Matrices matriz = new Matrices(setDePalabras, documentos, consultas, rels, resultadosDataset.getResultadosConsultas());
		ArrayList<Precision> precisiones = new ArrayList<>();
		//matriz.obtenerPrecision();
		System.out.println("Inicio "+"Test Consulta nula");
		matriz.obtenerPrecision(null, 4, precisiones, resultadosDataset, true);
		assertTrue(precisiones.get(0).getPrecision()-0.52 <= 0.009); //comparacion de solo dos decimales
		System.out.println("Fin "+"Test Consulta nula");
	}
}