import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
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
//			Icon img = new ImageIcon("src/image/fond_menu_transp.png");
//			button.setIcon(img);
			//panel.add(button);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(button.getX());
					System.out.println(button.getY());
					//System.out.println(button.getCellNum());
				}
			});
			button.setPreferredSize(cellDim);
			this.add(button);
		}
		
		//désactiver case milieu
		for(int i = 2; i < nbPioche*5-1; i+=5) {
			this.getComponent(i).setVisible(false);
		}
		
		
	}//fin constructor
	
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

//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JPanel;
//
///**
// * Objet graphique pioche
// * @author Batelier
// *
// */
//public class PiocheGraphic extends JPanel{
//	private int cellSize = 50;
//	Dimension cellDim = new Dimension(cellSize, cellSize);
//	private int nbPioche; //nb de domino à choisir par les joueurs
//	private int width = 250;
//	private int height = 200;
//	private int vgap = 5;
//	public PiocheGraphic() {
//		
//		if (utils.numbPlayers == 2 || utils.numbPlayers == 4) {
//			nbPioche = 4;
//		} else if (utils.numbPlayers == 3) {nbPioche = 3;}
//		this.height = nbPioche*cellSize + nbPioche*vgap;
//		
//		GridLayout terrainLayout = new GridLayout(nbPioche, 5);
//		terrainLayout.setVgap(vgap);
//		this.setLayout(terrainLayout);
//		this.setOpaque(false);
//		for (int i = 0; i<nbPioche*5; i++) {
//			JButton button = new JButton();
//			button.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					//System.out.println(button.getCellNum());
//				}
//			});
//			button.setPreferredSize(cellDim);
//			this.add(button);
//		}
//		
//		//désactiver case milieu
//		for(int i = 2; i < nbPioche*5-1; i+=5) {
//			this.getComponent(i).setVisible(false);
//		}
//		
//		
//	}//fin constructor
//	
//	//------------------------------------------------------------------
//	//GETTERS
//	public int getNbPioche() {
//		return nbPioche;
//	}
//	public int getWidth() {
//		return width;
//	}
//	public int getHeight() {
//		return height;
//	}
//	//------------------------------------------------------------------
//
//}//fin class

