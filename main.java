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

import javax.swing.JButton;
import javax.swing.JComponent;
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
            
            Player player = new Player("François", "blue");
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
        //////////////////////////////////////////////////////////// //
		/**
		 * Block pour attendre que tous les paramètres requis soient  //
		 * bien rempli dans la fenêtre menu
		 * @author Batelier
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
		
		 
		////////////////////////////////////////////////////////////
		
		Game.getInstance().setChoice(Game.getInstance().distribDominos());							// On pioche X dominos et on les affiche : ici 4 rois, donc 4 dominos
		System.out.println("stop");
		ArrayList<Player> turnX = Game.getInstance().randomizeKings();       	// On mélange les rois pour distribuer les dominos aléatoirement

		GameWindow gameGraphic = new GameWindow(turnX);///  gameGraphic 
		
		for(Player player : turnX){
			//player.getTerrainGraphic().drawTerrainGraphic();
		}
		
	    disp(Game.getInstance(), gameGraphic, turnX);
	    
	    //#################################### TOUR 1 ######################################
	    // Affiche l'ordre du 1er tour
	    for(Player i : turnX) 	{System.out.print(i.getName() + " ");}  System.out.println();
// TOUR N°1
	    for (Player playerTurn1 : turnX) // On peut remplacer turnX par Game.getInstance().randomizeKings() SSI on enlève le display de l'ordre du 1er tour (sinon, deux objets)
	    {
	    	
	    	//Graphic Update and display terrain----------------------------
	    	playerTurn1.getTerrainGraphic().updateTerrainGraphic(playerTurn1.getKingdomMap(), playerTurn1);
	    	gameGraphic.drawTerrainGraphic(playerTurn1.getNumTerrainGraphic());

	    	// Choix console dans la pioche
	    	//Domino pick = playerTurn1.selectDomino(Game.getInstance().getChoice());

	    	//graphique pioche----------------------------------------------
	    	gameGraphic.showWhoseTurn(playerTurn1);
	    	System.out.println("Choix domino " + playerTurn1.getName());
	    	while (!utils.choiceDone) {  //mettre une condition valable
				try {
					System.out.println("En attente...");
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} utils.choiceDone = false; //reset le choix
			Domino pick = utils.choiceDomino;

			//code gestion choix domino-------------------------------------
	    	Game.getInstance().addRefreshTurnOrder(pick, playerTurn1);
	    	playerTurn1.addToHand(pick);									// Chaque joueur choisit un domino parmi la pioche
	    	Game.getInstance().getOldChoice().add(pick);								// On passe les dominos choisis dans la pioche des anciens dominos
	    	Game.getInstance().getChoice().remove(pick);								// On enleve les dominos choisis des options possibles
	    	
	    	// display graphic main du joueur-------------------------------
	    	gameGraphic.showChosenDomino(playerTurn1.getDominoInHands());
	    	
	    	//code créer la liste de move possible--------------------------
	    	ArrayList<Move> moves = new ArrayList<Move>();
	    	for(Domino domino : playerTurn1.getDominoInHands())
	    	{	
	    		moves.addAll(playerTurn1.getPossibleMove(domino, playerTurn1.getKingdomMap()));	    		
	    	}

	    	//Choix graphique du move---------------------------------------
	    	Move move = utils.choiceMove;
	    	boolean moveAccepted = false;
	    	while (!moveAccepted) { //tant que le move n'est pas valide, réessayer
	    		while (!utils.choiceMoveDone) {  //mettre une condition valable
					try {
						System.out.println("Choix move...");
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
	    		//Verfification du move
	    		System.out.println("######## VERIFICATION ########");
	    		int terrainSize = playerTurn1.getKingdomMap().getTerrain().get(0).size();
	    		int x1 = utils.posCell1 % terrainSize ;
	    		int y1 = (int) Math.floorDiv(utils.posCell1 , terrainSize) ;
	    		int[] pos1 = new int[] {x1, y1};
	    		
	    		int x2 = utils.posCell2 % terrainSize ;
	    		int y2 = (int) Math.floorDiv(utils.posCell2 , terrainSize) ;
	    		int[] pos2 = new int[] {x2, y2};
	    		
	    		move = new Move(utils.choiceDomino, pos1, pos2);
	    		System.out.println(utils.choiceDomino.getCell1().getTerrainType() + " " + utils.choiceDomino.getCell2().getTerrainType());
	    		System.out.println("Cell1 : " + move.getPos1()[0] + " " + move.getPos1()[1]
	    				+" Cell 2 : "+ move.getPos2()[0] + " " + move.getPos2()[1]);
	    		
	    		System.out.println("Move possible : " + moves.contains(move));

	    		for (Move mv : moves) {
	    			if (mv.getPos1()[0] == move.getPos1()[0] && mv.getPos1()[1] == move.getPos1()[1]
	    					&& mv.getPos2()[0] == move.getPos2()[0] && mv.getPos2()[1] == move.getPos2()[1]) {
	    				System.out.println("MOVE ACCEPTED");
	    				moveAccepted = true;
	    			}
	    			
	    		}
		    	utils.choiceMoveDone = false; //reset le choix
		    	utils.posCell1 = -1; utils.posCell2 = -1;
	    	} moveAccepted = false;

	    	//Graphic cacher affichage du domino choisi une fois posé-------------
	    	gameGraphic.hideChosenDomino();
	    	
	    	//console ajouter domino à la map-------------------------------------
	    	playerTurn1.setKingdomMap(playerTurn1.returnFutureMap(move, playerTurn1.getKingdomMap()));		// joue le domino/move sur le terrain
	    	playerTurn1.getDominoInHands().remove(move.getDomino());				// Enlève le domino de la main après l'avoir joué
	    	
	    }														
	    	    
	    
// TOUR N°X
	  //#################################### TOUR X ######################################
	    while(!Game.getInstance().getAvailableDominos().isEmpty())
	    {

	    	//System.out.println(Game.getInstance().getTurnOrder());
	    	Game.getInstance().setChoice(Game.getInstance().distribDominos());							// On repioche 3-4 dominos pour constituer une nouvelle pioche
		    
	    	disp(Game.getInstance(), gameGraphic, Game.getInstance().generateOrder(Game.getInstance().getTurnOrder()));
	    	
		    //HashMap Temporaire
		    HashMap<Integer, Player> hashTemporaire = new HashMap<Integer, Player>();
		    // On doit récupérer l'id le plus faible parmi l'ancienne pioche et faire jouer le joueur en mettant son domino sur le terrain
	
		    //changer 
		    //for(Player player : Game.getInstance().getTurnOrder())								// Répétition d'un tour normal jusqu'à l'épuisement de la bibliothèque
		    for(Player i : Game.getInstance().generateOrder(Game.getInstance().getTurnOrder())) 	{System.out.print(i.getName() + " ");}  System.out.println();
		    
		    for(Player player : Game.getInstance().generateOrder(Game.getInstance().getTurnOrder()))
		    {
		    	//Graphic Update and display terrain----------------------------
		    	player.getTerrainGraphic().updateTerrainGraphic(player.getKingdomMap(), player);
		    	gameGraphic.drawTerrainGraphic(player.getNumTerrainGraphic());

		    	// Choix console dans la pioche
		    	//Domino pick = playerTurn1.selectDomino(Game.getInstance().getChoice());

		    	//graphique pioche----------------------------------------------
		    	gameGraphic.showWhoseTurn(player);
		    	System.out.println("Choix domino " + player.getName());
		    	while (!utils.choiceDone) {  //mettre une condition valable
					try {
						System.out.println("En attente...");
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} utils.choiceDone = false; //reset le choix
				Domino pick = utils.choiceDomino;

				//code gestion choix domino-------------------------------------
				hashTemporaire.put(pick.getIndex(), player);
		    	Game.getInstance().addRefreshTurnOrder(pick, player);
		    	player.addToHand(pick);									// Chaque joueur choisit un domino parmi la pioche
		    	Game.getInstance().getOldChoice().add(pick);								// On passe les dominos choisis dans la pioche des anciens dominos
		    	Game.getInstance().getChoice().remove(pick);								// On enleve les dominos choisis des options possibles
		    	
		    	// display graphic main du joueur-------------------------------
		    	gameGraphic.showChosenDomino(player.getDominoInHands());
		    	
		    	//code créer la liste de move possible--------------------------
		    	ArrayList<Move> moves = new ArrayList<Move>();
		    	for(Domino domino : player.getDominoInHands())
		    	{	
		    		moves.addAll(player.getPossibleMove(domino, player.getKingdomMap()));	    		
		    	}

		    	//Choix graphique du move---------------------------------------
		    	Move move = utils.choiceMove;
		    	boolean moveAccepted = false;
		    	while (!moveAccepted) { //tant que le move n'est pas valide, réessayer
		    		while (!utils.choiceMoveDone) {  //mettre une condition valable
						try {
							System.out.println("Choix move...");
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
		    		//Verfification du move
		    		System.out.println("######## VERIFICATION ########");
		    		int terrainSize = player.getKingdomMap().getTerrain().get(0).size();
		    		int x1 = utils.posCell1 % terrainSize ;
		    		int y1 = (int) Math.floorDiv(utils.posCell1 , terrainSize) ;
		    		int[] pos1 = new int[] {x1, y1};
		    		
		    		int x2 = utils.posCell2 % terrainSize ;
		    		int y2 = (int) Math.floorDiv(utils.posCell2 , terrainSize) ;
		    		int[] pos2 = new int[] {x2, y2};
		    		
		    		move = new Move(utils.choiceDomino, pos1, pos2);
		    		System.out.println(utils.choiceDomino.getCell1().getTerrainType() + " " + utils.choiceDomino.getCell2().getTerrainType());
		    		System.out.println("Cell1 : " + move.getPos1()[0] + " " + move.getPos1()[1]
		    				+" Cell 2 : "+ move.getPos2()[0] + " " + move.getPos2()[1]);

		    		for (Move mv : moves) {
		    			if (mv.getPos1()[0] == move.getPos1()[0] && mv.getPos1()[1] == move.getPos1()[1]
		    					&& mv.getPos2()[0] == move.getPos2()[0] && mv.getPos2()[1] == move.getPos2()[1]) {
		    				System.out.println("MOVE ACCEPTED");
		    				moveAccepted = true;
		    			}
		    			
		    		}
			    	utils.choiceMoveDone = false; //reset le choix
			    	utils.posCell1 = -1; utils.posCell2 = -1;
		    	} moveAccepted = false;

		    	//Graphic cacher affichage du domino choisi une fois posé-------------
		    	gameGraphic.hideChosenDomino();
		    	
		    	//console ajouter domino à la map-------------------------------------
		    	player.setKingdomMap(player.returnFutureMap(move, player.getKingdomMap()));		// joue le domino/move sur le terrain
		    	player.getDominoInHands().remove(move.getDomino());				// Enlève le domino de la main après l'avoir joué
		    	
		    	
		    }
		    
		    Game.getInstance().setTurnOrder(hashTemporaire);
		    
		    //disp(Game.getInstance(), gameGraphic, Game.getInstance().getTurnOrder());
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

	public static void disp(Game game, GameWindow gameGraphic, ArrayList<Player> playerOrder) //, ArrayList<Player> turnOrder
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
	    
	    //////////////////////////////////////////////////////////////////////////
	    //Affichage graphique de la pioche + rois
	    ArrayList<Domino> piocheDomino = new ArrayList<Domino>();
	    //tri ordre dominos pour affichage graphique
	    ArrayList<Integer> indexDominoSort = new ArrayList<Integer>();
	    for(Domino i : Game.getInstance().getChoice())
	    {
	    	indexDominoSort.add(i.getIndex());
	    }
	    indexDominoSort.sort(null);
	    for(int i : indexDominoSort) {
	    	piocheDomino.add(Game.getInstance().dominoParser(i));
	    }
	    gameGraphic.dispPioche(piocheDomino, playerOrder); //methode affichage pioche   //Avec tri
	    //gameGraphic.dispPioche(Game.getInstance().getChoice(), playerOrder); //sans tri
	    piocheDomino.clear();
	    //fin affichage graphique de la pioche
	    //////////////////////////////////////////////////////////////////////////
	    
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
