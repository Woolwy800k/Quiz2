package pb.edu.wi.pl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button trueButton;
private Button falseButton;
private Button nextButton;
private Button promptButton;
private static final int Request_Code_Prompt = 0;
private boolean answerWasShown;
private TextView questionTextView;
private static final String QuizTag = "QuizTag";
private static final String Key_Current_Index = "currentIndex";
public static final String Key_Extra_Answer = "pl.edu.pb.wi.quiz.correctAnswer";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QuizTag, "Wywołano onCreate");
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(Key_Current_Index);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        promptButton = findViewById(R.id.prompt_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questions.length;
                setNextQuestion();
            }
        });
        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(Key_Extra_Answer, correctAnswer);
                startActivityForResult(intent, Request_Code_Prompt);
            }
        });
        setNextQuestion();
    }
    private Question[] questions = new Question[] {
            new Question(R.string.q_one, false),
            new Question(R.string.q_two, true),
            new Question(R.string.q_three, false),
            new Question(R.string.q_four, false),
            new Question(R.string.q_five, true)
    };
    private int currentIndex=0;
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId=0;
        if(answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        }
        else {
            if(userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
            }
            else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QuizTag, "Wywołano onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QuizTag, "Wywołano onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QuizTag, "Wywołano onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QuizTag, "Wywołano onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QuizTag, "Wywołano onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QuizTag, "Wywołano onSaveInstanceState");
        outState.putInt(Key_Current_Index, currentIndex);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == Request_Code_Prompt) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}