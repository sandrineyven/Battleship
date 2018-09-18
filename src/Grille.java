
public class Grille {
	int size;
	
	private String[][] grille;
	
	public Grille(int size){
		this.size = size;
		this.grille = new String[size][size];
		for(int i =0;i<size;i++){
			for(int j=0;j<size;j++){
				grille[i][j]= "  ";
			}
		}
	}

	public void show(){
		System.out.println();
		for(int i =0;i<this.size;i++){
			for(int j=0;j<this.size;j++){
				System.out.print(" | " + this.grille[i][j]);
			}
			System.out.println(" | ");
		}
		System.out.println();
	}
}
