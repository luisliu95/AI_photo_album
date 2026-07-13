package com.harmony.moments.ui

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.harmony.moments.R

@Composable fun EditorScreen(vm:MomentsViewModel,nav:NavHostController){
    var playing by remember{mutableStateOf(false)};var timeline by remember{mutableFloatStateOf(.34f)};val totalSec=vm.videoLengthPref;val currentSec=(timeline*totalSec).toInt()
    Column(Modifier.fillMaxSize().background(Color.White)){TopBar("AI 视频剪辑",{nav.popBackStack(Routes.PICKER,false)});Box(Modifier.padding(horizontal=16.dp).fillMaxWidth().height(225.dp).clip(RoundedCornerShape(20.dp))){MediaImage(R.drawable.stitch_media_01,Modifier.fillMaxSize());IconButton({playing=!playing},Modifier.align(Alignment.Center).size(54.dp).background(Color.White.copy(.84f),CircleShape)){Icon(if(playing)Icons.Outlined.Pause else Icons.Outlined.PlayArrow,null,tint=HarmonyBlue)};Surface(Modifier.align(Alignment.BottomEnd).padding(9.dp),color=Color.Black.copy(.7f),shape=CircleShape){Text("${String.format("%02d",currentSec)} / ${totalSec}s",Modifier.padding(7.dp,2.dp),Color.White,fontSize=9.sp)}}
        Row(Modifier.fillMaxWidth().clickable{nav.go(Routes.VERSIONS)}.padding(16.dp,14.dp,16.dp,8.dp),verticalAlignment=Alignment.CenterVertically){Text("AI 生成版本",Modifier.weight(1f),fontSize=11.sp,color=Muted);Icon(Icons.Outlined.History,null,Modifier.size(17.dp),tint=HarmonyBlue)};LazyRow(contentPadding=PaddingValues(horizontal=16.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){items(3){i->SelectPill(if(i==0)"✦ V3 - 电影感" else "V${3-i} - ${if(i==1)"动感" else "原始想法"}",vm.editorVersion==3-i){vm.editorVersion=3-i}}}
        InfoCard(Modifier.padding(16.dp).fillMaxWidth()){Row(verticalAlignment=Alignment.CenterVertically){Text("时间轴结构",Modifier.weight(1f),fontWeight=FontWeight.Bold);Text("拖动调整",color=Muted,fontSize=10.sp)};LazyRow(Modifier.padding(top=14.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){itemsIndexed(mediaItems.take(4)){i,m->Column(Modifier.width(72.dp)){Box{MediaImage(m.res,Modifier.fillMaxWidth().height(82.dp));Box(Modifier.padding(5.dp).size(20.dp).background(Color.White,CircleShape),contentAlignment=Alignment.Center){Text("${i+1}",fontSize=9.sp)}};Text(listOf("开场","探索","高潮","收尾")[i],fontSize=9.sp,maxLines=1)}}};Slider(timeline,{timeline=it},modifier=Modifier.height(24.dp))}
        Spacer(Modifier.weight(1f));EditorBar(nav)
    }
}

@Composable private fun EditorBar(nav:NavHostController){Row(Modifier.fillMaxWidth().background(Color.White).border(1.dp,Hairline).navigationBarsPadding().padding(vertical=6.dp,horizontal=4.dp),horizontalArrangement=Arrangement.SpaceEvenly){ToolNavItem(Icons.Outlined.AutoAwesome,"特效"){nav.go(Routes.AI_CHAT)};ToolNavItem(Icons.Outlined.Movie,"剪辑",true){};ToolNavItem(Icons.Outlined.TextFields,"文字"){nav.go(Routes.CAPTIONS)};ToolNavItem(Icons.Outlined.MusicNote,"音频"){nav.go(Routes.MUSIC)}}}

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun MusicScreen(vm:MomentsViewModel,nav:NavHostController){
    var showMusic by remember{mutableStateOf(false)};var vocal by remember{mutableFloatStateOf(.2f)};var music by remember{mutableFloatStateOf(1f)};var playing by remember{mutableStateOf(false)}
    Column(Modifier.fillMaxSize().background(Canvas)){TopBar("AI 视频剪辑",{nav.popBackStack()});Box(Modifier.padding(16.dp).fillMaxWidth().height(205.dp).clip(RoundedCornerShape(20.dp))){MediaImage(R.drawable.stitch_media_04,Modifier.fillMaxSize());Row(Modifier.align(Alignment.Center).background(Color.White.copy(.9f),CircleShape),verticalAlignment=Alignment.CenterVertically){IconButton({}){Icon(Icons.Outlined.FastRewind,null)};IconButton({playing=!playing},Modifier.background(HarmonyBlue,CircleShape)){Icon(if(playing)Icons.Outlined.Pause else Icons.Outlined.PlayArrow,null,tint=Color.White)};IconButton({}){Icon(Icons.Outlined.FastForward,null)}}}
        InfoCard(Modifier.padding(horizontal=16.dp).fillMaxWidth()){Row(verticalAlignment=Alignment.CenterVertically){Box(Modifier.size(38.dp).background(HarmonyBlue,RoundedCornerShape(10.dp)),contentAlignment=Alignment.Center){Icon(Icons.Outlined.MusicNote,null,tint=Color.White)};Column(Modifier.padding(horizontal=10.dp).weight(1f)){Text(vm.chosenMusic,fontWeight=FontWeight.Bold);Text("午夜都市节奏 · AI 自动卡点",color=Muted,fontSize=10.sp)};TextButton({showMusic=true}){Text("换曲")}};BeatWave();Button({},modifier=Modifier.fillMaxWidth(),shape=CircleShape,colors=ButtonDefaults.buttonColors(containerColor=AiViolet)){Icon(Icons.Outlined.AutoAwesome,null);Text("  重新卡点")}}
        InfoCard(Modifier.padding(16.dp).fillMaxWidth()){Text("音频混音",fontWeight=FontWeight.Bold);VolumeLine("视频原声",vocal){vocal=it};VolumeLine("背景音乐",music){music=it}}
        Spacer(Modifier.weight(1f));EditorBar(nav)
    }
    if(showMusic)ModalBottomSheet({showMusic=false},shape=RoundedCornerShape(topStart=28.dp,topEnd=28.dp)){Text("选择音乐",Modifier.padding(20.dp),fontWeight=FontWeight.Bold,fontSize=18.sp);listOf("午夜都市节奏","风经过山谷","夏日公路","温柔时光").forEach{song->ListItem(headlineContent={Text(song)},supportingContent={Text("AI 推荐 · 02:38")},leadingContent={Icon(Icons.Outlined.MusicNote,null)},trailingContent={RadioButton(song==vm.chosenMusic,{vm.chosenMusic=song;showMusic=false})},modifier=Modifier.clickable{vm.chosenMusic=song;showMusic=false})};Spacer(Modifier.navigationBarsPadding())}
}

@Composable private fun BeatWave(){Row(Modifier.fillMaxWidth().height(92.dp).padding(vertical=14.dp),horizontalArrangement=Arrangement.SpaceEvenly,verticalAlignment=Alignment.CenterVertically){repeat(28){i->Box(Modifier.width(3.dp).height((12+(i*17%58)).dp).background(if(i==11)AiViolet else HarmonyBlue,RoundedCornerShape(3.dp)))}}}
@Composable private fun VolumeLine(label:String,value:Float,onChange:(Float)->Unit){Row(verticalAlignment=Alignment.CenterVertically){Icon(Icons.Outlined.VolumeUp,null,Modifier.size(16.dp));Text(label,Modifier.padding(start=8.dp).weight(1f),fontSize=11.sp);Text("${(value*100).toInt()}%",fontSize=10.sp)};Slider(value,onChange,modifier=Modifier.height(30.dp))}

@Composable fun CaptionsScreen(vm:MomentsViewModel,nav:NavHostController){
    var editing by remember{mutableIntStateOf(-1)};var draft by remember{mutableStateOf("")}
    LazyColumn(Modifier.fillMaxSize().background(Color.White),contentPadding=PaddingValues(bottom=30.dp)){item{TopBar("AI Video Editor [P10]",{nav.popBackStack()})};item{Box(Modifier.padding(horizontal=16.dp).fillMaxWidth().height(250.dp)){MediaImage(R.drawable.stitch_media_06,Modifier.fillMaxSize());Text(vm.captions.getOrNull(editing.coerceAtLeast(0))?.text?:vm.captions.first().text,Modifier.align(Alignment.BottomCenter).background(Color.Black.copy(.66f),RoundedCornerShape(8.dp)).padding(8.dp),Color.White,fontSize=11.sp)}};item{LazyRow(Modifier.padding(16.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){items(4){i->AssistChip({},label={Text(listOf("智能断句","去除语气词","翻译","样式")[i],fontSize=10.sp)},leadingIcon={Icon(listOf(Icons.Outlined.AutoAwesome,Icons.Outlined.CleaningServices,Icons.Outlined.Translate,Icons.Outlined.TextFields)[i],null,Modifier.size(15.dp))})}}};item{InfoCard(Modifier.padding(horizontal=16.dp).fillMaxWidth()){Row{Text("交互式文稿",Modifier.weight(1f),fontWeight=FontWeight.Bold);Icon(Icons.Outlined.Undo,null,Modifier.clickable{editing=-1});Spacer(Modifier.width(12.dp));Icon(Icons.Outlined.Redo,null)};vm.captions.forEachIndexed{i,line->Row(Modifier.fillMaxWidth().clickable{editing=i;draft=line.text}.padding(vertical=9.dp),verticalAlignment=Alignment.Top){Text(line.time,color=if(editing==i)AiViolet else Muted,fontSize=9.sp,modifier=Modifier.width(42.dp));if(editing==i)OutlinedTextField(draft,{draft=it},Modifier.weight(1f),textStyle=LocalTextStyle.current.copy(fontSize=11.sp),trailingIcon={IconButton({vm.captions[i]=line.copy(text=draft);editing=-1}){Icon(Icons.Outlined.Check,null)}})else Text(line.text,Modifier.weight(1f).then(if(i==1)Modifier.border(1.dp,AiViolet,RoundedCornerShape(10.dp)).padding(8.dp)else Modifier),fontSize=11.sp)}}}}}
}

@Composable fun ResultScreen(nav:NavHostController){var playing by remember{mutableStateOf(false)};Column(Modifier.fillMaxSize().background(Canvas)){
        TopBar("AI 视频剪辑",{nav.popBackStack()})
        LazyColumn(Modifier.weight(1f),contentPadding=PaddingValues(16.dp),verticalArrangement=Arrangement.spacedBy(14.dp)){
            item{InfoCard{Box(Modifier.fillMaxWidth().height(220.dp).clickable{playing=!playing}){MediaImage(R.drawable.stitch_media_02,Modifier.fillMaxSize());Icon(if(playing)Icons.Outlined.Pause else Icons.Outlined.PlayCircle,null,Modifier.align(Alignment.Center).size(54.dp),tint=Color.White);Surface(Modifier.align(Alignment.BottomEnd).padding(8.dp),shape=CircleShape,color=Color.Black.copy(.7f)){Text("00:38",Modifier.padding(7.dp,2.dp),Color.White,fontSize=9.sp)}};Text("周末旅行高光",Modifier.padding(top=12.dp),fontSize=20.sp,fontWeight=FontWeight.Bold);Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){listOf("38s","电影感","22 素材").forEach{Surface(shape=CircleShape,color=Canvas){Text(it,Modifier.padding(9.dp,4.dp),fontSize=9.sp)}}}}}
            item{InfoCard{Row{Text("AI 摘要",Modifier.weight(1f),color=HarmonyBlue,fontWeight=FontWeight.Bold);Icon(Icons.Outlined.AutoAwesome,null,tint=AiViolet)};Text("已成功将您的周末旅行高光剪辑为电影感短片。节奏已根据背景音乐自动卡点，色调采用了明亮的夏日风格。",Modifier.padding(top=10.dp),color=Muted,fontSize=12.sp)}}
        }
        BlueButton("⇩  保存到相册",{nav.go(Routes.EXPORT)},Modifier.padding(horizontal=16.dp).fillMaxWidth())
        Row(Modifier.padding(10.dp,8.dp).navigationBarsPadding(),horizontalArrangement=Arrangement.spacedBy(10.dp)){OutlinePill("继续编辑",{nav.go(Routes.EDITOR)},Modifier.weight(1f));OutlinePill("↻ 重新生成",{nav.go(Routes.PROCESSING)},Modifier.weight(1f))}
    }
}

@Composable fun ExportScreen(nav:NavHostController){val context=LocalContext.current;Column(Modifier.fillMaxSize().background(Canvas)){TopBar("AI 视频剪辑",{nav.popBackStack()});LazyColumn(Modifier.weight(1f),contentPadding=PaddingValues(16.dp),horizontalAlignment=Alignment.CenterHorizontally){item{Icon(Icons.Outlined.CheckCircle,null,tint=HarmonyBlue,modifier=Modifier.size(42.dp));Text("导出成功",Modifier.padding(8.dp),fontSize=20.sp,fontWeight=FontWeight.Bold)};item{InfoCard(Modifier.fillMaxWidth()){MediaImage(R.drawable.stitch_media_01,Modifier.fillMaxWidth().height(430.dp));Row(Modifier.padding(top=10.dp),verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text("Final_Render_01.mp4",fontSize=11.sp);Text("1080P · 00:45",color=Muted,fontSize=9.sp)};Surface(shape=CircleShape,color=Canvas){Text("124 MB",Modifier.padding(8.dp,4.dp),fontSize=9.sp)}}}};item{BlueButton("⇩  保存到相册",{},Modifier.padding(top=14.dp).fillMaxWidth())};item{Row(Modifier.padding(top=8.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){OutlinePill("◉ 查看作品",{nav.go(Routes.TASKS)},Modifier.weight(1f));OutlinePill("✎ 继续编辑",{nav.go(Routes.EDITOR)},Modifier.weight(1f))}};item{Text("快速分享",Modifier.padding(20.dp),color=Muted,fontSize=10.sp);Row(horizontalArrangement=Arrangement.spacedBy(22.dp)){listOf("小红书","抖音","B站","微信").forEach{label->Column(horizontalAlignment=Alignment.CenterHorizontally,modifier=Modifier.clickable{val send=Intent(Intent.ACTION_SEND).apply{type="text/plain";putExtra(Intent.EXTRA_TEXT,"我的 AI 高光作品")};context.startActivity(Intent.createChooser(send,"分享作品"))}){Box(Modifier.size(42.dp).background(SoftBlue,CircleShape),contentAlignment=Alignment.Center){Icon(Icons.Outlined.Share,null,tint=HarmonyBlue)};Text(label,fontSize=9.sp)}}}}}
    }
}

@Composable fun AiChatScreen(nav:NavHostController){var prompt by remember{mutableStateOf("")};var sent by remember{mutableStateOf(false)};Column(Modifier.fillMaxSize().background(Canvas)){TopBar("AI 对话修改",{nav.popBackStack()});MediaImage(R.drawable.stitch_media_01,Modifier.padding(16.dp).fillMaxWidth().height(230.dp));InfoCard(Modifier.padding(horizontal=16.dp).fillMaxWidth()){Text("AI 剪辑助手",fontWeight=FontWeight.Bold);Text(if(sent)"好的，我会加强日落镜头并让结尾更舒缓。" else "可以告诉我你想怎样调整这支视频。",Modifier.padding(top=8.dp),color=Muted)};Spacer(Modifier.weight(1f));Row(Modifier.padding(16.dp).fillMaxWidth().background(Color.White,CircleShape).padding(8.dp),verticalAlignment=Alignment.CenterVertically){TextField(prompt,{prompt=it},Modifier.weight(1f),placeholder={Text("例如：让结尾更温暖")},colors=TextFieldDefaults.colors(unfocusedContainerColor=Color.Transparent,focusedContainerColor=Color.Transparent,unfocusedIndicatorColor=Color.Transparent,focusedIndicatorColor=Color.Transparent));IconButton({sent=true;prompt=""},Modifier.background(HarmonyBlue,CircleShape)){Icon(Icons.Outlined.ArrowUpward,null,tint=Color.White)}};Spacer(Modifier.navigationBarsPadding())}}

@Composable fun VersionScreen(vm:MomentsViewModel,nav:NavHostController){Column(Modifier.fillMaxSize().background(Canvas)){TopBar("版本管理",{nav.popBackStack()});LazyColumn(contentPadding=PaddingValues(16.dp),verticalArrangement=Arrangement.spacedBy(10.dp)){items(3){i->val v=3-i;InfoCard(Modifier.fillMaxWidth().clickable{vm.editorVersion=v;nav.popBackStack()}){Row(verticalAlignment=Alignment.CenterVertically){MediaImage(mediaItems[i].res,Modifier.size(72.dp));Column(Modifier.padding(12.dp).weight(1f)){Text("V$v · ${listOf("电影感","动感","原始想法")[i]}",fontWeight=FontWeight.Bold);Text(if(v==vm.editorVersion)"当前版本" else "点击恢复此版本",color=if(v==vm.editorVersion)HarmonyBlue else Muted,fontSize=10.sp)};Icon(Icons.Outlined.Restore,null)}}}}}}
