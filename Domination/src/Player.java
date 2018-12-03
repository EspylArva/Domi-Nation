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
	
	//TODO
	private KingdomMap map;
	
	

	//////////////////////////////////////////////////////////////
	// 						 METHODS							//
	//////////////////////////////////////////////////////////////
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

	

	public String showHand() {			// Montre l'index de tous les dominos en main
		String hand = "";
		for(Domino i : getDominoInHands())
		{
			hand+= (i.getIndex() + " ");
		}
		return hand;
	}
	/*
	public ArrayList<Move> getListValidMove(Domino domino, Map map)
	{
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		for (Cell cell : domino.getCells())
		{
			
		}
		
		for(Move move : possibleMoves)
		{
			if(!move.isPossible())
			{
				possibleMoves.remove(move);
			}
		}
		return possibleMoves;
	}
	//*/
	
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
		return moves;
	}
	
	public KingdomMap returnFutureMap(Move move)
	{
		int x1 = move.getPos1()[0];
		int y1 = move.getPos1()[1];
		int x2 = move.getPos2()[0];
		int y2 = move.getPos2()[1];
		
		Cell cell1 = move.getDomino().getCells()[0];
		Cell cell2 = move.getDomino().getCells()[1];
		
		System.out.println(cell1.getCrownNb() + " " + cell1.getTerrainType());
		
		System.out.println(String.valueOf(x1)+ String.valueOf(y1) + " " + String.valueOf(x2)+ String.valueOf(y2));
		
		this.map = this.map.swapCell(x1, y1, cell1);
		this.map = this.map.swapCell(x2, y2, cell2);
		
		return this.map;
	}
	
	private ArrayList<Move> testNeighbours(int x, int y, Domino domino, KingdomMap map) {
		HashMap<int[], String> listOfNeighboursTypes = new HashMap<int[], String>();
		ArrayList<ArrayList<Cell>> mapCell = map.getTerrain();
		ArrayList<Move> listOfMoves = new ArrayList<Move>();
		
		
		// Adding <"Top" , type> ; <"Bottom" , type> if possible
		if(x > 0)						//  anything but first row (top = possible)
		{
			listOfNeighboursTypes.put(new int[] {x-1, y} ,
					mapCell.get(x-1).get(y).getTerrainType());
		}
		if( x < map.getSize()-1 )			// anything but last row (bottom = possible)
		{
			listOfNeighboursTypes.put(new int[] {x+1, y} ,
					mapCell.get(x+1).get(y).getTerrainType());
		}
		
			// Adding <"Left" , type> ; <"Right" , type> if possible
		if(y > 0)						// anything but first column (left = possible)
		{
			listOfNeighboursTypes.put(new int[] {x, y-1} ,
					mapCell.get(x).get(y-1).getTerrainType());
		}
		if( y < map.getSize()-1 )			// anything but last column (right = possible)
		{
			listOfNeighboursTypes.put(new int[] {x, y+1} ,
					mapCell.get(x).get(y+1).getTerrainType());
		}

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

	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////
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
