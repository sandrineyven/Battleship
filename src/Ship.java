public abstract class Ship {
	//id correcpondant au type du bateau
	int type;
	//nom du bateau
	String name;
	//Longueur du bateau
	int longueur;
	//longueur du champ de tir
		int champdetir;
	//Nombre de points de vie (2 pour chaques bateaux)
	int pointsdevie =2;
	//Position sur la grille de l'avant du bateau
	int positionX;
	int positionY;
	//sens du bateau (0=horizontal et 1=vertical)
	int sens;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLongueur() {
		return longueur;
	}
	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}
	public int getChampdetir() {
		return champdetir;
	}
	public void setChampdetir(int champdetir) {
		this.champdetir = champdetir;
	}	
	public int getPointsdevie() {
		return pointsdevie;
	}
	public void setPointsdevie(int pointsdevie) {
		this.pointsdevie = pointsdevie;
	}
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	public int getSens() {
		return sens;
	}
	public void setSens(int sens) {
		this.sens = sens;
	}
	
	
	public boolean chekShoot(Grille grille,int posTX, int posTY)
	{ 
		boolean aRetourner=false;
		for(int i=0; i<champdetir;i++)
		{
			//Bateau horizontal
			if(sens==0)
			{
				//tir vers la gauche
				if(positionX-i-1==posTX&&positionY==posTY)
					aRetourner=true;
				//tir vers la droite
				if(positionX+longueur+i+1==posTX&&positionY==posTY)
					aRetourner=true;
				//tir vers haut et bas
				for(int j= 0; j<longueur;j++)
				{
					//tir bas
					if(positionX+j==posTX&&positionY+i+1==posTY)
						aRetourner=true;
					//tir haut
					if(positionX+j==posTX&&positionY-i-1==posTY)
						aRetourner=true;
				}
			}
			//Bateau vertical
			else 
			{
				//tir vers le haut
				if(positionX==posTX&&positionY-i-1==posTY)
					aRetourner=true;
				//tir vers le bas
				if(positionX==posTX&&positionY+longueur+i+1==posTY)
					aRetourner=true;
				//tir sur les cotes
				for(int j= 0; j<longueur;j++)
				{
					//tir cote droit 
					if(positionX+i+1==posTX&&positionY+j==posTY)
						aRetourner=true;
					//tir cote gauche
					if(positionX-i-1==posTX&&positionY+j==posTY)
						aRetourner=true;
				}
				
			}
				
		}
		return aRetourner;
	}
}