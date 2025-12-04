package udemy.android.kidd.view.usage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import udemy.android.kidd.R
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun UsageTitle(viewModel: UsageUserViewModel = viewModel()) {

    val user by viewModel.user.collectAsState()

    Log.d("UsageTitle", "heros: $user")

    LaunchedEffect(Unit) {
        viewModel.loadUsageHero()
    }

    Log.d("UsageTitle", "heros: $user")


    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(id = R.color.testColor)
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val spacerWidth = screenWidth * 0.1f   // 屏幕宽度的 10%

            Spacer(Modifier.width(spacerWidth))

            // ⭐ 头像固定大小，不要 weight
            Image(
                painter = painterResource(id = R.drawable.tiga),
                contentDescription = "迪迦",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // 图标和文字之间固定 16dp 空隙
            Spacer(Modifier.width(16.dp))

            // ⭐ Column 使用 weight 占剩余空间
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text("Id: ${user?.id}", fontSize = 16.sp, color = colorResource(R.color.black))
                Text("Hero: ${user?.name}", fontSize = 16.sp, color = colorResource(R.color.black))
                Text("Name: ${user?.lastName}", fontSize = 16.sp, color = colorResource(R.color.black))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ViewUsageTitle() {
    Surface(modifier = Modifier.fillMaxSize()) {
        UsageTitle()
    }
}