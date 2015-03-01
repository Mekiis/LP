package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.model.data_sets.IASet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultiChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MultiChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiChooserFragment extends ChooserFragment {


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MultiChooserFragment.
     */
    public static MultiChooserFragment newInstance() {
        MultiChooserFragment fragment = new MultiChooserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void createFragment() {

    }

    @Override
    protected View createViewFragment(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_chooser, container, false);

        (view.findViewById(R.id.mode_chooser_spinner_ia)).setVisibility(View.GONE);
        (view.findViewById(R.id.mode_chooser_label_ia)).setVisibility(View.GONE);

        return view;
    }

    @Override
    protected void onValidate(EGameMode gameMode, int column, int row, boolean needChosenBorderTile, boolean needChosenTile) {
        saveData();
        mListener.onPlayersReady(gameMode,
                column,
                row,
                needChosenBorderTile,
                needChosenTile);
    }

    private void saveData() {
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_GAME_MODE_KEY + getMode().toString(), mGameMode.toString());
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_NEED_CHOSEN_BORDER_TILE_KEY + getMode().toString(), (mSwitchChosenBorderTile.isChecked() == true ? "1" : "0"));
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_NEED_CHOSEN_TILE_KEY + getMode().toString(), (mSwitchChosenTile.isChecked() == true ? "1" : "0"));
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_COLUMN_KEY + getMode().toString(), Integer.toString(mColumnPicker.getValue()));
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_ROW_KEY + getMode().toString(), Integer.toString(mRowPicker.getValue()));
    }

    public MultiChooserFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initializeElements() {
        EGameMode dGameMode = GameModeSet.searchGameMode(getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_GAME_MODE_KEY + getMode().toString(), ""));
        boolean dNeedChosenBorderTile = (getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_NEED_CHOSEN_BORDER_TILE_KEY + getMode().toString(), "1").compareTo("1") == 0 ? true : false);
        boolean dNeedChosenTile = (getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_NEED_CHOSEN_TILE_KEY + getMode().toString(), "1").compareTo("1") == 0 ? true : false);
        int dColumn = Integer.parseInt(getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_COLUMN_KEY + getMode().toString(), "3"));
        int dRow = Integer.parseInt(getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_ROW_KEY + getMode().toString(), "3"));


        for(int i = 0; i < mGameModes.size(); i++){
            if(mGameModes.get(i) == dGameMode)
                mSpinnerGameMode.setSelection(i);
        }
        mSwitchChosenBorderTile.setChecked(dNeedChosenBorderTile);
        mSwitchChosenTile.setChecked(dNeedChosenTile);
        mColumnPicker.setValue(dColumn);
        mRowPicker.setValue(dRow);
    }

    @Override
    public void attachFragment(Activity activity) {
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void detachFragment() {
        saveData();
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

        public void onPlayersReady(EGameMode gameMode, int column, int row, boolean needChosenBorderTile, boolean needChosenTile);
    }

    @Override
    protected EMode getMode() {
        return EMode.MULTI;
    }

}
