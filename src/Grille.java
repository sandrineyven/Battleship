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
				grille[x][y]= Constante.emptyCell;
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
			case 1: tag = Constante.tag_pa;
				this.ships.add(ship);
				break;
			case 2: tag = Constante.tag_cr;
				this.ships.add(ship);
				break;
			case 3: tag = Constante.tag_ct;
				this.ships.add(ship);
				break;
			case 4: tag = Constante.tag_sm;
				this.ships.add(ship);
				break;
			case 5: tag = Constante.tag_to;
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
		if(this.grille[x][y] == Constante.emptyCell){
			return true;
		}else{
			return false;
		}
	}

	protected void showShipsAlive(){
		for(int i =0; i<ships.size();i++){
			int j=i+1;
			System.out.println("(" + j + ")" + this.ships.get(i).name + ": " + this.ships.get(i).pointsdevie + " pdv");
		}
	}

	public boolean checkMoveZ(Ship ship, int delta){
		if(ship.getPositionY()-delta < 0){
			return false;
		}
		if(ship.getSens()==0){
			for(int i = ship.getPositionX();i<ship.getLongueur();i++){
				if(!this.cellIsEmpty(i,ship.getPositionY()-delta)){
					return false;
				}
			}
		}else if(ship.getSens()==1){
			if(!this.cellIsEmpty(ship.getPositionX(),ship.getPositionY()-delta)){
				return false;
			}
		}
		return true;
	}
	public boolean checkMoveQ(Ship ship, int delta){
		if(ship.getPositionX()-delta < 0){
			return false;
		}
		if(ship.getSens()==1){
			for(int i = ship.getPositionY();i<ship.getLongueur();i++){
				if(!this.cellIsEmpty(ship.getPositionX()-delta,i)){
					return false;
				}
			}
		}else if(ship.getSens()==0){
			if(!this.cellIsEmpty(ship.getPositionX()-delta,ship.getPositionY())){
				return false;
			}
		}
		return true;
	}
	
	public boolean checkMoveS(Ship ship, int delta){
		if(ship.getPositionY()+delta > this.getSize()){
			return false;
		}
		if(ship.getSens()==0){
			for(int i = ship.getPositionX();i<ship.getLongueur();i++){
				if(!this.cellIsEmpty(i,ship.getPositionY()+delta)){
					return false;
				}
			}
		}else if(ship.getSens()==1){
			if(!this.cellIsEmpty(ship.getPositionX(),ship.getPositionY()+delta+ship.getLongueur()-1)){
				return false;
			}
		}
		return true;
	}
	public boolean checkMoveD(Ship ship, int delta){
		if(ship.getPositionX()+delta > this.getSize()){
			return false;
		}
		if(ship.getSens()==1){
			for(int i = ship.getPositionY();i<ship.getLongueur();i++){
				if(!this.cellIsEmpty(ship.getPositionX()+delta,i)){
					return false;
				}
			}
		}else if(ship.getSens()==0){
			if(!this.cellIsEmpty(ship.getPositionX()+delta+ship.getLongueur()-1,ship.getPositionY())){
				return false;
			}
		}
		return true;
	}

	protected void deleteCell(int x, int y)
	{
		this.grille[x][y]=Constante.emptyCell;
	}
	
	public void deleteShip(int type){
		for(int y=1;y<this.getSize();y++){
			for(int x=1;x<this.getSize();x++){
				if(this.getGrille()[x][y]==intToShip(type)){
					this.deleteCell(x,y);
				}
			}
		}
	}
	
	public String intToShip(int i)
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


	public void update(int x, int y){
		String tag = this.getGrille()[x][y];
		switch(tag){
		case Constante.tag_pa:
			updatePdvByType(1);
			break;
		case Constante.tag_cr:
			updatePdvByType(2);
			break;
		case Constante.tag_ct:
			updatePdvByType(3);
			break;
		case Constante.tag_sm:
			updatePdvByType(4);
			break;
		case Constante.tag_to:
			updatePdvByType(5);
			break;
		}
		this.getGrille()[x][y] =Constante.emptyCell;
	}
	
	//Enlève les points de vie et retire le bateau de la liste si nécessaire
	//Selon le type de bateau
	public void updatePdvByType(int type){
		int index =0;
		for (Ship ship : this.getShips()){
			if(ship.getType() == type){
				ship.setPointsdevie(ship.getPointsdevie()-1);
				if(ship.getPointsdevie() == 0){
					this.getShips().remove(index);

					//Efface tout le bateau sur ligne ou colonne
					this.deleteShip(type);
					
					System.out.println("Le " + ship.getName() + "a été détruit");
				}
				break;
			};
			index++;
		};
	}
}