/**
 * @file Grid.java
 * @copyright WTFPL v2
 * @author Félix PINEL Elbert Noel 	NYUNTING
 * @date 14:09
 * @brief Fichier source réalisant le plateau de jeu
*/



class Grid{
	private int taille_;
	private Tile tabTiles_[];
	private Noeud tabNoeud_[];
	private Player player1_;
	private Player player2_
		
	/**
	 * @brief constructeur de Grid
	 * @param entier taille_ longueur de Grid
	 * @pre taille > 0
	 * @details fais un Grid carrée de taile * taille cases
	*/
	public Grid(int taille, Player player1, Player player2){
		taille_ = taille;
		player1_ = player1;
		player2_ = player2;
	}
	
	public int getTaille(){
		return taille_;
	}
	
	public Player getPlayer1(){
		return player1_;
	}
	
	public Player getPlayer2(){
		return player2_;
	}
	
	public void setTaille(int taille){
		taille_ = taille;
	}
	
	public void setPlayer1(Player player1){
		player1_ = player1;
	}
	
	public void setPlayer2(Player player2){
		player2_ = player2;
	}
	
	
	
	
	
	
	
