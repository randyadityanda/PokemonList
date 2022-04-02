package com.example.pokemonlist

import com.example.pokemonlist.Common.Common
import com.example.pokemonlist.Common.Common.KEY_ENABLE_HOME
import com.example.pokemonlist.Model.Pokemon
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    lateinit var toolbar : Toolbar
    //Create BroadCast handle
    private val showDetail = object:BroadcastReceiver(){

        override fun onReceive(p0: Context?, intent: Intent?) {
            if(intent?.action!!.toString() == Common.KEY_ENABLE_HOME)
            {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowCustomEnabled(true)

                //Replace Fragment

                val detailFragment : PokemonDetail = PokemonDetail.getInstance()
                val position = intent.getIntExtra("position", -1)
                val bundle = Bundle()
                bundle.putInt("position",position)
                detailFragment.arguments= bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.list_pokemon_fragment,detailFragment)
                fragmentTransaction.addToBackStack("detail")
                fragmentTransaction.commit()

                //Set Pokemon Name for Toolbar
                val Pokemon = Common.pokemonList[position]
                toolbar.title = Pokemon.name
            }
        }

    }

    private val showEvolution = object:BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            if(intent?.action!!.toString() == Common.KEY_NUM_EVOLUTION)
            {

                //Replace Fragment

                val detailFragment : PokemonDetail = PokemonDetail.getInstance()
                val bundle = Bundle()
                val num = intent.getStringExtra("num")
                bundle.putString("num", num)
                detailFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.remove(detailFragment) // Remove Current
                fragmentTransaction.replace(R.id.list_pokemon_fragment,detailFragment)
                fragmentTransaction.addToBackStack("detail")
                fragmentTransaction.commit()

                //Set Pokemon Name for Toolbar
                val Pokemon = Common.findPokemonByNum(num)
                toolbar.title = Pokemon!!.name
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)

        toolbar.setTitle("Pokemon List")
        setSupportActionBar(toolbar)

        //Register Broadcast
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showEvolution, IntentFilter(Common.KEY_NUM_EVOLUTION))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item!!.itemId)
        {
            android.R.id.home -> {
                toolbar.title = "POKEMON LIST"

                //Clear all Fragment in stack name 'detail'
                supportFragmentManager.popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                supportActionBar!!.setDisplayShowHomeEnabled(false)
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            }
        }

        return true
    }
}