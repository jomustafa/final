package com.example.messagingstompwebsocket.utilities;

import com.example.messagingstompwebsocket.games.Player;
import com.example.messagingstompwebsocket.games.visual.Color;

import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.example.messagingstompwebsocket.games.visual.memoryquest.HiddenObject;
import com.example.messagingstompwebsocket.games.visual.memoryquest.HidingSpot;
import com.example.messagingstompwebsocket.games.visual.memoryquest.Quest;

import org.nustaq.serialization.FSTConfiguration;

public class FileManager {

	private List<String> countries_gr, countries_en, sortedDictionary_gr, sortedDictionary_en, splitEasy_en,
			splitEasy_gr, simpleShort_en, simpleShort_gr, animalList_gr, animalList_en, plantList_gr, plantList_en,
			occupationList_gr, occupationList_en, countryList_gr, countryList_en, nameList_gr, nameList_en,
			simpleDict_gr, simpleDict_en, faces, houses, femaleNames_gr, femaleNames_en, maleNames_gr, maleNames_en,
			faces2, facesSituations, situations, sayingsList_gr, sayingsList_en, anagramList_gr, anagramList_en,
			hangmanPlants_gr, hangmanPlants_en, hangmanAnimals_gr, hangmanAnimals_en, hangmanOccupations_gr,
			hangmanOccupations_en, spotdifferences;
	private Map<String, List<Quest>> questMap;
//    private Map<String, List<Quests>> questMaps;
	// sortedDictionary_en
//    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
	private List<Color> colors;
//    private String[] wordex_EN;
	private String[] wordex_GR;
	private static final String FACES = "lib/text/facenamehouse/faces.txt";
	private static final String FEMALE_NAMES_GR = "lib/text/facenamehouse/femaleNames_gr.txt";
	private static final String MALE_NAMES_GR = "lib/text/facenamehouse/maleNames_gr.txt";
	private static final String FEMALE_NAMES_EN = "lib/text/facenamehouse/femaleNames_en.txt";
	private static final String MALE_NAMES_EN = "lib/text/facenamehouse/maleNames_en.txt";
	private static final String HOUSES = "lib/text/facenamehouse/houses.txt";
	private static final String WORD_SEARCH_GR = "lib/text/wordsearch/wordsearchfix_gr.txt";
	private static final String WORD_SEARCH_EN = "lib/text/wordsearch/wordsearchfix_en.txt";
	private static final String WORDEX_GR = "lib/text/wordex/wordexfix_GR.txt";
//    private static final String WORDEX_EN = "lib/text/wordex/wordexfix_EN.txt";

	private static final String COLORS = "lib/text/coloredboxes/colors.txt";
	private static final String PLANTS_GR = "lib/text/namesanimalsandplants/plantsfix_gr.txt";
	private static final String PLANTS_EN = "lib/text/namesanimalsandplants/plantsfix_en.txt";
	private static final String COUNTRIES_GR = "lib/text/namesanimalsandplants/countriesfix_gr.txt";
	private static final String COUNTRIES_EN = "lib/text/namesanimalsandplants/countriesfix_en.txt";
	private static final String NAMES_GR = "lib/text/namesanimalsandplants/namesfix_gr.txt";
	private static final String NAMES_EN = "lib/text/namesanimalsandplants/namesfix_en.txt";
	private static final String OCCUPATIONS_GR = "lib/text/namesanimalsandplants/occupationsfix_gr.txt";
	private static final String OCCUPATIONS_EN = "lib/text/namesanimalsandplants/occupationsfix_en.txt";
	private static final String ANIMALS_GR = "lib/text/namesanimalsandplants/animalsv2fix_gr.txt";
	private static final String ANIMALS_EN = "lib/text/namesanimalsandplants/animalsv2fix_en.txt";
	private static final String SPLITWORDS_GR = "lib/text/splitwords/easyWordsfix_gr.txt";
	private static final String SPLITWORDS_EN = "lib/text/splitwords/easyWordsfix_en.txt";
	private static final String SIMPLE_DICT_GR = "lib/text/correctspelling/simplenonamesfix_gr.txt";
	private static final String SIMPLE_DICT_EN = "lib/text/correctspelling/simplenonamesfix_en.txt";
	private static final String SAYINGS_GR = "lib/text/anagram/SAYINGS_GR.txt";
	private static final String SAYINGS_EN = "lib/text/anagram/SAYINGS_EN.txt";
	private static final String ANAGRAM_GR = "lib/text/anagram/ANAGRAM_GR.txt";
	private static final String ANAGRAM_EN = "lib/text/anagram/ANAGRAM_EN.txt";
	private static final String HANGMAN_PLANTS_GR = "lib/text/hangman/HANGMAN_PLANTS_GR.txt";
	private static final String HANGMAN_PLANTS_EN = "lib/text/hangman/HANGMAN_PLANTS_EN.txt";
	private static final String HANGMAN_ANIMALS_GR = "lib/text/hangman/HANGMAN_ANIMALS_GR.txt";
	private static final String HANGMAN_ANIMALS_EN = "lib/text/hangman/HANGMAN_ANIMALS_EN.txt";
	private static final String HANGMAN_OCCUPATIONS_GR = "lib/text/hangman/HANGMAN_OCCUPATIONS_GR.txt";
	private static final String HANGMAN_OCCUPATIONS_EN = "lib/text/hangman/HANGMAN_OCCUPATIONS_EN.txt";
	private static final String SPOTDIFFERENCES = "lib/text/spotdifferences/spotdifferences.txt";
	private static final List<String> FILES = new LinkedList<>();
	private static Player player;
	private static Integer level = 1;
	private static String selectedGame = "";

	static {
		FILES.add(WORD_SEARCH_GR);
		FILES.add(WORD_SEARCH_EN);
		FILES.add(WORDEX_GR);
//        FILES.add(WORDEX_EN);
		FILES.add(COLORS);
		FILES.add(PLANTS_GR);
		FILES.add(PLANTS_EN);
		FILES.add(COUNTRIES_GR);
		FILES.add(COUNTRIES_EN);
		FILES.add(NAMES_GR);
		FILES.add(NAMES_EN);
		FILES.add(OCCUPATIONS_GR);
		FILES.add(OCCUPATIONS_EN);
		FILES.add(ANIMALS_GR);
		FILES.add(ANIMALS_EN);
		FILES.add(SPLITWORDS_GR);
		FILES.add(SPLITWORDS_EN);
		FILES.add(SIMPLE_DICT_GR);
		FILES.add(SIMPLE_DICT_EN);
		FILES.add(FACES);
		FILES.add(HOUSES);
		FILES.add(FEMALE_NAMES_GR);
		FILES.add(MALE_NAMES_GR);
		FILES.add(FEMALE_NAMES_EN);
		FILES.add(MALE_NAMES_EN);
		FILES.add(SAYINGS_GR);
		FILES.add(SAYINGS_EN);
		FILES.add(ANAGRAM_GR);
		FILES.add(ANAGRAM_EN);
		FILES.add(HANGMAN_PLANTS_GR);
		FILES.add(HANGMAN_PLANTS_EN);
		FILES.add(HANGMAN_ANIMALS_GR);
		FILES.add(HANGMAN_ANIMALS_EN);
		FILES.add(HANGMAN_OCCUPATIONS_GR);
		FILES.add(HANGMAN_OCCUPATIONS_EN);
		FILES.add(SPOTDIFFERENCES);
	}

	public FileManager() {
		colors = new LinkedList<>();
		loadAll();
	}

	public List<String> getSayingsList_gr() {
		return sayingsList_gr;
	}

	public List<String> getSayingsList_en() {
		return sayingsList_en;
	}

	public List<String> getHangmanPlants_gr() {
		return hangmanPlants_gr;
	}

	public List<String> getHangmanPlants_en() {
		return hangmanPlants_en;
	}

	public List<String> getHangmanAnimals_gr() {
		return hangmanAnimals_gr;
	}

	public List<String> getHangmanAnimals_en() {
		return hangmanAnimals_en;
	}

	public List<String> getHangmanOccupations_gr() {
		return hangmanOccupations_gr;
	}

	public List<String> getHangmanOccupations_en() {
		return hangmanOccupations_en;
	}

	public List<String> getAnagramList_gr() {
		return anagramList_gr;
	}

	public List<String> getAnagramList_en() {
		return anagramList_en;
	}

	public List<String> getFemaleNames_gr() {
		return femaleNames_gr;
	}

	public List<String> getFemaleNames_en() {
		return femaleNames_en;
	}

	public List<String> getMaleNames_gr() {
		return maleNames_gr;
	}

	public List<String> getMaleNames_en() {
		return maleNames_en;
	}

	public List<String> getFaces() {
		return faces;
	}

	public List<String> getFaces2() {
		return faces2;
	}

	public List<String> getFacesSituations() {
		return facesSituations;
	}

	public List<String> getSituations() {
		return situations;
	}

	public List<String> getHouses() {
		return houses;
	}

	public List<String> getSimpleShort_en() {
		return simpleShort_en;
	}

	public List<String> getSimpleShort_gr() {
		return simpleShort_gr;
	}

//    public List<String> getSimpleDict() {
//        return simpleDict;
//    }
	public List<String> getSimpleDict_gr() {
		return simpleDict_gr;
	}

	public List<String> getSimpleDict_en() {
		return simpleDict_en;
	}

	public List<String> getAnimals_gr() {
		return animalList_gr;
	}

	public List<String> getAnimals_en() {
		return animalList_en;
	}

	public List<String> getCountryList_gr() {
		return countryList_gr;
	}

	public List<String> getCountryList_en() {
		return countryList_en;
	}

	public List<String> getNames_gr() {
		return nameList_gr;
	}

	public List<String> getNames_en() {
		return nameList_en;
	}

	public List<String> getOccupations_gr() {
		return occupationList_gr;
	}

	public List<String> getOccupations_en() {
		return occupationList_en;
	}

	public List<String> getPlants_gr() {
		return plantList_gr;
	}

	public List<String> getPlants_en() {
		return plantList_en;
	}

	public Map<String, List<Quest>> getQuestMap() {
		return questMap;
	}

//    public Map<String, List<Quests>> getQuestMaps() {
//        return questMaps;
//    }

	public List<Color> getColors() {
		return colors;
	}

	public List<String> getSplitEasy_en() {
		return splitEasy_en;
	}

	public List<String> getSplitEasy_gr() {
		return splitEasy_gr;
	}

	public List<String> getCountries_gr() {
		return countries_gr;
	}

	public List<String> getCountries_en() {
		return countries_en;
	}

	public List<String> getSortedDictionary_en() {
		return sortedDictionary_en;
	}

	public List<String> getSortedDictionary_gr() {
		return sortedDictionary_gr;
	}

	public List<String> getDifferences() {
		return spotdifferences;
	}

//    public String[] getWordex_en() {
//        return wordex_EN;
//    }
	public String[] getWordex_gr() {
		return wordex_GR;
	}

	public static void savePlayer(Player plr) {
		player = plr;
	}

	public static void saveLevel(int lvl) {
		level = lvl;
	}

	public static void saveGame(String game) {
		selectedGame = game;
	}

	public static Player loadPlayer() {
		return player;
	}

	public static int loadLevel() {
		return level;
	}

	private String removeAccent(String s) {
		if (s.contains("ï»¿")) {
			s = s.replace("ï»¿", "");
		}
		return s;
	}

	private void loadAll() {
		Long time = System.currentTimeMillis();
		loadColors();
		femaleNames_gr = new LinkedList<>();
		maleNames_gr = new LinkedList<>();
		femaleNames_en = new LinkedList<>();
		maleNames_en = new LinkedList<>();
		faces = new LinkedList<>();
		houses = new LinkedList<>();
		countries_gr = new LinkedList<>();
		countries_en = new LinkedList<>();
		faces2 = new LinkedList<>();
		facesSituations = new LinkedList<>();
		situations = new LinkedList<>();
		sortedDictionary_en = new LinkedList<>();
		sortedDictionary_gr = new LinkedList<>();
		splitEasy_en = new LinkedList<>();
		splitEasy_gr = new LinkedList<>();
		simpleShort_en = new LinkedList<>();
		simpleShort_gr = new LinkedList<>();
		animalList_gr = new LinkedList<>();
		animalList_en = new LinkedList<>();
		plantList_gr = new LinkedList<>();
		plantList_en = new LinkedList<>();
		occupationList_gr = new LinkedList<>();
		occupationList_en = new LinkedList<>();
		countryList_gr = new LinkedList<>();
		countryList_en = new LinkedList<>();
		nameList_gr = new LinkedList<>();
		nameList_en = new LinkedList<>();
//        simpleDict = new LinkedList<>();
		simpleDict_gr = new LinkedList<>();
		simpleDict_en = new LinkedList<>();
		anagramList_gr = new LinkedList<>();
		anagramList_en = new LinkedList<>();
		sayingsList_gr = new LinkedList<>();
		sayingsList_en = new LinkedList<>();
		hangmanPlants_gr = new LinkedList<>();
		hangmanPlants_en = new LinkedList<>();
		hangmanAnimals_gr = new LinkedList<>();
		hangmanAnimals_en = new LinkedList<>();
		hangmanOccupations_gr = new LinkedList<>();
		hangmanOccupations_en = new LinkedList<>();
		spotdifferences = new LinkedList<>();
		questMap = new HashMap<>();
//        questMaps = new HashMap<>();
		List<String> list;
		for (String s : FILES) {
			switch (s) {
			case WORD_SEARCH_GR:
				list = countries_gr;
				break;
			case WORD_SEARCH_EN:
				list = countries_en;
				break;
//                case WORDEX_EN:
//                    list = sortedDictionary_en;
//                    break;
			case WORDEX_GR:
				list = sortedDictionary_gr;
				break;
			case PLANTS_GR:
				list = plantList_gr;
				break;
			case PLANTS_EN:
				list = plantList_en;
				break;
			case COUNTRIES_GR:
				list = countryList_gr;
				break;
			case COUNTRIES_EN:
				list = countryList_en;
				break;
			case NAMES_GR:
				list = nameList_gr;
				break;
			case NAMES_EN:
				list = nameList_en;
				break;
			case OCCUPATIONS_GR:
				list = occupationList_gr;
				break;
			case OCCUPATIONS_EN:
				list = occupationList_en;
				break;
			case ANIMALS_GR:
				list = animalList_gr;
				break;
			case ANIMALS_EN:
				list = animalList_en;
				break;
			case SPLITWORDS_EN:
				System.out.println("en case");
				list = splitEasy_en;
				break;
			case SPLITWORDS_GR:
				System.out.println("gr case");
				list = splitEasy_gr;
				break;
			case FACES:
				list = faces;
				break;
			case HOUSES:
				list = houses;
				break;
			case FEMALE_NAMES_GR:
				list = femaleNames_gr;
				break;
			case FEMALE_NAMES_EN:
				list = femaleNames_en;
				break;
			case MALE_NAMES_GR:
				list = maleNames_gr;
				break;
			case MALE_NAMES_EN:
				list = maleNames_en;
				break;
			case SAYINGS_GR:
				list = sayingsList_gr;
				break;
			case SAYINGS_EN:
				list = sayingsList_en;
				break;
			case ANAGRAM_GR:
				list = anagramList_gr;
				break;
			case ANAGRAM_EN:
				list = anagramList_en;
				break;
			case HANGMAN_PLANTS_GR:
				list = hangmanPlants_gr;
				break;
			case HANGMAN_PLANTS_EN:
				list = hangmanPlants_en;
				break;
			case HANGMAN_ANIMALS_GR:
				list = hangmanAnimals_gr;
				break;
			case HANGMAN_ANIMALS_EN:
				list = hangmanAnimals_en;
				break;
			case HANGMAN_OCCUPATIONS_GR:
				list = hangmanOccupations_gr;
				break;
			case HANGMAN_OCCUPATIONS_EN:
				list = hangmanOccupations_en;
				break;
			case SPOTDIFFERENCES:
				System.out.println("Heress");
				list = spotdifferences;
				break;

			case SIMPLE_DICT_EN:
				list = simpleDict_en;
				break;
//                case SIMPLE_DICT_GR:
//                    list = simpleDict_gr;
//                break;
			default:
				System.out.println("default case");
				list = simpleDict_gr;
				break;

			}

			// InputStream inputStream;

			Object inputStream;
			try {

				inputStream = new FileInputStream(s);

				ByteArrayOutputStream result = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length;

				while ((length = ((FileInputStream) inputStream).read(buffer)) != -1) {
					result.write(buffer, 0, length);
				}

				String longLine = result.toString("UTF-8");

				if (longLine != null) {
					StringTokenizer st = new StringTokenizer(longLine, "\n");
					String nextToken;
					while (st.hasMoreTokens()) {
						nextToken = st.nextToken();

						nextToken = removeAccent(nextToken.trim());
						if (nextToken.length() != 0) {
							list.add(nextToken);
						}

					}
				}
			} catch (IOException e) {

				try {
					inputStream = getClass().getResourceAsStream(s.substring(3));
					ByteArrayOutputStream result = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int length;
					while ((length = ((InputStream) inputStream).read(buffer)) != -1) {
						result.write(buffer, 0, length);
					}

					String longLine = result.toString("UTF-8");

					if (longLine != null) {
						StringTokenizer st = new StringTokenizer(longLine, "\n");
						String nextToken;
						while (st.hasMoreTokens()) {
							nextToken = st.nextToken();

							nextToken = removeAccent(nextToken.trim());
							if (nextToken.length() != 0) {
								list.add(nextToken);
							}
						}
					}
				} catch (IOException a) {
					a.printStackTrace();
				}
			}
		}

//        FSTConfiguration confhidden = FSTConfiguration.createDefaultConfiguration();
//        confhidden.registerClass(Quests.class, HidingSpots.class, HiddenObjects.class);
//
//        ResultSet resultSets;
//        List<Quests> questLists = new LinkedList<>();
//        try {
//            resultSets = new DBManager().getQuests();
//
//            while ( resultSets.next() ){
//                Blob blob = resultSets.getBlob("data_blob");
//                try {
//                     questLists.add((Quests) confhidden.asObject(blob.getBytes(1, (int) blob.length())));
//                } catch ( Exception e ) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        } catch ( SQLException e ) {
//            e.printStackTrace();
//        }

//        for ( int i = 0; i < questLists.size(); i++ ) {
//            Quests quests = questLists.get(i);
//            String categories = quests.getCategory();
//
//            if ( questMaps.containsKey(categories) )
//                questMaps.get(categories).add(quests);
//            else
//                questMaps.put(categories, new ArrayList<>(Collections.singletonList(quests)));
//
//        }

		FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
		conf.registerClass(Quest.class, HidingSpot.class, HiddenObject.class);

		ResultSet resultSet;
		List<Quest> questList = new LinkedList<>();
//		try {
//			resultSet = DBManager.getQuests();
//
//			while (resultSet.next()) {
//				Blob blob = resultSet.getBlob("data_blob");
//				try {
//					questList.add((Quest) conf.asObject(blob.getBytes(1, (int) blob.length())));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		for (int i = 0; i < questList.size(); i++) {
			Quest quest = questList.get(i);
			String category = quest.getCategory();

			if (questMap.containsKey(category))
				questMap.get(category).add(quest);
			else
				questMap.put(category, new ArrayList<>(Collections.singletonList(quest)));

		}

		initWordex();
		initShort();

		countries_gr = GreekLangUtils.latinToGreek(countries_gr);
		sortedDictionary_gr = GreekLangUtils.latinToGreek(sortedDictionary_gr);
		plantList_gr = GreekLangUtils.latinToGreek(plantList_gr);
		countryList_gr = GreekLangUtils.latinToGreek(countryList_gr);
		nameList_gr = GreekLangUtils.latinToGreek(nameList_gr);
		occupationList_gr = GreekLangUtils.latinToGreek(occupationList_gr);
		animalList_gr = GreekLangUtils.latinToGreek(animalList_gr);
		splitEasy_gr = GreekLangUtils.latinToGreek(splitEasy_gr);
//        simpleDict = GreekLangUtils.latinToGreek(simpleDict);

		System.out.println("Optimised " + (System.currentTimeMillis() - time));
	}

	private void loadColors() {
//        InputStream inputStream;
		FileInputStream inputStream;
		String longLine = null;
		try {

//            inputStream = getClass().getResourceAsStream(COLORS);
			inputStream = new FileInputStream(COLORS);

			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;

			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}

			longLine = result.toString("UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (longLine != null) {
			StringTokenizer st = new StringTokenizer(longLine, "\n");
			String nextToken;
			while (st.hasMoreTokens()) {
				nextToken = st.nextToken();
				nextToken = removeAccent(nextToken);
				if (nextToken.length() != 0) {
					String[] color = nextToken.split("\\s+");
					colors.add(new Color(color[0], color[1], Boolean.parseBoolean(color[2])));
				}

			}
		}

	}

	private void initWordex() {

//            wordex_EN = sortedDictionary_en.toArray(new String[0]);
//            for (int i = 0; i < wordex_EN.length; i++) {
//                String tmp = wordex_EN[i];
//                wordex_EN[i] = sortWord(tmp);
//            }
//            wordex_EN = GreekLangUtils.latinToGreek(wordex_EN);

		wordex_GR = sortedDictionary_gr.toArray(new String[0]);
		for (int i = 0; i < wordex_GR.length; i++) {
			String tmp = wordex_GR[i];
			wordex_GR[i] = sortWord(tmp);
		}
		wordex_GR = GreekLangUtils.latinToGreek(wordex_GR);

	}

	private void initShort() {

		for (String s : simpleDict_gr) {
			if (s.length() < 11) {
				simpleShort_gr.add(s);
			}
		}
		// simpleShort_gr = GreekLangUtils.latinToGreek(simpleShort_gr);

		for (String s : simpleDict_en) {
			if (s.length() < 11) {
				simpleShort_en.add(s);
			}
		}

	}

	private String sortWord(String word) {
		char[] tmp = word.toCharArray();
		Arrays.sort(tmp);
		return new String(tmp);
	}

	public static String loadGame() {
		return selectedGame;
	}
}
