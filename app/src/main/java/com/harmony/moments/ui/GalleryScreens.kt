package com.harmony.moments.ui

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.harmony.moments.R

@Composable fun GalleryScreen(vm:MomentsViewModel,nav:NavHostController){
    var permission by remember{mutableStateOf(false)}
    Box(Modifier.fillMaxSize().background(Color.White)){
        LazyColumn(contentPadding=PaddingValues(bottom=24.dp),modifier=Modifier.navigationBarsPadding()){
            item{Row(Modifier.fillMaxWidth().statusBarsPadding().height(52.dp).padding(horizontal=12.dp),verticalAlignment=Alignment.CenterVertically){Icon(Icons.Outlined.Menu,null,Modifier.size(18.dp));Text("相册",Modifier.padding(start=12.dp).weight(1f),fontWeight=FontWeight.SemiBold);if(DemoMode)Surface(shape=CircleShape,color=SoftBlue){Text("演示",Modifier.padding(horizontal=10.dp,vertical=4.dp),color=HarmonyBlue,fontSize=10.sp,fontWeight=FontWeight.Bold)};IconButton({}){Icon(Icons.Outlined.Search,null)};IconButton({}){Icon(Icons.Outlined.MoreVert,null)}}}
            if(vm.showNextDayReminder)item{NextDayReminderCard{nav.go(Routes.HIGHLIGHTS)}}
            item{HighlightCard(vm,{permission=true},{nav.go(Routes.PICKER)})}
            item{Text("今天",Modifier.padding(16.dp,14.dp,16.dp,8.dp),fontWeight=FontWeight.SemiBold)}
            item{GalleryGrid(mediaItems.filter{it.day=="Today"},vm,nav)}
            item{Text("昨天",Modifier.padding(16.dp,14.dp,16.dp,8.dp),fontWeight=FontWeight.SemiBold)}
            item{GalleryGrid(mediaItems.filter{it.day=="Yesterday"},vm,nav)}
        }
        HighlightAssistantOverlay(vm,Modifier.align(Alignment.CenterEnd),onLearnMore={vm.assistantExpanded=false;vm.onboardingStep=0;nav.go(Routes.ONBOARDING)})
        if(permission)PrivacySheet(onDismiss={permission=false},onAccept={vm.permissionGranted=true;permission=false;if(vm.preferenceDone)nav.go(Routes.PICKER)else nav.go(Routes.PROFILE)})
    }
}


@Composable private fun HighlightCard(vm:MomentsViewModel,onGenerate:()->Unit,onViewMaterials:()->Unit){
    Box(Modifier.padding(horizontal=16.dp,vertical=8.dp).fillMaxWidth().height(205.dp).clip(RoundedCornerShape(24.dp)).clickable{onGenerate()}){
        Image(painterResource(R.drawable.stitch_media_01),null,Modifier.fillMaxSize(),contentScale=ContentScale.Crop)
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent,Color.Black.copy(.72f)))))
        Text("✦",Modifier.align(Alignment.TopEnd).padding(14.dp),color=Color.White,fontSize=22.sp)
        Column(Modifier.align(Alignment.BottomStart).padding(16.dp)){
            Text("为你发现一段旅行高光",color=Color.White,fontSize=20.sp,fontWeight=FontWeight.Bold)
            Text("已选 ${vm.selected.size} 项 · 预计 ${formatVideoDuration(vm.estimatedSeconds())}",color=Color.White.copy(.9f),fontSize=11.sp)
            Row(Modifier.padding(top=10.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){Button(onGenerate,shape=CircleShape,contentPadding=PaddingValues(horizontal=16.dp,vertical=0.dp)){Text("立即生成",fontSize=11.sp)};OutlinedButton(onViewMaterials,shape=CircleShape,colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.White)){Text("查看素材",fontSize=11.sp)}}
        }
    }
}

@Composable private fun GalleryGrid(items:List<MediaItem>,vm:MomentsViewModel,nav:NavHostController){
    val columns=items.chunked(2)
    Column(Modifier.padding(horizontal=16.dp),verticalArrangement=Arrangement.spacedBy(4.dp)){columns.forEachIndexed{row,pair->Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(4.dp)){pair.forEachIndexed{i,m->MediaImage(m.res,Modifier.weight(1f).height(if((row+i)%2==0)150.dp else 116.dp).clickable{if(m.id !in vm.selected)vm.toggle(m.id);nav.go(Routes.PICKER)});};if(pair.size==1)Spacer(Modifier.weight(1f))}}}
}

@Composable private fun PrivacySheet(onDismiss:()->Unit,onAccept:()->Unit){
    AlertDialog(onDismissRequest={},shape=RoundedCornerShape(28.dp),icon={Icon(Icons.Outlined.Security,null,tint=HarmonyBlue)},title={Text("让 AI 帮你发现精彩时刻")},text={Text("允许访问你选择的照片和视频，用于识别高光片段并生成作品。素材仅在本次创作中使用。",color=Muted)},confirmButton={BlueButton("选择照片并继续",onAccept,Modifier.fillMaxWidth())},dismissButton={TextButton(onDismiss){Text("稍后")}})
}

@Composable fun PickerScreen(vm:MomentsViewModel,nav:NavHostController){
    val filters=listOf("全部素材","视频","照片","相册");var showMenu by remember{mutableStateOf(false)}
    Box(Modifier.fillMaxSize().background(Color.White)){
        Column{TopBar("AI 创作",{nav.popBackStack()},menu={showMenu=true});LazyRow(Modifier.padding(horizontal=12.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){items(filters){SelectPill(it,vm.mediaFilter==it){vm.mediaFilter=it}}};LazyColumn(Modifier.weight(1f),contentPadding=PaddingValues(top=12.dp,bottom=132.dp)){listOf("Today","Yesterday").forEach{day->item{Text(if(day=="Today")"今天" else "昨天",Modifier.padding(16.dp,10.dp,16.dp,6.dp),fontWeight=FontWeight.SemiBold)};item{PickerGrid(mediaItems.filter{m->m.day==day&&(vm.mediaFilter=="全部素材"||vm.mediaFilter=="相册"||(vm.mediaFilter=="视频"&&m.video)||(vm.mediaFilter=="照片"&&!m.video))},vm)}}}}
        SelectedTray(vm,Modifier.align(Alignment.BottomCenter)){if(vm.selected.isNotEmpty())nav.go(Routes.SETTINGS) else {}}
        if(showMenu)AlertDialog({showMenu=false},title={Text("更多操作")},text={Text("选择要进行的操作")},confirmButton={TextButton({showMenu=false;nav.go(Routes.SETTINGS)}){Text("创作设置")}},dismissButton={TextButton({showMenu=false;nav.go(Routes.PROFILE)}){Text("创作偏好")}})
    }
}

@Composable private fun PickerGrid(items:List<MediaItem>,vm:MomentsViewModel){
    Column(Modifier.padding(horizontal=4.dp)){items.chunked(3).forEach{row->Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(3.dp)){row.forEach{m->val order=vm.selected.indexOf(m.id);Box(Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(7.dp)).clickable{vm.toggle(m.id)}){Image(painterResource(m.res),null,Modifier.fillMaxSize(),contentScale=ContentScale.Crop);if(order>=0)Box(Modifier.align(Alignment.TopEnd).padding(5.dp).size(22.dp).background(HarmonyBlue,CircleShape),contentAlignment=Alignment.Center){Text("${order+1}",color=Color.White,fontSize=10.sp)};if(m.video)Surface(Modifier.align(Alignment.BottomEnd).padding(5.dp),color=Color.Black.copy(.65f),shape=CircleShape){Text(m.duration,Modifier.padding(6.dp,2.dp),Color.White,fontSize=9.sp)}}};};repeat(3-row.size){Spacer(Modifier.weight(1f))}}}
}

@Composable private fun SelectedTray(vm:MomentsViewModel,mod:Modifier,next:()->Unit){
    Card(mod.padding(12.dp).fillMaxWidth(),shape=RoundedCornerShape(24.dp),colors=CardDefaults.cardColors(Color.White),elevation=CardDefaults.cardElevation(10.dp)){
        Row(Modifier.padding(12.dp),verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text("已选择 ${vm.selected.size} 项",fontWeight=FontWeight.Bold);Text("✦ 预计生成 ${formatVideoDuration(vm.estimatedSeconds())}",color=Muted,fontSize=10.sp);if(vm.selected.isEmpty())Text("请点选上方素材",color=Muted,fontSize=9.sp,modifier=Modifier.padding(top=4.dp));LazyRow(Modifier.padding(top=6.dp),horizontalArrangement=Arrangement.spacedBy(4.dp)){items(vm.selected){id->MediaImage(mediaItems.first{it.id==id}.res,Modifier.size(34.dp))}}};BlueButton(if(vm.selected.isEmpty())"请先选择素材" else "下一步  →",next,enabled=vm.selected.isNotEmpty())}
    }
}
