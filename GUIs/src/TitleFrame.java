import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TitleFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationData.titleFrame = new TitleFrame();
					ApplicationData.titleFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TitleFrame() {

		getContentPane().setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);

		JButton playButton = new JButton("Play/Settings");
		playButton.setBounds(300, 450, 200, 80);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData.titleFrame.setVisible(false);
				ApplicationData.settingsFrame = new SettingsFrame();
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(playButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(200, 50, 400, 300);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.CENTER);
		java.net.URL url = this.getClass().getResource("Hangman2.jpg");
		ImageIcon icon = new ImageIcon(url);
		Image image = icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(image));
		getContentPane().add(lblNewLabel);

		setVisible(true);
	}

}
