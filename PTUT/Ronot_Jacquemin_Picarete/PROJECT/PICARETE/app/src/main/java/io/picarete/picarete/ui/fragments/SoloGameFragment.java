package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.ia.AIA;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.game_logics.ia.IAFactory;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.container.userdata.User;
import io.picarete.picarete.model.container.userdata.UserAccessor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoloGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoloGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoloGameFragment extends GameFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private AIA IA;
    private EIA IAEnum;
    private Edge edgeFoundByIA;

    private OnFragmentInteractionListener mListener;

    public static SoloGameFragment newInstance(int col, int row, EIA iaName, EGameMode mode, boolean needChosenBorderTile, boolean needChosenTile) {
        SoloGameFragment fragment = new SoloGameFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.CHOSER_COLUMN_KEY, col);//col
        args.putInt(Constants.CHOSER_ROW_KEY, row);//row
        args.putSerializable(Constants.CHOSER_IA_KEY, iaName);//IA Name
        args.putSerializable(Constants.CHOSER_GAME_MODE_KEY, mode);
        args.putBoolean(Constants.CHOSER_NEED_CHOSEN_BORDER_TILE_KEY, needChosenBorderTile);
        args.putBoolean(Constants.CHOSER_NEED_CHOSEN_TILE_KEY, needChosenTile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View createViewFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_game, container, false);
        return inflate;
    }

    @Override
    protected void createFragment(Bundle savedInstanceState) {
        if (getArguments() != null) {
            IAEnum = (EIA) getArguments().getSerializable(Constants.CHOSER_IA_KEY);
        }
    }

    @Override
    protected void createGame() {
        IA = IAFactory.getIA(IAEnum);
        super.createGame();
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
        mListener = null;
    }

    @Override
    public void OnFinished() {
        int res = 0;
        if(game.getScores().get(0) > game.getScores().get(1))
            res = -1;
        else if(game.getScores().get(0) < game.getScores().get(1))
            res = 1;

        UserAccessor.getUser(getActivity()).userFinishedAGame(getActivity(), EMode.SOLO, mode, IAEnum, game.getTilesForPlayer(0).size(), game.getTilesForPlayer(1).size(),
                game.getTilesForPlayer(-1).size(), game.getScores().get(0), game.getScores().get(1), res);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(game.getScores().get(0) > game.getScores().get(1)){
            // 2. Chain together various setter methods to set the dialog
            // characteristics
            builder.setTitle(R.string.dlg_solo_win_title);
            builder.setMessage(R.string.dlg_solo_win_msg);
            // 3. Add the buttons
            builder.setNegativeButton(R.string.dlg_solo_retry,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            createGame();
                            resetScore();
                        }
                    });
            builder.setPositiveButton(R.string.dlg_solo_continue,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            getActivity().onBackPressed();
                        }
                    });
        } else if(game.getScores().get(0) < game.getScores().get(1)){
            // 2. Chain together various setter methods to set the dialog
            // characteristics
            builder.setTitle(R.string.dlg_solo_lost_title);
            builder.setMessage(R.string.dlg_solo_lost_msg);
            // 3. Add the buttons
            builder.setNegativeButton(R.string.dlg_solo_retry,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            createGame();
                            resetScore();
                        }
                    });
            builder.setPositiveButton(R.string.dlg_solo_continue,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            getActivity().onBackPressed();
                        }
                    });
        } else {
            // 2. Chain together various setter methods to set the dialog
            // characteristics
            builder.setTitle(R.string.dlg_solo_equality_title);
            builder.setMessage(R.string.dlg_solo_equality_msg);
            // 3. Add the buttons
            builder.setNegativeButton(R.string.dlg_solo_retry,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            createGame();
                            resetScore();
                        }
                    });
            builder.setPositiveButton(R.string.dlg_solo_continue,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            getActivity().onBackPressed();
                        }
                    });
        }
        // 4. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void OnNextPlayer(int idPlayerActual) {
        super.OnNextPlayer(idPlayerActual);

        if(idPlayerActual == 1){
            UIGridGame.interceptEvent = true;
            Handler handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    edgeFoundByIA = IA.getEdgeFound(row, column, game, game.getEdgesPreviousPlayed());
                    List<Tile> t = game.findNeighborFromEdge(edgeFoundByIA);
                    t.get(0).onClick(edgeFoundByIA);
                }
            };

            handler.post(r);
        } else {
            UIGridGame.interceptEvent = false;
        }
    }

    public SoloGameFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initializeComponent() {
        UITitle.setText(R.string.solo);
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

        public void onFragmentInteraction();
    }

}
