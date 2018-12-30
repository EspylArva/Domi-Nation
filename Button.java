import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
/*
 * Specifie un String a chaque bouton cree pour connaître l'action à effectuer
 */


//Pour un format de bouton personnalise + animation a la souris
public class Button extends JButton implements MouseListener{
	private String txt;
	private String btnType;
	//private Image img;


	//Constructor
	public Button(String txt, String btnType) {
		super(txt); //String txt est defini dans JButton
		this.btnType = btnType;
	}//Constructor

//	public void paintComponent(Graphics g){
//		g.setColor(Color.BLACK);
//		g.drawOval(100, 100, 100, 100);
//	}

	//implements
	@Override
	public void mouseClicked(MouseEvent e) {
//		if (this.getBtnType().equals("1")) {
//			this.setVisible(false);
//		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//Getter and Setters
	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getBtnType() {
		return btnType;
	}

	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}

	public String getTxt() {
		return txt;
	}
	
	
}//Class
