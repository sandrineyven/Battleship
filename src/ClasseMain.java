
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
		int size = Constante.gridSize;
		Grille grilleJ1 = new Grille(size);
		Grille grilleJ2 = new Grille(size);
		
		//init pour le joueur 1
		System.out.println("Joueur 1:");
		int joueur = 1;
		initPlayer(scanner,frame,joueur,grilleJ1);

		//init pour le joueur 2
		clear();
		System.out.println("Joueur 2:");
		joueur = 2;
		initPlayer(scanner,frame,joueur,grilleJ2);
		clear();
		
		//Boucle de jeu
		boolean run = true;
		joueur = 1;
		Grille grilleJoueur = grilleJ1;
		Grille grilleAdverse = grilleJ2;
		clear();
		while(run){
			clear();
			System.out.println("Joueur "+joueur+" :");
			grilleJoueur.show();
			grilleJoueur.showShipsAlive();
			//Choix du bateau pour tirer
			int indexShip = 99;
			while(indexShip>grilleJoueur.getShips().size()){
				System.out.println("Quel bateau pour tirer ? (Entrez le chiffre correspondant):");
				indexShip = scanner.nextInt();
			}
			//A utiliser pour les champ de tir:
			Ship choosenShip = grilleJoueur.getShips().get(indexShip-1);
			
			//R�cup�ration des coordonn�es d'attaque
			//A faire: prendre en compte de champ de tir
			System.out.println("Entre les coordonn�es d'attaque X et Y");
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
				//bouger le bateau au choix
				Ship ship = pickShip(scanner, grilleJoueur);
				if(ship != null){
					 moveShip(scanner,grilleJoueur,ship);
				}
			}else{
				System.out.println("Touch� !");
				//savoir quel bateau est touch� et agir en cons�quence
				grilleAdverse.update(posAttaqueX, posAttaqueY);
				if(grilleAdverse.getShips().isEmpty()){
					System.out.println("Le joueur " + joueur + " a gagn� !");
					sleep();
					run = false;
				}else{
					//bouger le bateau au choix
					Ship ship = pickShip(scanner, grilleJoueur);
					if(ship != null){
						 moveShip(scanner,grilleJoueur,ship);
					}
				}
			}
			
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
		if(!grille.cellIsEmpty(ship.getPositionX(), ship.getPositionY()))
		{
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
			for(int i=ship.getPositionX()+1;i<ship.getLongueur()+ship.getPositionX();i++){
				if(grille.getGrille()[i][ship.getPositionY()]!=Constante.emptyCell){
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
			for(int i=ship.getPositionY()+1;i<ship.getLongueur()+ship.getPositionY();i++){
				if(grille.getGrille()[ship.getPositionX()][i]!=Constante.emptyCell){
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
		boolean temp = detectCollision(frame,ship,grille);
		if(temp){
			return initShip(scanner,frame,type,joueur,grille);
		}else{
			return ship;
		}
	}

	//Initialisation du joueur
	public static void initPlayer(Scanner scanner, JFrame frame, int joueur, Grille grille){
		
		grille.show();
		int type =1;
		while(type<6){
		//Cr�ation des 5 bateaux
		Ship ship = initShip(scanner,frame,type,joueur,grille);
		grille.add(ship);//try catch
		grille.show();
		type++;
		}
	}

	//Bouger le beteau
	public static Ship pickShip(Scanner scanner, Grille grille){
		System.out.println("Voulez-vous bouger un bateau ? (y pour oui)1");
		String answer = scanner.next();
		int indexShip = 99;
		if(answer.substring(0, 1).equals("y")){
			//Choix du bateau
			while(indexShip>grille.getShips().size()){
				System.out.println("Lequel ? (Entrez le chiffre correspondant):");
				grille.showShipsAlive();
				indexShip = scanner.nextInt();
			}
			Ship ship = grille.getShips().get(indexShip-1);
			return ship;
		}else{
			return null;
		}
	}
	public static void moveShip(Scanner scanner, Grille grille, Ship ship){
		System.out.println("Dans quelle direction ? z/q/s/d");
		String answer = scanner.next();
		int delta = 0;
		while(delta < 1 || delta > 2){
			System.out.println("De combien de case ? 1/2");
			delta = scanner.nextInt();
		}
		switch(answer){
			case "z": 
				if(!grille.checkMoveZ(ship,delta)){
					System.out.println("Mouvement impossible");
					sleep();
				}else{
					grille.deleteShip(ship.getType());
					ship.setPositionY(ship.getPositionY()-delta);
					grille.add(ship);
					grille.show();
					sleep();
				}
				break;

			case "q":
				if(!grille.checkMoveQ(ship,delta)){
					System.out.println("Mouvement impossible");
					sleep();
				}else{
					grille.deleteShip(ship.getType());
					ship.setPositionX(ship.getPositionX()-delta);
					grille.add(ship);
					grille.show();
					sleep();
				}
				break;
			case "s":
				if(!grille.checkMoveS(ship,delta)){
					System.out.println("Mouvement impossible");
					sleep();
				}else{
					grille.deleteShip(ship.getType());
					ship.setPositionY(ship.getPositionY()+delta);
					grille.add(ship);
					grille.show();
					sleep();
				}
				break;
				
			case "d":
				if(!grille.checkMoveD(ship,delta)){
					System.out.println("Mouvement impossible");
					sleep();
				}else{
					grille.deleteShip(ship.getType());
					ship.setPositionX(ship.getPositionX()+delta);
					grille.add(ship);
					grille.show();
					sleep();
				}
				break;
				
			default:
				moveShip(scanner,grille,ship);
				break;
		}
	}

	
	//Console
	public static void sleep(){
		try {
			Thread.sleep(Constante.timeSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void clear(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
}
