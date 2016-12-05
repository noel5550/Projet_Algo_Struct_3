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

	private ArrayList<Integer> fils_; 
	private int pere_;

	/*
	*	@brief constructeur de Noeud. Un noeud commence sa vie seul et sans père, pour indiquer cela son pèere est à -1, car dans le tableau de cases, il n'y a pas de Tile au case -1
	*/
	public Noeud(){	
		pere_ = -1;	
		fils_ = new ArrayList<Integer>();
	}


	public int getPere(){return pere_;}

	public ArrayList<Integer> getFils(){return fils_;}

	public void setPere(int x){pere_ = x;}

	public void ajouterFils(int x){fils_.add(x);}

}
