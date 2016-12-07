import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;


/*
* <h1>Grid</h1>
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
	

	/*
	* @brief Constructeur de Grid
	* @param int tilePlayer qui est le numéro du joueur de la case dans le grid
	* @pre actingPlayer doit être un joueur de l'équipe jouant en jeu, ou 0 si c'est une case vide, donc actingPlayer appartient à {0,1,2}
	* @see La couleur de la case est indiquée par rapport au numéro du joueur 
	*/ 
	public Grid(int size, int baseNum){
		
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
		int cmptBase1;
		int cmptBase2;
		
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
		dim_ = 50*(size_); /* Dim est 50 fois plus grand que size, donc chaque case est 50 fois plus grand */

		Dimension dimen = new Dimension(dim_, dim_);
		setBackground(Color.GRAY);
		setPreferredSize(dimen);

		GridLayout layout = new GridLayout(size_, size_,2 ,2);
		setLayout(layout);
		
		/*
		*	Remplissage du grid
		*/
		
		while (cmpt < size_*size_){
			this.tileTab_[cmpt] = new Tile(0);
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
			cmptBase1 = 0;
			
			/*
			*	deuxième case
			*/
			cmptBase2 = 0;
			
			/*
			* Pour voir si deux cases sont assez espacées l'un de l'autre
			*/
			assezEspaceePlayer1 = true;
			assezEspaceePlayer2 = true;
			
			
			while(getValue(cmptBase1, cmptBase2) != 0 || !assezEspacee){
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
					distCasesPlayer1 = distanceCases(base1Player1, base2Player1, playerBase1.get(cmpt2)%size_);
					distSizePlayer1 = playerBase1.get(cmpt2)/size_;
								
					if ((max(distCasesPlayer1, distSizePlayer1) < minimumDistance)){
						assezEspaceePlayer1 = false;
					++cmpt2;	
				}
				
				cmpt2 = 0;
				
				/*
				* Player 2
				*/
				while(cmpt2 < playerBase2.size() && assezEspaceePlayer2){
					distCasesPlayer2 = distanceCases(base1Player2, base2Player2, playerBase2.get(cmpt2)%size_);
					distSizePlayer2 = playerBase2.get(cmpt2)/size_;
					
					
					if ((max(distCasesPlayer2, distSizePlayer2) < minimumDistance)){
						assezEspaceePlayer2 = false;
					}
					++cmpt2;	
				}
				
			}
			tileTab_[base1Player1+base2Player1*size_].setBase(1);
			playerBase1.add(base1+base2*size_);
			
			tileTab_[base1Player2+base2Player2*size_].setBase(2);
			playerBase2.add(base1+base2*size_);
			++cmpt;
		}		
	}

									
	
	/*
	* @brief getter de Taille, retorune un entier, taille du grid en nombre de cases 
	*/
	public int getTaille(){return size_;}

	/*
	* @brief getter de dim_, retorune un entier, dimension du grid en pixels
	*/									
	public int getDim() {return dim_;}

	/*
	* @brief getter de la taille n pixels de chaque cases
	*/
	public Tile getTileSmall(int x, int y){return tileTab_[((x-1)/50)+((y-1)/50)*size_];}
	
		/*
	* @brief getter du Tile à partir des coordonnées
	*/
	public Tile getTile2(int x, int y){return tileTab_[x+y*size_];}
	
	/*
	* @brief getter de Tile, retorune le tile dans le tableau
	*/
	public Tile getTile(int tileNum){return tileTab_[tileNum];}

	/*
	* @brief getter de numero de joueur
	*/
	public int getValue(int x, int y){return tileTab_[x+y*size_].getTilePlayer();}

	/*
	* @brief checker pour voir si une case contient une étoile
	*/
	public boolean isBasePlayer(int x, int y){return tileTab_[x + y*size_].isStar();}

	/*
	* @brief getter pour retourner la classe union d'une case par rapport aux coordonnées
	*/
	public int getCompression(int x, int y){return class_.classUnion(x,y);}

	/*
	* @brief getter pour retourner la classe union d'une case par rapport au numero de case
	*/
	public int getCompression(int num){return class_.classUnion(num%size_, num/size_);}


	/*
	* @brief Affichage du grid. Copie complète de l'internet, vous verez probablement cette méthode dans plusieurs groupes.
	*/
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
	

	/*
	* @brief Affichage graphique du composante, avec des clignotements des cases.
	* @details Contient une classe locale qui permet d'utiliser un timer qui change les couleurs de clignotement 
	*/
	public void afficheComposante(int x, int y){
		int shortcut = getCompression(x,y);
		ArrayList<Integer> tmpArray = new ArrayList<Integer>();
		Timer timer = new Timer();			
		tmpArray.addAll(class_.getTousFils(shortcut%size_,shortcut/size_));
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
			    	if(tileTab_[tmpArray.get(i)].getBackground() != Color.yellow){
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


	/*
	* @brief retorune un booleen pour voir si deux cases partagent la même composante
	* @param int x1, y1 coordonnées du premier Tile, x2, y2 coordonnées du deuxième
	*/
	public boolean existeCheminCases(int x1, int y1, int x2, int y2){return (getCompression(x1, y1) == getCompression(x2, y2));}


	/*
	* @brief Donner la distance en vol d'oiseau entre deux cases
	* @param int x1, y1 coordonnées du premier Tile, x2, y2 coordonnées du deuxième
	* @detail se facade dans la méthode pathfind
	*/
	public int relierCasesMin(int x1, int y1, int x2, int y2){
		int res = 0;
		return pathfind(x1, y1, x2, y2, res);
	}
	
	/*
	* @brief Donner la distance en vol d'oiseau entre deux cases
	* @param int xdeb, ydeb coordonnées du premier Tile, xarr, yarr coordonnées du deuxième, int res
	* @return res la distance entre deux cases
	* @detail récursif et ne prend pas en compte les obstacles
	*/
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
			*	Si tileDeb se trouve au SE de tileArr
			*/
			if(((xarr - xdeb)<0) && ((yarr - ydeb)>0)){
				--xdeb;
				++ydeb;
				++res;
				
			/*
			*	Si tileDeb se trouve au NW de tileArr
			*/
			}else if (((xarr - xdeb)>0) && ((yarr - ydeb)<0)){
				++xdeb;
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

	/*
	* @brief nombreEtoiles, parcours les cases pour voir combien il y a de cases base
	* @param int x et y coordonnées de la case
	* @return cmpt nombre d'etoiles
	*/
	public int nombreEtoiles(int x, int y) {
		
		int shortcut = getCompression(x,y);
		int cmpt = 0;
		ArrayList<Integer> stockArray = new ArrayList<Integer>();
		
		/* stockage des noeuds dans l'union class dans une arrayList de stockage */
		stockArray.addAll(class_.getTousFils(shortcut%size_,shortcut/size_));
		stockArray.add(shortcut);
		
		/* Parcours de chaque bases pour voir si il y a des étoiles */
		for (int i = 0; i < stockArray.size(); ++i) {
			if (isBasePlayer(stockArray.get(i)%size_, stockArray.get(i)/size_)){
				++cmpt;
			}
		}

		return cmpt;
	}

	/*
	* @brief relieComposantes, utilise relieComposantesProxy pour voir si l'arrayList retouné par ce dernier ciontient au moins deux cases cherchées
	* @param int x, y corrdonnées de la case étudiée et int playerNum le numéro de joueur
	* @return booléen answer, true s'il y a une case autout de la case étudié appartenant au joueur numéro playerNum
	*/
	public boolean relieComposantes(int x, int y, int playerNum){
		
		boolean answer = false;
		ArrayList<Integer> res = new ArrayList<Integer>();
		res = relieComposantesProxy(x, y, playerNum);		
		if(res.size() >= 2){
			answer = true;
		}
		return answer;

	}
	
		/*
	* @brief relieComposantesProxy, utilise les valeurs de poids afin de voir les cases autour et éviter les bords
	* @param int x, y corrdonnées de la case étudiée et int playerNum le numéro de joueur
	* @return une arraylist res contenant les cases autous du Tile (x, y) appartenant au meme couleur que celui de playerNum
	*/
	public ArrayList<Integer> relieComposantesProxy(int x, int y, int playerNum){
		int xstock1 = 0;
		int ystock1 = 0;
		int xstock2 = 0;
		int ystock2 = 0;
		
		
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		ArrayList<Integer> res = new ArrayList<Integer>();


		/* Mise en place les poids des cases */
		if (x == size_ - 1){
			
			if(y == size_ - 1){
				xstock1 = -1; 
				ystock1 = -1; 
				xstock2 = 0; 
				ystock2 = 0;
			}			
			else if( y == 0){
				xstock1 = -1; 
				ystock1 = 0; 
				xstock2 = 0; 
				ystock2 = 1;
			}
			else{
				xstock1 = -1; 
				ystock1 = -1; 
				xstock2 = 0; 
				ystock2 = 1;
			}
		}		
		else if (x == 0){
			
			if(y == size_ - 1){
				xstock1 = 0; 
				ystock1 = -1; 
				xstock2 = 1; 
				ystock2 = 0;
			}			
			else if( y == 0){
				xstock1 = 0; 
				ystock1 = 0;
				xstock2 = 1; 
				ystock2 = 1;
			}
			else{
				xstock1 = 0; 
				ystock1 = -1; 
				xstock2 = 1; 
				ystock2 = 1;
			}
		}
		else{
		
			if(y == size_-1){
				xstock1 = -1; 
				ystock1 = -1; 
				xstock2 = 1; 
				ystock2 = 0;
			}			
			else if( y == 0){
				xstock1 = -1; 
				ystock1 = 0; 
				xstock2 = 1; 
				ystock2 = 1;
			}
			else {
				xstock1 = -1; 
				ystock1 = -1; 
				xstock2 = 1; 
				ystock2 = 1;
			}
		}

		for (int i = ystock1; i <= ystock2; ++i){
			for (int j = xstock1; j <= xstock2;  ++j){
				if (getValue(x+j, y+i) == playerNum){
					if(tmp.size() == 0){
						tmp.add(class_.classUnion(x+j, y+i));
						res.add((x+l)+(y+k)*size_);
					}
					boolean newComp = true;
					for (int k = 0; k < tmp.size(); ++k) {
						if (existeCheminCases(x+j, y+i, tmp.get(k)%size_, tmp.get(k)/size_)){
							newComp = false;
						}
					}
					if (newComp){
						tmp.add(class_.classUnion(x+j, y+i));
						res.add((x+j)+(y+i)*size_);
					}
				}
			}
		}
	}

	
	public void union(int x1, int y1, int x2, int y2){class_.union(x1, y1, x2, y2);}
	
	public int distanceCases(int x1, int y1, int x2, int y2){

		int tmp_1 = Math.abs(x1-x2);
		int tmp_2 = Math.abs(y1-y2);
		return Math.max(tmp_1, tmp_2) - 1;

	}
	
}
