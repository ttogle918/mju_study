package frames;
// 화면 중간정도에 있는 3개 버튼

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

public class TPToolBar extends JToolBar {
	private ButtonGroup bGroup;
	private JPanel panel;
	private JRadioButton rButton1;
	private JRadioButton rButton2;
	private JRadioButton rButton3;

	public TPToolBar(String label) {
		super(label);
		bGroup = new ButtonGroup();

		panel = new JPanel();
		panel.setBorder(new TitledBorder(""));
		// panel.setPreferredSize(new Dimension(51,30));

		rButton1 = new JRadioButton("1");
		rButton2 = new JRadioButton("2");
		rButton3 = new JRadioButton("3");

		this.add(panel);
		this.add(rButton1);
		this.add(rButton2);
		this.add(rButton3);

		bGroup.add(rButton1);
		bGroup.add(rButton2);
		bGroup.add(rButton3);

		panel.add(rButton1);
		panel.add(rButton2);
		panel.add(rButton3);
	}
}
