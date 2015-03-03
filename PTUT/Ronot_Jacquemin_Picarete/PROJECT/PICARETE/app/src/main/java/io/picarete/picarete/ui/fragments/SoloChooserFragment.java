package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.AIA;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.NoDuplicatesList;
import io.picarete.picarete.model.container.userdata.Config;
import io.picarete.picarete.model.container.userdata.UserAccessor;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.model.data_sets.IASet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoloChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoloChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoloChooserFragment extends ChooserFragment {
    private OnFragmentInteractionListener mListener;

    private EIA mNameIa;
    private Spinner mSpinnerIA;
    private List<EIA> mNameIAs;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment SoloFragment.
     */
    public static SoloChooserFragment newInstance() {
        SoloChooserFragment fragment = new SoloChooserFragment();
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

        mNameIAs = getIAForLevel(UserAccessor.getUser(getActivity()).level);
        mSpinnerIA = (Spinner) view.findViewById(R.id.mode_chooser_spinner_ia);
        mSpinnerIA.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                IASet.getNames(getActivity(), mNameIAs)));
        mSpinnerIA.setSelection(0);
        mSpinnerIA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNameIa = mNameIAs.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private List<EIA> getIAForLevel(int level) {
        List<EIA> availableIA = new NoDuplicatesList<>();

        for(int i = 0 ; i <= level ; i++){
            availableIA.addAll(Config.getIAs(i));
        }

        return availableIA;
    }

    @Override
    protected void onValidate(EGameMode gameMode, int column, int row, boolean needChosenBorderTile, boolean needChosenTile) {
        saveData();
        mListener.onPlayerReady(gameMode,
                column,
                row,
                mNameIa,
                needChosenBorderTile,
                needChosenTile);
    }

    private void saveData() {
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_GAME_MODE_KEY + getMode().toString(), mGameMode.toString());
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_NEED_CHOSEN_BORDER_TILE_KEY + getMode().toString(), (mSwitchChosenBorderTile.isChecked() == true ? "1" : "0"));
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_NEED_CHOSEN_TILE_KEY + getMode().toString(), (mSwitchChosenTile.isChecked() == true ? "1" : "0"));
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_COLUMN_KEY + getMode().toString(), Integer.toString(mColumnPicker.getValue()));
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_ROW_KEY + getMode().toString(), Integer.toString(mRowPicker.getValue()));
        setPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_IA_KEY + getMode().toString(), mNameIa.toString());
    }

    @Override
    protected void attachFragment(Activity activity) {

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    protected void detachFragment() {
        saveData();
        mListener = null;
    }

    public SoloChooserFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initializeElements() {
        EGameMode dGameMode = GameModeSet.searchGameMode(getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_GAME_MODE_KEY + getMode().toString(), ""));
        boolean dNeedChosenBorderTile = (getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_NEED_CHOSEN_BORDER_TILE_KEY + getMode().toString(), "1").compareTo("1") == 0 ? true : false);
        boolean dNeedChosenTile = (getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_NEED_CHOSEN_TILE_KEY + getMode().toString(), "1").compareTo("1") == 0 ? true : false);
        int dColumn = Integer.parseInt(getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_COLUMN_KEY + getMode().toString(), "3"));
        int dRow = Integer.parseInt(getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_ROW_KEY + getMode().toString(), "3"));
        EIA dIA = IASet.searchIA(getPref(Constants.FILE_CHOSER_DATA, Constants.CHOSER_IA_KEY + getMode().toString(), ""));

        for(int i = 0; i < mGameModes.size(); i++){
            if(mGameModes.get(i) == dGameMode)
                mSpinnerGameMode.setSelection(i);
        }
        mSwitchChosenBorderTile.setChecked(dNeedChosenBorderTile);
        mSwitchChosenTile.setChecked(dNeedChosenTile);
        mColumnPicker.setValue(dColumn);
        mRowPicker.setValue(dRow);
        for(int i = 0; i < mNameIAs.size(); i++){
            if(mNameIAs.get(i) == dIA)
                mSpinnerIA.setSelection(i);
        }
    }

    @Override
    protected EMode getMode() {
        return EMode.SOLO;
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

        public void onPlayerReady(EGameMode gameMode, int columnCount, int rowCount, EIA nameIa, boolean needChosenBorderTile, boolean needChoosenTile);
    }

}
