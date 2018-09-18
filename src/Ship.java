
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
	
}
