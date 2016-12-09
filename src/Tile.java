class Tile{
	private int x_;
	private int y_;
	private int playerNum_;
	//private int nbBases_;
	private boolean isBase_;
	private ArrayList<Tile> fils_; 
	private Tile pere_;

	
	public Tile(int x, int y/*, int nbBases*/){
		x_ = x;
		y_ = y;
		playerNum_ = 0;
		//nbBases_ = nbBases;	
		isBase_ = false; 
		pere_ = null;
		fils_ = new ArrayList<Tile>;
			
	}
	
	public int getX(){return x_;}	
	public int getY(){return y_;}
	public Tile getPere(){return pere_;}
	public ArrayList<Tile> getFils(){return fils_;}
	//public int getNbEtoiles(){return nbBases_;}
	public int getPlayerNum(){return playerNum_;}
	public boolean getIsBase(){return isBase_;}
	
	public void setX(int x){this.x_ = x;}
	public void setY(int y){this.y_ = y;}
	//public void setStar(int nbBases){ nbBases_ = nbBases;}
	public void setPere(Tile tile){pere_ = tile;}
	public void ajouterFils(Tile tile){fils_.add(tile);}
	public void setBase(){isBase_ = true;}
	public void unsetBase(){isBase_ = false;}
	
	public boolean colorerCase(int playerNum){
		boolean result;
		if(playerNum_ != 1 && playerNum_ != 2){
			playerNum_ = playerNum;
			result = true;	
		}else{
			result = false;
		}
		return result;
	} 
}
