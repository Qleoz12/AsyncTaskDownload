package bammellab.asynctaskdownload;


import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyTask extends AsyncTask<String, Integer, Boolean> {
    static private String TAG = "asynctask";
    private int contentLength = -1;
    private int counter = 0;
    private int calculatedProgress;
    private Activity activity;
        /*
         * 1 create the URL object that represents the URL
         * 2 open connection using the URL
         * 3 read data using input stream into a byte arry
         * 4 open a file output stream to save the data on an SD card
         * 5 write data to the fileoutputstream
         * 6 close the connections
         */
    public MyTask( Activity activity) {
        onAttach( activity );
    }

    public void onAttach( Activity activity) {
        this.activity = activity;
    }

    public void onDetach() {
        activity = null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (activity != null) {
            ((MainActivity)activity).showProgressBarBeforeDownloading();
        }
    }


            /*  old DUMB method to lock rotation


            if (MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT )
                MainActivity.this.setRequestedOrientation( Configuration.ORIENTATION_PORTRAIT);
            else
                MainActivity.this.setRequestedOrientation( Configuration.ORIENTATION_LANDSCAPE);

                 */



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
                publishProgress( counter );  // calls onProgressUpdate
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

        if (activity == null) {
            L.m( "Skipping progress update since activity is NULL");
        } else {
            calculatedProgress = (int) (((double) values[0] / contentLength) * 100.0);
            ((MainActivity)activity).updateProgress( calculatedProgress );
        }

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (activity != null) {
            ((MainActivity)activity).hideProgressBarAfterDownloading();
        }



    }
}

