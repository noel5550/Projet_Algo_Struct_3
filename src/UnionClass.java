import java.util.*;

class UnionClass{
	
	private ArrayList<Indice> classUnion_;
	private int taille_;


	//!\ Constructeur de la UnionClass
	public UnionClass(int t){

		//!\ Initialisation des variables
		taille_ = t;
		classUnion_ = new ArrayList<Indice>();

		for (int x = 0; x < taille_*taille_; ++x) {
			classUnion_.add(new Indice());
		}
	}


	//!\ Méthode d'union de la composante contenant la case de coordonnée (x1,y1) avec la composante contenant la case de coordonnée (x2, y2) par union pondérée
	public void union(int x1, int y1, int x2, int y2){ 

		int vRac = classUnion(x1,y1);
		int wRac = classUnion(x2,y2);
		int tv = getAllFils(vRac%taille_, vRac/taille_).size();
		int tw = getAllFils(wRac%taille_, wRac/taille_).size();


		if (vRac != wRac) {
			if (tv <= tw) {
				classUnion_.get(vRac).setPere(wRac);
				classUnion_.get(wRac).ajouterFils(vRac);
			}
			else{
				classUnion_.get(wRac).setPere(vRac);
				classUnion_.get(vRac).ajouterFils(wRac);
			}
		}

	}


	//!\ Méthode retournant la classUnion d'une case de coordonnée (x, y) avec compression des chemins
	public int classUnion(int x, int y){
		if (classUnion_.get(x+y*taille_).getPere() == -1)
			return (x+y*taille_);
		else{	
			int a = classUnion(classUnion_.get(x+y*taille_).getPere()%taille_, classUnion_.get(x+y*taille_).getPere()/taille_);
			classUnion_.get(x+y*taille_).setPere(a);
			return a;	
		}
	}


	//!\---------------------- Getters
	public int getTaille(){

		return taille_;
	}

	//!\ Permet d'obtenir tous les fils d'un Indice de la UnionClass (cette fonction est généralement appelé sur la racine)
	public ArrayList<Integer> getAllFils(int x, int y){
		
		int t = classUnion_.get(x+y*taille_).getFils().size();

		if (t == 0)
			return (new ArrayList<Integer>());

		else{
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.addAll(classUnion_.get(x+y*taille_).getFils());
			for (int i = 0; i < t; ++i)
				tmp.addAll(getAllFils(tmp.get(i)%taille_, tmp.get(i)/taille_));

			return tmp;
		}
	}
	//!\---------------------- Fin Getters
}
