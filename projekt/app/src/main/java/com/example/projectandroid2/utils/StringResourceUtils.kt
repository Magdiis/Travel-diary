package com.example.projectandroid2.utils

import com.example.projectandroid2.R

fun returnStringResource(name: String): Int {
    return when(name){
        "Austria" -> {
            return R.string.Austria
        }
        "Belgium" -> {
            return R.string.Belgium
        }
        "Croatia" -> {
            return R.string.Croatia
        }
        "Czechia" -> {
            return R.string.Czechia
        }
        "Finland" -> {
            return R.string.Finland
        }
        "France" -> {
            return R.string.France
        }
        "Germany" -> {
            return R.string.Germany
        }
        "Greece" -> {
            return R.string.Greece
        }
        "Hungary" -> {
            return R.string.Hungary
        }
        "Iceland" -> {
            return R.string.Iceland
        }
        "Ireland" -> {
            return R.string.Ireland
        }
        "Italy" -> {
            return R.string.Italy
        }
        "Netherlands" -> {
            return R.string.Netherlands
        }
        "Norway" -> {
            return R.string.Norway
        }
        "Poland" -> {
            return R.string.Poland
        }
        "Portugal" -> {
            return R.string.Portugal
        }
        "Romania" -> {
            return R.string.Romania
        }
        "Russia" -> {
            return R.string.Russia
        }
        "Slovakia" -> {
            return R.string.Slovakia
        }
        "Spain" -> {
            return R.string.Spain
        }
        "Switzerland" -> {
            return R.string.Switzerland
        }
        "Ukraine" -> {
            return R.string.Ukraine
        }

        else -> {
            -500000
        }
    }
}