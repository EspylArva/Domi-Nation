import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Game
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

	private void generateDominos(int numberOfDominos)		// Génère X dominos
	{														// A modifier : génération aléatoire ? Préconçue selon un XML ?
		for(int i=1 ; i<=numberOfDominos ; i++)
		{
			this.availableDominos.add(new Domino(i)); //TODO
		}
	}

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
	public ArrayList<Player> getTurnOrder()
	{
	    ArrayList<Domino> temp = new ArrayList<Domino>();
	    for(int i=1 ; i<5 ; i++)
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
	
	
	//////////////////////////////////////////////////////////////
	// 						CONSTRUCTOR							//
	//////////////////////////////////////////////////////////////
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
}
