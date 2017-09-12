package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();

        setTitle(intent.getStringExtra("username")+ "\'s" + " feed");

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        query.whereEqualTo("username", intent.getStringExtra("username"));
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null && objects.size()>0){

                    for (ParseObject object : objects){


                        ParseFile parseFile = object.getParseFile("image");
                        parseFile.getDataInBackground(new GetDataCallback() {

                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (e == null && data != null){

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    layoutParams.gravity = Gravity.CENTER;
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    imageView.setPadding(0,5,0,5);
                                    imageView.setLayoutParams(layoutParams);
                                    linearLayout.addView(imageView);
                                    imageView.setImageBitmap(bitmap);


                                } else {

                                    Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }

                } else {

                    Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

                    });



    }
}
