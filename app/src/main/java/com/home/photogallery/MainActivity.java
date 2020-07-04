package com.home.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

class   MainActivity extends AppCompatActivity
{
    private ListView l1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
           .cacheInMemory(true)
                .cacheOnDisk(true)
           .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
           .defaultDisplayImageOptions(defaultOptions)
           .build();
        ImageLoader.getInstance().init(config); // Do it on Application start
        l1 = findViewById(R.id.l1);
        JsonWork jsonWork = new JsonWork();
        jsonWork.execute();
    }

    private void defaultDisplayImageOptions(DisplayImageOptions defaultOptions) {
    }


    public class JsonWork extends AsyncTask<String, String, List<PhotoModel>>{


      HttpURLConnection httpURLConnection = null;
      BufferedReader bufferedReader = null;

    @Override
    protected List<PhotoModel> doInBackground(String... strings) {

        try {
            URL url = new URL("https://picsum.photos/list");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            String myFile;
            int i;

            while((line = bufferedReader.readLine()) != null )
            {
                stringBuffer.append(line);
            }

              myFile = stringBuffer.toString();

            List<PhotoModel> photoModelList = new ArrayList<>();

            JSONObject FileObject = new JSONObject(myFile);
            JSONArray photos = FileObject.getJSONArray("photos");

            for(i=0; i<photos.length(); i++)
            {
                JSONObject innerObject = photos.getJSONObject(i);

                PhotoModel photoModel = new PhotoModel();

                photoModel.setFilename(innerObject.getString("filename"));
                photoModel.setAuthor(innerObject.getString("author"));

                photoModelList.add(photoModel);

            }

                return photoModelList;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

        } catch (JSONException e) {

        } finally {

            httpURLConnection.disconnect();
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(List<PhotoModel> s) {
        super.onPostExecute(s);

        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), R.layout.sample,s);

        l1.setAdapter(imageAdapter);


    }
}




}

