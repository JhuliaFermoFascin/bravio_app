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
import com.example.vinhedobravioapp.database.dao.OrderDAO;
import com.example.vinhedobravioapp.database.dao.OrderItemDAO;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.model.OrderItemModel;
import com.example.vinhedobravioapp.database.model.OrderModel;
import com.example.vinhedobravioapp.domain.model.Vinho;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
        menu_suspenso.setOnClickListener(v -> MenuSuspensoHelper.show(this, true));

        pieChart = findViewById(R.id.pieChart);
        popularPieChartVinhosMaisVendidosMesAtual();

        lineChart = findViewById(R.id.lineChart);
        configLineChart();

        RecyclerView recyclerView = findViewById(R.id.recyclerVinhos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        List<Vinho> vinhosDashboard = getTop3VinhosDashboard();

        DashboardWineAdapter adapter = new DashboardWineAdapter(vinhosDashboard);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        String mensagem = getString(R.string.pergunta_saida, getString(R.string.confirmar_retorno_menu));

        ConfirmacaoHelper.mostrarConfirmacao(this, mensagem, () -> {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

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

    private List<Vinho> getTop3VinhosDashboard() {
        OrderItemDAO itemDAO = new OrderItemDAO(this);
        WineDAO wineDAO = new WineDAO(this);

        List<OrderModel> pedidos = getPedidosConcluidos();

        List<Long> idsPedidosMesAtual = new ArrayList<>();
        List<Long> idsPedidosMesAnterior = new ArrayList<>();

        Calendar agora = Calendar.getInstance();
        int mesAtual = agora.get(Calendar.MONTH) + 1;
        int anoAtual = agora.get(Calendar.YEAR);

        // Mês anterior
        int mesAnterior = mesAtual - 1;
        int anoAnterior = anoAtual;
        if (mesAnterior == 0) {
            mesAnterior = 12;
            anoAnterior--;
        }

        for (OrderModel pedido : pedidos) {
            String data = pedido.getDate();
            if (data != null) {
                try {
                    int mes = Integer.parseInt(data.substring(3, 5));
                    int ano = Integer.parseInt(data.substring(6, 10));

                    if (ano == anoAtual && mes == mesAtual) {
                        idsPedidosMesAtual.add(pedido.getOrderId());
                    } else if (ano == anoAnterior && mes == mesAnterior) {
                        idsPedidosMesAnterior.add(pedido.getOrderId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Map<Long, Integer> qtdAtualPorVinho = new HashMap<>();
        Map<Long, Integer> qtdAnteriorPorVinho = new HashMap<>();

        for (long id : idsPedidosMesAtual) {
            for (OrderItemModel item : itemDAO.getByOrderId(id)) {
                long vinhoId = item.getWineId();
                qtdAtualPorVinho.put(vinhoId, qtdAtualPorVinho.getOrDefault(vinhoId, 0) + item.getQuantity());
            }
        }

        for (long id : idsPedidosMesAnterior) {
            for (OrderItemModel item : itemDAO.getByOrderId(id)) {
                long vinhoId = item.getWineId();
                qtdAnteriorPorVinho.put(vinhoId, qtdAnteriorPorVinho.getOrDefault(vinhoId, 0) + item.getQuantity());
            }
        }

        List<Map.Entry<Long, Integer>> ordenado = new ArrayList<>(qtdAtualPorVinho.entrySet());
        ordenado.sort((a, b) -> Integer.compare(b.getValue(), a.getValue())); // decrescente

        List<Vinho> listaVinhos = new ArrayList<>();

        for (int i = 0; i < Math.min(3, ordenado.size()); i++) {
            long vinhoId = ordenado.get(i).getKey();
            int qtdAtual = ordenado.get(i).getValue();
            int qtdAnterior = qtdAnteriorPorVinho.getOrDefault(vinhoId, 0);

            String nome = wineDAO.getNomeById(vinhoId);

            String variacao;
            boolean subiu;
            if (qtdAnterior > 0) {
                float diff = ((qtdAtual - qtdAnterior) * 100f) / qtdAnterior;
                variacao = String.format("%.1f%%", Math.abs(diff));
                subiu = qtdAtual >= qtdAnterior;
            } else {
                variacao = "Novo!";
                subiu = true;
            }

            listaVinhos.add(new Vinho(nome, qtdAtual, variacao, subiu));
        }

        return listaVinhos;
    }


    private List<OrderModel> getPedidosConcluidos() {
        OrderDAO orderDAO = new OrderDAO(this);
        List<OrderModel> pedidos = orderDAO.getAll();
        List<OrderModel> concluídos = new ArrayList<>();

        for (OrderModel pedido : pedidos) {
            if (pedido.getStatus() != null && pedido.getStatus().equalsIgnoreCase("CONCLUIDO")) {
                concluídos.add(pedido);
            }
        }
        return concluídos;
    }

    private void popularPieChartVinhosMaisVendidosMesAtual() {
        OrderItemDAO itemDAO = new OrderItemDAO(this);
        WineDAO wineDAO = new WineDAO(this);

        List<OrderModel> pedidos = getPedidosConcluidos();

        List<Long> idsPedidosConcluidosMesAtual = new ArrayList<>();
        Calendar agora = Calendar.getInstance();
        int mesAtual = agora.get(Calendar.MONTH) + 1;
        int anoAtual = agora.get(Calendar.YEAR);

        for (OrderModel pedido : pedidos) {
            String data = pedido.getDate();
            if (data != null) {
                try {
                    int mes = Integer.parseInt(data.substring(3, 5));
                    int ano = Integer.parseInt(data.substring(6, 10));
                    if (ano == anoAtual && mes == mesAtual) {
                        idsPedidosConcluidosMesAtual.add(pedido.getOrderId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Map<Long, Integer> quantidadePorVinho = new HashMap<>();
        for (long pedidoId : idsPedidosConcluidosMesAtual) {
            List<OrderItemModel> itens = itemDAO.getByOrderId(pedidoId);
            for (OrderItemModel item : itens) {
                long vinhoId = item.getWineId();
                int qtd = quantidadePorVinho.getOrDefault(vinhoId, 0);
                quantidadePorVinho.put(vinhoId, qtd + item.getQuantity());
            }
        }

        List<Map.Entry<Long, Integer>> ordenado = new ArrayList<>(quantidadePorVinho.entrySet());
        ordenado.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        int totalVendido = 0;
        for (Map.Entry<Long, Integer> entry : ordenado) {
            totalVendido += entry.getValue();
        }
        if (totalVendido == 0) {
            pieChart.clear();
            pieChart.invalidate();
            return;
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        int outros = 0;

        for (int i = 0; i < ordenado.size(); i++) {
            long vinhoId = ordenado.get(i).getKey();
            int qtd = ordenado.get(i).getValue();
            float porcentagem = (float) qtd / totalVendido * 100f;

            String nomeVinho = wineDAO.getNomeById(vinhoId);

            if (nomeVinho != null && !nomeVinho.isEmpty()) {
                if (i < 3) {
                    entries.add(new PieEntry(porcentagem, nomeVinho));
                    quantidadePorNome.put(nomeVinho, qtd);
                } else {
                    outros += qtd;
                }
            }
        }

        if (outros > 0) {
            float porcentagemOutros = (float) outros / totalVendido * 100f;
            entries.add(new PieEntry(porcentagemOutros, "Outros"));
            quantidadePorNome.put("Outros", outros);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        ArrayList<Integer> cores = new ArrayList<>();
        cores.add(getColor(R.color.button_text_green_primary));
        cores.add(getColor(R.color.button_green_primary));
        cores.add(getColor(R.color.button_green_terciary));
        cores.add(getColor(R.color.border_button_purple_primary));
        dataSet.setColors(cores);

        dataSet.setValueFormatter(new PercentFormatter(pieChart));
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(8f);

        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(false);
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

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            PopupWindow popupWindow;

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

                if (e instanceof PieEntry) {
                    PieEntry entry = (PieEntry) e;
                    String label = entry.getLabel();
                    int qtd = quantidadePorNome.getOrDefault(label, 0);

                    TextView textView = new TextView(DashboardAdmActivity.this);
                    textView.setText(label + ": " + qtd + " unidades");
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundColor(Color.DKGRAY);
                    textView.setPadding(20, 10, 20, 10);

                    popupWindow = new PopupWindow(textView,
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);

                    pieChart.post(() -> {
                        int[] location = new int[2];
                        pieChart.getLocationOnScreen(location);
                        float x = h.getXPx();
                        float y = h.getYPx();
                        popupWindow.showAtLocation(pieChart, Gravity.NO_GRAVITY,
                                (int) x + location[0], (int) y + location[1]);
                    });
                }
            }

            @Override
            public void onNothingSelected() {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    private Map<String, Integer> getPedidosPorMesAno() {
        List<OrderModel> pedidos = getPedidosConcluidos();
        Map<String, Integer> contagem = new HashMap<>();

        for (OrderModel pedido : pedidos) {
            String data = pedido.getDate();
            if (data != null) {
                try {
                    int mes = Integer.parseInt(data.substring(3, 5));
                    int ano = Integer.parseInt(data.substring(6, 10));
                    String chave = ano + "-" + String.format("%02d", mes);

                    contagem.put(chave, contagem.getOrDefault(chave, 0) + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return contagem;
    }

    private Map<String, Double> getValorTotalPedidosPorMesAno() {
        OrderDAO orderDAO = new OrderDAO(this);
        List<OrderModel> pedidos = orderDAO.getAll();

        Map<String, Double> valorTotalPorMesAno = new HashMap<>();

        for (OrderModel pedido : pedidos) {
            String data = pedido.getDate();
            String status = pedido.getStatus();

            if (data != null && status.equalsIgnoreCase("CONCLUIDO")) {
                try {
                    int mes = Integer.parseInt(data.substring(3, 5));
                    int ano = Integer.parseInt(data.substring(6, 10));
                    String chave = ano + "-" + String.format("%02d", mes);

                    double valorAtual = valorTotalPorMesAno.getOrDefault(chave, 0.0);
                    valorTotalPorMesAno.put(chave, valorAtual + pedido.getTotal());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return valorTotalPorMesAno;
    }

    private ArrayList<Entry> montarEntradasGraficoLinha() {
        Map<String, Integer> contagem = getPedidosPorMesAno();
        List<String> chaves = new ArrayList<>(contagem.keySet());
        chaves.sort(String::compareTo);

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < chaves.size(); i++) {
            entries.add(new Entry(i, contagem.get(chaves.get(i))));
        }
        return entries;
    }

    private List<String> obterChavesOrdenadas() {
        List<String> chaves = new ArrayList<>(getPedidosPorMesAno().keySet());
        chaves.sort(String::compareTo);
        return chaves;
    }
    private void configLineChart() {
        ArrayList<Entry> entries1 = montarEntradasGraficoLinha();

        if (entries1.isEmpty()) {
            lineChart.clear();
            lineChart.invalidate();
            return;
        }

        LineDataSet dataSet1 = new LineDataSet(entries1, "Pedidos por mês");
        dataSet1.setColor(getColor(R.color.border_button_purple_primary));
        dataSet1.setCircleColor(getColor(R.color.border_button_purple_primary));
        dataSet1.setDrawCircles(true);
        dataSet1.setCircleRadius(5f);
        dataSet1.setDrawValues(false);
        dataSet1.setValueTextSize(12f);
        dataSet1.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet1);
        lineChart.setData(lineData);

        MyMarkerView markerView = new MyMarkerView(this, R.layout.marker_view);
        markerView.setChartView(lineChart);
        lineChart.setMarker(markerView);

        lineChart.setBackgroundColor(getColor(R.color.background_text_primary));
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
                int index = (int) value;
                List<String> mesesOrdenados = obterChavesOrdenadas();
                if (index >= 0 && index < mesesOrdenados.size()) {
                    String chave = mesesOrdenados.get(index);
                    String[] partes = chave.split("-");
                    String ano = partes[0];
                    String mes = partes[1];
                    return mes + "/" + ano.substring(2); // ex: 06/23
                }
                return "";
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

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            PopupWindow popupWindow;

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

                int index = (int) e.getX();
                List<String> mesesOrdenados = obterChavesOrdenadas();
                Map<String, Double> valoresPorMes = getValorTotalPedidosPorMesAno();

                if (index >= 0 && index < mesesOrdenados.size()) {
                    String chave = mesesOrdenados.get(index);
                    double valorTotal = valoresPorMes.getOrDefault(chave, 0.0);

                    String[] partes = chave.split("-");
                    String ano = partes[0];
                    String mes = partes[1];
                    String dataFormatada = mes + "/" + ano;  // pode usar ano.substring(2) para "23"

                    String valorFormatado = String.format("R$ %.2f", valorTotal).replace(".", ",");

                    TextView textView = new TextView(DashboardAdmActivity.this);
                    textView.setText(dataFormatada + ": " + valorFormatado);
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundColor(Color.DKGRAY);
                    textView.setPadding(20, 10, 20, 10);

                    popupWindow = new PopupWindow(textView,
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);

                    lineChart.post(() -> {
                        int[] location = new int[2];
                        lineChart.getLocationOnScreen(location);
                        popupWindow.showAtLocation(lineChart, Gravity.NO_GRAVITY,
                                (int) h.getXPx() + location[0], (int) h.getYPx() + location[1]);
                    });
                }
            }
            @Override
            public void onNothingSelected() {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

        lineChart.animateX(800, Easing.EaseInOutQuad);
        lineChart.invalidate();
    }
}
