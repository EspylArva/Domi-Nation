
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Fenêtre affichage menu.
 * 
 * @author Batelier
 *
 */
public class MenuWindow extends JFrame{
	private BackgroundMenuPanel menu = new BackgroundMenuPanel();
	private JButton btn1j = new JButton("1 joueur");
	private JButton btn2j = new JButton("2 joueurs");
	private JButton btn3j = new JButton("3 joueurs");
	private JButton btn4j = new JButton("4 joueurs");
	private JButton btnplay = new JButton("Play !");
	private JTextField nom1 = new JTextField("Joueur 1");
	private JTextField nom2 = new JTextField("Joueur 2");
	private JTextField nom3 = new JTextField("Joueur 3");
	private JTextField nom4 = new JTextField("Joueur 4");
	private JComboBox<String> colorBox1 = new JComboBox<String>();
	private JComboBox<String> colorBox2 = new JComboBox<String>();
	private JComboBox<String> colorBox3 = new JComboBox<String>();
	private JComboBox<String> colorBox4 = new JComboBox<String>();
	ArrayList<JComboBox> comboList = new ArrayList<JComboBox>(Arrays.asList(colorBox1, colorBox2, colorBox3, colorBox4));
	private int nbJoueurs = 0;

	public MenuWindow() {
		//Code JFrame base
		this.setTitle("Menu DomiNations"); //titre
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(fullScreen); //taille de la fenetre
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quitter le programme quand croix
		this.setLocationRelativeTo(null); //centrer
		//fin Code JFrame
	
		//layout null pour placement libre des boutons
		menu.setLayout(null);
		
		//liste de boutons pour implementer les Action Listener
		ArrayList<JButton> btnJoueurList = new ArrayList<JButton>(Arrays.asList(btn1j,btn2j,btn3j,btn4j));
		for (JButton btns : btnJoueurList) {
			btns.addActionListener(new btnNbJoueurs(Integer.parseInt(btns.getText().substring(0, 1))));
			menu.add(btns);
		}

		//Rendre invisible et disable les entrees noms et couleurs
		ArrayList<JTextField> textFieldlist = new ArrayList<JTextField>(Arrays.asList(nom1, nom2, nom3, nom4));
		for (JTextField noms : textFieldlist) {
			noms.setVisible(false);
			menu.add(noms);
		}
		ArrayList<JComboBox> comboList = new ArrayList<JComboBox>(Arrays.asList(colorBox1, colorBox2, colorBox3, colorBox4));
		ArrayList<String> availableColor = new ArrayList<String>(Arrays.asList("Rouge", "Bleu", "Orange", "Vert"));
		int initColor = 0;
		for (JComboBox comboBox : comboList) {
			for (String str : availableColor) {
				comboBox.addItem(str);
			}
			comboBox.setSelectedIndex(initColor);
			//set Color
			switch (initColor) {
			case 0:
				comboBox.setForeground(Color.red);
				break;
			case 1:
				comboBox.setForeground(Color.blue);
				break;
			case 2:
				comboBox.setForeground(Color.orange);
				break;
			case 3:
				comboBox.setForeground(Color.green);
				break;
			}
			initColor ++;
			comboBox.setVisible(false);
			comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					switch (comboBox.getSelectedIndex()) {
					case 0:
						comboBox.setForeground(Color.red);
						break;
					case 1:
						comboBox.setForeground(Color.blue);
						break;
					case 2:
						comboBox.setForeground(Color.orange);
						break;
					case 3:
						comboBox.setForeground(Color.green);
						break;
					}
					
				}
			});
			menu.add(comboBox);
		}
		//Set Position des buttons / label / texte
		btn1j.setBounds(100, fullScreen.height/3, 100, 40);
		btn2j.setBounds(200, fullScreen.height/3, 100, 40);
		btn3j.setBounds(300, fullScreen.height/3, 100, 40);
		btn4j.setBounds(400, fullScreen.height/3, 100, 40);
		nom1.setBounds(150, 420, 100, 20);
		nom2.setBounds(150, 450, 100, 20);
		nom3.setBounds(150, 480, 100, 20);
		nom4.setBounds(150, 510, 100, 20);
		colorBox1.setBounds(nom1.getX() + 110, nom1.getY(), 100, 20);
		colorBox2.setBounds(nom2.getX() + 110, nom2.getY(), 100, 20);
		colorBox3.setBounds(nom3.getX() + 110, nom3.getY(), 100, 20);
		colorBox4.setBounds(nom4.getX() + 110, nom4.getY(), 100, 20);
		
		//set le bouton Play
		btnplay.setBounds(900, 450, 300, 200);
		btnplay.addActionListener(new btnPlayAction());
		menu.add(btnplay);
		
		//Affichage
		this.setContentPane(menu);
		this.setVisible(true);
	} //fin constructeur

	//---------------------------------------------------------------------------
	/**
	 * Actions boutons nb de joueurs
	 * @author Batelier
	 *
	 */
	class btnNbJoueurs implements ActionListener{

		private int nbj;

		public btnNbJoueurs(int nbj) {
			this.nbj = nbj;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//liste bouton (code à opti)
			ArrayList<JButton> btnJoueurList = new ArrayList<JButton>(Arrays.asList(btn1j,btn2j,btn3j,btn4j));
			for (JButton btns : btnJoueurList) {
				btns.setBackground(null); //reset color
			}
			//Liste noms et couleurs (Pour set visible ou non)
			ArrayList<JTextField> textFieldlist = new ArrayList<JTextField>(Arrays.asList(nom1, nom2, nom3, nom4));
			for (JTextField noms : textFieldlist) {
				noms.setVisible(false);
			}
			for (JComboBox comboBox : comboList) {
				comboBox.setVisible(false);
			}
			switch (nbj) {
			case 1:
				btn1j.setBackground(Color.lightGray);
				break;
			case 2:
				btn2j.setBackground(Color.lightGray);
				break;
			case 3:
				btn3j.setBackground(Color.lightGray);
				break;
			case 4:
				btn4j.setBackground(Color.lightGray);
				break;
			}
					
			//set visible les noms et couleurs à saisir
			for (int i = 0; i< nbj; i++) {
				textFieldlist.get(i).setVisible(true);
				comboList.get(i).setVisible(true);
			}
			
			setNbJoueurs(nbj);
			utils.numbPlayers = nbj;
			System.out.println(nbJoueurs + " joueur(s)");
			
		}//Action Performed
		
	}//Action Listener
	
	//---------------------------------------------------------------------------
	/**
	 * Action btn play
	 * possible de set le nombre de lettres max des joueurs
	 * @author Batelier
	 *
	 */
	class btnPlayAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//verfier que les noms entrés soient corrects
			ArrayList<JTextField> textFieldlist = new ArrayList<JTextField>(Arrays.asList(nom1, nom2, nom3, nom4));
			int limiteNomJoueurs = 12; //longueur max des noms des joueurs
			for (JTextField noms : textFieldlist) {
				if(noms.isVisible()) { //gg
					if (noms.getText().length() < 1 || noms.getText().length() > limiteNomJoueurs) {
						String msgLengthName = "Les noms de joueurs doivent contenir entre 1 et " + limiteNomJoueurs +" lettres.";
						JOptionPane.showMessageDialog(menu, msgLengthName, "Erreur !", 2);
						return;
					}
				}
			}
			ArrayList<Integer> listr = new ArrayList<Integer>();
			for (JComboBox jbox : comboList) {
				if(jbox.isVisible()) {
					if (!listr.contains(jbox.getSelectedIndex())) {
						listr.add(jbox.getSelectedIndex());
					}
					else {
						String msgColor = "Plusieurs joueurs ne peuvent pas avoir la même couleur !";
						JOptionPane.showMessageDialog(menu, msgColor, "Erreur !", 2);
						return;
					}
				}
			}

			utils.name1 = nom1.getText();
			utils.name2 = nom2.getText();
			utils.name3 = nom3.getText();
			utils.name4 = nom4.getText();
			utils.colorJ1 = (String) colorBox1.getSelectedItem();
			utils.colorJ2 = (String) colorBox2.getSelectedItem();
			utils.colorJ3 = (String) colorBox3.getSelectedItem();
			utils.colorJ4 = (String) colorBox4.getSelectedItem();
			if (nbJoueurs != 0) {
				//utils.playOn = true;
				utils.play = true;
			}//si nbJoueurs choisis correct
			else {
				String msg = "Choisir un nombre de joueur(s) !";
				JOptionPane.showMessageDialog(menu, msg, "Erreur !", 2);
				//fenetre input dialog
			}
			
		}//ActionPerformed
		
	}//class
	
	//---------------------------------------------------------------------------
	
//	/**
//	 * Changement noms des joueurs
//	 * @author Batelier
//	 *
//	 */
//	class zoneTextListener implements KeyListener{
//
//		private int nameNum;
//		public zoneTextListener(int nameNum) {
//			super();
//			this.nameNum = nameNum;
//		}
//		
//		public void keyTyped(KeyEvent e) {
//			switch (nameNum) {
//			case 1:
//				utils.name1 = nom1.getText();
//				System.out.println(nom1.getText());
//				System.out.println(utils.name1);
//				break;
//			case 2:
//				utils.name2 = nom2.getText();
//				break;
//			case 3:
//				utils.name3 = nom3.getText();
//				break;
//			case 4:
//				utils.name4 = nom4.getText();
//				break;
//			}
//			
//			
//		}
//
//		//unused
//		@Override
//		public void keyPressed(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		//unused
//		@Override
//		public void keyReleased(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//	}
//	
	//---------------------------------------------------------------------------
	
	/**
	 * Getters Setters
	 * 
	 * @author Batelier
	 */

	public JTextField getNom1() {
		return nom1;
	}

	public void setNom1(JTextField nom1) {
		this.nom1 = nom1;
	}

	public JTextField getNom2() {
		return nom2;
	}

	public void setNom2(JTextField nom2) {
		this.nom2 = nom2;
	}

	public JTextField getNom3() {
		return nom3;
	}

	public void setNom3(JTextField nom3) {
		this.nom3 = nom3;
	}

	public JTextField getNom4() {
		return nom4;
	}

	public void setNom4(JTextField nom4) {
		this.nom4 = nom4;
	}

	public int getNbJoueurs() {
		return nbJoueurs;
	}

	public void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
	}

	//---------------------------------------------------------------------------
	
}//Window classe















