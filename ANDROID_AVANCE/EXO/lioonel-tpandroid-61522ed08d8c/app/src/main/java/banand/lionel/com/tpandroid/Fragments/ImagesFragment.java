package banand.lionel.com.tpandroid.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import banand.lionel.com.tpandroid.R;

/**
 * Created by lionel on 30/09/14.
 */
public class ImagesFragment extends Fragment {
    OnElementSelectedListener mListener;
    // Container Activity must implement this interface
    public interface OnElementSelectedListener {
        public void onElementSelected();
    }
    TextView mTVHelloText;
    ImageView mIVHelloText;

    String mHelloText;
    int mHelloImageResId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.image_fragment, container, false);

        mTVHelloText = (TextView) rootView.findViewById(R.id.tvHelloText);
        mIVHelloText = (ImageView) rootView.findViewById(R.id.ivHelloText);

        if(mHelloText != null) {
            mTVHelloText.setText(mHelloText);
            mIVHelloText.setImageResource(mHelloImageResId);
        }

        mIVHelloText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onElementSelected();
            }
        });

        return rootView;
    }

    public void setHelloParameters(String text, int imageResId) {
        mHelloImageResId = imageResId;
        mHelloText = text;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnElementSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
}
