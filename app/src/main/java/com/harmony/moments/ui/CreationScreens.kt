package com.harmony.moments.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable fun SettingsScreen(vm:MomentsViewModel,nav:NavHostController){
    var analyse by remember{mutableStateOf(true)};var length by remember{mutableFloatStateOf(15f)};var privacy by remember{mutableStateOf(0)}
    Column(Modifier.fillMaxSize().background(Canvas)){TopBar("AI 创作设置",{nav.popBackStack()});LazyColumn(Modifier.weight(1f),contentPadding=PaddingValues(start=16.dp,end=16.dp,bottom=24.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
        item{Text("智能处理",color=HarmonyBlue,fontSize=11.sp,modifier=Modifier.padding(top=4.dp))}
        item{InfoCard{Row(verticalAlignment=Alignment.CenterVertically){Box(Modifier.size(38.dp).background(SoftBlue,CircleShape),contentAlignment=Alignment.Center){Icon(Icons.Outlined.AutoAwesome,null,tint=HarmonyBlue)};Column(Modifier.padding(horizontal=12.dp).weight(1f)){Text("自动分析素材",fontWeight=FontWeight.SemiBold);Text("导入后立即识别精彩片段",color=Muted,fontSize=11.sp)};Switch(analyse,{analyse=it})};HorizontalDivider(Modifier.padding(vertical=12.dp),color=Hairline);Row(verticalAlignment=Alignment.CenterVertically){Icon(Icons.Outlined.Timelapse,null,Modifier.size(20.dp));Text("视频长度偏值",Modifier.padding(start=12.dp).weight(1f));Text("${length.toInt()}秒",color=HarmonyBlue,fontSize=11.sp)};Slider(length,{length=it},valueRange=5f..60f);Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Text("5s",fontSize=9.sp,color=Muted);Text("60s",fontSize=9.sp,color=Muted)}}}
        item{InfoCard{Text("隐私授权模式",fontWeight=FontWeight.SemiBold);RadioLine("允许云端辅助（推荐，效果更佳）",privacy==0){privacy=0};RadioLine("仅本地处理（保护绝对隐私）",privacy==1){privacy=1}}}
        item{Text("常规偏好",color=Muted,fontSize=11.sp,modifier=Modifier.padding(top=4.dp))}
        item{InfoCard{SettingRow(Icons.Outlined.Notifications,"通知方式","横幅与震动");HorizontalDivider(color=Hairline);SettingRow(Icons.Outlined.Widgets,"桌面组件偏好","最近灵感");HorizontalDivider(color=Hairline);SettingRow(Icons.Outlined.History,"清除编辑记录","")}}
        item{Text("存储与缓存",color=Muted,fontSize=11.sp,modifier=Modifier.padding(top=4.dp))}
        item{InfoCard{SettingRow(Icons.Outlined.DeleteSweep,"清理缓存","1.2 GB")}}
    };BlueButton("保存并开始创作",{vm.processing=.02f;nav.go(Routes.PROCESSING)},Modifier.padding(16.dp).fillMaxWidth().navigationBarsPadding())}
}

@Composable private fun RadioLine(text:String,selected:Boolean,onClick:()->Unit){Row(Modifier.fillMaxWidth().clickable{onClick()}.padding(top=12.dp),verticalAlignment=Alignment.CenterVertically){RadioButton(selected,onClick);Text(text,fontSize=12.sp)}}

@Composable fun ProcessingScreen(vm:MomentsViewModel,nav:NavHostController){
    var cancel by remember{mutableStateOf(false)}
    LaunchedEffect(Unit){while(vm.processing<1f){delay(220);vm.processing=(vm.processing+.025f).coerceAtMost(1f)};delay(450);nav.go(Routes.EDITOR)}
    val steps=listOf("整理素材","识别人和地点场景","筛选高光","编排故事","生成预览")
    Box(Modifier.fillMaxSize().background(androidx.compose.ui.graphics.Brush.verticalGradient(listOf(Color(0xFFF3F6FF),Color.White)))){
        Column(Modifier.fillMaxSize(),horizontalAlignment=Alignment.CenterHorizontally){Spacer(Modifier.height(120.dp));Box(Modifier.size(104.dp).background(Color(0xFFD8E6FF),CircleShape),contentAlignment=Alignment.Center){Icon(Icons.Outlined.AutoAwesome,null,tint=HarmonyBlue,modifier=Modifier.size(42.dp))};Spacer(Modifier.weight(1f));InfoCard(Modifier.padding(16.dp).fillMaxWidth()){Text("AI 分析中...",Modifier.fillMaxWidth(),textAlign=TextAlign.Center,fontSize=19.sp,fontWeight=FontWeight.SemiBold);Spacer(Modifier.height(8.dp));steps.forEachIndexed{i,s->val current=(vm.processing*5).toInt().coerceAtMost(4);Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(if(i==current)SoftBlue else Color.Transparent).padding(9.dp),verticalAlignment=Alignment.CenterVertically){Icon(if(i<current)Icons.Outlined.CheckCircle else if(i==current)Icons.Outlined.Radar else Icons.Outlined.Circle,null,tint=if(i<=current)HarmonyBlue else Hairline,modifier=Modifier.size(20.dp));Text(s,Modifier.padding(start=10.dp).weight(1f),fontSize=12.sp,fontWeight=if(i==current)FontWeight.Bold else FontWeight.Normal);if(i==current)Text("${(vm.processing*100).toInt()}%",color=HarmonyBlue,fontSize=11.sp)}};LinearProgressIndicator({vm.processing},Modifier.fillMaxWidth().padding(top=10.dp).height(3.dp),color=HarmonyBlue)};OutlinePill("—  后台运行",{nav.go(Routes.TASKS)},Modifier.padding(horizontal=16.dp).fillMaxWidth());TextButton({cancel=true}){Text("取消任务",color=Muted,fontSize=11.sp)};Spacer(Modifier.navigationBarsPadding())}
        if(cancel)AlertDialog({cancel=false},title={Text("取消当前任务？")},text={Text("已完成的分析进度将不会保留。")},confirmButton={TextButton({cancel=false;nav.popBackStack(Routes.HOME,false)}){Text("确认取消")}},dismissButton={TextButton({cancel=false}){Text("继续生成")}})
    }
}

@Composable fun PreferenceScreen(vm:MomentsViewModel,nav:NavHostController){
    var rhythm by remember{mutableStateOf("叙事感")};var style by remember{mutableStateOf("电影感")};var mood by remember{mutableStateOf("舒缓")}
    Column(Modifier.fillMaxSize().background(Canvas)){TopBar("创作偏好",{nav.popBackStack()});LazyColumn(Modifier.weight(1f),contentPadding=PaddingValues(16.dp),verticalArrangement=Arrangement.spacedBy(18.dp)){
        item{Column(horizontalAlignment=Alignment.CenterHorizontally,modifier=Modifier.fillMaxWidth()){Box(Modifier.size(66.dp).background(androidx.compose.ui.graphics.Brush.radialGradient(listOf(Color.White,AiViolet.copy(.18f))),CircleShape),contentAlignment=Alignment.Center){Icon(Icons.Outlined.AutoAwesome,null,tint=AiViolet)};Text("定制您的 AI 创作偏好",fontSize=22.sp,fontWeight=FontWeight.SemiBold);Text("告诉我们您的喜好，AI 将自动把屏幕的风格进行剪辑。",color=Muted,fontSize=11.sp,textAlign=TextAlign.Center)}}
        item{ChoiceSection("视频节奏",listOf("叙事感","平衡","轻缓"),rhythm){rhythm=it}}
        item{ChoiceSection("视觉风格",listOf("电影感","简约","复古"),style){style=it}}
        item{ChoiceSection("默认配乐情绪",listOf("欢快","舒缓","史诗","轻声","电子","轻摇滚"),mood){mood=it}}
    };BlueButton("◎  保存偏好设置",{vm.preferenceDone=true;nav.go(if(vm.permissionGranted)Routes.PICKER else Routes.HOME)},Modifier.padding(16.dp).fillMaxWidth().navigationBarsPadding())}
}

@Composable private fun ChoiceSection(title:String,choices:List<String>,selected:String,onSelect:(String)->Unit){Column{Text(title,fontWeight=FontWeight.SemiBold);Row(Modifier.fillMaxWidth().padding(top=10.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){choices.take(3).forEach{choice->Card(Modifier.weight(1f).clickable{onSelect(choice)}.then(if(choice==selected)Modifier.border(2.dp,AiViolet,RoundedCornerShape(20.dp))else Modifier),shape=RoundedCornerShape(20.dp),colors=CardDefaults.cardColors(Color.White)){Box(Modifier.fillMaxWidth().height(68.dp),contentAlignment=Alignment.Center){Text(choice,fontSize=12.sp)}}}};if(choices.size>3)Row(Modifier.padding(top=8.dp),horizontalArrangement=Arrangement.spacedBy(6.dp)){choices.forEach{SelectPill(it,it==selected){onSelect(it)}}}}}
