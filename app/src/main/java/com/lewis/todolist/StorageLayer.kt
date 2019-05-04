package com.lewis.todolist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import java.util.*


class ListDataHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "ListDatabase", null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("List", true,
            "id" to INTEGER,
            "title" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    init {
      var instance = this
  }
    companion object {
        private var instance: ListDataHelper? = null
        @Synchronized
        fun getInstance(ctx: Context) = instance?: ListDataHelper(ctx.applicationContext)
    }

}

class ListRowPraser: RowParser<list>{
    override fun parseRow(columns: Array<Any?>): list {
        return list(columns[0] as String)
    }
}

val Context.database: ListDataHelper
    get() = ListDataHelper.getInstance(this)

class list (main: String) {
    var title = main
    var havedone = false
    lateinit var endtime: Date
    lateinit var subtitle: MutableMap<String, Boolean>

    fun addSubtitle (key: String, value: Boolean){
        subtitle.put(key,value)
    }

    fun removeSubtitle (key: String, value: Boolean){
        subtitle.remove(key)
    }

    fun addEndtime (time: Date){
        endtime = time
    }

    fun removeEndtime (){
        endtime = Date(0)
    }
}