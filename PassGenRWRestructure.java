import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;
import java.util.Random;
import java.io.*;

public class PassGenRWRestructure {
	static JFrame frame = new JFrame("PassGen");
	static JTextArea passwordDisplay = new JTextArea(5, 20);
	static String newline = "\n";
	static String fileName = "passwordsTesting.txt";
	
	public static void main(String [] args) {
		generateGUI();
		openFile();
		System.out.println(System.getProperty("java.class.path"));
	}
	
	public static void generateGUI() {
		//Build the GUI
		final JPanel options = new JPanel();
		
		passwordDisplay.setEditable(false);
		
		JPanel upper = new JPanel();
		JTextField numUpper = new JTextField(2);
		JLabel numUpperLabel = new JLabel("A");
		upper.add(numUpper);
		upper.add(numUpperLabel);
		
		JPanel lower = new JPanel();
		JTextField numLower = new JTextField(2);
		JLabel numLowerLabel = new JLabel("a");
		lower.add(numLower);
		lower.add(numLowerLabel);
		
		JPanel num = new JPanel();
		JTextField numNum /*lol*/ = new JTextField(2);
		JLabel numNumLabel = new JLabel("1-9");
		num.add(numNum);
		num.add(numNumLabel);
		
		JPanel symbols = new JPanel();
		JTextField numSymbols = new JTextField(2);
		JLabel numSymbolsLabel = new JLabel("! % #, etc.");
		symbols.add(numSymbols);
		symbols.add(numSymbolsLabel);
		
		JPanel minLength = new JPanel();
		JTextField minLen = new JTextField(2);
		JLabel minLenLabel = new JLabel("min length");
		minLength.add(minLen);
		minLength.add(minLenLabel);
		
		JPanel maxLength = new JPanel();
		JTextField maxLen = new JTextField(2);
		JLabel maxLenLabel = new JLabel("max length");
		maxLength.add(maxLen);
		maxLength.add(maxLenLabel);
		
		JPanel description = new JPanel();
		JTextField desc = new JTextField(20);
		JLabel descLabel = new JLabel("Description");
		description.add(descLabel);
		description.add(desc);
		
		JButton generateButton = new JButton("Generate");
		
		JScrollPane scrollPane = new JScrollPane(passwordDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		options.add(upper);
		options.add(lower);
		options.add(num);
		options.add(symbols);
		options.add(minLength);
		options.add(maxLength);
		options.add(description);
		options.add(generateButton);
		
		Container content = frame.getContentPane();
		content.setLayout(new GridLayout(2, 1));
		content.add(options);
		//content.add(passwordDisplay);
		content.add(scrollPane);
		//content.add(displayArea);
		
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generatePassword(numUpper, numLower, numNum, numSymbols, minLen, maxLen, desc);
			}
		});
		
		frame.setSize(500, 250);
		//frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void openFile() {
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				passwordDisplay.append(line);
				passwordDisplay.append(newline);
				System.out.println("Writing to textArea");
			}
			
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) {
			try {
				FileWriter fileWriter = new FileWriter(fileName);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				//System.out.println("Making " + fileName);
				
				bufferedWriter.newLine();
				bufferedWriter.close();
			}
			catch(IOException ioE) {
				System.out.println("Error writing file");
				ioE.printStackTrace();
			}
		}
		catch(IOException ex) {
			System.out.println("Error reading file");
			ex.printStackTrace();
		}
	}
	
	public static void generatePassword(JTextField numUpper, JTextField numLower, 
										JTextField numNum, JTextField numSymbols, 
										JTextField minLen, JTextField maxLen, 
										JTextField desc) {
		String [] upperLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
								  "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
								  "Y", "Z"};
		String [] lowerLetters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
								  "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
								  "y", "z"};
		String [] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String [] symbols = {"`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", 
							 "-", "+", "=", "{", "}", "[", "]", "\\", "|", ":", ";", "\"", "'",
							 "<", ">", ",", ".", "?", "/"};
		
		String passwordString = "";
		
		int minNumOfUpper = Integer.parseInt(numUpper.getText());
		int minNumOfLower = Integer.parseInt(numLower.getText());
		int minNumOfNum = Integer.parseInt(numNum.getText());
		int minNumOfSymbols = Integer.parseInt(numSymbols.getText());
		int minimumLength = Integer.parseInt(minLen.getText());
		int maximumLength = Integer.parseInt(maxLen.getText());
		String passDescription = desc.getText();
		
		String [] password = new String[randomInteger(minimumLength, maximumLength)];
		String[] [] chars = {upperLetters, lowerLetters, numbers};
		
		int minRequiredCharsTotal = minNumOfUpper + minNumOfLower + minNumOfNum + minNumOfSymbols;
		
		for (int passCharCount = 0; passCharCount < minRequiredCharsTotal; passCharCount++) {
			for (int i = 0; i < minNumOfUpper; i++) {
				password[i] = upperLetters[randomInteger(0, (upperLetters.length-1))];
			}
			for (int i = 0; i < minNumOfLower; i++) {
				password[i + minNumOfUpper] = lowerLetters[randomInteger(0, (lowerLetters.length-1))];
			}
			for (int i = 0; i < minNumOfNum; i++) {
				password[i + minNumOfUpper + minNumOfLower] = numbers[randomInteger(0, (numbers.length-1))];
			}
			for (int i = 0; i < minNumOfSymbols; i++) {
				password[i + minNumOfUpper + minNumOfLower + minNumOfSymbols] = symbols[randomInteger(0, (symbols.length-1))];
			}
		}
		
		for (int i = 0; i < (password.length - minRequiredCharsTotal); i++) {
			switch (randomInteger(1, 4)) {
				case 1:
					password[i+minRequiredCharsTotal] = upperLetters[randomInteger(0, (upperLetters.length-1))];
					break;
				case 2:
					password[i+minRequiredCharsTotal] = lowerLetters[randomInteger(0, (upperLetters.length-1))];
					break;
				case 3:
					password[i+minRequiredCharsTotal] = numbers[randomInteger(0, (numbers.length-1))];
					break;
				case 4:
					password[i+minRequiredCharsTotal] = symbols[randomInteger(0, (symbols.length-1))];
					break;
				default:
					passwordDisplay.append("Something went wrong" + newline);
					passwordDisplay.setCaretPosition(passwordDisplay.getDocument().getLength());
					break;
			}
		} 
		
		shuffle(password);
		
		if (checkPassForRequirements(password, minNumOfUpper, minNumOfLower, minNumOfNum, minNumOfSymbols, upperLetters, lowerLetters, numbers, symbols)) {
			for (int i = 0; i < password.length; i++) {
				passwordString += password[i];
			}
			
			passwordDisplay.append(passwordString + " - " + passDescription + newline);
			passwordDisplay.setCaretPosition(passwordDisplay.getDocument().getLength());
			
			try {
				FileWriter fileWriter = new FileWriter(fileName, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				
				bufferedWriter.write(passwordString + " - " + passDescription);
				bufferedWriter.newLine();
				//System.out.println("Writing to " + fileName);
			
				bufferedWriter.close();
			}
			catch (IOException ex) {
				System.out.println("Error writing file");
				ex.printStackTrace();
			}
		}
		else {
			generatePassword(numUpper, numLower, numNum, numSymbols, minLen, maxLen, desc);
		}
	}
	
	public static boolean checkPassForRequirements(String[] password, int minNumOfUpper, 
												   int minNumOfLower, int minNumOfNum, int minNumOfSymbols,
												   String[] upperLetters, String[] lowerLetters,
												   String[] numbers, String[] symbols) {
		int upperTotal = 0;
		int lowerTotal = 0;
		int numTotal = 0;
		int symTotal = 0;
		
		for (int i = 0; i < upperLetters.length; i++) {
			upperTotal += search(password, upperLetters[i]);
		}
		for (int i = 0; i < lowerLetters.length; i++) {
			lowerTotal += search(password, lowerLetters[i]);
		}
		for (int i = 0; i < numbers.length; i++) {
			numTotal += search(password, numbers[i]);
		}
		for (int i = 0; i <symbols.length; i++) {
			symTotal += search(password, symbols[i]);
		}
		
		if (upperTotal >= minNumOfUpper && lowerTotal >= minNumOfLower && numTotal >= minNumOfNum && symTotal >= minNumOfSymbols) {
			return true;
		}
		else {
			return false;
		}
	}

	public static int randomInteger(int min, int max) {

		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
	
	public static int search(String [ ] list, String key) {
		int i, count = 0;
		for( i = 0; i< list.length; i++)
		{
			if (list [ i ].equals( key ))
				count = count+1;
		}
		return (count);
	}
	
	public static void shuffle(String[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			// between i and n-1
			int r = i + (int) (Math.random() * (n-i));
			String tmp = a[i];    // swap
			a[i] = a[r];
			a[r] = tmp;
		}
	}
		
}