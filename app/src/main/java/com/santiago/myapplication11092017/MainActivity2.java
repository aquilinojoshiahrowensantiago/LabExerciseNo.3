package com.santiago.myapplication11092017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {

    FileInputStream fis;
    Button btnlInternalStorage, btnlSharedPref;
    TextView tvDisplay, tvDisplay2;
    SharedPreferences preferences;
    TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnlInternalStorage = (Button) findViewById(R.id.btn_lInternalStorage);
        btnlSharedPref = (Button) findViewById(R.id.btn_lSharedPref);
        preferences = getPreferences(Context.MODE_PRIVATE);
        tvDisplay = (TextView) findViewById(R.id.tv_display);
        tvDisplay2 = (TextView) findViewById(R.id.tv_display2);
    }
    public void previous (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void dispSharedPrefe(View view){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String data = preferences.getString("data", "");
        String fnm = preferences.getString("filename", "");
        tvDisplay2.setText("The filename of " + data + " is " + fnm);

    }


    public void dispInteStorage(View view){
        StringBuffer buffer = new StringBuffer();
        int read = 0;
        try {
            fis = openFileInput("MyFile1.txt");
            while((read = fis.read()) != -1){
                buffer.append((char)read);
            }
            fis.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        tvDisplay.setText("The Data is" + buffer.toString());
    }
    public void LoadFromInternalCacheStorage(View view) {
        File dir = getCacheDir();
        File file = new File(dir,"MyFile2.txt");
        readData(file);
    }

    public void LoadFromExternalCacheStorage(View view) {
        File dir = getExternalCacheDir();
        File file = new File(dir,"MyFile3.txt");
        readData(file);
    }
    public void LoadFromExternalPrivateStorage(View view) {
        File dir = getExternalFilesDir("MyDir");
        File file = new File(dir,"MyFile4.txt");
        readData(file);
    }


    public void LoadFromExternalPublicStorage(View view) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir,"MyFile5.txt");
        readData(file);
    }
    public void readData(File file) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ((read = fis.read()) != -1){
                buffer.append((char) read);
            }
        tvDisplay.setText(buffer);
    }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void clear(View view){
        tvDisplay.setText("");
        tvDisplay2.setText("");
    }

}
