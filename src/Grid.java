import java.util.Random;
import java.util.Scanner;
import java.lang.String;

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
	private String couleurvide_ = "\u001B[0m";
	private Scanner keyboardInput_ =new Scanner(System.in);
	
	/*
	* Constructeur de Grid
	*/
	public Grid(int size){
		this.size_ = size;
		this.playerScore1_ = 0;
		this.playerScore2_ = 0;
	}
	
	public int getSize(){ return size_;}
	public int getPlayerScore1(){ return playerScore1_;}
	public int getPlayerScore2(){ return playerScore2_;}


	/*
	* méthode pour unir deux cases et donner le nb d'étoiles (s'il y a) dans le Tile(x2 y2) dans le Tile pere (x1, y1)
	*/
	public void unionFind(int x1, int y1, int x2, int y2){
		Tile pere = tileTab_[x1][y1];
		Tile fils = tileTab_[x2][y2];
		
		/*
		* Si fils n'est pas encore dans pere, il va etre dans pere
		*/
		if(fils.equals(pere) == false){
			int starSum = pere.getStarNb() + fils.getStarNb();
			pere.setStar(starSum);
		}
	}
	
	/*
	* Compression du fil où se trouve Tile(x,y)
	*/
	public void compress(int x, int y){
		
		/* Condition d'arret de recursion */
		if(tileTab_[x][y].getX() == x || tileTab_[x][y].getY() == y){
			System.out.println("( " + x + "," + y + " )");
		}else{
			tileTab_[x][y] = tileTab_[tileTab_[x][y].getX()][tileTab_[x][y].getY()];
			compress(tileTab_[x][y].getX(), tileTab_[x][y].getY());
			System.out.println("( " +x + "," + y + " )");
		}
	}
	
	/*
	* Méthode pour la question 3 et méthode existeCheminCases
	*/
	
	public void existeCheminCases(Tile tileStart, Tile tileEnd, int playerNum){
		boolean memeUnionFind;
		boolean memePlayer;
		/* compression des deux Tiles */
		compress(tileStart.getX(), tileStart.getY());
		compress(tileEnd.getX(), tileEnd.getY());
		
		memeUnionFind = tileTab_[tileStart.getX()][tileStart.getY()] == tileTab_[tileEnd.getX()][tileEnd.getY()];
		memePlayer = tileStart.getPlayerNum() == tileEnd.getPlayerNum();
		
		return memeUnionFind && memePlayer;	
	}	
	
	/*
	* Méthode pour la question 2 et méthode récursive afficheComposante
	* On a un peu triché en utilisant existeCheminCases et en prenant deux Tiles en parametre pour pouvoir le faire en récursif
	* On affiche les cases parcourus dans les composantes	
	*/
	public void afficheComposante(Tile tile1, Tile tile2, int playerNum){
		if(getSize() - 1 > tile2.getX() || getSize() - 1 > tile2.getY()){ /* pour ne pas sortir du Grid */
			if(existeCheminCases(tile1, tile2, playerNum)){
				System.out.println("( " +tile2.getX() + "," + tile2.getY() + " )"); /* affichage de la case qui fait "avancer" le parcours*/ 
			}
			tile2.setX(tile2.getX() + 1);
			afficheComposante(tile1, tile2, playerNum);
			tile2.setY(tile2.getY() + 1);
			afficheComposante(tile1, tile2, playerNum);
		}
	}
	
	
	/*
	* Méthode pour la question 4 et méthode récursive relierCasesMin
	* Donner la distance en vol d'oiseau entre deux cases
	* Tile tile1 et tile2 les deux tiles à lier.
	* se facade dans la méthode pathfind
	* N'est probablement pas ce qui est demandé, ce qui nécessiera l'implémentation d'un algorithme de Dijkstra simplifié avec un compteur
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
			/*
			*	Si tileDeb se trouve au SW de tileArr
			*/
			if(((xarr - xdeb)>0) && ((yarr - ydeb)>0)){
				++xdeb;
				++ydeb;
				
				
			/*
			*	Si tileDeb se trouve au NE de tileArr
			*/
			}else if (((xarr - xdeb)<0) && ((yarr - ydeb)<0)){
				--xdeb;
				--ydeb;
					
				
			/*
			*	Si tileDeb se trouve au SE de tileArr
			*/
			}else if(((xarr - xdeb)<0) && ((yarr - ydeb)>0)){
				--xdeb;
				++ydeb;
				
				
			/*
			*	Si tileDeb se trouve au NW de tileArr
			*/
			}else if (((xarr - xdeb)>0) && ((yarr - ydeb)<0)){
				++xdeb;
				--ydeb;
					

			/*
			*	Si tileDeb se trouve au Nord de tileArr
			*/				
			}else if (((xarr - xdeb) == 0) && ((yarr - ydeb)<0)){
				--ydeb;
					
			/*
			*	Si tileDeb se trouve au Sud de tileArr
			*/					
			}else if (((xarr - xdeb) == 0) && ((yarr - ydeb)>0)){
				++ydeb;
				
				
			/*
			*	Si tileDeb se trouve a l'Ouest de tileArr
			*/	
			}else if (((xarr - xdeb)>0) && ((yarr - ydeb)==0)){
				++xdeb;
				
				
			/*
			*	Si tileDeb se trouve a l'Est de tileArr
			*/		
			}else if (((xarr - xdeb)<0) && ((yarr - ydeb)==0)){
				--xdeb;
				
			}

			++res;
			return pathfind(xdeb, ydeb, xarr, yarr, res);
		}
	}
	
	
	/*
	* Méthode pour la question 5 et méthode nombreEtoiles
	* On regarde combiend d'étoiles à cette Tile
	*/
	public void nombresEtoiles(Tile tile){
		int playerNum = tile1.getPlayerNum();
		int x = tile1.getX();
		int y = tile1.getY();
		int nbEtoiles = tileTab_[x][y].getNbEtoiles();
		
		System.out.println("Le nombre d'étoiles est " + nbEtoiles);
	}
	
	/*
	* Méthode pour la question 6 et méthode afficherScore
	* S'explique tout seul
	*/
	public void afficherScore(int playerNum){
		if(playerNum != 1 || playerNum != 2){
			System.out.println("Hé mais t'es pas dans le jeu!");
		}		
		if(playerNum == 1){
			System.out.println("Score player 1 : " playerScore1_);
		}else if (playerNum == 2){
			System.out.println("Score player 2 : " playerScore2_);
		}
	}

	/*
	* Méthode pour la question 7 et méthode relieComposantes
	* Assez énorme comme on check tous les cases autour
	* Aurait du trouver quelque chose de reccursif
	* Ou au moins utiliser des switch
	*/
	
	public boolean relieComposantes(Tile tile1, int playerNum){
		Tile tileTest;
		int x = tile1.getX();
		int y = tile1.getY();
		
		
		/* on y va... */
		if (x == 0 && y == 0){
			if (tileTab_[x+1][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x+1][y+1];}
			else if (tileTab_[x][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x][y+1];}
			else if (tileTab_[x+1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x+1][y];}
			else{return tileTab_[x][y];}
		 }
		 else if (y == 0 && x == longueur_-1){
			if (tileTab_[x][y].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x][y];}
			else if (tileTab_[x-1][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x-1][y+1];}
			else if (tileTab_[x-1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x-1][y];}
			else if (tileTab_[x][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x][y+1];}
			else{return tileTab_[x][y];}
		}
		else if (y == 0){
			if (tileTab_[x+1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x+1][y];}
			else if (tileTab_[x-1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x-1][y];}
			else if  (tileTab_[x][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x][y+1];}
			else if (tileTab_[x+1][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x+1][y+1];}
			else if (tileTab_[x-1][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum()){return tileTab_[x-1][y+1];}
			else{return tileTab_[x][y];}
		}
		else if (x == 0 && y == longueur_-1)
		{
			if (tileTab_[x+1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y]));
		 		return tileTab_[x+1][y];
		 	}
		 	else if (tileTab_[x][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x][y-1]));
		 		return tileTab_[x][y-1];
		 	}
		 	else if (tileTab_[x+1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y-1]));
		 		return tileTab_[x+1][y-1];
		 	}
		 	else if (tileTab_[x+1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y-1]));
		 		return tileTab_[x+1][y-1];
		 	}
		 	else
		 	{
		 		return tileTab_[x][y];
		 	}
		}
		else if (x == 0)
		{
			if (tileTab_[x+1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y]));
		 		return tileTab_[x+1][y];
		 	}
		 	else if (tileTab_[x][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x][y-1]));
		 		return tileTab_[x][y-1];
		 	}
		 	else if (tileTab_[x][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x][y+1]));
		 		return tileTab_[x][y+1];
		 	}
		 	else if (tileTab_[x+1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y-1]));
		 		return tileTab_[x+1][y-1];
		 	}
		 	else if (tileTab_[x+1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y-1]));
		 		return tileTab_[x+1][y-1];
		 	}
		 	else if (tileTab_[x+1][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y+1]));
		 		return tileTab_[x+1][y+1];
		 	}
		 	else
		 	{
		 		return tileTab_[x][y];
		 	}
		}
		if (x == longueur_-1 && y == longueur_-1)
		{
			if (tileTab_[x-1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x+1][y]));
				return tileTab_[x-1][y];
			}
			else if (tileTab_[x][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x][y+1]));
				return tileTab_[x][y-1];
			}
			else if (tileTab_[x-1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x+1][y+1]));
				return tileTab_[x-1][y-1];
			}
			else
			{
				return tileTab_[x][y];
			}
		 }
		else if (x == longueur_-1)
		{
			if (tileTab_[x-1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y]));
		 		return tileTab_[x-1][y];
		 	}
		 	else if (tileTab_[x][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x][y-1]));
		 		return tileTab_[x][y-1];
		 	}
		 	else if (tileTab_[x][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x][y+1]));
		 		return tileTab_[x][y+1];
		 	}
		 	else if (tileTab_[x-1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y-1]));
		 		return tileTab_[x-1][y-1];
		 	}
		 	else if (tileTab_[x-1][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y+1]));
		 		return tileTab_[x-1][y+1];
		 	}

		 	else
		 	{
		 		return tileTab_[x][y];
		 	}
		}
		else if (y == longueur_-1)
		{
			if (tileTab_[x+1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x+1][y]));
				return tileTab_[x+1][y];
			}
			else if (tileTab_[x-1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x-1][y]));
				return tileTab_[x-1][y];
			}
			else if  (tileTab_[x][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x][y+1]));
				return tileTab_[x][y-1];
			}
			else if (tileTab_[x+1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x+1][y+1]));
				return tileTab_[x+1][y-1];
			}
			else if (tileTab_[x-1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x-1][y+1]));
				return tileTab_[x-1][y-1];
			}
			else
			{
				return tileTab_[x][y];
			}
		}
		else
		{
			if (tileTab_[x-1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x-1][y]));
				return tileTab_[x-1][y];
			}
			else if (tileTab_[x+1][y].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x+1][y]));
				return tileTab_[x+1][y];
			}
			else if (tileTab_[x][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
			{
				//System.out.println(xandy(tileTab_[x][y+1]));
				return tileTab_[x][y+1];
			}
		 	else if (tileTab_[x][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x][y-1]));
		 		return tileTab_[x][y-1];
		 	}
		 	else if (tileTab_[x-1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x-1][y-1]));
		 		return tileTab_[x-1][y-1];
		 	}
		 	else if (tileTab_[x+1][y-1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y-1]));
		 		return tileTab_[x+1][y-1];
		 	}
		 	else if (tileTab_[x-1][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x-1][y+1]));
		 		return tileTab_[x-1][y+1];
		 	}
		 	else if (tileTab_[x+1][y+1].getPlayerNum() == tileTab_[x][y].getPlayerNum())
		 	{
		 		//System.out.println(xandy(tileTab_[x+1][y+1]));
		 		return tileTab_[x+1][y+1];
		 	}
		 	else
		 	{
		 		return tileTab_[x][y];
		 	}
		 }
	
	
	}
	
	
		
	
	
	
	
