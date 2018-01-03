package teamtreehouse.com.youtube_learning_buddy.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import teamtreehouse.com.youtube_learning_buddy.Adapter.CategoryPhotoAdapter;
import teamtreehouse.com.youtube_learning_buddy.Database.AppDatabase;
import teamtreehouse.com.youtube_learning_buddy.Model.Photo;
import teamtreehouse.com.youtube_learning_buddy.R;
import teamtreehouse.com.youtube_learning_buddy.Utilities.Utils;
import teamtreehouse.com.youtube_learning_buddy.API.YoutubeApiCall;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class CategoryActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 0;
    public static Context context;
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    FloatingActionButton addPinFab;
    FloatingActionButton addVideoFab;
    FloatingActionButton addPhotoFab;
    String categoryName;
    Uri mediaUri;
    public static int fk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        AppDatabase db = new Utils().createDatabase(CategoryActivity.this);
        fk = db.categoryDao().getCategoryId(categoryName);
        Log.d(TAG, "FK is: " +fk);
        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        YoutubeApiCall api = new YoutubeApiCall();

        context = getApplicationContext();
        recyclerView = findViewById(R.id.categoryRecycler);

        List<Photo> photos = db.photoDao().getAllPhotosFromBoard(fk + "");
        Log.d(TAG, photos.toString());
        int columns = Utils.calculateNoOfColumns(CategoryActivity.this);
        float marginSpace = Utils.setGridMargins(CategoryActivity.this, columns);
//        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
//        Log.d(TAG, "margin is " + marginSpace);
//        marginLayoutParams.setMargins((int)marginSpace, 0, (int)marginSpace, 0);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        CategoryPhotoAdapter mAdapter = new CategoryPhotoAdapter(photos, CategoryActivity.this);
        mAdapter.setPhotos(photos);

        recyclerView.setAdapter(mAdapter);

        addPinFab = findViewById(R.id.categoryFab);
        addVideoFab = findViewById(R.id.videoFab);
        addPhotoFab = findViewById(R.id.photoFab);

        addPinFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(CategoryActivity.this, "Add a Youtube video", Toast.LENGTH_LONG).show();
                return true;
            }
        });


        addPinFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabVisibility();
            }
        });

        addPhotoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                if (mediaUri == null) {
                    Toast.makeText(CategoryActivity.this,
                            "There was a problem accessing the external storage.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mediaUri);
                    startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
                }
            }
        });

    }

    private Uri getOutputMediaFileUri(int mediaType) {
        if (isExternalStorageAvailable()) {

            //1. Get external storage directory
            File mediaStorageDir = getExternalFilesDir((Environment.DIRECTORY_PICTURES));
            //2. CREATE unique file name
            String fileName = "";
            String fileType = "";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (mediaType == MEDIA_TYPE_IMAGE) {
                fileName = "IMG_" + timeStamp;
                fileType = ".jpg";
            } else if (mediaType == MEDIA_TYPE_VIDEO) {
                fileName = "VID_" + timeStamp;
                fileType = ".mp4";
            } else {
                return null;
            }

            File mediaFile;
            //3. Create the file
            try {
                mediaFile = File.createTempFile(fileName, fileType, mediaStorageDir);
                //4. Return the file's URI
                return Uri.fromFile(mediaFile);
            } catch (IOException e) {
                Log.e(TAG, "Error creating file: " +
                        mediaStorageDir.getAbsolutePath() + fileName + fileType);
            }

            return null;
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                Log.d(TAG, mediaUri.toString());
                //new Utils().createDatabase(CategoryActivity.this).photoDao().insertAll(new Photo(mediaUri,fk));
                Intent intent = new Intent(this, AddPhotoPinActivity.class);
                Log.d(TAG, fk + " fk is here in activityresult");
                intent.putExtra("FK",fk);
                intent.setData(mediaUri);
                startActivity(intent);
            }

        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(CategoryActivity.this,
                    "Sorry, there was an error.", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    private void toggleFabVisibility() {
        if (addVideoFab.getVisibility() == View.VISIBLE) {
            addVideoFab.setVisibility(View.INVISIBLE);
            addPhotoFab.setVisibility(View.INVISIBLE);
        } else {
            addVideoFab.setVisibility(View.VISIBLE);
            addPhotoFab.setVisibility(View.VISIBLE);
        }
    }
}

