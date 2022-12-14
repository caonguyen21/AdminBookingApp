package com.example.adminbookingapp.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminbookingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText edt_Password, edt_Nhaplaipassword;
    Button DangKy;
    EditText edt_Hoten, edt_Sdt, edt_Email, edttenks;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        edt_Hoten = findViewById(R.id.edt_Hoten);
        edt_Email = findViewById(R.id.edt_Email);
        edt_Sdt = findViewById(R.id.edt_Sdt);
        edt_Password = findViewById(R.id.edt_Password);
        edt_Nhaplaipassword = findViewById(R.id.edt_Nhaplaipassword);
        edttenks = findViewById(R.id.edttenks);
        DangKy = findViewById(R.id.btn_Dangky);
        DangKy.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Dangky:
                DangKy();
                break;
        }
    }

    @SuppressLint("NotConstructor")
    private void DangKy() {
        String username = edt_Hoten.getText().toString().trim();
        String email = edt_Email.getText().toString().trim();
        String password = edt_Password.getText().toString().trim();
        String confirmpassword = edt_Nhaplaipassword.getText().toString().trim();
        String phone = edt_Sdt.getText().toString().trim();
        String tenks = edttenks.getText().toString().trim();

        if (tenks.isEmpty()) {
            edttenks.setError("Vui l??ng nh???p t??n kh??ch s???n!");
            edttenks.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            edt_Hoten.setError("Vui l??ng nh???p h??? t??n!");
            edt_Hoten.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            edt_Sdt.setError("Vui l??ng nh???p s??? ??i???n tho???i");
            edt_Sdt.requestFocus();
            return;
        }

        if (phone.length() > 12) {
            edt_Sdt.setError("S??? ??i???n tho???i kh??ng h??n 12 k?? t???!");
            edt_Sdt.requestFocus();
            return;
        }

        if (phone.length() < 9) {
            edt_Sdt.setError("S??? ??i???n tho???i h??n 9 k?? t???!");
            edt_Sdt.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edt_Email.setError("Vui l??ng nh???p email!");
            edt_Email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_Email.setError("Email ch??a h???p l???!");
            edt_Email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edt_Password.setError("Vui l??ng nh???p m???t kh???u m???i!");
            edt_Password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edt_Password.setError("M???t kh???u ph???i h??n 6 k?? t???!");
            edt_Password.requestFocus();
            return;
        }

        if (confirmpassword.isEmpty()) {
            edt_Nhaplaipassword.setError("Vui l??ng nh???p l???i m???t kh???u m???i!");
            edt_Nhaplaipassword.requestFocus();
            return;
        }

        if (!confirmpassword.equals(password)) {
            edt_Nhaplaipassword.setError("Kh??ng tr??ng v???i m???t kh???u m???i!");
            edt_Nhaplaipassword.requestFocus();
            return;
        }
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            databaseReference = FirebaseDatabase.getInstance().getReference("Owner");
                            Map<String, Object> owner = new HashMap<>();
                            owner.put("email", email);
                            owner.put("username", username);
                            owner.put("phone", phone);
                            owner.put("tenks", tenks);
                            owner.put("image", "1");

                            databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(owner);
                            Toast.makeText(SignUp.this, "T???o t??i kho???n th??nh c??ng.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, SignIn.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(SignUp.this, "T???o t??i kho???n th???t b???i.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}