class Tile{
	private int x_;
	private int y_;
	private int playerNum_;
	private int nbBases_;
	
	public Tile(int x, int y, int nbBases){
		x_ = x;
		y_ = y;
		playerNum_ = 0;
		nbBases_ = nbBases;		
	}
	
	public int getX(){return x_;}	
	public int getY(){return y_;}
	public int getNbEtoiles(){return nbBases_;}
	public int getPlayerNum(){return playerNum_;}
	
	public void setX(int x){this.x_ = x;}
	public void setY(int y){this.y_ = y;}
	public void setStar(int nbBases){ nbBases_ = nbBases;}
	
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
