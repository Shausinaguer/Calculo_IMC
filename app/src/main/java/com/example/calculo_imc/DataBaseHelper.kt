package com.example.calculo_imc

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Cria o banco de dados app.db e a tabela denominada pessoa vei receber
//os dados da pessoa

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

        companion object {
            private const val DATABASE_NAME = "app.db"
            private const val DATABASE_VERSION =  2
            private const val TABLE_NAME = "pessoa"
            private const val COLUMN_CPF = "cpf"
            private const val COLUMN_NAME = "nome"
            private const val COLUMN_AGE = "idade"
            private const val COLUMN_HEIGHT = "altura"
            private const val COLUMN_WEIGHT = "peso"
        }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQueryPessoa = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "id INTEGER primary key AUTOINCREMENT," +
                "$COLUMN_CPF VARCHAR," +
                "$COLUMN_NAME VARCHAR," +
                "$COLUMN_AGE INT(3)," +
                "$COLUMN_HEIGHT REAL," +
                "$COLUMN_WEIGHT REAL)"
        db.execSQL(createTableQueryPessoa)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertPessoa(cpf: String, nome: String, idade: Int, altura: Double, peso: Double) {
        val db = writableDatabase
        val insertQuery = "INSERT INTO $TABLE_NAME($COLUMN_CPF, $COLUMN_NAME, $COLUMN_AGE, $COLUMN_HEIGHT, $COLUMN_WEIGHT) VALUES ('$cpf', '$nome', '$idade', '$altura', '$peso')"
        db.execSQL(insertQuery)
        db.close()
    }

//    fun getAllPessoas(): ArrayList<Pessoa> {
//        val pessoasList = ArrayList<Pessoa>()
//        val db = readableDatabase
//        val selectQuery = "SELECT $COLUMN_CPF, $COLUMN_NAME, $COLUMN_AGE, $COLUMN_HEIGHT, $COLUMN_WEIGHT FROM $TABLE_NAME ORDER BY id DESC LIMIT 2"
//        val cursor = db.rawQuery(selectQuery, null)
//
//        if (cursor.moveToFirst()) {
//            do {
//                val cpf = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CPF))
//                val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
//                val idade = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
//                val altura = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT))
//                val peso = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT))
//                val pessoa = Pessoa(cpf, nome, idade, altura, peso)
//                pessoasList.add(pessoa)
//            } while (cursor.moveToNext())
//        }

    fun getUltimasPessoas(): List<Pessoa> {
        val pessoasList = mutableListOf<Pessoa>()
        val db = readableDatabase
        val selectQuery = "SELECT $COLUMN_CPF, $COLUMN_NAME, $COLUMN_AGE, $COLUMN_HEIGHT, $COLUMN_WEIGHT FROM $TABLE_NAME ORDER BY id DESC LIMIT 2"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val cpf = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CPF))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val idade = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
                val altura = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT))
                val peso = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT))
                val pessoa = Pessoa(cpf, nome, idade, altura, peso)
                pessoasList.add(pessoa)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return pessoasList
    }
}