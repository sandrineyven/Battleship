public class SousMarin extends Ship {
	public SousMarin(int positionX, int positionY, int sens, int joueur){
		this.type = 4;
		this.name = (joueur == 1) ? "Sous Marin J1" : "Sous Marin J2";
		this.champdetir = 4;
		this.longueur = 3;
		this.positionX = positionX;
		this.positionY = positionY;
		this.sens = sens;
	}
}