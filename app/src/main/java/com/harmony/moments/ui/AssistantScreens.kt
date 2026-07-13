package com.harmony.moments.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.harmony.moments.R

@Composable fun HighlightAssistantOverlay(vm:MomentsViewModel,modifier:Modifier=Modifier,onLearnMore:()->Unit){
    Row(modifier.padding(end=4.dp),verticalAlignment=Alignment.CenterVertically){
        if(vm.assistantExpanded)Card(Modifier.width(300.dp),shape=RoundedCornerShape(24.dp),colors=CardDefaults.cardColors(Color.White),elevation=CardDefaults.cardElevation(14.dp)){
            Column(Modifier.padding(16.dp)){Row(verticalAlignment=Alignment.CenterVertically){AssistantOrb(34.dp);Text("我是你的 AI 高光助手",Modifier.padding(start=9.dp).weight(1f),fontWeight=FontWeight.Bold);IconButton({vm.assistantExpanded=false},Modifier.size(28.dp)){Icon(Icons.Outlined.Close,"关闭",Modifier.size(17.dp))}};Text("我可以在你空闲的时候，帮你从相册里整理精彩瞬间，自动生成 Vlog、旅行回忆和高光动态照片。",Modifier.padding(vertical=10.dp),fontSize=12.sp,lineHeight=18.sp,color=Muted);Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){BlueButton("了解一下",onLearnMore,Modifier.weight(1f));OutlinePill("以后再说",{vm.assistantExpanded=false},Modifier.weight(1f))}}
        }
        Box(Modifier.offset(x=8.dp).clickable{vm.assistantExpanded=!vm.assistantExpanded}){AssistantOrb(if(vm.assistantExpanded)40.dp else 52.dp)}
    }
}

@Composable private fun AssistantOrb(size:androidx.compose.ui.unit.Dp){
    Box(Modifier.size(size).background(AiGradient,CircleShape).border(3.dp,Color.White,CircleShape),contentAlignment=Alignment.Center){Icon(Icons.Outlined.AutoAwesome,"AI 高光助手",tint=Color.White,modifier=Modifier.size(size*.48f))}
}

@Composable fun NextDayReminderCard(onClick:()->Unit){
    Card(Modifier.padding(horizontal=16.dp,vertical=8.dp).fillMaxWidth().clickable{onClick()},shape=RoundedCornerShape(20.dp),colors=CardDefaults.cardColors(Color.White),elevation=CardDefaults.cardElevation(6.dp)){
        Row(Modifier.padding(12.dp),verticalAlignment=Alignment.CenterVertically){Box(Modifier.size(42.dp).background(SoftBlue,RoundedCornerShape(12.dp)),contentAlignment=Alignment.Center){Icon(Icons.Outlined.Movie,null,tint=HarmonyBlue)};Column(Modifier.padding(horizontal=10.dp).weight(1f)){Text("AI 高光已生成",fontWeight=FontWeight.Bold,fontSize=13.sp);Text("你的周末旅行回忆已准备好，点击查看",color=Muted,fontSize=10.sp)};Icon(Icons.Outlined.ArrowForward,null,tint=HarmonyBlue)}
    }
}

@Composable fun AssistantOnboardingScreen(vm:MomentsViewModel,nav:NavHostController){
    val step=vm.onboardingStep.coerceIn(0,5)
    Column(Modifier.fillMaxSize().background(Canvas)){
        Row(Modifier.fillMaxWidth().statusBarsPadding().height(54.dp).padding(horizontal=8.dp),verticalAlignment=Alignment.CenterVertically){IconButton({if(step==0)nav.popBackStack()else vm.onboardingStep--}){Icon(Icons.Outlined.ArrowBack,"返回")};LinearProgressIndicator({(step+1)/6f},Modifier.weight(1f).height(3.dp),color=HarmonyBlue,trackColor=Hairline);TextButton({nav.popBackStack()}){Text("稍后",fontSize=11.sp)}}
        AnimatedContent(step,Modifier.weight(1f),label="assistantOnboarding"){
            when(it){0->ValueIntro();1->CaseShowcase();2->PrivacyIntro(vm);3->DirectionPicker(vm);4->AutomationSetup(vm);else->OnboardingDone(vm)}
        }
        if(step<5)BlueButton(if(step==0)"看看它能做什么" else if(step==4)"启用 AI 高光助手" else "继续",{vm.onboardingStep++},Modifier.padding(16.dp).fillMaxWidth().navigationBarsPadding())
        else BlueButton("进入 AI 高光助手",{vm.highlightAssistantEnabled=true;vm.showNextDayReminder=true;vm.assistantExpanded=false;nav.navigate(Routes.STUDIO){popUpTo(Routes.ONBOARDING){inclusive=true}}},Modifier.padding(16.dp).fillMaxWidth().navigationBarsPadding())
    }
}

@Composable private fun ValueIntro(){
    Column(Modifier.fillMaxSize().padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){Spacer(Modifier.height(20.dp));PhotoStack();Spacer(Modifier.weight(1f));Text("让相册里的高光\n自己浮现",textAlign=TextAlign.Center,fontSize=28.sp,lineHeight=34.sp,fontWeight=FontWeight.Bold);Text("AI 会在你空闲时发现珍贵瞬间，自动整理成 Vlog、旅行回忆和高光动态照片。",Modifier.padding(20.dp),textAlign=TextAlign.Center,color=Muted,fontSize=12.sp);Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){listOf("自动整理","后台生成","隐私优先").forEach{Surface(shape=CircleShape,color=SoftBlue){Text(it,Modifier.padding(10.dp,5.dp),color=HarmonyBlue,fontSize=10.sp)}}}}
}

@Composable private fun PhotoStack(){Box(Modifier.fillMaxWidth().height(310.dp),contentAlignment=Alignment.Center){MediaImage(R.drawable.stitch_onboarding_13,Modifier.width(250.dp).height(180.dp).offset(y=34.dp).clip(RoundedCornerShape(24.dp)));MediaImage(R.drawable.stitch_onboarding_12,Modifier.width(275.dp).height(190.dp).offset(y=(-15).dp).clip(RoundedCornerShape(24.dp)));Box(Modifier.size(66.dp).background(Color.White.copy(.92f),CircleShape),contentAlignment=Alignment.Center){Icon(Icons.Outlined.AutoAwesome,null,tint=HarmonyBlue,modifier=Modifier.size(30.dp))}}}

@Composable private fun CaseShowcase(){
    var category by remember{mutableStateOf("旅行 Vlog")}
    val showcase=listOf("旅行 Vlog" to R.drawable.stitch_onboarding_23,"日常高光" to R.drawable.stitch_onboarding_21,"运动集锦" to R.drawable.stitch_onboarding_22)
    val current=showcase.first{it.first==category}
    LazyColumn(Modifier.fillMaxSize(),contentPadding=PaddingValues(20.dp),horizontalAlignment=Alignment.CenterHorizontally){item{Text("一组普通素材，\n可以变成什么？",textAlign=TextAlign.Center,fontSize=26.sp,lineHeight=32.sp,fontWeight=FontWeight.Bold);Text("从原始片段到完整故事，AI 自动完成挑选、编排与卡点。",Modifier.padding(12.dp),textAlign=TextAlign.Center,color=Muted,fontSize=12.sp)};item{InfoCard(Modifier.fillMaxWidth()){Box(Modifier.fillMaxWidth().height(220.dp)){MediaImage(current.second,Modifier.fillMaxSize());Icon(Icons.Outlined.PlayCircle,null,Modifier.align(Alignment.Center).size(58.dp),tint=Color.White)};Text(when(category){"日常高光"->"日常碎片集锦";"运动集锦"->"运动高光瞬间";else->"周末海边 Vlog"},Modifier.padding(top=12.dp),fontWeight=FontWeight.Bold);Text(when(category){"日常高光"->"18 段素材 → 35 秒日常短片";"运动集锦"->"12 段素材 → 28 秒运动集锦";else->"28 段素材 → 42 秒高光视频"},color=Muted,fontSize=10.sp)}};item{Row(Modifier.padding(top=14.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){showcase.forEach{(label,_)->SelectPill(label,category==label){category=label}}}}}
}

@Composable private fun PrivacyIntro(vm:MomentsViewModel){
    LazyColumn(Modifier.fillMaxSize(),contentPadding=PaddingValues(20.dp),horizontalAlignment=Alignment.CenterHorizontally){item{Box(Modifier.size(66.dp).background(SoftBlue,CircleShape),contentAlignment=Alignment.Center){Icon(Icons.Outlined.Lock,null,tint=HarmonyBlue,modifier=Modifier.size(30.dp))};Text("隐私与信任",Modifier.padding(top=12.dp),fontSize=25.sp,fontWeight=FontWeight.Bold);Text("你的回忆属于你。选择视频的处理方式。",Modifier.padding(bottom=16.dp),color=Muted,fontSize=12.sp)};item{PrivacyCard(Icons.Outlined.PhoneAndroid,"优先本地处理","只要设备能力允许，AI 会直接在本机完成，保障隐私与速度。",false)};item{Spacer(Modifier.height(10.dp));PrivacyCard(Icons.Outlined.Cloud,"云端辅助需要授权","复杂渲染可使用安全云处理，并且随时可以关闭。",true)};item{Text("处理偏好",Modifier.fillMaxWidth().padding(top=20.dp),color=Muted,fontSize=10.sp);ChoiceRadio("允许云端辅助（推荐）",vm.cloudAssist){vm.cloudAssist=true};ChoiceRadio("仅本地处理",!vm.cloudAssist){vm.cloudAssist=false};Text("稍后可在设置中随时修改。",Modifier.padding(10.dp),color=Muted,fontSize=9.sp)}}
}

@Composable private fun PrivacyCard(icon:androidx.compose.ui.graphics.vector.ImageVector,title:String,body:String,accent:Boolean){InfoCard(Modifier.fillMaxWidth().then(if(accent)Modifier.border(1.dp,HarmonyBlue.copy(.3f),RoundedCornerShape(CardRadius))else Modifier)){Row(verticalAlignment=Alignment.Top){Icon(icon,null,tint=if(accent)HarmonyBlue else Ink);Column(Modifier.padding(start=12.dp)){Text(title,fontWeight=FontWeight.Bold);Text(body,Modifier.padding(top=5.dp),color=Muted,fontSize=11.sp)}}}}
@Composable private fun ChoiceRadio(text:String,selected:Boolean,onClick:()->Unit){Row(Modifier.padding(top=8.dp).fillMaxWidth().background(Color.White,RoundedCornerShape(14.dp)).border(1.dp,if(selected)HarmonyBlue else Hairline,RoundedCornerShape(14.dp)).clickable{onClick()}.padding(10.dp),verticalAlignment=Alignment.CenterVertically){RadioButton(selected,onClick);Text(text,fontSize=12.sp)}}

@Composable private fun DirectionPicker(vm:MomentsViewModel){
    val directions=listOf("旅行 Vlog","日常回忆","电影短片","运动集锦","高光动态照片")
    val images=listOf(R.drawable.stitch_onboarding_10,R.drawable.stitch_onboarding_11,R.drawable.stitch_onboarding_14,R.drawable.stitch_onboarding_15,R.drawable.stitch_onboarding_16)
    Column(Modifier.fillMaxSize().padding(20.dp)){Text("你通常喜欢创作什么？",fontSize=25.sp,fontWeight=FontWeight.Bold);Text("选择喜欢的方向，AI 会为你定制推荐。",Modifier.padding(top=6.dp,bottom=20.dp),color=Muted,fontSize=12.sp);directions.chunked(2).forEachIndexed{rowIdx,row->Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp)){row.forEachIndexed{i,label->val selected=label in vm.creationDirections;val imgIdx=rowIdx*2+i;Card(Modifier.weight(1f).padding(bottom=10.dp).clickable{if(selected)vm.creationDirections.remove(label)else vm.creationDirections.add(label)}.then(if(selected)Modifier.border(2.dp,HarmonyBlue,RoundedCornerShape(22.dp))else Modifier),shape=RoundedCornerShape(22.dp)){Box(Modifier.fillMaxWidth().height(118.dp)){MediaImage(images[imgIdx],Modifier.fillMaxSize());Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent,Color.Black.copy(.6f)))));Text(label,Modifier.align(Alignment.BottomStart).padding(12.dp),Color.White,fontWeight=FontWeight.Bold);if(selected)Icon(Icons.Outlined.CheckCircle,null,Modifier.align(Alignment.TopEnd).padding(8.dp),tint=Color.White)}}};if(row.size==1)Spacer(Modifier.weight(1f))}};Text("可以多选，之后仍可在设置中修改。",Modifier.padding(top=8.dp),color=Muted,fontSize=10.sp)}
}

@Composable private fun AutomationSetup(vm:MomentsViewModel){
    val parts=vm.reminderTime.split(":");var hour by remember{mutableIntStateOf(parts.getOrNull(0)?.toIntOrNull()?.coerceIn(6,22)?:12)};var minute by remember{mutableIntStateOf(if(parts.getOrNull(1)=="30")30 else 0)}
    LaunchedEffect(hour,minute){vm.reminderTime=String.format("%02d:%02d",hour,minute)}
    LazyColumn(Modifier.fillMaxSize(),contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
        item{Text("自动生成与提醒",fontSize=25.sp,fontWeight=FontWeight.Bold);Text("设置偏好，让 AI 高光助手在后台安静工作。",color=Muted,fontSize=12.sp)}
        item{InfoCard{Text("后台处理",fontWeight=FontWeight.Bold);ToggleRow("夜间处理","在睡眠时优化电量并运行",vm.autoAtNight){vm.autoAtNight=it};ToggleRow("充电时处理","仅在连接电源时运行密集任务",vm.onlyWhenCharging){vm.onlyWhenCharging=it}}}
        item{InfoCard{Text("回顾范围",fontWeight=FontWeight.Bold);Text("AI 应该向前查看多久的素材？",color=Muted,fontSize=10.sp);Row(Modifier.padding(top=10.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){listOf("最近 3 天","最近 7 天").forEach{range->SelectPill(range,vm.reviewRange==range){vm.reviewRange=range}}}}}
        item{InfoCard{Text("提醒时间",fontWeight=FontWeight.Bold);Text("滑动选择你希望查看新高光的时间",color=Muted,fontSize=10.sp);Text(String.format("%02d : %02d",hour,minute),Modifier.fillMaxWidth().padding(top=14.dp),textAlign=TextAlign.Center,fontSize=32.sp,fontWeight=FontWeight.Bold,color=HarmonyBlue);Slider(hour.toFloat(),{hour=it.toInt()},valueRange=6f..22f,steps=16,modifier=Modifier.padding(top=8.dp));Text("小时",color=Muted,fontSize=10.sp,modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center);Slider(if(minute>=30)1f else 0f,{minute=if(it>=.5f)30 else 0},modifier=Modifier.padding(top=4.dp));Text("分钟",color=Muted,fontSize=10.sp,modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center);ToggleRow("高光完成后通知我","发送系统通知提醒",true){}}}
    }
}
@Composable private fun ToggleRow(title:String,sub:String,checked:Boolean,onChange:(Boolean)->Unit){Row(Modifier.fillMaxWidth().padding(top=12.dp),verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text(title,fontSize=12.sp,fontWeight=FontWeight.Medium);Text(sub,color=Muted,fontSize=9.sp)};Switch(checked,onChange)}}

@Composable private fun OnboardingDone(vm:MomentsViewModel){Column(Modifier.fillMaxSize().padding(24.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){Box(Modifier.size(120.dp).background(SoftBlue,CircleShape),contentAlignment=Alignment.Center){AssistantOrb(76.dp)};Text("AI 高光助手已开启",Modifier.padding(top=28.dp),fontSize=27.sp,fontWeight=FontWeight.Bold);Text("接下来它会在合适的时间整理相册。新的高光准备好后，你会收到提醒。",Modifier.padding(16.dp),textAlign=TextAlign.Center,color=Muted,fontSize=12.sp);Surface(shape=RoundedCornerShape(20.dp),color=Color.White){Row(Modifier.padding(16.dp),verticalAlignment=Alignment.CenterVertically){Icon(Icons.Outlined.Schedule,null,tint=HarmonyBlue);Text("首次回顾预计在明天 ${vm.reminderTime}",Modifier.padding(start=10.dp),fontSize=12.sp)}}}}

@Composable fun HighlightAggregationScreen(vm:MomentsViewModel,nav:NavHostController){
    LazyColumn(Modifier.fillMaxSize().background(Canvas),contentPadding=PaddingValues(bottom=30.dp)){
        item{TopBar("AI 高光",{nav.popBackStack()})}
        item{Column(Modifier.padding(horizontal=16.dp)){Text("AI 高光已完成",fontSize=25.sp,fontWeight=FontWeight.Bold);Text("你的周末旅行回忆已准备好分享。",color=Muted,fontSize=12.sp)}}
        item{InfoCard(Modifier.padding(16.dp).fillMaxWidth()){
            Text("已生成 Vlog",color=HarmonyBlue,fontSize=11.sp,fontWeight=FontWeight.Bold)
            Box(Modifier.padding(top=10.dp).fillMaxWidth().height(220.dp)){MediaImage(R.drawable.stitch_onboarding_23,Modifier.fillMaxSize());Icon(Icons.Outlined.PlayCircle,null,Modifier.align(Alignment.Center).size(56.dp),tint=Color.White)}
            Row(Modifier.padding(top=10.dp),verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text("周末旅行 Vlog",fontWeight=FontWeight.Bold);Text("1080P · 00:42",color=Muted,fontSize=10.sp)};IconButton({nav.go(Routes.EDITOR)}){Icon(Icons.Outlined.Edit,null)};IconButton({nav.go(Routes.RESULT)},Modifier.background(HarmonyBlue,CircleShape)){Icon(Icons.Outlined.Download,null,tint=Color.White)}}
        }}
        item{Row(Modifier.padding(horizontal=16.dp),verticalAlignment=Alignment.CenterVertically){Text("高光动态照片",Modifier.weight(1f),fontWeight=FontWeight.Bold);TextButton({nav.go(Routes.LIVE)}){Text("查看全部")}}}
        item{Row(Modifier.padding(horizontal=16.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){listOf(R.drawable.stitch_onboarding_21,R.drawable.stitch_onboarding_22).forEach{res->MediaImage(res,Modifier.weight(1f).height(160.dp))}}}
        item{InfoCard(Modifier.padding(16.dp).fillMaxWidth()){Text("处理摘要",fontWeight=FontWeight.Bold);SummaryRow("扫描照片","142");SummaryRow("扫描视频","38");SummaryRow("选中高光","15",true)}}
        item{InfoCard(Modifier.padding(horizontal=16.dp).fillMaxWidth()){Row{Icon(Icons.Outlined.AutoAwesome,null,tint=AiViolet);Text("AI 剪辑决策",Modifier.padding(start=8.dp),fontWeight=FontWeight.Bold)};Text("优先保留了人物互动与日落镜头，并根据情绪变化匹配了背景音乐节奏。",Modifier.padding(top=8.dp),color=Muted,fontSize=11.sp)}}
        item{Row(Modifier.padding(16.dp),horizontalArrangement=Arrangement.spacedBy(10.dp)){OutlinePill("继续编辑",{nav.go(Routes.EDITOR)},Modifier.weight(1f));BlueButton("保存全部",{nav.go(Routes.EXPORT)},Modifier.weight(1f))}}
    }
}
@Composable private fun SummaryRow(label:String,value:String,accent:Boolean=false){Row(Modifier.fillMaxWidth().padding(top=10.dp)){Text(label,Modifier.weight(1f),color=Muted,fontSize=11.sp);Text(value,color=if(accent)HarmonyBlue else Ink,fontWeight=FontWeight.Bold,fontSize=11.sp)}}

@Composable fun AiStudioHomeScreen(vm:MomentsViewModel,nav:NavHostController){
    Box(Modifier.fillMaxSize().background(Canvas)){
        LazyColumn(contentPadding=PaddingValues(bottom=96.dp)){
            item{Row(Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),verticalAlignment=Alignment.CenterVertically){IconButton({nav.popBackStack(Routes.HOME,false)}){Icon(Icons.Outlined.ArrowBack,"返回相册")};Column(Modifier.weight(1f)){Text("AI 高光助手",fontSize=18.sp,fontWeight=FontWeight.Bold);Text("让每段回忆都有故事",color=Muted,fontSize=10.sp)};IconButton({nav.go(Routes.PROFILE)}){Icon(Icons.Outlined.Settings,null)}}}
            item{Box(Modifier.padding(16.dp).fillMaxWidth().height(245.dp).clip(RoundedCornerShape(28.dp))){MediaImage(R.drawable.stitch_onboarding_23,Modifier.fillMaxSize());Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent,Color.Black.copy(.72f)))));Column(Modifier.align(Alignment.BottomStart).padding(20.dp)){Surface(shape=CircleShape,color=Color.White.copy(.2f)){Text("✦ AI MOMENTS",Modifier.padding(10.dp,5.dp),color=Color.White,fontSize=10.sp)};Text("把今天的精彩\n剪成一段故事",Modifier.padding(top=10.dp),color=Color.White,fontSize=25.sp,lineHeight=30.sp,fontWeight=FontWeight.Bold);Button({nav.go(Routes.PICKER)},Modifier.padding(top=14.dp).fillMaxWidth().height(52.dp),shape=CircleShape,colors=ButtonDefaults.buttonColors(containerColor=HarmonyBlue),elevation=ButtonDefaults.buttonElevation(defaultElevation=8.dp)){Icon(Icons.Outlined.AutoAwesome,null,tint=Color.White);Spacer(Modifier.width(8.dp));Text("开始 AI 创作",fontWeight=FontWeight.Bold,fontSize=16.sp)}}}}
            item{Row(Modifier.padding(horizontal=16.dp),verticalAlignment=Alignment.CenterVertically){Text("你的高光",Modifier.weight(1f),fontSize=19.sp,fontWeight=FontWeight.Bold);TextButton({nav.go(Routes.TASKS)}){Text("查看全部")}}}
            item{InfoCard(Modifier.padding(horizontal=16.dp).fillMaxWidth().clickable{nav.go(Routes.HIGHLIGHTS)}){Row(verticalAlignment=Alignment.CenterVertically){MediaImage(R.drawable.stitch_onboarding_21,Modifier.size(88.dp));Column(Modifier.padding(12.dp).weight(1f)){Text("周末旅行回忆",fontWeight=FontWeight.Bold);Text("Vlog · 42 秒 · 15 个高光",color=Muted,fontSize=10.sp);LinearProgressIndicator({1f},Modifier.padding(top=9.dp).fillMaxWidth().height(3.dp))};Icon(Icons.Outlined.ChevronRight,null,tint=Muted)}}}
            item{Text("创作工具",Modifier.padding(16.dp),fontSize=19.sp,fontWeight=FontWeight.Bold)}
            item{Row(Modifier.padding(horizontal=16.dp),horizontalArrangement=Arrangement.spacedBy(10.dp)){StudioTool(Icons.Outlined.Movie,"自动剪辑","AI 编排故事",Modifier.weight(1f)){nav.go(Routes.PICKER)};StudioTool(Icons.Outlined.MotionPhotosOn,"高光动态照片","定格精彩一刻",Modifier.weight(1f)){nav.go(Routes.LIVE)}}}
        }
        Row(Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(Color.White).navigationBarsPadding().padding(vertical=5.dp),horizontalArrangement=Arrangement.SpaceAround){ToolNavItem(Icons.Outlined.Home,"首页",true){};ToolNavItem(Icons.Outlined.AddCircle,"创作"){nav.go(Routes.PICKER)};ToolNavItem(Icons.Outlined.VideoLibrary,"作品"){nav.go(Routes.TASKS)};ToolNavItem(Icons.Outlined.Person,"我的"){nav.go(Routes.PROFILE)}}
    }
}

@Composable private fun StudioTool(icon:androidx.compose.ui.graphics.vector.ImageVector,title:String,sub:String,modifier:Modifier,onClick:()->Unit){Card(modifier.clickable{onClick()},shape=RoundedCornerShape(24.dp),colors=CardDefaults.cardColors(Color.White)){Column(Modifier.padding(16.dp)){Box(Modifier.size(42.dp).background(SoftBlue,CircleShape),contentAlignment=Alignment.Center){Icon(icon,null,tint=HarmonyBlue)};Text(title,Modifier.padding(top=18.dp),fontWeight=FontWeight.Bold);Text(sub,color=Muted,fontSize=10.sp)}}}
