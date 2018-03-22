package bleizing.punyatemenuser;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends Activity {

    private static final String TAG = "SignupActivity";

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;

    private PrefManager prefManager;
//    private FirebaseAuth auth;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
//        auth = FirebaseAuth.getInstance();

        prefManager = new PrefManager(this);

        // Request Queue Volley Network Connection
        requestQueue = Volley.newRequestQueue(SignupActivity.this);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
//                finish();
//            }
//        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginForm.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                String emailArr[] = email.split("@");
                String formatEmail = emailArr[1].substring(0, 5);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!formatEmail.equals("yahoo")) {
                        if (!formatEmail.equals("gmail")) {
                            Toast.makeText(getApplicationContext(), "Enter valid email address using yahoo or gmail!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
//                auth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//
////                                progressBar.setVisibility(View.GONE);
//                                // If sign in fails, display a message to the user. If sign in succeeds
//                                // the auth state listener will be notified and logic to handle the
//                                // signed in user can be handled in the listener.
//                                if (!task.isSuccessful()) {
//                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
//                                            Toast.LENGTH_SHORT).show();
//                                } else {

                                    // Create a JSON Object
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("email", email);
                                        jsonObject.put("password", password);
                                        jsonObject.put("type", "registerCalonPenyewa");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Log.d(TAG, "JSON Request = " + jsonObject.toString());

                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetAPI.URL, jsonObject, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            progressBar.setVisibility(View.GONE);
                                            Log.d(TAG, "registerResponse : " + response);

                                            try {
                                                String type = response.getString("type");

                                                if (type.equals("success")) {
                                                    int id = Integer.parseInt(response.getString("id"));

                                                    CalonPenyewa calonPenyewa = new CalonPenyewa(id, email);
                                                    Model.setCalonPenyewa(calonPenyewa);

                                                    prefManager.setUser(calonPenyewa);

                                                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(SignupActivity.this, "Email Sudah Terdaftar, Silahkan Register Dengan Email Lainnya", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e(TAG, "error registerPenyewa : " + error);
                                            Toast.makeText(SignupActivity.this, "Tidak Dapat Terhubung ke Server, Register Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    jsonObjectRequest.setTag(TAG);
                                    requestQueue.add(jsonObjectRequest);

//                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                                    finish();
//                                }
//                            }
//                        });
//
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
