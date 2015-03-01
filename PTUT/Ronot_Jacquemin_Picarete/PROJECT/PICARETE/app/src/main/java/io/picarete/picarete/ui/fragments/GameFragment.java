package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.ui.UITile;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.ui.custom.CustomFontTextView;
import io.picarete.picarete.ui.custom.CustomFontGridLayout;
import io.picarete.picarete.model.data_sets.ColorSet;
import io.picarete.picarete.model.Constants;

public abstract class GameFragment extends Fragment implements Game.GameEventListener {

    protected EGameMode mode;
    protected int column;
    protected int row;
    protected boolean needChosenBorderTile;
    protected boolean needChosenTile;
    protected Game game;

    protected List<UITile> UITiles;
    private int size;

    protected CustomFontGridLayout UIGridGame;
    protected RelativeLayout UIRoot;
    protected TextView UITurnPlayer1;
    protected TextView UITurnPlayer2;
    protected TextView UIScorePlayer1;
    protected TextView UIScorePlayer2;
    private LinearLayout UIScores;
    private CustomFontTextView UIScoresTitle;
    private CustomFontTextView UISubTitle;
    protected CustomFontTextView UITitle;
    private LinearLayout UIHeader;

    protected abstract View createViewFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract void createFragment(Bundle savedInstanceState);
    protected abstract void attachFragment(Activity activity);
    protected abstract void detachFragment();

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            column = getArguments().getInt(Constants.CHOSER_COLUMN_KEY);
            row = getArguments().getInt(Constants.CHOSER_ROW_KEY);
            mode = (EGameMode) getArguments().getSerializable(Constants.CHOSER_GAME_MODE_KEY);
            needChosenBorderTile = getArguments().getBoolean(Constants.CHOSER_NEED_CHOSEN_BORDER_TILE_KEY);
            needChosenTile = getArguments().getBoolean(Constants.CHOSER_NEED_CHOSEN_TILE_KEY);
        }
        createFragment(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = createViewFragment(inflater, container, savedInstanceState);

        UITurnPlayer1 = (TextView) view.findViewById(R.id.game_turn_p1);
        UITurnPlayer2 = (TextView) view.findViewById(R.id.game_turn_p2);
        UIScorePlayer1 = (TextView) view.findViewById(R.id.game_score_p1);
        UIScorePlayer2 = (TextView) view.findViewById(R.id.game_score_p2);

        UIGridGame = (CustomFontGridLayout) view.findViewById(R.id.game_grid);
        UIRoot = (RelativeLayout) view.findViewById(R.id.game_root);

        UIScores = (LinearLayout) view.findViewById(R.id.game_scores);
        UIScoresTitle = (CustomFontTextView) view.findViewById(R.id.game_scores_title);
        UISubTitle = (CustomFontTextView) view.findViewById(R.id.game_sub_title);
        UITitle = (CustomFontTextView) view.findViewById(R.id.game_title);
        UIHeader = (LinearLayout) view.findViewById(R.id.game_header);

        UISubTitle.setText(GameModeSet.gameModes.get(mode).title);
        initializeComponent();

        UIScores.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                size = getTileMeasure(column);
                createGame();
                resetScore();
                UIScores.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        return view;
    }

    protected abstract void initializeComponent();

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

    protected void createGame(){
        game = new Game(getActivity(), mode, row, column, 0);
        game.setEventListener(this);
        List<Tile> tiles = game.createGame(needChosenBorderTile, needChosenTile);

        UIGridGame.removeAllViews();
        UIGridGame.setRowCount(row);
        UIGridGame.setColumnCount(column);
        UITiles = new ArrayList<>();
        for(Tile t : tiles){
            UITile UITile = new UITile(getActivity());
            UITile.setTile(t);
            UITiles.add(UITile);
            GridLayout.Spec row = GridLayout.spec(t.row);
            GridLayout.Spec col = GridLayout.spec(t.col);

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row, col);
            lp.width = size;
            lp.height = size;
            UIGridGame.addView(UITile, lp);
        }

        OnMajGUI(0);
        OnNextPlayer(0);
    }

    protected void resetScore(){
        game.getScores().put(0, 0);
        game.getScores().put(1, 0);
    }

    protected int getTileMeasure(int column) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int heigth =  size.y;
        int headerSize = UIHeader.getHeight() + UIHeader.getPaddingBottom() + UIHeader.getPaddingTop();
        int footerSize = UIScores.getHeight() + UIScores.getPaddingBottom() + UIScores.getPaddingTop() + UIScoresTitle.getHeight() + UIScoresTitle.getPaddingBottom() + UIScoresTitle.getPaddingTop();

        heigth -= (headerSize + footerSize + dpToPx(Constants.GRID_PADDING));
        width -= dpToPx(Constants.GRID_PADDING);

        if(column>=row)
            return width/column;
        else
            return heigth/row;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void OnMajGUI(int idPlayerActual) {
        this.UIScorePlayer1.setText(Integer.toString(game.getScores().get(0)));
        this.UIScorePlayer2.setText(Integer.toString(game.getScores().get(1)));
    }

    @Override
    public void OnMajTile(List<Tile> tilesToRedraw) {
        Log.d(this.getClass().getName(), "Need to redraw "+tilesToRedraw.size()+" elements");
        for(UITile t : UITiles){
            if(tilesToRedraw.contains(t.getTile()))
                t.invalidate();
        }
    }

    @Override
    public void OnNextPlayer(int idPlayerActual) {
        if(idPlayerActual == 0){
            UITurnPlayer2.setVisibility(View.INVISIBLE);
            UITurnPlayer1.setVisibility(View.VISIBLE);
            UITurnPlayer1.setBackgroundColor(ColorSet.colorTileBgPlayer1.getColor());
            UIRoot.setBackgroundColor(ColorSet.colorTileBgPlayer1.getColor());
        } else if (idPlayerActual == 1){
            UITurnPlayer1.setVisibility(View.INVISIBLE);
            UITurnPlayer2.setVisibility(View.VISIBLE);
            UITurnPlayer2.setBackgroundColor(ColorSet.colorTileBgPlayer2.getColor());
            UIRoot.setBackgroundColor(ColorSet.colorTileBgPlayer2.getColor());
        }
    }
}
