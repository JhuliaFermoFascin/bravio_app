package com.example.vinhedobravioapp.ui.components.inicial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.DashboardWineAdapter;
import com.example.vinhedobravioapp.database.dao.InventoryMovementDAO;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.model.InventoryMovementModel;
import com.example.vinhedobravioapp.domain.model.Vinho;
import com.example.vinhedobravioapp.loginManager.LoginManager;
import com.example.vinhedobravioapp.ui.components.helper.ConfirmacaoHelper;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DashboardAdmActivity extends AppCompatActivity {

    private PieChart pieChart;
    private LineChart lineChart;
    private final Map<String, Integer> quantidadePorNome = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_adm);

        ImageView menu_suspenso = findViewById(R.id.menu_suspenso);
        menu_suspenso.setOnClickListener(v -> MenuSuspensoHelper.show(this, 1));

        pieChart = findViewById(R.id.pieChart);
        popularPieChartVinhosMaisVendidosMesAtual();

        lineChart = findViewById(R.id.lineChart);
        configLineChart();

        TextView userNameField = findViewById(R.id.user_name);
        if (LoginManager.getInstance().getLoginStatus() != null) {
            String userName = LoginManager.getInstance().getLoginStatus().getNome();
            userNameField.setText(userName);
        } else {
            userNameField.setText("Usuário");
            // Você pode também redirecionar para o login se preferir
            // startActivity(new Intent(this, LoginActivity.class));
            // finish(); return;
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerVinhos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);

        List<Vinho> vinhosDashboard = getTop3VinhosDashboard();
        recyclerView.setAdapter(new DashboardWineAdapter(vinhosDashboard));
    }

    @Override
    public void onBackPressed() {
        String mensagem = getString(R.string.pergunta_saida, getString(R.string.confirmar_retorno_menu));
        ConfirmacaoHelper.mostrarConfirmacao(this, mensagem, () -> {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
            editor.clear().apply();
            LoginManager.getInstance().clearLoginStatus(this);
            Intent intent = new Intent(DashboardAdmActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        popularPieChartVinhosMaisVendidosMesAtual();
        configLineChart();
    }

    // Top 3 vinhos comparando SAIDA no mês atual x mês anterior
    private List<Vinho> getTop3VinhosDashboard() {
        List<InventoryMovementModel> movs = new InventoryMovementDAO(this).getAll();
        Calendar c = Calendar.getInstance();
        int mAtual = c.get(Calendar.MONTH) + 1, aAtual = c.get(Calendar.YEAR);
        int mAnt = mAtual == 1 ? 12 : mAtual - 1;
        int aAnt = mAtual == 1 ? aAtual - 1 : aAtual;

        Map<Long, Integer> qtdAtual = new HashMap<>();
        Map<Long, Integer> qtdAnterior = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        for (InventoryMovementModel mov : movs) {
            if (!"SAIDA".equalsIgnoreCase(mov.getMovementType())) continue;
            String d = mov.getMovementDate();
            if (d == null || d.length() < 10) continue;

            try {
                Date data = sdf.parse(d);
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH) + 1;

                if (ano == aAtual && mes == mAtual)
                    qtdAtual.merge(mov.getWineId(), mov.getQuantity(), Integer::sum);
                else if (ano == aAnt && mes == mAnt)
                    qtdAnterior.merge(mov.getWineId(), mov.getQuantity(), Integer::sum);
            } catch (ParseException e) {
                e.printStackTrace();
                // opcional: continue para ignorar datas inválidas
            }
        }

        List<Map.Entry<Long,Integer>> ord = new ArrayList<>(qtdAtual.entrySet());
        ord.sort((a,b)-> Integer.compare(b.getValue(), a.getValue()));

        WineDAO wineDAO = new WineDAO(this);
        List<Vinho> top3 = new ArrayList<>();
        for (int i=0; i<Math.min(3, ord.size()); i++) {
            long id = ord.get(i).getKey();
            int qAt = ord.get(i).getValue();
            int qAnt = qtdAnterior.getOrDefault(id,0);
            String nome = wineDAO.getNomeById(id);

            String variacao;
            boolean subiu;
            if (qAnt > 0) {
                float diff = (qAt - qAnt) * 100f / qAnt;
                variacao = String.format("%.1f%%", Math.abs(diff));
                subiu = qAt >= qAnt;
            } else {
                variacao = "Novo!";
                subiu = true;
            }
            top3.add(new Vinho(nome, qAt, variacao, subiu));
        }
        return top3;
    }


    // PieChart: vinhos mais vendidos (SAIDA) no mês atual
    private void popularPieChartVinhosMaisVendidosMesAtual() {
        InventoryMovementDAO movDAO = new InventoryMovementDAO(this);
        List<InventoryMovementModel> movs = movDAO.getAll();
        Calendar c = Calendar.getInstance();
        int mes = c.get(Calendar.MONTH) + 1, ano = c.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false);

        Map<Long, Integer> qtdPorVinho = new HashMap<>();
        for (InventoryMovementModel mov : movs) {
            if (!"SAIDA".equalsIgnoreCase(mov.getMovementType())) continue;
            try {
                String d = mov.getMovementDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(d));
                int m = cal.get(Calendar.MONTH) + 1;
                int y = cal.get(Calendar.YEAR);
                if (m == mes && y == ano)
                    qtdPorVinho.merge(mov.getWineId(), mov.getQuantity(), Integer::sum);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int total = qtdPorVinho.values().stream().mapToInt(i -> i).sum();
        if (total == 0) {
            pieChart.clear();
            pieChart.invalidate();
            return;
        }

        List<Map.Entry<Long, Integer>> list = new ArrayList<>(qtdPorVinho.entrySet());
        list.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        ArrayList<PieEntry> entries = new ArrayList<>();
        int outros = 0;
        WineDAO wineDAO = new WineDAO(this);
        for (int i = 0; i < list.size(); i++) {
            long id = list.get(i).getKey();
            int q = list.get(i).getValue();
            float pct = q * 100f / total;
            String nome = wineDAO.getNomeById(id);
            if (i < 3) {
                entries.add(new PieEntry(pct, nome));
                quantidadePorNome.put(nome, q);
            } else {
                outros += q;
            }
        }
        if (outros > 0) {
            float pctOut = outros * 100f / total;
            entries.add(new PieEntry(pctOut, "Outros"));
            quantidadePorNome.put("Outros", outros);
        }

        PieDataSet ds = new PieDataSet(entries, "");
        ds.setColors(
                getColor(R.color.button_text_green_primary),
                getColor(R.color.button_green_primary),
                getColor(R.color.button_green_terciary),
                getColor(R.color.border_button_purple_primary)
        );
        ds.setValueFormatter(new PercentFormatter(pieChart));
        ds.setValueTextSize(12f);
        ds.setValueTextColor(Color.WHITE);
        ds.setSliceSpace(2f);
        ds.setSelectionShift(8f);

        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        pieChart.setData(new PieData(ds));
        pieChart.animateY(900, Easing.EaseInOutQuad);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            PopupWindow popup;

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (popup != null) popup.dismiss();
                PieEntry pe = (PieEntry) e;
                String label = pe.getLabel();
                int q = quantidadePorNome.getOrDefault(label, 0);
                TextView tv = new TextView(DashboardAdmActivity.this);
                tv.setText(label + ": " + q + " unidades");
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundColor(Color.DKGRAY);
                tv.setPadding(20, 10, 20, 10);
                popup = new PopupWindow(tv, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popup.setOutsideTouchable(true);
                popup.setFocusable(true);
                pieChart.post(() -> {
                    int[] loc = new int[2];
                    pieChart.getLocationOnScreen(loc);
                    popup.showAtLocation(pieChart, Gravity.NO_GRAVITY,
                            (int) h.getXPx() + loc[0], (int) h.getYPx() + loc[1]);
                });
            }

            @Override
            public void onNothingSelected() {
                if (popup != null) popup.dismiss();
            }
        });
    }

    // --- LINHA ---
    private Map<String, Integer> getSaidasPorMesAno() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Map<String, Integer> cont = new HashMap<>();
        for (InventoryMovementModel mov : new InventoryMovementDAO(this).getAll()) {
            if (!"SAIDA".equalsIgnoreCase(mov.getMovementType())) continue;
            String d = mov.getMovementDate();
            try {
                Date date = sdf.parse(d);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                String chave = String.format("%04d-%02d",
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH) + 1
                );
                cont.merge(chave, mov.getQuantity(), Integer::sum);
            } catch (ParseException e) {
            }
        }
        return cont;
    }


    private Map<String, Double> getValorSaidasPorMesAno() {
        List<InventoryMovementModel> movs = new InventoryMovementDAO(this).getAll();
        Map<String, Double> total = new HashMap<>();
        for (InventoryMovementModel mov : movs) {
            if (!"SAIDA".equalsIgnoreCase(mov.getMovementType())) continue;
            String d = mov.getMovementDate();
            String chave = d.substring(6, 10) + "-" + d.substring(3, 5);
            double val = mov.getQuantity() * mov.getUnitPrice();
            total.merge(chave, val, Double::sum);
        }
        return total;
    }

    private ArrayList<Entry> montarEntradasGraficoLinha() {
        Map<String, Double> valores = getValorSaidasPorMesAno();
        List<String> meses = new ArrayList<>(valores.keySet());
        Collections.sort(meses);
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < meses.size(); i++) {
            entries.add(new Entry(i, valores.get(meses.get(i)).floatValue()));
        }
        return entries;
    }


    private List<String> obterChavesOrdenadas() {
        List<String> meses = new ArrayList<>(getSaidasPorMesAno().keySet());
        Collections.sort(meses);
        return meses;
    }

    private void configLineChart() {
        ArrayList<Entry> e = montarEntradasGraficoLinha();
        if (e.isEmpty()) {
            lineChart.clear();
            lineChart.invalidate();
            return;
        }
        LineDataSet ds = new LineDataSet(e, "Movimentações de saída");
        ds.setColor(getColor(R.color.border_button_purple_primary));
        ds.setCircleColor(getColor(R.color.border_button_purple_primary));
        ds.setDrawCircles(true);
        ds.setCircleRadius(5f);
        ds.setDrawValues(false);

        lineChart.setData(new LineData(ds));
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.marker_view);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);

        XAxis x = lineChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawAxisLine(false);
        x.setGranularity(1f);
        x.setTextColor(Color.BLACK);
        x.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int idx = (int) value;
                List<String> meses = obterChavesOrdenadas();
                if (idx >= 0 && idx < meses.size()) {
                    String chave = meses.get(idx);
                    String[] p = chave.split("-");
                    if (p.length == 2 && p[0].length() >= 4 && p[1].length() >= 2) {
                        // p[0] = "2025", p[1] = "06"
                        return p[1] + "/" + p[0].substring(2);
                    }
                }
                return "";
            }
        });

        YAxis y = lineChart.getAxisLeft();
        y.setTextColor(Color.BLACK);
        y.setAxisMinimum(0f);
        y.enableGridDashedLine(8f, 8f, 0f);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            PopupWindow popup;

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (popup != null) popup.dismiss();
                int idx = (int) e.getX();
                List<String> meses = obterChavesOrdenadas();
                if (idx >= 0 && idx < meses.size()) {
                    String chave = meses.get(idx);
                    int quantidadePedidos = contarPedidosPorMes(chave);
                    String[] p = chave.split("-");
                    String txt = p[1] + "/" + p[0] + ": " + quantidadePedidos + " unidades vendidas";

                    TextView tv = new TextView(DashboardAdmActivity.this);
                    tv.setText(txt);
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundColor(Color.DKGRAY);
                    tv.setPadding(20, 10, 20, 10);

                    popup = new PopupWindow(tv, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    popup.setOutsideTouchable(true);
                    popup.setFocusable(true);
                    lineChart.post(() -> {
                        int[] loc = new int[2];
                        lineChart.getLocationOnScreen(loc);
                        popup.showAtLocation(lineChart, Gravity.NO_GRAVITY,
                                (int) h.getXPx() + loc[0], (int) h.getYPx() + loc[1]);
                    });
                }
            }

            @Override
            public void onNothingSelected() {
                if (popup != null) popup.dismiss();
            }
        });

    }

    private double calcularValorTotalPorMes(String mesAno) {
        List<InventoryMovementModel> movs = new InventoryMovementDAO(this).getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        double total = 0.0;
        for (InventoryMovementModel mov : movs) {
            if (!"SAIDA".equalsIgnoreCase(mov.getMovementType())) continue;
            String data = mov.getMovementDate();
            try {
                Date date = sdf.parse(data);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                String chave = String.format("%04d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
                if (chave.equals(mesAno)) {
                    total += mov.getQuantity() * mov.getUnitPrice();
                }
            } catch (ParseException ex) {
            }
        }
        return total;
    }

    private int contarPedidosPorMes(String mesAno) {
        List<InventoryMovementModel> movs = new InventoryMovementDAO(this).getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int total = 0;
        for (InventoryMovementModel mov : movs) {
            if (!"SAIDA".equalsIgnoreCase(mov.getMovementType())) continue;
            try {
                Date date = sdf.parse(mov.getMovementDate());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                String chave = String.format("%04d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
                if (chave.equals(mesAno)) {
                    total++;
                }
            } catch (ParseException ignored) {}
        }
        return total;
    }


}
