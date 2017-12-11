package com.example.chaipichit_jek.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView ivTest;
    private Button bt,bt2;
    private AsyncTask a;
    TextView tv;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            initInstance();

    }

    private void initInstance(){
        ivTest = (ImageView) findViewById(R.id.iv_test);
        bt=(Button) findViewById(R.id.bt);
        bt2=(Button)findViewById(R.id.bt2);
        tv=(TextView) findViewById(R.id.tv);
        bt.setOnClickListener(this);
        bt2.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        if (v.getId()==bt.getId()){
            setfirstImage();
        }
        else if (v.getId()==bt2.getId()){
            new LoadImageTask().execute("https://f.ptcdn.info/555/047/000/ohdtzwez9BJov391zQV-o.jpg");
        }
    }

    private void setfirstImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url= null;
                try {
                    url = new URL("https://picpost.postjung.com/data/184/184340-1-2995.jpg");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap= null;
                try {
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                final Bitmap finalBitmap = bitmap;
                ivTest.post(new Runnable() {
                    @Override
                    public void run() {
                        ivTest.setImageBitmap(finalBitmap);
                    }
                });
            }
        }).start();
    }

    private class LoadImageTask extends AsyncTask<String ,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {

            URL url= null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bitmap= null;
            try {
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }


            final Bitmap finalBitmap = bitmap;
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivTest.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.d("WalksMan",""+values[0]+" "+values.length);
            tv.setText(values[0].toString());
        }
    }
}
