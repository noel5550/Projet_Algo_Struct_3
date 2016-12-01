import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.lang.Exception;

/**
* <h1>Tile</h1>
* Le programme Tile implémente les cases (nomé içi Tile pour éviter les confusions avec le mot anglais case) 
* Elle traite aussi la création graphique de la case
* <p>
* Comme attribut elle a un int tilePlayer_ qui représent le numéro de joueur de la case (ou 0 si elle n'appartient pas à un joueur)  
* et un booléen star_ pour vois si la case contient une étoile ou non
*
* @file Tile.java
* @copyright WTFPL v2
* @author Elbert NYUNTING, Félix PINEL
*/

class Tile extends JPanel {

	private int tilePlayer_;
	private boolean star_;



	/**
	* @brief Constructeur de Tile
	* @param int tilePlayer qui est le numéro du joueur de la case dans le grid
	* @pre actingPlayer doit être un joueur de l'équipe jouant en jeu, ou 0 si c'est une case vide, donc actingPlayer appartient à {0,1,2}
	* @see La couleur de la case est indiquée par rapport au numéro du joueur 
	*/ 
	public Tile(int tilePlayer) throws Exception{
		if(tilePlayer>2 && tilePlayer<0){
			throw new Exception("Numéro joueur invalide ! ");
		}
		
		this.tilePlayer_ = tilePlayer;
		this.star_ = false;

		if (tilePlayer == 1){
			setBackground(Color.blue);
			setPreferredSize(new Dimension(25,25));
		}else if(tilePlayer == 2){
			setBackground(Color.red);
			setPreferredSize(new Dimension(25,25));
		}else{
			setBackground(Color.white);
			setPreferredSize(new Dimension(25,25));
		}
	}


	/**
	* @brief Getter pour tilePlayer_
	* @post retourne l'entier tilePlayer_. 
	*/ 
	public int getTilePlayer(){
		return this.tilePlayer_;
	}

	/**
	* @brief Getter pour star_
	* @post retourne le booléen star_. 
	*/ 
	public boolean isStar(){
		return this.star_;
	}	
	
	/**
	* @brief setter pour le joueur occupant le Tile, méthode de coloriage
	* @pre tilePlayer == 1 ou tilePlayer == 2 
	*/ 
	public void setTilePlayer(int tilePlayer) throws Exception{
		if(tilePlayer != 2 || tilePlayer != 1){
			throw new Exception("Numéro joueur invalide ! ");
		}
		this.tilePlayer_ = tilePlayer;
		
		if (tilePlayer == 1){
			setBackground(Color.blue);
			//setPreferredSize(new Dimension(25,25));
		}else if(tilePlayer == 2){
			setBackground(Color.red);
			//setPreferredSize(new Dimension(25,25));
		}		
	}
	
	/**
	* @brief unsetter pour remettre la case en blanc
	*/ 
	public void unsetTile(){
		this.tilePlayer_ = 0;
		setBackground(Color.white);
	}
	
	/**
	* @brief setter pour mettre une étoile dans un Tile, et sa représentation graphique
	*/ 
	public void setStar(){
		this.star_ = true;
		JLabel symbol = new JLabel("*");
		Font font = new Font("Serif", Font.BOLD, 18);
		symbol.setFont(font);
		symbol.setForeground(Color.BLACK);
		add(symbol);
	}	

	/**
	* @brief unsetter pour enlever une étoile dans un Tile
	*/ 
	public void unsetStar(){
		this.star_ = false;
		JLabel symbol = new JLabel("");
		Font font = new Font("Serif", Font.BOLD, 18);
		symbol.setFont(font);
		symbol.setForeground(Color.BLACK);
		add(symbol);
	}


	/*//!\ Méthode utile à afficheComposante
	public void colorerTemp(){

		if (getBackground() != Color.green)
			setBackground(Color.green);
		else if(case_ == 1)
			setBackground(Color.blue);
		else if(case_ == 2)
			setBackground(Color.red);
		else
			setBackground(Color.white);
	}
*/

	//!\ Méthode utile à l'initialisation de la grille de jeu
	/*public boolean setBase(int val){

		if (case_ == 0 && (val ==1 || val == 2)){
			case_ = val;
			etoile_ = true;

			if (val == 1) 
				setBackground(Color.blue);
			else
				setBackground(Color.red);

			// Interface graphique de la case transformé en base
			JLabel texte = new JLabel("*");
			Font font = new Font("Serif", Font.BOLD, 20);
			texte.setFont(font);
			texte.setForeground(Color.BLACK);
			add(texte);

			return true;
		}
		return false;
	}*/
}
