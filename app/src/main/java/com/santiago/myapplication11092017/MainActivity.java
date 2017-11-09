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
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText etData, etFilename;
    Button btnInternalStorage, btnSharedPref;
    SharedPreferences preferences;
    FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etData = (EditText) findViewById(R.id.et_Data);
        etFilename = (EditText) findViewById(R.id.et_Filename);
        btnSharedPref = (Button) findViewById(R.id.btn_SharedPref);
        btnInternalStorage = (Button) findViewById(R.id.btn_InternalStorage);
        preferences = getPreferences(Context.MODE_PRIVATE);

    }
    public void sharedPref(View view){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data",etData.getText().toString());
        editor.putString("filename",etFilename.getText().toString());
        editor.apply();
        Toast.makeText(this, "Data and Filename Saved!", Toast.LENGTH_SHORT).show();

    }
    public void intStorage(View view){
        String message = " " + etData.getText().toString() + " and";
        String message2 = " Filename is " + etFilename.getText().toString() + " ";

        try{
            fos = openFileOutput("MyFile1.txt", Context.MODE_PRIVATE);
            fos.write(message.getBytes());
            fos.write(message2.getBytes());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e ){
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Data and Filename Saved!", Toast.LENGTH_SHORT).show();
    }

    public void SaveInInternalCacheStorage(View view) {
        String text = etData.getText().toString();
        File dir = getCacheDir();
        File file = new File(dir,"MyFile2.txt");
        writeData(file, text);
        Toast.makeText(this, "Successfully written to internal cache", Toast.LENGTH_LONG).show();

    }

    public void SaveInExternalCacheStorage(View view) {
        String text=etData.getText().toString();
        File dir = getExternalCacheDir();
        File file = new File(dir,"MyFile3.txt");
        writeData(file, text);
        Toast.makeText(this, "Successfully written to external cache", Toast.LENGTH_LONG).show();
    }

    public void SaveInExternalPrivateStorage(View view) {
        String text = etData.getText().toString();
        File dir = getExternalFilesDir("MyDir");
        File file = new File(dir,"MyFile4.txt");
        writeData(file, text);
        Toast.makeText(this, "Successfully written to external storage", Toast.LENGTH_LONG).show();

    }

    public void SaveInExternalPublicStorage(View view) {
        String text=etData.getText().toString();
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir,"MyFile5.txt");
        writeData(file, text);
        Toast.makeText(this, "Successfully written to external public storage", Toast.LENGTH_LONG).show();

    }

    public void writeData(File file,String text)
    {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fileOutputStream!=null)
            {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Toast.makeText(this,"Data saved to " + file.getAbsolutePath(),Toast.LENGTH_LONG).show();

    }

    public void next(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}
