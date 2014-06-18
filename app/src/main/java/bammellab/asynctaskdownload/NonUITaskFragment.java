package bammellab.asynctaskdownload;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Jim on 6/17/2014.
 */
public class NonUITaskFragment extends Fragment {
    MyTask myTask;
    private Activity activity;

    public NonUITaskFragment() {
        super();
    }

    public void beginTask( String... arguments ) {
        myTask = new MyTask( activity );
        myTask.execute( arguments );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        L.m("frag onAttach !!!!");
        this.activity = activity;
        if (myTask != null) {
            myTask.onAttach( activity );
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        L.m("frag onStart");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.m("frag onStart");
        setRetainInstance( true );   // suppress onDestroy - so the Frag instance persists
    }

    @Override
    public void onResume() {
        super.onResume();
        L.m("frag onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.m("frag onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.m("frag onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.m("frag onDestroy *****");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.m("frag onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.m("frag onDetach");
        if (myTask != null ) {
            myTask.onDetach();
        }

    }
}
