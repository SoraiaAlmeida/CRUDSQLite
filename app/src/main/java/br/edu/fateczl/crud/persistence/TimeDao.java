package br.edu.fateczl.crud.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.crud.model.Time;

public class TimeDao implements ITimeDao, ICRUDDao<Time> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public TimeDao(Context context) {
        this.context = context;
    }

    @Override
    public TimeDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();

    }

    @Override
    public void insert(Time time) throws java.sql.SQLException {
        ContentValues contentValues = getContentValues(time);
        database.insert("time", null, contentValues);
    }

    @Override
    public int update(Time time) throws java.sql.SQLException {
        ContentValues contentValues = getContentValues(time);
        int ret = database.update("time", contentValues, "codigo = " +
                time.getCodigo(), null);
        return ret;
    }

    @Override
    public void delete(Time time) throws java.sql.SQLException {
        database.delete("time", "codigo = " + time.getCodigo(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public Time findOne(Time time) throws java.sql.SQLException {
        String sql = "SELECT codigo, nome, cidade FROM time WHERE codigo = " + time.getCodigo();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();

            //if (!cursor.isAfterLast()) {
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            cursor.close();
            return time;
        } else {
            cursor.close();
            return null;
        }
    }

    @SuppressLint("Range")
    @Override
    public List<Time> findAll() throws java.sql.SQLException {
        List<Time> times = new ArrayList<>();
        String sql = "SELECT codigo, nome, cidade FROM time";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                Time time = new Time();
                time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
                times.add(time);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return times;
    }

        private static ContentValues getContentValues(Time time) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("codigo", time.getCodigo());
            contentValues.put("nome", time.getNome());
            contentValues.put("cidade", time.getCidade());
            return contentValues;
    }
}
