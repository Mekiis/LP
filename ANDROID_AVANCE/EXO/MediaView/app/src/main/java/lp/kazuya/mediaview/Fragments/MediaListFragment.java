package lp.kazuya.mediaview.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import lp.kazuya.mediaview.R;

/**
 * Created by IEM on 14/11/2014.
 */
public class MediaListFragment extends ListFragment {

    private String[] medias = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.media_listview_item, this.medias);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
    }

    // TODO : Replace with a type "Media"
    public void setValues(String[] medias){
        this.medias = medias;
    }
}
