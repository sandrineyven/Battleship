
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;




public class ClasseMain {
	public static void main(String[] args){ 
	    //données, variables, différents traitements…
		//initialisation
		Scanner scanner = new Scanner (System.in);
		JFrame frame = new JFrame("FrameDemo");
		
		//Creation des grilles
		int size = 9;
		Grille grilleJ1 = new Grille(size);
		Grille grilleJ2 = new Grille(size);
		
		//List qui va contenir tout les bateaux du jeux
		List<Ship> shiplist = new ArrayList();
		
		//init pour le joueur 1
		System.out.println("Joueur 1:");
		int joueur = 1;
		initPlayer(scanner,frame,joueur,grilleJ1);
		grilleJ1.showShipsAlive();
		
		//init pour le joueur 2
		System.out.println("Joueur 2:");
		joueur = 2;
		initPlayer(scanner,frame,joueur,grilleJ2);
		grilleJ2.showShipsAlive();
		
		//Boucle de jeu
		//while(1)
		joueur = 1;
		System.out.println("Joueur 1:");
		
	}  
	
	
	//Permet de vérifier que la position soit inclue dans la grille
	public static boolean checkOutOfGrid(JFrame frame,int position, Grille grille){
		if(position < 1 || position > grille.getSize()){
			JOptionPane.showMessageDialog(frame,"Entre un nombre entre 1 et "+grille.getSize());
			return false;
		}else{
			return true;
		}
	}	
	
	//Permet de vérifier les collisions
	public static boolean detectCollision(JFrame frame,Ship ship, Grille grille){
		//Case déjà occupée
		if(!grille.cellIsEmpty(ship.getPositionY(),ship.getPositionX())){
			JOptionPane.showMessageDialog(frame,"Cette place est occupée");
			return true;
		}
		//Pour le sens horizontal
		if(ship.getSens() == 0){
			//Verification que le bateau ne dépasse pas
			if(ship.getPositionX() + ship.getLongueur() - grille.getSize() > 0){
				JOptionPane.showMessageDialog(frame,"Le bateau est en dehors de la grille");
				return true;
			}
			//Vérification qu'il n'y a rien aux places prévues par le bateau
			for(int i=ship.getPositionX()+1;i<ship.getLongueur();i++){
				if(!grille.cellIsEmpty(i, ship.getPositionX())){
					JOptionPane.showMessageDialog(frame,"Cette place est occupée");
					return true;
				}
			}
			//Meme chose pour le sens vertical
		}else if(ship.getSens() == 1){
			if(ship.getPositionY() + ship.getLongueur()-1 > grille.getSize()){
				JOptionPane.showMessageDialog(frame,"Le bateau est en dehors de la grille");
				return true;
			}
			for(int i=ship.getPositionX()+1;i<ship.getLongueur();i++){
				if(!grille.cellIsEmpty(i, ship.getPositionX())){
					JOptionPane.showMessageDialog(frame,"Cette place est occupée");
					return true;
				}
			}
		}
		return false;
	}
	
	//Permet de vérifié le sens
	//Permet de vérifier que l'utilisateur choisi bien 1 ou 0
	public static boolean checkSens(JFrame frame,int sens){
		if(sens < 0 || sens > 1){
			JOptionPane.showMessageDialog(frame,"Entre 0 pour horizontal et 1 pour vertical");
			return false;
		}else{
			return true;
		}
	}
	
	//Initialise un bateau selon son type passé en paramètre
	public static Ship initShip(Scanner scanner, JFrame frame, int type, int joueur, Grille grille){
		
		System.out.print("Entre la position X et Y (entre 0 et "+ grille.getSize() +")\n et le sens (0:horizontal 1: vertical) de l'avant du bateau:");  
		//Position X
		int posX = scanner.nextInt();
		while(!checkOutOfGrid(frame,posX,grille)){
			posX = scanner.nextInt();
		}
		//Position Y
		int posY = scanner.nextInt();
		while(!checkOutOfGrid(frame,posY,grille)){
			posY = scanner.nextInt();
		}
		//Sens 
		int sens = scanner.nextInt();
		while(checkSens(frame,sens)==false){
			sens = scanner.nextInt();
		}
		//Factory
		Ship ship;
		//Selection du bon contructeur selon le type
		switch(type){
		default:
		case 1:
			ship = new PorteAvion(posX,posY,sens,joueur);
			break;
		case 2:
			ship = new Croiseur(posX,posY,sens,joueur);
			break;
		case 3:
			ship = new ContreTorpilleur(posX,posY,sens,joueur);
			break;
		case 4:
			ship = new SousMarin(posX,posY,sens,joueur);
			break;
		case 5:
			ship = new Torpilleur(posX,posY,sens,joueur);
			break;
		}
		//Fin de la factory
		//Détection des collision
		if(detectCollision(frame,ship,grille)){
			return initShip(scanner,frame,type,joueur,grille);
		}else{
			return ship;
		}
	}

	//Initialisation du joueur
	public static void initPlayer(Scanner scanner, JFrame frame, int joueur, Grille grille){
		grille.show();
		int type =0;
		while(type<5){
		//Création des 5 bateaux
		type++;
		Ship ship = initShip(scanner,frame,type,joueur,grille);
		grille.add(ship);//try catch
		grille.show();
		}
	}

}
