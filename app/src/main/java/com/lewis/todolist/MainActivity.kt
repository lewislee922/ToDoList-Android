package com.lewis.todolist

import android.content.ContentValues
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        addnewitem.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        setupAdapter(getAllList())
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        addnewitem.setOnClickListener {view ->
            alert("","新增事項"){
                var ed =" "
                   customView {
                       editText{
                           doOnTextChanged { text, start, count, after ->
                               ed = text.toString()
                           }
                       }
                   }
                positiveButton("確定"){
                    if (ed.isNullOrBlank()){
                        toast("文字不得為空白!!")
                    }
                    else{
                        addNewList(ed, LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE).toInt())
                        setupAdapter(getAllList())
                    }
                }
                negativeButton("取消"){}
            }.show()
        }
    }
    private fun setupAdapter(todo: MutableList<list>) {
        todolistView.adapter = RecyclerLinearAdapter(this, todo)
        todolistView.layoutManager = LinearLayoutManager(this)
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    fun addNewList(main: String, id: Int) {
        database.use {
            insert("List",
                "id" to id,
                "title" to main
            )
        }
    }

    fun getAllList(): MutableList<list> {
        val list = mutableListOf<list>()
        database.use {
            select("List").exec {
                    this.moveToFirst()
                    while(this.moveToNext()){
                        val name = this.getString(this.getColumnIndex("title"))
                        val l = com.lewis.todolist.list(name)
                        list.add(l)
                    }
            }
        }
        return list
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun selected() {
        database.use {
            val value = ContentValues()
            value.put("id", 5)
            value.put("cc",22)
            insert("list",null,value)
        }
    }
}
