package io.picarete.picarete.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.model.container.ColorCustom;
import io.picarete.picarete.model.container.userdata.Config;
import io.picarete.picarete.model.container.userdata.UserAccessor;
import io.picarete.picarete.model.data_sets.TitleSet;
import io.picarete.picarete.ui.color_picker.ColorPickerDialog;
import io.picarete.picarete.ui.color_picker.ColorPickerSwatch;
import io.picarete.picarete.ui.color_picker.ColorStateDrawable;
import io.picarete.picarete.ui.custom.CustomFontTextView;

public class ProfileFragment extends Fragment {

    int colorPlayer1;
    int colorPlayer2;
    private ImageView UIImageColorPlayer1;
    private ImageView UIImageColorPlayer2;
    private ImageView UIImageEdit;
    private TextView UITextViewPlayerName;
    private EditText UIEditTextPlayerName;
    private TextView UIPlayerTitle;
    private boolean isInEditMode = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        UIImageEdit = (ImageView) view.findViewById(R.id.edit_profile);
        UITextViewPlayerName = (TextView) view.findViewById(R.id.profile_player_name_tv);
        UITextViewPlayerName.setText(UserAccessor.getUser(getActivity()).name.equalsIgnoreCase("") ? getString(R.string.profile) : UserAccessor.getUser(getActivity()).name);
        UIPlayerTitle = (TextView) view.findViewById(R.id.profile_player_title_tv);
        UIPlayerTitle.setText(UserAccessor.getUser(getActivity()).title.equalsIgnoreCase("") ? getString(R.string.noTitle) : UserAccessor.getUser(getActivity()).title);
        UIEditTextPlayerName = (EditText) view.findViewById(R.id.profile_player_name_et);
        UIEditTextPlayerName.setVisibility(View.GONE);

        ProgressBar progressXp = (ProgressBar)view.findViewById(R.id.seekBar_xp);
        progressXp.setProgress((int) (((double) UserAccessor.getUser(getActivity()).actualXp/ (double) UserAccessor.getUser(getActivity()).nextXp)*100.0));
        CustomFontTextView level = (CustomFontTextView) view.findViewById(R.id.lvl);
        level.setText(Integer.toString(UserAccessor.getUser(getActivity()).level));
        CustomFontTextView value_playedGames = (CustomFontTextView) view.findViewById(R.id.value_playedGames);
        value_playedGames.setText(Integer.toString(UserAccessor.getUser(getActivity()).getPlayedGames()));
        CustomFontTextView value_wonGames = (CustomFontTextView) view.findViewById(R.id.value_wonGames);
        value_wonGames.setText(Integer.toString(UserAccessor.getUser(getActivity()).getWonGames()));

        RatingBar ratio = (RatingBar) view.findViewById(R.id.value_ratio);
        ratio.setRating(UserAccessor.getUser(getActivity()).getRatio()*5);
        ImageView edit = (ImageView) view.findViewById(R.id.edit_profile);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditMode();
            }
        });

        UIImageColorPlayer1 = (ImageView) view.findViewById(R.id.value_colorP1);
        initColorImage(UIImageColorPlayer1, 0);
        UIImageColorPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] colors = new int[Config.getColors(UserAccessor.getUser(getActivity()).level).size()];
                int i = 0;
                for (ColorCustom color : Config.getColors(UserAccessor.getUser(getActivity()).level)) {
                    colors[i] = color.getColor();
                    i++;
                }
                showColorPickerDialog(colors, 0);
            }
        });

        UIImageColorPlayer2 = (ImageView) view.findViewById(R.id.value_colorP2);
        initColorImage(UIImageColorPlayer2, 1);
        UIImageColorPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] colors = new int[Config.getColors(UserAccessor.getUser(getActivity()).level).size()];
                int i = 0;

                for (ColorCustom color : Config.getColors(UserAccessor.getUser(getActivity()).level)) {
                    colors[i] = color.getColor();
                    i++;
                }
                showColorPickerDialog(colors, 1);
            }
        });

        //titles.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, TitleSet.getUnlockedTitles(getActivity())));
        return view;
    }

    private void startEditMode() {
        isInEditMode = !isInEditMode;
        if(isInEditMode){
            UIImageEdit.setImageResource(R.drawable.profil_check);
            UITextViewPlayerName.setVisibility(View.GONE);
            UIPlayerTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    titleList();
                }
            });
            UIPlayerTitle.setVisibility(View.VISIBLE);
            UIEditTextPlayerName.setVisibility(View.VISIBLE);
            UIEditTextPlayerName.setHint(getString(R.string.nickname));
            if(UserAccessor.getUser(getActivity()).name.equalsIgnoreCase(""))
                UIEditTextPlayerName.setText("");
            else
                UIEditTextPlayerName.setText(UserAccessor.getUser(getActivity()).name);
            UIEditTextPlayerName.requestFocus();
        }else{
            UIImageEdit.setImageResource(R.drawable.profil_edit);
            UIEditTextPlayerName.setVisibility(View.GONE);
            String name = UIEditTextPlayerName.getText().toString();
            UserAccessor.getUser(getActivity()).name = name;
            if(!name.equalsIgnoreCase("")) {
                UITextViewPlayerName.setText(name);
            }else
                UITextViewPlayerName.setText(getString(R.string.profile));
            UITextViewPlayerName.setVisibility(View.VISIBLE);

            String title = UIPlayerTitle.getText().toString();
            UserAccessor.getUser(getActivity()).title = title;
            UIPlayerTitle.setOnClickListener(null);
            if(!UserAccessor.getUser(getActivity()).title.equalsIgnoreCase(""))
                UIPlayerTitle.setText(UserAccessor.getUser(getActivity()).title);
            else
                UIPlayerTitle.setText(getString(R.string.noTitle));

            UserAccessor.getUser(getActivity()).save(getActivity());
        }

    }

    private void titleList() {
        List<String> titles  = TitleSet.getUnlockedTitles(getActivity());
        if(titles.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.noTitleAvailable)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }else {
            final String arrayTitles[] = new String[titles.size()];
            titles.toArray(arrayTitles);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.pick_title)
                    .setItems(arrayTitles, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            if(!arrayTitles[which].equalsIgnoreCase(""))
                                UIPlayerTitle.setText(arrayTitles[which]);
                            else
                                UIPlayerTitle.setText(getString(R.string.noTitle));
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    }

    private void initColorImage(ImageView colorImage, int player) {
        Drawable[] colorDrawable = new Drawable[] {
                getActivity().getResources().getDrawable(R.drawable.calendar_color_picker_swatch)
        };
        switch (player){
            case 0 :
                colorImage.setImageDrawable(new ColorStateDrawable(colorDrawable, (UserAccessor.getUser(getActivity()).getColorPlayer1().getColor())));
                break;
            case 1:
                colorImage.setImageDrawable(new ColorStateDrawable(colorDrawable, (UserAccessor.getUser(getActivity()).getColorPlayer2().getColor())));
                break;
        }

    }

    private void showColorPickerDialog(int[] colors, final int player){
        ColorPickerDialog colorCalendar = ColorPickerDialog.newInstance(
                R.string.color_picker_default_title,
                colors,
                (player == 0 ? UserAccessor.getUser(getActivity()).getColorPlayer1().getColor() : UserAccessor.getUser(getActivity()).getColorPlayer2().getColor()),
                5,
                ColorPickerDialog.SIZE_SMALL);

        //Implement listener to get selected color value
        colorCalendar.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                changeColorForPlayer(player, color);
            }

        });

        colorCalendar.show(getFragmentManager(), "cal");
    }

    private void changeColorForPlayer(int player, int color){
        if(player == 0 && color != UserAccessor.getUser(getActivity()).getColorPlayer2().getColor()) {
            UserAccessor.getUser(getActivity()).setColorPlayer1(new ColorCustom(color));
            UserAccessor.getUser(getActivity()).save(getActivity());
            initColorImage(UIImageColorPlayer1, 0);
        } else if(player == 1 && color != UserAccessor.getUser(getActivity()).getColorPlayer1().getColor()) {
            UserAccessor.getUser(getActivity()).setColorPlayer2(new ColorCustom(color));
            UserAccessor.getUser(getActivity()).save(getActivity());
            initColorImage(UIImageColorPlayer2, 1);
        }
    }

}
