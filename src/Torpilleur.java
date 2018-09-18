
public class Torpilleur extends Ship{
	public Torpilleur(int positionX, int positionY, int sens, int joueur){
		this.type = 5;
		this.name = (joueur == 1) ? "Torpilleur J1" : "Torpilleur J2";
		this.champdetir = 5;
		this.longueur = 2;
		this.positionX = positionX;
		this.positionY = positionY;
		this.sens = sens;
	}
}
