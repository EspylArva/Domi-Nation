import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Objet graphique pioche
 * @author Batelier
 *
 */
public class PiocheGraphic extends JPanel{
	private int cellSize = 50;
	Dimension cellDim = new Dimension(cellSize, cellSize);
	private int nbPioche; //nb de domino à choisir par les joueurs
	private int width = 250;
	private int height = 200;
	private int vgap = 5;
	private boolean tour1 = true;
	private ArrayList<ImageIcon> oldPiocheImg = new ArrayList<ImageIcon>();
	
	private ArrayList<Cell> listOfOldCell = new ArrayList<Cell>();
	
	//Constructeur de base, représente des boutons vierges
	public PiocheGraphic() {
		
		if (utils.numbPlayers == 2 || utils.numbPlayers == 4) {
			nbPioche = 4;
		} else if (utils.numbPlayers == 3) {nbPioche = 3;}
		this.height = nbPioche*cellSize + nbPioche*vgap;
		
		GridLayout terrainLayout = new GridLayout(nbPioche, 5);
		terrainLayout.setVgap(vgap);
		this.setLayout(terrainLayout);
		this.setOpaque(false);
		for (int i = 0; i<nbPioche*5; i++) {
			JButton button = new JButton();
			//PaintedButton button = new PaintedButton();
			button.setPreferredSize(cellDim);
			this.add(button);
		}
		
		//désactiver case milieu
		for(int i = 2; i < nbPioche*5-1; i+=5) {
			this.getComponent(i).setVisible(false);
		}
		
	}//fin constructor 1
	
	//------------------------------------------------------------------
	//Method
	public void updatePioche(ArrayList<Cell> listOfNewCell, ArrayList<Player> turnOrder) {
		//supprimer ancien affichage
		this.setVisible(false);
		this.removeAll();
		
		//Update btn
		int compteur = 0;
		int compteurOld = 0;
		for (int i = 0; i<this.nbPioche; i++) {
			this.add(new PaintedButton(listOfNewCell.get(compteur), turnOrder.get(i), false)); compteur ++;
			this.add(new PaintedButton(listOfNewCell.get(compteur), turnOrder.get(i), true)); compteur ++;
			JButton btnVoid = new JButton(); btnVoid.setVisible(false);
			this.add(btnVoid);
			if (tour1) {
				this.add(new JButton());
				JButton btnDrawKing = new JButton();
				btnDrawKing.setIcon(colorKing(turnOrder.get(i)));
				this.add(btnDrawKing);
			}
			else {
				JButton btn = new JButton(); btn.setIcon(oldPiocheImg.get(compteurOld));
				compteurOld ++;
				JButton btn2 = new JButton(); btn2.setIcon(oldPiocheImg.get(compteurOld));
				compteurOld++;
				this.add(btn); 
				this.add(btn2);
				//this.add(new JButton("YO"));this.add(new JButton("YO"));
				
			}
		} 
		//afficher nouvel affichage
		this.setVisible(true);
		
		//Update oldListOfCell + tour suivant
		listOfOldCell = listOfNewCell;
		tour1 = false;
	}
	//Sub Class
	//------------------------------------------------------------------

	/**
	 * Create a JButton containing ImageIcon
	 * TODO : 
	 * img1, img2
	 * set img1, set img2
	 * à la fin : assembler img1 et img2 puis setIcon();
	 * @author Batelier
	 *
	 */
	class PaintedButton extends JButton{

		private ImageIcon imgfond = new ImageIcon();
		private ImageIcon imgCrwn = new ImageIcon();
		private ImageIcon imgFinale = new ImageIcon();
		
		public PaintedButton(Cell cell, Player player, Boolean drawKing) {
			switch (cell.getCrownNb()) {
			case 0:
				break;
			case 1:
				imgCrwn = new ImageIcon(new ImageIcon("src/image/1crown.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				break;
			case 2:
				imgCrwn = new ImageIcon(new ImageIcon("src/image/2crowns.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				break;
			case 3:
				imgCrwn = new ImageIcon(new ImageIcon("src/image/3crowns.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				break;
			}
			
			if (cell.getTerrainType().equals("Champs")) {
				imgfond = new ImageIcon(new ImageIcon("src/image/champs.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				}
			else if (cell.getTerrainType().equals("Prairie")) {
				imgfond = new ImageIcon(new ImageIcon("src/image/prairie.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				}
			else if (cell.getTerrainType().equals("Mer")) {
				imgfond = new ImageIcon(new ImageIcon("src/image/mer.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				}
			else if (cell.getTerrainType().equals("Montagne")) {
				imgfond = new ImageIcon(new ImageIcon("src/image/montagne.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				}
			else if (cell.getTerrainType().equals("Mine")) {
				imgfond = new ImageIcon(new ImageIcon("src/image/mine.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				}
			else if (cell.getTerrainType().equals("Foret")) {
				imgfond = new ImageIcon(new ImageIcon("src/image/foret.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
				}
			imgFinale = buildImageIcon(imgfond, imgCrwn);
//			if (drawKing && !tour1) {
//				imgFinale = buildImageIcon(imgFinale, colorKing(player));
//			}
			oldPiocheImg.add(imgFinale);
			this.setIcon(imgFinale);
			
		}//fon constructor
	}//fin classe

	//------------------------------------------------------------------
	//Methods
	/**
	 * Fusionne deux imageIcon ensemble
	 * @author Batelier
	 * @return {ImageIcon}
	 */
	public ImageIcon buildImageIcon(ImageIcon crown1, ImageIcon gifsea) {
		ImageIcon imgf = new ImageIcon();
		BufferedImage buf = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = buf.createGraphics();
		g.drawImage(crown1.getImage(), 0, 0, null);
		g.drawImage(gifsea.getImage(), 0, 0, null);
		imgf.setImage(buf);
		return imgf;
	}
	
	//------------------------------------------------------------------
	/**
	 * Renvoit l'ImageIcon de la couleur du player en paramètre
	 * @param player
	 * @return
	 */
	private ImageIcon colorKing(Player player) {
		ImageIcon icon = new ImageIcon();
		if (player.getColor().equals("Bleu")) {
			icon = new ImageIcon(new ImageIcon("src/image/roi_bleu.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
		} else if (player.getColor().equals("Rouge")) {
			icon = new ImageIcon(new ImageIcon("src/image/roi_rouge.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
		} else if (player.getColor().equals("Orange")) {
			icon = new ImageIcon(new ImageIcon("src/image/roi_orange.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
		} else if (player.getColor().equals("Vert")) {
			icon = new ImageIcon(new ImageIcon("src/image/roi_vert.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
		}
		return icon;
	}
	
	//------------------------------------------------------------------
	/**
	 * Choix Domino graphiquement
	 * @author Batelier
	 * @param player
	 * @return DOmino
	 */
	public Domino getPickDomino(Player player) {
		ArrayList<Integer> listInt = new ArrayList<Integer>(Arrays.asList(0,1,5,6,10,11,15,16));
		if (nbPioche == 3) {
			listInt = new ArrayList<Integer>(Arrays.asList(0,1,5,6,10,11));
		}
		
		for (Integer i : listInt) {
			this.getComponent(i).setEnabled(false);
		}
		return null;
	}
	//------------------------------------------------------------------
	//GETTERS
	public int getNbPioche() {
		return nbPioche;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	//------------------------------------------------------------------

}//fin class
