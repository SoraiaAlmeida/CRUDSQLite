package br.edu.fateczl.crud.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    public void insert(Jogador jogador) throws java.sql.SQLException {
        if (jogadorExiste(jogador.getId())) {
            throw new SQLException("ID já existente");
        }
        ContentValues contentValues = getcontentValues(jogador);
        database.insert("jogador", null, contentValues);
    }

    private boolean jogadorExiste(int id) {
        String sql = "SELECT id FROM jogador WHERE id = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return exists;
    }


    @Override
    public int update(Jogador jogador) throws java.sql.SQLException {
        ContentValues contentValues = getcontentValues(jogador);
        int ret = database.update("jogador", contentValues, "id = " +
                jogador.getId(), null);
        return ret;
    }
    @Override
    public void delete(Jogador jogador) throws java.sql.SQLException {
        database.delete("jogador", "id = " + jogador.getId(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findOne(Jogador jogador) throws java.sql.SQLException {
        String sql = "SELECT id, nome, data_nasc, altura, peso, timeCodigo FROM jogador WHERE id = " + jogador.getId();
        Cursor cursor = database.rawQuery(sql, null);

        //if (cursor != null) {
            //cursor.moveToFirst();

            //if (!cursor.isAfterLast()) {

        if (cursor != null && cursor.moveToFirst()) {
                jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
                jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                jogador.setDataNasc(cursor.getString(cursor.getColumnIndex("data_nasc")));
                jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
                jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));

            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("timeCodigo")));
            jogador.setTime(time);
            cursor.close();
            return jogador;
        } else {
            if (cursor != null) cursor.close();
            return null;
        }
    }
    @SuppressLint("Range")
    @Override
    public List<Jogador> findAll() throws java.sql.SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT id, nome, data_nasc, altura, peso, timeCodigo FROM jogador";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Jogador jogador = new Jogador();
                jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
                jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                jogador.setDataNasc(cursor.getString(cursor.getColumnIndex("data_nasc")));
                jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
                jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));

                Time time = new Time();
                time.setCodigo(cursor.getInt(cursor.getColumnIndex("timeCodigo")));
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
        contentValues.put("data_nasc", jogador.getDataNasc().toString());
        contentValues.put("altura", jogador.getAltura());
        contentValues.put("peso", jogador.getPeso());
        contentValues.put("timeCodigo", jogador.getTime().getCodigo());
        return contentValues;
    }
}
