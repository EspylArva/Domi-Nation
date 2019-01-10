
public class Domino implements Comparable<Domino> {

	//////////////////////////////////////////////////////////////
	// 						VARIABLES							//
	//////////////////////////////////////////////////////////////

	private int index;
	private Cell[] cells = new Cell[2];
	
	//////////////////////////////////////////////////////////////
	// 						 METHODS							//
	//////////////////////////////////////////////////////////////
	
	/**
	 * Compares two dominos' indexes.
	 * <p>
	 * @return			Difference between two indexes.
	 * @author 			Tchong-Kite HUAM
	 * @date			last updated on 10.12.2018
	 */
	@Override
	public int compareTo(Domino arg0) {
		return this.index - arg0.index;
	}
	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////
	/**
	 * Default constructor, creating an empty domino only using an index.
	 * <p>
	 * @param index		Domino's index for reference.
	 * @author 			Tchong-Kite HUAM
	 * @date			last updated on 10.12.2018
	 */
	public Domino(int index)
	{
		this.index = index;
	}
	
	/**
	 * Custom constructor, using cells to define domino's contents.
	 * <p>
	 * @param index		Domino's index for reference.
	 * @param cell1		First cell of the domino.
	 * @param cell2		Second cell of the domino.
	 * @author 			Tchong-Kite HUAM
	 * @date			last updated on 10.12.2018
	 */
	public Domino(int index, Cell cell1, Cell cell2)
	{
		this.index = index;
		this.cells[0] = cell1;
		this.cells[1] = cell2;
	}
	
	/**
	 * Custom constructor, using csv's data to construct a domino.
	 * <p>
	 * @param index		Domino's index for reference.
	 * @param nbCrown1	Number of crowns on the first cell.
	 * @param type1		First cell's type.
	 * @param nbCrown2	Number of crowns on the second cell.
	 * @param type2		Second cell's type.
	 * @author 			Tchong-Kite HUAM
	 * @date			last updated on 10.12.2018
	 */
	public Domino(int index, int nbCrown1, String type1, int nbCrown2, String type2)
	{
		this.index = index;
		Cell cell1 = new Cell(nbCrown1, type1);
		Cell cell2 = new Cell(nbCrown2, type2);
		this.cells[0] = cell1;
		this.cells[1] = cell2;
	}
	//////////////////////////////////////////////////////////////
	// 						ACCESSOR							//
	//////////////////////////////////////////////////////////////
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	public Cell[] getCells()
	{
		return this.cells;
	}
	
	public void setCells(Cell cell1, Cell cell2) {
		this.cells[0] = cell1;
		this.cells[1] = cell2;
	}
	
	public Cell getCell1() {
		return this.cells[0];
	}
	
	public Cell getCell2() {
		return this.cells[1];
	}
	
}
