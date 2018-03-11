package bleizing.punyatemenuser;

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

public class LoginForm extends AppCompatActivity {

    private static final String TAG = "LoginForm";

    private EditText inputEmail, inputPassword;
//    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    private PrefManager prefManager;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
//        auth = FirebaseAuth.getInstance();

        // Request Queue Volley Network Connection
        requestQueue = Volley.newRequestQueue(LoginForm.this);
        prefManager = new PrefManager(this);

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginForm.this, MainActivity.class));
//            finish();
//        }
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_form);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginForm.this, SignupActivity.class));
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginForm.this, ResetPasswordActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
//                auth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(LoginForm.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                // If sign in fails, display a message to the user. If sign in succeeds
//                                // the auth state listener will be notified and logic to handle the
//                                // signed in user can be handled in the listener.
////                                progressBar.setVisibility(View.GONE);
//                                if (!task.isSuccessful()) {
//                                    // there was an error
//                                    if (password.length() < 6) {
//                                        inputPassword.setError(getString(R.string.minimum_password));
//                                    } else {
//                                        Toast.makeText(LoginForm.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
//                                    }
//                                } else {
                                    // Create a JSON Object
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("email", email);
                                        jsonObject.put("password", password);
                                        jsonObject.put("type", "loginCalonPenyewa");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Log.d(TAG, "JSON Request = " + jsonObject.toString());

                                    // Make a Request
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetAPI.URL, jsonObject, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            progressBar.setVisibility(View.GONE);
                                            Log.d(TAG, "loginResponse : " + response);
                                            try {
                                                String type = response.getString("type");

                                                if (type.equals("success")) {
                                                    int id = Integer.parseInt(response.getString("id"));
                                                    String nama = response.getString("nama");
                                                    String tempat_lahir = response.getString("tempat_lahir");
                                                    String tanggal_lahir = response.getString("tanggal_lahir");
                                                    String jekel = response.getString("jekel");
                                                    String alamat = response.getString("alamat");
                                                    String no_hp = response.getString("no_hp");
                                                    String no_ktp = response.getString("no_ktp");
                                                    String email = response.getString("email");
                                                    String foto_ktp = response.getString("foto_ktp");

                                                    CalonPenyewa calonPenyewa = new CalonPenyewa(id, nama, tempat_lahir, tanggal_lahir, jekel, alamat, no_hp, no_ktp, email, foto_ktp);
                                                    Model.setCalonPenyewa(calonPenyewa);

                                                    prefManager.setUser(calonPenyewa);

                                                    Intent intent = new Intent(LoginForm.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                } else if (type.equals("failed")) {
                                                    Toast.makeText(LoginForm.this, "Email atau Password Anda Salah", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e(TAG, "error loginPenyewa : " + error);
                                            Toast.makeText(LoginForm.this, "Tidak Dapat Terhubung ke Server, Login Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    jsonObjectRequest.setTag(TAG);
                                    requestQueue.add(jsonObjectRequest);

//                                    Intent intent = new Intent(LoginForm.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }
//                        });
            }
        });
    }
}
