package br.edu.fateczl.crud.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.crud.model.Jogador;
import br.edu.fateczl.crud.model.Time;

public class JogadorDao implements IJogadorDao, ICRUDDao<Jogador> {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public JogadorDao(Context context) {
        this.context = context;
    }

    @Override
    public JogadorDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        try {
            ContentValues contentValues = getcontentValues(jogador);
            database.insert("jogador", null, contentValues);
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            throw new SQLException("ID já existe. Escolha um ID diferente.");
        }
    }

    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues contentValues = getcontentValues(jogador);
        int ret = database.update("jogador", contentValues, "id = " + jogador.getId(), null);
        return ret;
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        database.delete("jogador", "id = " + jogador.getId(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        String sql = "SELECT a.id, a.nome AS nomeJogador, a.dataNasc, a.altura, a.peso, a.timeCodigo, " +
                "b.nome AS nomeTime, b.cidade AS cidadeTime FROM jogador a INNER JOIN time b " +
                "ON a.timeCodigo = b.codigo WHERE a.id = " + jogador.getId();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("timeCodigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nomeTime")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nomeJogador")));
            jogador.setDtNasc(cursor.getString(cursor.getColumnIndex("dataNasc")));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(time);

            cursor.close();
            return jogador;
        } else {
            cursor.close();
            return null;
        }
    }

    @SuppressLint("Range")
    @Override
    public List<Jogador> findAll() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT a.id, a.nome AS nomeJogador, a.dataNasc, a.altura, a.peso, a.timeCodigo, " +
                "b.nome AS nomeTime, b.cidade AS cidadeTime FROM jogador a INNER JOIN time b ON a.timeCodigo = b.codigo";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                Time time = new Time();
                time.setCodigo(cursor.getInt(cursor.getColumnIndex("timeCodigo")));
                time.setNome(cursor.getString(cursor.getColumnIndex("nomeTime")));
                time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

                Jogador jogador = new Jogador();
                jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
                jogador.setNome(cursor.getString(cursor.getColumnIndex("nomeJogador")));
                jogador.setDtNasc(cursor.getString(cursor.getColumnIndex("dataNasc")));
                jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
                jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
                jogador.setTime(time);
                jogadores.add(jogador);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return jogadores;
    }

    private static ContentValues getcontentValues(Jogador jogador) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", jogador.getId());
        contentValues.put("nome", jogador.getNome());
        contentValues.put("dataNasc", jogador.getDtNasc());
        contentValues.put("altura", jogador.getAltura());
        contentValues.put("peso", jogador.getPeso());
        contentValues.put("timeCodigo", jogador.getTime().getCodigo());
        return contentValues;
    }
}