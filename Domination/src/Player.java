import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

public class Player {

	//////////////////////////////////////////////////////////////
	// 						VARIABLES							//
	//////////////////////////////////////////////////////////////
	private String name;
	private ArrayList<Domino> dominoInHands = new ArrayList<Domino>();
	private Domino lastDomino;			// Utile ?
	private int points;
	
	private KingdomMap map;
	
	

	//////////////////////////////////////////////////////////////
	// 						 METHODS							//
	//////////////////////////////////////////////////////////////
	/**
	 * Allows the player to select a domino from the list of dominos in the
	 * drawing pile and add it to his hand.
	 * <p>
	 * @param availableDominos		ArrayList of dominos in the drawing pile.
	 * @return						Selected domino.
	 * @author 						Tchong-Kite HUAM
	 * @date						Last updated on 10.12.2018
	 */
	public Domino selectDomino(ArrayList<Domino> availableDominos)		// Demande un input d'index (int) et vérifie que cet input existe dans la liste availableDominos (paramètre)
	{																	// TODO ? Ajout à lastDomino
		Scanner scan = new Scanner(System.in);
		
		ArrayList<Integer> listOfDominoIndex = new ArrayList<Integer>();
		for(Domino i : availableDominos)
		{
			listOfDominoIndex.add(i.getIndex());
		}
		//listOfDominoIndex contient les n° des dominos de la pioche
		
		int choice = -1;
		while(! listOfDominoIndex.contains(choice))
		{			
			System.out.println(this.name + ", quel domino souhaitez-vous ajouter à votre bibliothèque ?");
			choice = scan.nextInt();
			if(! listOfDominoIndex.contains(choice))
			{
				System.out.println("Veuillez selectionner un domino disponible");
			}
		}
		for(Domino i : availableDominos)
		{
			if(i.getIndex() == choice)			//getIndex retourne la valeur du domino, comprise entre 1 et 48 (n° du domino) 
			{
				return i;
			}
		}
		return null;		// Code jamais atteint
	}

	public void addToHand(Domino domino) {
		this.getDominoInHands().add(domino);
	}

	
	/**
	 * Returns indexes of all dominos in the player's hand, for debugging
	 * purposes.
	 * <p>
	 * @return		String containing all dominos in hand.
	 * @author 		Tchong-Kite HUAM
	 * @date		Last updated on 10.12.2018
	 */
	public String showHand() {			// Montre l'index de tous les dominos en main
		String hand = "";
		for(Domino i : getDominoInHands())
		{
			hand+= (i.getIndex() + " ");
		}
		return hand;
	}

	/**
	 * Returns an array of possible moves for a selected domino.
 	 * The domino entered in parameter should be selected from the player's
	 * hand, drawing pile, or library. This method should be appliable to one's
	 * hand, but also another player in order to maximize/minimize one's loss.
	 * <p>
	 * A move is composed of a domino and
	 * its potential positions.
	 * <p>
	 * @param domino		Selected domino.
 	 * @param map			Selected map.
	 * @return				ArrayList of possible moves for a selected domino
	 * and map.
	 * @author 				Tchong-Kite HUAM
	 * @date				Last updated on 10.12.2018
	 */
	public ArrayList<Move> getPossibleMove(Domino domino, KingdomMap map)
	{

		int x; int y;
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell currentCell;
		
		for(int i=0 ; i<map.getSize() ; i++ )
		{
			for(int j=0; j<map.getSize() ; j++)
			{
				x=i;y=j;
				currentCell = map.getTerrain().get(x).get(y);
				String currentType = currentCell.getTerrainType();
				if(currentType.equals("void"))
				{
					moves.addAll(testNeighbours(x,y, domino, map));
				}
			}
		}
		try {
			for(Move move : moves)
			{
				if(!testNewSize(move))
				{
					moves.remove(move);
				}
			}		
		}
		catch(Exception e)
		{
			
		}
		return moves;
	}
	
	/**
	 * Tests if a move will imply an oversized map.
	 * <p>
	 * Map maximum size is defined in utils class and should be defined once
	 * for all in the game initialization.
	 * <p>
	 * @param move			Input move.
	 * @return				Explicit answer to whether the move is legitimate
	 * or not.
	 * @author 				Tchong-Kite HUAM
	 * @date				Last updated on 10.12.2018
	 */
	private boolean testNewSize(Move move) {
		KingdomMap potentialMap = (KingdomMap) this.map.clone();
		Domino inputDomino = move.getDomino();
		int[] cell1position = move.getPos1();
		int[] cell2position = move.getPos2();
		
		potentialMap.swapCell(cell1position[0], cell1position[1], inputDomino.getCells()[0]);
		potentialMap.swapCell(cell2position[0], cell2position[1], inputDomino.getCells()[1]);
		
		if(potentialMap.getRoughSize()[0] > utils.mapSize || potentialMap.getRoughSize()[1] > utils.mapSize)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * 
	 * @param move
	 * @return
	 * @author 						Tchong-Kite HUAM
	 * @date						Last updated on 10.12.2018
	 */
	public KingdomMap returnFutureMap(Move move)
	{
		int x1 = move.getPos1()[0];
		int y1 = move.getPos1()[1];
		int x2 = move.getPos2()[0];
		int y2 = move.getPos2()[1];
		
		Cell cell1 = move.getDomino().getCells()[0];
		Cell cell2 = move.getDomino().getCells()[1];
		
		this.map = this.map.swapCell(x1, y1, cell1);
		this.map = this.map.swapCell(x2, y2, cell2);
		
		return this.map;
	}
	
	/**
	 * Returns a list of possible moves for a set domino, for a set map and at
	 * a precise position.
	 * <p>
	 * The method should be appliable to any map from any player, for any
	 * domino, in order to allow further decisions for the players.
	 * <p>
	 * @param x			X precise position. Absciss.
	 * @param y			Y precise position. Ordonate.
	 * @param domino	Input domino.
	 * @param map		Input map.
	 * @return			ArrayList of moves possible at set position for input
	 * map and domino.
	 * @author 						Tchong-Kite HUAM
	 * @date						Last updated on 10.12.2018
	 */
	private ArrayList<Move> testNeighbours(int x, int y, Domino domino, KingdomMap map) {
		HashMap<int[], String> listOfNeighboursTypes = map.getNeighbours(x, y);
		ArrayList<Move> listOfMoves = new ArrayList<Move>();
		
		// At this point, listOfNeighboursType contains entries such as :
		/*
		 "top" 		; "mountains"
		 "bottom" 	; "void"
		 "left" 	; "plains"
		 "right" 	; "castle"		 
		*/
		
		for(int i=0 ; i<domino.getCells().length ; i++)		// i==0 => Cell=Cell1 ; i==1 => Cell=Cell2
		{	
			// if no neighbour is "void", the domino can't fit ; no action possible
			// no else
			if(listOfNeighboursTypes.containsValue("void"))// there is room to place the domino !
			{
				// Testing if a neighbour has the same type or is the castle !
				if(listOfNeighboursTypes.containsValue(domino.getCells()[i].getTerrainType())
						|| listOfNeighboursTypes.containsValue("castle"))
				{
					//System.out.print(i + " | " + String.valueOf(x) + " " + String.valueOf(y) + " | ");
					switch(i)
					{
					case 0:
						for(java.util.Map.Entry<int[], String> entry : listOfNeighboursTypes.entrySet())
						{
							if(entry.getValue().equals("void"))
							{
								listOfMoves.add(new Move(domino, new int[] {x,y}, entry.getKey() ));
							}
						}
						break;
					case 1:
						for(java.util.Map.Entry<int[], String> entry : listOfNeighboursTypes.entrySet())
						{
							if(entry.getValue().equals("void"))
							{
								listOfMoves.add(new Move(domino, entry.getKey() , new int[] {x,y}));
							}
						}
						break;
					}
					//System.out.println(listOfMoves.size());
				}
			}
		}
		return listOfMoves;
	}
	
	/**
	 * Updates player's score according to his current map.
	 * <p>
	 * @author 						Tchong-Kite HUAM
	 * @date						Last updated on 10.12.2018
	 */
	private void updateScore()
	{
		this.points = this.map.returnScore();
	}

	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////
	/**
	 * Default constructor for a player. Asks for a name input and sets the
	 * score to 0.
	 * <p>
	 * @param name		Player name input.
	 * @author 			Tchong-Kite HUAM
	 * @date			Last updated on 10.12.2018
	 */
	public Player(String name)
	{
		this.setName(name);
		this.setPoints(0);
	}
	//////////////////////////////////////////////////////////////
	// 						ACCESSOR							//
	//////////////////////////////////////////////////////////////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Domino> getDominoInHands() {
		return dominoInHands;
	}

	public void setDominoInHands(ArrayList<Domino> dominoInHands) {
		this.dominoInHands = dominoInHands;
	}
	public Domino getLastDomino()
	{
		return lastDomino;
	}
	public void setLastDomino(Domino idDomino) {
		this.lastDomino = idDomino;
	}
	
	public KingdomMap getKingdomMap() {
		return map;
	}

	public void setKingdomMap(KingdomMap map) {
		this.map = map;
	}
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
