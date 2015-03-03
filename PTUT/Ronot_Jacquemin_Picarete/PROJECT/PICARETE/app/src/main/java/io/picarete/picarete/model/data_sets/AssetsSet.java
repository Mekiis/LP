package io.picarete.picarete.model.data_sets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.HashMap;
import java.util.Map;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.EdgeBad;
import io.picarete.picarete.game_logics.gameplay.EdgeGood;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.gameplay.TileBad;
import io.picarete.picarete.game_logics.gameplay.TileBrother;
import io.picarete.picarete.game_logics.gameplay.TileGood;

/**
 * Created by root on 1/12/15.
 */
public class AssetsSet {
    private static Bitmap tileBackground = null;
    private static Bitmap tileBackgroundOverlayGood = null;
    private static Bitmap tileBackgroundOverlayBad = null;
    private static Bitmap tileOnEdgeOverlay = null;
    private static Map<ETileSide, Bitmap> tileOnEdgeOverlayRotated = new HashMap<>();
    private static Bitmap edgeBackground = null;
    private static Bitmap edgeBackgroundGood = null;
    private static Bitmap edgeBackgroundBad = null;
    private static Map<ETileSide, Bitmap> edgesBackgroundRotated = new HashMap<>();
    private static Map<ETileSide, Bitmap> edgesBackgroundGoodRotated = new HashMap<>();
    private static Map<ETileSide, Bitmap> edgesBackgroundBadRotated = new HashMap<>();

    public static Bitmap getTileBackground(Context context) {
        if(tileBackground == null)
            tileBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_bg);
        return tileBackground;
    }

    public static Bitmap getEdgeBackground(Context context) {
        if(edgeBackground == null)
            edgeBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.edge_bg);

        return edgeBackground;
    }

    public static Bitmap getEdgeBackgroundRotated(Context context, Edge edge, ETileSide side, Matrix matrix){
        if(!edgesBackgroundRotated.containsKey(side))
            edgesBackgroundRotated.put(side, Bitmap.createBitmap(getEdgeBackground(context), 0, 0, getEdgeBackground(context).getWidth(), getEdgeBackground(context).getHeight(), matrix, true));

        return edgesBackgroundRotated.get(side);
    }

    public static Bitmap getEdgeBackgroundOverlay(Context context, Edge edge) {
        if (edge instanceof EdgeBad) {
            if(edgeBackgroundBad == null)
                edgeBackgroundBad = BitmapFactory.decodeResource(context.getResources(), R.drawable.edge_bad_bg);

            return edgeBackgroundBad;
        } else if (edge instanceof EdgeGood) {
            if(edgeBackgroundGood == null)
                edgeBackgroundGood = BitmapFactory.decodeResource(context.getResources(), R.drawable.edge_good_bg);

            return edgeBackgroundGood;
        }

        return null;
    }

    public static Bitmap getEdgeBackgroundOverlayRotated(Context context, Edge edge, ETileSide side, Matrix matrix){
        if (edge instanceof EdgeBad) {
            if(!edgesBackgroundBadRotated.containsKey(side))
                edgesBackgroundBadRotated.put(side, Bitmap.createBitmap(getEdgeBackgroundOverlay(context, edge), 0, 0, getEdgeBackgroundOverlay(context, edge).getWidth(), getEdgeBackgroundOverlay(context, edge).getHeight(), matrix, true));

            return edgesBackgroundBadRotated.get(side);
        } else if (edge instanceof EdgeGood) {
            if(!edgesBackgroundGoodRotated.containsKey(side))
                edgesBackgroundGoodRotated.put(side, Bitmap.createBitmap(getEdgeBackgroundOverlay(context, edge), 0, 0, getEdgeBackgroundOverlay(context, edge).getWidth(), getEdgeBackgroundOverlay(context, edge).getHeight(), matrix, true));

            return edgesBackgroundGoodRotated.get(side);
        }

        return null;
    }

    public static Bitmap getTileOnEdgeOverlay(Context context) {
        if(tileOnEdgeOverlay == null)
            tileOnEdgeOverlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_brother);

        return tileOnEdgeOverlay;
    }

    public static Bitmap getTileOnEdgeBackgroundOverlayRotated(Context context, Tile tile, ETileSide side, Matrix matrix){
        if (tile instanceof TileBrother) {
            if(!tileOnEdgeOverlayRotated.containsKey(side))
                tileOnEdgeOverlayRotated.put(side, Bitmap.createBitmap(getTileOnEdgeOverlay(context), 0, 0, getTileOnEdgeOverlay(context).getWidth(), getTileOnEdgeOverlay(context).getHeight(), matrix, true));

            return tileOnEdgeOverlayRotated.get(side);
        }

        return null;
    }

    public static Bitmap getTileBackgroundOverlay(Context context, Tile tile) {
        if (tile instanceof TileBad) {
            if (tileBackgroundOverlayBad == null)
                tileBackgroundOverlayBad = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_bad);

            return tileBackgroundOverlayBad;
        } else if (tile instanceof TileGood) {
            if (tileBackgroundOverlayGood == null)
                tileBackgroundOverlayGood = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_good);

            return tileBackgroundOverlayGood;

        }
        return null;
    }
}
