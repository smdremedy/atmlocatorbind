package com.soldiersofmobile.atmlocator

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.database.Observable
import android.databinding.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.soldiersofmobile.atmlocator.databinding.ActivityAddAtmBinding

class AddAtmActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddAtmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_atm)

        binding.model = AddAtmModel(ObservableDouble(2.0), ObservableDouble(3.5),
                ObservableField("asdfasdf"))
        binding.presenter = AddAtmPresenter(this, binding.model)

    }

    fun pickPlace() {
        val intent = PlacePicker.IntentBuilder().build(this)
        startActivityForResult(intent, AddAtmActivity.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val place = PlacePicker.getPlace(this, data)
            toast("Picked:$place")

        }
    }

    companion object {
        val REQUEST_CODE = 1
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
        activity.toast("Save clicked: $model")
    }

    fun reset(view: View) {
        with(model) {
            address.set("")
            lat.set(0.0)
            lng.set(0.0)
            note = "notefromcode"
        }
    }
}

data class AddAtmModel(var lat: ObservableDouble,
                       var lng: ObservableDouble,
                       var address: ObservableField<String>) : BaseObservable() {

    @Bindable
    var hasNote: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasNote)
            if (!value) {
                note = ""
            }
        }
    @Bindable
    var note: String = "note"
        set(value) {
            field = value
            notifyPropertyChanged(BR.note)
            if (value != "") {
                hasNote = true
            }
        }

}

