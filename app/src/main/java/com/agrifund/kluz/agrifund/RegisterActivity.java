package com.agrifund.kluz.agrifund;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "http://takethecorner.com/login_app/sign_up.php";
    ProgressDialog progressDialog;

    private EditText signupInputName, signupInputEmail, signupInputPassword, signupInputUsername,signupName;
    private Button btnSignUp;
    private Button btnLinkLogin;
    private RadioGroup genderRadioGroup;
    private Spinner spinner1;
    List<String> districts = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        signupInputName = (EditText) findViewById(R.id.signup_first_name);
        signupName = (EditText) findViewById(R.id.signup_last_name);
        signupInputEmail = (EditText) findViewById(R.id.signup_input_phone);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);
        signupInputUsername = (EditText) findViewById(R.id.signup_username);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(RegisterActivity.this);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkLogin = (Button) findViewById(R.id.btn_link_login);

        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });

        //List<String> districts = new ArrayList<String>();
            //districts.add("Kampala");
            //districts.add("Jinja");
            //districts.add("Iganga");




        getDistrict();
    }

    private void submitForm() {

        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        String gender;
        if(selectedId == R.id.female_radio_btn)
            gender = "Female";
        else
            gender = "Male";

        registerUser(signupInputName.getText().toString(),
                     signupName.getText().toString(),
                     signupInputEmail.getText().toString(),
                     signupInputUsername.getText().toString(),
                     signupInputPassword.getText().toString(),
                     gender);
    }

    private void registerUser(final String firstname, final  String lastname, final String phone,final String username, final String password , final  String gender) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Regestering "+ lastname +". Please Wait...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("phone", phone);
                params.put("username", username);
                params.put("password", password);
                params.put("gender", gender);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getDistrict(){

        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

        String url = "http://takethecorner.com/login_app/get_results.php";

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        String data = "";
                        int count = 0;
                        while (count<response.length()){
                            try {
                                JSONObject obj = response.getJSONObject(count);
                                //District a = new District(obj.getString("region_name"));

                                districts.add(obj.getString("region_name"));

                            }

                            catch (JSONException e) {

                                e.printStackTrace();
                            }
                            count++;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item, districts);

                        spinner1.setAdapter(adapter);


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        queue.add(arrayreq);


    }
}