import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adsdk.AdManager
import com.example.adsdk.AdState
import com.example.galleryapp.R
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun GalleryScreen(adManager: AdManager?) {
    val nativeAdState by (adManager?.getNativeAdState() ?: emptyFlow()).collectAsState(initial = AdState.Loading)

    val imageList = listOf(
        R.drawable.ab1_inversions,
        R.drawable.ab2_quick_yoga,
        R.drawable.ab3_stretching,
        R.drawable.ab4_tabata,
        R.drawable.ab5_hiit,
        R.drawable.ab6_pre_natal_yoga
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        itemsIndexed(imageList) { index, imageRes ->
            if (adManager != null &&
                (index == 1 || index == 3)
                ) {

                when (nativeAdState) {
                    is AdState.Loaded -> Text("Native Ad here")
                    is AdState.Failed -> Text("Ad failed to load")
                    else -> Text("Loading ad...")
                }
            } else {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Gallery Image $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreen() {
    GalleryScreen(adManager = null) // Теперь AdManager выключен в Preview
}
