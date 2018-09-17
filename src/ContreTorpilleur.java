
public class ContreTorpilleur extends Ship{
	public ContreTorpilleur(int positionX, int positionY, int sens, int joueur){
		this.name = (joueur == 1) ? "Contre Torpilleur J1" : "Contre Torpilleur J2";
		this.champdetir = 2;
		this.longueur = 3;
		this.positionX = positionX;
		this.positionY = positionY;
		this.sens = sens;
	}
}
