package android.pursuit.org.giphy;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class PreActivity extends AppCompatActivity {
    private String queryChoice;
    private boolean isOther = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);
        final Intent intent = new Intent(PreActivity.this,MainActivity.class);
        final EditText otherText = findViewById(R.id.other_editText);
        Button nextButton = findViewById(R.id.nextButton);
        RadioButton dogs = findViewById(R.id.radioButton_Dogs);
        RadioButton cats = findViewById(R.id.radioButton_Cats);
        final RadioButton other = findViewById(R.id.radioButton_Other);



        dogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        cats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOther){
                    queryChoice = otherText.getText().toString();
                }

                if(queryChoice != null) {

                    intent.putExtra("query", queryChoice);
                    startActivityForResult(intent, 456);

                }else{
                    Toast.makeText(PreActivity.this, "please enter something!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 123) {
            Toast.makeText(getApplicationContext(), "your query returned no results!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("queryChoice",queryChoice);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        queryChoice = savedInstanceState.getString("queryChoice");
    }

    private void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();

        EditText eo = findViewById(R.id.other_editText);

        switch(view.getId()) {

            case R.id.radioButton_Dogs:

                if (checked)
                    queryChoice = "dogs";
                isOther = false;
                eo.setVisibility(View.INVISIBLE);
                    break;

            case R.id.radioButton_Cats:

                if (checked)
                    queryChoice = "cats";
                isOther = false;
                eo.setVisibility(View.INVISIBLE);
                    break;

            case R.id.radioButton_Other:
                if (checked) {
                    isOther = true;
                    eo.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
