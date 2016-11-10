/**
 * @file Player.java
 * @copyright WTFPL v2
 * @author Félix PINEL Elbert Noel 	NYUNTING
 * @date 14:09
 * @brief Fichier source réalisant les joueurs
*/

import java.awt.Color;

class Player{

	private String nom_;
	private int nbCases_;
	private boolean premierjoueur_;
	//private ArrayList<UnionFindTile> classUnion_;
	private Color coulPlayer_;
	
	
	/**
	 * @brief constructeur de Player
	 * @param String nom, nom de Joueur
	 * @param Color coulPlayer couleur des joueurs
	 * @pre coulPlayer soit green, soit red
	*/	
	public Player(String nom, Color coulPlayer){
		nom_ = nom;
		nbCases_ = 0;
		coulPlayer_ = coulPlayer;
		//classUnion_ = new ArrayList<UnionFindTile>;
	}
	
	public String getNom(){
		return nom_;
	}

	public int getNbCases(){
		return nbCases_;
	}
	
	public boolean isPremierJoueur(){
		return premierjoueur_;
	}
	
	/*public ArrayList<UnionFindTile> getclassUnion(){
		return classUnion_;
	}*/
	
	public String getCoulPlayer(){
		return coulPlayer_;
	}
	
	public void setNom(String nom){
		nom_ = nom;
	}		

	public void setNbCases(int nbCases){
		nbCases_ = nbCases;
	}

	public void setPremierJoueur(){
		premierjoueur_ = true;
	}
	
	public void unsetPremierJoueur(){
		premierjoueur_ = false;
	}
	
	public void setCoul(Color coul){
		coul_ = coul;
	}
	
	/*public void addUnionFind(UnionFindTile addUnion){
		classUnion_.add(addUnion);
	}*/
	
	public void colorTile(){
		
	}
		
	
	
	
	
}
