package br.edu.fateczl.crud.persistence;

import android.database.SQLException;

public interface ITimeDao {

    public TimeDao open() throws SQLException;
    public void close();
}
