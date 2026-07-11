package com.harmony.moments.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harmony.moments.R

val HarmonyBlue = Color(0xFF087CF0)
val AiViolet = Color(0xFF7047FF)
val AiCyan = Color(0xFF00B9D8)
val Canvas = Color(0xFFF8F9FB)
val Ink = Color(0xFF17191C)
val Muted = Color(0xFF737984)
val Hairline = Color(0xFFE5E8EC)
val SoftBlue = Color(0xFFEAF3FF)
val AiGradient = Brush.linearGradient(listOf(AiViolet, HarmonyBlue, AiCyan))

val CardRadius = 24.dp
val SmallRadius = 14.dp
val PageMargin = 16.dp

private val Vietnam = FontFamily(
    Font(R.font.be_vietnam_pro, FontWeight.Normal),
    Font(R.font.be_vietnam_pro, FontWeight.Medium),
    Font(R.font.be_vietnam_pro, FontWeight.SemiBold),
    Font(R.font.be_vietnam_pro, FontWeight.Bold),
)

private val Typography = androidx.compose.material3.Typography(
    headlineLarge = TextStyle(fontFamily=Vietnam,fontSize=28.sp,lineHeight=34.sp,fontWeight=FontWeight.Bold),
    headlineMedium = TextStyle(fontFamily=Vietnam,fontSize=22.sp,lineHeight=28.sp,fontWeight=FontWeight.SemiBold),
    titleLarge = TextStyle(fontFamily=Vietnam,fontSize=18.sp,lineHeight=24.sp,fontWeight=FontWeight.SemiBold),
    titleMedium = TextStyle(fontFamily=Vietnam,fontSize=16.sp,lineHeight=22.sp,fontWeight=FontWeight.SemiBold),
    bodyLarge = TextStyle(fontFamily=Vietnam,fontSize=15.sp,lineHeight=22.sp),
    bodyMedium = TextStyle(fontFamily=Vietnam,fontSize=13.sp,lineHeight=19.sp),
    labelMedium = TextStyle(fontFamily=Vietnam,fontSize=12.sp,lineHeight=16.sp,fontWeight=FontWeight.Medium),
)

@Composable fun MomentsTheme(content:@Composable ()->Unit) {
    MaterialTheme(
        colorScheme=lightColorScheme(primary=HarmonyBlue,secondary=AiViolet,background=Canvas,surface=Color.White,onSurface=Ink,outline=Hairline),
        typography=Typography,
        content=content,
    )
}
