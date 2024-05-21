package com.example.simplegallery.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplegallery.core.Constants
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainRoute(viewModel: MainViewModel = hiltViewModel()) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = Constants.PERMISSIONS
    )


    LaunchedEffect(permissionState.shouldShowRationale, permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            viewModel.start()
        } else if (!permissionState.allPermissionsGranted && permissionState.shouldShowRationale) {
            // Show rationale if needed
        } else {
            permissionState.launchMultiplePermissionRequest()
        }
    }
    val state = viewModel.state.collectAsState()


    MainScreen(state, viewModel.player, viewModel::play)
}