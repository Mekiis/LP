package lp.kazuya.mediaview.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityTestCase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lp.kazuya.mediaview.R;

/**
 * Created by IEM on 14/11/2014.
 */
public class MediaFragment extends Fragment {

    public enum EType{Image, Video, Text, Audio};

    private static EType type;

    public interface OnElementSelectedListener{
        public abstract void onElementSelected();
    }
    private OnElementSelectedListener listener;

    public MediaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.media_fragment_simple, container, false);

        rootView.findViewById(R.id.content_frame_simple);

        MediaListFragment listFragment = new MediaListFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit();

        // TODO : Get the list of the elements to display
        // TODO : Display the list of elements
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            // TODO : Use 2 fragments side by side
        } else {
            // TODO : Use only 1 fragment
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (OnElementSelectedListener) activity;
        } catch (Exception e){
            Log.d(this.getClass().getName(), "Error - Activity must implement OnElementSelectedListener");
        }
    }

    public void setType(EType type){
        this.type = type;
    }

}