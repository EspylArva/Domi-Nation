import java.awt.Menu;
import java.io.IOException;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Timer;
import java.util.function.UnaryOperator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

public class main {
	
	public static void main(String[] args)
	{
		boolean zoneTest = false;
		if(zoneTest)
		{
			
//TODO: TESTER LES REGLES "Dynastie" ET "Harmonie"
            
            Player player = new Player("François");
//          Player player2 = new Player("Jean");
            Domino domino = new Domino(24, new Cell(3, "Forêt"), new Cell(0, "Mer"));
            player.addToHand(domino);

            KingdomMap map = new KingdomMap(9);
                
            System.out.println("Cell 1 : " + domino.getCells()[0].getTerrainType() + " " + domino.getCells()[0].getCrownNb()
                    + " | Cell 2 : " + domino.getCells()[1].getTerrainType()  + " " + domino.getCells()[1].getCrownNb());
            
            /* TYPES : Pairie, Forêt, Champs, Mines, Mer, Montagne*/
            
            map.swapCell(1, 3, new Cell(1,"Prairie"));

            map.swapCell(2, 3, new Cell(0,"Prairie"));
            map.swapCell(2, 4, new Cell(0,"Mer"));
            
            map.swapCell(3, 4, new Cell(2,"Mer"));
            map.swapCell(3, 5, new Cell(0,"Forêt"));
            
            map.swapCell(4, 2, new Cell(0,"Mer"));
            map.swapCell(4, 5, new Cell(1,"Forêt"));
            map.swapCell(4, 6, new Cell(2,"Forêt"));

            map.swapCell(5, 2, new Cell(0,"Mer"));
            map.swapCell(5, 3, new Cell(3,"Mer"));
            map.swapCell(5, 4, new Cell(0,"Mer"));
            map.swapCell(5, 5, new Cell(0,"Forêt"));
            
            map.swapCell(3, 3, new Cell(3,"Forêt"));
            map.swapCell(4, 3, new Cell(0,"Mer"));
            
//          map.swapCell(6, 4, new Cell(0,"Mer"));
//          map.swapCell(7, 4, new Cell(0,"Mer"));

//            player.setKingdomMap(map);

            System.out.println("Size : " + player.getKingdomMap().getRoughSize()[0] + "x" + player.getKingdomMap().getRoughSize()[1]);
            System.out.println("Score : " + player.getKingdomMap().returnScore());

            dispTerrain(player.getKingdomMap());
            
            System.out.println("*************");
            
            ArrayList<Move> moves = player.getPossibleMove(domino, player.getKingdomMap());
            
            for(Move move : moves)
            {
                System.out.println("Cell1 : " + move.getPos1()[0] + ";" + move.getPos1()[1] + " | Cell2 : " + + move.getPos2()[0] + ";" + move.getPos2()[1]);
            }

            System.out.println("*************");
/*          KingdomMap newmap = player.returnFutureMap(moves.get(3));
            
            
            for(ArrayList<Cell> row : newmap.getTerrain())
            {
                for(Cell column : row)
                {
                    System.out.print(column.getTerrainType().substring(0, 1) + " ");
                }
                System.out.println();
            }//
            
            System.out.println(newmap.getRoughSize()[0] + "x" + newmap.getRoughSize()[1]);
            System.out.println(player.getKingdomMap().returnScore());

            System.out.println("*********************");
            */
            
            KingdomMap newMap = player.returnFutureMap(moves.get(1), player.getKingdomMap());
            dispTerrain(newMap);
            
            System.out.println("New Score : " + player.returnFutureMap(moves.get(1), player.getKingdomMap()).returnScore());

            
            HashMap <Move, Integer> gainPerMove = player.AI_scoreGainOnMove(moves, player);
            for(Entry<Move,Integer> entry : gainPerMove.entrySet())
            {
                Move move = entry.getKey();
                System.out.println(move.getPos1()[0] + ";" + move.getPos1()[1] + "x" +move.getPos2()[0] + ";" + move.getPos2()[1] + " | " + entry.getValue());
            }
        
            
            
            


		}else {

		//*/
//		Game Game.getInstance() = new Game();								// Jeu sur deux joueurs, créé : 
									//								1 deck de X * 12 dominos (ici 2*12 = 24)
																//								2 joueurs et leur distribue un domino, en l'enlevant du deck
																//								X rois (ici 4, car 2*2 ; on aurait pu avoir 3 ou 4)
		/**
		 * Block pour attendre que tous les paramètres requis soient
		 * bien rempli dans la fenêtre menu
		 */
		MenuWindow menu = new MenuWindow();
		while (!utils.play) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		menu.dispose(); //ferme le menu
		
		Game.getInstance().setChoice(Game.getInstance().distribDominos());							// On pioche X dominos et on les affiche : ici 4 rois, donc 4 dominos
		System.out.println("stop");
		ArrayList<Player> turnX = Game.getInstance().randomizeKings();       	// On mélange les rois pour distribuer les dominos aléatoirement

	    disp(Game.getInstance());
	    /*
	    //TODO ALERT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    for(Domino d : Game.getInstance().getAvailableDominos())
    	{
    		System.out.println("1: " + d.getCells()[0] +
    		" | 2: " + d.getCells()[1]);
    		System.out.println("INDEX: " + d.getIndex());
    	}
	    //TODO ALERT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    */
	    
	    // Affiche l'ordre du 1er tour
	    for(Player i : turnX) 	{System.out.print(i.getName() + " ");}  System.out.println();
// TOUR N°1
	    for (Player i : turnX) // On peut remplacer turnX par Game.getInstance().randomizeKings() SSI on enlève le display de l'ordre du 1er tour (sinon, deux objets)
	    {
	    	Domino pick = i.selectDomino(Game.getInstance().getChoice());
	    	
		    		
	    	i.addToHand(pick);									// Chaque joueur choisit un domino parmi la pioche
	    	Game.getInstance().getOldChoice().add(pick);								// On passe les dominos choisis dans la pioche des anciens dominos
	    	Game.getInstance().getChoice().remove(pick);								// On enleve les dominos choisis des options possibles
	    }														
	    
	    
	    
// TOUR N°X
	    while(!Game.getInstance().getAvailableDominos().isEmpty())
	    {
	    	Game.getInstance().setChoice(Game.getInstance().distribDominos());							// On repioche 3-4 dominos pour constituer une nouvelle pioche
	    
		    disp(Game.getInstance());
		    
		    // On doit récupérer l'id le plus faible parmi l'ancienne pioche et faire jouer le joueur en mettant son domino sur le terrain
	
		    for(Player player : Game.getInstance().getTurnOrder())								// Répétition d'un tour normal jusqu'à l'épuisement de la bibliothèque
		    {
		    	/*for(Domino d : player.getDominoInHands())
		    	{
		    		System.out.println("0: " + d.getCells()[0] +
		    		" | 1: " + d.getCells()[1]);
		    		System.out.println("0: " + d.getIndex());
		    	}*/
	
		    	System.out.print(player.getName() + " | " + player.showHand() + " | " +'\n');
		    	// display de la main du joueur
		    	
		    	ArrayList<Move> moves = new ArrayList<Move>();
		    	for(Domino domino : player.getDominoInHands())
		    	{	
		    		moves.addAll(player.getPossibleMove(domino, player.getKingdomMap()));	    		
		    	}
		    	
		    	for(Move move : moves){System.out.println("Cell1 : " + move.getPos1()[0] + ";" + move.getPos1()[1] + " | Cell2 : " + + move.getPos2()[0] + ";" + move.getPos2()[1]);}
		    	// display du movepool
		    	
		    	System.out.println(player.getName() + ", choisissez un movement :");
		    	Scanner scan = new Scanner(System.in);
		    	int i = scan.nextInt();
		    	Move move = moves.get(i);														// choix du move
		    	
		    	player.setKingdomMap(player.returnFutureMap(move, player.getKingdomMap()));		// joue le domino/move sur le terrain
		    	player.getDominoInHands().remove(move.getDomino());				// Enlève le domino après l'avoir joué
		    	
		    	Domino pick = player.selectDomino(Game.getInstance().getChoice());
			    player.addToHand(pick);
			    Game.getInstance().getOldChoice().add(pick);
			    Game.getInstance().getChoice().remove(pick);
		    	System.out.println();
		    }
		    
		    disp(Game.getInstance());
	    }// Fin du while(!Game.getInstance().getAvailableDominos.isEmpty())		<-- fin du : tant que la bibliotheque n'est pas vide
	    
	    // Get winner ?
	    if(Game.getInstance().returnWinner() != null)
	    {
	    	System.out.println("Gagnant du jeu : " + Game.getInstance().returnWinner());
		}
		else
		{
			System.out.println("Partie nulle !");
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
		for (Domino i : Game.getInstance().getAvailableDominos())
	    {
	    	System.out.print(i.getIndex() + " ");
	    }
	    System.out.println();
	    System.out.println("JOUEURS : ");
	    for(Player i : Game.getInstance().getPlayers())
	    {
	    	System.out.println('\t' + i.getName() +'\t' + " : " + i.showHand());
	    }
	    System.out.print("PIOCHE : \n");
	    for(Domino i : Game.getInstance().getChoice())
	    {
//	    	System.out.print(i.getIndex() + " ");
	    	Game.getInstance().dominoParser(i.getIndex());
	    }
	    System.out.println();
	    System.out.print("ANCIENNE PIOCHE : ");
	    for(Domino i : Game.getInstance().getOldChoice())
	    {
	    	System.out.print(i.getIndex() + " ");
	    }
	    System.out.println();
	}
	
	public static void dispTerrain(KingdomMap map)
	{
		for(ArrayList<Cell> row : map.getTerrain())
		{
			for(Cell column : row)
			{
				System.out.print(column.getTerrainType().substring(0, 1) + " ");
			}
			System.out.println();
		}
	}
	
}
