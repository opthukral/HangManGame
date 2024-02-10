import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CongratulationsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public CongratulationsFrame(boolean lost) {
		setName("Congratulations");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);

	

		JPanel resultPanel = new JPanel(new BorderLayout());
		JLabel resultLabel;

		if (lost) {
			resultLabel = new JLabel("Game Over! The Word Was: " + ApplicationData.secretWord);
		} else {
			resultLabel = new JLabel("Congratulations! You Guessed The Word!");
		}

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData.gameFrame.setVisible(false);
				ApplicationData.congratsFrame.setVisible(false);
				ApplicationData.titleFrame = new TitleFrame();
			}
		});

		resultPanel.add(resultLabel, BorderLayout.CENTER);
		resultPanel.add(okButton, BorderLayout.SOUTH);

		getContentPane().add(resultPanel);
		setVisible(true);

	}

}
