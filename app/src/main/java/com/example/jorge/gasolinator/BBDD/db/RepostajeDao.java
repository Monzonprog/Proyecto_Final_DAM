package com.example.jorge.gasolinator.BBDD.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "REPOSTAJE".
*/
public class RepostajeDao extends AbstractDao<Repostaje, Void> {

    public static final String TABLENAME = "REPOSTAJE";

    /**
     * Properties of entity Repostaje.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property IdVehiculo = new Property(0, String.class, "idVehiculo", false, "ID_VEHICULO");
        public final static Property Marca = new Property(1, String.class, "marca", false, "MARCA");
        public final static Property Modelo = new Property(2, String.class, "modelo", false, "MODELO");
        public final static Property Apodo = new Property(3, String.class, "apodo", false, "APODO");
        public final static Property Tipo = new Property(4, String.class, "tipo", false, "TIPO");
        public final static Property Combustible = new Property(5, String.class, "combustible", false, "COMBUSTIBLE");
        public final static Property Foto_Uri = new Property(6, String.class, "foto_Uri", false, "FOTO__URI");
    }


    public RepostajeDao(DaoConfig config) {
        super(config);
    }
    
    public RepostajeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"REPOSTAJE\" (" + //
                "\"ID_VEHICULO\" TEXT," + // 0: idVehiculo
                "\"MARCA\" TEXT NOT NULL ," + // 1: marca
                "\"MODELO\" TEXT NOT NULL ," + // 2: modelo
                "\"APODO\" TEXT NOT NULL ," + // 3: apodo
                "\"TIPO\" TEXT NOT NULL ," + // 4: tipo
                "\"COMBUSTIBLE\" TEXT NOT NULL ," + // 5: combustible
                "\"FOTO__URI\" TEXT);"); // 6: foto_Uri
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"REPOSTAJE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Repostaje entity) {
        stmt.clearBindings();
 
        String idVehiculo = entity.getIdVehiculo();
        if (idVehiculo != null) {
            stmt.bindString(1, idVehiculo);
        }
        stmt.bindString(2, entity.getMarca());
        stmt.bindString(3, entity.getModelo());
        stmt.bindString(4, entity.getApodo());
        stmt.bindString(5, entity.getTipo());
        stmt.bindString(6, entity.getCombustible());
 
        String foto_Uri = entity.getFoto_Uri();
        if (foto_Uri != null) {
            stmt.bindString(7, foto_Uri);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Repostaje entity) {
        stmt.clearBindings();
 
        String idVehiculo = entity.getIdVehiculo();
        if (idVehiculo != null) {
            stmt.bindString(1, idVehiculo);
        }
        stmt.bindString(2, entity.getMarca());
        stmt.bindString(3, entity.getModelo());
        stmt.bindString(4, entity.getApodo());
        stmt.bindString(5, entity.getTipo());
        stmt.bindString(6, entity.getCombustible());
 
        String foto_Uri = entity.getFoto_Uri();
        if (foto_Uri != null) {
            stmt.bindString(7, foto_Uri);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public Repostaje readEntity(Cursor cursor, int offset) {
        Repostaje entity = new Repostaje( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // idVehiculo
            cursor.getString(offset + 1), // marca
            cursor.getString(offset + 2), // modelo
            cursor.getString(offset + 3), // apodo
            cursor.getString(offset + 4), // tipo
            cursor.getString(offset + 5), // combustible
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // foto_Uri
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Repostaje entity, int offset) {
        entity.setIdVehiculo(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setMarca(cursor.getString(offset + 1));
        entity.setModelo(cursor.getString(offset + 2));
        entity.setApodo(cursor.getString(offset + 3));
        entity.setTipo(cursor.getString(offset + 4));
        entity.setCombustible(cursor.getString(offset + 5));
        entity.setFoto_Uri(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(Repostaje entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(Repostaje entity) {
        return null;
    }

    @Override
    public boolean hasKey(Repostaje entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}