
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
		int size = Constante.gridSize;
		Grille grilleJ1 = new Grille(size);
		Grille grilleJ2 = new Grille(size);
		
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
			//A faire: chosir un beteau pour tirer
			//Récupération des coordonnées d'attaque
			//A faire: prendre en compte de champ de tir
			System.out.println("Entre les coordonnées d'attaque X et Y");
			int posAttaqueX = scanner.nextInt();
			while(!checkOutOfGrid(frame,posAttaqueX,grilleJoueur)){
				posAttaqueX = scanner.nextInt();
			}
			int posAttaqueY = scanner.nextInt();
			while(!checkOutOfGrid(frame,posAttaqueY,grilleJoueur)){
				posAttaqueY = scanner.nextInt();
			}
			
			//Vérification sur la grille adverse
			if(grilleAdverse.cellIsEmpty(posAttaqueX, posAttaqueY)){
				System.out.println("A l'eau !");
				sleep();
			}else{
				System.out.println("Touché !");
				//savoir quel bateau est touché et agir en conséquence
				update(posAttaqueX, posAttaqueY, grilleAdverse);
				sleep();
				clear();
				if(grilleAdverse.getShips().isEmpty()){
					System.out.println("Le joueur " + joueur + " a gagné !");
					sleep();
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
		if(!grille.cellIsEmpty(ship.getPositionX(), ship.getPositionY()))
		{
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
			for(int i=ship.getPositionX()+1;i<ship.getLongueur()+ship.getPositionX();i++){
				if(grille.getGrille()[i][ship.getPositionY()]!=Constante.emptyCell){
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
			for(int i=ship.getPositionY()+1;i<ship.getLongueur()+ship.getPositionY();i++){
				if(grille.getGrille()[ship.getPositionX()][i]!=Constante.emptyCell){
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
		//Création des 5 bateaux
		Ship ship = initShip(scanner,frame,type,joueur,grille);
		grille.add(ship);//try catch
		grille.show();
		type++;
		}
	}

	
	public static void update(int x, int y, Grille grille){
		String tag = grille.getGrille()[x][y];
		switch(tag){
		case Constante.tag_pa:
			updatePdvByType(grille, 1);
			break;
		case Constante.tag_cr:
			updatePdvByType(grille, 2);
			break;
		case Constante.tag_ct:
			updatePdvByType(grille, 3);
			break;
		case Constante.tag_sm:
			updatePdvByType(grille, 4);
			break;
		case Constante.tag_to:
			updatePdvByType(grille, 5);
			break;
		}
		grille.getGrille()[x][y] =Constante.emptyCell;
	}
	
	//Enlève les points de vie et retire le bateau de la liste si nécessaire
	//Selon le type de bateau
	public static void updatePdvByType(Grille grille, int type){
		int index =0;
		for (Ship ship : grille.getShips()){
			if(ship.getType() == type){
				ship.setPointsdevie(ship.getPointsdevie()-1);
				if(ship.getPointsdevie() == 0){
					grille.getShips().remove(index);

					//Efface tout le bateau sur ligne ou colonne
					deleteShip(grille,type);
					
					System.out.println("Le " + ship.getName() + "a été détruit");
				}
				break;
			};
			index++;
		};
	}
	public static String intToShip(int i)
	{
		switch(i)
		{
		case 1 :
			return Constante.tag_pa;
		case 2 :
			return Constante.tag_cr;
		case 3 : 
			return Constante.tag_ct;
		case 4 : 
			return Constante.tag_sm;
		default :
			return Constante.tag_to;
		
		}
	}
	public static void deleteShip(Grille grille,int type){
		for(int y=1;y<grille.getSize();y++){
			for(int x=1;x<grille.getSize();x++){
				if(grille.getGrille()[x][y]==intToShip(type)){
					grille.deleteCell(x,y);
				}
			}
		}
	}
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
