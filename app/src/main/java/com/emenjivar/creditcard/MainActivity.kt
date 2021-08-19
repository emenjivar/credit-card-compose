package com.emenjivar.creditcard

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emenjivar.creditcard.ui.theme.ComposeBasicTheme
import kotlinx.coroutines.launch

val conversation: List<Message> = List(1000) {
    Message(
        author = if (it % 2 == 0) "carlos.menjivar" else "david.melara",
        body = "$it Que ondas man, este es el mensaje $it"
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                Conversation(messages = conversation)
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    val picture = if (msg.author == "carlos.menjivar") R.drawable.me else R.drawable.profile_image
    Row(
        modifier = Modifier.padding(all = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(1.0.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column(
            modifier = Modifier.clickable {
                isExpanded = !isExpanded
            }
        ) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 8.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)

        }
    }
}

@Composable
fun Counter(@DrawableRes icon: Int, count: Int, updateCount: (Int) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        FloatingActionButton(onClick = { updateCount(count + 1) }) {
            Icon(painter = painterResource(icon), contentDescription = null)
        }
    }

}

@Composable
fun CustomTopBar(
    title: String,
    subtitle: String,
    actionIcon: Int = R.drawable.ic_baseline_tag_faces_24
) {
    TopAppBar(
        title = {
            Column {
                Text(text = title)
                Text(subtitle, fontSize = 12.sp)
            }
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(painter = painterResource(actionIcon), contentDescription = null)
            }
        }
    )
}

@Composable
fun Conversation(messages: List<Message>) {
    val counterState = remember { mutableStateOf(0) }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Slack group - applaudo",
                subtitle = "Created on december 23, 2021",
                actionIcon = R.drawable.ic_baseline_search_24
            )
        },
        floatingActionButton = {
            Row {
                Counter(R.drawable.ic_baseline_arrow_upward_24, counterState.value) {
                    counterState.value = it
                    coroutineScope.launch {
                        scrollState.scrollToItem(0)
                    }
                }
                Counter(R.drawable.ic_baseline_arrow_downward_24, counterState.value) {
                    counterState.value = it
                    coroutineScope.launch {
                        scrollState.scrollToItem(conversation.size - 1)
                    }
                }
            }
        }
    ) {


        LazyColumn(state = scrollState) {
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }

}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    ComposeBasicTheme {
        content()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark mode"
)
@Composable
fun PreviewMessageCard() {
    MyApp {
        Conversation(messages = conversation)
    }
}

