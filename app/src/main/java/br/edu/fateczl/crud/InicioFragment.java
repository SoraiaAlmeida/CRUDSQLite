package br.edu.fateczl.crud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InicioFragment extends Fragment {

    private View view;

    private TextView tvinicio;

    public InicioFragment(){
        super();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               view =  inflater.inflate(R.layout.fragment_inicio, container, false);
               tvinicio = view.findViewById(R.id.tvInicio);
               tvinicio.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
               return view;
    }
}