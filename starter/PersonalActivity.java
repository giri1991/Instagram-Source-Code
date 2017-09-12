package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        setTitle("Your Feed");
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0 ){

                        for (ParseObject object : objects){



                            ParseFile parseFile = object.getParseFile("image");
                            parseFile.getDataInBackground(new GetDataCallback() {

                                @Override
                                public void done(byte[] data, ParseException e) {

                                      if (e==null){

                                          Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);


                                          ImageView imageView = new ImageView(getApplicationContext());

                                          LinearLayout.LayoutParams layoutParams = new LinearLayout.
                                                  LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                  ViewGroup.LayoutParams.WRAP_CONTENT);

                                          layoutParams.gravity = Gravity.CENTER;
                                          imageView.setLayoutParams(layoutParams);
                                          imageView.setPadding(0,5,0,5);
                                          linearLayout.addView(imageView);
                                          imageView.setImageBitmap(bitmap);


                                      } else {

                                          Log.i("info", e.getMessage());
                                      }


                                }
                            });

                        }

                    }

                } else {

                   Log.i("info",e.getMessage());

                }

            }
        });

    }
}
