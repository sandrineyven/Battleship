import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;




public class ClasseMain {
	public static void main(String[] args){ 
	    //données, variables, différents traitements…
		//initialisation
		Scanner scanner = new Scanner (System.in);
		JFrame frame = new JFrame("FrameDemo");
		
		//Creation de la grille 
		Grille grille = new Grille(10);
		grille.show();
		/*JPanel pan = new JPanel(new GridLayout(grille.size,grille.size));
		Border blackline = BorderFactory.createLineBorder(Color.black,1);
		for(int i=0;i < grille.size*grille.size;i++){
			JPanel ptest = new JPanel();
			ptest.setBorder(blackline);
			pan.add(ptest);
		}
		pan.setBorder(blackline);
		frame.add(pan);
		frame.setVisible(true);*/
		
		
		
		//List qui va contenir tout les bateaux du jeux
		List<Ship> shiplist = new ArrayList();
		
		//init pour le joueur 1
		System.out.println("Joueur 1:");
		int joueur = 1;
		int type =0;
		while(type<5){
		//Création des 5 bateaux
		type++;
		Ship ship = initShip(scanner,frame,type,joueur,grille.size);
		shiplist.add(ship);
		}
		//init pour le joueur 2
		System.out.println("Joueur 2:");
		joueur = 2;
		type =0;
		while(type<5){
		//Création des 5 bateaux
		type++;
		Ship ship = initShip(scanner,frame,type,joueur,grille.size);
		shiplist.add(ship);
		}
		
		//while(1)
		System.out.println(shiplist);  
	}  
	//Permet de vérifier que la position soit inclue dans la grille
	public static boolean checkPosition(JFrame frame,int position, int size){
		if(position < 1 || position > size){
			JOptionPane.showMessageDialog(frame,"Entre un nombre entre 1 et "+size);
			return false;
		}else{
			return true;
		}
	}
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
	public static Ship initShip(Scanner scanner, JFrame frame, int type, int joueur, int size){
		System.out.print("Entre la position X et Y (entre 0 et "+ size +") et le sens (0:horizontal 1: vertical) de l'avant du bateau:");  
		int posX = scanner.nextInt();
		while(checkPosition(frame,posX,size)==false){
			posX = scanner.nextInt();
		}	
		int posY = scanner.nextInt();
		while(checkPosition(frame,posY,size)==false){
			posY = scanner.nextInt();
		}	
		int sens = scanner.nextInt();
		while(checkSens(frame,sens)==false){
			sens = scanner.nextInt();
		}
		Ship ship;
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
		return ship; 
	}
}
