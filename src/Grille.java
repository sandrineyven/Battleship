
public class Grille {
	int size;
	
	private String[][] grille;
	
	public Grille(int size){
		this.size = size+1;
		this.grille = new String[size+1][size+1];
		for(int i =0;i<this.size;i++){
			for(int j=0;j<this.size;j++){
				grille[i][j]= "    ";
				grille[0][j]= "  " + j + " ";
				grille[i][0]= "  " + i + " ";
			}
		}
	}

	//Permet d'afficher la grille
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

	public void add(Ship ship){
		String tag = "  ";
		switch(ship.type){
			case 1: tag = " PA ";
				break;
			case 2: tag = " CR ";
				break;
			case 3: tag = " CT ";
				break;
			case 4: tag = " SM ";
				break;
			case 5: tag = " TO ";
				break;
		}
		this.grille[ship.positionY][ship.positionX]= tag;
		
			for(int i =1;i<ship.longueur;i++){
				if(ship.sens == 0){
					this.grille[ship.positionY][ship.positionX+i]= tag;
				}else{
					this.grille[ship.positionY+i][ship.positionX]= tag;
				}
			}
	
	}

	public boolean cellIsEmpty(int x, int y){
		if(this.grille[y][x] == "    "){
			return true;
		}else{
			return false;
		}
	}

}
