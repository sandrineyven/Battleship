import java.util.Scanner;

public class ClasseMain {

	public static void main(String[] args) { 

		//initialisation

		Scanner scanner = new Scanner (System.in);

		//Creation des grilles

		int size = Constante.gridSize;

		Grille grilleJ1 = new Grille(size);

		Grille grilleJ2 = new Grille(size);

		

		//init pour le joueur 1

		System.out.println("Joueur 1:");

		int joueur = 1;

		initPlayer(scanner,joueur,grilleJ1);



		//init pour le joueur 2

		clear();

		System.out.println("Joueur 2:");

		joueur = 2;

		initPlayer(scanner,joueur,grilleJ2);

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

			boolean choixBateau=false;

			while((indexShip>grilleJoueur.getShips().size()&&indexShip>0)||!choixBateau){

				System.out.println("Quel bateau pour tirer ? (Entrez le chiffre correspondant):");
				indexShip=LireNombre(scanner);

				if(indexShip>=1&&indexShip<=5)
					if(grilleJoueur.getShips().get(indexShip-1).getPointsdevie()==0)
						System.out.println("Veuillez choisir un bateau de la liste");
				else choixBateau=true;

			}

			

			Ship choosenShip = grilleJoueur.getShips().get(indexShip-1);

			

			//Recuperation des coordonnees d'attaque

			//Champ de tir

			int posAttaqueX = 0;

			int posAttaqueY = 0;

			while(!choosenShip.chekShoot(grilleJoueur, posAttaqueX, posAttaqueY))

			{

				System.out.println("Entre les coordonnees d'attaque X :");

				posAttaqueX = LireNombre(scanner);

				while(!checkOutOfGrid(posAttaqueX,grilleJoueur))
					posAttaqueX = LireNombre(scanner);

				System.out.println("Entre les coordonnees d'attaque Y :");
				posAttaqueY = LireNombre(scanner);

				while(!checkOutOfGrid(posAttaqueY,grilleJoueur))
					posAttaqueY = LireNombre(scanner);

				if(!choosenShip.chekShoot(grilleJoueur, posAttaqueX, posAttaqueY))
					System.out.println("Vous ne pouvez pas tirer à cet emplacement avec ce bateau! ");

			}

			

			

			//Verification sur la grille adverse

			if(grilleAdverse.cellIsEmpty(posAttaqueX, posAttaqueY)){

				System.out.println("A l'eau !");

				//bouger le bateau au choix

				Ship ship = pickShip(scanner, grilleJoueur);

				if(ship != null){

					 moveShip(scanner,grilleJoueur,ship);

				}

			}else{

				//savoir quel bateau est touche et agir en consequence

				Ship shipTouch = grilleAdverse.update(posAttaqueX, posAttaqueY);

				System.out.println("Le " + shipTouch.getName()+" a été touché.");

				sleep();

				if(grilleAdverse.win())

				{

					System.out.println("Le joueur " + joueur + " a gagne !");

					sleep();

					run = false;

				}

				else{

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

	

	

	//Permet de verifier que la position est inclue dans la grille

	public static boolean checkOutOfGrid(int position, Grille grille)
	{
		if(position <1 || position > grille.getSize())
		{
			System.out.println("Entre un nombre entre 1 et "+grille.getSize());
			return false;

		}
		else
			return true;
	}	

	

	//Permet de verifier les collisions

	public static boolean detectCollision(Ship ship, Grille grille){

		//Case deja  occupee

		if(!grille.cellIsEmpty(ship.getPositionX(), ship.getPositionY()))

		{

			System.out.println("Cette place est occupee.");

			return true;

		}

		//Pour le sens horizontal

		if(ship.getSens() == 0){

			//Verification que le bateau ne depasse pas

			if(ship.getPositionX() + ship.getLongueur() - grille.getSize() > 0){

				System.out.println("Le bateau est en dehors de la grille.");

				return true;

			}

			//Verification qu'il n'y a rien aux places prevues par le bateau

			for(int i=ship.getPositionX()+1;i<ship.getLongueur()+ship.getPositionX();i++){

				if(grille.getGrille()[i][ship.getPositionY()]!=Constante.emptyCell){

					System.out.println("Cette place est occupee");

					return true;

				}

			}

			//Meme chose pour le sens vertical

		}else if(ship.getSens() == 1){

			if(ship.getPositionY() + ship.getLongueur()-1 > grille.getSize()){

				System.out.println("Le bateau est en dehors de la grille");

				return true;

			}

			for(int i=ship.getPositionY()+1;i<ship.getLongueur()+ship.getPositionY();i++){

				if(grille.getGrille()[ship.getPositionX()][i]!=Constante.emptyCell){

					System.out.println("Cette place est occupee");

					return true;

				}

			}

		}

		return false;

	}

	

	//Permet de verifier le sens

	//Permet de verifier que l'utilisateur choisi bien 1 ou 0

	public static boolean checkSens(int sens){

		if(sens < 0 || sens > 1){

			System.out.println("Entrez 0 pour horizontal et 1 pour vertical.");

			return false;

		}else{

			return true;

		}

	}

	

	//Initialise un bateau selon son type passe en parametre

	public static Ship initShip(Scanner scanner, int type, int joueur, Grille grille){

		System.out.println("Entrez la position X (entre 1 et "+ (grille.getSize()-1)+")");
		
		//Position X
				int posX=LireNombre(scanner);
				
				while(!checkOutOfGrid(posX,grille))
					posX = LireNombre(scanner);
				
				
	    System.out.println("Entrez la position Y (entre 1 et "+ (grille.getSize()-1)+")");
		
	  //Position Y

	  		int posY = LireNombre(scanner);

	  		while(!checkOutOfGrid(posY,grille))
	  			posY = LireNombre(scanner);

	    System.out.println("Entrer le sens (0:horizontal 1: vertical) de l'avant du bateau:");  

		//Sens 

		int sens = LireNombre(scanner);

		while(!checkSens(sens))
			sens = LireNombre(scanner);

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

		//Detection des collision

		boolean temp2 = detectCollision(ship,grille);

		if(temp2){

			return initShip(scanner,type,joueur,grille);

		}else{

			return ship;

		}

	}



	//Initialisation du joueur

	public static void initPlayer(Scanner scanner, int joueur, Grille grille){

		

		grille.show();

		int type =1;

		while(type<6){

		//Creation des 5 bateaux

		Ship ship = initShip(scanner,type,joueur,grille);

		grille.add(ship);

		grille.show();

		type++;

		}

	}

	

	//Choix du bateau

	public static Ship pickShip(Scanner scanner, Grille grille){

		System.out.println("Voulez-vous bouger un bateau ? \n(O) Oui\n(N) Non");

		String answer = scanner.next();

		answer=answer.toUpperCase();

		int indexShip = 99;

		if(answer.substring(0, 1).equals("O")){

			//Choix du bateau

			while(indexShip>grille.getShips().size()){

				System.out.println("Lequel ? (Entrez le chiffre correspondant):");
				indexShip = LireNombre(scanner);

			}

			

			Ship ship = grille.getShips().get(indexShip-1);

			return ship;

		}

		else if(answer.substring(0, 1).equals("N")){

			return null;

		}

		else {

			System.out.println("La lettre au clavier n'est pas valide" );

			return pickShip(scanner,grille);

		}

	}

	//Verifie si cest possible de bouger le bateau et si oui, le bouge

	public static void moveShip(Scanner scanner, Grille grille, Ship ship){

		String answer = "";

		while(!answer.toUpperCase().equals("H") && !answer.toUpperCase().equals("B") && !answer.toUpperCase().equals("D") && !answer.toUpperCase().equals("G")){

			System.out.println("Dans quelle direction ? \n(G) Gauche\n(D) Droite\n(H) Haut\n(B) Bas");

			 answer = scanner.next();

		}

		int delta=0;
		while(delta<1||delta>2)
		{
			System.out.println("De combien de case ? \n(1) Une case\n(2) Deux cases");
			delta = LireNombre(scanner);
		}
		

		switch(answer.toUpperCase()){

			case "H": 

				if(!grille.checkMoveH(ship,delta)){

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



			case "G":

				if(!grille.checkMoveG(ship,delta)){

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

			case "B":

				if(!grille.checkMoveB(ship,delta)){

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

				

			case "D":

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

		System.out.println("\n\n\n\n\n");

	}
	public static int LireNombre(Scanner scanner)
	{
		String input;
		int nombre=-1;
		boolean estNombre=false;
		do {
			  input = scanner.next();
			  try {
			    nombre = Integer.parseInt(input);
			    estNombre = true;
			  } catch(NumberFormatException e) {
			    System.out.println("Cette valeur n'est pas un chiffre, veuillez entrer un chiffre entre 1 et 9 :");
			  }
			} while (estNombre != true);
		return nombre;
	}

}