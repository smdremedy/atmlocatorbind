package com.soldiersofmobile.atmlocator.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class DbHelper(context: Context) : OrmLiteSqliteOpenHelper(context,
        "atms.db",
        null,
        2) {

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTable(connectionSource, Bank::class.java)
        TableUtils.createTable(connectionSource, Atm::class.java)
        val sql = TableUtils.getCreateTableStatements<Bank, Long>(connectionSource, Bank::class.java)
        Log.d("TAG", "SQL:$sql")

        val dao = getDao(Bank::class.java)
        dao.create(Bank("ING", "500 600 700"))
        dao.create(Bank("Pekao SA", "503 600 700"))
        dao.create(Bank("PKO BP", "502 600 700"))
        dao.create(Bank("Milenium", "501 600 700"))
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        Log.d("TAG", "Upgrade old:$oldVersion new:$newVersion")
        //drop
        TableUtils.dropTable<Bank, Long>(connectionSource, Bank::class.java, true)
        TableUtils.dropTable<Atm, Long>(connectionSource, Atm::class.java, true)
        onCreate(database, connectionSource)
    }


}