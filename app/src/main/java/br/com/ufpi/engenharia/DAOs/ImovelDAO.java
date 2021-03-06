package br.com.ufpi.engenharia.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpi.engenharia.entidade.Imovel;

/**
 * Created by Nei on 20/12/2016.
 */
public class ImovelDAO extends SQLiteOpenHelper {


    public ImovelDAO(Context context) {
        super(context, "Enge", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Imoveis(" +
                "id INTEGER PRIMARY KEY," +
                "valor INTEGER," +
                "endereco TEXT," +
                "nome TEXT NOT NULL UNIQUE);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Imoveis";
        db.execSQL(sql);
        onCreate(db);
    }

    /***
     * Insere um imóvel no banco local
     * @param imovel
     */
    public void insere(Imovel imovel) {
        SQLiteDatabase db = getWritableDatabase();
        //dados.put("nome", "aaaaaa");
        //System.out.println("LINHA = " + db.insert("Ocasioes", null, dados));

        ContentValues dados = pegaDadosDoImovel(imovel);

        System.out.println("LINHA = " + db.insert("Imoveis   ", null, dados));
    }

    /***
     * Altera dados de um imóvel no banco local
     * @param imovel
     */
      public void altera(Imovel imovel) {
          SQLiteDatabase db = getWritableDatabase();

          ContentValues dados = pegaDadosDoImovel(imovel);

          String[] params = {imovel.getNome()};
          db.update("Imoveis", dados, "nome = ?", params);
      }

    /***
     * Lista imóveis do banco local
     * @return
     */
    public List<Imovel> buscaImoveis() {
        String sql = "SELECT * FROM Imoveis";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Imovel> imoveis = new ArrayList<>();

        while(c.moveToNext()) {
            Imovel imovel = new Imovel();
            imovel.setValor(c.getInt(c.getColumnIndex("valor")));
            imovel.setNome(c.getString(c.getColumnIndex("nome")));
            imovel.setEndereco(c.getString(c.getColumnIndex("endereco")));
            imoveis.add(imovel);
        }
        c.close();

        return imoveis;
    }

    /***
     * Deleta imóvel do banco local
     * @param imovel
     */
    public void deleta(Imovel imovel) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {imovel.getNome()};
        db.delete("Imoveis", "nome = ?", params);

    }

    @NonNull
    /***
     * Retorna dados do imóvel em um objeto ContentValues
     */
    private ContentValues pegaDadosDoImovel(Imovel imovel) {
        ContentValues dados = new ContentValues();
        dados.put("nome", imovel.getNome());
        dados.put("endereco", imovel.getEndereco());
        dados.put("valor", imovel.getValor());
        return dados;
    }
}
