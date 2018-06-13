package com.khpi.classschedule.data.models

data class BufferSearchModel (
        val gAbbr: String?,
        val gId: Int?,
        var gKurs: Int?,
        val fAbbr: String?,

        val pLast: String,
        val pName: String,
        val pMiddle: String,
        val pId: Int?,
        val kName: String?
)