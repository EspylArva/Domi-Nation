import java.util.ArrayList;
import java.util.HashMap;

public class KingdomMap implements Cloneable {
	

	//////////////////////////////////////////////////////////////
	// 						VARIABLES							//
	//////////////////////////////////////////////////////////////
	private ArrayList<ArrayList<Cell>> terrain = new ArrayList<ArrayList<Cell>>();
	private int size;
	
	//////////////////////////////////////////////////////////////
	// 						 METHODS							//
	//////////////////////////////////////////////////////////////
	
	/**
	 * Returns a score according to the point counting rules:
	 * <p>
	 * The total score is the sum of every area score. An area is made of cells
	 * that share a same type. The area score is equal to the area surface
	 * multiplied by the sum of crowns on the area.
	 * <p>
	 * @return      The total score of the KingdomMap.
	 * @author 		Tchong-Kite HUAM
	 * @date		Last updated on 07.12.2018
	 */
	public int returnScore()
	{
		int res = 0;
		for(int i=0; i<terrain.size() ; i++)
		{
			for(int j=0; j<terrain.get(i).size() ; j++)		// iterates throughout the KingdomMap
			{
				res += calculateScore(i,j,0,0);				// add to the score ; detailed in calculateScore
				
//				System.out.println("Cell " + i + " "+ j + " | visited : " + terrain.get(i).get(j).isVisited() +" | score : "+ res);		// tracking the operations my cell XY positions
			}
		}		
		resetTerrainVisits();								// resetting the observation variables (isVisited = false)
		return res;
	}
	
	/**
	 * Resets the map's observation variables.
	 * <p>
	 * To allow score calculation through map-recursive function, boolean
	 * variable was implemented. Resetting the observation variable allows
	 * further score-counting.
	 * <p>
	 * @author 		Tchong-Kite HUAM
	 * @date		Last updated on 07.12.2018
	 */
	private void resetTerrainVisits() {
		for(ArrayList<Cell> row : terrain)
		{
			for(Cell cell : row)
			{
				cell.setVisited(false);
			}
		}		
	}
	
	/**
	 * Returns the score for the area located at a precise location.
	 * <p>
	 * Any area located at a precise location that has already been visited
	 * will no longer have a score; the function will simply return 0.
	 * <p>
	 * @param i				X position of the cell. Absciss.
	 * @param j				Y position of the cell. Ordinate.
	 * @param size			Size of the area.
	 * @param multiplier	Number of crown(s) on the area.
	 * @return				Partial or complete score for an area.
	 * @author 				Tchong-Kite HUAM
	 * @date				Last updated on 07.12.2018
	 */
	private int calculateScore(int i, int j, int size, int multiplier)
	{
		int res = 0;
		String cellType = terrain.get(i).get(j).getTerrainType();
		
		if(cellType.equals("void") || cellType.equals("castle"))		// void and castle cells are not part of any area; they should return 0
		{
			terrain.get(i).get(j).setVisited(true);						// not mandatory
			return res;
		}
		
		if(terrain.get(i).get(j).isVisited() == false)					// size and multiplier incrementation if the cell has never been visited
		{
			size += 1;
			multiplier += terrain.get(i).get(j).getCrownNb();
			terrain.get(i).get(j).setVisited(true);
		}
		
		HashMap<Cell, int[]> neighbours = new HashMap<Cell, int[]>();
		for(java.util.Map.Entry<int[], String> entry : this.getNeighbours(i, j).entrySet())
		{
			neighbours.put(terrain.get(entry.getKey()[0]).get(entry.getKey()[1]), entry.getKey());
		}
		// At this point, neighbours contains a HashMap<Cell, int[]> of cells near the targeted cell

		ArrayList<Cell> cellToDeleteTmp = new ArrayList<Cell>();
		for(java.util.Map.Entry<Cell, int[]> entry : neighbours.entrySet())
		{
			if(!entry.getKey().getTerrainType().equals(cellType) ||	// if the neighbours are not from the same type
					entry.getKey().isVisited() == true)				// if the neighbours have been visited
			{
				cellToDeleteTmp.add(entry.getKey());				
			}
		}
		for(Cell cellToDelete : cellToDeleteTmp)
		{
			neighbours.remove(cellToDelete);						// delete from neighbours
		}
		
		// At this point, neighbours contains a HashMap<Cell,int[]> of cells of the same type that have not been visited
		if(neighbours.size() == 0)			// Recursivity stop-condition
		{
			return (size * multiplier);
		}
		else
		{
/*
			for(java.util.Map.Entry<Cell, int[]> entry : neighbours.entrySet())
			{
				System.out.print(entry.getKey().getTerrainType());					// Displays list of nearby same type cells that have not been visited
			}
			System.out.println();
//*/
			for(java.util.Map.Entry<Cell, int[]> entry : neighbours.entrySet())		// Recursion on available neighbours
			{
				res += calculateScore(entry.getValue()[0], entry.getValue()[1], size, multiplier);
			}
		}
		return res;
	}
	
	
	
	
	/**
	 * Returns a X*Y rough size of the map.
	 * <p>
	 * @return				Returns an int[2]{Xsize, Ysize} array.
 	 * @author 				Tchong-Kite HUAM
	 * @date				last updated on 07.12.2018
	 */
	public int[] getRoughSize()
	{
		int minx=this.terrain.size(); int maxx=0;
		int miny=this.terrain.size(); int maxy=0;
		for(int i=0; i<this.terrain.size() ;i++)
		{
			
			for(int j=0; j<this.terrain.get(i).size(); j++)
			{
				if(!this.terrain.get(i).get(j).getTerrainType().equals("void"))
				{
					if(i<minx) {minx = i;}
					if(i>maxx) {maxx = i;}
					if(j<miny) {miny = j;}
					if(j>maxy) {maxy = j;}
				}
			}
		}
		int[] roughSize = new int[] {maxx-minx +1 , maxy-miny +1};
		return roughSize;
	}
	
	/**
	 * Replaces a cell by another at a precise position if it is free.
	 * <p>
	 * @param x				X precise position. Absciss.
	 * @param y				Y precise position. Ordonate.
	 * @param newCell		Cell replacing the old cell.
	 * @return				New map after change are done.
	 * @author 				Tchong-Kite HUAM
	 * @date				last updated on 07.12.2018
	 */
	public KingdomMap swapCell(int x, int y, Cell newCell)
	{
		if(terrain.get(x).get(y).getTerrainType().equals("void"))
		{
			ArrayList<Cell> row = terrain.get(x);
			row.set(y, newCell);
			terrain.set(x, row);
		}
		return this;
	}
	
	/**
	 * Returns a HashMap<int[], String> of the neighbours of a cell at a
	 * precise location.
	 * <p>
	 * @param posX			X precise position. Absciss.
	 * @param posY			Y precise position. Ordonate.
	 * @return				HashMap<int[], String> of the cell's neighbours.
	 * int[] contains the neighbour cell precise coordinates. String contains
	 * the type of the neighbour cell.
	 * @author 				Tchong-Kite HUAM
	 * @date				last updated on 07.12.2018
	 */
	public HashMap<int[], String> getNeighbours(int posX, int posY)
	{
		HashMap<int[], String> listOfNeighboursTypes = new HashMap<int[], String>();
		if(posX > 0)						//  anything but first row (top = possible)
		{
			listOfNeighboursTypes.put(new int[] {posX-1, posY} ,
					terrain.get(posX-1).get(posY).getTerrainType());
		}
		if( posX < size-1 )			// anything but last row (bottom = possible)
		{
			listOfNeighboursTypes.put(new int[] {posX+1, posY} ,
					terrain.get(posX+1).get(posY).getTerrainType());
		}
		
			// Adding <"Left" , type> ; <"Right" , type> if possible
		if(posY > 0)						// anything but first column (left = possible)
		{
			listOfNeighboursTypes.put(new int[] {posX, posY-1} ,
					terrain.get(posX).get(posY-1).getTerrainType());
		}
		if( posY < size-1 )			// anything but last column (right = possible)
		{
			listOfNeighboursTypes.put(new int[] {posX, posY+1} ,
					terrain.get(posX).get(posY+1).getTerrainType());
		}
			// Adding <"Top" , type> ; <"Bottom" , type> if possible
		return listOfNeighboursTypes;
	}
	
	
	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////
	
	/**
	 * Default constructor. Instanciate a map using utils.mapSize. Adds a
	 * castle at the middle of the map.
	 * <p>
	 * @author 				Tchong-Kite HUAM
	 * @date				last updated on 07.12.2018
	 */
	public KingdomMap()
	{
		this.size = utils.mapSize;
		
		for(int i=0 ; i<size; i++)
		{
			ArrayList<Cell> row = new ArrayList<Cell>();
			for(int j=0 ; j<size ; j++)
			{
				row.add(new Cell(0,"void"));
			}
			this.terrain.add(row);
		}
		
		this.swapCell((this.size -1)/2, (this.size -1)/2, new Cell(0, "castle"));	// adds the castle in the middle
		
	}
	/**
	 * Custom constructor. Instanciate a map of precise size. Adds a castle at
	 * the middle of the map.
	 * <p>
	 * @param size			Precise map size wanted. Should be odd number.
	 * @author 				Tchong-Kite HUAM
	 * @date				last updated on 07.12.2018
	 */
	public KingdomMap(int size)
	{
		this.size = size;
		
		for(int i=0 ; i<size; i++)
		{
			ArrayList<Cell> row = new ArrayList<Cell>();
			for(int j=0 ; j<size ; j++)
			{
				row.add(new Cell(0,"void"));
			}
			this.terrain.add(row);
		}
		
		this.swapCell((this.size -1)/2, (this.size -1)/2, new Cell(0, "castle")); // adds the castle in the middle
	}
	
	/**
	 * Default cloning method, using Clonable interface.
	 * <p>
	 * @date	last updated on 07.12.2018
	 */
	public Object clone() {
		Object o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la 
			// méthode super.clone()
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		return o;
	}
	
	//////////////////////////////////////////////////////////////
	// 						ACCESSOR							//
	//////////////////////////////////////////////////////////////
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public ArrayList<ArrayList<Cell>> getTerrain() {
		return terrain;
	}

	public void setTerrain(ArrayList<ArrayList<Cell>> terrain) {
		this.terrain = terrain;
	}

}
