package my.edu.utar.individualassignment;

public class Score implements Comparable<Score> {
    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(other.score, this.score); // Sort in descending order
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }

}

