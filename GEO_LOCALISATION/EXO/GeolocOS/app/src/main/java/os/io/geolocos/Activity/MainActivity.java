package os.io.geolocos.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import os.io.geolocos.Container.Coordinate;
import os.io.geolocos.R;
import os.io.geolocos.Converters;


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
    private int idSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        coordinates = new ArrayList<>();
        coordinates = readFromFile();

        setFieldSecurity();

        UIAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!UILatitudeSexa.getText().toString().equalsIgnoreCase("") && !UILongitudeSexa.getText().toString().equalsIgnoreCase("")) {
                    if(state == 0){
                        coordinates.add(new Coordinate(UIId.getText().toString(), Double.parseDouble(UILatitudeDecimal.getText().toString()), UILatitudeSexa.getText().toString(),
                                Double.parseDouble(UILongitudeDecimal.getText().toString()), UILongitudeSexa.getText().toString()));
                        UILatitudeSexa.setText("");
                        UILongitudeSexa.setText("");
                        UIId.setText("");
                        displayGUI(state, coordinates);
                    } else if(state == 1){
                        coordinates.set(idSelected, new Coordinate(UIId.getText().toString(), Double.parseDouble(UILatitudeDecimal.getText().toString()), UILatitudeSexa.getText().toString(),
                                Double.parseDouble(UILongitudeDecimal.getText().toString()), UILongitudeSexa.getText().toString()));
                        UILatitudeSexa.setText("");
                        UILongitudeSexa.setText("");
                        UIId.setText("");
                        state = 0;
                        displayGUI(state, coordinates);
                    }

                    writeToFile(coordinates);
                }

            }
        });

        UIMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == 1){
                    Coordinate c = coordinates.get(idSelected);
                    coordinates.remove(idSelected);
                    idSelected--;
                    if(idSelected < 0)
                        idSelected = 0;
                    coordinates.add(idSelected, c);
                    displayGUI(state, coordinates);
                }
            }
        });

        UIPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == 1) {
                    Coordinate c = coordinates.get(idSelected);
                    coordinates.remove(idSelected);
                    idSelected++;
                    if (idSelected > coordinates.size())
                        idSelected = coordinates.size();
                    coordinates.add(idSelected, c);
                    displayGUI(state, coordinates);
                }
            }
        });

        UIDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 1) {
                    coordinates.remove(idSelected);
                    UILatitudeSexa.setText("");
                    UILongitudeSexa.setText("");
                    UIId.setText("");
                    state = 0;
                    displayGUI(state, coordinates);
                }
            }
        });

        UIUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 1) {
                    UILatitudeSexa.setText("");
                    UILongitudeSexa.setText("");
                    UIId.setText("");
                    state = 0;
                    displayGUI(state, coordinates);
                }
            }
        });

        UIVizualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Display display = getWindowManager().getDefaultDisplay();
                Point screen = new Point();
                display.getSize(screen);
                int width = screen.x;
                int height = screen.y;
                String svg = Converters.exportSVG(getMinScreenSize(), getMinScreenSize(), Converters.coordinates2SVGPoints(coordinates, getMinScreenSize(), getMinScreenSize()));
                Converters.exportKML(coordinates);
                Intent i = new Intent(getApplicationContext(), SVGView.class);
                i.putExtra(SVGView.SVG_KEY, svg);
                startActivity(i);
            }
        });

        displayGUI(state, coordinates);
    }

    private int getMinScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);
        int width = screen.x;
        int height = screen.y;
        return (width > height ? height : width);
    }

    private void displayGUI(int state, List<Coordinate> coordinates){
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

        listToTable(coordinates);
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
                double decimal_latitude = Converters.SexaToDecimal(s.toString());
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
                double decimal_longitude = Converters.SexaToDecimal(s.toString());
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

    private void listToTable(List<Coordinate> coordinates) {
        UITable.removeAllViews();

        addToTable(UITable, new String[]{"Index", "Latitude", "Longitude"}, -1, -1);

        int i = 0;
        for(Coordinate coordinate : coordinates){
            addToTable(UITable, new String[]{coordinate.getId(), Double.toString(coordinate.getLatitude()), Double.toString(coordinate.getLongitude())}, -1, i);
            i++;
        }
    }

    private boolean displayDataFromCoordinate(int id){
        boolean canDisplay = true;

        if(id < coordinates.size() && id > -1){
            UILatitudeSexa.setText(coordinates.get(id).getLatitudeSexa());
            UILongitudeSexa.setText(coordinates.get(id).getLongitudeSexa());
            UIId.setText(coordinates.get(id).getId());
        } else {
            canDisplay = false;
        }

        return canDisplay;
    }

    private void addToTable(TableLayout table, String[] values, int color, int tag){
        final TableRow row;
        TableRow.LayoutParams set = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        set.weight = 1;
        row = new TableRow(this);

        row.setTag(tag);

        row.setClickable(true);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(displayDataFromCoordinate((int) v.getTag())){
                    state = 1;
                    idSelected = (int) v.getTag();
                    displayGUI(state, coordinates);
                }
            }
        });

        for(String s : values){
            TextView rowString = new TextView(this);
            rowString.setTextSize(20);
            rowString.setText(s);
            row.addView(rowString, set);
        }

        if(state == 1 && idSelected == tag)
            row.setBackgroundColor(Color.GRAY);

        TableRow.LayoutParams setTable = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if(color != -1)
            row.setBackgroundColor(color);
        table.addView(row, setTable);
    }

    private void writeToFile(List<Coordinate> coordinates) {
        Gson gson = new Gson();
        String data = gson.toJson(coordinates);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data); outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private List<Coordinate> readFromFile() {
        String ret = "";
        try { InputStream inputStream = openFileInput("config.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                } inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Gson gson = new Gson();
        List<Coordinate> coordinates = gson.fromJson(ret, new TypeToken<List<Coordinate>>() {}.getType());

        if(coordinates != null)
            return coordinates;
        else
            return new ArrayList<>();
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
