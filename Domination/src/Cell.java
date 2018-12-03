
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
	
	public Cell(int crownNb, String terrainType, boolean isVisited)
	{
		this.crownNb = crownNb;
		this.terrainType = terrainType;
		this.isVisited = isVisited;
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
