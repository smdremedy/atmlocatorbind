package com.soldiersofmobile.atmlocator

import android.app.Activity
import android.app.Fragment
import android.database.Observable
import android.databinding.DataBindingUtil
import android.databinding.ObservableDouble
import android.databinding.ObservableField
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

class AddAtmPresenter(val activity: AddAtmActivity, val model: AddAtmModel) {
    fun save(view: View) {
        activity.toast("Save clicked: $model")
    }

    fun reset(view: View) {
        with(model) {
            address.set("")
            lat.set(0.0)
            lng.set(0.0)
        }
    }
}

data class AddAtmModel(var lat: ObservableDouble, var lng: ObservableDouble, var address: ObservableField<String>) {
//    val latAsString: String
//        get() = lat.get().toString()
//    val lngAsString: String
//        get() = lng.get().toString()
}

