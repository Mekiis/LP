package os.io.geolocos.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import os.io.geolocos.Container.Coordinate;
import os.io.geolocos.Converters;
import os.io.geolocos.R;
import os.io.geolocos.Container.SVGPoint;


public class SVGView extends ActionBarActivity {

    public final static String SVG_KEY = "SVG_K";

    private List<Coordinate> coordinates = new ArrayList<>();
    private String svgFile = "";
    private WebView UIWebView = null;
    private boolean first = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        coordinates = (List<Coordinate>) getIntent().getSerializableExtra(SVG_KEY);
        UIWebView = (WebView) findViewById(R.id.wb_view);
        UIWebView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(!first) {
                    int min = Math.min(right + left, bottom + top);
                    svgFile = Converters.exportSVG(min, min, Converters.coordinates2SVGPoints(coordinates, min, min));

                    if(svgFile != "")
                        UIWebView.loadData(svgFile, "text/html", "UTF-8");
                    first = true;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
