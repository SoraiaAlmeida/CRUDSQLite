package br.edu.fateczl.crud.controller;

import java.sql.SQLException;
import java.util.List;

public interface IController <T> {

    public void insert(T t) throws SQLException;
    public void modificar(T t) throws SQLException;
    public void delete(T t) throws SQLException;
    public T buscar(T t) throws SQLException;
    public List<T> listar() throws SQLException;
}
