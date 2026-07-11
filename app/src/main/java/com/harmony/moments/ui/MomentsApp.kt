package com.harmony.moments.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Routes{
    const val HOME="home";const val PICKER="picker";const val SETTINGS="settings";const val PROCESSING="processing";const val EDITOR="editor"
    const val MUSIC="music";const val CAPTIONS="captions";const val RESULT="result";const val EXPORT="export";const val LIVE="live";const val LIVE_DETAIL="liveDetail"
    const val COVER="cover";const val TASKS="tasks";const val PROFILE="profile";const val AI_CHAT="aiChat";const val VERSIONS="versions"
    const val ONBOARDING="onboarding";const val HIGHLIGHTS="highlights"
    const val STUDIO="studio"
}

@Composable fun MomentsApp(vm:MomentsViewModel=viewModel(),nav:NavHostController=rememberNavController()){
    Surface(Modifier.fillMaxSize(),color=Canvas){
        NavHost(nav,Routes.HOME){
            composable(Routes.HOME){GalleryScreen(vm,nav)}
            composable(Routes.PICKER){PickerScreen(vm,nav)}
            composable(Routes.SETTINGS){SettingsScreen(vm,nav)}
            composable(Routes.PROCESSING){ProcessingScreen(vm,nav)}
            composable(Routes.EDITOR){EditorScreen(vm,nav)}
            composable(Routes.MUSIC){MusicScreen(vm,nav)}
            composable(Routes.CAPTIONS){CaptionsScreen(vm,nav)}
            composable(Routes.RESULT){ResultScreen(nav)}
            composable(Routes.EXPORT){ExportScreen(nav)}
            composable(Routes.LIVE){LiveAlbumScreen(vm,nav)}
            composable(Routes.LIVE_DETAIL){LiveDetailScreen(nav)}
            composable(Routes.COVER){CoverScreen(nav)}
            composable(Routes.TASKS){TaskScreen(vm,nav)}
            composable(Routes.PROFILE){PreferenceScreen(vm,nav)}
            composable(Routes.AI_CHAT){AiChatScreen(nav)}
            composable(Routes.VERSIONS){VersionScreen(vm,nav)}
            composable(Routes.ONBOARDING){AssistantOnboardingScreen(vm,nav)}
            composable(Routes.HIGHLIGHTS){HighlightAggregationScreen(vm,nav)}
            composable(Routes.STUDIO){AiStudioHomeScreen(vm,nav)}
        }
    }
}

fun NavHostController.go(route:String){navigate(route){launchSingleTop=true}}
