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
import com.example.vinhedobravioapp.ui.components.clientes.ClientesActivity;

import com.example.vinhedobravioapp.ui.components.inicial.DashboardAdmActivity;
import com.example.vinhedobravioapp.ui.components.inicial.HistoriaActivity;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;
import com.example.vinhedobravioapp.ui.components.pedidos.PedidosActivity;
import com.example.vinhedobravioapp.ui.components.representantes.RepresentantesActivity;
import com.example.vinhedobravioapp.ui.components.usuarios.UsuarioActivity;
import com.example.vinhedobravioapp.ui.components.vinhos.EstoqueActivity;
import com.example.vinhedobravioapp.ui.components.visitas.VisitasActivity;

public class MenuSuspensoHelper {
    public static void show(Activity activity, Integer tipoUsuario) {
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
        LinearLayout usuarios_btn = popupView.findViewById(R.id.usuarios_btn);
        LinearLayout representantes_btn = popupView.findViewById(R.id.representantes_btn);
        LinearLayout visitantes_page_btn = popupView.findViewById(R.id.visitantes_page_btn);
        LinearLayout sair_btn = popupView.findViewById(R.id.sair_btn);
        LinearLayout fora_menu = popupView.findViewById(R.id.fora_menu);
        LinearLayout pedidos_btn = popupView.findViewById(R.id.pedidos_btn);
        LinearLayout comissoes_btn = popupView.findViewById(R.id.comissoes_btn);
        TextView dashboardText = dashboard_btn.findViewById(R.id.dashboard_text);
        ImageView dashboardImage = dashboard_btn.findViewById(R.id.dashboard_image);
        LinearLayout cliente_btn = popupView.findViewById(R.id.cliente_btn);

        if (tipoUsuario == 1) {
            dashboardText.setText(activity.getString(R.string.dashboard));
            dashboardImage.setImageResource(R.drawable.icon_dashboard);
            dashboard_btn.setOnClickListener(view -> {
                Intent intent = new Intent(activity, DashboardAdmActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            });
            usuarios_btn.setOnClickListener(view -> {
                Intent intent = new Intent(activity, UsuarioActivity.class);
                intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            });
        } else {
            usuarios_btn.setVisibility(View.GONE);
            representantes_btn.setVisibility(View.GONE);
            dashboardText.setText(activity.getString(R.string.agenda));
            dashboardImage.setImageResource(R.drawable.icon_location);
            dashboard_btn.setOnClickListener(view -> {
                Intent intent = new Intent(activity, VisitasActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            });
        }

        fora_menu.setOnClickListener(view -> {
            popupWindow.dismiss();
        });

        estoque_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, EstoqueActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });

        pedidos_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, PedidosActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });

        cliente_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ClientesActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });

        representantes_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, RepresentantesActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });

        visitantes_page_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, HistoriaActivity.class);
            intent.putExtra(activity.getString(R.string.tipo_usuario_input), tipoUsuario);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });

        sair_btn.setOnClickListener(view -> {
            popupWindow.dismiss();
            ConfirmacaoHelper.mostrarConfirmacao(activity, activity.getString(R.string.pergunta_saida, activity.getString(R.string.confirmar_retorno_menu)), () -> {
                SharedPreferences.Editor editor = activity.getSharedPreferences(
                        activity.getString(R.string.preferencia_login),
                        Context.MODE_PRIVATE
                ).edit();

                editor.clear();
                editor.apply();

                Intent intent = new Intent(activity, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                activity.finish();
            });
        });

        comissoes_btn.setOnClickListener(view -> {
            Intent intent = new Intent(activity, com.example.vinhedobravioapp.ui.components.comissoes.ComissoesActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            popupWindow.dismiss();
        });
    }
}
