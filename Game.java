import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;



public final class Game
{


	//////////////////////////////////////////////////////////////
	// 						VARIABLES							//
	//////////////////////////////////////////////////////////////
	private ArrayList<Domino> availableDominos = new ArrayList<Domino>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Domino> choice = new ArrayList<Domino>();
	private ArrayList<Domino> oldChoice = new ArrayList<Domino>();
	private int numKing;

	//////////////////////////////////////////////////////////////
	// 						 METHODS							//
	//////////////////////////////////////////////////////////////
	/**
	 * Allows the game to define a precise number of players. Defining a player
	 * is possible through defining its name.
	 * <p>
	 * @param numberOfPlayers		Precise number of players to instanciate.
	 * @author 						Tchong-Kite HUAM
	 * @date						Last updated on 10.12.2018
	 */
	private void addPlayer(int numberOfPlayers) {			// Ajoute des joueurs en leur demandant un nom
		Scanner scan = new Scanner(System.in);
		for(int i=0 ; i<numberOfPlayers ; i++)
		{
			System.out.println("Entrez un nom de joueur : ");
			String name = scan.nextLine();
//			scan.next();
			Player player = new Player(name);
			this.players.add(player);
		}
	}

	/**
	 * Adds a precise number of dominos to the library. The number of dominos
	 * to add to the library depends on the number of players.
	 * <p>
	 * @param numberOfDominos		A library containing a set number of dominos.
	 * @author 						Baptiste ROBERJOT
	 * @date						Last updated on 21.12.2018
	 */
	private void generateDominos(int numberOfDominos)		// Génère X dominos
	{		
		ArrayList<Integer> list48Dominos = new ArrayList<Integer>();
		for(int i=1 ; i<=48 ; i++){
			list48Dominos.add(i);
		}//end for
		Collections.shuffle(list48Dominos);
		ArrayList<Integer> listSorted = new ArrayList<Integer>();
		//ArrayList<Domino> listDomino = new ArrayList<Domino>();
		for (int i = 1; i<= numberOfDominos; i++) {
			listSorted.add(list48Dominos.get(i));
		}
		listSorted.sort(null);
		for (int i : listSorted) {
			this.availableDominos.add(new Domino(i));
		}
		
	}//end method


	/**
	 * Method to draw a set number a set number of dominos.
	 * <p>
	 * According to the number of players, draws a certain amount of dominos
	 * depending from the number of players from the library and adds them to 
	 * the drawing pile.
	 * <p>
	 * @return			Drawing pile containing a certain amount of dominos.
	 * @author 			Tchong-Kite HUAM
	 * @date			Last updated on 10.12.2018
	 */
	public ArrayList<Domino> popDomino()		// pops X dominos (selon le nombre de rois : (4 - 3 - 4) rois pour (2 - 3 - 4) joueurs
	{
		ArrayList<Domino> pop = new ArrayList<Domino>();
		for(int i = 0 ; i<this.getNumKings() ; i++)
		{			
			int randomIndex = (int)(Math.random() * this.availableDominos.size()) ;
			pop.add(this.availableDominos.get(randomIndex));
			this.availableDominos.remove(randomIndex);
		}
		return pop;
	}
	
	/**
	 * Randomize the order of players for the very first turn.
	 * <p>
	 * @return		Randomized array of players for the first turn.
	 * @author 		Tchong-Kite HUAM
	 * @date		Last updated on 10.12.2018
	 */
	public ArrayList<Player> randomizeKings()							// Renvoie l'ordre du 1er tour (aléatoire)
	{
		ArrayList<Player> randomKingArray = new ArrayList<Player>();
		ArrayList<Player> tmp = new ArrayList<Player>();
		if(this.players.size() == 2)
		{
			tmp.addAll(players);
		}
		for(Player i : players)
		{
			tmp.add(i);
		}
		
		for(int i=0 ; i<this.numKing ; i++)
		{
			int rndIndex = (int)(Math.random() * tmp.size());
			randomKingArray.add(tmp.get(rndIndex));
			tmp.remove(rndIndex);
		}
		return randomKingArray;
	}
	
	/**
	 * Update the drawing pile by checking the library content.
	 * <p>
	 * Calls the drawing method if the library still has enough dominos;
	 * otherwise, returns an error message.
	 * <p>
	 * @return			New drawing pile if existant; message error otherwise.
	 * @author 			Tchong-Kite HUAM
	 * @date			Last updated on 10.12.2018
	 */
	public ArrayList<Domino> distribDominos()
	{
	    if(!availableDominos.isEmpty())            // Tant qu'il reste des dominos dans la pile, il faut les distribuer
	    {
	        ArrayList<Domino> availableDominos = popDomino();   // On pioche autant de dominos qu'il y a de rois
	        return availableDominos;				// TODO Return les faces cachées
	    }
	    else                // plus de pioche !
	    {
	        System.out.println("Plus de pioche !");
	        return null;
	    }
	}
	
	/**
	 * Returns the order of the players accordingly to last turn domino picks.
	 * <p>
	 * According to the rules, the lower index domino holder will play first
	 * next turn. This function returns the order for the upcoming turn by
	 * observing last turn domino picks.
	 * <p>
	 * @return			Order of the players for the upcoming turn.
	 * @author 			Tchong-Kite HUAM
	 * @date			Last updated on 10.12.2018
	 */
	public ArrayList<Player> getTurnOrder()
	{
	    ArrayList<Domino> temp = new ArrayList<Domino>();
	    for(int i=1 ; i<numKing+1 ; i++)
	    {
	    	temp.add(oldChoice.get(oldChoice.size()-i));
	    }
	    
	    Collections.sort(temp, new Comparator<Domino>()
	    		{
	    			public int compare(Domino domino1, Domino domino2)
	    			{
	    				return domino1.getIndex() - domino2.getIndex();
	    			}
	    		});
	    ArrayList<Player> turnOrder = new ArrayList<Player>();
	    
	    for(Domino i : temp)
	    {
	    	for(Player player : players)
	    	{
	    		ArrayList<Integer> listOfDominoIndex = new ArrayList<Integer>();
	    		for(Domino hand : player.getDominoInHands())
	    		{
	    			listOfDominoIndex.add(hand.getIndex());
	    		}
	    		if( listOfDominoIndex.contains(i.getIndex()) )
		    	{
		    		turnOrder.add(player);
		    	}
	    	}
	    }
		return turnOrder;
	}
	
	/* Bien mettre le fichier csv dans le projet (src)
	 * Renvoit Domnino contenant : index cell1 cell2
	 */
	public Domino dominoParser(int index){
		//Domino contenant index, cell1, cell2
		index++; //Le fichier csv contient une ligne useless
		String file = "src/dominos.csv"; //Chemin fichier csv
        String splitLine = "";			 //Character spliting line
        String splitValue = ",";		 //Character split values
        int compteur = 0;   //Principe a optimiser
        int crownNb1 = 0; int crownNb2 = 0;
        String terrainType1 = "void"; String terrainType2 = "void";
        Cell cell1 = new Cell(crownNb1, terrainType1); //Par defaut
        Cell cell2 = new Cell(crownNb2, terrainType2); //Par defaut
        Domino dominoParse = new Domino(index, cell1, cell2); //Par defaut
        
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(file))) {
        	
            while ((splitLine = bufferReader.readLine()) != null) {
            	compteur ++;
                // Separateur value
            	if (compteur == index) {
            		String[] domino = splitLine.split(splitValue);

                    //créer domino
                    crownNb1 = Integer.parseInt(domino[0]);
                    crownNb2 = Integer.parseInt(domino[2]);
                    terrainType1 = domino[1];
                    terrainType2 = domino[3];
                    cell1 = new Cell(crownNb1, terrainType1);
                    cell2 = new Cell(crownNb2, terrainType2);
                    dominoParse = new Domino(index, cell1, cell2);
                    
                    String indexFormated = "";
                    if((index-1) < 10)
                	{
                    	indexFormated += "0";
                	}
                    indexFormated += String.valueOf(index-1);
                    System.out.println("Domino n" + indexFormated + " : " + '\t'
                    		+ "Crown Nb1 : " + crownNb1+ " | Type terrain1 : "+ terrainType1  + '\t'
                    		+ " | Crown Nb2 : "+crownNb2+ " | Type terrain2 : "+terrainType2 );
                    // Domino n32 :
                    
                    return dominoParse;
            	}
            	if (compteur > 49) {System.out.println("404not found");return dominoParse;}

            } 

        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println("404 Not found");
		return dominoParse;	
	}
	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////
	
	/**
	 * Custom constructor for a Game instance.
	 * <p>
	 * @param numberOfPlayers		Number of players for a game instance.
	 */
	public Game(int numberOfPlayers) {		// TODO : mods (bonus) : default = no mode
		//TODO
		switch (numberOfPlayers)
		{
			case 2:
				setNumKing(4);
				break;
			case 3:
				setNumKing(3);
				break;
			case 4:
				setNumKing(4);
				break;
			default:
				System.out.println("Erreur");
				break;
		}
		generateDominos(12*numberOfPlayers);
		addPlayer(numberOfPlayers);
		/*
		for(Player player : this.getPlayers())					// Pour commencer avec 1 domino aléatoire
		 
		{
			int randomIndex = (int)(Math.random() * this.availableDominos.size());

			player.addToHand(this.availableDominos.get(randomIndex));
			this.availableDominos.remove(randomIndex);


		}//*/
	}
	

	
	//////////////////////////////////////////////////////////////
	// 						ACCESSOR							//
	//////////////////////////////////////////////////////////////
	public void setNumKing(int numKing)
	{
		this.numKing = numKing;
	}
	
	public int getNumKings() {
		return this.numKing;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public ArrayList<Domino> getAvailableDominos()
	{
		return availableDominos;
	}

	public void setAvailableDominos(ArrayList<Domino> availableDominos)
	{
		this.availableDominos = availableDominos;
	}

	public ArrayList<Domino> getChoice() {
		return choice;
	}

	public void setChoice(ArrayList<Domino> choice) {
		this.choice = choice;
	}

	public ArrayList<Domino> getOldChoice() {
		return oldChoice;
	}

	public void setOldChoice(ArrayList<Domino> oldChoice) {
		this.oldChoice = oldChoice;
	}

	/** Instance unique pré-initialisée */
    public static Game INSTANCE;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static Game getInstance()
    {
    	if(INSTANCE == null)
    	{
    		INSTANCE = new Game(2);
    	}
		return INSTANCE;    		

    }
}

	