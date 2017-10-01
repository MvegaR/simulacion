package modelo;
/**
 * Clase de referencias no utilizada en el proyecto, corresponde a las referencias de los documentos, 
 * nacesitaba una estructura de datos abstracta nueva para lectura.
 * 
 * Descripción en ingles pressente en el archivo, base de datos CACM:
 * 4 : "bibliographic coupling" - if document id Y appears in the bibliographic
 *   coupling subvector for document X with a weight of w, it means X
 *   and Y have w common references in their bibliographies; the weight
 *   of did X in the vector for X is the number of items in X's bibliography.
 * 5 : "links" - documents X and Y are linked if X cites Y, Y cites X, or
 *   X == Y.
 * 6 : "co-citations" - if document id Y appears in the co-citation subvector
 *   for document X with weight w, it means X and Y are cited together in
 *   w documents; the weight of did X in the vector for X is the number
 *  of documents that cite X.
 *  
 *   @author Marcos
 **/
public class Referencia {

	private Integer x;
	private Integer tipo;
	private Integer y;

	public Referencia(Integer x, Integer tipo, Integer y){
		this.x = x;
		this.y = y;
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Referencia [x=" + x + ", tipo=" + tipo + ", y=" + y + "]";
	}

	public Integer getX() {
		return x;
	}
	public Integer getTipo() {
		return tipo;
	}
	public Integer getY() {
		return y;
	}

}
