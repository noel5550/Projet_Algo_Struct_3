import java.util.*;
import java.lang.Exception;

/*
* <h1>Noeud</h1>
* Le programme qui gere les indices dans une class union
* <p>
* Comme attribut elle a un ArrayList d int contenant les fils d'un noeud 
* Un entier pere_ qui est le père de ce noeud
* Classe assez banale
* @file Noeud.java
* @copyright WTFPL v2
* @author Elbert NYUNTING, Félix PINEL
*/
class Noeud{

	private ArrayList<Tile> fils_; 
	private Tile pere_;

	/*
	*	@brief constructeur de Noeud. Un noeud commence sa vie seul et sans père, pour indiquer cela son pèere est à -1, car dans le tableau de cases, il n'y a pas de Tile au case -1
	*/
	public Noeud(){	
		pere_ = null;	
		fils_ = new ArrayList<Integer>();
	}


	public Tile getPere(){return pere_;}

	public ArrayList<Tile> getFils(){return fils_;}

	public void setPere(Tile tile){pere_ = tile;}

	public void ajouterFils(Tile tile){fils_.add(tile);}

}
