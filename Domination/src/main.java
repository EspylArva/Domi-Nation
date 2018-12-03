import java.io.IOException;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.io.TMXMapWriter;

public class main {
	
	public static void main(String[] args)
	{
		boolean zoneTest = true;
		if(zoneTest){

			Player player = new Player("François");
			Domino domino = new Domino(24, new Cell(3, "mountain", false), new Cell(0, "sea", false));
			player.addToHand(domino);
			KingdomMap map = new KingdomMap(9);
			
			System.out.println(domino.getCells()[0].getTerrainType());
			System.out.println(domino.getCells()[1].getTerrainType());
			
			map.swapCell(2, 3, new Cell(0,"plains"	, false));
			map.swapCell(2, 4, new Cell(0,"plains"	, false));
			map.swapCell(3, 2, new Cell(0,"plains"	, false));
			map.swapCell(3, 4, new Cell(0,"mountain", false));
			map.swapCell(4, 2, new Cell(0,"sea"		, false));
			map.swapCell(4, 3, new Cell(0,"mountain", false));
			map.swapCell(5, 2, new Cell(0,"mountain", false));
			
			player.setKingdomMap(map);
			
		/*	0 1 2 3 4 5 6 7 8
		 0	0 0 0 0 0 0 0 0 0
		 1	0 0 0 0 0 0 0 0 0
		 2	0 0 0 P P 0 0 0 0
		 3	0 0 P 0 M 0 0 0 0
		 4	0 0 S M K 0 0 0 0
		 5	0 0 M 0 0 0 0 0 0
		 6	0 0 0 0 0 0 0 0 0
		 7	0 0 0 0 0 0 0 0 0
		 8	0 0 0 0 0 0 0 0 0
		*/
			/*
			for(ArrayList<Cell> row : map.getTerrain())
			{
				for(Cell cell:row)
				{
					System.out.println(cell.getTerrainType());
				}
			}//*/
			
			ArrayList<Move> moves = player.getPossibleMove(domino, map);
			for(Move move : moves)
			{
				System.out.println("Cell1 : " + move.getPos1()[0] + ";" + move.getPos1()[1] + " | Cell2 : " + + move.getPos2()[0] + ";" + move.getPos2()[1]);
			}

			System.out.println(map.roughSize()[0] + "x" + map.roughSize()[1]);
			
			System.out.println(moves.get(3).getDomino().getCells()[0].getTerrainType());
			
			int x1 = moves.get(3).getPos1()[0];
			int y1 = moves.get(3).getPos1()[1];
			int x2 = moves.get(3).getPos2()[0];
			int y2 = moves.get(3).getPos2()[1];
			
			System.out.println(x1 + " " + y1 + " | " + x2 + " " + y2);

			for(ArrayList<Cell> row : map.getTerrain())
			{
				for(Cell column : row)
				{
					System.out.print(column.getTerrainType() + " ");
				}
				System.out.println();
			}//*/

			
			KingdomMap newmap = player.returnFutureMap(moves.get(3));
			
			
			for(ArrayList<Cell> row : newmap.getTerrain())
			{
				for(Cell column : row)
				{
					System.out.print(column.getTerrainType() + " ");
				}
				System.out.println();
			}//*/
			
			System.out.println("End");
			
			
			
			
			
			
			
			
			
			
			
		}else {
		
		
		
		
		//*/
		Game game = new Game(3);								// Jeu sur deux joueurs, créé : 
																//								1 deck de X * 12 dominos (ici 2*12 = 24)
																//								2 joueurs et leur distribue un domino, en l'enlevant du deck
																//								X rois (ici 4, car 2*2 ; on aurait pu avoir 3 ou 4)

		game.setChoice(game.distribDominos());							// On pioche X dominos et on les affiche : ici 4 rois, donc 4 dominos
		ArrayList<Player> turnX = game.randomizeKings();       	// On mélange les rois pour distribuer les dominos aléatoirement

	    disp(game);
	    
	    // Affiche l'ordre du 1er tour
	    for(Player i : turnX) 	{System.out.print(i.getName() + " ");}  System.out.println();
// TOUR N°1
	    for (Player i : turnX) // On peut remplacer turnX par game.randomizeKings() SSI on enlève le display de l'ordre du 1er tour (sinon, deux objets)
	    {
	    	Domino pick = i.selectDomino(game.getChoice());
	    	i.addToHand(pick);									// Chaque joueur choisit un domino parmi la pioche
	    	game.getOldChoice().add(pick);								// On passe les dominos choisis dans la pioche des anciens dominos
	    	game.getChoice().remove(pick);								// On enleve les dominos choisis des options possibles
	    	//TODO : Choisir l'emplacement du chateau
	    }														
	    
	    
	    
// TOUR N°X
	    while(!game.getAvailableDominos().isEmpty())
	    {
	    	game.setChoice(game.distribDominos());							// On repioche 3-4 dominos pour constituer une nouvelle pioche
	    
	    disp(game);
	    
	    // On doit récupérer l'id le plus faible parmi l'ancienne pioche et faire jouer le joueur en mettant son domino sur le terrain

	    for(Player player : game.getTurnOrder())								// Répétition d'un tour normal jusqu'à l'épuisement de la bibliothèque
	    {
	    	System.out.print(player.getName() + " | " + player.showHand() + " | ");
	    	// player.placeDomino();			// TODO
	    	Domino pick = player.selectDomino(game.getChoice());
		    player.addToHand(pick);
		    game.getOldChoice().add(pick);
		    game.getChoice().remove(pick);
	    	System.out.println();
	    }
	    
	    disp(game);
	    }

		///////////															
	    // TESTS //															
	    ///////////																	
	    
	    
	// TODO GENERATING RANDOM NUMBERS
		/*int i = 0;
		int sum = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		while(i < 1000)
		{
			int j = (int)(Math.random()*100);
			sum += j;
			System.out.println(j);
			i++;
		}//*/
	// TODO RANDOMIZING AN ARRAY
		/*ArrayList<Integer> test = new ArrayList<Integer>(); test.add(1);test.add(2);test.add(3);test.add(4);
		
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (Integer i : test)
		{
			tmp.add(i);
		}
		for(int i=0 ; i<4 ; i++)
		{
			int rndIndex = (int)(Math.random() * tmp.size());
			res.add(tmp.get(rndIndex));
			tmp.remove(rndIndex);
		}
		
		System.out.println("Array resul  : " + res);
		System.out.println("Array temp   : " + tmp);
		System.out.println("Array Origin : " + test);
		//*/

    // TODO DISPLAYING A WINDOW FROM JAVA
		/*
	       // on crée une fenêtre dont le titre est "Hello World!"
	       JFrame frame = new JFrame("Hello World!");
	       // la fenêtre doit se fermer quand on clique sur la croix rouge
	       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	       
	       // on ajoute le texte "Hello, World!" dans la fenêtre
	       frame.getContentPane().add(new JLabel("Hello, World!"));
	       
	       // on demande d'attribuer une taille minimale à la fenêtre
	       //  (juste assez pour voir tous les composants)
	       frame.pack();
	       // on centre la fenêtre
	       frame.setLocationRelativeTo(null);
	       // on rend la fenêtre visible
	       frame.setVisible(true);
	   //*/
    // TODO DISPLAY A TMX MAP
		/*
		TMXViewer viewer = new TMXViewer();
		String[] str = new String[] {"res\\map.tmx"};
		TMXViewer.main(str);
		//*/
		/*
		TMXViewer viewer = new TMXViewer();
		String[] str = new String[] {"res\\map.tmx"};
		
		TMXMapWriter writer = new TMXMapWriter();
		Map map = new Map();
		
		try {
			writer.writeMap(map, str[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}//*/
		}
	}

	public static void disp(Game game)
	{
		System.out.print("BIBLIOTHEQUE : ");
		for (Domino i : game.getAvailableDominos())
	    {
	    	System.out.print(i.getIndex() + " ");
	    }
	    System.out.println();
	    System.out.println("JOUEURS : ");
	    for(Player i : game.getPlayers())
	    {
	    	System.out.println("           " + i.getName() + " : " + i.showHand());
	    }
	    System.out.print("PIOCHE : ");
	    for(Domino i : game.getChoice())
	    {
	    	System.out.print(i.getIndex() + " ");
	    }
	    System.out.println();
	    System.out.print("ANCIENNE PIOCHE : ");
	    for(Domino i : game.getOldChoice())
	    {
	    	System.out.print(i.getIndex() + " ");
	    }
	    System.out.println();
	}
	
}
