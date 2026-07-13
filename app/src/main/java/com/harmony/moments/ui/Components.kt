package com.harmony.moments.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable fun TopBar(title:String,back:(()->Unit)?=null,menu:(()->Unit)?={}){
    Row(Modifier.fillMaxWidth().statusBarsPadding().height(54.dp).padding(horizontal=8.dp),verticalAlignment=Alignment.CenterVertically){
        Box(Modifier.size(44.dp),contentAlignment=Alignment.Center){if(back!=null)IconButton(back){Icon(Icons.Outlined.ArrowBack,"返回")}}
        Text(title,Modifier.weight(1f),textAlign=TextAlign.Center,fontWeight=FontWeight.SemiBold,fontSize=16.sp)
        Box(Modifier.size(44.dp),contentAlignment=Alignment.Center){if(menu!=null)IconButton(menu){Icon(Icons.Outlined.MoreVert,"更多")}}
    }
}

@Composable fun MediaImage(@DrawableRes res:Int,modifier:Modifier=Modifier,contentScale:ContentScale=ContentScale.Crop){
    Image(painterResource(res),null,modifier.clip(RoundedCornerShape(SmallRadius)),contentScale=contentScale)
}

@Composable fun BlueButton(text:String,onClick:()->Unit,modifier:Modifier=Modifier,enabled:Boolean=true){
    Button(onClick,modifier.height(48.dp),shape=CircleShape,enabled=enabled,colors=ButtonDefaults.buttonColors(containerColor=HarmonyBlue)){Text(text,fontWeight=FontWeight.SemiBold)}
}

@Composable fun OutlinePill(text:String,onClick:()->Unit,modifier:Modifier=Modifier){
    OutlinedButton(onClick,modifier.height(44.dp),shape=CircleShape,border=ButtonDefaults.outlinedButtonBorder){Text(text,color=Ink,fontSize=13.sp)}
}

@Composable fun SelectPill(text:String,selected:Boolean,onClick:()->Unit){
    Surface(Modifier.clickable{onClick()},shape=CircleShape,color=if(selected)HarmonyBlue else Color.White,border=if(selected)null else androidx.compose.foundation.BorderStroke(1.dp,Hairline)){
        Text(text,Modifier.padding(horizontal=15.dp,vertical=8.dp),color=if(selected)Color.White else Ink,fontSize=12.sp)
    }
}

@Composable fun ToolNavItem(icon:ImageVector,label:String,selected:Boolean=false,onClick:()->Unit){
    Column(Modifier.widthIn(min=72.dp).clickable{onClick()}.padding(vertical=8.dp,horizontal=2.dp),horizontalAlignment=Alignment.CenterHorizontally){
        Box(Modifier.size(36.dp).then(if(selected)Modifier.background(HarmonyBlue,CircleShape) else Modifier),contentAlignment=Alignment.Center){Icon(icon,null,tint=if(selected)Color.White else Ink,modifier=Modifier.size(20.dp))}
        Text(label,fontSize=10.sp,color=if(selected)HarmonyBlue else Ink,maxLines=1)
    }
}

@Composable fun SettingRow(icon:ImageVector,title:String,value:String,onClick:()->Unit={}){
    Row(Modifier.fillMaxWidth().clickable{onClick()}.padding(vertical=14.dp),verticalAlignment=Alignment.CenterVertically){
        Box(Modifier.size(32.dp).background(Canvas,CircleShape),contentAlignment=Alignment.Center){Icon(icon,null,Modifier.size(17.dp))}
        Text(title,Modifier.padding(start=12.dp).weight(1f),fontSize=13.sp)
        Text(value,color=Muted,fontSize=11.sp)
        Text("  ›",color=Muted,fontSize=18.sp)
    }
}

@Composable fun InfoCard(modifier:Modifier=Modifier,content:@Composable ColumnScope.()->Unit){
    Card(modifier,shape=RoundedCornerShape(CardRadius),colors=CardDefaults.cardColors(Color.White),border=androidx.compose.foundation.BorderStroke(1.dp,Hairline)){Column(Modifier.padding(16.dp),content=content)}
}
