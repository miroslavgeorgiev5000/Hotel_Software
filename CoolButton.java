///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: CoolButton
//// Description:
////  A very cool button.
////  Default JButtons are ugly,
////  this way it looks better.
//////////////////////////////////////////



import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
public class CoolButton extends JButton{

	private Color buttonColor = new Color(246,243,226);

	private void styleThisButton(){
		setBackground(buttonColor);
		setBorderPainted(true);
	}

	public CoolButton(String in){
		super(in);
		styleThisButton();
	}

	public CoolButton(){
		super();
		styleThisButton();
	}

}