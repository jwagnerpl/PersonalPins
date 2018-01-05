package teamtreehouse.com.youtube_learning_buddy.UI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import teamtreehouse.com.youtube_learning_buddy.Adapter.CategoryPhotoAdapter;
import teamtreehouse.com.youtube_learning_buddy.Database.AppDatabase;
import teamtreehouse.com.youtube_learning_buddy.Model.Category;
import teamtreehouse.com.youtube_learning_buddy.Model.Photo;
import teamtreehouse.com.youtube_learning_buddy.R;
import teamtreehouse.com.youtube_learning_buddy.Utilities.FileHelper;
import teamtreehouse.com.youtube_learning_buddy.Utilities.Utils;
import teamtreehouse.com.youtube_learning_buddy.API.YoutubeApiCall;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class CategoryActivity extends AppCompatActivity implements CategoryPhotoAdapter.ItemClickListener {

    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_TAKE_VIDEO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_PICK_VIDEO = 3;
    public static Context context;
    RecyclerView recyclerView;
    private static final String TAG = "CategoryActivity";

    FloatingActionButton addPinFab;
    FloatingActionButton addVideoFab;
    FloatingActionButton addPhotoFab;
    FloatingActionButton choosePhotoFab;
    FloatingActionButton chooseVideoFab;

    String categoryName;
    public static Uri mediaUri;
    public static int fk;
    CategoryPhotoAdapter mAdapter;
    private static boolean selectingCoverImage = false;
    AppDatabase db = new Utils().createDatabase(CategoryActivity.this);
    private Menu appMenu;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.setCoverImage);
        menuItem.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        appMenu = menu;
        return true;
    }

        private Menu getMenu(){
            return appMenu;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId() == R.id.setCoverImage ) {
                Toast.makeText(CategoryActivity.this, "Please select an image", Toast.LENGTH_LONG).show();
                if (!selectingCoverImage) {
                    selectingCoverImage = true;
                    item.getIcon().setColorFilter(getResources().getColor(R.color.selected), PorterDuff.Mode.SRC_ATOP);
                } else {
                    selectingCoverImage = false;
                    item.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
            }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        fk = db.categoryDao().getCategoryId(categoryName);
        Log.d(TAG, "FK is: " + fk);
        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = CategoryActivity.this;
        recyclerView = findViewById(R.id.categoryRecycler);

        List<Photo> photos = db.photoDao().getAllPhotosFromBoard(fk + "");
        setDefaultBoardImage(db, photos);

        int columns = Utils.calculateNoOfColumns(CategoryActivity.this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        mAdapter = new CategoryPhotoAdapter(photos, CategoryActivity.this);
        mAdapter.setClickListener(this);
        mAdapter.setPhotos(photos);

        recyclerView.setAdapter(mAdapter);

        addPinFab = findViewById(R.id.categoryFab);
        addVideoFab = findViewById(R.id.videoFab);
        addPhotoFab = findViewById(R.id.photoFab);
        choosePhotoFab = findViewById(R.id.choosePhotoFab);
        chooseVideoFab = findViewById(R.id.chooseVideoFab);

        addPinFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(CategoryActivity.this, "Add a pin", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        addPinFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabVisibility();
            }
        });

        choosePhotoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhotoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                pickPhotoIntent.setType("image/*");
                pickPhotoIntent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivityForResult(pickPhotoIntent, REQUEST_PICK_PHOTO);
            }
        });

        chooseVideoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickVideoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                pickVideoIntent.setType("video/*");
                startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
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

        addVideoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                if (mediaUri == null) {
                    Toast.makeText(CategoryActivity.this,
                            "There was a problem accessing the external storage.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mediaUri);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                    startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);
                }
            }
        });

    }

    private void setDefaultBoardImage(AppDatabase db, List<Photo> photos) {
        if (photos.size() != 0 && db.categoryDao().getCategory(fk).getCoverPhoto() == null) {
            String uri = photos.get(0).getPhotoUri();
            Category category = db.categoryDao().getCategory(fk);
            category.setCoverPhoto(uri);
            db.categoryDao().updateAlbumCover(category);
        }
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
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {

                if (requestCode == REQUEST_PICK_PHOTO) {
                    mediaUri = data.getData();
                    Log.d(TAG,mediaUri.toString());
                    Uri otherMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    byte[] file = FileHelper.getByteArrayFromFile(this, mediaUri);
                    FileHelper.getFileName(this, otherMediaUri, "image");

                    if(mediaUri.toString().contains("content")){mediaUri = Uri.parse(mediaUri.toString());}
                }

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mediaUri);
                sendBroadcast(mediaScanIntent);

                Intent intent = new Intent(this, AddPhotoPinActivity.class);
                intent.putExtra("FK", fk);
                intent.putExtra("type", "photo");
                intent.setData(mediaUri);
                startActivity(intent);
            } else if (requestCode == REQUEST_TAKE_VIDEO || requestCode == REQUEST_PICK_VIDEO) {

                if (requestCode == REQUEST_PICK_VIDEO) {
                    if (data != null) {
                        mediaUri = data.getData();
                    }
                }

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mediaUri);
                sendBroadcast(mediaScanIntent);

                Intent intent = new Intent(this, AddPhotoPinActivity.class);
                Log.d(TAG, fk + " fk is here in activityresult");
                intent.putExtra("FK", fk);
                intent.putExtra("type", "video");
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
            chooseVideoFab.setVisibility(View.INVISIBLE);
            choosePhotoFab.setVisibility(View.INVISIBLE);
        } else {
            addVideoFab.setVisibility(View.VISIBLE);
            addPhotoFab.setVisibility(View.VISIBLE);
            chooseVideoFab.setVisibility(View.VISIBLE);
            choosePhotoFab.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, view.toString() + "here is the view");
        Photo photo = mAdapter.getItem(position);
        String path = photo.getPhotoUri();
        Uri uri = Uri.parse(path);

        if (!selectingCoverImage) {
            Log.d(TAG, uri.toString());
            if (path.contains(".jpg") || path.contains("image")) {

                Intent intent = new Intent(CategoryActivity.this, DisplayImageActivity.class);
                intent.setData(uri);
                intent.putExtra("URI", photo.getPhotoUri());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            } else if(path.contains(".mp4") || path.contains("video")) {
                VideoView vv = view.findViewById(R.id.videoView);
                if (vv.isPlaying() == false) {
                    vv.start();
                } else {
                    vv.stopPlayback();
                }
            }

        } else {
            MenuItem menuItem  = getMenu().findItem(R.id.setCoverImage);
            menuItem.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            selectingCoverImage = false;

            if (uri.toString().contains("jpg")) {
                Category category = db.categoryDao().getCategory(photo.getFkBoardId());
                category.setCoverPhoto(uri.toString());
                db.categoryDao().updateAlbumCover(category);
                Toast.makeText(CategoryActivity.this, "Your cover photo has been updated", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(CategoryActivity.this, "Sorry bro, looks like that's not an image. Pleez try again.", Toast.LENGTH_LONG).show();
            }
        }

    }
    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    }


