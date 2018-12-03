
public class Move {

	//////////////////////////////////////////////////////////////
	// 						VARIABLES							//
	//////////////////////////////////////////////////////////////

	private Domino domino;

	private int[] pos1;
	private int[] pos2;
	private KingdomMap map;
	private boolean isPossible;
	
	//////////////////////////////////////////////////////////////
	// 						 METHODS							//
	//////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////

	public Move(Domino dominal, int[] pos1, int[] pos2)
	{
		this.domino = dominal;
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public boolean isPossible() {
		boolean res = false;
		// parcourir chaque cell. Verifier si les voisins sont legit.
			//SI oui : res = true;
			//SI non : res = false;
		
		//SI oui : 
		
		
		return false;
	}
	
	//////////////////////////////////////////////////////////////
	// 						ACCESSOR							//
	//////////////////////////////////////////////////////////////
	public Domino getDomino() {
		return domino;
	}

	public void setDomino(Domino domino) {
		this.domino = domino;
	}

	public int[] getPos1() {
		return pos1;
	}

	public void setPos1(int[] pos1) {
		this.pos1 = pos1;
	}

	public int[] getPos2() {
		return pos2;
	}

	public void setPos2(int[] pos2) {
		this.pos2 = pos2;
	}

	public KingdomMap getMap() {
		return map;
	}

	public void setMap(KingdomMap map) {
		this.map = map;
	}

	public void setPossible(boolean isPossible) {
		this.isPossible = isPossible;
	}

}


