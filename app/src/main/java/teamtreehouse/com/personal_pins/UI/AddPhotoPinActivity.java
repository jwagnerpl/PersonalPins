package teamtreehouse.com.personal_pins.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import teamtreehouse.com.personal_pins.Database.AppDatabase;
import teamtreehouse.com.personal_pins.Model.Photo;
import teamtreehouse.com.personal_pins.R;
import teamtreehouse.com.personal_pins.Utilities.Utils;


public class AddPhotoPinActivity extends AppCompatActivity {

    Button submitButton;
    private String tags;
    private String comments;
    ImageView imageView;
    private static final String TAG = "AddPhotoPinActivity";
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_pin);

        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        final Uri mediaUri = intent.getData();

        if (!mediaUri.toString().contains("jpg")) {
            findViewById(R.id.imageView).setVisibility(View.GONE);
        }

        final int fk = getIntent().getIntExtra("FK", 0);
        final String type = intent.getStringExtra("type");
        if (type.equals("photo")) {
            Picasso.with(this).load(mediaUri).into(imageView);
        } else {
            Log.d(TAG, mediaUri.toString());
        }
        submitButton = findViewById(R.id.submitPhotoButton);
        final EditText tagsEditText = findViewById(R.id.tagEditText);
        final EditText commentsEditText = findViewById(R.id.commentEditText);
        final EditText titleEditText = findViewById(R.id.titleEditText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!titleEditText.getText().toString().matches("")) {
                    comments = commentsEditText.getText().toString();
                    tags = tagsEditText.getText().toString();
                    title = titleEditText.getText().toString();
                    AppDatabase db = new Utils().createDatabase(AddPhotoPinActivity.this);
                    db.photoDao().insertAll(new Photo(mediaUri.toString(), Integer.valueOf(fk), tags, comments, title));
                    String coverPhotoUri = db.categoryDao().getCategory(fk).getCoverPhoto();
                    if (coverPhotoUri == null && type.equals("photo")) {
                        db.categoryDao().getCategory(fk).setCoverPhoto(mediaUri.toString());
                    }
                    startActivity(new Intent(AddPhotoPinActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(AddPhotoPinActivity.this, "Sorry, please enter a title first.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
