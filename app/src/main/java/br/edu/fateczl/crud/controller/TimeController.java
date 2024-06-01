package br.edu.fateczl.crud.controller;

import java.sql.SQLException;
import java.util.List;


import br.edu.fateczl.crud.model.Time;
import br.edu.fateczl.crud.persistence.TimeDao;

public class TimeController implements IController<Time> {

        private final TimeDao tDao;

    public TimeController(TimeDao tDao){
        this.tDao = tDao;
    }

    @Override
    public void insert(Time time) throws SQLException {
        if (tDao.open() == null){
            tDao.open();
        }
        tDao.insert(time);
        tDao.close();
    }

    @Override
    public void modificar(Time time) throws SQLException {
        if (tDao.open() == null){
            tDao.open();
        }
        tDao.update(time);
        tDao.close();
    }

    @Override
    public void delete(Time time) throws SQLException {
        tDao.open();
        try {
            tDao.delete(time);
        } finally {
            tDao.close();
        }
    }
    @Override
    public Time buscar(Time time) throws SQLException {
        tDao.open();
        try {
            return tDao.findOne(time);
        } finally {
            tDao.close();
        }
    }
    @Override
    public List<Time> listar() throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        return tDao.findAll();
    }
}
