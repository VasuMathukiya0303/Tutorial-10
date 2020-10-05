package com.example.tutorial10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetail extends AppCompatActivity {
    TextView txtUserData;
    String valUserData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        txtUserData = findViewById(R.id.txtUserData);
        int position = getIntent().getIntExtra("userPosition",0);

        try {
            JSONObject userobj = MyUtil.jsonArray.getJSONObject(position);
            valUserData += "Id : " + userobj.getString("id");
            valUserData += "\nName : " + userobj.getString("name");
            valUserData += "\nUsername : " + userobj.getString("username");
            valUserData += "\nEmail : " + userobj.getString("email");
            JSONObject addressObj = userobj.getJSONObject("address");
            valUserData += "\nAddress : " +
                    addressObj.getString("street") + ", "+
                    addressObj.getString("suite") + ", "+
                    addressObj.getString("city") + ", "+
                    addressObj.getString("zipcode");
            valUserData += "\nPhone : "+userobj.getString("phone");
            valUserData += "\nWebsite : "+userobj.getString("website");
            JSONObject companyObj = userobj.getJSONObject("company");
            valUserData += "\nAddress : " +
                    companyObj.getString("name") + ", "+
                    companyObj.getString("catchPhrase") + ", "+
                    companyObj.getString("bs");

            txtUserData.setText(valUserData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}