package os.io.geolocos.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import os.io.geolocos.Container.Coordinate;
import os.io.geolocos.R;
import os.io.geolocos.Container.SVGPoint;


public class MainActivity extends ActionBarActivity {

    List<Coordinate> coordinates;
    private TextView UILatitudeDecimal;
    private TextView UILongitudeDecimal;
    private EditText UILongitudeSexa;
    private EditText UILatitudeSexa;
    private EditText UIId;
    private TableLayout UITable;
    private Button UIAddValue;
    private Button UIUndo;
    private Button UIPlus;
    private Button UIMinus;
    private Button UIDelete;
    private LinearLayout UIDataModification;
    private Button UIVizualize;

    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        coordinates = new ArrayList<>();

        setFieldSecurity();

        UIAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!UILatitudeSexa.getText().toString().equalsIgnoreCase("") && !UILongitudeSexa.getText().toString().equalsIgnoreCase("")) {
                    coordinates.add(new Coordinate(UIId.getText().toString(), Double.parseDouble(UILatitudeDecimal.getText().toString()),
                            Double.parseDouble(UILongitudeDecimal.getText().toString())));
                    listToTable(coordinates);
                    UILatitudeSexa.setText("");
                    UILongitudeSexa.setText("");
                    UIId.setText("");
                }

            }
        });

        UIVizualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String svg = exportSVG(getMinScreenSize(), getMinScreenSize(), coordinates2SVGPoints(coordinates, getMinScreenSize(), getMinScreenSize()));
                Intent i = new Intent(getApplicationContext(), SVGView.class);
                i.putExtra(SVGView.SVG_KEY, svg);
                startActivity(i);
            }
        });

        listToTable(coordinates);

        displayGUI(state);
    }

    private int getMinScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);
        int width = screen.x;
        int height = screen.y;
        return (width > height ? height : width);
    }

    private void displayGUI(int state){
        if(state == 0){
            // No point selected
            UIDataModification.setVisibility(View.GONE);
            UIUndo.setVisibility(View.GONE);
            UIAddValue.setText("Add");
        } else if(state == 1){
            // Point selected and try to modify
            UIDataModification.setVisibility(View.VISIBLE);
            UIUndo.setVisibility(View.VISIBLE);
            UIAddValue.setText("Modify");
        }
    }

    private void initializeComponents() {
        UILatitudeSexa = (EditText) findViewById(R.id.latitude_insert);
        UILongitudeSexa = (EditText) findViewById(R.id.longitude_insert);
        UIId = (EditText) findViewById(R.id.id_insert);
        UIAddValue = (Button) findViewById(R.id.btn_add);
        UIMinus = (Button) findViewById(R.id.btn_minus);
        UIDelete = (Button) findViewById(R.id.btn_delete);
        UIPlus = (Button) findViewById(R.id.btn_plus);
        UIUndo = (Button) findViewById(R.id.btn_undo);
        UIVizualize = (Button) findViewById(R.id.btn_visualize);
        UILatitudeDecimal = (TextView) findViewById(R.id.latitude_value);
        UILongitudeDecimal = (TextView) findViewById(R.id.longitude_value);
        UITable = (TableLayout) findViewById(R.id.values_table);
        UIDataModification = (LinearLayout) findViewById(R.id.data_modification);
    }

    private void setFieldSecurity() {
        UILatitudeSexa.addTextChangedListener(new TextWatcher() {
            boolean isModifcated = false;

            public void afterTextChanged(Editable s) {
                double decimal_latitude = SexaToDecimal(s.toString());
                UILatitudeDecimal.setText(Double.toString(decimal_latitude));

                if(!isModifcated){
                    isModifcated = true;
                    String stringTmp = s.toString().replace(" ", "");
                    String strFinal = "";
                    for(int i = 0; i < stringTmp.length(); i++){
                        if(i == 2 || i == 4)
                            strFinal += " ";
                        strFinal += stringTmp.charAt(i);
                    }
                    UILatitudeSexa.setText(strFinal);
                    UILatitudeSexa.setSelection(strFinal.length());
                }

                isModifcated = false;
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        UILongitudeSexa.addTextChangedListener(new TextWatcher() {
            boolean isModifcated = false;

            public void afterTextChanged(Editable s) {
                double decimal_longitude = SexaToDecimal(s.toString());
                UILongitudeDecimal.setText(Double.toString(decimal_longitude));

                if(!isModifcated){
                    isModifcated = true;
                    String stringTmp = s.toString().replace(" ", "");
                    String strFinal = "";
                    for(int i = 0; i < stringTmp.length(); i++){
                        if(i == 2 || i == 4)
                            strFinal += " ";
                        strFinal += stringTmp.charAt(i);
                    }
                    UILongitudeSexa.setText(strFinal);
                    UILongitudeSexa.setSelection(strFinal.length());
                }

                isModifcated = false;
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    private List<SVGPoint> coordinates2SVGPoints(List<Coordinate> coordinates, int width, int height) {
        List<SVGPoint> points = new ArrayList<>();
        double minWidth = Double.MAX_VALUE;
        double maxWidth = 0;
        double minHeight = Double.MAX_VALUE;
        double maxHeight = 0;

        for (Coordinate c : coordinates){
            if(c.getLongitude() < minWidth)
                minWidth = c.getLongitude();
            if(c.getLongitude() > maxWidth)
                maxWidth = c.getLongitude();
            if(c.getLatitude() < minHeight)
                minHeight = c.getLatitude();
            if(c.getLatitude() > maxHeight)
                maxHeight = c.getLatitude();
        }

        for (Coordinate c : coordinates){
            double lat = (c.getLatitude() - minHeight) * height / (maxHeight - minHeight);
            double lng = (c.getLongitude() - minWidth) * width / (maxWidth - minWidth);;
            points.add(new SVGPoint(lat, lng));
        }

        return points;
    }


    private String exportSVG(int width, int height, List<SVGPoint> points) {
        String svg = "<?xml version=\"1.0\" standalone=\"yes\"?>";
        svg += "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
        svg += "<svg width=\""+width+"px\" height=\""+height+"px\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">";

        for(int i = 0; i < points.size(); i++) {
            svg += "<circle cx=\""+points.get(i).getX()+"\" cy=\""+points.get(i).getY()+"\"";
            svg += " r=\""+points.get(i).rayon+"\" stroke=\""+points.get(i).strokeColor+"\" stroke-width=\""+points.get(i).strokeWidth+"\" fill=\""+points.get(i).fillColor+"\" />";
        }
        svg += "<rect x=\"0\" y=\"0\" width=\""+width+"\" height=\""+height+"\" style=\"stroke: #009900; stroke-width: 3; stroke-dasharray: 10 5; fill: none;\"/>";
        svg += "</svg>";

        return svg;
    }

    private void listToTable(List<Coordinate> coordinates) {
        UITable.removeAllViews();

        addToTable(UITable, new String[]{"Index", "Latitude", "Longitude"}, -1);

        for(Coordinate coordinate : coordinates){
            addToTable(UITable, new String[]{coordinate.getId(), Double.toString(coordinate.getLatitude()), Double.toString(coordinate.getLongitude())}, -1);
        }
    }

    private void addToTable(TableLayout table, String[] values, int color){
        TableRow row;
        TableRow.LayoutParams set = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        row = new TableRow(this);

        for(String s : values){
            TextView rowString = new TextView(this);
            rowString.setTextSize(20);
            rowString.setText(s);
            row.addView(rowString);
        }

        if(color != -1)
            row.setBackgroundColor(color);
        UITable.addView(row, set);
    }

    private double SexaToDecimal(String coordinates) {

        double degrees = 0;
        double minutes = 0;
        double seconds = 0;

        String[] coo = coordinates.split(" ");

        if(coo.length > 1)
            degrees = Double.parseDouble(coo[0]);

        if(coo.length > 2)
            minutes = Double.parseDouble(coo[1]);

        if(coo.length > 3)
            seconds = Double.parseDouble(coo[2]);

        double decimal = -1;

        decimal = degrees;
        decimal += minutes / 60.0f;
        decimal += seconds / 3600.0f;

        return decimal;
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
