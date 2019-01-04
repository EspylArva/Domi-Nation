

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Terrain sous forme graphique, gridLayout rempli de boutons personnalisés
 * L'objet terrain contient un String contenant le nom du joueur associé au terrain
 * @author Batelier
 *
 */
public class TerrainGraphic extends JPanel {
	
	private int cellSize = 50; //taille d'une cellule
	Dimension cellDim = new Dimension(cellSize, cellSize);
	private String nomJoueur = "";
	
	public TerrainGraphic(String nomJoueur){
		this.nomJoueur = nomJoueur;
		GridLayout terrainLayout = new GridLayout(9, 9);
		this.setLayout(terrainLayout);
		for (int i = 0; i<81; i++) {
			TerrainCell button = new TerrainCell(i);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(button.getCellNum());
				}
			});
			button.setPreferredSize(cellDim);
			this.add(button);
		}
	}//fin constructor
	
	//-----------------------------------------------------------------------

	//getter nom joueur
	public String getNomJoueur() {
		return nomJoueur;
	}

	//-----------------------------------------------------------------------

	/**
	 * Class TerrainCell
	 * Jbutton + int
	 * @author Batelier
	 */
	class TerrainCell extends JButton{
		private int cellNum;

		//constructor
		public TerrainCell(int cellNum) {
			this.cellNum = cellNum;
			this.setBackground(Color.lightGray);
		}

		//getter
		public int getCellNum() {
			return cellNum;
		}

	}//fin class TerrainCell
	
}//fin class
