package ro.pub.cs.systems.eim.practicaltest01var08;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;
public class PracticalTest01Var08PlayActivity extends AppCompatActivity {

    private final ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent resultIntent = new Intent();
            int id = view.getId();

            if (id == R.id.press_me_button) {
                if (Objects.equals(answer, leftEditText.getText().toString())) {
                    resultIntent.putExtra(Constants.SUM_RESULT, answer);
                    setResult(RESULT_OK, resultIntent);
                } else
                    setResult(RESULT_CANCELED, resultIntent);

            } else if (id == R.id.press_me_too_button) {

                setResult(RESULT_CANCELED, resultIntent);
            }

            finish();
        }
    }

    public  static  String answer;
    private EditText leftEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        TextView numberOfClicksTextView = findViewById(R.id.riddle);
        TextView numberOfClicksTextView2 = findViewById(R.id.riddle2);
        leftEditText = (EditText)findViewById(R.id.answer);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.CLICK_HISTORY)) {
            ArrayList<String> clickHistory = intent.getStringArrayListExtra(Constants.CLICK_HISTORY);

            if (clickHistory != null && clickHistory.size() >= 2) {
                numberOfClicksTextView.setText(clickHistory.get(0));
                numberOfClicksTextView2.setText(clickHistory.get(0));



                answer = clickHistory.get(1);

            }
        }

        Button okButton = findViewById(R.id.press_me_button);
        okButton.setOnClickListener(buttonClickListener);
        Button cancelButton = findViewById(R.id.press_me_too_button);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}

