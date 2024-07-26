//package org.me.gcu.medconnect.activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.models.User;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//
//import java.util.List;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private EditText etEmail, etPassword;
//    private Button btnLogin;
//    private TextView txtNotAccount;
//    private DynamoDBMapper dynamoDBMapper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        AwsClientProvider.initialize(getApplicationContext());
//        dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//
//        initViewComponents();
//    }
//
//    private void initViewComponents() {
//        etEmail = findViewById(R.id.etEmail);
//        etPassword = findViewById(R.id.etPassword);
//        btnLogin = findViewById(R.id.btnLogin);
//        txtNotAccount = findViewById(R.id.txtNotAccount);
//
//        txtNotAccount.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
//
//        btnLogin.setOnClickListener(view -> new Thread(() -> {
//            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//            List<User> userList = dynamoDBMapper.scan(User.class, scanExpression);
//
//            boolean isUserFound = false;
//            String username = "";
//            String userId = "";
//            for (User user : userList) {
//                if (user.getEmail().equals(etEmail.getText().toString()) &&
//                        user.getPassword().equals(etPassword.getText().toString())) {
//                    isUserFound = true;
//                    username = user.getUsername();
//                    userId = user.getUserId();
//                    break;
//                }
//            }
//
//            if (isUserFound) {
//                String finalUsername = username;
//                String finalUserId = userId;
//                runOnUiThread(() -> {
//                    // Save userId to shared preferences
//                    getSharedPreferences("user_prefs", MODE_PRIVATE).edit().putString("userId", finalUserId).apply();
//
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("USERNAME", finalUsername);
//                    intent.putExtra("USER_ID", finalUserId);
//                    startActivity(intent);
//                });
//            } else {
//                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show());
//            }
//        }).start());
//    }
//}
//

package org.me.gcu.medconnect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.User;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView txtNotAccount;
    private DynamoDBMapper dynamoDBMapper;
    private static final String TAG = "LoginActivity";
    private static final String PREFS_NAME = "user_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AwsClientProvider.initialize(getApplicationContext());
        dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();

        initViewComponents();
    }

    private void initViewComponents() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtNotAccount = findViewById(R.id.txtNotAccount);

        txtNotAccount.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

        btnLogin.setOnClickListener(view -> new Thread(() -> {
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<User> userList = dynamoDBMapper.scan(User.class, scanExpression);

            boolean isUserFound = false;
            String username = "";
            String userId = "";
            for (User user : userList) {
                if (user.getEmail().equals(etEmail.getText().toString()) &&
                        user.getPassword().equals(etPassword.getText().toString())) {
                    isUserFound = true;
                    username = user.getUsername();
                    userId = user.getUserId();
                    break;
                }
            }

            if (isUserFound) {
                String finalUsername = username;
                String finalUserId = userId;
                runOnUiThread(() -> {
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USERNAME", finalUsername);
                    editor.putString("USER_ID", finalUserId);
                    editor.apply();

                    Log.d(TAG, "Login successful. Username: " + finalUsername + " , UserId: " + finalUserId);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                });
            } else {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show());
            }
        }).start());
    }
}
