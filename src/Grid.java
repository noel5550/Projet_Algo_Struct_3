import java.util.Random;
import java.util.Scanner;
import java.lang.String;
import java.util.ArrayList;

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
class Grid {

	
	private Tile tileTab_[][];
	private int size_;
	private int playerScore1_;
	private int playerScore2_;
	
	/*
	* Interface "graphique"
	*/	
	private String couleurPlayer1_ = "\u001B[31m";
	private String couleurPlayer2_ ="\u001B[34m";
	private String couleurVide_ = "\u001B[0m";
	private Scanner keyboardInput_ =new Scanner(System.in);
	
	/*
	* Constructeur de Grid
	*/
	public Grid(int size){
		this.size_ = size;
		this.tileTab_ = new Tile[size_][size_];
		this.playerScore1_ = 0;
		this.playerScore2_ = 0;
	}
	
	public int getSize(){ return size_;}
	public int getPlayerScore1(){ return playerScore1_;}
	public int getPlayerScore2(){ return playerScore2_;}

	/*
	* Méthode "Classe"
	*/
	public Tile classe(Tile tile){
		Tile temp = tile;
		if (tile.getPere() != null){

			while(temp.getPere() != null){
				temp = temp.getPere();
			}
			tile.setPere(temp);
		}else{

		}
		return temp;
	}
	
	/*
	* Méthode Union
	*/
	public void union(Tile tile1, Tile tile2){
		Tile pere1 = classe(tile1);
		//System.out.println("888888888888888888"); //Debug
		Tile pere2 = classe(tile2);
		//System.out.println("11111111111111111111"); //Debug
		if(pere1.getPlayerNum() == pere2.getPlayerNum() ){
			//System.out.println("77777777777777777"); //Debug
			pere2.setPere(pere1);
			
		}
	}
	
	/*
	* Méthode pour la question 3 et méthode existeCheminCases
	* Deux cases de meme identifiant de classe ont forcement la meme couleur
	*/
	
	public boolean existeCheminCases(Tile tileStart, Tile tileEnd){
		return classe(tileStart) == classe(tileEnd);
	}	
	
	/*
	* Méthode pour la question 2
	* On affiche les cases parcourus dans les composantes	
	*/
	public void afficheComposante(Tile tile, int playerNum){
		ArrayList<Tile> tileTest = new ArrayList<Tile>();
		ArrayList<Tile> tileStock = new ArrayList<Tile>();
		boolean dejaMis = false;
		int minx = -1, miny = -1, maxx = 1, maxy = 1;
		int x = tile.getX();
		int y = tile.getY();
		if( x == 0){
			minx = 0;
		}else if(x == size_ - 1){
			maxx = 0;
		}
		if( y == 0){
			miny = 0;
		}else if(y == size_ - 1){
			maxy = 0;
		}
		for(int i = minx; i <= maxx; ++i){
			for(int j = miny; j <= maxy; ++j){
				if(i != 0 || j != 0){
					if(tileTab_[x + i][y + j].getPlayerNum() == playerNum){
						tileTest.add(tileTab_[x+i][y+j]);
					}
				}
			}
		}
		
		/* enlever les cases qui ne sont pas de celui de player actif */
		for(int i = 0; i < tileTest.size(); ++i){
			if(tileTest.get(i).getPlayerNum() != playerNum){
				tileTest.remove(i);
				System.out.println(i);
			}
		}
		
		for(int i = 0; i < tileTest.size(); ++i){
			for(int j = i+1; j < tileTest.size() && dejaMis == false; ++j){
				if(classe(tileTest.get(i)) == classe(tileTest.get(j))){
					dejaMis = true;
									
				}
			}
			if (!dejaMis) {
				tileStock.add(tileTest.get(i));
			}
			dejaMis = false;
			
		}		
		
		if(tileStock.size() >= 2){
			System.out.println("(afficheComposante) Les cases adjacentes liées sont : \n" );
			for(int i = 0; i < tileStock.size(); ++i){
				System.out.println("( " + tileStock.get(i).getX() + " ; " + tileStock.get(i).getY() + " )\n" );
			}		
			System.out.println("(afficheComposante) Fin des cases \n" );
		}else{
			System.out.println("(afficheComposante) Pas d'adjacence ! \n" );
		}
		
		
	}
	
	
	
	
	/*
	* Méthode pour la question 4 et méthode récursive relierCasesMin
	* Donner la distance en vol d'oiseau entre deux cases
	* Tile tile1 et tile2 les deux tiles à lier.
	* se facade dans la méthode pathfind
	* N'est probablement pas ce qui est demandé; ce qui nécessiera l'implémentation d'un algorithme de Dijkstra simplifié avec un compteur
	*/
	public int relierCasesMin(Tile tile1, Tile tile2){
		int res = 0;
		int x1, x2, y1, y2;
		x1 = tile1.getX();
		y1 = tile1.getY();
		
		x2 = tile2.getX();
		y2 = tile2.getY();
		return pathfind(x1, y1, x2, y2, res);
	}
	
	/*
	* Le fameux pathfind tiré d'un autre projet, qui n'est pas vraiment un pathfind car il donne la distance en vol d'oiseau
	*/
	public int pathfind(int xdeb, int ydeb, int xarr, int yarr, int res){
		int result = res;
		/*
		*	Si on compare deux cases identiques et cond d'arret
		*/
		if((xdeb == xarr)&&(ydeb == yarr)){
			return result;
		}else{
			
			if ((xarr - xdeb) < 0) { --xdeb; }			
			else if ((xarr - xdeb) > 0) { ++xdeb; }	
			
			if ((yarr - ydeb) < 0) { --ydeb; }
			else if ((yarr - ydeb) > 0) { ++ydeb; }
			
			++res;
			return pathfind(xdeb, ydeb, xarr, yarr, res);
		}
	}
	
	
	/*
	* Méthode pour la question 5 et méthode nombresEtoiles
	* On parcours récursivement les fils de l'identifiant de la classe union de tile et retourne la somme de leurs étoiles
	*/
	public int nombresEtoiles(Tile tile){
		Tile temp = classe(tile);
		int result = 0;		
		for(int i = 0; i < temp.getFils().size(); ++i){
			result += nombresEtoiles(temp.getFils().get(i));
		}
		if(temp.getIsBase()){
			++result;
		}
		//System.out.println("nbétoile " + result);				//DEBUG
		return result;
	}
	
	/*
	* Méthode pour la question 6 et méthode afficherScore
	* S'explique tout seul
	*/
	public void afficherScore(int playerNum){
		ArrayList<Tile> tileStock = new ArrayList<Tile>();
		ArrayList<Tile> tileScore = new ArrayList<Tile>();
		boolean dejaMis = false;
		int scoreCount = 0;
		
		/* enlever les cases non bases de mauvaise couleur */		
		for(int x = 0; x < size_ ; ++x){
			for(int y = 0; y < size_ ; ++y){
				if((tileTab_[x][y].getPlayerNum() == playerNum) && tileTab_[x][y].getIsBase()){
					tileStock.add(tileTab_[x][y]);
				}				
			}
		}
		
		for(int i = 0; i < tileStock.size(); ++i){
			for(int j = i+1; j < tileStock.size() && dejaMis == false; ++j){
				/*System.out.println(classe(tileStock.get(i)) +"   "+ classe(tileStock.get(j))); //Debug*/
				if(classe(tileStock.get(i)) == classe(tileStock.get(j))){
					/*System.out.println("///////"+dejaMis); ////////////////DEBUG*/
					dejaMis = true;
				}/*else{
					System.out.println("============="+dejaMis); //Debug
				}*/
			}
			if (!dejaMis) tileScore.add(tileStock.get(i));
			/*for(int k = 0; k<tileScore.size(); ++k){ //FOR DEBUG
				System.out.println("eeeeee" + tileScore.get(k).getX()); //Debug
			}*/
			dejaMis = false;
		}
		
		/* parcours de tileScore */
		for(int i = 0; i < tileScore.size(); ++i){
			//System.out.println("============="+tileScore.size()); //Debug
			//System.out.println("oooooooooooo " + scoreCount + " iiii " + nombresEtoiles(tileScore.get(i)));//debug
			scoreCount += nombresEtoiles(tileScore.get(i))-1;
		}
		
		System.out.println("(afficherScore) Le score de joueur " + playerNum + " est : " + scoreCount );
		
		if(playerNum == 1){
			playerScore1_ = scoreCount;
		}else if(playerNum == 2){
			playerScore2_ = scoreCount;
		}
		
	}
		
	/*
	* Méthode relie composantes
	*/
	public boolean relieComposantes(Tile tile, int playerNum){
		ArrayList<Tile> tileTest = new ArrayList<Tile>();
		ArrayList<Tile> tileStock = new ArrayList<Tile>();
		boolean result = false;
		boolean dejaMis = false;
		int minx = -1, miny = -1, maxx = 1, maxy = 1;
		int x = tile.getX();
		int y = tile.getY();
		if( x == 0){
			minx = 0;
		}else if(x == size_ - 1){
			maxx = 0;
		}
		if( y == 0){
			miny = 0;
		}else if(y == size_ - 1){
			maxy = 0;
		}
		for(int i = minx; i <= maxx; ++i){
			for(int j = miny; j <= maxy; ++j){
				if(i != 0 || j != 0){
					if(tileTab_[x + i][y + j].getPlayerNum() == tileTab_[x][y].getPlayerNum()){
						tileTest.add(tileTab_[x+i][y+j]);
					}
				}
			}
		}
		
		for(int i = 0; i < tileTest.size(); ++i){
			if(tileTest.get(i).getPlayerNum() != playerNum){
				tileTest.remove(i);
			}
		}
		
		for(int i = 0; i < tileTest.size(); ++i){
			for(int j = i+1; j < tileTest.size() && dejaMis == false; ++j){
				if(classe(tileTest.get(i)) == classe(tileTest.get(j))){
					dejaMis = true;
				}
			}
			if (!dejaMis) tileStock.add(tileTest.get(i));
			dejaMis = false;
		}		
		if(tileStock.size() >= 2){
			result = false;
		}else{
			result = true;
		}		
		return result;
	}
	
	/*
	* Pour la question 8, ou comment faire jouer deux humains
	*/	
	public int startGame(){
		int playerInput;
		Random randy;
		int randomX;
		int randomY;
		
		ArrayList<Integer> etoiles = new ArrayList<Integer>();
		
		tileTab_ = new Tile[size_][size_];
		for(int x = 0; x < size_; ++x){
			for(int y = 0; y < size_ ; ++y){
				tileTab_[x][y] = new Tile(x, y);
			}
		}
		
		System.out.println("Donnez le nombre de bases.");
		playerInput = keyboardInput_.nextInt();

		for(int cmptr = 0; cmptr < playerInput; ++cmptr){
			/* ajouter bases player 1 */
			randy = new Random();			
			randomX = randy.nextInt((size_ - 1 - 0) + 1) + 0;
			/*
			for(int i = 0; i<etoiles.size(); i+=2){
				if(randomX - etoiles.get(i) < 2 && randomX - etoiles.get(i) > -2 ){
					randomX = randy.nextInt((size_ - 1 - 0) + 1) + 0;
					System.out.println("for1");
				}
			}
			etoiles.add(randomX);
			*/
			randy = new Random();
			randomY = randy.nextInt((size_ - 1 - 0) + 1) + 0;			
			/*for(int i = 1; i<etoiles.size(); i+=2){
				if(randomY - etoiles.get(i) < 2 && randomY - etoiles.get(i) > -2 ){
					randomY = randy.nextInt((size_ - 1 - 0) + 1) + 0;
					System.out.println("for2");
				}
			}
			etoiles.add(randomY);
			*/
			tileTab_[randomX][randomY].colorerCase(1);
			tileTab_[randomX][randomY].setBase();
			System.out.println("Une base dans : ( " + randomX +" , "+ randomY + " )");
			
			/* ajouter bases player 2 */
			randy = new Random();
			randomX = randy.nextInt((size_ - 1 - 0) + 1) + 0;
			/*
			for(int i = 0; i<etoiles.size(); i+=2){
				if(randomX - etoiles.get(i) < 2 && randomX - etoiles.get(i) > -2 ){
					randomX = randy.nextInt((size_ - 1 - 0) + 1) + 0;
					System.out.println("for1");
				}
			}
			etoiles.add(randomX);
			*/
			randy = new Random();
			randomY = randy.nextInt((size_ - 1 - 0) + 1) + 0;		
			/*
			for(int i = 1; i<etoiles.size(); i+=2){
				if(randomY - etoiles.get(i) < 2 && randomY - etoiles.get(i) > -2 ){
					randomY = randy.nextInt((size_ - 1 - 0) + 1) + 0;
					System.out.println("for2");
				}
			}
			etoiles.add(randomY);
			*/
			tileTab_[randomX][randomY].colorerCase(2);
			tileTab_[randomX][randomY].setBase();
			System.out.println("Une base dans : ( " + randomX +" , "+ randomY + " )");
		}
		return playerInput;
	}
	
		
	/*
	* Affichage en unicode et code couleur qui marchera probablement pas en terminal
	* concatenation de String affich ligne par ligne
	*/
	public void display(int turnNum){
		String affich = new String();

		
		if(turnNum % 2 == 1){
			affich += "\n\n==========TOUR DE JOUEUR ROUGE========== \n\n";
		}else if(turnNum % 2 == 0){
			affich += "\n\n==========TOUR DE JOUEUR BLEU========== \n\n";
		}
		
		for(int i = 0; i < size_; ++i){
			if(i == 0){
				affich += "  ";
			}
			if(i<=9){
				affich += " "+i+" ";
			}else{
				affich += " "+i;
			}	
		}
		
		affich += "\n";

		
		for(int y = 0; y < size_; ++y){
			if(y<=9){
				affich += y+" ";
			}else{
				affich += y;
			}
			for(int x = 0; x< size_; ++x){
				if(tileTab_[x][y].getIsBase() == false){
					if(tileTab_[x][y].getPlayerNum() != 1 && tileTab_[x][y].getPlayerNum() != 2){ affich += " ⬜ "; }
					if(tileTab_[x][y].getPlayerNum() == 1 ){ affich += couleurPlayer1_ + " ⬜ " + couleurVide_; }
					if(tileTab_[x][y].getPlayerNum() == 2 ){ affich += couleurPlayer2_ + " ⬜ " + couleurVide_; }
				}else{
					if(tileTab_[x][y].getPlayerNum() != 1 && tileTab_[x][y].getPlayerNum() != 2){ affich += " * "; }
					if(tileTab_[x][y].getPlayerNum() == 1 ){ affich += couleurPlayer1_ + " * " + couleurVide_; }
					if(tileTab_[x][y].getPlayerNum() == 2 ){ affich += couleurPlayer2_ + " * " + couleurVide_; }
				}
			}
			affich += "\n";
		}
		
		System.out.println(affich);
	}
	
	
	/*
	* Méthode 8: joueDeuxHumains
	*/	
	public void joueDeuxHumains(){
		int bases;
		bases = startGame();
		
		boolean gameOngoing = false;
		boolean action = false;
		boolean checker;
		int playerNum = 0;
		int x, y, choix;
		int x1,x2,y1,y2;
		int turnNum = 0;
		Tile tileTest;
		int starNumTile;
		int starNumVoisin;
		playerScore1_ = 0;
		playerScore2_ = 0;
		
		while(!gameOngoing){
			++turnNum;
			/* choix des joueurs */
			if(turnNum % 2 == 1){playerNum = 1;}
			else{playerNum = 2;}
			
			/* affichage ecran */
			display(turnNum);
			afficherScore(playerNum);
			
			System.out.println("Choisissez :");
			System.out.println("1 pour Menu");
			System.out.println("2 pour jouer :");
			choix = keyboardInput_.nextInt();
			if(choix == 1){
				System.out.println("===============MENU fonctions choisissez quoi choisir ============= \n");
				System.out.println("1 : AfficheComposante : \n");
				System.out.println("2 : ExisteCheminCases : \n");
				System.out.println("3 : RelierCasesMin : \n");
				System.out.println("4 : nombresEtoiles : \n");
				System.out.println("5 : RelieComposantes : \n");
				System.out.println("============================================================= \n");
				
				System.out.println("Donnez votre choix");
				choix = keyboardInput_.nextInt();
				
				switch(choix) {
        			case 1 :
        				System.out.println("Donnez x");
						x = keyboardInput_.nextInt();
						System.out.println("Donnez y");
						y = keyboardInput_.nextInt();
            			afficheComposante(tileTab_[x][y], playerNum);
            		break;
         			case 2 :
         				System.out.println("Donnez x de la 1ere case :");
						x1 = keyboardInput_.nextInt();
						System.out.println("Donnez y de la 1ere case :");
						y1 = keyboardInput_.nextInt();
            			System.out.println("Donnez x de la 2eme case :");
						x2 = keyboardInput_.nextInt();
						System.out.println("Donnez y de la 2eme case :");
						y2 = keyboardInput_.nextInt();
            			if(existeCheminCases(tileTab_[x1][y1], tileTab_[x2][y2])){
            				System.out.println("Un chemin existe.");
            			}else{
            				System.out.println("Pas de chemin.");
            			}
            		break;
        			case 3 :        			
        				System.out.println("Donnez x de la 1ere case :");
						x1 = keyboardInput_.nextInt();
						System.out.println("Donnez y de la 1ere case :");
						y1 = keyboardInput_.nextInt();
            			System.out.println("Donnez x de la 2eme case :");
						x2 = keyboardInput_.nextInt();
						System.out.println("Donnez y de la 2eme case :");
						y2 = keyboardInput_.nextInt();
            			System.out.println("Il y a " + relierCasesMin(tileTab_[x1][y1], tileTab_[x2][y2]) + " cases à parcourir.");
            		break;
         			case 4 :
         				System.out.println("Donnez x");
						x = keyboardInput_.nextInt();
						System.out.println("Donnez y");
						y = keyboardInput_.nextInt();
						System.out.println("Il y a " + nombresEtoiles(tileTab_[x][y]) +" etoiles içi.");            			
            		break;
         			case 5 :
         				System.out.println("Donnez x");
						x = keyboardInput_.nextInt();
						System.out.println("Donnez y");
						y = keyboardInput_.nextInt();
         				if(relieComposantes(tileTab_[x][y], playerNum)){
         					System.out.println("C'est lié");
         				}else{
         					System.out.println("Ce n'est pas lié");
         				}
         				
            		break;
         		default :
            		System.out.println("Choix invalide.");
      }
			}
			
			/******* Selection Menu si x < 0 *******/

			
			/* input du joueur */
			System.out.println("Donnez x");
			x = keyboardInput_.nextInt();
			System.out.println("Donnez y");
			y = keyboardInput_.nextInt();
			action = tileTab_[x][y].colorerCase(playerNum);
			
			int minx = -1, miny = -1, maxx = 1, maxy = 1;
			
			
	/*		for(int xFor = 0; xFor< size_ ; ++xFor){
				for (int yFor = 0; yFor<size_ ; ++yFor){
					minx = -1;
					miny = -1;
					maxx = 1;
					maxy = 1;
					*/
					int xFor = x, yFor = y;
					if( xFor == 0){
						minx = 0;
					}else if(xFor == size_ - 1){
						maxx = 0;
					}
					if( yFor == 0){
						miny = 0;
					}else if(yFor == size_ - 1){
						maxy = 0;
					}
					//System.out.println("ENTREE EN BOUCLE INFERNALE");//Debug
					for(int i = minx; i <= maxx; ++i){
						for(int j = miny; j <= maxy; ++j){
						//System.out.println(xFor +" " + yFor+ " " + i + " " + j); //Debug
							if(i != 0 || j != 0){
								if(tileTab_[xFor + i][yFor + j].getPlayerNum() == playerNum){
									union(tileTab_[x][y], tileTab_[xFor+i][yFor+j]);
			//						System.out.println("555555555555555555"); //Debug
								}
			//				System.out.println("55511111111555555555"); //Debug
							}
			//			System.out.println("5555522222222255555"); //Debug
						}
			//		System.out.println("555555333333335555555"); //Debug
					}
			//	System.out.println("55555554444444444555555"); //Debug
/*				}
			System.out.println("55555556666666666666555"); //Debug
			} */
			//System.out.println("66666666666666666"); //Debug
			if(playerScore1_ == bases){
				System.out.println("Victoire player 1");
				gameOngoing = true;
			}else if(playerScore2_ == bases){
				System.out.println("Victoire player 2");
				gameOngoing = true;
			}			
		}
	}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
