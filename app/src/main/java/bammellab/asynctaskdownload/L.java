package bammellab.asynctaskdownload;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class L {
    static private String TAG = "asynctask";

    public static void m( String string ) {
        Log.i(TAG, string );
    }

    public static void s( Context context, String message ) {
        Toast.makeText( context, message, Toast.LENGTH_SHORT ).show();
    }
}
