package my.edu.utar.individualassignment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*public class HighScoresActivity extends AppCompatActivity {

    private ListView scoresListView;
    private List<Score> scoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        scoresListView = findViewById(R.id.scores_list_view);
        scoresList = getTop25Scores();
        ScoresListAdapter adapter = new ScoresListAdapter(this, scoresList);
        scoresListView.setAdapter(adapter);
    }

    private List<Score> getTop25Scores() {
        SharedPreferences prefs = getSharedPreferences("scores", MODE_PRIVATE);
        Set<String> scoresSet = prefs.getStringSet("scoresSet", new HashSet<String>());
        List<Score> scoresList = new ArrayList<>();
        for (String scoreStr : scoresSet) {
            String[] splitScore = scoreStr.split(":");
            String name = splitScore[0];
            int score = Integer.parseInt(splitScore[1]);
            Score s = new Score(name, score);
            scoresList.add(s);
        }
        Collections.sort(scoresList, Collections.reverseOrder());
        if (scoresList.size() > 25) {
            scoresList = scoresList.subList(0, 25);
        }
        return scoresList;
    }

    private static class Score implements Comparable<Score> {
        String name;
        int score;

        Score(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public int compareTo(Score other) {
            return Integer.compare(this.score, other.score);
        }
    }

    private static class ScoresListAdapter extends ArrayAdapter<Score> {

        ScoresListAdapter(Context context, List<Score> scores) {
            super(context, 0, scores);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_high_scores, parent, false);
            }

            Score score = getItem(position);
            TextView nameTextView = convertView.findViewById(R.id.textView2);
            TextView scoreTextView = convertView.findViewById(R.id.textView3);

            nameTextView.setText(score.name);
            scoreTextView.setText(String.valueOf(score.score));

            return convertView;
        }
    }
}*/


/*public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        ListView scoresListView = findViewById(R.id.scores_list_view);
        List<String> scoresList = getTop25Scores();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, scoresList);
        scoresListView.setAdapter(adapter);
    }

    private List<String> getTop25Scores() {
        SharedPreferences prefs = getSharedPreferences("scores", MODE_PRIVATE);
        Set<String> scoresSet = prefs.getStringSet("scoresSet", new HashSet<String>());
        List<Integer> scoresList = new ArrayList<>();
        for (String scoreStr : scoresSet) {
            scoresList.add(Integer.parseInt(scoreStr.split(":")[1]));
        }
        Collections.sort(scoresList, Collections.reverseOrder());
        if (scoresList.size() > 25) {
            scoresList = scoresList.subList(0, 25);
        }
        List<String> topScoresList = new ArrayList<>();
        for (int i = 0; i < scoresList.size(); i++) {
            int rank = i + 1;
            int scoreValue = scoresList.get(i);
            String scoreStr = rank + ": " + scoreValue;
            topScoresList.add(scoreStr);
        }
        return topScoresList;
    }
}*/

/*
public class ScoreActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<Score> mScoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mPrefs = getSharedPreferences("high_scores", MODE_PRIVATE);
        mListView = findViewById(R.id.high_scores_list);
        mScoresList = new ArrayList<Score>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);

        // Read scores from shared preferences and add them to the list
        Map<String, ?> allEntries = mPrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            String name;
            int underscoreIndex = key.indexOf("_");
            if (underscoreIndex != -1) {
                name = key.substring(0, underscoreIndex);
            } else {
                name = "Unknown";
            }
            Integer scoreValue = (Integer) entry.getValue();
            Score score = new Score(name, scoreValue);
            mScoresList.add(score);
        }

        // Sort the list in descending order
        Collections.sort(mScoresList, Collections.reverseOrder());

        // Add the top 25 scores to the adapter
        for (int i = 0; i < 25 && i < mScoresList.size(); i++) {
            Score score = mScoresList.get(i);
            //mAdapter.add(score.toString());
            mAdapter.add(score.getName() + " - " + score.getScore());
        }
    }



}
*/

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Get the high scores from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("high_scores", MODE_PRIVATE);
        Map<String, ?> scoresMap = sharedPreferences.getAll();

        // Convert the scores map to a list of Score objects
        List<Score> scoresList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : scoresMap.entrySet()) {
            String name = entry.getKey();
            int score = Integer.parseInt(entry.getValue().toString());
            Score s = new Score(name, score);
            scoresList.add(s);
        }

        // Sort the scores list in descending order by score
        Collections.sort(scoresList);

        // Display the top 25 scores in a ListView
        ListView listView = findViewById(R.id.high_scores_list);
        ArrayAdapter<Score> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, scoresList.subList(0, Math.min(scoresList.size(), 25)));
        listView.setAdapter(adapter);
    }
}

