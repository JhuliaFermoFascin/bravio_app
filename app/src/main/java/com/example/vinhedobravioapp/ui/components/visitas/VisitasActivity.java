package com.example.vinhedobravioapp.ui.components.visitas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.AgendaAdapter;
import com.example.vinhedobravioapp.database.dao.VisitDAO;
import com.example.vinhedobravioapp.database.model.VisitModel;
import com.example.vinhedobravioapp.domain.model.Agenda;
import com.example.vinhedobravioapp.domain.model.AgendaItem;
import com.example.vinhedobravioapp.ui.components.helper.ConfirmacaoHelper;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.example.vinhedobravioapp.ui.components.helper.MenuSuspensoHelper;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;
import com.example.vinhedobravioapp.utils.FeriadosUtils;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VisitasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<AgendaItem> listaAgrupada;
    private static final int REQUEST_CODE_CADASTRO = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitas);

        HeaderHelper.configurarHeader(this, getString(R.string.visit), 0, false, true, false);

        ExtendedFloatingActionButton addVisit_btn = findViewById(R.id.addVisit_btn);
        recyclerView = findViewById(R.id.orders_recycleview);
        ImageView icon_calendar = findViewById(R.id.icon_calendar);
        ImageView menu_suspenso = findViewById(R.id.menu_suspenso);

        atualizarListaVisitas();

        // Obtem visitas reais do banco
        VisitDAO visitDAO = new VisitDAO(this);
        List<VisitModel> visitasSalvas = visitDAO.getAll();

        List<Agenda> listaVisitas = new ArrayList<>();
        for (VisitModel visita : visitasSalvas) {
            String data = "";
            String horaInicio = "";

            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                SimpleDateFormat formatoDataISO = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

                Date dataHora = formatoEntrada.parse(visita.getDateTime());
                if (dataHora != null) {
                    data = formatoDataISO.format(dataHora);
                    horaInicio = formatoHora.format(dataHora);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Agenda agenda = new Agenda(
                    visita.getName(),
                    visita.getDescription(),
                    data,
                    horaInicio,
                    "",
                    visita.getLocation()
            );
            listaVisitas.add(agenda);
        }

        List<FeriadosUtils.Feriado> feriados = FeriadosUtils.carregarFeriados(this);
        if (feriados != null) {
            for (FeriadosUtils.Feriado feriado : feriados) {
                listaVisitas.add(new Agenda(
                        feriado.nome,
                        "Feriado Nacional",
                        feriado.data,
                        "",
                        "",
                        ""
                ));
            }
        }

        listaAgrupada = AgendaAdapter.agruparPorSemana(listaVisitas);

        AgendaAdapter adapter = new AgendaAdapter(listaAgrupada, this);
        recyclerView.setAdapter(adapter);

        int posicaoHoje = -1;
        String hojeStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        for (int i = 0; i < listaAgrupada.size(); i++) {
            AgendaItem item = listaAgrupada.get(i);
            if (item.type == AgendaItem.TYPE_EVENT && item.agenda != null && item.agenda.data.equals(hojeStr)) {
                posicaoHoje = i;
                break;
            }
            if (item.type == AgendaItem.TYPE_NO_EVENT && item.agenda == null) {
                posicaoHoje = i;
                break;
            }
        }

        if (posicaoHoje != -1) {
            recyclerView.scrollToPosition(posicaoHoje);
        }

        addVisit_btn.setOnClickListener(view -> {
            Intent intent = new Intent(VisitasActivity.this, CadastroVisitasActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CADASTRO);
        });

        icon_calendar.setOnClickListener(view -> showDatePicker());
        menu_suspenso.setOnClickListener(v -> MenuSuspensoHelper.show(this, 0));
    }

    private String formatarDataParaISO(String dataBR) {
        try {
            SimpleDateFormat formatoBR = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat formatoISO = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatoBR.parse(dataBR);
            return formatoISO.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onBackPressed() {
        String mensagem = getString(R.string.pergunta_saida, getString(R.string.confirmar_retorno_menu));

        ConfirmacaoHelper.mostrarConfirmacao(this, mensagem, () -> {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(VisitasActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    onDateSelected(selectedDate.getTime());
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void onDateSelected(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDateStr = sdf.format(date);

        int positionToScroll = -1;
        for (int i = 0; i < listaAgrupada.size(); i++) {
            AgendaItem item = listaAgrupada.get(i);
            if (item.agenda != null && selectedDateStr.equals(item.agenda.data)) {
                positionToScroll = i;
                break;
            }
        }

        if (positionToScroll != -1) {
            final int finalPositionToScroll = positionToScroll;
            recyclerView.post(() -> {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    layoutManager.scrollToPositionWithOffset(finalPositionToScroll, 0);
                }
            });
        } else {
            Toast.makeText(this, "Nenhum evento encontrado para essa data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CADASTRO && resultCode == RESULT_OK) {
            atualizarListaVisitas();  // método que você cria para recarregar a lista
        }
    }

    private void atualizarListaVisitas() {
        VisitDAO visitDAO = new VisitDAO(this);
        List<VisitModel> visitasSalvas = visitDAO.getAll();

        List<Agenda> listaVisitas = new ArrayList<>();
        for (VisitModel visita : visitasSalvas) {
            String data = "";
            String horaInicio = "";

            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                SimpleDateFormat formatoDataISO = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

                Date dataHora = formatoEntrada.parse(visita.getDateTime());
                if (dataHora != null) {
                    data = formatoDataISO.format(dataHora);
                    horaInicio = formatoHora.format(dataHora);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Agenda agenda = new Agenda(
                    visita.getName(),
                    visita.getDescription(),
                    data,
                    horaInicio,
                    "",
                    visita.getLocation()
            );
            listaVisitas.add(agenda);
        }

        List<FeriadosUtils.Feriado> feriados = FeriadosUtils.carregarFeriados(this);
        if (feriados != null) {
            for (FeriadosUtils.Feriado feriado : feriados) {
                listaVisitas.add(new Agenda(
                        feriado.nome,
                        "Feriado Nacional",
                        feriado.data,
                        "",
                        "",
                        ""
                ));
            }
        }

        listaAgrupada = AgendaAdapter.agruparPorSemana(listaVisitas);

        AgendaAdapter adapter = new AgendaAdapter(listaAgrupada, this);
        recyclerView.setAdapter(adapter);

        // Opcional: fazer scroll para o dia atual
        int posicaoHoje = -1;
        String hojeStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        for (int i = 0; i < listaAgrupada.size(); i++) {
            AgendaItem item = listaAgrupada.get(i);
            if (item.type == AgendaItem.TYPE_EVENT && item.agenda != null && item.agenda.data.equals(hojeStr)) {
                posicaoHoje = i;
                break;
            }
            if (item.type == AgendaItem.TYPE_NO_EVENT && item.agenda == null) {
                posicaoHoje = i;
                break;
            }
        }

        if (posicaoHoje != -1) {
            recyclerView.scrollToPosition(posicaoHoje);
        }
    }
}
