import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
//Ajoute juste l image de fond pour le menu
// + les labels
//Panel contenant l'image de fond 
public class BackgroundMenuPanel extends JPanel{
	//garder le nom, methode predefinie
	public void paintComponent(Graphics g) {
		try {
			Image title = ImageIO.read(new File("src/image/titre.png"));
			Image fond = ImageIO.read(new File("src/image/fond_menu_transp.png"));
			g.drawImage(fond, 0, 0, this.getWidth(), this.getHeight(), this);
			g.drawImage(title, 100, 20, this);
			Font font = new Font("Arial", Font.CENTER_BASELINE, 30);
			g.setFont(font);
			g.drawString("Choisir un nombre de joueur(s).", 100, 200);
			g.drawString("Saisir le(s) nom(s).", 100, 400);
			g.drawString("Choisir un mode de jeu.", 900, 200);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

//		this.setLayout(null);
//		btn.setBounds(100, 200, 80, 40);
//		this.add(btn);
		
	}//paintComponent
}//class

