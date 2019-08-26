package com.jandas.codechallenge.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.jandas.codechallenge.model.cart.Cart

@Entity(tableName = "listing_item")
data class CartEntity(
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
    override val salesTax: Int?
) : Cart {
    @Ignore
    override val quantity: Int = 0
    @Ignore
    override var totalTax = 0f
}
