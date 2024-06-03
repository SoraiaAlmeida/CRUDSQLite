package br.edu.fateczl.crud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.SQLException;
import java.util.List;


import br.edu.fateczl.crud.controller.TimeController;
import br.edu.fateczl.crud.model.Time;
import br.edu.fateczl.crud.persistence.TimeDao;



public class TimeFragment extends Fragment {

    private View view;

    private EditText etCodTime, etNomeTime, etCidadeTime;
    private Button btnInserirTime, btnAlterarTime, btnExcluirTime, btnListarTime, btnBuscarTime;

    private TextView tvListarTime;

    private TimeController tCont;



    public TimeFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);
        etCodTime = view.findViewById(R.id.etCodTime);
        etNomeTime = view.findViewById(R.id.etNomeTime);
        etCidadeTime = view.findViewById(R.id.etCidadeTime);
        btnAlterarTime = view.findViewById(R.id.btnAlterarTime);
        btnBuscarTime = view.findViewById(R.id.btnBuscarTime);
        btnExcluirTime = view.findViewById(R.id.btnExcluirTime);
        btnInserirTime = view.findViewById(R.id.btnInserirTime);
        btnListarTime = view.findViewById(R.id.btnListarTime);
        tvListarTime = view.findViewById(R.id.tvListarTime);
        tvListarTime.setMovementMethod(new ScrollingMovementMethod());

        tCont = new TimeController(new TimeDao(view.getContext()));

        btnInserirTime.setOnClickListener(op -> acaoInserir());
        btnAlterarTime.setOnClickListener(op -> acaoModificar());
        btnExcluirTime.setOnClickListener(op -> acaoExcluir());
        btnBuscarTime.setOnClickListener(op -> acaoBuscar());
        btnListarTime.setOnClickListener(op -> acaoListar());

        return view;
    }

    private void acaoInserir() {
        Time time = montaTime();
        if (time != null) {
            try {
                tCont.insert(time);
                Toast.makeText(view.getContext(), "Time Inserido com Sucesso!",
                        Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Erro ao montar o time", Toast.LENGTH_LONG).show();
        }
    }
    private void acaoModificar() {
        Time time = montaTime();
        try {
            tCont.modificar(time);
            Toast.makeText(view.getContext(), "Time Atualizado com Sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }
        

    private void acaoExcluir() {
        Time time = montaTime();
        try {
            tCont.delete(time);
            Toast.makeText(view.getContext(), "Time Excluido com Sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
        
    }

    private void acaoBuscar() {
        Time time = montaTime();
        if (time == null) {
            Toast.makeText(view.getContext(), "Erro ao montar o time", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            time = tCont.buscar(time);
            if (time != null && time.getNome() != null) {
                preencherCampos(time);
            } else {
                Toast.makeText(view.getContext(), "Time Não Encontrado!", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Time> times = tCont.listar();
            StringBuffer buffer = new StringBuffer();
            for (Time j : times) {
                buffer.append(j.toString() + "\n");
            }
            tvListarTime.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        
    }
}
    private Time montaTime() {
        try {
            Time t = new Time();
            t.setCodigo(Integer.parseInt(etCodTime.getText().toString()));
            t.setNome(etNomeTime.getText().toString());
            t.setCidade(etCidadeTime.getText().toString());
            return t;
        } catch (NumberFormatException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(view.getContext(), "Código do time inválido", Toast.LENGTH_LONG).show();
            return null;
        }
    }
private void preencherCampos(Time t) {
    etCodTime.setText(String.valueOf(t.getCodigo()));
    etNomeTime.setText(String.valueOf(t.getNome()));
    etCidadeTime.setText(String.valueOf(t.getCidade()));

}

    private void limpaCampos() {
        etCodTime.setText("");
        etNomeTime.setText("");
        etCidadeTime.setText("");

    }
}

