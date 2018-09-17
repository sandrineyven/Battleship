import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ClasseMain {
	public static void main(String[] args){ 
	    //données, variables, différents traitements…
		//initialisation
		Scanner scanner = new Scanner (System.in);
		JFrame frame = new JFrame("FrameDemo");
		System.out.println("Joueur 1:");
		int joueur = 1;
		
		System.out.print("Entrez la position X et Y (entre 1 et 10) et le sens (0:horizontal 1: vertical) de l'avant du porte-avion:");  
		int posX = scanner.nextInt();
		while(checkPosition(frame,posX)==false){
			posX = scanner.nextInt();
		}	
		int posY = scanner.nextInt();
		while(checkPosition(frame,posY)==false){
			posY = scanner.nextInt();
		}	
		int sens = scanner.nextInt();
		while(checkSens(frame,sens)==false){
			sens = scanner.nextInt();
		}
		
		PorteAvion porteavionJ1 = new PorteAvion(posX,posY,sens,joueur);
		Croiseur croiseurJ1 = new Croiseur(5,8,0,1);
		//while(1)
		System.out.println(porteavionJ1.sens);
		System.out.println(croiseurJ1.name);  
	}  
	
	public static boolean checkPosition(JFrame frame,int position){
		if(position < 1 || position > 10){
			JOptionPane.showMessageDialog(frame,"Entre un nombre entre 1 et 10");
			return false;
		}else{
			return true;
		}
	}
	public static boolean checkSens(JFrame frame,int sens){
		if(sens < 0 || sens > 1){
			JOptionPane.showMessageDialog(frame,"Entre 0 pour horizontal et 1 pour vertical");
			return false;
		}else{
			return true;
		}
	}
}
