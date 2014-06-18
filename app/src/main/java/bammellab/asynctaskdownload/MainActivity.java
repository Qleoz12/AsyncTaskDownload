package bammellab.asynctaskdownload;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;



// #17 AsyncTask Example: Download Images App
// now
// #19 AsyncTask Rotation Example with Fragments Part 1  handle rotation (started)
// #20 included
// #21 included - successful - handles rotation while updating progress bar
//      Async Task is in its own class now, and a helper fragment tracks UI changes.

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    static private String TAG = "asynctask";

    ListView chooseImagesList;
    EditText selectionText;
    String[] listOfImages;
    ProgressBar downloadImagesProgress;
    NonUITaskFragment fragment;

    private String[] texts = {
            "German", "has", "a", "grammatical", "distinction", "between", "preterite" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        selectionText = (EditText) findViewById(R.id.urlSelectionText);
        listOfImages = getResources().getStringArray(R.array.imageUrls);
        downloadImagesProgress = (ProgressBar) findViewById(R.id.downloadProgress);
        chooseImagesList = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.urls,
                android.R.layout.simple_list_item_1);
        chooseImagesList.setAdapter(adapter);

        if (savedInstanceState == null ) {
            fragment = new NonUITaskFragment();
            getFragmentManager().beginTransaction()
                    .add(fragment, "TaskFragment").commit();
        } else {
            fragment = (NonUITaskFragment) getFragmentManager()
                    .findFragmentByTag("TaskFragment");
        }
        if (fragment != null ) {
            if (fragment.myTask !=null && fragment.myTask.getStatus() == AsyncTask.Status.RUNNING ) {
                downloadImagesProgress.setVisibility( View.VISIBLE );
            }
        }

        /*
        chooseImagesList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));

                */

        chooseImagesList.setOnItemClickListener(this);
        // new MyTask().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        selectionText.setText(listOfImages[position]);
        Log.i(TAG, "click on " + position);
    }

    public void downloadImage(View view) {

        if (selectionText.getText() != null
            && selectionText.getText().toString().length() > 0 ) {
            Log.i(TAG, "download image");
            // MyTask myTask = new MyTask();
            // myTask.execute(selectionText.getText().toString());
            fragment.beginTask(selectionText.getText().toString());

        }
    }

    public void showProgressBarBeforeDownloading() {
        if ( fragment.myTask != null ) {
            if ( downloadImagesProgress.getVisibility() != View.VISIBLE
                    && downloadImagesProgress.getProgress() != downloadImagesProgress.getMax()) {
                downloadImagesProgress.setVisibility( View.VISIBLE );
            }
        }
    }
    public void hideProgressBarAfterDownloading() {
        if ( fragment.myTask != null ) {
            if ( downloadImagesProgress.getVisibility() == View.VISIBLE ) {
                downloadImagesProgress.setVisibility( View.GONE );
            }
        }
    }

    public void updateProgress( int progress ) {
        downloadImagesProgress.setProgress( progress );
    }

    /*
    class MyTask extends AsyncTask<String, Integer, Boolean> {
        private int contentLength = -1;
        private int counter = 0;
        private int calculatedProgress;
        /*
         * 1 create the URL object that represents the URL
         * 2 open connection using the URL
         * 3 read data using input stream into a byte arry
         * 4 open a file output stream to save the data on an SD card
         * 5 write data to the fileoutputstream
         * 6 close the connections


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            downloadImagesProgress.setVisibility( View.VISIBLE );

            /*  old DUMB method to lock rotation


            if (MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT )
                MainActivity.this.setRequestedOrientation( Configuration.ORIENTATION_PORTRAIT);
            else
                MainActivity.this.setRequestedOrientation( Configuration.ORIENTATION_LANDSCAPE);



        }

        @Override
        protected Boolean doInBackground(String... params) {


            boolean successful = false;
            URL downloadURL = null;
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            File file = null;
            try {

                downloadURL = new URL( params[0] );
                connection = (HttpURLConnection) downloadURL.openConnection();
                contentLength = connection.getContentLength();
                inputStream = connection.getInputStream();

                file = new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + Uri.parse(params[0]).getLastPathSegment());
                fileOutputStream = new FileOutputStream(file);
                Log.i(TAG, "writing to here: " + file.getAbsolutePath());
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, read);
                    counter += read;
                    publishProgress( counter );
                }
                successful = true;

            } catch (MalformedURLException e) {
                Log.e(TAG, "malformed");
            } catch (IOException e) {
                Log.e(TAG, "IOexception");
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();

                    } catch (IOException e) {
                        Log.e(TAG, "IOexception");
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();

                    } catch (IOException e) {
                        Log.e(TAG, "IOexception");
                    }
                }
            }
            return successful;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            calculatedProgress = (int) (((double) values[0] / contentLength) * 100.0);
            downloadImagesProgress.setProgress( calculatedProgress );

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            downloadImagesProgress.setVisibility( View.GONE );
            // MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    */

}




/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

*/

