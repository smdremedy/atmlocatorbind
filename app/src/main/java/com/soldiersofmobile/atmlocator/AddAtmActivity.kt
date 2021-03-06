package com.soldiersofmobile.atmlocator

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.database.Observable
import android.databinding.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.j256.ormlite.dao.Dao
import com.soldiersofmobile.atmlocator.databinding.ActivityAddAtmBinding
import com.soldiersofmobile.atmlocator.db.*

class AddAtmActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddAtmBinding
    lateinit var adapter: ArrayAdapter<Bank>
    lateinit var bankDao: BankDao
    lateinit var atmDao: AtmDao


    private val model = AddAtmModel(ObservableDouble(0.0), ObservableDouble(0.0),
            ObservableField(""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_atm)

        binding.model = model
        binding.presenter = AddAtmPresenter(this, model)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        binding.adapter = adapter

        val dbHelper = DbHelper(this)
        bankDao = dbHelper.getDao(Bank::class.java)
        atmDao = dbHelper.getDao(Atm::class.java)

        adapter.addAll(bankDao.queryForAll())

    }

    fun pickPlace() {
        val intent = PlacePicker.IntentBuilder().build(this)
        startActivityForResult(intent, AddAtmActivity.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val place = PlacePicker.getPlace(this, data)
            toast("Picked:$place")

            with(model) {
                lat.set(place.latLng.latitude)
                lng.set(place.latLng.longitude)
                address.set(place.address.toString())
                note = place.name.toString()
            }

        }
    }

    companion object {
        val REQUEST_CODE = 1
    }

    fun save() {
        val bank = binding.bankSpinner.selectedItem as Bank

        val atm: Atm = with(Atm()) {
            this.bank = bank
            address = model.address.get()
            lat = model.lat.get()
            lng = model.lng.get()
            note = model.note
            this
        }
        atmDao.create(atm)
        finish()


    }
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}

class AddAtmPresenter(val activity: AddAtmActivity, val model: AddAtmModel) {

    fun pick(view: View) {
        activity.pickPlace()
    }

    fun save(view: View) {
        //activity.toast("Save clicked: $model")
        activity.save()

    }

    fun reset(view: View) {
        with(model) {
            address.set("")
            lat.set(0.0)
            lng.set(0.0)
            hasNote = false
        }
    }
}

data class AddAtmModel(var lat: ObservableDouble,
                       var lng: ObservableDouble,
                       var address: ObservableField<String>) : BaseObservable() {

    @Bindable
    var hasNote: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasNote)
            if (!value) {
                note = ""
            }
        }
    @Bindable
    var note: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.note)
            if (value != "") {
                hasNote = true
            }
        }

}

