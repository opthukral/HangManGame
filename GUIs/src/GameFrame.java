import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel hangmanPanel;
	private boolean hintUsed;
	private List<Integer> unguessedIndices;
	private JLabel wordLabel;
	private JLabel livesChosenLabel;
	private JLabel dashesLabel;
	private int remainingLives;
	private JTextArea wrongAnswerBox;
	private JScrollPane scrollPane;
	private JPanel textPanel;
	private JPanel centerPanel;
	private StickmanPanel stickmanPanel;
	private JPanel buttonPanel;
	private JButton hintButton;
	private JTextField letterField;
	private JProgressBar progressBar; // Added progress bar

	/**
	 * Create the frame.
	 */
	public GameFrame() {
		this.setName("Hangman Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);

		hangmanPanel = new JPanel(new BorderLayout());

		wordLabel = new JLabel("The word length you have chosen is: " + ApplicationData.secretWord.length());
		wordLabel.setFont(new Font("Arial", Font.BOLD, 20));

		livesChosenLabel = new JLabel("Number of lives:" + ApplicationData.lives);
		livesChosenLabel.setFont(new Font("Arial", Font.BOLD, 20));

		dashesLabel = new JLabel(getDashes(ApplicationData.secretWord.length()));
		dashesLabel.setFont(new Font("Arial", Font.BOLD, 40));

		wrongAnswerBox = new JTextArea();
		wrongAnswerBox.setEditable(false);
		wrongAnswerBox.setFont(new Font("Arial", Font.BOLD, 20));

		scrollPane = new JScrollPane(wrongAnswerBox);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(150, 400));

		textPanel = new JPanel(new GridLayout(4, 1));
		textPanel.add(wordLabel);
		textPanel.add(livesChosenLabel);
		textPanel.add(dashesLabel);

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(textPanel, BorderLayout.WEST);

		// Create StickmanPanel and add it to the left side
		stickmanPanel = new StickmanPanel(ApplicationData.lives);
		centerPanel.add(stickmanPanel, BorderLayout.CENTER);

		buttonPanel = new JPanel(); // Right-align components in buttonPanel

		// Initialize letterField before adding it
		letterField = new JTextField();
		letterField.setVisible(false); // Hide the text field

		// Initialize hintButton before adding it
		hintButton = new JButton("Hint (1 Use Only)");
		hintButton.setFocusable(false); // Set focusable to false
		hintButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!hintUsed) { // Check if the hint has not been used before
					provideHint();
					// Disable the hint button if the last hint revealed the final letter
					if (unguessedIndices.isEmpty()) {
						hintButton.setEnabled(false);
						if (dashesLabel.getText().replace(" ", "").equals(ApplicationData.secretWord)) {

						}
					}
					hintUsed = true; // Set the hint as used
				}
			}
		});
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(hintButton);
		buttonPanel.add(letterField);

		// Add progress bar to the right of the hint button
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true); // Display percentage string
		buttonPanel.add(progressBar);

		// Add buttonPanel to the top of hangmanPanel
		hangmanPanel.add(buttonPanel, BorderLayout.NORTH);

		hangmanPanel.add(centerPanel, BorderLayout.CENTER); // Add the centerPanel to the center
		hangmanPanel.add(scrollPane, BorderLayout.EAST);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char typedChar = e.getKeyChar();
				if (Character.isLetter(typedChar)) {
					String typedString = String.valueOf(typedChar).toLowerCase();
					processGuess(typedString);
				}
			}
		});

		getContentPane().add(hangmanPanel);
		setVisible(true);

		// Focus the hangmanFrame so that typing works without needing to click
		requestFocusInWindow();
	}

	private void provideHint() {
		// Find the first correct letter that hasn't been guessed
		if (!unguessedIndices.isEmpty()) {
			int hintIndex = unguessedIndices.get(0);
			char letter = ApplicationData.secretWord.charAt(hintIndex);

			// Set the correct letter in its appropriate place in the word
			StringBuilder dashes = new StringBuilder(dashesLabel.getText());
			dashes.setCharAt(hintIndex * 2, letter);
			dashesLabel.setText(dashes.toString());

			// Remove the hint index from the list
			unguessedIndices.remove(0);
		}

		hintUsed = true; // Set the hint as used
	}

	private void processGuess(String guess) {
		boolean correctGuess = false;

		for (int i = 0; i < ApplicationData.secretWord.length(); i++) {
			if (ApplicationData.secretWord.charAt(i) == guess.charAt(0)) {
				correctGuess = true;
				StringBuilder dashes = new StringBuilder(dashesLabel.getText());
				dashes.setCharAt(i * 2, guess.charAt(0));
				dashesLabel.setText(dashes.toString());
			}
		}

		if (!correctGuess) {
			ApplicationData.lives--;
			wrongAnswerBox.append(guess + " ");
			livesChosenLabel.setText("Number of lives: " + ApplicationData.lives);
			stickmanPanel.updateStickman(ApplicationData.lives); // Update stickman drawing
		}

		if (ApplicationData.lives == 0) {

			ApplicationData.congratsFrame = new CongratulationsFrame(true);
			// Show game over message
		}

		if (dashesLabel.getText().replace(" ", "").equals(ApplicationData.secretWord)) {

			ApplicationData.congratsFrame = new CongratulationsFrame(false);
		}

		letterField.setText("");

		// Update unguessed indices
		for (int i = 0; i < ApplicationData.secretWord.length(); i++) {
			if (ApplicationData.secretWord.charAt(i) == guess.charAt(0) && unguessedIndices.contains(i)) {
				unguessedIndices.remove(Integer.valueOf(i));
			}
		}

		// Update progress bar when a correct letter is guessed
		updateProgressBar();
	}

	private void updateProgressBar() {
		int totalLetters = ApplicationData.secretWord.length();
		int remainingLetters = unguessedIndices.size();
		int guessedLetters = totalLetters - remainingLetters;

		// Calculate the percentage of correct letters guessed
		int progress = (int) (((double) guessedLetters / totalLetters) * 100);
		progressBar.setValue(progress);

		// Check if all letters are guessed, and the progress bar is full
		if (progress == 100) {
			// Do something when all letters are guessed
		}
	}

	private String getDashes(int length) {
		StringBuilder dashes = new StringBuilder();
		unguessedIndices = new ArrayList<>();

		for (int i = 0; i < length; i++) {
			dashes.append("_ ");
			unguessedIndices.add(i);
		}

		return dashes.toString().trim();
	}
}
