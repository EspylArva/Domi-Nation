

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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
	//private String nomJoueur = "";
	
	//constructor Terrain
	public TerrainGraphic(String color){
		//this.nomJoueur = nomJoueur;
		GridLayout terrainLayout = new GridLayout(9, 9);
		this.setLayout(terrainLayout);
		for (int i = 0; i<81; i++) {
			TerrainCell button = new TerrainCell(i);
			
			if (i == 40) {
				button.setIcon(chateauImg(color));
			}
			
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

	/**
	 * Met à jour graphiquement le TerrainGraphique concerné
	 * @param kingdomMapWrong
	 * @param player
	 * @author Batelier
	 */
	public void updateTerrainGraphic(KingdomMap kingdomMapWrong, Player player) {
		this.removeAll();
		
		ArrayList<Cell> listOfCell = triKingdomMap(kingdomMapWrong);

		int cellPos = 0;

		for (Cell cell : listOfCell) {
			PaintedGameButton btn = new PaintedGameButton(cell, cellPos);
			btn.setBackground(Color.white);
			
			
			if (cellPos == 40) {
				btn.setIcon(chateauImg(player.getColor()));
				btn.setBackground(Color.white);
				//				btn.Action
			}
			cellPos++;
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (utils.posCell1 == -1) {
						System.out.println("##### POS 1 #####");
						utils.posCell1 = btn.getCellPos();
						btn.setIcon(imgFromCell(utils.choiceDomino.getCell1(), btn));
					} else {
						System.out.println("##### POS 2 #####");
						System.out.println(btn.getCellPos());
						utils.posCell2 = btn.getCellPos();
						utils.choiceMoveDone = true;
						btn.setIcon(imgFromCell(utils.choiceDomino.getCell2(), btn));
					}
					//btn.setIcon(imageIcon(btn));

				}
			});
			
			this.add(btn);
		}


		//PaintedGameButton btn = new PaintedGameButton(cell);
	}
	
	//-----------------------------------------------------------------------
	/**
	 * Return Image Icon of castle
	 * @author Batelier
	 * @param color
	 * @return
	 */
	public ImageIcon chateauImg(String color) {
		ImageIcon img = new ImageIcon();
		if (color.equals("Bleu")) {
			img = new ImageIcon(new ImageIcon("src/image/chateau_bleu.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
		} else if (color.equals("Rouge")) {
			img = (new ImageIcon(new ImageIcon("src/image/chateau_rouge.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT)));
		} else if (color.equals("Orange")) {
			img = (new ImageIcon(new ImageIcon("src/image/chateau_orange.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT)));
		} else if (color.equals("Vert")) {
			img = (new ImageIcon(new ImageIcon("src/image/chateau_vert.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT)));
		}
		return img;
	}

	//-----------------------------------------------------------------------
	
	public ArrayList<Cell> triKingdomMap(KingdomMap kgMap) {
		ArrayList<Cell> listCell = new ArrayList<Cell>();
		
		for (int index = 0; index<kgMap.getTerrain().get(0).size() ; index++) {
			for (ArrayList<Cell> xList : kgMap.getTerrain()) {
				listCell.add(xList.get(index));
			}
		}

		return listCell;
	}
	
	//-----------------------------------------------------------------------
	
	public ImageIcon imgFromCell(Cell cell, PaintedGameButton btn) {

		ImageIcon imgfond = new ImageIcon();
		ImageIcon imgCrwn = new ImageIcon();
		ImageIcon imgFinale = new ImageIcon();
		
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
		
		imgFinale = btn.buildImageIcon(imgfond, imgCrwn);
		return imgFinale;
	}
	
	//-----------------------------------------------------------------------

	/**
	 * OBSOLETE
	 * Class TerrainCell
	 * Jbutton + int
	 * @author Batelier
	 */
	class TerrainCell extends JButton{
		private int cellNum;

		//constructor
		public TerrainCell(int cellNum) {
			this.cellNum = cellNum;
			this.setBackground(Color.white);
		}

		//getter
		public int getCellNum() {
			return cellNum;
		}

	}//fin class TerrainCell
	
}//fin class
