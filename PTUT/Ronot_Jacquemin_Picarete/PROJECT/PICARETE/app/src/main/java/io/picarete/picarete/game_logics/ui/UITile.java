package io.picarete.picarete.game_logics.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;

import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.gameplay.TileBrother;
import io.picarete.picarete.model.data_sets.AssetsSet;
import io.picarete.picarete.model.data_sets.ColorSet;

/**
 * Created by root on 1/8/15.
 */
public class UITile extends ImageView implements View.OnTouchListener{
    private int size = 0;
    private Tile tile;
    private Map<ETileSide, Edge> edges;

    // Fade color Management
    private int colorBackground;
    private int colorTileBackground;
    private int colorEdgeBackground;

    // Constructor
    public UITile(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public UITile(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public UITile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    public void setTile(Tile tile){
        this.tile = tile;
    }

    public void startFade(){
        this.invalidate();
    }

    public int getColorBackground(){
        int c = -1;

        c = ColorSet.colorTileBgGeneral.getColor();

        return c;
    }

    public int getColorTileBackground(){
        int c = -1;
        if(tile != null && !tile.isComplete()){
            c = ColorSet.colorTileBg.getColor();
        } else if(tile != null && tile.isComplete()){
            if(tile.getIdPlayer() == 0)
                c =  ColorSet.colorTileBgPlayer1.getColor();
            else if(tile.getIdPlayer() == 1)
                c = ColorSet.colorTileBgPlayer2.getColor();
            else
                c = ColorSet.colorTileBgPlayerNone.getColor();
        }

        return c;
    }

    public int getColorEdge(Edge e){
        int c = -1;

        if(e.getIdPlayer() == 0)
            c = ColorSet.colorEdgePlayer1.getColor();
        else if(e.getIdPlayer() == 1)
            c = ColorSet.colorEdgePlayer2.getColor();
        else
            c = ColorSet.colorEdgePlayerNone.getColor();

        return c;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(tile != null)
            edges = tile.getEdges();

        // Background General
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getColorBackground());
        canvas.drawPaint(paint);

        // Tile BG image
        paint = new Paint();
        ColorFilter filterTile;
        filterTile = new LightingColorFilter(getColorTileBackground(), 0);
        paint.setColorFilter(filterTile);
        Bitmap tileBG = AssetsSet.getTileBackground(getContext());
        canvas.drawBitmap(tileBG, null, new Rect(0, 0, getWidth(), getHeight()), paint);

        // Edges
        if(edges != null){
            paint = new Paint();
            for(Map.Entry<ETileSide, Edge> cursor : edges.entrySet()) {
                Matrix matrix = new Matrix();

                if(cursor.getKey() == ETileSide.LEFT){
                    matrix.postRotate(90);
                } else if(cursor.getKey() == ETileSide.TOP){
                    matrix.postRotate(180);
                } else if(cursor.getKey() == ETileSide.RIGHT){
                    matrix.postRotate(-90);
                } else if(cursor.getKey() == ETileSide.BOTTOM){
                    matrix.postRotate(0);
                }

                if(cursor.getValue().isChosen()){
                    Bitmap tileEdgeRotated = AssetsSet.getEdgeBackgroundRotated(getContext(), cursor.getValue(), cursor.getKey(), matrix);
                    ColorFilter filterEdge;
                    if(!tile.isComplete())
                        filterEdge = new LightingColorFilter(getColorEdge(cursor.getValue()), 0);
                    else
                        filterEdge = new LightingColorFilter(getColorTileBackground(), 0);
                    paint.setColorFilter(filterEdge);
                    canvas.drawBitmap(tileEdgeRotated, null, new Rect(0, 0, getWidth(), getHeight()), paint);
                }


                Bitmap tileEdgeOverlayRotated = AssetsSet.getEdgeBackgroundOverlayRotated(getContext(), cursor.getValue(), cursor.getKey(), matrix);
                // Search if the edge is link with another TileBrother
                Bitmap tileOnEdgeOverlayRotated = null;
                if(tile instanceof TileBrother
                        && ((TileBrother) tile).getBrothers().get(cursor.getKey()) != null
                        && ((TileBrother) tile).getBrothers().get(cursor.getKey()).isComplete()
                        && tile.isComplete()
                        && tile.getIdPlayer() == ((TileBrother) tile).getBrothers().get(cursor.getKey()).getIdPlayer()){
                    tileOnEdgeOverlayRotated = AssetsSet.getTileOnEdgeBackgroundOverlayRotated(getContext(), tile, cursor.getKey(), matrix);
                }
                ColorFilter filterEdge;
                if(!tile.isComplete())
                    filterEdge = new LightingColorFilter(getColorEdge(cursor.getValue()), 0);
                else
                    filterEdge = new LightingColorFilter(getColorTileBackground(), 0);
                paint.setColorFilter(filterEdge);
                if(tileEdgeOverlayRotated != null)
                    canvas.drawBitmap(tileEdgeOverlayRotated, null, new Rect(0, 0, getWidth(), getHeight()), paint);
                if(tileOnEdgeOverlayRotated != null)
                    canvas.drawBitmap(tileOnEdgeOverlayRotated, null, new Rect(0, 0, getWidth(), getHeight()), paint);



            }
        }

        // Tile overlay image
        paint = new Paint();
        filterTile = new LightingColorFilter(getColorTileBackground(), 0);
        paint.setColorFilter(filterTile);
        Bitmap tileBGOverlay = AssetsSet.getTileBackgroundOverlay(getContext(), tile);
        if(tileBGOverlay != null)
            canvas.drawBitmap(tileBGOverlay, null, new Rect(0, 0, getWidth(), getHeight()), paint);

        /*
        paint = new Paint();
        paint.setColor(ColorSet.DEBUG.getColor());
        paint.setTextSize(20);
        if(tile != null)
            canvas.drawText(Integer.toString(tile.id), getWidth()/2, getWidth()/2, paint);
            */

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        size = (widthMeasureSpec > heightMeasureSpec ? heightMeasureSpec : widthMeasureSpec);
        super.onMeasure(size, size);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action){
            case MotionEvent.ACTION_UP:
                edges = tile.getEdges();
                try {
                    Edge edge = choseEdge((int) event.getX(), (int) event.getY(), getWidth());
                    Log.d(this.getClass().getName(), "Position : ("+(int)event.getX()+";"+(int)event.getY()+") / Width : "+getWidth()+" / Chose edge : "+edge);
                    tile.onClick(edge);
                } catch (ArithmeticException e){
                    Log.d(this.getClass().getName(), e.getMessage());
                }
                break;
        }

        return true;
    }

    private Edge choseEdge(float posX, float posY, int size) {

        int width = size, height = size;

        // Create points
        Point pA;
        Point pB;
        Point pC = new Point(width/2, height/2);
        Point pP = new Point((int) posX, (int) posY);

        // LEFT
        pA = new Point(0, height);
        pB = new Point(0, 0);
        if(isPointOnTriangle(pA, pB, pC, pP))
            return edges.get(ETileSide.LEFT);

        // TOP
        pA = new Point(0, 0);
        pB = new Point(width, 0);
        if(isPointOnTriangle(pA, pB, pC, pP))
            return edges.get(ETileSide.TOP);

        // RIGHT
        pA = new Point(width, 0);
        pB = new Point(width, height);
        if(isPointOnTriangle(pA, pB, pC, pP))
            return edges.get(ETileSide.RIGHT);

        // BOTTOM
        pA = new Point(width, height);
        pB = new Point(0, height);
        if(isPointOnTriangle(pA, pB, pC, pP))
            return edges.get(ETileSide.BOTTOM);

        throw new ArithmeticException(this.getClass().getName()+"No edge can be found for the point : ("+posX+"/"+posY+")");
    }

    private boolean isPointOnTriangle(Point pA, Point pB, Point pC, Point pP){
        // http://math.stackexchange.com/questions/51326/determining-if-an-arbitrary-point-lies-inside-a-triangle-defined-by-three-points

        // Move the origin at one vertex
        pB = moveToPoint(pB, pA);
        pC = moveToPoint(pC, pA);
        pP = moveToPoint(pP, pA);

        // Calculate the scalar d
        float d = pB.x*pC.y - pC.x*pB.y;
        // Calculate Barycentric weights
        float wA = (pP.x*(pB.y-pC.y) + pP.y*(pC.x-pB.x) + pB.x*pC.y - pC.x*pB.y) / d;
        float wB = (pP.x*pC.y - pP.y*pC.x) / d;
        float wC = (pP.y*pB.x - pP.x*pB.y) / d;

        boolean isOnTriangle = true;

        if(wA <= 0 || wA >= 1)
            isOnTriangle = false;
        if(wB <= 0 || wB >= 1)
            isOnTriangle = false;
        if(wC <= 0 || wC >= 1)
            isOnTriangle = false;

        return isOnTriangle;
    }

    private Point moveToPoint(Point pointToMove, Point origin){
        Point p = new Point();

        p.x = pointToMove.x - origin.x;
        p.y = pointToMove.y - origin.y;

        return p;
    }

    public Tile getTile() {
        return tile;
    }
}
