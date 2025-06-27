package com.example.vinhedobravioapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.domain.model.Agenda;
import com.example.vinhedobravioapp.domain.model.AgendaItem;
import com.example.vinhedobravioapp.utils.FeriadosUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class AgendaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AgendaItem> agendaItems;
    private Set<String> datasFeriado;

    public AgendaAdapter(List<AgendaItem> agendaItems, Context context) {
        this.agendaItems = agendaItems;

        List<FeriadosUtils.Feriado> listaFeriados = FeriadosUtils.carregarFeriados(context);
        datasFeriado = new HashSet<>();
        if (listaFeriados != null) {
            for (FeriadosUtils.Feriado f : listaFeriados) {
                datasFeriado.add(f.data);
            }
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerText;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.header_text);
        }
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView dataBalao, mesBalao, tituloEvento, objetivoEvento, horaEvento, localEvento;

        public EventViewHolder(View itemView) {
            super(itemView);
            dataBalao = itemView.findViewById(R.id.data_balao);
            mesBalao = itemView.findViewById(R.id.mes_balao);
            tituloEvento = itemView.findViewById(R.id.titulo_evento);
            objetivoEvento = itemView.findViewById(R.id.objetivo_evento);
            horaEvento = itemView.findViewById(R.id.hora_evento);
            localEvento = itemView.findViewById(R.id.local_evento);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return agendaItems.get(position).type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == AgendaItem.TYPE_HEADER) {
            View view = inflater.inflate(R.layout.component_view_header_semana, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == AgendaItem.TYPE_NO_EVENT) {
            View view = inflater.inflate(R.layout.component_view_card_agenda, parent, false);
            return new EventViewHolder(view);
        } else if (viewType == AgendaItem.TYPE_MONTH_HEADER) {
            View view = inflater.inflate(R.layout.component_view_header_mes, parent, false);
            return new HeaderViewHolder(view);
        }
        else {
            View view = inflater.inflate(R.layout.component_view_card_agenda, parent, false);
            return new EventViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AgendaItem item = agendaItems.get(position);

        if (item.type == AgendaItem.TYPE_HEADER) {
            HeaderViewHolder h = (HeaderViewHolder) holder;
            h.headerText.setText(item.headerTitle);
        } else if (item.type == AgendaItem.TYPE_MONTH_HEADER) {
            HeaderViewHolder h = (HeaderViewHolder) holder;
            h.headerText.setText(item.headerTitle);
        }else if (item.type == AgendaItem.TYPE_NO_EVENT) {
            EventViewHolder vh = (EventViewHolder) holder;

            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat dia = new SimpleDateFormat("dd", Locale.getDefault());
            SimpleDateFormat mes = new SimpleDateFormat("MMM", Locale.getDefault());

            String hojeStr = parser.format(new Date());
            Date hojeDate = null;
            try {
                hojeDate = parser.parse(hojeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String diaStr = dia.format(hojeDate);
            String mesStr = mes.format(hojeDate);

            vh.dataBalao.setVisibility(View.VISIBLE);
            vh.mesBalao.setVisibility(View.VISIBLE);
            vh.dataBalao.setText(diaStr);
            vh.mesBalao.setText(mesStr);

            vh.dataBalao.setBackgroundResource(R.drawable.button_purple2);
            vh.dataBalao.setTextColor(ContextCompat.getColor(vh.itemView.getContext(), R.color.background_text_primary));

            View semAgenda = vh.itemView.findViewById(R.id.sem_agenda);
            View cardAgenda = vh.itemView.findViewById(R.id.card_agenda);

            semAgenda.setVisibility(View.VISIBLE);
            cardAgenda.setVisibility(View.GONE);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) vh.itemView.getLayoutParams();
            layoutParams.bottomMargin = dpToPx(vh.itemView, 16);
            vh.itemView.setLayoutParams(layoutParams);

        }else if (item.type == AgendaItem.TYPE_EVENT) {
            EventViewHolder vh = (EventViewHolder) holder;
            Agenda agenda = item.agenda;

            vh.tituloEvento.setText(agenda.titulo);
            vh.objetivoEvento.setText(agenda.objetivo);
            vh.horaEvento.setText(agenda.horaInicio + " – " + agenda.horaFim);
            vh.localEvento.setText(agenda.endereco);

            View semAgenda = vh.itemView.findViewById(R.id.sem_agenda);
            View cardAgenda = vh.itemView.findViewById(R.id.card_agenda);

            semAgenda.setVisibility(View.GONE);
            cardAgenda.setVisibility(View.VISIBLE);

            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat dia = new SimpleDateFormat("dd", Locale.getDefault());
            SimpleDateFormat mes = new SimpleDateFormat("MMM", Locale.getDefault());

            try {
                Date dateAtual = parser.parse(agenda.data);
                String diaStr = dia.format(dateAtual);
                String mesStr = mes.format(dateAtual);

                String dataAtualStr = agenda.data;

                boolean isMesmoDiaDoAnterior = false;
                boolean isMesmoDiaDoProximo = false;

                for (int i = position - 1; i >= 0; i--) {
                    AgendaItem anterior = agendaItems.get(i);
                    if (anterior.type == AgendaItem.TYPE_EVENT && anterior.agenda != null) {
                        if (dataAtualStr.equals(anterior.agenda.data)) {
                            isMesmoDiaDoAnterior = true;
                        }
                        break;
                    }
                }

                for (int i = position + 1; i < agendaItems.size(); i++) {
                    AgendaItem proximo = agendaItems.get(i);
                    if (proximo.type == AgendaItem.TYPE_EVENT && proximo.agenda != null) {
                        if (dataAtualStr.equals(proximo.agenda.data)) {
                            isMesmoDiaDoProximo = true;
                        }
                        break;
                    }
                }

                if (isMesmoDiaDoAnterior) {
                    vh.dataBalao.setVisibility(View.INVISIBLE);
                    vh.mesBalao.setVisibility(View.INVISIBLE);
                } else {
                    vh.dataBalao.setVisibility(View.VISIBLE);
                    vh.mesBalao.setVisibility(View.VISIBLE);
                    vh.dataBalao.setText(diaStr);
                    vh.mesBalao.setText(mesStr);
                }

                String hojeStr = parser.format(new Date());

                if (agenda.objetivo.equalsIgnoreCase("Feriado Nacional")) {
                    vh.mesBalao.setTextColor(ContextCompat.getColor(vh.itemView.getContext(), R.color.button_text_green_primary));
                    cardAgenda.setBackgroundResource(R.drawable.card_background_green);
                    if(datasFeriado.equals(hojeStr)){
                        vh.dataBalao.setBackgroundResource(R.drawable.button_green2);
                        vh.dataBalao.setTextColor(ContextCompat.getColor(vh.itemView.getContext(), R.color.background_text_primary));
                    }else{
                        vh.dataBalao.setBackgroundResource(0);
                        vh.dataBalao.setTextColor(ContextCompat.getColor(vh.itemView.getContext(), R.color.button_text_green_primary));
                    }
                    vh.objetivoEvento.setVisibility(View.GONE);
                    vh.horaEvento.setVisibility(View.GONE);
                    vh.localEvento.setVisibility(View.GONE);
                } else if (dataAtualStr.equals(hojeStr)) {
                    vh.dataBalao.setBackgroundResource(R.drawable.button_purple2);
                    vh.dataBalao.setTextColor(ContextCompat.getColor(vh.itemView.getContext(), R.color.background_text_primary));
                    vh.mesBalao.setTextColor(ContextCompat.getColor(vh.itemView.getContext(), R.color.border_text_purple_primary));
                    cardAgenda.setBackgroundResource(R.drawable.card_background_purple);
                    vh.objetivoEvento.setVisibility(View.VISIBLE);
                    vh.horaEvento.setVisibility(View.VISIBLE);
                    vh.localEvento.setVisibility(View.VISIBLE);
                } else {
                    vh.dataBalao.setBackgroundResource(0);
                    vh.dataBalao.setTextColor(ContextCompat.getColor(vh.itemView.getContext(), R.color.border_text_purple_primary));
                    vh.mesBalao.setTextColor(ContextCompat.getColor(vh.itemView.getContext(), R.color.border_text_purple_primary));
                    cardAgenda.setBackgroundResource(R.drawable.card_background_purple);
                    vh.objetivoEvento.setVisibility(View.VISIBLE);
                    vh.horaEvento.setVisibility(View.VISIBLE);
                    vh.localEvento.setVisibility(View.VISIBLE);
                }

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) vh.itemView.getLayoutParams();
                if (!isMesmoDiaDoProximo) {
                    layoutParams.bottomMargin = dpToPx(vh.itemView, 15);
                } else {
                    layoutParams.bottomMargin = dpToPx(vh.itemView, 0);
                }
                vh.itemView.setLayoutParams(layoutParams);

            } catch (ParseException e) {
                vh.dataBalao.setText("?");
                vh.mesBalao.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return agendaItems.size();
    }

    public static List<AgendaItem> agruparPorSemana(List<Agenda> agendas) {
        List<AgendaItem> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat dia = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat mesAbrev = new SimpleDateFormat("MMM", Locale.getDefault());
        SimpleDateFormat mesInt = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat mesAno = new SimpleDateFormat("MMMM 'de' yyyy", new Locale("pt", "BR"));

        String hojeStr = sdf.format(new Date());
        boolean temEventoHoje = false;

        for (Agenda agenda : agendas) {
            if (agenda.data.equals(hojeStr)) {
                temEventoHoje = true;
                break;
            }
        }

        if (!temEventoHoje) {
            agendas.add(new Agenda("", "", hojeStr, "", "", ""));
        }

        agendas.sort(Comparator.comparing(a -> a.data));

        Calendar cal = Calendar.getInstance();
        Date semanaAtual = null;
        String mesAtual = "";

        for (int i = 0; i < agendas.size(); ) {
            Agenda agenda = agendas.get(i);

            try {
                Date data = sdf.parse(agenda.data);
                String mesDaData = mesInt.format(data);

                if (!mesDaData.equals(mesAtual)) {
                    mesAtual = mesDaData;
                    String mesAnoTexto = capitalize(mesAno.format(data));
                    result.add(new AgendaItem(AgendaItem.TYPE_MONTH_HEADER, mesAnoTexto, null));
                }

                cal.setTime(data);
                cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                Date inicioSemana = cal.getTime();

                cal.add(Calendar.DAY_OF_WEEK, 6);
                Date fimSemana = cal.getTime();

                if (semanaAtual == null || !inicioSemana.equals(semanaAtual)) {
                    semanaAtual = inicioSemana;
                    String header = dia.format(inicioSemana) + " – " + dia.format(fimSemana) + " de " + mesAbrev.format(inicioSemana);
                    result.add(new AgendaItem(AgendaItem.TYPE_HEADER, header, null));
                }

                String dataAtual = agenda.data;
                List<Agenda> eventosMesmaData = new ArrayList<>();
                eventosMesmaData.add(agenda);
                int j = i + 1;
                while (j < agendas.size() && agendas.get(j).data.equals(dataAtual)) {
                    eventosMesmaData.add(agendas.get(j));
                    j++;
                }

                List<Agenda> feriados = new ArrayList<>();
                List<Agenda> outros = new ArrayList<>();
                for (Agenda ev : eventosMesmaData) {
                    if (ev.objetivo.equalsIgnoreCase("Feriado Nacional")) {
                        feriados.add(ev);
                    } else {
                        outros.add(ev);
                    }
                }

                for (Agenda f : feriados) {
                    result.add(new AgendaItem(AgendaItem.TYPE_EVENT, null, f));
                }
                for (Agenda ev : outros) {
                    result.add(new AgendaItem(AgendaItem.TYPE_EVENT, null, ev));
                }

                i += eventosMesmaData.size();

            } catch (ParseException e) {
                e.printStackTrace();
                i++;
            }
        }

        return result;
    }

    private int dpToPx(View view, int dp) {
        float density = view.getContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
