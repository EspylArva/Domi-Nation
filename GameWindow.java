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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
	
	//constructor
	public GameWindow() {
		//Code JFrame base
		this.setTitle("DomiNations"); //titre
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(fullScreen); //taille de la fenetre
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quitter le programme quand croix
		this.setLocationRelativeTo(null); //centrer
		//fin Code JFrame
		
		//Onglet jeu =====================================================================
		JPanel game = new BackgroundPanel();
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
		
		ArrayList<String> listName = new ArrayList<String>(Arrays.asList(utils.name1,utils.name2,utils.name3,utils.name4));
		ArrayList<TerrainGraphic> listOfTerrain = new ArrayList<TerrainGraphic>();
		for (int i = 0; i < utils.numbPlayers; i++) {
			TerrainGraphic terrain = new TerrainGraphic(listName.get(i));
			listOfTerrain.add(terrain);
			ongletTerrains.add(terrain, listOngletTerrains[i]);
//			game.add(terrain);
//			terrain.setBounds((int) (fullScreen.getWidth()/2)-250, ((int)fullScreen.getHeight()/2)-250, 500, 500);
		}
		game.add(ongletTerrains);
		ongletTerrains.setBounds((int) (fullScreen.getWidth()/2)-250, ((int)fullScreen.getHeight()/2)-250, 500, 500);
		
		listOfTerrain.get(1).getComponent(5).setBackground(Color.blue); //methode de la magie
		
		//pioche -------------------------------------------------------------------------
		game.add(pioche);
		pioche.setBounds((int) (3*fullScreen.getWidth()/4), (int)fullScreen.getHeight()/2-pioche.getHeight(), pioche.getWidth(), pioche.getHeight());
		
		//Affichage des points -----------------------------------------------------------
		
		//Labels -------------------------------------------------------------------------
		
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
	 * JPanel + image de fond
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
	 * @param arrayListDomino
	 */
	public void dispPioche(ArrayList<Domino> arrayListDomino) {
		//arraylist posistion de la pioche en cours dans le gridLayout
		ArrayList<Integer> listPosPioche = new ArrayList<>(Arrays.asList(0,1,5,6,10,11,15,16));
		int compteur = 0;
		for (int i = 0; i < arrayListDomino.size(); i++) {
			drawPiocheCell(listPosPioche.get(compteur), arrayListDomino.get(i).getCell1());
			compteur ++;
			drawPiocheCell(listPosPioche.get(compteur), arrayListDomino.get(i).getCell2());
			compteur ++;
		}
		
	}
	
	//-----------------------------------------------------------------------
	
	public void drawPiocheCell(int pos, Cell cell) {
		pioche.getComponent(pos).setBackground(terrainColor(cell.getTerrainType()));
		//System.out.println(pioche.getComponent(pos));
		pioche.getComponent(pos);
		Icon i = new ImageIcon("src/image/1crown.png");
		//pioche.getComponent(pos)).paintComponent(i);
		//pioche.getComponent(pos).pain
		//pioche.setCompo
	}
	
	//-----------------------------------------------------------------------
	
	/**
	 * @author Batelier
	 * @param str
	 * @return Color de la cell
	 */
	public Color terrainColor(String str) {
		if (str.equals("Champs")) {return Color.orange;}
		else if (str.equals("Prairie")) {return Color.yellow;}
		else if (str.equals("Mer")) {return Color.blue;}
		else if (str.equals("Montagne")) {return Color.black;}
		else if (str.equals("Mine")) {return Color.gray;}
		else if (str.equals("Foret")) {return Color.green;}
		return null;
		
	}
	
	//-----------------------------------------------------------------------
	
}//GameWindow class



