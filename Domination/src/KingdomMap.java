import java.util.ArrayList;

public class KingdomMap {
	
	private ArrayList<ArrayList<Cell>> terrain = new ArrayList<ArrayList<Cell>>();

	private int size;
	

	public KingdomMap(int size)
	{
		this.size = size;
		
		for(int i=0 ; i<size; i++)
		{
			ArrayList<Cell> row = new ArrayList<Cell>();
			for(int j=0 ; j<size ; j++)
			{
				row.add(new Cell(0,"void", false));
			}
			this.terrain.add(row);
		}
		
		this.swapCell((this.size -1)/2, (this.size -1)/2, new Cell(0, "castle", false));
		
	}
	
	public KingdomMap swapCell(int x, int y, Cell newCell)
	{
		if(true)		// vérifie si la cell(x,y) est vide TODO
		{
			ArrayList<Cell> oldRow = terrain.get(x);
			oldRow.set(y, newCell);
			terrain.set(x, oldRow);
		}
		return this;
	}
	
	public int[] roughSize()
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

	//////////////////////////////////////////////////////////////
	// 						VARIABLES							//
	//////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////
	// 						 METHODS							//
	//////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////

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
