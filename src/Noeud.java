/**
 * @file Noeud.java
 * @copyright WTFPL v2
 * @author Félix PINEL Elbert Noel 	NYUNTING
 * @date 14:09
 * @brief Fichier source réalisant les noeuds de Tiles
*/


class Noeud{
	private Noeud pere_;
	private Tile tile_;
	private Player owner_;
	
	public Noeud(Noeud pere, Tile tile){
		pere_ = pere;
		tile_ = tile;
	}
	
	public Noeud(Tile tile){
		pere_ = null; //case racine étoile
		tile_ = tile;
	}
	
	public Noeud getPere(){
		return pere_;
	}
	
	public Tile getTile(){
		return tile_;
	}
	
	public void setPere(Noeud pere){
		pere_ = pere;
	}
}
