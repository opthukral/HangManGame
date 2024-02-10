import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JSlider wordLengthSlider;
	private JPasswordField secretWordField;
	private JPanel gamePanel;
	private JSpinner spinner;

	/**
	 * Create the frame.
	 */
	public SettingsFrame() {
		setBackground(new Color(255, 255, 255));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);

		gamePanel = new JPanel(new BorderLayout());

		JPanel settingsPanel = new JPanel(new GridLayout(4, 2));
		JLabel wordLengthLabel = new JLabel("Word Length:");
		wordLengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordLengthLabel.setBackground(new Color(255, 255, 255));
		wordLengthSlider = new JSlider(3, 8);
		wordLengthSlider.setMajorTickSpacing(1);
		wordLengthSlider.setPaintTicks(true);
		wordLengthSlider.setPaintLabels(true);

		JLabel secretWordLabel = new JLabel("Secret Word:");
		secretWordLabel.setBounds(311, 9, 100, 13);
		secretWordField = new JPasswordField(8);
		secretWordField.setBounds(383, 6, 70, 19);
		secretWordField.setEnabled(false); // Initially disabled

		JCheckBox addSecretWordCheckbox = new JCheckBox("Add Secret Word?");
		addSecretWordCheckbox.setBackground(new Color(255, 255, 255));
		addSecretWordCheckbox.setBounds(115, 5, 158, 21);
		addSecretWordCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean enabled = addSecretWordCheckbox.isSelected();
				secretWordLabel.setEnabled(enabled);
				secretWordField.setEnabled(enabled);
			}
		});

		settingsPanel.add(wordLengthLabel);
		settingsPanel.add(wordLengthSlider);

		JLabel livesLabel = new JLabel("# of Lives:");
		livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		livesLabel.setBackground(new Color(255, 255, 255));
		settingsPanel.add(livesLabel);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(3, 3, 6, 1));
		settingsPanel.add(spinner);

		JPanel secretWordPanel = new JPanel();
		secretWordPanel.setBackground(new Color(255, 255, 255));
		secretWordPanel.setLayout(null);
		secretWordPanel.add(addSecretWordCheckbox);
		secretWordPanel.add(secretWordLabel);
		secretWordPanel.add(secretWordField);

		secretWordField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (Character.isDigit(c)) {
					e.consume(); // Ignore the key press if it's a digit
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// You can add additional logic if needed
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// You can add additional logic if needed
			}
		});

		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData.hintUsed = false; // Reset the hint usage when setting up a new game
				int wordLength = wordLengthSlider.getValue();
				ApplicationData.lives = (int) spinner.getValue();

				if (secretWordField.isEnabled()) {
					// If "Add Secret Word?" is checked, use the user-inputted word

					ApplicationData.secretWord = new String(secretWordField.getPassword()).toLowerCase();

					if (ApplicationData.secretWord.length() < 3 || ApplicationData.secretWord.length() > 8) {
						JOptionPane.showMessageDialog(gamePanel, "Please enter a word between 3 and 8 letters.",
								"Invalid Word", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					// If "Add Secret Word?" is not checked, choose a word from the word bank
					ApplicationData.secretWord = getRandomWord(wordLength);
				}

				ApplicationData.settingsFrame.setVisible(false);
				ApplicationData.gameFrame = new GameFrame();
			}
		});

		gamePanel.add(settingsPanel, BorderLayout.WEST);
		gamePanel.add(secretWordPanel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.CENTER);
		java.net.URL url = this.getClass().getResource("hello.jpg");
		ImageIcon icon = new ImageIcon(url);
		Image image = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(image));
		lblNewLabel.setBounds(67, 107, 476, 329);
		secretWordPanel.add(lblNewLabel);
		gamePanel.add(startButton, BorderLayout.SOUTH);

		getContentPane().add(gamePanel);
		setVisible(true);
	}

	private String getRandomWord(int length) {
		String[] wordBank;
		switch (length) {
		case 3:
			wordBank = ApplicationData.wordBank3;
			break;
		case 4:
			wordBank = ApplicationData.wordBank4;
			break;
		case 5:
			wordBank = ApplicationData.wordBank5;
			break;
		case 6:
			wordBank = ApplicationData.wordBank6;
			break;
		case 7:
			wordBank = ApplicationData.wordBank7;
			break;
		case 8:
			wordBank = ApplicationData.wordBank8;
			break;
		default:
			return "";
		}

		Random rand = new Random();
		return wordBank[rand.nextInt(wordBank.length)];
	}

}
