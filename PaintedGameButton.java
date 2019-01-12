import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
	 * JButton with img for GameWindow
	 * @author Batelier
	 *
	 */
	class PaintedGameButton extends JButton{
		
		private ImageIcon imgfond = new ImageIcon();
		private ImageIcon imgCrwn = new ImageIcon();
		private ImageIcon imgFinale = new ImageIcon();
		private int cellSize = 50;
		private int cellPos; //pour choix du move
		private Cell cell;
		
		//constructor
		public PaintedGameButton(Cell cell, int cellPos) {
			this.cell = cell;
			this.cellPos = cellPos;

			switch (cell.getCrownNb()) {
			case 0:
				//imgCrwn = new ImageIcon(new ImageIcon("src/image/void.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
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
			else {
				imgfond = new ImageIcon(new ImageIcon("src/image/blank.png").getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
			}
			
			imgFinale = buildImageIcon(imgfond, imgCrwn);
			this.setIcon(imgFinale);
		}//fin constructor
		
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

		public int getCellPos() {
			return cellPos;
		}

		public void setCellPos(int cellPos) {
			this.cellPos = cellPos;
		}

		public Cell getCell() {
			return cell;
		}

		public void setCell(Cell cell) {
			this.cell = cell;
		}
		
		
	}//fin class