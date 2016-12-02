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
* Comme attribut elle a un entier de Tile tiletileTab_
* Une class union class_, un ArrayList en entier des cases bases (étoiles) des deux joueurs
* et deux entiers donnant la taille du grid carrée 
*et sa dimension pour son affichage graphique
*
* @file Grid.java
* @copyright WTFPL v2
* @author Elbert NYUNTING, Félix PINEL
*/
class Grid extends JPanel {

	
	private Tile tiletileTab_[];
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


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////CONTINUE HERE///////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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


	//!\brief Méthode n°3 isTheSame
	//!\param x, y, z, t : x et y les coordonnées de la première case; z et t les coordonnées de la seconde case
	//!\return un booléen si les deux cases appartiennent à la même composante
	public boolean isTheSame(int x, int y, int z, int t){
		return (getCompression(x, y) == getCompression(z, t));
	}


	//!\brief Méthode n°4 relieCaseMin
	//!\param x, y, z, t : x et y les coordonnées de la première case; z et t les coordonnées de la seconde case;
	//!\return compt : le nombre minimum de cases blanche à colorié afin de rejoindre les deux cases. Retourne -1 si les deux cases sont d'une couleur différente
	public int relieCaseMin(int x, int y, int z, int t){

		int xDep = 0;
		int yDep = 0;
		int xArr = 0;
		int yArr = 0;
		int compt = 0;

		// On vérifie si les deux cases ont la même couleur 
		if(getValue(x, y) == getValue(z, t)){

			ArrayList<Integer> caseImp = new ArrayList<Integer>();		// ArrayList permettant d'éviter de retourner sur une case déjà étudié
			ArrayList<Integer> caseWait = new ArrayList<Integer>();		// ArrayList de stockage de cases disponibles pour le tour en cours
			ArrayList<Integer> caseAcc = new ArrayList<Integer>();		// ArrayList de stockage des cases disponibles pour le tour suivant

			caseAcc.add(x+y*size_);
			caseWait.add(x+y*size_);
			caseImp.add(x+y*size_);
			
			for (int v = 0; v < size_*size_; ++v) {						

				caseWait.clear();
				caseWait.addAll(caseAcc);
				caseAcc.clear();

				for (int a = 0; a < caseWait.size(); ++a) {


					int xTmp = caseWait.get(a)%size_;
					int yTmp = caseWait.get(a)/size_;

					// Pour chaque case on attribu des valeurs différentes pour la double boucle à suivre afin d'éviter
					// que le programme essaye d'effectuer ses test sur des cases extérieurs au tableau (-1 par exemple)
					if (xTmp == 0){
						if( yTmp == 0){
							xDep = 0; yDep = 0; xArr = 1; yArr = 1;
						}
						else if(yTmp == size_ - 1){
							xDep = 0; yDep = -1; xArr = 1; yArr = 0;
						}
						else {
							xDep = 0; yDep = -1; xArr = 1; yArr = 1;
						}
					}
					else if (xTmp == size_ - 1){
						if( yTmp == 0){
							xDep = -1; yDep = 0; xArr = 0; yArr = 1;
						}
						else if(yTmp == size_ - 1){
							xDep = -1; yDep = -1; xArr = 0; yArr = 0;
						}
						else {
							xDep = -1; yDep = -1; xArr = 0; yArr = 1;
						}
					}
					else{
						if( yTmp == 0){
							xDep = -1; yDep = 0; xArr = 1; yArr = 1;
						}
						else if(yTmp == size_-1){
							xDep = -1; yDep = -1; xArr = 1; yArr = 0;
						}
						else {
							xDep = -1; yDep = -1; xArr = 1; yArr = 1;
						}
					}

					// Double boucle qui permet de tester toutes les cases adjacentes à la case actuelle
					for (int i = yDep; i <= yArr; ++i) {
						for (int j = xDep; j <= xArr; ++j){

							// Si c'est la case actuelle on la passe (aucun intérêt à la tester)
							if (i == 0 && j == 0) 
								continue;

							// Si c'est une case blanche alors...
							if (getValue(xTmp+j, yTmp+i) == 0){

								// Si elle est adjacente à la case d'arriver on retourne le compteur+1
								if (distanceCase(xTmp+j, yTmp+i, z, t) == 0)
										return ++compt;

								// Si elle n'appartient pas au cases imposibles on l'ajoute dans le tableau de case pour le tour suivant ainsi qu'au tableau de cases impossible
								if (!caseImp.contains(((xTmp+j)+(yTmp+i)*size_))){
									caseAcc.add(((xTmp+j)+(yTmp+i)*size_));
									caseImp.add(((xTmp+j)+(yTmp+i)*size_));
								}
							}
							// ...sinon si elle est de la même couleur que la case actuelle alors...
							else if (getValue(xTmp+j, yTmp+i) == getValue(x, y)) {


								// Si elle n'appartient pas aux cases impossibles alors...
								if (!caseImp.contains((xTmp+j)+(yTmp+i)*size_)) {

									ArrayList<Integer> tabComp = new ArrayList<Integer>();

									// On ajoute d'en un tableau temporaires tous les éléments de la composante dont elle fait partie
									int pereComp = getComp((xTmp+j),(yTmp+i));
									tabComp.addAll(class_.getTousFils(pereComp%size_, pereComp/size_));
									tabComp.add(pereComp);

									int xDep2 = 0;
									int yDep2 = 0;
									int xArr2 = 0;
									int yArr2 = 0;
									int xTmp2 = 0;
									int yTmp2 = 0;

									// On vérifie que la case d'arrivé n'appartient pas à la composante
									for (int f = 0; f < tabComp.size(); ++f) {

										xTmp2 = tabComp.get(f)%size_;
										yTmp2 = tabComp.get(f)/size_;

										// Si c'est le cas on retourne le compteur
										if (distanceCase(xTmp2, yTmp2, z, t) == -1)
											return compt;
									}

									// Sinon pour chaque case de la composante...
									for (int f = 0; f < tabComp.size(); ++f) {

										xTmp2 = tabComp.get(f)%size_;
										yTmp2 = tabComp.get(f)/size_;

										// ...on attribu des valeurs différentes pour la double boucle à suivre afin d'éviter
										// que le programme essaye d'effectuer ses test sur des cases extérieurs au tableau (-1 par exemple)
										if (xTmp2 == 0){
											if( yTmp2 == 0){
												xDep2 = 0; yDep2 = 0; xArr2 = 1; yArr2 = 1;
											}
											else if(yTmp2 == size_ - 1){
												xDep2 = 0; yDep2 = -1; xArr2 = 1; yArr2 = 0;
											}
											else {
												xDep2 = 0; yDep2 = -1; xArr2 = 1; yArr2 = 1;
											}
										}
										else if (xTmp2 == size_ - 1){
											if( yTmp+i == 0){
												xDep2 = -1; yDep2 = 0; xArr2 = 0; yArr2 = 1;
											}
											else if(yTmp2 == size_ - 1){
												xDep2 = -1; yDep2 = -1; xArr2 = 0; yArr2 = 0;
											}
											else {
												xDep2 = -1; yDep2 = -1; xArr2 = 0; yArr2 = 1;
											}
										}
										else{
											if( yTmp2 == 0){
												xDep2 = -1; yDep2 = 0; xArr2 = 1; yArr2 = 1;
											}
											else if(yTmp2 == size_-1){
												xDep2 = -1; yDep2 = -1; xArr2 = 1; yArr2 = 0;
											}
											else {
												xDep2 = -1; yDep2 = -1; xArr2 = 1; yArr2 = 1;
											}
										}

										// Double boucle qui permet de tester toutes les cases adjacentes à toutes les cases de la composante
										for (int i2 = yDep2; i2 <= yArr2; ++i2) {
											for (int j2 = xDep2; j2 <= xArr2; ++j2){

												// Si ce n'est pas la case actuelle de la composante alors...
												if (i2 != 0 || j2 != 0) {

													// Si c'est une case blanche alors...
													if (getValue(xTmp2+j2, yTmp2+i2) == 0){

														// Si elle est adjacente à la case d'arriver on retourne le compteur+1
														if(distanceCase(xTmp2+j2, yTmp2+i2, z, t) == 0)
															return ++compt;

														// Sinon si elle n'appartient pas au cases imposibles on l'ajoute dans le tableau de case pour le tour suivant ainsi qu'au tableau de cases impossible
														if (!caseImp.contains(((xTmp2+j2)+(yTmp2+i2)*size_))){
															caseAcc.add(((xTmp2+j2)+(yTmp2+i2)*size_));
															caseImp.add(((xTmp2+j2)+(yTmp2+i2)*size_));
														}
													}
													// Sinon si c'est une case de couleur adverse on l'ajoute au tableau de case impossible (si elle n'y est pas déjà)
													else if (getValue(xTmp2+j2, yTmp2+i2) != getValue(x, y)){

														if (!caseImp.contains(((xTmp+j2)+(yTmp+i2)*size_)))
															caseImp.add(((xTmp2+j2)+(yTmp2+i2)*size_));
													}
												}
												// Sinon c'est la case actuelle de la composante donc on l'ajoute directement aux case impossibles
												else{
													if (!caseImp.contains(((xTmp2+j2)+(yTmp2+i2)*size_))){
														caseImp.add(((xTmp2+j2)+(yTmp2+i2)*size_));
													}
												}
											}
										}
									}
								}
							}
							//Sinon c'est une case de couleur adverse donc on l'ajoute au tableau des cases impossibles (si elle n'y est pas déjà)
							else{
								if (!caseImp.contains(((xTmp+j)+(yTmp+i)*size_))){
									caseImp.add(((xTmp+j)+(yTmp+i)*size_));
								}
							}
						}
					}
					// Si le tableau pour le tour suivant est vide c'est que la case est bloqué dans un coin, donc on l'ajoute au tableau des cases impossibles
					if (caseAcc.isEmpty()){
						caseImp.add((xTmp+yTmp*size_));
					}
				}
				// Pour finir on incrémente le compteur à la fin du tour
				++compt;
			}
		}
		// Sinon les deux cases ne sont pas de la même couleur, ou bien il n'y a pas de passage possible entre les deux cases donc on retourne -1
		return -1;
	}
	

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

		int xDep = 0;
		int yDep = 0;
		int xArr = 0;
		int yArr = 0;


		// On attribu des valeurs différentes pour la double boucle à suivre afin d'éviter
		// que le programme essaye d'effectuer ses test sur des cases extérieurs au tableau (-1 par exemple)
		if (x == 0){
			if( y == 0){
				xDep = 0; yDep = 0; xArr = 1; yArr = 1;
			}
			else if(y == size_ - 1){
				xDep = 0; yDep = -1; xArr = 1; yArr = 0;
			}
			else {
				xDep = 0; yDep = -1; xArr = 1; yArr = 1;
			}
		}
		else if (x == size_ - 1){
			if( y == 0){
				xDep = -1; yDep = 0; xArr = 0; yArr = 1;
			}
			else if(y == size_ - 1){
				xDep = -1; yDep = -1; xArr = 0; yArr = 0;
			}
			else {
				xDep = -1; yDep = -1; xArr = 0; yArr = 1;
			}
		}
		else{
			if( y == 0){
				xDep = -1; yDep = 0; xArr = 1; yArr = 1;
			}
			else if(y == size_-1){
				xDep = -1; yDep = -1; xArr = 1; yArr = 0;
			}
			else {
				xDep = -1; yDep = -1; xArr = 1; yArr = 1;
			}
		}

		// Double boucle qui permet de tester toutes les cases adjacentes la case actuelle
		for (int k = yDep; k <= yArr; ++k) {
			for (int l = xDep; l <= xArr;  ++l) {

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
						if (isTheSame(x+l, y+k, tmp.get(i)%size_, tmp.get(i)/size_)){
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
