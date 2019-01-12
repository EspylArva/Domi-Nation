import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

/**
 * Fenetre ingame
 * CardLayout contient jeu et consultation de royaumes
 * @author Batelier
 *
 */

public class GameWindow extends JFrame {
	//variables 
	private BackgroundMenuPanel fond = new BackgroundMenuPanel();
	private JButton btnRotate = new JButton("Rotate");
	private int cellSize = 50; //taille d'une cellule
	Dimension cellDim = new Dimension(cellSize, cellSize);
	Dimension terrainDimension = new Dimension(9*cellSize,9*cellSize);
	//CardLayout game + royaumes
	CardLayout onglet = new CardLayout();
	JPanel ongletPanel = new JPanel(); //Le JPanel principal
	String[] listOnglet = {"Game", "Voir les royaumes"};
	//CardLayout des terrains
	CardLayout ongletLayoutTerrains = new CardLayout();
	JPanel ongletTerrains = new JPanel();
	String[] listOngletTerrains = {"1", "2", "3", "4"};
	
	//pioche
	private PiocheGraphic pioche = new PiocheGraphic();
	
	//utils
	private Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel game = new BackgroundPanel();
	
	//Label
	private JLabel playerTurnLabel = new JLabel();
	
	//InGame
	private JPanel panelDominoToPLay = new JPanel(); 
	private JLabel labelDominoToPlay = new JLabel("Domino à jouer :");
	
	//constructor
	public GameWindow(ArrayList<Player> listOfPLayer) {
		//Code JFrame base
		this.setTitle("DomiNations"); //titre
		
		this.setSize(fullScreen); //taille de la fenetre
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quitter le programme quand croix
		this.setLocationRelativeTo(null); //centrer
		//fin Code JFrame
		
		//Onglet jeu =====================================================================
		game.setLayout(null);
		//game.setBackground(Color.gray);
		JButton btnShowRoyaumes = new JButton("Consulter les royaumes");
		btnShowRoyaumes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onglet.show(ongletPanel, listOnglet[1]);
				
			}
		});
		game.add(btnShowRoyaumes);
		btnShowRoyaumes.setBounds(0, 0, 100, 20);  //a placer
		
		//Terrains -----------------------------------------------------------------------
		ongletTerrains.setLayout(ongletLayoutTerrains);
		
		//ArrayList<String> listName = new ArrayList<String>(Arrays.asList(utils.name1,utils.name2,utils.name3,utils.name4));
		ArrayList<TerrainGraphic> listOfTerrain = new ArrayList<TerrainGraphic>();
		for (int i = 0; i < utils.numbPlayers; i++) {
			TerrainGraphic terrain = new TerrainGraphic(listOfPLayer.get(i).getColor());
			listOfPLayer.get(i).setNumTerrainGraphic(i);
			listOfPLayer.get(i).setTerrainGraphic(terrain);
			listOfTerrain.add(terrain);
			ongletTerrains.add(terrain, listOngletTerrains[i]);
//			game.add(terrain);
//			terrain.setBounds((int) (fullScreen.getWidth()/2)-250, ((int)fullScreen.getHeight()/2)-250, 500, 500);
		}
		game.add(ongletTerrains);
		ongletTerrains.setBounds((int) (fullScreen.getWidth()/2)-250, ((int)fullScreen.getHeight()/2)-250, 500, 500);
		
		//listOfTerrain.get(1).getComponent(5).setBackground(Color.blue); //methode de la magie
		
		//pioche -------------------------------------------------------------------------
		game.add(pioche);
		pioche.setBounds((int) (3*fullScreen.getWidth()/4), (int)fullScreen.getHeight()/2-pioche.getHeight(), pioche.getWidth(), pioche.getHeight());
		
		//Affichage des points -----------------------------------------------------------
		
		//InGame Affichage ---------------------------------------------------------------
		panelDominoToPLay.setLayout(new GridLayout(1, 2));
		panelDominoToPLay.setBounds(pioche.getX() + 50, pioche.getY() + 300, 100, 50);
		panelDominoToPLay.setVisible(false);
		game.add(panelDominoToPLay);
		
		//Labels -------------------------------------------------------------------------
		     //police à augmenter
		JLabel labelNewPioche = new JLabel("Nouvelle pioche"); labelNewPioche.setFont(new Font("Bold", 1, 15));
		JLabel labelOldPioche = new JLabel("Ancienne pioche"); labelOldPioche.setFont(new Font("Bold", 1, 15));
		labelNewPioche.setBounds(pioche.getX(), pioche.getY() - 80, 200, 100);
		labelOldPioche.setBounds(pioche.getX() + 150, pioche.getY() - 80, 200, 100);
		playerTurnLabel.setBounds(labelNewPioche.getX(), labelNewPioche.getY()-120, 400, 200);
		labelDominoToPlay.setFont(new Font("Bold", 1, 15));
		labelDominoToPlay.setBounds(pioche.getX() + 50, pioche.getY() + 250, 250, 50);
		game.add(labelDominoToPlay);
		game.add(labelNewPioche);
		game.add(labelOldPioche);
		game.add(playerTurnLabel);
		
		//Onglet consultation des royaumes ===============================================
		JPanel royaumes = new JPanel();
		//royaumes.setBackground(Color.white);
		JButton btnReturnToGame = new JButton("Retour au jeu");
		btnReturnToGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onglet.show(ongletPanel, listOnglet[0]);
				
			}
		});
		royaumes.add(btnReturnToGame);
		
		//ajouter onglet au Panel principal ==============================================
		ongletPanel.setLayout(onglet);
		ongletPanel.add(game, listOnglet[0]);
		ongletPanel.add(royaumes, listOnglet[1]);
				
		//Ajout
		this.setContentPane(ongletPanel);
		this.setVisible(true);
		
	}//fin constructor

	//-----------------------------------------------------------------------
	
	/**
	 * JPanel background : image de fond
	 * @author Batelier
	 */
	class BackgroundPanel extends JPanel{
		public void paintComponent(Graphics g) {
			try {
				Image title = ImageIO.read(new File("src/image/titre.png"));
				Image fond = ImageIO.read(new File("src/image/fond_menu_transp.png"));
				g.drawImage(fond, 0, 0, this.getWidth(), this.getHeight(), this);
				g.drawImage(title, 100, 20, this);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
		}//paintComponent
	}
	
	//======================================================================= Pioche Graphic Method

	/**
	 * gère l'affichage de la pioche
	 * @author Batelier
	 * @param arrayListDomino --> les dominos de la pioche
	 * @param turnOrder --> l'ordre de jeu des Player
	 */
	public void dispPioche(ArrayList<Domino> arrayListDomino, ArrayList<Player> turnOrder) {
		//idée 3 :
		//changer list de Domino en list de Cell
		
		//QUE FAIT ON DE L'ARRAYLIST PLAYER (pour affichage des rois et ordre de jeu etc)
		
		
		ArrayList<Cell> listOfCell = new ArrayList<Cell>();
		for(Domino dom : arrayListDomino) {
			listOfCell.add(dom.getCell1());
			listOfCell.add(dom.getCell2());
		}
		
		pioche.updatePioche(arrayListDomino, listOfCell, turnOrder);	
	}
	
	/**
	 * Label Tour du joueur en cours
	 * @param playerTurn1
	 */
	public void showWhoseTurn(Player playerTurn1) {
		String txt = playerTurn1.getName();
		if (playerTurn1.getColor().equals("Bleu")) {
			playerTurnLabel.setForeground(Color.blue);
		} else if (playerTurn1.getColor().equals("Rouge")) {
			playerTurnLabel.setForeground(Color.red);
		} else if (playerTurn1.getColor().equals("Orange")) {
			playerTurnLabel.setForeground(Color.orange);
		} else if (playerTurn1.getColor().equals("Vert")) {
			playerTurnLabel.setForeground(Color.green);
		}
		playerTurnLabel.setText("Tour " + txt);
		playerTurnLabel.setFont(new Font("Bold", 1, 40));		
	}

	//======================================================================= Game Graphic Method
	
	/**
	 * Afficher le domino qui doit être posé ou skip
	 * @author Batelier
	 * @param listOfDomino
	 */
	public void showChosenDomino(ArrayList<Domino> listOfDomino) {
		panelDominoToPLay.removeAll();
		Domino domino = listOfDomino.get(0);
		PaintedGameButton btn1 = new PaintedGameButton(domino.getCell1(),0);
		PaintedGameButton btn2 = new PaintedGameButton(domino.getCell2(),0);
		panelDominoToPLay.add(btn1);
		panelDominoToPLay.add(btn2);
		panelDominoToPLay.setVisible(true);
		pioche.setVisible(false);
	}
	public void hideChosenDomino() {
		panelDominoToPLay.setVisible(false);
		pioche.setVisible(true);
	}
	
	
	
	/**
	 * Affiche l'onglet de cardlayout contenant le terrain du joueur en cours
	 * @author Batelier
	 * @param indexOfTerrain
	 */
	public void drawTerrainGraphic(int indexOfTerrain) {
		ongletLayoutTerrains.show(ongletTerrains, listOngletTerrains[indexOfTerrain]);
	}
	
	//=====================================================================
	//GETTERS SETTERS
	public PiocheGraphic getPioche() {
		return pioche;
	}

	public void setPioche(PiocheGraphic pioche) {
		this.pioche = pioche;
	}
	//=====================================================================
	
}//GameWindow class



