package com.jandas.codechallenge.model.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "listing_item")
data class ListingItemEntry(
    @PrimaryKey
    override val id: Int,

    @ColumnInfo(name = "name")
    override val name: String,

    @ColumnInfo(name = "price")
    override val price: Float,

    @ColumnInfo(name = "description")
    override val description: String?,

    @ColumnInfo(name = "imported")
    override val imported: Boolean?,

    @ColumnInfo(name = "salesTax")
    override val salesTax: Int = 0,

    override val quantity: Int = 0

) : Cart {
    @Ignore
    override var totalTax: Float = 0f


}