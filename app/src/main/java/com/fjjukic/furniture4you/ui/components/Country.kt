package com.fjjukic.furniture4you.ui.components

import java.util.UUID


sealed class MenuItem(val name: String) {
    class Country(
        name: String,
        val id: String = UUID.randomUUID().toString()
    ) : MenuItem(name)

    class City(
        name: String,
        val countryId: String,
        val id: String = UUID.randomUUID().toString(),
    ) : MenuItem(name)
}