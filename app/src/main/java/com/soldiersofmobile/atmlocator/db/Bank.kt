package com.soldiersofmobile.atmlocator.db

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "bank", daoClass = BankDao::class)
class Bank() {

    @DatabaseField(columnName = "_id", generatedId = true)
    var id: Long = 0
    @DatabaseField(canBeNull = false, unique = true)
    var name: String = ""
    @DatabaseField(canBeNull = false)
    var phone: String = ""

    constructor(name: String, phone: String) : this() {
        this.name = name
        this.phone = phone
    }

    override fun toString() = name
}

class BankDao(connectionSource: ConnectionSource?, dataClass: Class<Bank>?)
    : BaseDaoImpl<Bank, Long>(connectionSource, dataClass) {

    fun getBanksByPhone(phone: String) = queryForEq("phone", phone)

}