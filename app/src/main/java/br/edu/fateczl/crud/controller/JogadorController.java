package br.edu.fateczl.crud.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.crud.model.Jogador;
import br.edu.fateczl.crud.persistence.JogadorDao;

public class JogadorController implements IController<Jogador>{

    private final JogadorDao jDao;

    public JogadorController(JogadorDao jDao) {
        this.jDao = jDao;
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        jDao.open();
        try {
            jDao.insert(jogador);
        } finally {
            jDao.close();
        }
    }
    @Override
    public void modificar(Jogador jogador) throws SQLException {
        jDao.open();
        try {
            jDao.update(jogador);
        } finally {
            jDao.close();
        }
    }
    @Override
    public void delete(Jogador jogador) throws SQLException {
        jDao.open();
        try {
            jDao.delete(jogador);
        } finally {
            jDao.close();
        }
    }

    @Override
    public Jogador buscar(Jogador jogador) throws SQLException {
        jDao.open();
        try {
            return jDao.findOne(jogador);
        } finally {
            jDao.close();
        }
    }

    @Override
    public List<Jogador> listar() throws SQLException {
        jDao.open();
        try {
            return jDao.findAll();
        } finally {
            jDao.close();
        }
    }
}