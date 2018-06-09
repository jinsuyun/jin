package ssm.hel_per;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class myState extends Fragment {
    View v;

    String url_daily="http://13.209.40.50:3000/dailysearch";
    double weight=0;
    double height=0;
    double targetweight=0;
    double bmi=0;
    String id="";
    int targetperiod=0;
    int worklevel=0;
    int workperiod=0;
    int sum_spent_calories=0;
    int all_spent_calories=0;
    int spent_calories=0;
    int all_eat_calories=0;
    int eat_calories=0;
    int sixpack=0;
    int leg=0;
    int chest=0;
    int shoulder=0;
    int back=0;
    int arm=0;
    String workoutday="";
    int running_time=0;
    int weight_time=0;
    Handler handler = new Handler();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    ArrayList arrayList_workoutday=new ArrayList();
    ArrayList arrayList_weight =new ArrayList();
    ArrayList arrayList_sixpack=new ArrayList();
    ArrayList arrayList_leg=new ArrayList();
    ArrayList arrayList_chest =new ArrayList();
    ArrayList arrayList_shoulder=new ArrayList();
    ArrayList arrayList_back=new ArrayList();
    ArrayList arrayList_arm =new ArrayList();
    ArrayList arrayList_running_time=new ArrayList();
    ArrayList arrayList_weight_time=new ArrayList();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.my_state, container, false);

        id = getActivity().getIntent().getStringExtra("id");
        weight=getActivity().getIntent().getDoubleExtra("weight",0);
        targetweight=getActivity().getIntent().getDoubleExtra("targetweight",0);
        targetperiod=getActivity().getIntent().getIntExtra("targetperiod",0);
        worklevel=getActivity().getIntent().getIntExtra("worklevel",0);
        workperiod=getActivity().getIntent().getIntExtra("workperiod",0);
        height=getActivity().getIntent().getDoubleExtra("height",0);


        ConnectThread thread = new ConnectThread(url_daily, id);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String test=thread.getResult();

        try {
            JSONArray ary=new JSONArray(test);

            for(int i=0;i<ary.length();i++) {
                JSONObject obj =  ary.getJSONObject(i);
                Log.i("TTTT22",""+obj);
                workoutday = obj.getString("workoutday");
                weight = obj.getDouble("weight");
                all_spent_calories = obj.getInt("all_spent_calories");
                spent_calories = obj.getInt("spent_calories");
                sixpack = obj.getInt("sixpack");
                leg = obj.getInt("leg");
                chest = obj.getInt("chest");
                shoulder = obj.getInt("shoulder");
                back = obj.getInt("back");
                arm = obj.getInt("arm");
                running_time = obj.getInt("running_time");
                weight_time = obj.getInt("weight_time");

                sum_spent_calories=sum_spent_calories+spent_calories;
                arrayList_workoutday.add(workoutday);
                arrayList_weight.add(weight);
                arrayList_sixpack.add(sixpack) ;
                arrayList_leg.add(leg);
                arrayList_chest.add(chest);
                arrayList_shoulder.add(shoulder);
                arrayList_back.add(back);
                arrayList_arm.add(arm);
                arrayList_running_time.add(running_time);
                arrayList_weight_time.add(weight_time);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        bmi=bodyAlgo.bmiCal(height,weight);
        float f_weight=Float.parseFloat(String.valueOf(weight));

        float f_targetweight=Float.parseFloat(String.valueOf(targetweight));
        float f_targetperiod=Float.parseFloat(String.valueOf(targetperiod));
        float f_worklevel=Float.parseFloat(String.valueOf(worklevel));
        float f_workperiod=Float.parseFloat(String.valueOf(workperiod));
        float f_bmi=Float.parseFloat(String.valueOf(bmi));

        HorizontalBarChart horizontalBarChart = (HorizontalBarChart) v.findViewById(R.id.chart);

        List<BarEntry> BarEntry_weight = new ArrayList<>();
        BarEntry_weight.add(new BarEntry(0f, f_weight,"체중"));

        List<BarEntry> BarEntry_targetweight = new ArrayList<>();
        BarEntry_targetweight.add(new BarEntry(1f, f_targetweight));

        List<BarEntry> BarEntry_bmi = new ArrayList<>();
        BarEntry_bmi.add(new BarEntry(2f, f_bmi));

        BarDataSet barDataSet_weight = new BarDataSet(BarEntry_weight, "현재 체중");
        BarDataSet barDataSet_bmi = new BarDataSet(BarEntry_bmi, "BMI");
        BarDataSet barDataSet_targetweight = new BarDataSet(BarEntry_targetweight, "목표 체중");

        barDataSet_bmi.setValueTextSize(20f);
        barDataSet_bmi.setValueTextColor(Color.WHITE);
        barDataSet_weight.setValueTextSize(20f);
        barDataSet_weight.setValueTextColor(Color.WHITE);
        barDataSet_targetweight.setValueTextSize(20f);
        barDataSet_targetweight.setValueTextColor(Color.WHITE);


        barDataSet_weight.setColor(Color.parseColor("#FF0000"));
        barDataSet_bmi.setColor(Color.parseColor("#DF01A5"));
        barDataSet_targetweight.setColor(Color.parseColor("#0000FF"));


        BarData barData = new BarData(barDataSet_targetweight,barDataSet_weight,barDataSet_bmi);
        horizontalBarChart.setData(barData);

        barData.setBarWidth(0.25f);

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(10f);

        //xAxis.setTextColor(Color.WHITE);


       // xAxis.enableGridDashedLine(8, 24, 0);


        YAxis yLAxis = horizontalBarChart.getAxisLeft();
        yLAxis.setTextColor(Color.WHITE);

        //yLAxis.setTextColor(Color.RED);
        yLAxis.setAxisMinimum(0f);

        YAxis yRAxis = horizontalBarChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        yRAxis.setTextSize(20f);

        horizontalBarChart.setDoubleTapToZoomEnabled(false);
        horizontalBarChart.setDrawGridBackground(false);
        horizontalBarChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        horizontalBarChart.invalidate();

        LineChart lineChart =v.findViewById(R.id.weight_chart);
        List<Entry> weight_change = new ArrayList<>();

        for(int i=0;i<arrayList_weight.size();i++) {

            weight_change.add(new Entry(i, Float.valueOf(arrayList_weight.get(i).toString()),"체중"));
        }


        LineDataSet lineDataSet_weight_change = new LineDataSet(weight_change, "체중");
        lineDataSet_weight_change.setColor(Color.parseColor("#0000FF"));
        lineDataSet_weight_change.setValueTextColor(Color.WHITE);
        lineDataSet_weight_change.setValueTextSize(20f);

        LineData lineData = new LineData(lineDataSet_weight_change);
        lineChart.setData(lineData);

        XAxis line_xAxis = lineChart.getXAxis();
        line_xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        line_xAxis.setDrawLabels(false);

        //xAxis.setTextColor(Color.YELLOW);

        // xAxis.enableGridDashedLine(8, 24, 0);


        YAxis line_yLAxis = lineChart.getAxisLeft();
        line_yLAxis.setTextColor(Color.WHITE);

        YAxis line_yRAxis = lineChart.getAxisRight();
        line_yRAxis.setDrawLabels(false);
        line_yRAxis.setDrawAxisLine(false);
        line_yRAxis.setDrawGridLines(false);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();



        RadarChart set_chart = (RadarChart)v.findViewById(R.id.set_chart);
        ArrayList<RadarEntry> set_entries = new ArrayList<>();
        set_entries.add(new RadarEntry(Float.valueOf(arrayList_back.get(0).toString()), "등"));
        set_entries.add(new RadarEntry(Float.valueOf(arrayList_arm.get(0).toString()), "팔"));
        set_entries.add(new RadarEntry(Float.valueOf(arrayList_sixpack.get(0).toString()), "복근"));
        set_entries.add(new RadarEntry(Float.valueOf(arrayList_leg.get(0).toString()), "하체"));
        set_entries.add(new RadarEntry(Float.valueOf(arrayList_chest.get(0).toString()), "가슴"));
        set_entries.add(new RadarEntry(Float.valueOf(arrayList_shoulder.get(0).toString()), "어깨"));

        RadarDataSet dataset_comp1 = new RadarDataSet(set_entries, "세트");

                dataset_comp1.setColor(Color.CYAN);

                dataset_comp1.setDrawFilled(true);

                dataset_comp1.setValueTextColor(Color.WHITE);
                dataset_comp1.setValueTextSize(15f);

                RadarData setdata = new RadarData(dataset_comp1);
                set_chart.setData(setdata);



        XAxis radar_xAxis = set_chart.getXAxis();
        //radar_xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        radar_xAxis.setTextSize(20f);
        radar_xAxis.setAxisMinimum(0f);


        radar_xAxis.setDrawAxisLine(false);
        radar_xAxis.setDrawGridLines(false);
        radar_xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"등", "팔", "복근","하체", "가슴", "어깨"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        radar_xAxis.setTextColor(Color.WHITE);

        YAxis radar_yLAxis = set_chart.getYAxis();
        radar_yLAxis.setDrawGridLines(false);
        radar_yLAxis.setDrawAxisLine(false);

       // YAxis radar_yRAxis = set_chart.getAxisRight();

       // radar_yRAxis.setDrawLabels(false);
       // radar_yRAxis.setDrawAxisLine(false);
       // radar_yRAxis.setDrawGridLines(false);

        set_chart.animateY(2000, Easing.EasingOption.EaseInCubic);
        set_chart.invalidate();
////////////////////////////////////////////////////////////////////////////////////////////////

        PieChart calories_chart = (PieChart) v.findViewById(R.id.calories_chart);
        ArrayList<PieEntry> calories_entries = new ArrayList<>();
        float tmp=all_spent_calories-sum_spent_calories;
        calories_entries.add(new PieEntry(Float.valueOf(sum_spent_calories), "소모 칼로리"));
        calories_entries.add(new PieEntry(Float.valueOf(tmp), "남은 칼로리"));

        PieDataSet pieDataSet = new PieDataSet(calories_entries, "칼로리");

        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(15f);
        pieDataSet.setDrawIcons(false);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.rgb(34,116,28));
        colors.add(Color.GRAY);

        colors.add(ColorTemplate.getHoloBlue());
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDataSet(pieDataSet);

        pieData.setValueTextSize(20f);
        pieData.setValueTextColor(Color.WHITE);
        calories_chart.setData(pieData);


        calories_chart.animateY(2000, Easing.EasingOption.EaseInCubic);
        calories_chart.invalidate();

        return v;
    }

    class ConnectThread extends Thread {
        String id;
        String url_daily;
        String result;
        public ConnectThread(String url_daily, String id) {
            this.url_daily=url_daily;
            this.id = id;
        }

        public void run() {
            try {
                final String output = request(url_daily,id);
                result=output;

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        private String request(String url_daily, String id) throws IOException {
            StringBuilder output = new StringBuilder();
            try {
                URL url = new URL(url_daily);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if(conn != null) {
                    String json = "";

                    // build jsonObject
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("id", id);

                    // convert JSONObject to JSON to String
                    json = jsonObject.toString();
                    conn.setConnectTimeout(1000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    os.write(json.getBytes("euc-kr")); // 출력 스트림에 출력.
                    os.flush(); // 출력 스트림을 플러시(비운다)하고 버퍼링 된 모든 출력 바이트를 강제 실행.
                    os.close();

                    int resCode = conn.getResponseCode();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    while(true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        output.append(line + "\n");
                    }
                    reader.close();
                    conn.disconnect();
                }
            } catch (Exception e) {
                Log.e("SampleHTTP", "Exception in processing response.", e);
            }
            return output.toString();
        }

        public String getResult(){
            return result;
        }
    }
}
