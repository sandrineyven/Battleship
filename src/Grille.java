import java.util.ArrayList;
import java.util.List;

public class Grille {
	private int size;
	private List <Ship> ships;
	private String[][] grille;
	
	
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public String[][] getGrille() {
		return grille;
	}


	public Grille(int size){
		this.size = size+1;
		this.ships = new ArrayList<Ship>();
		this.grille = new String[size+1][size+1];
		for(int y =0;y<this.size;y++){
			for(int x=0;x<this.size;x++){
				grille[x][y]= "    ";
				grille[0][y]= "  " + y + " ";
				grille[x][0]= "  " + x + " ";
			}
		}
	}

	//Permet d'afficher la grille
	protected void show(){
		System.out.println();
		for(int y =0;y<this.size;y++){
			for(int x=0;x<this.size;x++){
				System.out.print(" | " + this.grille[x][y]);
			}
			System.out.println(" | ");
		}
		System.out.println();
	}

	protected void add(Ship ship){
		String tag = "  ";
		switch(ship.type){
			case 1: tag = " PA ";
				this.ships.add(ship);
				break;
			case 2: tag = " CR ";
				this.ships.add(ship);
				break;
			case 3: tag = " CT ";
				this.ships.add(ship);
				break;
			case 4: tag = " SM ";
				this.ships.add(ship);
				break;
			case 5: tag = " TO ";
				this.ships.add(ship);
				break;
		}
		this.grille[ship.positionX][ship.positionY]= tag;
		
			for(int i =1;i<ship.longueur;i++){
				if(ship.sens == 0){
					this.grille[ship.positionX+i][ship.positionY]= tag;
				}else{
					this.grille[ship.positionX][ship.positionY+i]= tag;
				}
			}
	
	}

	protected boolean cellIsEmpty(int x, int y){
		if(this.grille[x][y] == "    "){
			return true;
		}else{
			return false;
		}
	}

	protected void showShipsAlive(){
		for(int i =0; i<ships.size();i++){
		System.out.println(this.ships.get(i).name + ": " + this.ships.get(i).pointsdevie + " pdv");
		}
	}

	protected void deleteCell(int x, int y)
	{
		this.grille[x][y]="    ";
	}
}
