package com.example.messagingstompwebsocket.games.verbal.wordsearch;

import com.example.messagingstompwebsocket.games.verbal.VerbalGame;
import com.example.messagingstompwebsocket.utilities.RandomGenerator;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class WordSearch extends VerbalGame {

	private int found, missed;
	private int size;
	private int wordLength;
	private Character[][] puzzle;
	private Country[] randomCountries;
	private Country lastCountryFound;
	private final RandomGenerator<String> rgCountries;
	private final RandomGenerator<Character> rgVowels;
	private final RandomGenerator<Character> rgConsonants;
	private int insertedWords;
	int level;

	public WordSearch(int level) {

		super();
		this.level = level;
		List<String> countries = null;
		if (Locale.getDefault().getLanguage().equals("en")) {
			countries = fileManager.getCountries_en();
		} else {
			countries = fileManager.getCountries_gr();
		}
		insertedWords = 0;
		initLevel(level);
		found = 0;
		missed = 0;
		lastCountryFound = null;
		rgCountries = new RandomGenerator<>(countries);
		if (Locale.getDefault().getLanguage().equals("en")) {
			rgVowels = new RandomGenerator<>(vowels_en);
			rgConsonants = new RandomGenerator<>(consonants_en);
		} else {
			rgVowels = new RandomGenerator<>(vowels_gr);
			rgConsonants = new RandomGenerator<>(consonants_gr);
		}
		initRandomCountries();
		createEmptyPuzzle();
		fillPuzzle(level);
	}

	@Override
	public boolean isFinished() {
		return goal == found;
	}

	public Country[] getCountries() {
		return randomCountries;
	}

	public int getSize() {
		return size;
	}

	public Country getLastCountryFound() {
		return lastCountryFound;
	}

	@Override
	public int isValidAction(Object[] actions) {
		if (actions.length == 4) {
			int startRow = (Integer) actions[0];
			int startCol = (Integer) actions[1];
			int endRow = (Integer) actions[2];
			int endCol = (Integer) actions[3];
			Point start = new Point(startRow, startCol);
			Point end = new Point(endRow, endCol);
			for (Country c : randomCountries) {
				if ((start.equals(c.getStart()) && end.equals(c.getEnd()))
						|| (start.equals(c.getEnd()) && end.equals(c.getStart()))) {
					lastCountryFound = c;
					found++;
					return 1;
				}
			}
		}
		missed++;
		return 0;
	}

	public int getMissed() {
		return missed;
	}

	public int getLevel() {
		return level;
	}

	public boolean isStartOrEnd(int row, int col) {
		Point p = new Point(row, col);
		for (Country c : randomCountries) {
			if (p.equals(c.getStart()) || p.equals(c.getEnd())) {
				return true;
			}
		}
		return false;
	}

	public Character[][] getPuzzle() {
		return puzzle;
	}

	private void createEmptyPuzzle() {
		puzzle = new Character[size][size];
		for (Character[] puzzle1 : puzzle) {
			for (int j = 0; j < puzzle.length; j++) {
				puzzle1[j] = ' ';
			}
		}
	}

	private void initRandomCountries() {
		randomCountries = new Country[goal];
		for (int i = 0; i < goal; i++) {
			String country = rgCountries.getRandomElement();
			while (isValidLength(country)) {
				country = rgCountries.getRandomElement();
			}
			if (i > 0) {
				while (isValidLength(country) || existsCountry(country, i)) {
					country = rgCountries.getRandomElement();
				}
			}
			randomCountries[i] = new Country(country);
		}
	}

	private boolean isValidLength(String country) {
		return country.length() + 2 >= wordLength;
	}

	private boolean existsCountry(String country, int limit) {
		for (int i = 0; i < limit; i++) {
			if (randomCountries[i].getName().equals(country)) {
				return true;
			}
		}
		return false;
	}

	private void fillPuzzle(int level) {
		fillWithCountries(puzzle, level);
		for (Character[] puzzle1 : puzzle) {
			for (int j = 0; j < puzzle.length; j++) {
				System.out.print(puzzle1[j]);
			}
			System.out.println();
		}
		fillEmptyCharacters(puzzle);
	}

	private void fillEmptyCharacters(Character[][] puzzle) {
		for (Character[] puzzle1 : puzzle) {
			for (int j = 0; j < puzzle.length; j++) {
				if (puzzle1[j] != ' ') {
					puzzle1[j] = puzzle1[j];
				} else {
					Random r = new Random();
					int choice = r.nextInt(3);
					if (choice == 1) {
						puzzle1[j] = rgVowels.getRandomElement();
					} else {
						puzzle1[j] = rgConsonants.getRandomElement();
					}
				}
			}
		}
	}

	// get the arraylist that has the number of inserted
	public int getInsertedWords() {
		return insertedWords;
	}

	private void fillWithCountries(Character[][] puzzle, int level) {
		Arrays.sort(randomCountries);
		for (Country randomCountry : randomCountries) {
			boolean isAdded = addCountry(randomCountry, puzzle, level);
			if (isAdded) {
				insertedWords++;
			}
			System.out.println(randomCountry.getName() + ":" + isAdded);
		}
	}

	private boolean addCountry(Country country, Character[][] puzzle) {
		String name = country.getName();
		Character[][] origPuzzle = new Character[puzzle.length][puzzle.length];
		for (int i = 0; i < puzzle.length; i++) {
			System.arraycopy(puzzle[i], 0, origPuzzle[i], 0, puzzle.length);
		}
		int counter = 0;
		while (!country.isPlaced()) {
			Random r = new Random();
			// 0 = Normal, 1 = Flipped
			int orientation = r.nextInt(2);
			if (orientation == 1) {
				name = flipWord(name);
			}
			// 0 = Horizontal, 1 = Vertical, 2 = Diagonal
			int direction = r.nextInt(3);
			if (name.contains(" ")) {
				name = name.replace(" ", "");
			}
			int row = r.nextInt(puzzle.length - name.length());
			int col = r.nextInt(puzzle.length - name.length());
			for (int i = 0; i < name.length(); i++) {
				if (puzzle[row][col] == ' ' || puzzle[row][col] == name.charAt(i)) {
					puzzle[row][col] = name.charAt(i);
					if (i == 0) {
						country.setStart(row, col);
					}
					if (i == (name.length() - 1)) {
						country.setEnd(row, col);
					}
					if (direction == 0) {
						col++;
					}
					if (direction == 1) {
						row++;
					}
					if (direction == 2) {
						col++;
						row++;
					}
				} else {
					for (int j = i; j > 0; j--) {
						if (direction == 0) {
							col--;
						}
						if (direction == 1) {
							row--;
						}
						if (direction == 2) {
							col--;
							row--;
						}
						puzzle[row][col] = origPuzzle[row][col];
					}
					break;
				}
			}
			counter++;
			if (counter > 10000) {
				break;
			}
		}
		System.out.println(name + ":" + counter);
		return country.isPlaced();
	}

	private boolean addCountry(Country country, Character[][] puzzle, int difficulty) {
		String name = country.getName();
		System.out.println(name);
		int counter = 0;
		int direction = 0;
		int orientation = 0;
		Random r = new Random();
		Character[][] origPuzzle = new Character[puzzle.length][puzzle.length];
		for (int i = 0; i < puzzle.length; i++) {
			System.arraycopy(puzzle[i], 0, origPuzzle[i], 0, puzzle.length);
		}
		while (!country.isPlaced()) {
			// 0 = Normal, 1 = Flipped
			if (difficulty == 1) {
				orientation = 0;
			} else {
				orientation = r.nextInt(2);
			}
			if (orientation == 1) {
				name = flipWord(name);
			}
			// 0 = Horizontal, 1 = Vertical, 2 = Diagonal
			direction = r.nextInt(3);
			if (name.contains(" ")) {
				name = name.replace(" ", "");
			}
			int row = r.nextInt(puzzle.length - name.length());
			int col = r.nextInt(puzzle.length - name.length());
			for (int i = 0; i < name.length(); i++) {
				if (puzzle[row][col] == ' ' || puzzle[row][col] == name.charAt(i)) {
					puzzle[row][col] = name.charAt(i);
					if (i == 0) {
						country.setStart(row, col);
					}
					if (i == (name.length() - 1)) {
						country.setEnd(row, col);
					}
					if (direction == 0) {
						col++;
					}
					if (direction == 1) {
						row++;
					}
					if (direction == 2) {
						col++;
						row++;
					}
				} else {
					for (int j = i; j > 0; j--) {
						if (direction == 0) {
							col--;
						}
						if (direction == 1) {
							row--;
						}
						if (direction == 2) {
							col--;
							row--;
						}
						puzzle[row][col] = origPuzzle[row][col];
					}
					break;
				}
			}
			counter++;
			if (counter > 10000) {
				break;
			}
		}
		System.out.println(name + ":" + counter);
		return country.isPlaced();
	}

	private String flipWord(String word) {
		StringBuilder output = new StringBuilder();
		for (int i = word.length() - 1; i >= 0; i--) {
			output.append(word.charAt(i));
		}
		return output.toString();
	}

	private void initLevel(int level) {
		switch (level) {
		case 1:
			goal = 6;
			wordLength = 8;
			size = 10;
			break;
		case 2:
			goal = 9;
			wordLength = 10;
			size = 12;
			break;
		case 3:
			goal = 12;
			wordLength = 12;
			size = 14;
			break;
		case 4:
			goal = 15;
			wordLength = 16;
			size = 18;

			break;
		case 5:
			goal = 20;
			wordLength = 18;
			size = 20;
			break;
		}
		timeAllowed = goal * 15;
	}

}
