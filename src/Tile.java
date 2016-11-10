/**
 * @file Tile.java
 * @copyright WTFPL v2
 * @author Félix PINEL Elbert Noel 	NYUNTING
 * @date 14:09
 * @brief Fichier source réalisant les cases du plateau de jeu
*/

import java.awt.Color;

class Tile{
	private int x_;
	private int y_;
	private Color coul_;
	private boolean etoile_;


	/**
	 * @brief constructeur de Tile
	 * @param entier x abscisse
	 * @param entier y ordonnee
	 * @pre x, y > 0
	 * @details couleur gris et case libre, donc pas pris par un joueur
	*/
	public Tile(int x, int y){
		x_ = x;
		y_ = y;
		coul_ = Color.gray;
		etoile_ = false;
	}
	
	
	public int getX(){
		return x_;
	}
	
	public int getY(){
		return y_;
	}
	
	public Color getCoul(){
		return coul_;
	}
	
	public boolean isLibre(){
		return etoile_;
	}
	
	public void setX(int x){
		x_ = x;
	}
	
	public void setY(int y){
		y_ = y;
	}
	
	public void setColor(Color coul){
		if (coul_ == Color.gray){
			coul_ = coul;
		}else{
			System.out.println("Cette case est prise !");
		}
	}
	
	public void setLibre(){
		etoile_ = true;
	}
	
	public void unsetLibre(){
		etoile_ = false;
	}
	
	public void decX(){
		--x_;
	}
	
	public void incX(){
		++x_;
	}
	
	public void decY(){
		--y_;
	}
	
	public void incY(){
		++y_;
	}
	
	
}
