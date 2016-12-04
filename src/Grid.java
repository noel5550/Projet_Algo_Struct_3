import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.lang.Exception;

/**
* <h1>Tile</h1>
* Le programme Grid implémente le plateau composé de cases (ou Tile)
* Elle traite aussi la création graphique de la grille
* <p>
* Comme attribut elle a un entier de Tile tileTab_
* Une class union class_, un ArrayList en entier des cases bases (étoiles) des deux joueurs
* et deux entiers donnant la taille du grid carrée 
*et sa dimension pour son affichage graphique
*
* @file Grid.java
* @copyright WTFPL v2
* @author Elbert NYUNTING, Félix PINEL
*/
class Grid extends JPanel {

	
	private Tile tileTab_[];
	private UnionClass class_;	
	private ArrayList<Integer> playerBase1;
	private ArrayList<Integer> playerBase2;		
	private int size_;											
	private int dim_; 												
	

	/**
	* @brief Constructeur de Grid
	* @param int tilePlayer qui est le numéro du joueur de la case dans le grid
	* @pre actingPlayer doit être un joueur de l'équipe jouant en jeu, ou 0 si c'est une case vide, donc actingPlayer appartient à {0,1,2}
	* @see La couleur de la case est indiquée par rapport au numéro du joueur 
	*/ 
	public Grid(int size, int baseNum) throws Exception{
		if(size <= 1){
			throw new Exception ("Grille trop petite !");
		}
		
		if(baseNum <=1){
			throw new Exception ("Plus de bases ! ");
		}
		
		if(baseNum >= size){
			throw new Exception ("Pas de place pour jouer ! ");
		}
		/*
		* Compteurs
		*/
		int cmpt = 0;
		int base1Player1;
		int base2Player1;
		int base1Player2;
		int base2Player2;
		int cmpt2;
		int distCases;
		
		boolean assezEspaceePlayer1;
		boolean assezEspaceePlayer2;
		
		/*
		* Setting attributes
		*/
		this.size_ = size;
		class_ = new UnionClass(size_);
		tileTab_ = new Tile[size_*size_];
		playerBase1 = new ArrayList<Integer>();
		playerBase2 = new ArrayList<Integer>();
		dim_ = 25*(size_);

		Dimension dimen = new Dimension(dim_, dim_);
		setBackground(Color.GRAY);
		setPreferredSize(dimen);

		GridLayout layout = new GridLayout(size_, size_,2 ,2);
		setLayout(layout);
		
		/*
		*	Remplissage du grid
		*/
		
		while (cmpt < size_*size_){
			tileTab_[cmpt] = new Tile(0);
			cmpt ++;
			add(tileTab_[cmpt]);
		} 
		
		/*
		*	Distance minimum entre chaque cases base/étoile, pour pouvoir jouer, voyons !
		*/
		int minimumDistance = size_/baseNum;

		/*
		*	Ajout de bases pour le premier joueur
		*/
		cmpt = 0;
		while (cmpt < baseNum){
			/*
			*	première case
			*/
			base1 = 0;
			
			/*
			*	deuxième case
			*/
			base2 = 0;
			
			/*
			* Pour voir si deux cases sont assez espacées l'un de l'autre
			*/
			assezEspaceePlayer1 = true;
			assezEspaceePlayer2 = true;
			
			
			while(getValue(base1, base2) != 0 || !assezEspacee){
				assezEspaceePlayer1 = true;
				assezEspaceePlayer2 = true;
				base1Player1 = (int)(size_ * Math.random());
				base2Player1 = (int)(size_ * Math.random());
				
				base1Player2 = (int)(size_ * Math.random());
				base2Player2 = (int)(size_ * Math.random());
				cmpt2 = 0;
				
				/*
				*Player1
				*/ 
				while(cmpt2 < playerBase1.size()&& assezEspaceePlayer1){
					distCasesPlayer1 = distanceCase(base1Player1, base2Player1, playerBase1.get(cmpt2)%size_;
					distSizePlayer1 = playerBase1.get(cmpt2)/size_;
								
					if (((distCasesPlayer1, distSizePlayer1) < minimumDistance)){
						assezEspaceePlayer1 = false;
					}
					++cmpt2;	
				}
				
				cmpt2 = 0;
				
				/*
				* Player 2
				*/
				while(cmpt2 < playerBase2.size() && assezEspaceePlayer2){
					distCasesPlayer2 = distanceCase(base1Player2, base2Player2, playerBase2.get(cmpt2)%size_;
					distSizePlayer2 = playerBase2.get(cmpt2)/size_;
					
					
					if (((distCasesPlayer2, distSizePlayer2) < minimumDistance)){
						assezEspaceePlayer2 = false;
					}
					++cmpt2;	
				}
				
			}
			// On modifie les coordonnée de la base tant que les coordonnées ne sont pas ceux d'une base déjà existante 
			// et que la distance min entre la nouvelle base et toutes les bases déjà existante est bien supérieur ou égale à distMin
							
			// On transforme la Tile en base si les conditions sont respectées et on ajoute ses coordonnées à l'ArrayList de stockage des bases
			tileTab_[base1Player1+base2Player1*size_].setBase(1);
			playerBase1.add(base1+base2*size_);
			
			tileTab_[base1Player2+base2Player2*size_].setBase(2);
			playerBase2.add(base1+base2*size_);
			++cmpt;
		}		
	}

	//!\---------------------- Getters

	public int getTaille(){
		return size_;
	}

	public int getDim() {
		return dim_;
	}

	public Tile getTileSmall(int x, int y){
		return tileTab_[((x-1)/25)+((y-1)/25)*size_];
	}

	public Tile getTile(int tileNum){
		return tileTab_[tileNum];
	}

	public int getValue(int x, int y){
		return tileTab_[x+y*size_].getTilePlayer();
	}

	public boolean isBasePlayer(int x, int y){
		return tileTab_[x + y*size_].isStar();
	}

	public int getCompression(int x, int y){
		return class_.classUnion(x,y);
	}


	public int getCompression(int num){
		return class_.classUnion(num%size_, num/size_);
	}


	public void afficher(int x, int y){
		System.out.println("Les numéros de cases appartenant au père : " + class_.classUnion(x,y));

		int compression = getCompression(x,y);
		int cmpt = 0;
		ArrayList<Integer> tmpArray = new ArrayList<Integer>();
		tmpArray.addAll(class_.getAllFils(compression%size_, compression/size_));
		while(cmpt < tmpArray.size()){
			System.out.print(", " + tmpArray.get(cmpt));
			++cmpt;
		}
		System.out.println("=======================");		
	}
	
	
	//!\---------------------- Fin Getters


	//!\brief Méthode n°2 afficheComposante
	//!\param x, y : les coordonnées de la case dont pour laquelle la composante sera affiché
	public void afficheComposante(int x, int y) throws Exception{
		if(getVal(x, y) == 0){
			throw new Exception("C'est la même case !");
		}
		int shortcut = getCompression(x,y);
		ArrayList<Integer> tmpArray = new ArrayList<Integer>();
		Timer timer = new Timer();			
		tmpArray.addAll(class_.getTousFils(rac%size_,rac/size_));
		tmpArray.add(shortcut);
			
		/*
		* Création d'un timer pour beeps
		*/
		class CountingBeeps extends TimerTask {
			static int nbRep_ = 0;
		    public void run() {
		    	++nbRep_;
		    	if(nbRep >= 12){
			    	timer.cancel();
			    	timer.purge();
			    	return;
			    }
			    	
			    for (int i = 0; i < tmpArray.size(); ++i) {
			    	if(tileTab_[tmpArray.get(i)]getBackground() != Color.yellow){
			   			tileTab_[tmpArray.get(i)].coloTile("yellow");
					}else if(tileTab_[tmpArray.get(i)].getTilePlayer()==1){
						tileTab_[tmpArray.get(i)].coloTile("red");
					}else if(tileTab_[tmpArray.get(i)].getTilePlayer()==2){
						tileTab_[tmpArray.get(i)].coloTile("blue");
					}
								   	
			   	}
	   		}
	   	}
	    timer.scheduleAtFixedRate(new CountingBeeps(),0, 1000);
	}


	//!\brief Méthode n°3 existeCheminCases
	//!\param x, y, z, t : x et y les coordonnées de la première case; z et t les coordonnées de la seconde case
	//!\return un booléen si les deux cases appartiennent à la même composante
	public boolean existeCheminCases(int x, int y, int z, int t){
		return (getCompression(x, y) == getCompression(z, t));
	}


	//!\brief Méthode n°4 relierCasesMin
	//!\param x, y, z, t : x et y les coordonnées de la première case; z et t les coordonnées de la seconde case;
	//!\return compt_ : le nombre minimum de cases blanche à colorié afin de rejoindre les deux cases.
	public int relierCasesMin(int x1, int y1, int x2, int y2){
		int res = 0;
		return pathfind(x1, y1, x2, y2, res);
	}
	
	public int pathfind(int xdeb, int ydeb, int xarr, int yarr, int res){
		int result = res;
		/*
		*	Si on compare deux cases identiques et cond d'arret
		*/
		if((xdeb == xarr)&&(ydeb == yarr)){
			return result;
		}else{
			/*
			*	Si tileDeb se trouve au SW de tileArr
			*/
			if(((xarr - xdeb)>0) && ((yarr - ydeb)>0)){
				++xdeb;
				++ydeb;
				++res;
				
			/*
			*	Si tileDeb se trouve au NE de tileArr
			*/
			}else if (((xarr - xdeb)<0) && ((yarr - ydeb)<0)){
				--xdeb;
				--ydeb;
				++res;	

			/*
			*	Si tileDeb se trouve au Nord de tileArr
			*/				
			}else if (((xarr - xdeb) == 0) && ((yarr - ydeb)<0)){
				--ydeb;
				++res;	
			/*
			*	Si tileDeb se trouve au Sud de tileArr
			*/					
			}else if (((xarr - xdeb) == 0) && ((yarr - ydeb)>0)){
				++ydeb;
				++res;
				
			/*
			*	Si tileDeb se trouve a l'Ouest de tileArr
			*/	
			}else if (((xarr - xdeb)>0) && ((yarr - ydeb)==0)){
				++xdeb;
				++res;
				
			/*
			*	Si tileDeb se trouve a l'Est de tileArr
			*/		
			}else if (((xarr - xdeb)<0) && ((yarr - ydeb)==0)){
				--xdeb;
				++res;
			}
			pathfind(xdeb, ydeb, xarr, yarr, res);
		}
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////CONTINUE HERE///////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//!\brief Méthode n°5 nombreEtoiles
	//!\param x, y : les coordonnées de la case pour laquelle on veut connaitre le nombre de bases appartenant à sa composante
	//!\return un entier étant le nombre de base appartenant à la composante
	public int nombreEtoiles(int x, int y){

		int compt = 0;
		int rac = getComp(x,y);
		ArrayList<Integer> tmp = new ArrayList<Integer>();

		tmp.addAll(class_.getTousFils(rac%size_,rac/size_));
		tmp.add(rac);

		// Pour tous les éléments de la composante on test si c'est une base et on incrément le compteur si c'est le cas
		for (int i = 0; i < tmp.size(); ++i) {
			if (isBasePlayer(tmp.get(i)%size_, tmp.get(i)/size_))
				compt++;
		}

		return compt;
	}


	//!\brief Méthode n°7 relieComposantes
	//!\param x, y, c : x et y les coordonnées de la case à tester; c la couleur de la case à tester
	//!\return un ArrayList d'entier contenant la position des cases concernées
	public ArrayList<Integer> relieComposantes(int x, int y, int c){

		ArrayList<Integer> tmp = new ArrayList<Integer>();
		ArrayList<Integer> res = new ArrayList<Integer>();

		int xstock1 = 0;
		int ystock1 = 0;
		int xstock2 = 0;
		int ystock2 = 0;


		// On attribu des valeurs différentes pour la double boucle à suivre afin d'éviter
		// que le programme essaye d'effectuer ses test sur des cases extérieurs au tableau (-1 par exemple)
		if (x == 0){
			if( y == 0){
				xstock1 = 0; ystock1 = 0; xstock2 = 1; ystock2 = 1;
			}
			else if(y == size_ - 1){
				xstock1 = 0; ystock1 = -1; xstock2 = 1; ystock2 = 0;
			}
			else {
				xstock1 = 0; ystock1 = -1; xstock2 = 1; ystock2 = 1;
			}
		}
		else if (x == size_ - 1){
			if( y == 0){
				xstock1 = -1; ystock1 = 0; xstock2 = 0; ystock2 = 1;
			}
			else if(y == size_ - 1){
				xstock1 = -1; ystock1 = -1; xstock2 = 0; ystock2 = 0;
			}
			else {
				xstock1 = -1; ystock1 = -1; xstock2 = 0; ystock2 = 1;
			}
		}
		else{
			if( y == 0){
				xstock1 = -1; ystock1 = 0; xstock2 = 1; ystock2 = 1;
			}
			else if(y == size_-1){
				xstock1 = -1; ystock1 = -1; xstock2 = 1; ystock2 = 0;
			}
			else {
				xstock1 = -1; ystock1 = -1; xstock2 = 1; ystock2 = 1;
			}
		}

		// Double boucle qui permet de tester toutes les cases adjacentes la case actuelle
		for (int k = ystock1; k <= ystock2; ++k) {
			for (int l = xstock1; l <= xstock2;  ++l) {

				// Si c'est la case actuelle on la passe (aucun intérêt à la tester)
				if (k == 0 && l == 0) 		
					continue;

				// Si la case adjacente est de la même couleur que la case actuelle et que l'ArrayList temporaire est vide, on ajoute la classUnion de la case adjacente dans l'ArrayList
				if (getValue(x+l, y+k) == c && tmp.size() == 0){
					tmp.add(class_.classUnion(x+l, y+k));
					res.add((x+l)+(y+k)*size_);
				}
				// Sinon si la case adjacente est de la même couleur que la case actuelle alors...
				else if (getValue(x+l, y+k) == c){

					boolean newComp = true;
					// ... pour toutes les valeur de tmp...
					for (int i = 0; i < tmp.size(); ++i) {

						// ... on test si la classUnion de la case adjacente n'est pas déjà dedans, si ce n'est pas le cas on incrément le compteur et on ajoute sa classUnion dans tmp
						if (existeCheminCases(x+l, y+k, tmp.get(i)%size_, tmp.get(i)/size_)){
							newComp = false;
						}
					}
					if (newComp) {
						tmp.add(class_.classUnion(x+l, y+k));
						res.add((x+l)+(y+k)*size_);
					}
				}
			}
		}

		return res;

	}

	//!\brief Méthode n°9.1 evaluerCase1
	//!\param x, y, j : x et y les coordonnées de la case à tester; j le joueur pour lequel la fonction est utilisé
	//!\return un entier étant l'index dans la grille de la case à colorié
	public int evaluerCase1(int x, int y, int j){
		if(j == 1){
			if(getValue(x, y) != 0)
				return size_;
			else
				return distanceCase(x, y, xCentreJ1, yCentreJ1);
		}
		else if(j == 2){
			if(getValue(x, y) != 0)
				return size_;
			else
				return distanceCase(x, y, xCentreJ2, yCentreJ2);
		}
		return size_;
	}

	// ------------------------------------------ Méthodes supplémentaires

	//!\ Fait l'union entre les cases de coordonnées (x, y) et (z, t)
	public void union(int x, int y, int z, int t){

		class_.union(x, y, z, t);

	}

	//!\ Retourne la distance entre deux cases (non comprises)
	public int distanceCase(int x, int y, int z, int t){

		int tmp1 = Math.abs(x-z);
		int tmp2 = Math.abs(y-t);
		return Math.max(tmp1, tmp2) - 1;

	}
		
}
