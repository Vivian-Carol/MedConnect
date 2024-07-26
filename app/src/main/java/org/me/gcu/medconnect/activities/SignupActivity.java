package org.me.gcu.medconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.User;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.List;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etMobile, etPass, etRepeatPass;
    private CheckBox cbAgree;
    private DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        AwsClientProvider.initialize(getApplicationContext());
        dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();

        initViewComponents();
    }

    private void initViewComponents() {
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPass = findViewById(R.id.etPass);
        etRepeatPass = findViewById(R.id.etRepeatPass);
        cbAgree = findViewById(R.id.cbAgree);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(view -> {
            if (etPass.getText().toString().equals(etRepeatPass.getText().toString())) {
                if (cbAgree.isChecked()) {
                    new Thread(() -> {
                        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                        List<User> userList = dynamoDBMapper.scan(User.class, scanExpression);

                        boolean isEmailUnique = true;
                        boolean isMobileUnique = true;

                        for (User user : userList) {
                            if (user.getEmail().equals(etEmail.getText().toString())) {
                                isEmailUnique = false;
                                break;
                            }
                            if (user.getMobile().equals(etMobile.getText().toString())) {
                                isMobileUnique = false;
                                break;
                            }
                        }

                        if (isEmailUnique && isMobileUnique) {
                            String userId = UUID.randomUUID().toString();
                            User newUser = new User();
                            newUser.setUserId(userId);
                            newUser.setUsername(etUsername.getText().toString());
                            newUser.setEmail(etEmail.getText().toString());
                            newUser.setMobile(etMobile.getText().toString());
                            newUser.setPassword(etPass.getText().toString());

                            try {
                                dynamoDBMapper.save(newUser);
                                runOnUiThread(() -> {
                                    Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(() -> Toast.makeText(SignupActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show());
                            }
                        } else {
                            boolean finalIsEmailUnique = isEmailUnique;
                            boolean finalIsMobileUnique = isMobileUnique;
                            runOnUiThread(() -> {
                                if (!finalIsEmailUnique) {
                                    Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                }
                                if (!finalIsMobileUnique) {
                                    Toast.makeText(SignupActivity.this, "Mobile number already exists", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(SignupActivity.this, "You must agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
