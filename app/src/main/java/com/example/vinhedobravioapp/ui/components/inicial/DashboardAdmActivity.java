package com.example.vinhedobravioapp.ui.components.inicial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.MenuSuspensoHelper;
import com.example.vinhedobravioapp.ui.components.utils.MyMarkerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class DashboardAdmActivity extends AppCompatActivity {

    private PieChart pieChart;
    private LineChart lineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_adm);

        ImageView menu_suspenso = findViewById(R.id.menu_suspenso);

        menu_suspenso.setOnClickListener(v -> MenuSuspensoHelper.show(this, true));

        //        Gráfico pizza
        pieChart = findViewById(R.id.pieChart);
        configPieChart();

//        Gráfico linhas
        lineChart = findViewById(R.id.lineChart);
        configLineChart();
    }

    @Override
    public void onBackPressed() {
        View dialogView = getLayoutInflater().inflate(R.layout.modal_confirmacao, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(DashboardAdmActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
        btnNo.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void configPieChart() {

        int[] qtd = {125, 50, 25};
        String[] nomes = {"Item 1", "Item 2", "Item 3"};

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < qtd.length; i++) {
            PieEntry e = new PieEntry(qtd[i], nomes[i]);
            entries.add(e);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        ArrayList<Integer> customColors = new ArrayList<>();
        customColors.add(getColor(R.color.button_text_green_primary));
        customColors.add(getColor(R.color.button_green_primary));
        customColors.add(getColor(R.color.button_green_terciary));
        dataSet.setColors(customColors);

        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        pieChart.setUsePercentValues(true);

        dataSet.setValueFormatter(new PercentFormatter(pieChart));
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.WHITE);

        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(8f);

        pieChart.setDrawHoleEnabled(false);

        // Esconde os labels dentro da fatia para não aparecer "Item 1" junto do percentual
        pieChart.setEntryLabelColor(Color.TRANSPARENT);
        pieChart.setEntryLabelTextSize(0f);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(14f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setYEntrySpace(4f);
        legend.setXEntrySpace(10f);

        pieChart.getDescription().setEnabled(false);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        pieChart.animateY(900, Easing.EaseInOutQuad);
        pieChart.invalidate();
    }

    private void configLineChart(){
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(0, 18));
        entries1.add(new Entry(1, 27));
        entries1.add(new Entry(2, 24));
        entries1.add(new Entry(3, 34));
        entries1.add(new Entry(4, 36));

        LineDataSet dataSet1 = new LineDataSet(entries1, "Pedidos por mês");
        dataSet1.setColor(getColor(R.color.border_button_purple_primary));
        dataSet1.setCircleColor(getColor(R.color.border_button_purple_primary));
        dataSet1.setDrawCircles(true);
        dataSet1.setCircleRadius(5f);
        dataSet1.setDrawValues(false); // NÃO mostrar valores fixos nos pontos, só no MarkerView
        dataSet1.setValueTextSize(12f);
        dataSet1.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet1);
        lineChart.setData(lineData);

        // Cria e associa o MarkerView
        MyMarkerView markerView = new MyMarkerView(this, R.layout.marker_view);
        markerView.setChartView(lineChart); // necessário para posicionamento
        lineChart.setMarker(markerView);

        // Plano de fundo estilo “paper”
        lineChart.setBackgroundColor(getColor(R.color.background_resumo_pedidos));
        lineChart.setGridBackgroundColor(Color.TRANSPARENT);
        lineChart.setDrawGridBackground(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "Item " + (int)(value + 1);
            }
        });

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextColor(Color.BLACK);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisLineColor(Color.TRANSPARENT);
        yAxis.setGridColor(Color.parseColor("#D3D0C8"));
        yAxis.enableGridDashedLine(8f, 8f, 0f);

        lineChart.getAxisRight().setEnabled(false);

        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        lineChart.getDescription().setEnabled(false);

        lineChart.animateX(800, Easing.EaseInOutQuad);
        lineChart.invalidate();
    }
}
