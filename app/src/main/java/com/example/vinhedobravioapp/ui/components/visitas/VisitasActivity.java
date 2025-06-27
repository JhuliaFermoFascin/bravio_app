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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitas);

        HeaderHelper.configurarHeader(this, getString(R.string.visit), 0, false, true, false);

        ExtendedFloatingActionButton addVisit_btn = findViewById(R.id.addVisit_btn);
        recyclerView = findViewById(R.id.orders_recycleview);
        ImageView icon_calendar = findViewById(R.id.icon_calendar);
        ImageView menu_suspenso = findViewById(R.id.menu_suspenso);

        List<Agenda> listaMock = new ArrayList<>();
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-08-11", "09:30", "10:30", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-09-05", "08:00", "09:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-04-25", "16:00", "17:00", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-07-18", "11:00", "09:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-09-11", "09:30", "10:30", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-09-12", "08:00", "12:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-05-28", "08:00", "14:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-03-21", "08:00", "09:00", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-04-28", "08:00", "14:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Visita Técnica a Pontos Críticos de Distribuição e Armazenamento com Foco em Logística Reversa", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-06-25", "09:30", "17:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Visita Técnica a Pontos Críticos de Distribuição e Armazenamento com Foco em Logística Reversa", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-05-08", "13:00", "14:00", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-05-09", "08:00", "12:00", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-06-11", "16:00", "12:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-04-24", "11:00", "10:30", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-07-22", "09:30", "10:30", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-07-09", "08:00", "10:30", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-08-08", "13:00", "15:30", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-07-14", "14:30", "12:00", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-08-12", "09:30", "14:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Visita Técnica a Pontos Críticos de Distribuição e Armazenamento com Foco em Logística Reversa", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-09-01", "16:00", "12:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Visita Técnica a Pontos Críticos de Distribuição e Armazenamento com Foco em Logística Reversa", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-08-11", "09:30", "15:30", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-09-16", "08:00", "12:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-09-29", "14:30", "09:00", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Capacitar os colaboradores com técnicas atualizadas de negociação e gestão de conflitos com foco em resultados.", "2025-09-04", "16:00", "14:00", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-05-07", "11:00", "14:00", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-08-19", "09:30", "10:30", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-03-17", "09:30", "09:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-03-19", "11:00", "09:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-07-10", "14:30", "10:30", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-09-02", "13:00", "10:30", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-03-25", "13:00", "14:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-09-03", "16:00", "12:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-03-28", "13:00", "10:30", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-06-17", "08:00", "14:00", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-09-23", "09:30", "10:30", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-06-13", "13:00", "09:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-06-06", "13:00", "12:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Visita Técnica a Pontos Críticos de Distribuição e Armazenamento com Foco em Logística Reversa", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-06-17", "09:30", "09:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-09-08", "13:00", "15:30", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-07-14", "09:30", "09:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-07-31", "14:30", "10:30", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-08-06", "09:30", "17:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-05-20", "16:00", "17:00", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-05-16", "13:00", "15:30", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-07-23", "11:00", "10:30", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-05-29", "09:30", "14:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-09-30", "08:00", "10:30", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-03-28", "09:30", "17:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-05-27", "08:00", "09:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-06-17", "09:30", "17:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-05-07", "08:00", "09:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-03-20", "14:30", "10:30", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-06-19", "08:00", "12:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-04-23", "14:30", "14:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Capacitar os colaboradores com técnicas atualizadas de negociação e gestão de conflitos com foco em resultados.", "2025-08-06", "09:30", "14:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-03-07", "16:00", "17:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Capacitar os colaboradores com técnicas atualizadas de negociação e gestão de conflitos com foco em resultados.", "2025-09-24", "16:00", "09:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-04-21", "09:30", "10:30", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-03-07", "08:00", "12:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-05-29", "11:00", "10:30", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-03-28", "14:30", "15:30", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-04-18", "14:30", "15:30", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-09-01", "16:00", "12:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-08-07", "11:00", "17:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-06-13", "09:30", "10:30", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Visita Técnica a Pontos Críticos de Distribuição e Armazenamento com Foco em Logística Reversa", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-06-16", "14:30", "09:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-05-13", "13:00", "14:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Capacitar os colaboradores com técnicas atualizadas de negociação e gestão de conflitos com foco em resultados.", "2025-08-20", "08:00", "12:00", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-07-10", "11:00", "10:30", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Visita Técnica a Pontos Críticos de Distribuição e Armazenamento com Foco em Logística Reversa", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-09-23", "09:30", "14:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-08-04", "14:30", "10:30", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-08-26", "09:30", "17:00", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-09-09", "16:00", "15:30", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-06-27", "09:30", "14:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-08-18", "14:30", "14:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-04-10", "09:30", "17:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-09-16", "08:00", "17:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-05-13", "14:30", "09:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Capacitar os colaboradores com técnicas atualizadas de negociação e gestão de conflitos com foco em resultados.", "2025-04-08", "09:30", "09:00", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-06-13", "09:30", "14:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-07-28", "11:00", "12:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-09-09", "09:30", "12:00", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-06-20", "16:00", "17:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Capacitar os colaboradores com técnicas atualizadas de negociação e gestão de conflitos com foco em resultados.", "2025-06-12", "14:30", "09:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-06-09", "13:00", "10:30", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-03-13", "13:00", "12:00", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Alinhar expectativas entre setores de marketing e comercial para lançamento de nova campanha institucional.", "2025-05-26", "08:00", "14:00", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-03-05", "08:00", "17:00", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-03-11", "08:00", "15:30", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Visita Técnica a Pontos Críticos de Distribuição e Armazenamento com Foco em Logística Reversa", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-04-09", "08:00", "15:30", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Apresentação Oficial da Nova Linha de Produtos Sazonais para o Segundo Semestre", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-04-25", "14:30", "15:30", "Espaço de Eventos Corporativos Monte Belo - Sala Magna, Avenida das Videiras, 2012, Flores da Cunha, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Avaliar oportunidades de expansão regional com parceiros estratégicos visando o aumento de market share.", "2025-05-19", "16:00", "14:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Treinamento Avançado de Técnicas Comerciais e Atendimento ao Cliente Premium", "Realizar degustação técnica e avaliação sensorial comparativa entre diferentes safras e linhas de produção.", "2025-06-10", "16:00", "12:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Reunião Estratégica de Alinhamento de Metas com Diretores Regionais e Consultores Externos", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-08-01", "13:00", "17:00", "Salão de Convenções da Federação Gaúcha de Comércio, Av. Independência, 1765, Porto Alegre, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Discutir o plano de ação para os próximos trimestres com base em indicadores de desempenho e feedback do cliente.", "2025-06-03", "13:00", "14:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));
        listaMock.add(new Agenda("Capacitação de Equipes de Vendas em Abordagem Consultiva e Ferramentas de CRM Integrado", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-03-28", "09:30", "14:00", "Espaço Gourmet da Vinícola Encantos da Serra, Estrada do Vinho, Km 15, Pinto Bandeira, RS"));
        listaMock.add(new Agenda("Encontro de Planejamento Estratégico com Parceiros de Canais B2B e Stakeholders", "Explorar alternativas logísticas com fornecedores locais para redução de custos e otimização de prazos.", "2025-04-15", "16:00", "15:30", "Auditório Principal da Associação Brasileira de Enologia, Rua dos Vinhedos, 3050, Garibaldi, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Apresentar resultados da pesquisa de mercado recente e alinhar diretrizes com o plano estratégico corporativo.", "2025-05-08", "13:00", "10:30", "Sala de Conferências Panorâmica - Torre Empresarial Sul, 18º Andar, Porto Alegre, RS"));
        listaMock.add(new Agenda("Workshop Interativo com Especialistas em Marketing Sensorial Aplicado ao Setor Vinícola", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-09-09", "13:00", "12:00", "Centro de Convenções Empresarial Vinhos do Sul - Auditório 3, Bloco D, Bento Gonçalves, RS"));
        listaMock.add(new Agenda("Sessão Especial com Enólogos Certificados sobre Harmonização de Rótulos com Gastronomia Brasileira", "Implementar nova metodologia de atendimento baseado em customer success e fidelização proativa.", "2025-04-16", "11:00", "12:00", "Centro Técnico Enológico Regional - Sala de Degustações, R. da Produção, 820, Caxias do Sul, RS"));


        List<FeriadosUtils.Feriado> feriados = com.example.vinhedobravioapp.utils.FeriadosUtils.carregarFeriados(this);
        if (feriados != null) {
            for (FeriadosUtils.Feriado feriado : feriados) {
                listaMock.add(new Agenda(
                        feriado.nome,
                        "Feriado Nacional",
                        feriado.data,
                        "",
                        "",
                        ""
                ));
            }
        }

        listaAgrupada = AgendaAdapter.agruparPorSemana(listaMock);

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

        addVisit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitasActivity.this, CadastroVisitasActivity.class);
                startActivity(intent);
            }
        });

        icon_calendar.setOnClickListener(view -> showDatePicker());

        menu_suspenso.setOnClickListener(v -> MenuSuspensoHelper.show(this, 0));
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

}
