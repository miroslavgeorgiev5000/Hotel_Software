///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: CoolPanel
//// Description:
////  a very cool stylish and colorful panel
////  extends jpanel because that's why it's useful
////
//////////////////////////////////////////



import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class CoolPanel extends JPanel{
	private Color panelColor = new Color(236,233,216);
	private Color panelBorderColor = new Color(128,128,128);

	public CoolPanel(String thing){
		setBackground(panelColor);
		TitledBorder t =  new TitledBorder(thing);
		t.setBorder(BorderFactory.createLineBorder(panelBorderColor));
		setBorder(t);
		setLayout(new FlowLayout());
		//setBorder(BorderFactory.createTitledBorder(null, thing, TitledBorder.LEFT, TitledBorder.TOP, new Font("times new roman",Font.PLAIN,12), panelBorderColor));
	}
}