package analisis;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.SortedSet;
import documentosConsultas.Consulta;
import documentosConsultas.Documento;
import documentosConsultas.Relevancia;
/**
 * Incluye los métodos para calcular las matrices de frecuencia y frecuencia inversa
 *  @author Marcos
 */
public class Matrices {
	/**
	 * Clase local que relaciona un documento con un determinado 
	 * valor de similitud calculado, para poder ordenar de mayor 
	 * a menor por similitud sin perder la referencia del documento
	 */
	class Similitud{
		/** valor de similitud */
		private Double valor;
		/** Identificador del documento de la similitud */
		private Integer idDocumento;
		public Similitud(Double valor, Integer idDocumento) {
			if(valor.isNaN()){
				valor = 0.0;
			}
			this.valor = valor;
			this.idDocumento = idDocumento;
		}
		/**
		 * Obtiene el valor de similitud
		 * @return el valor
		 */
		public Double getValor() {
			return valor;
		}
		/**
		 * Obtiene el identificador del documento correspondiente a tal valor
		 * @return ID documento
		 */
		public Integer getIdDocumento() {
			return idDocumento;
		}
	}//Fin clase Similitud.
	//inicio atributos de this
	/** Conjunto de palabras sin repetir, minúsculas, ordenadas por alfabeto */
	private SortedSet<String> palabras; //
	/** Lista de lista de Integer, matriz de frecuencias */
	private ArrayList<ArrayList<Integer>> matrizFrecuncias;
	/** Lista de lista de Double, matriz de frecuencia inversa */
	private ArrayList<ArrayList<Double>> matrizFrecunciasInversas;
	/** Lista de documentos */
	private ArrayList<Documento> documentos;
	/** Lista de consultas */
	private ArrayList<Consulta> consultas;
	/** Lista de relevancias */
	private ArrayList<Relevancia> relevancias;
	//fin atributos
	/**
	 * Constructor, inicializador de variables
	 * @param palabras Conjunto (SortedSet) de palabras, por definición sin 
	 * repetición (por hashcode() de String no equals()) y orden natural (alfabetico)
	 * @param documentos Colección de documentos 
	 * (ArrayList, arreglo tamaño adaptativo, no lista enlazada)
	 * @param consultas Colección de consultas 
	 * (ArrayList, arreglo tamaño adaptativo, no lista enlazada)
	 * @param relevancias Colección de relevancia 
	 * (ArrayList, arreglo tamaño adaptativo, no lista enlazada)
	 * 
	 */
	public Matrices(SortedSet<String> palabras, ArrayList<Documento> documentos, 
			ArrayList<Consulta> consultas, ArrayList<Relevancia> relevancias) {
		this.palabras = palabras;
		this.matrizFrecuncias = new ArrayList<>();
		this.matrizFrecunciasInversas = new ArrayList<>();
		this.documentos = documentos;
		this.consultas = consultas;
		this.relevancias = relevancias;
	}
	/**
	 * Obtiene la precisión de cada documento para la consulta q con un p@ entregado por parámetro
	 * @param q Consulta a calcular precisión
	 * @param p Valor de p@
	 * @param precisiones Lista para almazenar las precisiones y utilizarlas con su respectivos idDocumentos y p@
	 * @exception NullPointerException - ArrayList precisiones no puede ser nulo.
	 */
	public void obtenerPrecision(Consulta q, int p, ArrayList<Precision> precisiones){
		if(precisiones == null){
			throw new NullPointerException("ArrayList Precisiones no puede se nulo");
		}
		if(q == null){
			q = new Consulta();
		}
		//ArrayList<Double> precisiones = new ArrayList<>();
		if(matrizFrecuncias.size() == 0){
			this.obtenerFrecuencias(); //se guardan en matrizFrecuencias
			System.out.println("Matriz de frecuencias calculada");
		}
		if(matrizFrecunciasInversas.size() == 0){
			this.obtenerFrecuenciasInversas(); // se guardan en matrizFrecuencaisInversa
			System.out.println("Matriz de frecuencias inversas calculadas");
		}
		//System.out.println("Consulta: q"+q.getId());
		ArrayList<Similitud> similitudes = calculoSimilitud(q); //uso de la clase local Similitud
		similitudes.sort(new Comparator<Similitud>() { //Ordenando por *valor* mayor primero
			@Override
			public int compare(Similitud o1, Similitud o2) { 
				return o2.getValor().compareTo(o1.getValor()); 
				//orden natural inverso de Double (mayor primero y no menor)
			}
		});
		Double precision = 0.0;
		Double recall = 0.0;
		Integer contadorDocumentosRelevantes = 0;
		Integer contadorTotalDocumentos = 0;
		for(int i = 0; i < p && i < similitudes.size(); i++){
			contadorTotalDocumentos++;
			if(isRelevante(q.getId(), similitudes.get(i).getIdDocumento())){
				contadorDocumentosRelevantes++;
				precision += (contadorDocumentosRelevantes * 1.0) / (contadorTotalDocumentos*1.0);
				recall += (contadorDocumentosRelevantes * 1.0) / totalRelevantes(q);
			}else{
				precision += (contadorDocumentosRelevantes *1.0) / (contadorTotalDocumentos*1.0);
				recall += (contadorDocumentosRelevantes * 1.0) / totalRelevantes(q);
			}
			/*
			System.out.println("\tQ"+q.getId()+" Documento: " + 
					String.format("%4d", similitudes.get(i).getIdDocumento()) 
			+ " Similitud: "+ String.format("%19.16f",   similitudes.get(i).getValor()) 
			+ " Relevante: " +isRelevante(q.getId(), similitudes.get(i).getIdDocumento()));
			//*/
			//* 
			//codigo para imprimir con formato para copiar a archivo excel.
			int r = 0;
			if(isRelevante(q.getId(), similitudes.get(i).getIdDocumento())){
				r = 1;
			}
			Double relevanciaAcumulada = (double)contadorDocumentosRelevantes/(double)contadorTotalDocumentos;
			Double recallAcumulada = (double)contadorDocumentosRelevantes/totalRelevantes(q);
			if(recallAcumulada.isNaN()){
				recallAcumulada = 0.0;
			}
			System.out.print(q.getId()+"\t" + similitudes.get(i).getIdDocumento() + "\t" +  
			similitudes.get(i).getValor() + "\t" + r +"\t"+ relevanciaAcumulada+"\t"+ recallAcumulada);
			HashSet<String> palabrasRelevantesSinRepetir = new HashSet<>();
			HashSet<String> palabrasTotalSinRepetir = new HashSet<>();
			for(String palabra: getDocumento(similitudes.get(i).getIdDocumento(), documentos).getPalabrasValidas()){
				palabrasTotalSinRepetir.add(palabra);
			}
			System.out.print("\t"+ palabrasTotalSinRepetir.size());
			if(true){ //imprimir palabras relevantes presentes en el documento y consulta
				for(String palabra: q.getPalabrasValidas()){
					if(getDocumento(similitudes.get(i).getIdDocumento(), 
					documentos).getPalabrasValidas().contains(palabra)){
						palabrasRelevantesSinRepetir.add(palabra);
					}
				}
				for(String palabra: palabrasRelevantesSinRepetir){
					if(getDocumento(similitudes.get(i).getIdDocumento(), 
					documentos).getPalabrasValidas().contains(palabra)){
						System.out.print("\t"+palabra); //!!!!!!!!!!!!!!!!
						//System.out.print("\t"+palabra+"\t" + count(getDocumento(similitudes.get(i).getIdDocumento(), documentos).getPalabrasValidas(), palabra) ); //!!!!!!!!!!!!!!!!

					}
				}
			}
			System.out.println();
			//System.out.println(q.getPalabrasValidas().size() +" - "+ getDocumento(similitudes.get(i).getIdDocumento(), documentos).getPalabrasValidas().size());
			 
			//*/
		}
		if(contadorTotalDocumentos != 0){
			precision /= contadorTotalDocumentos;
			recall /= contadorTotalDocumentos;
			
		}
		if(precision.isNaN() || palabras.isEmpty() || recall.isNaN()){
			precision = 0.0;
			recall = 0.0;
		}
		precisiones.add(new Precision(q.getId(), precision, contadorDocumentosRelevantes, p));
		/*
		System.out.println("\t\tPrecisión consulta Q"+q.getId()+"p@"+p+" es: "
		+ String.format("%.10f", precision)+" Docs Relevantes: "+contadorDocumentosRelevantes+"\n");
		//*/
		System.out.println("Precisión promedio"+"\t\t\t\t"+precision);
		System.out.println("Recall promedio"+"\t\t\t\t\t"+recall);
		System.out.println();
	}
	
	
	private Double totalRelevantes(Consulta q){
		Integer count = 0;
		for(Relevancia r: relevancias){
			if(r.getQueryID().equals(q.getId())){
				count++;
			}
		}
		return count.doubleValue();
	}
	
	private int count(LinkedList<String> lista, String elemento){
		int c = 0;
		for(String eli: lista){
			if(eli.equals(elemento)){
				c++;
			}
		}
		return c;
	}
	
	/**
	 * Obtiene un documento dado su id
	 * @param idDocumento id del documento a buscar
	 * @param listaDocumentos lista con todos los documentos
	 * @return Documento buscado
	 */

	public static Documento getDocumento(Integer idDocumento, ArrayList<Documento> listaDocumentos){
		for(Documento d: listaDocumentos){
			if(d.getId().equals(idDocumento)){
				return d;
			}
		}
		return null;
	}
	
	
	/**
	 * Determina si existe relevancia de entre consulta y documento, 
	 * segun la lista de relevancias creada con el archivo de relevancias
	 * @param idConsulta Identificador de la consulta
	 * @param idDocumento Identificador del documento
	 * @return <b>true</b> or <b>false</b>
	 */
	private Boolean isRelevante(Integer idConsulta, Integer idDocumento){
		for(Relevancia r: relevancias){
			if(r.getQueryID().equals(idConsulta) && r.getDocID().equals(idDocumento)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Método que rellena la matriz con las frecuencias de las 
	 * palabras (set de palabras), de cada documento.
	 * Por cada documento <b>d</b> de la lista de documentos 
	 * (excluyendo documentos sin cuerpo o sin palabras), 
	 * se crea una <b>lista</b> y se agrega a la lista de listas 
	 * <b>matrizFrecuencias</b> (atributo de <b>this</b>)
	 * por cada palabra se agrega a <b>lista</b> la frecuencia de 
	 * la palabra <b>p</b> en el documento <b>d</b>
	 * 
	 */
	public void obtenerFrecuencias(){
		//quitar documentos sin cuerpo
		matrizFrecuncias.clear();
		for(Documento d: documentos){
			if(d != null && !d.getPalabrasValidas().isEmpty()){ //quitando documentos no validos
				ArrayList<Integer> lista = new ArrayList<>();
				matrizFrecuncias.add(lista);
				lista.add(d.getId()); //agregando id documento al inicio de cada lista
				for(String p: palabras){
					lista.add(Collections.frequency(d.getPalabrasValidas(), p));
				}
			}
		}
	}
	/**
	 * Método que rellena la matriz con las frecuencias inversa de las palabras 
	 * (set de palabras), de cada documento. 
	 * Por cada documento, con palabras válidas,
	 * se crea una <b>lista</b> de double que se almacena en una lista de listas 
	 * (arraylist, es un arreglo, no lista enlazada),
	 * la <b>lista</b> se rellena inicialmente con el id del documento, a continuación con 
	 * los valores del cálculo de frecuencia inversa
	 * por cada palabra <b>s</b> se calcula:
	 * log10(totalDocumentos/totalDocumentosQueTieneLaPalabra<b>S</b>
	 * si en la matriz de frecuencias tiene el valor de cero no se realiza 
	 * cálculo se se agrega un 0 a la lista.
	 */
	public void obtenerFrecuenciasInversas(){
		// log(totalDocumeºntos/cantidadOcurrenciasEnTodosLosDocumentos)
		matrizFrecunciasInversas.clear();
		for(Documento d: documentos){
			if(d != null && !d.getPalabrasValidas().isEmpty()){//quitando documentos sin cuerpo ni título
				System.out.println("Frecuencia inversa documento: "+d.getId());
				ArrayList<Double> lista = new ArrayList<>();
				matrizFrecunciasInversas.add(lista);
				lista.add(d.getId()*1.0);  //agregando id documento al inicio de cada lista (como double)
				Integer contador = 0;
				for(String s: palabras){
					contador++;
					//   frecuencias   .         documento                    . palabra  
					if(matrizFrecuncias.get(matrizFrecunciasInversas.size()-1).get(contador) != 0){ 
						//no contador-1 porque tiene el id al inicio
						Integer totaldoc = totalDocumentos(s);
						if(totaldoc != 0)
							lista.add(Math.log10( (matrizFrecuncias.size()*1.0)/( totaldoc*1.0)));
						else
							lista.add(0.0);
					}else{
						lista.add(0.0);
					}
				}
			}
		}
	}
	/**
	 * Método que realiza el cálculo de similitud de la consulta entregada por parámetro
	 * Se crea el <b>vectorQ</b> de la consulta <b>q</b> y una 
	 * lista <b>vectorSimilitud</b> para retornar (de la clase local similitud)
	 * por cada palabra <b>s</b> si la consulta <b>q</b> contiene la palabra <b>s</b> se calcula 
	 * log10(cantidadDocumentos/totalDocumentosQuetenga<b>S</b>)
	 * si la consulta no tiene la palabra se agrega 0 a la lista.
	 * @param q Consulta a calcular similitud
	 * @return Lista de similitudes de cada documento 
	 */
	public ArrayList<Similitud> calculoSimilitud(Consulta q){
		ArrayList<Double> vectorQ = new ArrayList<>();
		ArrayList<Similitud> vectorSimilitud = new ArrayList<>();
		for(String s: palabras){
			if(q.getPalabrasValidas().contains(s)){
				try {
					vectorQ.add(Math.log10((matrizFrecuncias.size() * 1.0)/(totalDocumentos(s)*1.0)));
				} catch (Exception e) {
					vectorQ.add(0.0);
				}
				//System.out.println(matrizFrecuncias.size() + " ; " + totalDocumentos(s));
			}else{
				vectorQ.add(0.0);
			}
		}
		//System.out.println("Vector q" +q.getId()+": "+ vectorQ);
		//distancia coseno
		for(ArrayList<Double> list: matrizFrecunciasInversas){
			Double sumatoria = 0.0;
			Double sumatoria2 = 0.0;
			Double sumatoria3 = 0.0;
			Integer contador = 0;
			for(Double d: vectorQ){ //vectorQ es menos largo en una unidad que list actual

				sumatoria += d*list.get(contador+1); 
				// +1 porque matrizFrecunciasInversas tiene el id del documento en primer lugar
				sumatoria2 += d*d;
				sumatoria3 += list.get(contador+1) *list.get(contador+1);

				contador++;
			}
	
			vectorSimilitud.add(new Similitud( sumatoria / (Math.sqrt(sumatoria2 * sumatoria3)) , list.get(0).intValue())); 
			//vectorSimilitud.add(new Similitud( sumatoria /(sumatoria2 + sumatoria3 - sumatoria) , list.get(0).intValue())); 
			//list.get(0) tiene el id del documento
		}
		return vectorSimilitud;
	}
	/**
	 * Método que entrega la cantidad de documentos que tiene la palabra entregada por parámetro
	 * @param palabra String a buscar en los documentos
	 * @return Retorna la cantidad calculadora
	 */
	public Integer totalDocumentos(String palabra){
		Integer posPalabrasEnSet = -1;
		for(String s: palabras){
			posPalabrasEnSet++;
			if(s.equals(palabra)){
				break;
			}
		}
		Integer contador = 0;
		if(posPalabrasEnSet != -1){
			for(ArrayList<Integer> frecuencia: matrizFrecuncias){
				if(frecuencia.get(posPalabrasEnSet+1) > 0){ //+1 porque la primera pos es el id del documento
					contador++;
				}
			}
		}
		return contador;
	}
	/**
	 * Método para imprimir matriz de frecuencias por pantalla
	 */
	public void imprimirMatrizFrecuencias(){
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				for(ArrayList<Integer> l: matrizFrecuncias ){
					for(Integer s: l){
						System.out.print(s+"\t");
					}
					System.out.println();
				}
			}
		});
		hilo.start();
	}
	/**
	 * Método para imprimir matriz de frecuencia inversa por pantalla
	 */
	public void imprimirMatrizFrecuenciasInversas(){
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				for(ArrayList<Double> l: matrizFrecunciasInversas ){
					for(Double s: l){
						System.out.print(s+"\t");
					}
					System.out.println();
				}
			}
		});
		hilo.start();
	}
	/**
	 * Método para crear un archivo de relevancia ID - Documento, 
	 * para el caso de que los id del archivo de relevancias original 
	 * este considerando posiciones de un arreglo en vez del ID
	 */
	public void fixFileRel(){
		try {
			File fix = new File("relFix");
			if(fix.createNewFile()){
				FileWriter escritor = new FileWriter("relFix");
				for(Relevancia r: relevancias){
					escritor.write(consultas.get(r.getQueryID()-1).getId()+" "+r.getDocID()+"\n");
				}
				escritor.close();
			}else{
				System.err.println("Error al crear fichero");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}