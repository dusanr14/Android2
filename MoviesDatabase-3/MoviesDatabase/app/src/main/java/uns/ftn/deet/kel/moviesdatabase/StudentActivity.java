package uns.ftn.deet.kel.moviesdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import uns.ftn.deet.kel.moviesdatabase.sqlite.helper.DatabaseHelper;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Actor;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;

public class StudentActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper = MainActivity.databaseHelper;

    TextView txtStudName;
    TextView txtStudLastName;
    TextView txtIndex;
    TextView txtJmbg;
    Button btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        txtStudName = (TextView) findViewById(R.id.txtStudName);
        txtStudLastName = (TextView) findViewById(R.id.txtStudLastName);
        txtIndex = (TextView) findViewById(R.id.txtIndex);
        txtJmbg = (TextView) findViewById(R.id.txtJmbg);
        btnShow = (Button) findViewById(R.id.btnShow);

        Intent receivedIntent = getIntent();
        String recUser = receivedIntent.getStringExtra("key_username"); // Replace "key" with the same key used to send the data
        Toast.makeText(StudentActivity.this, "Dobrodosao i boljeg te nasao: "+recUser, Toast.LENGTH_SHORT).show();
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databaseHelper.findAdmin("admin","admin")){
                    Toast.makeText(StudentActivity.this, "PUSSSSSSSS", Toast.LENGTH_SHORT).show();}
                    int studID = databaseHelper.getStudentIDWithUserName(recUser);
                    Student student = new Student();
                    student = databaseHelper.getStudent(studID);
                    txtStudName.setText(student.getName());
                    txtStudLastName.setText(student.getLastName());
                    txtIndex.setText(student.getIndex());
                    txtJmbg.setText(student.getJmbg());
            }
        });


    }
}