package com.harmony.moments.ui

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.harmony.moments.R

@Composable fun LiveAlbumScreen(vm:MomentsViewModel,nav:NavHostController){
    val filters=listOf("全部 (42)","婚礼幸福","夏日旅行","极限运动")
    val shown=if(vm.liveFilter==filters.first())mediaItems else mediaItems.filter{it.category==vm.liveFilter}
    Box(Modifier.fillMaxSize().background(Canvas)){Column{TopBar(EditorScreenTitle,{nav.popBackStack()});Row(Modifier.padding(horizontal=16.dp),verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text("AI 高光动态照片",fontSize=23.sp,fontWeight=FontWeight.Bold);Text("智能生成主题回忆",color=Muted,fontSize=11.sp)};Box(Modifier.size(46.dp).background(SoftBlue,CircleShape),contentAlignment=Alignment.Center){Icon(Icons.Outlined.MotionPhotosOn,null,tint=HarmonyBlue)}};LazyRow(Modifier.padding(vertical=14.dp),contentPadding=PaddingValues(horizontal=16.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){items(filters){SelectPill(it,vm.liveFilter==it){vm.liveFilter=it}}};LazyColumn(contentPadding=PaddingValues(start=16.dp,end=16.dp,bottom=92.dp)){items(shown.chunked(2)){pair->Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp),verticalAlignment=Alignment.Top){pair.forEachIndexed{i,m->LiveCard(m,Modifier.weight(1f).height(if((m.id+i)%2==0)190.dp else 158.dp)){nav.go(Routes.LIVE_DETAIL)}};if(pair.size==1)Spacer(Modifier.weight(1f))};Spacer(Modifier.height(10.dp))}}}
        FloatingActionButton({nav.go(Routes.PICKER)},Modifier.align(Alignment.BottomEnd).padding(22.dp).navigationBarsPadding(),containerColor=HarmonyBlue,shape=CircleShape){Icon(Icons.Outlined.Add,null,tint=Color.White)}
    }
}

@Composable private fun LiveCard(item:MediaItem,mod:Modifier,onClick:()->Unit){Box(mod.clip(RoundedCornerShape(CardRadius)).clickable{onClick()}){Image(painterResource(item.res),null,Modifier.fillMaxSize(),contentScale=ContentScale.Crop);Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent,Color.Black.copy(.55f)))));Surface(Modifier.padding(8.dp),shape=CircleShape,color=Color.White.copy(.82f)){Text("AI 精选",Modifier.padding(7.dp,3.dp),fontSize=8.sp)};Column(Modifier.align(Alignment.BottomStart).padding(10.dp)){Text(listOf("梦幻婚礼","东京夜行","巴厘岛假日","毛孩子时光","极限冲浪")[item.id%5],color=Color.White,fontWeight=FontWeight.Bold,fontSize=13.sp);Text("◉ ${18+item.id}",color=Color.White.copy(.85f),fontSize=9.sp)}}}

@Composable fun LiveDetailScreen(nav:NavHostController){Column(Modifier.fillMaxSize().background(Color.White)){TopBar("动态照片",{nav.popBackStack()});Box(Modifier.padding(16.dp).fillMaxWidth().weight(1f).clip(RoundedCornerShape(26.dp))){MediaImage(R.drawable.stitch_media_01,Modifier.fillMaxSize());Icon(Icons.Outlined.MotionPhotosOn,null,Modifier.align(Alignment.TopEnd).padding(14.dp).size(34.dp),tint=Color.White);IconButton({},Modifier.align(Alignment.Center).background(Color.White.copy(.8f),CircleShape)){Icon(Icons.Outlined.PlayArrow,null,tint=HarmonyBlue)}};Text("山顶日落",Modifier.padding(horizontal=16.dp),fontSize=22.sp,fontWeight=FontWeight.Bold);Text("动态照片 · 3 秒 · AI 精选",Modifier.padding(16.dp,4.dp),color=Muted,fontSize=11.sp);Row(Modifier.padding(16.dp).navigationBarsPadding(),horizontalArrangement=Arrangement.spacedBy(10.dp)){OutlinePill("分享",{},Modifier.weight(1f));BlueButton("编辑封面",{nav.go(Routes.COVER)},Modifier.weight(1f))}}}

@Composable fun CoverScreen(nav:NavHostController){var frame by remember{mutableFloatStateOf(.45f)};var brightness by remember{mutableFloatStateOf(.4f)};var warmth by remember{mutableFloatStateOf(.55f)};var speed by remember{mutableStateOf("1x")};Column(Modifier.fillMaxSize().background(Canvas)){TopBar(EditorScreenTitle,{nav.popBackStack()});Box(Modifier.padding(horizontal=16.dp).fillMaxWidth().height(315.dp).clip(RoundedCornerShape(20.dp))){MediaImage(R.drawable.stitch_media_01,Modifier.fillMaxSize());Icon(Icons.Outlined.PlayCircle,null,Modifier.align(Alignment.Center).size(48.dp),tint=Color.White)};Row(Modifier.padding(16.dp),horizontalArrangement=Arrangement.spacedBy(5.dp)){mediaItems.take(6).forEachIndexed{i,m->MediaImage(m.res,Modifier.weight(1f).height(48.dp).then(if((frame*5).toInt()==i)Modifier.border(2.dp,HarmonyBlue,RoundedCornerShape(8.dp))else Modifier).clickable{frame=i/5f})}};InfoCard(Modifier.padding(horizontal=16.dp).fillMaxWidth()){AdjustLine("亮度",brightness){brightness=it};AdjustLine("色温",warmth){warmth=it}};InfoCard(Modifier.padding(16.dp).fillMaxWidth()){Text("播放速度",fontWeight=FontWeight.Bold,fontSize=12.sp);Row(Modifier.fillMaxWidth().padding(top=8.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){listOf("1x","0.5x","0.25x").forEach{SelectPill(it,speed==it){speed=it}}}};Spacer(Modifier.weight(1f));BlueButton("保存",{nav.popBackStack()},Modifier.padding(16.dp).fillMaxWidth().navigationBarsPadding())}}
@Composable private fun AdjustLine(label:String,value:Float,onChange:(Float)->Unit){Row(verticalAlignment=Alignment.CenterVertically){Text(label,Modifier.width(42.dp),fontSize=11.sp);Slider(value,onChange,Modifier.weight(1f).height(30.dp));Text("${(value*100).toInt()}",Modifier.width(28.dp),fontSize=9.sp)}}

@Composable fun TaskScreen(vm:MomentsViewModel,nav:NavHostController){
    var tab by remember{mutableStateOf("高光时刻")};val context=LocalContext.current
    Column(Modifier.fillMaxSize().background(Canvas)){
        TopBar("作品中心",{nav.popBackStack()})
        Text("作品 / 任务中心",Modifier.padding(horizontal=16.dp),fontSize=24.sp,fontWeight=FontWeight.Bold)
        Row(Modifier.padding(16.dp,8.dp),horizontalArrangement=Arrangement.spacedBy(16.dp)){listOf("高光时刻","Live 图","草稿").forEach{t->Column(Modifier.clickable{tab=t}){Text(t,color=if(tab==t)HarmonyBlue else Muted,fontSize=12.sp,fontWeight=if(tab==t)FontWeight.Bold else FontWeight.Normal);if(tab==t)HorizontalDivider(Modifier.padding(top=7.dp).width(50.dp),2.dp,HarmonyBlue)}}}
        LazyColumn(contentPadding=PaddingValues(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
            item{TaskCard(R.drawable.stitch_media_07,"Cyberpunk City Travel","AI 效果生成中...","${vm.taskProgress}%",onClick={vm.beginProcessing();nav.go(Routes.PROCESSING)})}
            item{TaskCard(R.drawable.stitch_media_02,"Morning Mountain","已完成 · 2 小时前","分享",onClick={nav.go(Routes.RESULT)},onAction=share(context))}
            item{InfoCard(Modifier.fillMaxWidth().border(1.dp,Color(0xFFFFC9C4),RoundedCornerShape(CardRadius)).clickable{vm.taskProgress=67;vm.beginProcessing();nav.go(Routes.PROCESSING)}){Row(verticalAlignment=Alignment.CenterVertically){Icon(Icons.Outlined.ErrorOutline,null,tint=Color.Red);Column(Modifier.padding(12.dp).weight(1f)){Text("人像精修",fontWeight=FontWeight.Bold);Text("渲染失败。点击重试。",color=Color.Red,fontSize=10.sp)};Icon(Icons.Outlined.Refresh,null,tint=Color.Red)}}}
            item{TaskCard(R.drawable.stitch_media_01,"滑板高光","已完成 · 昨天","分享",onClick={nav.go(Routes.RESULT)},onAction=share(context))}
        }
    }
}

private fun share(context:android.content.Context):()->Unit={val i=Intent(Intent.ACTION_SEND).apply{type="text/plain";putExtra(Intent.EXTRA_TEXT,"我的 AI 高光作品")};context.startActivity(Intent.createChooser(i,"分享作品"))}
@Composable private fun TaskCard(res:Int,title:String,sub:String,action:String,onClick:()->Unit,onAction:()->Unit={}){
    InfoCard(Modifier.fillMaxWidth().clickable{onClick()}){Row(verticalAlignment=Alignment.CenterVertically){MediaImage(res,Modifier.size(68.dp));Column(Modifier.padding(12.dp).weight(1f)){Text(title,fontWeight=FontWeight.Bold,maxLines=1,overflow=TextOverflow.Ellipsis);Text(sub,color=if(action.endsWith("%"))HarmonyBlue else Muted,fontSize=10.sp);if(action.endsWith("%"))LinearProgressIndicator({action.dropLast(1).toFloat()/100},Modifier.padding(top=8.dp).fillMaxWidth().height(3.dp))};if(action=="分享")IconButton(onAction){Icon(Icons.Outlined.Share,null)}else Text(action,color=HarmonyBlue,fontSize=11.sp)}}
}
