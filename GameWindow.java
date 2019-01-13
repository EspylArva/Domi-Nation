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
	ArrayList<Player> listPlayer = new ArrayList<Player>();
	
	//Label
	private JLabel playerTurnLabel = new JLabel();
	
	//InGame
	private JPanel panelDominoToPLay = new JPanel(); 
	private JLabel labelDominoToPlay = new JLabel("Domino à jouer :");
	private JButton btnDefausse = new JButton("Défausse");
	
	//affichage des points
	private ArrayList<Integer> listPointsJoueurs = new ArrayList<Integer>(); //points des joueurs
	JLabel labelScore = new JLabel();
	ArrayList<JLabel> listLabelScore = new ArrayList<JLabel>();
	
	//constructor
	public GameWindow(ArrayList<Player> listOfPLayer) {
		//Code JFrame base
		listPlayer = listOfPLayer;
		this.setTitle("DomiNations"); //titre
		
		this.setSize(fullScreen); //taille de la fenetre
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quitter le programme quand croix
		this.setLocationRelativeTo(null); //centrer
		//fin Code JFrame
		
		//Onglet jeu =====================================================================
		ongletTerrains.setBounds((int) (fullScreen.getWidth()/2)-300, ((int)fullScreen.getHeight()/2)-250, 500, 500);

		game.setLayout(null);
		//game.setBackground(Color.gray);
//		JButton btnShowRoyaumes = new JButton("Consulter les royaumes");
//		btnShowRoyaumes.setBounds(ongletTerrains.getX()-220, ongletTerrains.getY()+50, 200, 25);
//		btnShowRoyaumes.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				onglet.show(ongletPanel, listOnglet[1]);
//				
//			}
//		});
//		game.add(btnShowRoyaumes);
		
		//Terrains -----------------------------------------------------------------------
		ongletTerrains.setLayout(ongletLayoutTerrains);
		
		//ArrayList<String> listName = new ArrayList<String>(Arrays.asList(utils.name1,utils.name2,utils.name3,utils.name4));
		ArrayList<TerrainGraphic> listOfTerrain = new ArrayList<TerrainGraphic>();
		for (int i = 0; i < utils.numbPlayers; i++) {
			TerrainGraphic terrain = new TerrainGraphic(listOfPLayer.get(i).getColor());
			listPointsJoueurs.add(0);
			listLabelScore.add(new JLabel());
			listOfPLayer.get(i).setNumTerrainGraphic(i);
			listOfPLayer.get(i).setTerrainGraphic(terrain);
			listOfTerrain.add(terrain);
			ongletTerrains.add(terrain, listOngletTerrains[i]);
//			game.add(terrain);
//			terrain.setBounds((int) (fullScreen.getWidth()/2)-250, ((int)fullScreen.getHeight()/2)-250, 500, 500);
		}
		game.add(ongletTerrains);
		
		//listOfTerrain.get(1).getComponent(5).setBackground(Color.blue); //methode de la magie
		
		//pioche -------------------------------------------------------------------------
		game.add(pioche);
		pioche.setBounds(ongletTerrains.getX()+550,  ongletTerrains.getY()+100, pioche.getWidth(), pioche.getHeight());
		
		//Affichage des points -----------------------------------------------------------
		JPanel panelScore = new JPanel();
		int scoreSize = 50;
		for (int i =0; i<utils.numbPlayers; i++) {
			scoreSize+=30;
		}
		
		panelScore.setBounds(ongletTerrains.getX() - 230, ongletTerrains.getY()+100, 220, scoreSize);
		game.add(panelScore);
		labelScore.setFont(new Font("Bold", 1, 25));
		labelScore.setText("Score :");
		labelScore.setBounds(panelScore.getX()+5, panelScore.getY(), 150, 50);
		labelScore.setVisible(true);
		panelScore.add(labelScore);
		int compteurPosScore = 50;
		for (JLabel label : listLabelScore) {
			label.setBounds(labelScore.getX(), labelScore.getY()+compteurPosScore, 200, 50);
			compteurPosScore += 50;
			panelScore.add(label);
		}
		
		
		//InGame Affichage ---------------------------------------------------------------
		panelDominoToPLay.setLayout(new GridLayout(1, 2));
		panelDominoToPLay.setBounds(pioche.getX() + 50, pioche.getY() + 300, 100, 50);
		panelDominoToPLay.setVisible(false);
		btnDefausse.setBounds(panelDominoToPLay.getX()+150, panelDominoToPLay.getY(), 100, 50);
		btnDefausse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				utils.skipTurn = true;
				utils.choiceMoveDone = true;
				System.out.println("SKIPPED");
			}
		});
		game.add(btnDefausse); btnDefausse.setVisible(false);
		game.add(panelDominoToPLay);
		
		//Labels -------------------------------------------------------------------------
		     //police à augmenter
		JLabel labelNewPioche = new JLabel("Nouvelle pioche"); labelNewPioche.setFont(new Font("Bold", 1, 15));
		JLabel labelOldPioche = new JLabel("Ancienne pioche"); labelOldPioche.setFont(new Font("Bold", 1, 15));
		labelNewPioche.setBounds(pioche.getX(), pioche.getY() - 80, 200, 100);
		labelOldPioche.setBounds(pioche.getX() + 150, pioche.getY() - 80, 200, 100);
		getPlayerTurnLabel().setBounds(labelNewPioche.getX(), labelNewPioche.getY()-120, 400, 200);
		labelDominoToPlay.setFont(new Font("Bold", 1, 15));
		labelDominoToPlay.setBounds(pioche.getX() + 50, pioche.getY() + 250, 250, 50);
		game.add(labelDominoToPlay);
		game.add(labelNewPioche);
		game.add(labelOldPioche);
		game.add(getPlayerTurnLabel());
		
		//Onglet consultation des royaumes ===============================================
		BackgroundPanel royaumes = new BackgroundPanel();
		//royaumes.setBackground(Color.white);
		
		//Onglet consultation royaumes
		JButton btnReturnToGame = new JButton("Retour au jeu");
		btnReturnToGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onglet.show(ongletPanel, listOnglet[0]);
				
			}
		});
		royaumes.add(btnReturnToGame);
		ArrayList<JButton> buttonListConsultation = new ArrayList<JButton>();
		int compteurPlacementBtnJ = 0;
		int fix2playerBug = 0; //fixing bug : ça affiche 2 boutons pour chacun des joueurs en 2 player sinon
		for (Player player : listPlayer) {
			JButton btn = new JButton(player.getName());
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ongletLayoutTerrains.show(ongletTerrains, listOngletTerrains[player.getNumTerrainGraphic()]);
					for (JButton btns : buttonListConsultation) {
						btns.setBackground(null);
					}
					//btn.setBackground(Color.LIGHT_GRAY);
					
				}
			});
			btn.setBounds(ongletTerrains.getX() + compteurPlacementBtnJ, ongletTerrains.getY()-30, 100, 25);
			if (player.getColor().equals("Bleu")) {
				btn.setForeground(Color.blue);
			} else if (player.getColor().equals("Rouge")) {
				btn.setForeground(Color.red);
			} else if (player.getColor().equals("Orange")) {
				btn.setForeground(Color.orange);
			} else if (player.getColor().equals("Vert")) {
				btn.setForeground(Color.green);
			}
			compteurPlacementBtnJ += 110;
			buttonListConsultation.add(btn);
			game.add(btn);
			fix2playerBug++;
			if ((utils.numbPlayers == 2) && (fix2playerBug == 2)) {
				break;
			}
		}
		
		
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
			getPlayerTurnLabel().setForeground(Color.blue);
		} else if (playerTurn1.getColor().equals("Rouge")) {
			getPlayerTurnLabel().setForeground(Color.red);
		} else if (playerTurn1.getColor().equals("Orange")) {
			getPlayerTurnLabel().setForeground(Color.orange);
		} else if (playerTurn1.getColor().equals("Vert")) {
			getPlayerTurnLabel().setForeground(Color.green);
		}
		getPlayerTurnLabel().setText("Tour " + txt);
		getPlayerTurnLabel().setFont(new Font("Bold", 1, 40));		
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
		btnDefausse.setVisible(true);
	}
	public void hideChosenDomino() {
		panelDominoToPLay.setVisible(false);
		pioche.setVisible(true);
		btnDefausse.setVisible(false);
	}
	
	
	
	/**
	 * Affiche l'onglet de cardlayout contenant le terrain du joueur en cours
	 * @author Batelier
	 * @param indexOfTerrain
	 */
	public void drawTerrainGraphic(int indexOfTerrain) {
		ongletLayoutTerrains.show(ongletTerrains, listOngletTerrains[indexOfTerrain]);
	}
	
	/**
	 * Update l'affichage des scores
	 * @author Batelier
	 */
	public void updateScores() {
		int compteurPlayer = 0;
		String txt = "";
		for (JLabel label : listLabelScore) {
			if (listPlayer.get(compteurPlayer).getColor().equals("Bleu")) {
				label.setForeground(Color.blue);
			} else if (listPlayer.get(compteurPlayer).getColor().equals("Rouge")) {
				label.setForeground(Color.red);
			} else if (listPlayer.get(compteurPlayer).getColor().equals("Orange")) {
				label.setForeground(Color.orange);
			} else if (listPlayer.get(compteurPlayer).getColor().equals("Vert")) {
				label.setForeground(Color.green);
			}
			
			label.setFont(new Font("Bold", 1, 20));
			txt = listPlayer.get(compteurPlayer).getName() + " : " + listPlayer.get(compteurPlayer).getPoints()+" point(s)";
			label.setText(txt);
			compteurPlayer++;
		}
	}
	
	public void setPlayerTurnLabelEnd() {
		playerTurnLabel.setFont(new Font("Bold", 1, 40));
		playerTurnLabel.setForeground(Color.black);
		playerTurnLabel.setText("Partie terminée");
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

	public JLabel getPlayerTurnLabel() {
		return playerTurnLabel;
	}
	
}//GameWindow class



