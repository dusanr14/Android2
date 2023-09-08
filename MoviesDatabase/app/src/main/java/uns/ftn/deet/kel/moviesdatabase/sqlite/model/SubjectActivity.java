package uns.ftn.deet.kel.moviesdatabase.sqlite.model;

import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import uns.ftn.deet.kel.moviesdatabase.AdminActivity;
import uns.ftn.deet.kel.moviesdatabase.MainActivity;
import uns.ftn.deet.kel.moviesdatabase.R;
import uns.ftn.deet.kel.moviesdatabase.SeeStudentsActivity;

public class SubjectActivity extends AppCompatActivity {

    EditText txtSubjectName;
    EditText txtSubjectYear;
    Button btnAddSubject;
    Button btnBackToAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        txtSubjectName =  findViewById(R.id.txtSubjectName);
        txtSubjectYear =  findViewById(R.id.txtSubjectYear);

        btnAddSubject =  findViewById(R.id.btnAddSubject);
        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean correctYear = true;
                String subjName = txtSubjectName.getText().toString();
                String subjYear = txtSubjectYear.getText().toString();
                if(!subjYear.contains(String.valueOf('/'))){
                    correctYear = false;
                } else {
                    String[] str = subjYear.split("/");
                    if(Integer.parseInt(str[1]) != (Integer.parseInt(str[0]) + 1)){
                        correctYear = false;
                    }
                }
                if(correctYear == true){
                    Subject subject = new Subject(subjName,subjYear);
                    databaseHelper.createSubject(subject);
                    Toast.makeText(SubjectActivity.this, "Dodat predmet " + subjName, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SubjectActivity.this, "Neuspesno. Pogresan format godine. ", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnBackToAdmin = (Button) findViewById(R.id.btnBackToAdmin);
        btnBackToAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubjectActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}