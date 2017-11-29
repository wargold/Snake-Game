/**
 * Information about the person how set a score and
 * the score that person set.
 *
 * @author War & Miro
 */
public class HighScoreEntry {

    private int score;
    private String name;

    public HighScoreEntry(String name, int score) {
        this.score = score;
        this.name = name;
    }

    /**
     * Returns the score.
     *
     * @return the score
     */
    public Integer getScore() {
        return this.score;
    }

    /**
     * Returns the name of that person who set this score.
     *
     * @return name of a person
     */
    public String getName() {
        return name;
    }
}

