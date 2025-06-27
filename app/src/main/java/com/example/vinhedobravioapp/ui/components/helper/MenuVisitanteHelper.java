package com.example.vinhedobravioapp.ui.components.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.bemvindo.ContatoActivity;
import com.example.vinhedobravioapp.ui.components.inicial.DashboardAdmActivity;
import com.example.vinhedobravioapp.ui.components.inicial.HistoriaActivity;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;
import com.example.vinhedobravioapp.ui.components.vinhos.EstoqueActivity;
import com.example.vinhedobravioapp.ui.components.visitas.VisitasActivity;

public class MenuVisitanteHelper {

    public static void show(Activity activity, Integer origemAdm) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View popupView = inflater.inflate(R.layout.home_menu_visitante, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                true
        );

        popupWindow.showAtLocation(activity.findViewById(android.R.id.content), Gravity.TOP | Gravity.START, 0, 0);

        LinearLayout dashboard_btn = popupView.findViewById(R.id.dashboard_btn);
        LinearLayout historia_btn = popupView.findViewById(R.id.historia_btn);
        LinearLayout celular_btn = popupView.findViewById(R.id.celular_btn);
        LinearLayout vinhos_btn = popupView.findViewById(R.id.vinhos_btn);
        LinearLayout sair_btn = popupView.findViewById(R.id.sair_btn);
        LinearLayout fora_menu = popupView.findViewById(R.id.fora_menu);
        TextView dashboardText = dashboard_btn.findViewById(R.id.dashboard_text);

        if(origemAdm == 1){
            dashboardText.setText(activity.getString(R.string.adm));
        } else if(origemAdm == 0){
            dashboardText.setText(activity.getString(R.string.rep));
        }  else{
            dashboard_btn.setVisibility(View.GONE);
        }

        historia_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, HistoriaActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), origemAdm);
            intent.putExtra("forcar_menu_visitante", true);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            popupWindow.dismiss();
        });

        celular_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ContatoActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            popupWindow.dismiss();
        });

        dashboard_btn.setOnClickListener(view -> {
            popupWindow.dismiss();
            mostrarConfirmacaoSaida(origemAdm, activity);
        });

        vinhos_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, EstoqueActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), origemAdm);
            intent.putExtra("forcar_menu_visitante", true);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });

        sair_btn.setOnClickListener(view -> {
            popupWindow.dismiss();
            mostrarConfirmacaoSaida(-1, activity); // Corrigido: garante que visitante volta para o MenuActivity
        });

        fora_menu.setOnClickListener(view -> {
            popupWindow.dismiss();
        });
    }

    private static void mostrarConfirmacaoSaida(int destinoValor, Activity activity) {
        String destinoTexto = (destinoValor == 1 || destinoValor == 0) ?
                activity.getString(R.string.confirmar_retorno_adm) :
                activity.getString(R.string.confirmar_retorno_menu);

        String mensagem = activity.getString(R.string.pergunta_saida, destinoTexto);

        ConfirmacaoHelper.mostrarConfirmacao(activity, mensagem, () -> {
            if (destinoValor == 1) {
                Intent intent = new Intent(activity, DashboardAdmActivity.class);
                activity.startActivity(intent);
            } else if (destinoValor == 0) {
                Intent intent = new Intent(activity, VisitasActivity.class);
                activity.startActivity(intent);
            } else {
                SharedPreferences.Editor editor = activity.getSharedPreferences(activity.getString(R.string.preferencia_login), Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(activity, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            activity.finish();
        });
    }
}
