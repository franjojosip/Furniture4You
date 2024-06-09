package com.fjjukic.furniture4you.ui.main.notification

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.NotificationModel
import com.fjjukic.furniture4you.ui.components.Toolbar
import com.fjjukic.furniture4you.ui.home.BottomBarNavigation
import com.fjjukic.furniture4you.ui.navigation.Screens
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily

@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(onSearchClick = {}, onNavigateToBottomBarRoute = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationScreen(
    onSearchClick: () -> Unit,
    onNavigateToBottomBarRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val notifications by viewModel.notifications.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )
    val showMessage by viewModel.showMessage.collectAsState()

    LaunchedEffect(showMessage) {
        showMessage?.toastResId?.let {
            Toast.makeText(context, context.getString(it), Toast.LENGTH_SHORT).show()
            viewModel.onMessageShown()
        }
    }

    Scaffold(
        modifier,
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_notification),
                startIconResId = R.drawable.ic_search,
                onStartActionClick = onSearchClick,
                onEndActionClick = {},
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        bottomBar = {
            BottomBarNavigation(
                currentRoute = Screens.HomeSections.Notification.route,
                onNavigateToBottomBarRoute = onNavigateToBottomBarRoute
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 14.dp)
                    .background(colorResource(id = R.color.color_white)),
                contentPadding = PaddingValues(
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                itemsIndexed(notifications) { index, notification ->
                    key(notification.id) {
                        SwipeBox(
                            onDelete = { viewModel.removeNotification(index) },
                            onEdit = { viewModel.archiveNotification(index) },
                            modifier = Modifier.animateItemPlacement()
                        ) {
                            NotificationItem(
                                notification = notification,
                                modifier = Modifier
                            )
                            if ((index != notifications.size - 1) && notification.tag == null) {
                                HorizontalDivider(
                                    color = colorResource(id = R.color.color_tinted_white),
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    val icon: ImageVector
    val contentAlignment: Alignment
    val bgColor: Color

    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
            icon = Icons.Outlined.Delete
            contentAlignment = Alignment.CenterEnd
            bgColor = colorResource(id = R.color.bg_swipe_delete)
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            icon = ImageVector.vectorResource(id = R.drawable.ic_archive)
            contentAlignment = Alignment.CenterStart
            bgColor = colorResource(id = R.color.bg_swipe_archive)
        }

        SwipeToDismissBoxValue.Settled -> {
            icon = Icons.Outlined.Delete
            contentAlignment = Alignment.CenterEnd
            bgColor = colorResource(id = R.color.bg_swipe_delete)
        }
    }

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        backgroundContent = {
            Box(
                contentAlignment = contentAlignment,
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgColor)
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = icon,
                    contentDescription = null
                )
            }
        }
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            onDelete()
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(swipeState) {
                onEdit()
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }

        SwipeToDismissBoxValue.Settled -> {}
    }
}

@Preview
@Composable
fun NotificationItemPreviewNew() {
    NotificationItem(notification = MockRepository.getNotifications().first())
}

@Composable
fun NotificationItem(
    notification: NotificationModel,
    modifier: Modifier = Modifier
) {
    val bgColor =
        if (notification.tag != null) colorResource(id = R.color.bg_notification_new)
        else colorResource(id = R.color.bg_notification_old)

    Box {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(bgColor)
                .padding(vertical = 12.dp, horizontal = 20.dp)
                .height(70.dp)
        ) {
            if (!notification.hideImage) {
                Image(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    painter = painterResource(id = R.drawable.img_minimal_stand),
                    contentDescription = stringResource(R.string.content_desc_product),
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = notification.title,
                    fontFamily = nunitoSansFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    lineHeight = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.color_medium_gray),
                    modifier = Modifier.wrapContentHeight()
                )
                Text(
                    text = notification.description,
                    fontFamily = nunitoSansFamily,
                    fontSize = 10.sp,
                    lineHeight = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    color = colorResource(id = R.color.color_medium_gray),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (notification.tag != null) {
            Text(
                text = notification.tag.tag,
                fontFamily = nunitoSansFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(id = notification.tag.color),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 1.dp)
            )
        }
    }
}