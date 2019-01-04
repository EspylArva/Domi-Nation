import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Affichage graphique pioche
 * @author Batelier
 *
 */
public class PiocheGraphic extends JPanel{
	private int cellSize = 50;
	Dimension cellDim = new Dimension(cellSize, cellSize);
	private int nbPioche; //nb de domino à choisir par les joueurs
	private int width = 250;
	private int height = 200;
	public PiocheGraphic() {
		
		if (utils.numbPlayers == 2 || utils.numbPlayers == 4) {
			nbPioche = 4;
		} else if (utils.numbPlayers == 3) {nbPioche = 3;}
		this.height = nbPioche*cellSize;
		
		GridLayout terrainLayout = new GridLayout(nbPioche, 5);
		this.setLayout(terrainLayout);
		for (int i = 0; i<nbPioche*5; i++) {
			JButton button = new JButton();
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//System.out.println(button.getCellNum());
				}
			});
			button.setPreferredSize(cellDim);
			this.add(button);
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
