import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Will handle all the scores that have been made and store them
 * in a txt file.
 *
 * @author War& Miro
 */

public class HighScore {

    private String pathToHighscoreFile = "highscores.txt";
    private ArrayList<HighScoreEntry> highscores;

    public HighScore() {
        highscores = new ArrayList<HighScoreEntry>();
        fillHighscoreList();
    }

    //

    /**
    * Returns all the highscores sorted on descending order
     *
     * @return ArrayLists of highscores
     */
    public ArrayList<HighScoreEntry> getHighscores() {
        return this.highscores;
    }

    /**
     * Creates a txt file if there is no one created yet.
     */
    public void fillHighscoreList() {
        File f = new File(pathToHighscoreFile);
       if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("Could not create highscore file.");
            }
        }
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(f));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] nameAndScore = sCurrentLine.split("__");
                try {
                    this.addHighscore(nameAndScore[0], Integer.parseInt(nameAndScore[1])); // Add word to the highscore ArrayList
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("File format is corrupted.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Filled highscore list from: " + pathToHighscoreFile);
    }

    /**
     * Adds the score to the highscores.txt.
     *
     * @param name  Person who set the score
     * @param score The amount of score set.
     */
    public void addHighscore(String name, int score) {
        highscores.add(new HighScoreEntry(name, score));
        this.saveHighscores();
        sortHighscores();
    }

    /**
     * Saves the score after scores have been added.
     */
    public void saveHighscores() {
        File f = new File(pathToHighscoreFile);
        try {
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not write to file " + pathToHighscoreFile);
        }
    }

    public String toString() {
        String res = "";
        for(HighScoreEntry entry : this.highscores) {
            res += entry.getName() + "__" + entry.getScore() + "\n";
        }
        return res;
    }

    /**
     * Sorts the scores.
     */
    public void sortHighscores() {
        Collections.sort(this.highscores, new ascendingComparator());
    }

    /**
     * Compare method to see if the score is the new highscore or not.
     */
    public class ascendingComparator implements Comparator<HighScoreEntry> {
        @Override
        public int compare(HighScoreEntry o1, HighScoreEntry o2) {
            return o2.getScore().compareTo(o1.getScore());
        }
    }
}
