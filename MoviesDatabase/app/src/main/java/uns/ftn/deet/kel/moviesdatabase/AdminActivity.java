package uns.ftn.deet.kel.moviesdatabase;

import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Admin;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;

public class AdminActivity extends AppCompatActivity {

    TextView txtAdminUser;
    TextView txtAdminPass;
    Button btnAddAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        txtAdminUser = (TextView) findViewById(R.id.txtAdminUser);
        txtAdminPass = (TextView) findViewById(R.id.txtAdminPass);
        btnAddAdmin = (Button) findViewById(R.id.btnAddAdmin);

        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databaseHelper.findAdmin("admin","admin")){
                    Toast.makeText(AdminActivity.this, "PUSSSSSSSS", Toast.LENGTH_SHORT).show();
                }
                String userAdmin = txtAdminUser.getText().toString();
                String passAdmin = txtAdminPass.getText().toString();
                Admin a = new Admin(userAdmin,passAdmin);
                databaseHelper.createAdmin(a);
            }
        });
    }
}