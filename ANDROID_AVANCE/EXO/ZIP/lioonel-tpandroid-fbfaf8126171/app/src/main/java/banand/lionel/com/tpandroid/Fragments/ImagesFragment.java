package banand.lionel.com.tpandroid.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import banand.lionel.com.tpandroid.Activity.DisplayMediaActivity;
import banand.lionel.com.tpandroid.DAO.Accessor.MediasDataAccess;
import banand.lionel.com.tpandroid.DAO.Objects.MediaObject;
import banand.lionel.com.tpandroid.Internet.TpAndroidInternet;
import banand.lionel.com.tpandroid.Manager.MediasManager;
import banand.lionel.com.tpandroid.R;

/**
 * Created by lionel on 30/09/14.
 */
public class ImagesFragment extends Fragment implements TpAndroidInternet.TpAndroidInternetInterface {
    OnElementSelectedListener mListener;
   ImagesAdapter mAdapter;
    ListView mLVImages;

    @Override
    public void onMediaDownloaded(MediaObject mediaObject) {
        MediasDataAccess mediasDataAccess = new MediasDataAccess(getActivity());
        mediasDataAccess.insertMedia(mediaObject);
        mAdapter.notifyDataSetChanged();
    }

    // Container Activity must implement this interface
    public interface OnElementSelectedListener {
        public void onElementSelected();
        public int getSelectedMenu();
    }
    TextView mTVHelloText;
    ImageView mIVHelloText;

    String mHelloText;
    int mHelloImageResId;

    ArrayList<MediaObject> mMediaObjects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.image_fragment, container, false);

        switch (mListener.getSelectedMenu()) {
            case 0:
                mMediaObjects = MediasManager.getInstance().getImages();
                break;
            case 1:
                mMediaObjects = MediasManager.getInstance().getText();
                break;
            case 2:
                mMediaObjects = MediasManager.getInstance().getVideos();
                break;
            default:
                mMediaObjects = new ArrayList<MediaObject>();
                break;
        }


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

        mAdapter = new ImagesAdapter();
        mLVImages = (ListView)rootView.findViewById(R.id.lvImages);
        mLVImages.setAdapter(mAdapter);

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

    private class ImagesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMediaObjects.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LinearLayout layout;

            if(view == null) {
                layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.images_adapter_row, null);
            } else {
                layout = (LinearLayout)view;
            }

            ImageView iv = (ImageView)layout.findViewById(R.id.ivImage);
            iv.setImageResource(R.drawable.ic_action_picture);

            TextView tvName = (TextView) layout.findViewById(R.id.tvName);
            tvName.setText("Name : " + mMediaObjects.get(i).name);

            TextView tvVersion = (TextView) layout.findViewById(R.id.tvVersion);
            tvVersion.setText("Version : " + String.valueOf(mMediaObjects.get(i).versionCode));

            Button btnDownload = (Button)layout.findViewById(R.id.btnDownloadMedia);
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new TpAndroidInternet().downloadMediaFile(mMediaObjects.get(i), ImagesFragment.this);
                }
            });

            Button btnDisplay = (Button)layout.findViewById(R.id.btnDisplayMedia);
            if(mMediaObjects.get(i).localPath == null) {
                btnDisplay.setEnabled(false);
            } else {
                btnDisplay.setEnabled(true);
                btnDisplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), DisplayMediaActivity.class);
                        intent.putExtra("local_path", mMediaObjects.get(i).localPath);
                        getActivity().startActivity(intent);
                    }
                });
            }
            return layout;
        }
    }
}
