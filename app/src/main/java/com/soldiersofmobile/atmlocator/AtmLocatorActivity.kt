package com.soldiersofmobile.atmlocator

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.soldiersofmobile.atmlocator.db.*

class AtmLocatorActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    lateinit var atmDao: AtmDao
    lateinit var bankDao: BankDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atm_locator)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        val dbHelper = DbHelper(this)
        atmDao = dbHelper.getDao(Atm::class.java)
        bankDao = dbHelper.getDao(Bank::class.java)
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.atm_locator, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_atm) {
            startActivity(Intent(this, AddAtmActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        val atms: MutableList<Atm> = atmDao.queryForAll()
        atms.forEach { atm ->

            //bankDao.refresh(atm.bank)
            val latLng = LatLng(atm.lat, atm.lng)
            mMap!!.addMarker(MarkerOptions().position(latLng)
                    .title(atm.bank.name))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))

        }

    }
}
