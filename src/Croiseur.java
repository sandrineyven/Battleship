
public class Croiseur extends Ship {
	public Croiseur(int positionX, int positionY, int sens, int joueur){
		this.type = 2;
		this.name = (joueur == 1) ? "Croiseur J1" : "Croiseur J2";
		this.champdetir = 2;
		this.longueur = 4;
		this.positionX = positionX;
		this.positionY = positionY;
		this.sens = sens;
	}
}
