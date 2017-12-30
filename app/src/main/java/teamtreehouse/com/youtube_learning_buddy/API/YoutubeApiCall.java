package teamtreehouse.com.youtube_learning_buddy.API;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import teamtreehouse.com.youtube_learning_buddy.Adapter.YoutubeVideoAdapter;
import teamtreehouse.com.youtube_learning_buddy.Model.CommentItems;
import teamtreehouse.com.youtube_learning_buddy.Model.Item;
import teamtreehouse.com.youtube_learning_buddy.Model.TopLevelCommentSnippet;
import teamtreehouse.com.youtube_learning_buddy.Model.YoutubeCommentData;
import teamtreehouse.com.youtube_learning_buddy.Model.YoutubeVideo;
import teamtreehouse.com.youtube_learning_buddy.Model.YoutubeVideoData;

public class YoutubeApiCall extends Fragment {
    private static final String TAG = "YoutubeApiCall";

    public ArrayList<YoutubeVideo> youtubeSearch(final Context context, String query, final RecyclerView recyclerView, final ProgressBar pb) {

        pb.setVisibility(View.VISIBLE);

        final ArrayList<YoutubeVideo> videoList = new ArrayList<>();

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribed");
            }

            @Override
            public void onNext(Object value) {
                YoutubeVideoData ytv = (YoutubeVideoData) value;

                ArrayList<Item> items = ytv.getItems();
                for (Item item : items) {
                    String description = item.getSnippet().getDescription();
                    String title = item.getSnippet().getTitle();
                    String id = item.getId().getVideoId() + "";
                    String url = "https://youtube.com/watch?v=" + item.getId().getVideoId();
                    Log.d(TAG, description + title + url);
                    videoList.add(new YoutubeVideo(url, title, description, id));
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                YoutubeVideoAdapter mAdapter = new YoutubeVideoAdapter(videoList, context);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
            }
        };

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(YoutubeClient.YOUTUBE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();

        YoutubeClient ytc = retrofit.create(YoutubeClient.class);
        Observable<YoutubeVideoData> call = ytc.searchedVideos(query);

        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);


        return videoList;
    }

    public ArrayList<TopLevelCommentSnippet> getComments(final Context context, String videoId, final ProgressBar pb, final FragmentManager fragmentManager) {

        pb.setVisibility(View.VISIBLE);

        final ArrayList<TopLevelCommentSnippet> commentList = new ArrayList<>();

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribed");
            }

            @Override
            public void onNext(Object value) {
                Log.d(TAG, "Iterating");
                YoutubeCommentData ytc = (YoutubeCommentData) value;
                Log.d(TAG, value.toString());
                Log.d(TAG, ytc.getKind());
                ArrayList<CommentItems> items = ytc.getCommentItems();
                for (CommentItems item : items) {
                    String author = item.getCommentSnippet().getTopLevelComment().getTopLevelCommentSnippet().getAuthorDisplayName();
                    String commentText = item.getCommentSnippet().getTopLevelComment().getTopLevelCommentSnippet().getTextOriginal();
                    Log.d(TAG, author + commentText);
                    commentList.add(new TopLevelCommentSnippet(author, commentText));
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "there was an error " + e);
            }

            @Override
            public void onComplete() {
                String commentString = "";
                for(TopLevelCommentSnippet comment: commentList){
                    commentString = commentString + comment.authorDisplayName + " - " + comment.textOriginal + "\n\n";
                }
                //commentFragment
//                Dialog dialog = new Dialog(context);
//                dialog.setTitle("Comments");
//                dialog.setContentView(R.layout.dialoglayout);
//                TextView text = dialog.findViewById(R.id.dialogText);
//                text.setText("dialog here");
//                dialog.setPositiveButton
//                dialog.show();
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setTitle("Comments");
                ad.setMessage(commentString);
                ad.setPositiveButton("OK", null);
                ad.show();
                        //show(fragmentManager, "Hello");
                pb.setVisibility(View.GONE);
                Log.d(TAG, "completed");
            }
        };

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(YoutubeClient.YOUTUBE_COMMENT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();

        YoutubeClient ytc = retrofit.create(YoutubeClient.class);
        Observable<YoutubeCommentData> call = ytc.returnComments(videoId);

        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);


        return commentList;
    }

}


