package teamtreehouse.com.youtube_learning_buddy.UI;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import teamtreehouse.com.youtube_learning_buddy.Model.Photo;
import teamtreehouse.com.youtube_learning_buddy.R;
import teamtreehouse.com.youtube_learning_buddy.Utilities.FileHelper;
import teamtreehouse.com.youtube_learning_buddy.Utilities.Utils;

public class AddPhotoPinActivity extends AppCompatActivity {
    Button submitButton;
    private String tags;
    private String comments;
    ImageView imageView;
    private static final String TAG = "AddPhotoPinActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_pin);

        imageView = findViewById(R.id.imageView);
        final Uri mediaUri = getIntent().getData();
        final int fk = getIntent().getIntExtra("FK",0);
        Picasso.with(this).load(mediaUri).into(imageView);
        submitButton = findViewById(R.id.submitPhotoButton);
        final EditText tagsEditText = findViewById(R.id.tagEditText);
        final EditText commentsEditText = findViewById(R.id.commentEditText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comments = commentsEditText.getText().toString();
                tags = tagsEditText.getText().toString();
                new Utils().createDatabase(AddPhotoPinActivity.this).photoDao().insertAll(new Photo(mediaUri.toString(),Integer.valueOf(fk),tags,comments));
            }
        });
    }
}
