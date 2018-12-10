
public class Cell {

	private int crownNb;
	private String terrainType = "void";
	private boolean isVisited = false;
	
	//////////////////////////////////////////////////////////////
	// 						VARIABLES							//
	//////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////
	// 						 METHODS							//
	//////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////
	/**
	 * Custom constructor. Instanciate a cell using its crown number and its
	 * type.
	 * <p>
	 * @param crownNb		Number of crowns on the cell.
	 * @param terrainType	Cell type.
	 * @author 				Tchong-Kite HUAM
	 * @date 				last updated on 10.12.2018
	 */
	public Cell(int crownNb, String terrainType)
	{
		this.crownNb = crownNb;
		this.terrainType = terrainType;
		this.isVisited = false;
	}
	
	//////////////////////////////////////////////////////////////
	// 						ACCESSOR							//
	//////////////////////////////////////////////////////////////
	public int getCrownNb() {
		return crownNb;
	}

	public void setCrownNb(int crownNb) {
		this.crownNb = crownNb;
	}

	public String getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(String terrainType) {
		this.terrainType = terrainType;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
}
