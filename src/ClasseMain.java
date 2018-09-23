
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;




public class ClasseMain {
	public static void main(String[] args){ 
	    //donn�es, variables, diff�rents traitements�
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
		boolean run = true;
		joueur = 1;
		Grille grilleJoueur = grilleJ1;
		Grille grilleAdverse = grilleJ2;
		while(run){
			System.out.println("Joueur "+joueur+" :");
			grilleJoueur.show();
			grilleJoueur.showShipsAlive();
			//A faire: chosir un beteau pour tirer
			//R�cup�ration des coordonn�es d'attaque
			//A faire: prendre en compte de champ de tir
			JOptionPane.showMessageDialog(frame,"Entre les coordonn�es d'attaque X et Y");
			int posAttaqueX = scanner.nextInt();
			while(!checkOutOfGrid(frame,posAttaqueX,grilleJoueur)){
				posAttaqueX = scanner.nextInt();
			}
			int posAttaqueY = scanner.nextInt();
			while(!checkOutOfGrid(frame,posAttaqueY,grilleJoueur)){
				posAttaqueY = scanner.nextInt();
			}
		
			//V�rification sur la grille adverse
			if(grilleAdverse.cellIsEmpty(posAttaqueX, posAttaqueY)){
				System.out.println("A l'eau !");
			}else{
				System.out.println("Touch� !");
				//savoir quel bateau est touch� et agir en cons�quence
				update(posAttaqueX, posAttaqueY, grilleAdverse);
				if(grilleAdverse.getShips().isEmpty()){
					System.out.println("Le joueur " + joueur + "a gagn� !");
					run = false;
				}
			}
			//A Faire : bouger le bateau au choix
			//Changement de joueurs
			if(joueur == 1){
				joueur =2;
				grilleJoueur = grilleJ2;
				grilleAdverse = grilleJ1;
			}else{
				joueur =1;
				grilleJoueur = grilleJ1;
				grilleAdverse = grilleJ2;
			}
		}
		
	}  
	
	
	//Permet de v�rifier que la position soit inclue dans la grille
	public static boolean checkOutOfGrid(JFrame frame,int position, Grille grille){
		if(position < 1 || position > grille.getSize()){
			JOptionPane.showMessageDialog(frame,"Entre un nombre entre 1 et "+grille.getSize());
			return false;
		}else{
			return true;
		}
	}	
	
	//Permet de v�rifier les collisions
	public static boolean detectCollision(JFrame frame,Ship ship, Grille grille){
		//Case d�j� occup�e
		if(!grille.cellIsEmpty(ship.getPositionY(),ship.getPositionX())){
			JOptionPane.showMessageDialog(frame,"Cette place est occup�e");
			return true;
		}
		//Pour le sens horizontal
		if(ship.getSens() == 0){
			//Verification que le bateau ne d�passe pas
			if(ship.getPositionX() + ship.getLongueur() - grille.getSize() > 0){
				JOptionPane.showMessageDialog(frame,"Le bateau est en dehors de la grille");
				return true;
			}
			//V�rification qu'il n'y a rien aux places pr�vues par le bateau
			for(int i=ship.getPositionX()+1;i<ship.getLongueur();i++){
				if(!grille.cellIsEmpty(i, ship.getPositionX())){
					JOptionPane.showMessageDialog(frame,"Cette place est occup�e");
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
					JOptionPane.showMessageDialog(frame,"Cette place est occup�e");
					return true;
				}
			}
		}
		return false;
	}
	
	//Permet de v�rifi� le sens
	//Permet de v�rifier que l'utilisateur choisi bien 1 ou 0
	public static boolean checkSens(JFrame frame,int sens){
		if(sens < 0 || sens > 1){
			JOptionPane.showMessageDialog(frame,"Entre 0 pour horizontal et 1 pour vertical");
			return false;
		}else{
			return true;
		}
	}
	
	//Initialise un bateau selon son type pass� en param�tre
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
		//D�tection des collision
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
		//Cr�ation des 5 bateaux
		type++;
		Ship ship = initShip(scanner,frame,type,joueur,grille);
		grille.add(ship);//try catch
		grille.show();
		}
	}

	
	public static void update(int x, int y, Grille grille){
		String tag = grille.getGrille()[y][x];
		switch(tag){
		case " PA ":
			updatePdvByType(grille, 1);
			break;
		case " CR ":
			updatePdvByType(grille, 2);
			break;
		case " CT ":
			updatePdvByType(grille, 3);
			break;
		case " SM ":
			updatePdvByType(grille, 4);
			break;
		case " TO ":
			updatePdvByType(grille, 5);
			break;
		}
		grille.getGrille()[y][x] ="    ";
	}
	
	//Enl�ve les poitn de vie et retire le bateau de la liste di n�cessaire
	//Selon le type de bateau
	public static void updatePdvByType(Grille grille, int type){
		int index =0;
		for (Ship ship : grille.getShips()){
			if(ship.getType() == type){
				ship.setPointsdevie(ship.getPointsdevie()-1);
				if(ship.getPointsdevie() == 0){
					grille.getShips().remove(index);
					System.out.println("Le " + ship.getName() + "a �t� d�truit");
				}
				break;
			};
			index++;
		};
	}
}
