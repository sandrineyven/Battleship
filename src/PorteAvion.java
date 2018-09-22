
public class PorteAvion extends Ship {
	public PorteAvion(int positionX, int positionY, int sens, int joueur){
		this.type = 1;
		this.name = (joueur == 1) ? "Porte-avion J1" : "Porte-avion J2";
		this.champdetir = 2;
		this.longueur = 5;
		this.positionX = positionX;
		this.positionY = positionY;
		this.sens = sens;
	}
	
	
}
