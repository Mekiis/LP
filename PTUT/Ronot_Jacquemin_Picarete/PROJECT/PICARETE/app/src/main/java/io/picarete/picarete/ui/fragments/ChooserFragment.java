package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.NoDuplicatesList;
import io.picarete.picarete.model.container.userdata.Config;
import io.picarete.picarete.model.container.userdata.UserAccessor;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.ui.adapters.SpinnerModeAdapter;
import io.picarete.picarete.ui.custom.CustomFontSwitch;

public abstract class ChooserFragment extends Fragment {


    protected EGameMode mGameMode;
    protected NumberPicker mColumnPicker;
    protected NumberPicker mRowPicker;
    protected CustomFontSwitch mSwitchChosenBorderTile;
    protected CustomFontSwitch mSwitchChosenTile;
    protected Spinner mSpinnerGameMode;
    protected List<EGameMode> mGameModes;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    protected abstract void createFragment();
    protected abstract View createViewFragment(LayoutInflater inflater, ViewGroup container);
    protected abstract void onValidate(EGameMode gameMode, int column, int row, boolean needChosenBorderTile, boolean needChosenTile);
    protected abstract void attachFragment(Activity activity);
    protected abstract void detachFragment();

    public ChooserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = createViewFragment(inflater, container);

        mGameModes = getTitlesForLevel(UserAccessor.getUser(getActivity()).level);

        mSpinnerGameMode = (Spinner) view.findViewById(R.id.mode_chooser_spinner_game_mode);
        mSpinnerGameMode.setAdapter(new SpinnerModeAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                GameModeSet.getTitles(getActivity(), mGameModes),
                GameModeSet.getDesc(getActivity(), mGameModes)));
        mSpinnerGameMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGameMode = mGameModes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerGameMode.setSelection(0);

        mColumnPicker = (NumberPicker) view.findViewById(R.id.mode_chooser_picker_column);
        mColumnPicker.setMaxValue(Config.getColumn(UserAccessor.getUser(getActivity()).level));
        mColumnPicker.setMinValue(Constants.COLUMN_ROW_MIN);

        mRowPicker = (NumberPicker) view.findViewById(R.id.mode_chooser_picker_row);
        mRowPicker.setMaxValue(Config.getRow(UserAccessor.getUser(getActivity()).level));
        mRowPicker.setMinValue(Constants.COLUMN_ROW_MIN);

        mSwitchChosenBorderTile = (CustomFontSwitch) view.findViewById(R.id.mode_chooser_switch_chosen_border_tile);
        mSwitchChosenTile = (CustomFontSwitch) view.findViewById(R.id.mode_chooser_switch_chosen_tile);

        (view.findViewById(R.id.mode_chooser_validate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onValidate(mGameMode, mColumnPicker.getValue(), mRowPicker.getValue(), mSwitchChosenBorderTile.isChecked(), mSwitchChosenTile.isChecked());
            }
        });

        initializeElements();

        return view;
    }

    private List<EGameMode> getTitlesForLevel(int level) {
        List<EGameMode> availableModes = new NoDuplicatesList<>();

        for(int i = 0 ; i <= level ; i++){
            availableModes.addAll(Config.getGameModes(i));
        }

        return availableModes;
    }

    protected abstract void initializeElements();

    protected abstract EMode getMode();

    public String getPref(String file, String key, String defaultValue) {
        String s = key;
        SharedPreferences preferences = getActivity().getSharedPreferences(file, 0);
        return preferences.getString(s, defaultValue);
    }
    public void setPref(String file, String key, String value) {
        SharedPreferences preferences = getActivity().getSharedPreferences(file, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        attachFragment(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        detachFragment();
    }


}
