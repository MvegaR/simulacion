package documentosConsultas;

public class Referencia {
	/*
	 * 
     * 
    4 : "bibliographic coupling" - if document id Y appears in the bibliographic
	    coupling subvector for document X with a weight of w, it means X
	    and Y have w common references in their bibliographies; the weight
	    of did X in the vector for X is the number of items in X's bibliography.
	5 : "links" - documents X and Y are linked if X cites Y, Y cites X, or
	    X == Y.
	6 : "co-citations" - if document id Y appears in the co-citation subvector
	    for document X with weight w, it means X and Y are cited together in
	    w documents; the weight of did X in the vector for X is the number
	    of documents that cite X.
	*/
	private Integer x;
	private Integer tipo;
	private Integer y;
	
	public Referencia(Integer x, Integer tipo, Integer y){
		this.x = x;
		this.y = y;
		this.tipo = tipo;
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
