package com.example.arnavgohil.ai;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.io.InputStream;
import java.net.HttpURLConnection;




public class MainActivity extends AppCompatActivity
{



    public static int flag;
    public static String REQUEST_URL;
    public static TextView t1;
    public static TextView t2;
    public static TextView t3;
    public static HttpURLConnection urlConnection = null;
    public static InputStream inputStream = null;
    public static String jsonResponse = null;
    public String para ;
    RadioButton fl ;
    EditText param ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        t1=findViewById(R.id.t1);
        t2= findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        param=findViewById(R.id.para);
        fl = findViewById(R.id.r1);



    }

    public void click(View view) {
        para = param.getText().toString();

        if (fl.isChecked())
        {
            REQUEST_URL="http://aviation-edge.com/v2/public/flights?key=110c4a-fbb2d1&regNum=" + para;
            flag=0;
        }
        else
        {
            REQUEST_URL= "https://aviation-edge.com/v2/public/airplaneDatabase?key=110c4a-fbb2d1&numberRegistration=" + para;
            flag=1;
        }

     AsyncTask<Void, Void, Void> execute = new Utlis().execute();

    }
}

