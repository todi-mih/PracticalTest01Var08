package ro.pub.cs.systems.eim.practicaltest01var08;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
public class PracticalTest01Var08MainActivity extends AppCompatActivity {
    private EditText leftEditText;
    private EditText rightEditText;

    private final ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String left = leftEditText.getText().toString();
            String right = rightEditText.getText().toString();

            if (view.getId() == R.id.navigate_to_secondary_activity_button) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var08PlayActivity.class);
                ArrayList<String> clickHistory = new ArrayList<>();
                clickHistory.add(left);
                clickHistory.add(right);

                intent.putStringArrayListExtra(Constants.CLICK_HISTORY, clickHistory);

                startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);

                if ( serviceStatus == Constants.SERVICE_STOPPED) {
                    Intent intent2 = new Intent(getApplicationContext(), PracticalTest01Var08Service.class);
                    intent2.putExtra(Constants.FIRST_NUMBER, right);
                    getApplicationContext().startService(intent2);
                    serviceStatus = Constants.SERVICE_STARTED;
                }

            }

        }
    }
    private final IntentFilter intentFilter = new IntentFilter();
    private int serviceStatus = Constants.SERVICE_STOPPED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var08_main);

        leftEditText = findViewById(R.id.left_edit_text);
        rightEditText = findViewById(R.id.right_edit_text);
        Button pressMeButton = findViewById(R.id.navigate_to_secondary_activity_button);
        pressMeButton.setOnClickListener(buttonClickListener);


        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.Ans)) {
                leftEditText.setText(savedInstanceState.getString(Constants.Ans));
            }

        }


    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.Ans, global);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.Ans)) {
            leftEditText.setText(savedInstanceState.getString(Constants.Ans));
            //Log.d("Restore", Objects.requireNonNull(savedInstanceState.getString(Constants.Ans)));
           Log.d("Restore", Constants.Ans);
           /// Log.d("Restore", savedInstanceState.getString(Constants.Ans));
            //Log.d("Restore", global);
        }
    }
    public static String global;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == -1) {
                String ans = intent.getStringExtra(Constants.SUM_RESULT);
                global = ans;
                Toast.makeText(this, "Victory " + ans , Toast.LENGTH_LONG).show();
                Log.d("Victory", ans);
            } else {
                Toast.makeText(this, "Defeat", Toast.LENGTH_LONG).show();
                Log.d("Defeat","Defeat");
            }

        }
    }

    private final MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private static class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, Objects.requireNonNull(intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA)));
            //Toast.makeText(this, "Defeat", Toast.LENGTH_LONG).show();

        }
    }



    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter);
        }

    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

}