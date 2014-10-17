package com.android.calculatrice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.HashMap;

public class Main extends Activity {
    String[] symboles = {"plus", "moins"};
    boolean isNormal = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ResizeAction(View v){
        if(isNormal){
            changeColorElement(symboles, "button_", "#FFFF00");
            changeSizeElement(symboles, "button_", 50f);
            isNormal = false;
        }else {
            changeColorElement(symboles, "button_", "#888888");
            changeSizeElement(symboles, "button_", 10f);
            isNormal = true;
        }

        calculateChart("3x+4", 1f, -10f, 10f, -10f, 10f);
    }

    private String replaceVariables(String s){
        String clearString = "";
        int nbLettre = 0;
        String charTmp = "";
        for(int i = 0; i < s.length(); i++){
            if((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')){
                if(i != 0 && (s.charAt(i-1) >= '0' && s.charAt(i-1) <= '9') && nbLettre == 0){
                    clearString += "*";
                }
                nbLettre++;
                charTmp += s.charAt(i);
            }else {
                if(nbLettre == 1 && !charTmp.equalsIgnoreCase("e")){
                    clearString += "#{"+charTmp+"}";
                } else {
                    clearString += charTmp;
                }
                nbLettre = 0;
                charTmp = "";
                clearString += s.charAt(i);
            }
        }

        return clearString;
    }

    private double calculate(String function, HashMap<String, String> variables){
        Evaluator eval = new Evaluator();
        String s = "0.0";

        if(variables != null){
            eval.setVariables(variables);
        }
        try {
            eval.parse(function);
            s = eval.evaluate(function);
        } catch (EvaluationException e) {
            e.printStackTrace();
        }

        return Double.parseDouble(s);
    }

    private void changeColorElement(String[] allElement, String pre_name, String color){
        for(int i = 0; i < allElement.length; i++){
            int id = getResources().getIdentifier(pre_name+allElement[i], "id", getPackageName());
            Button btn = ((Button) findViewById(id));
            btn.setBackgroundColor(Color.parseColor(color));
        }
    }

    private void changeSizeElement(String[] allElement, String pre_name, float size){
        for(int i = 0; i < allElement.length; i++){
            int id = getResources().getIdentifier(pre_name+allElement[i], "id", getPackageName());
            Button btn = ((Button) findViewById(id));
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    protected String getPref(String file, String key, String defaulValue) {
        String s = key;
        SharedPreferences preferences = getSharedPreferences(file, 0);
        return preferences.getString(s, defaulValue);
    }

    protected void setPref(String file, String key, String value) {
        SharedPreferences preferences = getSharedPreferences(file, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void calculateChart(String function, float padding, float minX, float maxX, float minY, float maxY){
        float nbValue = maxX - minX;
        nbValue /= padding;
        double[] xValues = new double[(int) Math.ceil(nbValue)];
        double[] yValues = new double[(int) Math.ceil(nbValue)];
        for(int i = 0; i < (int) Math.ceil(nbValue); i++){
            xValues[i] = minX+padding;
            HashMap<String, String> h = new HashMap<String, String>();
            h.put("x", Double.toString((double) (minX+padding)));
            yValues[i] = (float)calculate(replaceVariables(function), h);
            if(yValues[i] < minY)
                yValues[i] = minY;
            if(yValues[i] > maxY)
                yValues[i] = maxY;
        }

        createChart(function, xValues, yValues, minY, maxY);
    }

    private void createChart(String title, double[] xValues, double[] yValues, float minY, float maxY){
        XYSeries series = new XYSeries(title);

        for(int i = 0; i < Math.max(xValues.length, yValues.length); i++){
            series.add(xValues[i],yValues[i]);
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        dataset.addSeries(series);

        // Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        // Include low and max value
        renderer.setDisplayBoundingPoints(true);
        // we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(false);
        renderer.setPointStrokeWidth(0);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        // Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(maxY);
        mRenderer.setYAxisMin(minY);
        mRenderer.setShowGrid(true); // we show the grid

        GraphicalView chartView = ChartFactory.getLineChartView(getApplicationContext(), dataset, mRenderer);

        ((LinearLayout) findViewById(R.id.chart)) .addView(chartView, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
