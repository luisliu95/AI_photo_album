package com.harmony.moments.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.harmony.moments.R

data class MediaItem(val id:Int,val res:Int,val day:String,val video:Boolean=false,val duration:String="",val category:String="全部")
data class CaptionLine(val time:String,var text:String)

val mediaItems=listOf(
    MediaItem(1,R.drawable.stitch_media_02,"Today",true,"0:15","夏日旅行"), MediaItem(2,R.drawable.stitch_media_03,"Today",false,category="夏日旅行"),
    MediaItem(3,R.drawable.stitch_media_04,"Today",true,"1:04","婚礼幸福"), MediaItem(4,R.drawable.stitch_media_06,"Today",false,category="夏日旅行"),
    MediaItem(5,R.drawable.stitch_media_05,"Today",false,category="极限运动"), MediaItem(6,R.drawable.stitch_media_07,"Today",true,"0:30","极限运动"),
    MediaItem(7,R.drawable.stitch_media_08,"Yesterday",false,category="婚礼幸福"), MediaItem(8,R.drawable.stitch_media_09,"Yesterday",false,category="婚礼幸福"),
    MediaItem(9,R.drawable.stitch_media_01,"Yesterday",true,"0:38","夏日旅行"),
)

class MomentsViewModel:ViewModel(){
    val selected=mutableStateListOf(1,2,3)
    var permissionGranted by mutableStateOf(false)
    var preferenceDone by mutableStateOf(false)
    var processing by mutableFloatStateOf(.64f)
    var mediaFilter by mutableStateOf("All Media")
    var liveFilter by mutableStateOf("全部 (42)")
    var editorVersion by mutableIntStateOf(3)
    var taskProgress by mutableIntStateOf(67)
    var chosenMusic by mutableStateOf("午夜都市节奏")
    var assistantExpanded by mutableStateOf(false)
    var onboardingStep by mutableIntStateOf(0)
    var highlightAssistantEnabled by mutableStateOf(false)
    var showNextDayReminder by mutableStateOf(false)
    var cloudAssist by mutableStateOf(true)
    var autoAtNight by mutableStateOf(true)
    var onlyWhenCharging by mutableStateOf(true)
    var reviewRange by mutableStateOf("最近 3 天")
    var reminderTime by mutableStateOf("12:00")
    var videoLengthPref by mutableIntStateOf(15)
    var processingNavigated by mutableStateOf(false)
    val creationDirections=mutableStateListOf<String>()
    val captions=mutableStateListOf(
        CaptionLine("00:00","欢迎来到我的山野旅行，今天我们将深入探索这片宁静的森林。"),
        CaptionLine("00:12","当金色阳光穿过树梢，我们遇见了旅途中最动人的风景。"),
        CaptionLine("00:24","记录每一个真实瞬间，让回忆在画面里继续流动。"),
    )
    fun toggle(id:Int){if(id in selected)selected.remove(id) else selected.add(id)}
}
