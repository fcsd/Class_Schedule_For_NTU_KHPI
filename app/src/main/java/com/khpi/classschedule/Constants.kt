package com.khpi.classschedule


object Constants {

    //Network
    val HOST = "http://schedule.kpi.kharkov.ua/json/"
    val CONNECT_TIMEOUT = 35L
    val WRITE_TIMEOUT = 35L
    val READ_TIMEOUT = 80L

    val GROUP_PREFIX = "group"
    val TEACHER_PREFIX = "teacher"
    val AUDITORY_PREFIX = "auditory"

    val REQUEST_OPEN_TASK_INFO = "open_task_info"

    val INVERT = "invert"
    val EVERYDAY_UPDATE = "everyday_update"
    val VIBRATE = "vibrate"
    val SOUND = "sound"
}