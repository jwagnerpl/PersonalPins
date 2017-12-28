package teamtreehouse.com.youtube_learning_buddy;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeApiCall {
    private static final String TAG = "YoutubeApiCall";

    public ArrayList<YoutubeVideo> youtubeSearch(String query) {

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
                    String url = "https://youtube.com/watch?v=" + item.getId().getVideoId();
                    videoList.add(new YoutubeVideo(url, title, description));
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(YoutubeClient.YOUTUBE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(YoutubeClient.YOUTUBE_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        YoutubeClient ytc = retrofit.create(YoutubeClient.class);
        Observable<YoutubeVideoData> call = ytc.searchedVideos(query);

        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);


//        call.enqueue(new Callback<YoutubeVideoData>() {
//            @Override
//            public void onResponse(Call<YoutubeVideoData> call, Response<YoutubeVideoData> response) {
//
//                YoutubeVideoData ytv = response.body();
//
//                ArrayList<Item> items = ytv.getItems();
//                for (Item item : items) {
//                    String description = item.getSnippet().getDescription();
//                    String title = item.getSnippet().getTitle();
//                    String url = "https://youtube.com/watch?v=" + item.getId().getVideoId();
//                    videoList.add(new YoutubeVideo(url,title,description));
//                    Log.d(TAG, description + " " + url + " " + title);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<YoutubeVideoData> call, Throwable t) {
//                Log.d(TAG, "something went wrong " + t);
//            }
//        });
        return videoList;
    }
}
