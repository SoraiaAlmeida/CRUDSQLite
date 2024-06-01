package br.edu.fateczl.crud.persistence;

import android.database.SQLException;

public interface IJogadorDao {

    public JogadorDao open() throws SQLException;
    public void close();
}

