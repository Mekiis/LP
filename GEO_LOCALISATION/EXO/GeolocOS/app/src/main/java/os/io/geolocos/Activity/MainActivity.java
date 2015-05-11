package os.io.geolocos.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import os.io.geolocos.Container.Coordinate;
import os.io.geolocos.FilesManager;
import os.io.geolocos.Path.Line;
import os.io.geolocos.Path.Path;
import os.io.geolocos.R;
import os.io.geolocos.Converters;


public class MainActivity extends ActionBarActivity {

    ArrayList<Coordinate> coordinates;
    private TextView UILatitudeDecimal;
    private TextView UILongitudeDecimal;
    private EditText UILongitudeSexa;
    private EditText UILatitudeSexa;
    private EditText UIId;
    private TableLayout UITable;
    private ImageButton UIAddValue;
    private ImageButton UIUndo;
    private ImageButton UIPlus;
    private ImageButton UIMinus;
    private ImageButton UIDelete;
    private LinearLayout UIDataModification;
    private Button UIVizualizeSVG;
    private Button UIVizualizeKML;
    private Button UIVizualizePath;

    private int state = 0;
    private int idSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        coordinates = new ArrayList<>();
        coordinates = readCoordinateFromFile();

        setFieldSecurity();

        UIAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!UILatitudeSexa.getText().toString().equalsIgnoreCase("") && !UILongitudeSexa.getText().toString().equalsIgnoreCase("")) {
                    if (state == 0) {
                        coordinates.add(new Coordinate(UIId.getText().toString(), Double.parseDouble(UILatitudeDecimal.getText().toString()), UILatitudeSexa.getText().toString(),
                                Double.parseDouble(UILongitudeDecimal.getText().toString()), UILongitudeSexa.getText().toString()));
                        UILatitudeSexa.setText("");
                        UILongitudeSexa.setText("");
                        UIId.setText("");
                        displayGUI(state, coordinates);
                    } else if (state == 1) {
                        coordinates.set(idSelected, new Coordinate(UIId.getText().toString(), Double.parseDouble(UILatitudeDecimal.getText().toString()), UILatitudeSexa.getText().toString(),
                                Double.parseDouble(UILongitudeDecimal.getText().toString()), UILongitudeSexa.getText().toString()));
                        UILatitudeSexa.setText("");
                        UILongitudeSexa.setText("");
                        UIId.setText("");
                        state = 0;
                        displayGUI(state, coordinates);
                    }

                    writeCoordinateToFile(coordinates);
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

        UIVizualizeSVG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SVGView.class);
                i.putExtra(SVGView.SVG_KEY, coordinates);
                startActivity(i);
            }
        });

        UIVizualizePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SVGView.class);
                i.putExtra(SVGView.SVG_KEY, coordinates);

                List<Line> lines = new Path().getPathFromPoints(coordinates, 1);
                LinkedList<Coordinate> path = new Path().getPathBetweenPoints(coordinates, coordinates.get(0), coordinates.get(1), lines);
                i.putExtra(SVGView.SVG_KEY, path);

                startActivity(i);
            }
        });

        UIVizualizeKML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean installed = isAppInstalled("com.google.earth");
                if(installed) {
                    String kml = Converters.exportKML(coordinates);
                    File file = null;
                    try {
                        file = new File(FilesManager.writeInFile(getApplicationContext(), kml, "KML"));
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.google-earth.kml+xml");
                        intent.putExtra("com.google.earth.EXTRA.tour_feature_id", "my_track");
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showGoogleEarthDialog();
                }
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
            UIAddValue.setImageResource(android.R.drawable.ic_menu_add);
        } else if(state == 1){
            // Point selected and try to modify
            UIDataModification.setVisibility(View.VISIBLE);
            UIUndo.setVisibility(View.VISIBLE);
            UIAddValue.setImageResource(R.drawable.ic_action_accept);
        }

        listToTable(coordinates);
    }

    private void initializeComponents() {
        UILatitudeSexa = (EditText) findViewById(R.id.latitude_insert);
        UILongitudeSexa = (EditText) findViewById(R.id.longitude_insert);
        UIId = (EditText) findViewById(R.id.id_insert);
        UIAddValue = (ImageButton) findViewById(R.id.btn_add);
        UIMinus = (ImageButton) findViewById(R.id.btn_minus);
        UIDelete = (ImageButton) findViewById(R.id.btn_delete);
        UIPlus = (ImageButton) findViewById(R.id.btn_plus);
        UIUndo = (ImageButton) findViewById(R.id.btn_undo);
        UIVizualizeSVG = (Button) findViewById(R.id.btn_visualize_svg);
        UIVizualizeKML = (Button) findViewById(R.id.btn_visualize_kml);
        UIVizualizePath = (Button) findViewById(R.id.btn_visualize_path);
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
                UILatitudeDecimal.setText(String.format("%.2f", decimal_latitude));

                if(!isModifcated){
                    isModifcated = true;
                    String stringTmp = s.toString().replace(" ", "");
                    String strFinal = "";
                    int nbChar = 0;
                    int nbSpace = 0;
                    for (int i = 0; i < stringTmp.length(); i++) {
                        if (stringTmp.charAt(i) >= '0' && stringTmp.charAt(i) <= '9')
                            nbChar++;

                        if (nbChar == 3 && nbSpace < 2) {
                            strFinal += " ";
                            nbChar = 1;
                            nbSpace++;
                        }

                        if (((stringTmp.charAt(i) >= '0' && stringTmp.charAt(i) <= '9') || stringTmp.charAt(i) == '-') && (nbChar <= 2 && nbSpace < 3))
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
                UILongitudeDecimal.setText(String.format("%.2f", decimal_longitude));

                if(!isModifcated) {
                    isModifcated = true;
                    String stringTmp = s.toString().replace(" ", "");
                    String strFinal = "";
                    int nbChar = 0;
                    int nbSpace = 0;
                    for (int i = 0; i < stringTmp.length(); i++) {
                        if (stringTmp.charAt(i) >= '0' && stringTmp.charAt(i) <= '9')
                            nbChar++;

                        if (nbChar == 3 && nbSpace < 2) {
                            strFinal += " ";
                            nbChar = 1;
                            nbSpace++;
                        }

                        if (((stringTmp.charAt(i) >= '0' && stringTmp.charAt(i) <= '9') || stringTmp.charAt(i) == '-') && (nbChar <= 2 && nbSpace < 3))
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

    private boolean isAppInstalled(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
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
            rowString.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(rowString, set);
        }

        if(state == 1 && idSelected == tag)
            row.setBackgroundColor(Color.GRAY);

        TableRow.LayoutParams setTable = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if(color != -1)
            row.setBackgroundColor(color);
        table.addView(row, setTable);
    }

    private void showGoogleEarthDialog() {

        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(this);
        downloadDialog.setTitle("Install Google Earth?");
        downloadDialog.setMessage("This application requires Google Earth. Would you like to install it?");
        downloadDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.earth")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.earth")));
                        }
                    }
                });
        downloadDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        downloadDialog.show();
    }

    private void writeCoordinateToFile(List<Coordinate> coordinates) {
        Gson gson = new Gson();
        String data = gson.toJson(coordinates);
        FilesManager.writeToPrivateFile(getApplicationContext(), data, "config.txt");
    }

    private ArrayList<Coordinate> readCoordinateFromFile() {
        String data = FilesManager.readFromPrivateFile(getApplicationContext(), "config.txt");
        Gson gson = new Gson();
        ArrayList<Coordinate> coordinates = gson.fromJson(data, new TypeToken<List<Coordinate>>() {}.getType());

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
