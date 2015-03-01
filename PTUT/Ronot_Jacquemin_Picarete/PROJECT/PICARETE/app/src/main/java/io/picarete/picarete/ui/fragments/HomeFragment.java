package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.picarete.picarete.R;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.container.userdata.UserAccessor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView UIWelcomeMsg = (TextView) view.findViewById(R.id.home_welcome);
        if(UserAccessor.getUser(getActivity()).name.compareToIgnoreCase("") != 0){
            UIWelcomeMsg.setText(getString(R.string.home_welcome) + " " + UserAccessor.getUser(getActivity()).name);
            UIWelcomeMsg.setVisibility(View.VISIBLE);
        }else{
            UIWelcomeMsg.setText("");
            UIWelcomeMsg.setVisibility(View.INVISIBLE);
        }

        (view.findViewById(R.id.b_solo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModeChosen(R.id.b_solo);
            }
        });

        (view.findViewById(R.id.b_multi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModeChosen(R.id.b_multi);
            }
        });

        (view.findViewById(R.id.b_profile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModeChosen(R.id.b_profile);
            }
        });

        return view;
    }

    public void onModeChosen(int id) {
        if (mListener != null) {
            switch(id){
                case R.id.b_solo:
                    mListener.onModeChosen(Constants.SOLO_GAME_CHOOSER);
                    break;
                case R.id.b_multi:
                    mListener.onModeChosen(Constants.MULTI_GAME_CHOOSER);
                    break;
                case R.id.b_profile:
                    mListener.onModeChosen(Constants.PROFILE);
                    break;
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        public void onModeChosen(String mode);
    }

}
