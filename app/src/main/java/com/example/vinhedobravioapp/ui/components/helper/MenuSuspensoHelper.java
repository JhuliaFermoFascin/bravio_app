package com.example.vinhedobravioapp.ui.components.helper;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.vinhedobravioapp.R;
<<<<<<< HEAD
import com.example.vinhedobravioapp.ui.components.inicial.BemVindoActivity;
import com.example.vinhedobravioapp.ui.components.inicial.DashboardAdmActivity;
import com.example.vinhedobravioapp.ui.components.inicial.PainelRepresentanteActivity;
=======
import com.example.vinhedobravioapp.ui.components.inicial.PainelVisitanteActivity;
>>>>>>> erick
import com.example.vinhedobravioapp.ui.components.pedidos.PedidosActivity;
import com.example.vinhedobravioapp.ui.components.vinhos.EstoqueActivity;
import com.example.vinhedobravioapp.ui.components.visitas.VisitasActivity;

public class MenuSuspensoHelper {
    public static void show(Activity activity, boolean isDashboard) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View popupView = inflater.inflate(R.layout.home_menu_suspenso, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                true
        );

        popupWindow.showAtLocation(activity.findViewById(android.R.id.content), Gravity.TOP | Gravity.START, 0, 0);

        LinearLayout dashboard_btn = popupView.findViewById(R.id.dashboard_btn);
        LinearLayout estoque_btn = popupView.findViewById(R.id.estoque_btn);
        LinearLayout agenda_btn = popupView.findViewById(R.id.agenda_btn);
        LinearLayout usuarios_btn = popupView.findViewById(R.id.usuarios_btn);
        LinearLayout representantes_btn = popupView.findViewById(R.id.representantes_btn);
        LinearLayout relatorios_btn = popupView.findViewById(R.id.relatorios_btn);
        LinearLayout visitantes_page_btn = popupView.findViewById(R.id.visitantes_page_btn);
        LinearLayout representantes_page_btn = popupView.findViewById(R.id.representantes_page_btn);
        LinearLayout sair_btn = popupView.findViewById(R.id.sair_btn);
        LinearLayout fora_menu = popupView.findViewById(R.id.fora_menu);
        LinearLayout pedidos_btn = popupView.findViewById(R.id.pedidos_btn);

        if (isDashboard) {
            agenda_btn.setVisibility(View.GONE);
            dashboard_btn.setOnClickListener(view -> {
                Intent intent = new Intent(activity, DashboardAdmActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            });
        } else{
            relatorios_btn.setVisibility(View.GONE);
            usuarios_btn.setVisibility(View.GONE);
            representantes_btn.setVisibility(View.GONE);
            representantes_page_btn.setVisibility(View.GONE);
            dashboard_btn.setOnClickListener(view -> {
                Intent intent = new Intent(activity, PainelRepresentanteActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            });
        }

        fora_menu.setOnClickListener(view -> {
            popupWindow.dismiss();
        });

        int tipoUsuario = isDashboard ? 1 : 2;

        estoque_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, EstoqueActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });

        agenda_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, VisitasActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            popupWindow.dismiss();
        });

        pedidos_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, PedidosActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });

        visitantes_page_btn.setOnClickListener(view -> {
<<<<<<< HEAD
            Intent intent = new Intent(activity, BemVindoActivity.class);
            if(isDashboard){
                intent.putExtra(activity.getString(R.string.tipo_usuario_input), 1);
            }else{
                intent.putExtra(activity.getString(R.string.tipo_usuario_input), 2);
            }
=======
            Intent intent = new Intent(activity, PainelVisitanteActivity.class);
>>>>>>> erick
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });

        sair_btn.setOnClickListener(view -> {
            popupWindow.dismiss();
            activity.onBackPressed();
        });
    }
}
