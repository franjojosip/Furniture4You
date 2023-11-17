package com.fjjukic.furniture4you.ui.mock

import com.fjjukic.furniture4you.ui.home.CategoryItemModel
import com.fjjukic.furniture4you.ui.model.Product
import ht.ferit.fjjukic.foodlovers.R
import java.util.UUID

object MockRepository {
    fun getCategories(): List<CategoryItemModel> {
        return listOf(
            CategoryItemModel(
                title = "Popular",
                imageResId = R.drawable.ic_star
            ),
            CategoryItemModel(
                title = "Chair",
                imageResId = R.drawable.ic_chair
            ),
            CategoryItemModel(
                title = "Table",
                imageResId = R.drawable.ic_table
            ),
            CategoryItemModel(
                title = "Armchair",
                imageResId = R.drawable.ic_sofa
            ),
            CategoryItemModel(
                title = "Bed",
                imageResId = R.drawable.ic_bed
            ),
            CategoryItemModel(
                title = "Lamp",
                imageResId = R.drawable.ic_lamp
            )
        )
    }

    fun getProducts(): List<Product> {
        return listOf(
            Product(
                UUID.randomUUID().toString(),
                "Black Simple Lamp",
                "12.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.black_simple_lamp
            ),
            Product(
                UUID.randomUUID().toString(),
                "Babylon Marble Table",
                "36.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.babylon_torrentno_marble_table
            ),
            Product(
                UUID.randomUUID().toString(),
                "Coffee Chair",
                "21.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.coffee_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Makali Lounge Chair",
                "16.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.makali_lounge_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Minimal Stand",
                "18.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.minimal_stand
            ),
            Product(
                UUID.randomUUID().toString(),
                "Office Chair",
                "34.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.office_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Orine Table Lamp",
                "17.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.orine_table_lamp
            ),
            Product(
                UUID.randomUUID().toString(),
                "The Day Bed",
                "15.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.the_day_bed
            ),
            Product(
                UUID.randomUUID().toString(),
                "Utility stool",
                "18.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.utility_stool
            ),
            Product(
                UUID.randomUUID().toString(),
                "Kitchen Island",
                "34.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.wide_rolling_kitchen_island
            ),

        )
    }
}