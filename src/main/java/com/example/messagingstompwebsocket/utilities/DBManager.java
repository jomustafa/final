package com.example.messagingstompwebsocket.utilities;

import com.example.messagingstompwebsocket.games.Player;
import com.example.messagingstompwebsocket.games.Score;
import com.example.messagingstompwebsocket.games.visual.memoryquest.HiddenObject;
import com.example.messagingstompwebsocket.games.visual.memoryquest.HidingSpot;
import com.example.messagingstompwebsocket.games.visual.memoryquest.Quest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.nustaq.serialization.FSTConfiguration;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {

	// private final txtFilePath = "scores/score.txt";
	private final String URL = "jdbc:postgresql://ec2-34-248-165-3.eu-west-1.compute.amazonaws.com:5432/d2ih864edca7sk?password=e57009808053709e25db6252f59e36590116990fe479cf0e1218efa854715eea&sslmode=require&user=zaahtcslbtazop";
	private static final String FILE_HEADER_PLAYER = "GAME,POINTS,DATE,COMPLETION TIME,LEVEL,COMPLETION PROGRESS,MISSED CLICKS";
	private static final String FILE_HEADER_GAME = "PLAYER,POINTS,DATE,COMPLETION TIME,LEVEL,COMPLETION PROGRESS,MISSED CLICKS";

	private Connection conn;

	private ResourceBundle bundle;
	private Locale locale;

	public DBManager() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Cannot find db driver: " + ex.getMessage());
		}
		try {
//            System.setProperty("derby.system.home", "");
			conn = DriverManager.getConnection(URL);
		} catch (SQLException ex) {
			System.out.println("Cannot find db: " + ex.getMessage() + "\nRecreating DB...");
//            createDB();
		}

//        createDB();
	}

//    Write BLOB example
//    ByteArrayOutputStream b=new ByteArrayOutputStream();
//        ImageIO.write(img[k], "jpeg", b);
//    byte[] imageInByte = b.toByteArray();
//        b.close();
//    InputStream fis = new ByteArrayInputStream(imageInByte);
//        stmt2.setBlob(1, fis);
//        stmt2.executeUpdate();

	private void createDB() {

		locale = Locale.getDefault();
		bundle = ResourceBundle.getBundle("language", locale);

		try {
			conn = DriverManager.getConnection(URL);
//            execute("create table PLAYERS (ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), PLAYER VARCHAR(100) UNIQUE, PRIMARY KEY (ID))");
//            execute("create table GAMES (ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), GAME VARCHAR(100) UNIQUE, PRIMARY KEY (ID))");
//            execute("create table SCORES (DATE DATE NOT NULL, POINTS INTEGER NOT NULL, GAME INTEGER NOT NULL REFERENCES GAMES(ID), PLAYER INTEGER NOT NULL REFERENCES PLAYERS(ID), COMPLETION_TIME INTEGER NOT NULL, LEVEL INTEGER NOT NULL, COMPLETION_PROGRESS DOUBLE NOT NULL, MISSED_CLICKS INTEGER NOT NULL)");
//            execute("create table QUESTS (ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), DATA_BLOB BLOB NOT NULL, PRIMARY KEY (ID))");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("SPLIT_WORDS") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("WORD_SEARCH") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("WORDEX") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("COLORED_BOXES") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("WORDS_FROM_A_LETTER") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("NAMES_ANIMALS_PLANTS") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("CORRECT_SPELLING") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("CORRECT_SPELLING_2") + "')");
			execute("insert into GAMES (GAME) VALUES ('Î¤Î‘Î™Î¡Î™Î‘ÎžÎ• ÎšÎ‘Î¡Î¤Î•Î£')");
			execute("insert into  GAMES (GAME) VALUES ('Î Î¡ÎŸÎ£Î©Î ÎŸ-ÎŸÎ�ÎŸÎœÎ‘-Î£Î Î™Î¤Î™')");
			execute("insert into  GAMES (GAME) VALUES ('Î•Î�Î¤ÎŸÎ Î™Î£Î• Î¤ÎŸ Î Î¡ÎŸÎ£Î©Î ÎŸ')");
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("HANGMAN") + "')");
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("ANAGRAM") + "')");

			// don't need to translate
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("MEMORY_QUEST") + "')");
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("HIDDEN_OBJECTS") + "')");
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("PUZZLE") + "')");
			execute("insert into  GAMES (GAME) VALUES ('SHOP')");
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("FIND_NEXT") + "')");
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("FIND_PAIRS") + "')");
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("SUPERMARKET") + "')");
			execute("insert into  GAMES (GAME) VALUES ('" + bundle.getString("SPOT_DIFFERENCES") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("FIND_PATTERNS") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("FIND_NEXT_BOX") + "')");
			execute("insert into GAMES (GAME) VALUES ('" + bundle.getString("QUICK_REACTIONS") + "')");

			addQuest(QuestCollection.generate());
//            addQuests(HiddenCollection.generate());

			System.out.println("DB recreated succesfully!");
		} catch (SQLException ex) {
			System.out.println("Java DB Driver problem - cannot create DB: " + ex.getMessage());
		}
	}

	private void execute(String statement) {
		try {
			Statement s = conn.createStatement();
			int result = s.executeUpdate(statement);
			System.out.println(statement);
			System.out.println(result + " rows were affected");
		} catch (SQLException s) {
		}
	}

	private void insert(String tableName, String columns, String values) {
		try {
			Statement s = conn.createStatement();
			String query = "INSERT INTO " + tableName + "(" + columns + ")" + " VALUES (" + values + ")";
			System.out.println(query);
			int result;
			result = s.executeUpdate(query);
			System.out.println(result + " rows were inserted");
		} catch (SQLException s) {
		}
	}

	public ResultSet getDataRecords(String SQL) throws SQLException {
		try {
			Statement s = conn.createStatement();
			return s.executeQuery(SQL);
		} catch (SQLException ex) {
			throw new SQLException("Query Failed: " + ex.getMessage());
		}
	}

	public TreeSet<Player> getPlayers() throws SQLException {
		TreeSet<Player> players = new TreeSet();
		String SQL = "SELECT * FROM PLAYERS";
		ResultSet results = getDataRecords(SQL);
		while (results.next()) {
			players.add(new Player(results.getString("PLAYER")));
		}
		return players;
	}

	public LinkedList<Player> getAllPlayers() throws SQLException {
		LinkedList<Player> players = new LinkedList<Player>();
		String SQL = "SELECT * FROM PLAYERS";
		ResultSet results = getDataRecords(SQL);
		while (results.next()) {
			players.add(new Player(results.getString("PLAYER"), results.getString("ID")));
		}
		return players;
	}

	public void clearQuests() {
		execute("DELETE FROM QUESTS WHERE true");
	}

	public ResultSet getQuests() throws SQLException {
		String SQL = "SELECT * FROM QUESTS";

		return getDataRecords(SQL);
	}

	public void addQuest(List<Quest> questList) {

		String sql = "INSERT INTO QUESTS(data_blob) VALUES(?)";

		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
		conf.registerClass(Quest.class, HidingSpot.class, HiddenObject.class);

		for (Quest quest : questList) {

			byte[] bytes = conf.asByteArray(quest);
			try {
				statement.setBinaryStream(1, new ByteArrayInputStream(bytes), bytes.length);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public LinkedList<String> getGames() throws SQLException {
		LinkedList<String> games = new LinkedList<>();
		String SQL = "SELECT * FROM GAMES";
		ResultSet results = getDataRecords(SQL);
		while (results.next()) {
			games.add(results.getString("GAME"));
		}
		return games;
	}

	private LinkedList<Score> getScores(Player player) throws SQLException {
		LinkedList<Score> scores = new LinkedList<>();
		String SQL = "SELECT * FROM SCORES, PLAYERS, GAMES WHERE PLAYERS.ID = "
				+ "SCORES.PLAYER AND GAMES.ID = SCORES.GAME AND " + "PLAYERS.PLAYER = '" + player.getName() + "'";
		ResultSet results = getDataRecords(SQL);
		while (results.next()) {
			String game = results.getString("GAME");
			Date date = results.getDate("DATE");
			int points = results.getInt("POINTS");
			int comp = results.getInt("COMPLETION_TIME");
			int lvl = results.getInt("LEVEL");
			double progress = results.getDouble("COMPLETION_PROGRESS");
			int missed = results.getInt("MISSED_CLICKS");

			scores.add(new Score(player, date, game, points, comp, lvl, progress, missed));
		}
		return scores;
	}

	public LinkedList<Score> getScores(String game) throws SQLException {
		LinkedList<Score> scores = new LinkedList<>();
		String SQL = "SELECT MISSED_CLICKS, COMPLETION_PROGRESS, LEVEL, COMPLETION_TIME, DATE, POINTS, PLAYERS.PLAYER FROM SCORES, GAMES, PLAYERS WHERE "
				+ "SCORES.GAME = GAMES.ID AND SCORES.PLAYER = PLAYERS.ID " + "AND GAMES.GAME = '" + game + "'";
		ResultSet results = getDataRecords(SQL);
		while (results.next()) {
			String player = results.getString("PLAYER");
			Date date = results.getDate("DATE");
			int points = results.getInt("POINTS");
			int comp = results.getInt("COMPLETION_TIME");
			int lvl = results.getInt("LEVEL");
			double progress = results.getDouble("COMPLETION_PROGRESS");
			int missed = results.getInt("MISSED_CLICKS");
			scores.add(new Score(new Player(player), date, game, points, comp, lvl, progress, missed));
		}
		return scores;
	}

	public LinkedList<Score> getScores(String name, String id, String game) throws SQLException {
		LinkedList<Score> scores = new LinkedList<>();
		String SQL = "SELECT DATE, POINTS, COMPLETION_TIME, LEVEL, COMPLETION_PROGRESS, MISSED_CLICKS, PLAYERS.PLAYER, PLAYERS.ID, GAMES.GAME FROM SCORES, PLAYERS, GAMES"
				+ " WHERE PLAYERS.ID = SCORES.PLAYER" + " AND GAMES.ID = SCORES.GAME";
		
		if(!game.equals("")) {
			SQL += " AND GAMES.GAME ='" + game + "'";
		}
		
		if(!name.equals("")) {
			SQL += " AND PLAYERS.PLAYER ='" + name+ "'";
		}
		
		if(!id.equals("")) {
			try{
				SQL += " AND PLAYERS.ID ='" + Long.parseLong(id) + "'";
			}catch(Exception e) {
				System.out.println("ID: " + id + "is not a number");
			}
			
		}
		SQL += ";";
		System.out.println(SQL);
		ResultSet results = getDataRecords(SQL);
		while (results.next()) {
			Date date = results.getDate("DATE");
			int points = results.getInt("POINTS");
			int comp = results.getInt("COMPLETION_TIME");
			int lvl = results.getInt("LEVEL");
			double progress = results.getDouble("COMPLETION_PROGRESS");
			int missed = results.getInt("MISSED_CLICKS");
			game = results.getString("GAME");
			String player = results.getString("PLAYER");
			id = results.getString("ID");
			scores.add(new Score(new Player(player, id), date, game, points, comp, lvl, progress, missed));
		}
		return scores;
	}

	public void printRecords(Player player) throws IOException {
		LinkedList<Score> scores = new LinkedList<>();
		try {
			scores = this.getScores(player);
			// setting the folder where the files will be created
			// String userHomeFolder = System.getProperty("user.home");
			// String otherFolder = userHomeFolder + File.separator + "Desktop" +
			// File.separator + "Scores";
			// File textFile = new File("./scores", player + ".xls");

			Workbook wb = new HSSFWorkbook();
			// CreationHelper createHelper = wb.getCreationHelper();
			Sheet sheet1 = wb.createSheet("Results");
			Row row = sheet1.createRow(0);
			// Create a cell and put a value in it.

			Cell cell1 = row.createCell(0);
			cell1.setCellValue("GAME");
			Cell cell2 = row.createCell(1);
			cell2.setCellValue("POINTS");
			Cell cell3 = row.createCell(2);
			cell3.setCellValue("DATE");
			Cell cell4 = row.createCell(3);
			cell4.setCellValue("COMPLETION TIME");
			Cell cell5 = row.createCell(4);
			cell5.setCellValue("LEVEL");
			Cell cell6 = row.createCell(5);
			cell6.setCellValue("COMPLETION PROGRESS");
			Cell cell7 = row.createCell(6);
			cell7.setCellValue("MISSED CLICKS");
			Row row1 = sheet1.createRow(1);

			FileOutputStream fileOut = new FileOutputStream("./scores/" + player + ".xls");

			// creating new file and it structure

			// BufferedWriter bw = new BufferedWriter(new FileWriter(textFile))) {
			// bw.append(FILE_HEADER_PLAYER);
			// bw.newLine();
			int i = 2;
			Row row2;
			for (Score score : scores) {
				String SQL = "SELECT GAME FROM GAMES WHERE ID = " + score.getGame() + "";
				ResultSet results = getDataRecords(SQL);
				String gamename = "";
				while (results.next()) {
					gamename = results.getString("GAME");
				}

				row2 = sheet1.createRow(i);
				Cell cellName = row2.createCell(0);
				cellName.setCellValue(gamename);
				Cell cellPoints = row2.createCell(1);
				cellPoints.setCellValue(score.getPoints());
				Cell cellDate = row2.createCell(2);
				cellDate.setCellValue(score.getDate().toString());
				Cell cellTime = row2.createCell(3);
				cellTime.setCellValue(score.getCompTime());
				Cell cellLevel = row2.createCell(4);
				cellLevel.setCellValue(score.getLevel());
				Cell cellPro = row2.createCell(5);
				cellPro.setCellValue(String.valueOf(score.getProgress() * 100));
				Cell cellCli = row2.createCell(6);
				cellCli.setCellValue(String.valueOf(score.getMissedClicks()));
				// Row row3 = sheet1.createRow(i+1);
				i++;

				/*
				 * bw.newLine(); bw.append(gamename + ","); bw.append(score.getPoints()+ ",");
				 * bw.append(score.getDate() + ","); bw.append(score.getCompTime()+ ",");
				 * bw.append(score.getLevel() + ",");
				 * bw.append(String.valueOf(score.getProgress()*100) + ",");
				 * bw.append(String.valueOf(score.getMissedClicks())); bw.newLine();
				 */
			}
			wb.write(fileOut);
			fileOut.close();
		} catch (SQLException ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void printRecordsGame(String game) throws IOException {
		LinkedList<Score> scores = new LinkedList<>();
		try {
			scores = this.getScores(game);
			// setting the folder where the files will be created
			// String userHomeFolder = System.getProperty("user.home");
			// String otherFolder = userHomeFolder + File.separator + "Desktop" +
			// File.separator + "Scores";
			// File textFile = new File("./scores", game + ".xls");

			Workbook wb = new HSSFWorkbook();
			// CreationHelper createHelper = wb.getCreationHelper();
			Sheet sheet1 = wb.createSheet("Results");
			Row row = sheet1.createRow(0);
			// Create a cell and put a value in it.

			Cell cell1 = row.createCell(0);
			cell1.setCellValue("GAME");
			Cell cell2 = row.createCell(1);
			cell2.setCellValue("POINTS");
			Cell cell3 = row.createCell(2);
			cell3.setCellValue("DATE");
			Cell cell4 = row.createCell(3);
			cell4.setCellValue("COMPLETION TIME");
			Cell cell5 = row.createCell(4);
			cell5.setCellValue("LEVEL");
			Cell cell6 = row.createCell(5);
			cell6.setCellValue("COMPLETION PROGRESS");
			Cell cell7 = row.createCell(6);
			cell7.setCellValue("MISSED CLICKS");
			Row row1 = sheet1.createRow(1);

			FileOutputStream fileOut = new FileOutputStream("./scores/" + game + ".xls");

			// creating new file and it structure

			// BufferedWriter bw = new BufferedWriter(new FileWriter(textFile))) {
			// bw.append(FILE_HEADER_PLAYER);
			// bw.newLine();
			int i = 2;
			Row row2;
			for (Score score : scores) {
				/*
				 * String SQL = "SELECT PLAYER FROM PLAYERS WHERE NAME = "+score.getPlayer()+"";
				 * ResultSet results = getDataRecords(SQL); String playername = ""; while
				 * (results.next()) { playername = results.getString("PLAYER"); }
				 */

				row2 = sheet1.createRow(i);
				Cell cellName = row2.createCell(0);
				cellName.setCellValue(score.getPlayer().getName());
				Cell cellPoints = row2.createCell(1);
				cellPoints.setCellValue(score.getPoints());
				Cell cellDate = row2.createCell(2);
				cellDate.setCellValue(score.getDate().toString());
				Cell cellTime = row2.createCell(3);
				cellTime.setCellValue(score.getCompTime());
				Cell cellLevel = row2.createCell(4);
				cellLevel.setCellValue(score.getLevel());
				Cell cellPro = row2.createCell(5);
				cellPro.setCellValue(String.valueOf(score.getProgress() * 100));
				Cell cellCli = row2.createCell(6);
				cellCli.setCellValue(String.valueOf(score.getMissedClicks()));
				// Row row3 = sheet1.createRow(i+1);
				i++;

				/*
				 * bw.newLine(); bw.append(score.getPlayer().getName()+ ",");
				 * bw.append(score.getPoints()+ ","); bw.append(score.getDate() + ",");
				 * bw.append(score.getCompTime()+ ","); bw.append(score.getLevel() + ",");
				 * bw.append(String.valueOf(score.getProgress()*100) + ",");
				 * bw.append(String.valueOf(score.getMissedClicks())); bw.newLine();
				 */
			}
			wb.write(fileOut);
			fileOut.close();
		} catch (SQLException ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void recordScore(String player, String game, int points, int compTime, int lvl, double compProg,
			int missedClicks) {
		try {
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			System.out.println("utilDate:" + utilDate);
			System.out.println("sqlDate:" + sqlDate);
			String SQL = "";
			ResultSet results;
//            String SQL = "SELECT ID FROM PLAYERS WHERE PLAYER = '" + player + "'";
//            System.out.println(SQL);
//            ResultSet results = getDataRecords(SQL);
			long playerID = Long.parseLong(player);
//            while (results.next()) {
//                playerID = results.getInt("ID");
//            }
			SQL = "SELECT ID FROM GAMES WHERE GAME = '" + game + "'";
			System.out.println(SQL);
			results = getDataRecords(SQL);
			int gameID = 0;
			while (results.next()) {
				gameID = results.getInt("ID");
			}
			if (playerID > 0 && gameID > 0) {
				insert("SCORES",
						"PLAYER, GAME, DATE, POINTS, COMPLETION_TIME, LEVEL, COMPLETION_PROGRESS, MISSED_CLICKS",
						playerID + ", " + gameID + ", '" + sqlDate + "', " + points + ", " + compTime + ", " + lvl
								+ ", " + compProg + ", " + missedClicks);
				System.out.println("Added to db");
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void recordPlayer(String ID, String name) {

		/*
		 * INSERT INTO public.players( id, player) VALUES (2, ab);
		 */
		try {
			Statement s = conn.createStatement();
			String query = "INSERT INTO PLAYERS(id, player) VALUES(" + ID + ", '" + name + "');";
			System.out.println(query);
			int result;
			result = s.executeUpdate(query);
			System.out.println(result + " rows were inserted");
		} catch (SQLException s) {
		}
	}

	public void recordGame(String game) {
		insert("GAMES", "GAME", "'" + game + "'");
	}

	public void deletePlayer(String name) throws SQLException {
		String SQL = "SELECT ID FROM PLAYERS WHERE PLAYER = '" + name + "'";
		System.out.println(SQL);
		ResultSet results = getDataRecords(SQL);
		int id = 0;
		while (results.next()) {
			id = results.getInt("ID");
		}
		if (id > 0) {
			SQL = "DELETE FROM SCORES WHERE PLAYER = " + id;
			System.out.println(SQL);
			conn.createStatement().executeUpdate(SQL);
			SQL = "DELETE FROM PLAYERS WHERE ID = " + id;
			System.out.println(SQL);
			conn.createStatement().executeUpdate(SQL);
		}
	}

	public void renamePlayer(String oldName, String newName) {
		try {
			String SQL = "UPDATE PLAYERS SET PLAYER = '" + newName + "' WHERE PLAYER = '" + oldName + "'";
			System.out.println(SQL);
			conn.createStatement().executeUpdate(SQL);
			SQL = "SELECT ID FROM PLAYERS WHERE PLAYER = '" + oldName + "'";
			System.out.println(SQL);
			ResultSet results = getDataRecords(SQL);
			int id = 0;
			while (results.next()) {
				id = results.getInt("ID");
			}
			if (id > 0) {
				SQL = "UPDATE SCORES SET PLAYER = '" + newName + "' WHERE ID = '" + id + "'";
				System.out.println(SQL);
				conn.createStatement().executeUpdate(SQL);
			}
		} catch (SQLException s) {
		}
	}

//    public void addQuests(List<Quests> questLists) {
//        String sql = "INSERT INTO QUESTS(data_blob) VALUES(?)";
//
//
//        PreparedStatement statement = null;
//        try {
//            statement = conn.prepareStatement(sql);
//        } catch ( SQLException e ) {
//            e.printStackTrace();
//        }

//        FSTConfiguration confhidden = FSTConfiguration.createDefaultConfiguration();
//        confhidden.registerClass(Quests.class, HidingSpots.class, HiddenObjects.class);
//
//        for ( Quests quests : questLists ){
//
//            byte[] bytes = confhidden.asByteArray(quests);
//            try {
//                statement.setBinaryStream(1,
//                        new ByteArrayInputStream(bytes), bytes.length);
//                statement.executeUpdate();
//            } catch ( SQLException e ) {
//                e.printStackTrace();
//            }
//
//        }

//    }
}
