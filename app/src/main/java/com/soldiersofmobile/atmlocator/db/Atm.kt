package com.soldiersofmobile.atmlocator.db

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "atm", daoClass = AtmDao::class)
class Atm {

    @DatabaseField(generatedId = true)
    var id: Long = 0
    @DatabaseField(canBeNull = false)
    var lat: Double = 0.0
    @DatabaseField(canBeNull = false)
    var lng: Double = 0.0
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    lateinit var bank: Bank
    @DatabaseField(canBeNull = false)
    var address: String = ""
    @DatabaseField
    var note: String = ""

}

class AtmDao(connectionSource: ConnectionSource?, dataClass: Class<Atm>?)
    : BaseDaoImpl<Atm, Long>(connectionSource, dataClass)