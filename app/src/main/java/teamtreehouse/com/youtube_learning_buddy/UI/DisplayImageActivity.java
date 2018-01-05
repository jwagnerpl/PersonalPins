package teamtreehouse.com.youtube_learning_buddy.UI;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import teamtreehouse.com.youtube_learning_buddy.R;

public class DisplayImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_display_image);
        Uri uri = getIntent().getData();
        ImageView imageView = findViewById(R.id.fullSizeImageView);

        Picasso.with(this).load(uri).fit().into(imageView);
    }
}
