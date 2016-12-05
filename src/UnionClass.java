import java.util.*;
import java.lang.Exception;

/*
* <h1>UnionClass</h1>
* Le programme qui gere les class unions
* <p>
* Comme attribut elle a un ArrayList de noeud appelé union_ 
* Un entier size_ retournant la taille
*
* @file UnionClass.java
* @copyright WTFPL v2
* @author Elbert NYUNTING, Félix PINEL
*/

class UnionClass{
	
	private ArrayList<Noeud> union_;
	private int size_;

	/*
	* @brief Constructeur de UnionClass
	* @param int size donnant la taille de UnionClass
	* @see On ajoute size² noeuds comme il y a t² cases dans une grille de taille size
	*/ 
	public UnionClass(int size){

		this.size_ = size;
		this.union_ = new ArrayList<Noeud>();

		for (int x = 0; x < size_*size_; ++x) {
			this.union_.add(new Noeud());
		}
	}
	/*
	*	@brief getter de Taille, retourne size_;
	*/
	public int getSize(){return size_;}

	/*
	*	@brief parcours récursif pour obtenir tous les fils d'un noeud d'une case dont les coordonnées sont données au paramètre
	*/
	public ArrayList<Integer> getAllFils(int x, int y){
		
		int size = union_.get(x+y*size_).getFils().size();

		if(size != 0){
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.addAll(union_.get(x+y*size_).getFils());
			for (int i = 0; i < size; ++i)
				tmp.addAll(getAllFils(tmp.get(i)%size_, tmp.get(i)/size_));

			return tmp;
		}else{return (new ArrayList<Integer>());} /* Condition d'arret */
		
	}
	
	/**
	* @brief classUnion compresse les chemins du noeud les tiles de coordonnées x, y
	* @param int x, y coordonnées du tile étudié
	*/ 
	public int classUnion(int x, int y){
			int res;
			int tmp1;
			int tmp2;
		if (union_.get(x+y*size_).getPere() == -1)
			return (x+y*size_);
		else{	
			tmp1 = union_.get(x+y*size_).getPere()%size_;
			tmp2 = union_.get(x+y*size_).getPere()/size_;
			res = classUnion(tmp1,tmp2);
			union_.get(x+y*size_).setPere(res);
			return res;	
		}
	}

	/*
	* @brief Méthode pour unir l'union des cases de coordonnées (x1,y1) avec celle de la case (x2, y2)
	*/
	public void union(int x1, int y1, int x2, int y2){ 
		
		int tile1Shorten = classUnion(x1,y1);
		int tile2Shorten = classUnion(x2,y2);
		int tv = getAllFils(tile1Shorten%size_, tile1Shorten/size_).size();
		int tw = getAllFils(tile2Shorten%size_, tile2Shorten/size_).size();


		if(getAllFils(tile1Shorten%size_, tile1Shorten/size_).size() >= getAllFils(tile2Shorten%size_, tile2Shorten/size_).size()){
			union_.get(tile2Shorten).setPere(tile1Shorten);
			union_.get(tile1Shorten).ajouterFils(tile2Shorten);
		}else{
			union_.get(tile1Shorten).setPere(tile2Shorten);
			union_.get(tile2Shorten).ajouterFils(tile1Shorten);			
		}
	}
}
