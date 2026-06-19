package com.sbz.devfolio.features.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbz.devfolio.ui.theme.DevFolioTheme
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbz.devfolio.R
import com.sbz.devfolio.ui.theme.PrimaryColor
import kotlin.random.Random
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    onSplashComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val app = context.applicationContext as com.sbz.devfolio.DevFolioApplication
    val viewModel: SplashViewModel = viewModel(
        factory = SplashViewModel.provideFactory(app.container.getPortfolioUseCase)
    )
    val uiState by viewModel.uiState.collectAsState()

    SplashScreen(uiState = uiState, onSplashComplete = onSplashComplete)
}

@Composable
fun SplashScreen(uiState: SplashUiState, onSplashComplete: () -> Unit) {
    var isExiting by remember { mutableStateOf(false) }

    val exitScale by
            animateFloatAsState(
                    targetValue = if (isExiting) 1.2f else 1f,
                    animationSpec = tween(1000, easing = FastOutSlowInEasing),
                    label = "ExitScale"
            )
    val exitAlpha by
            animateFloatAsState(
                    targetValue = if (isExiting) 0f else 1f,
                    animationSpec = tween(800, easing = FastOutSlowInEasing),
                    label = "ExitAlpha"
            )

    LaunchedEffect(uiState.isComplete) {
        if (uiState.isComplete) {
            isExiting = true
            delay(1000)
            onSplashComplete()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // Blurred Background Orbs
        Box(
                modifier =
                        Modifier.offset(x = (-50).dp, y = 100.dp)
                                .size(250.dp)
                                .background(Color(0xFF66fea2).copy(alpha = 0.4f), CircleShape)
                                .blur(120.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
        )
        Box(
                modifier =
                        Modifier.align(Alignment.BottomEnd)
                                .offset(x = 50.dp, y = (-100).dp)
                                .size(300.dp)
                                .background(Color(0xFFa4c7de).copy(alpha = 0.4f), CircleShape)
                                .blur(150.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
        )

        // Particle System
        ParticleCanvas()

        // Content
        Box(
                modifier = Modifier.fillMaxSize().scale(exitScale).alpha(exitAlpha),
                contentAlignment = Alignment.Center
        ) {
            Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 80.dp)
            ) {
                // Avatar
                AvatarSection()

                Spacer(modifier = Modifier.height(32.dp))

                // Typography
                Text(
                        text = "Shabaj Ansari",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                            text = "ANDROID DEVELOPER",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Bold
                    )
                }
            }

            // Progress Bar (Bottom)
            Column(
                    modifier =
                            Modifier.align(Alignment.BottomCenter)
                                    .fillMaxWidth(0.7f)
                                    .padding(bottom = 48.dp),
            ) {
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                            text = uiState.statusText,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                    )
                    Text(
                            text = "${uiState.progress.toInt()}%",
                            color = PrimaryColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .height(8.dp)
                                        .background(Color(0xFFf0f4f8), CircleShape)
                                        .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                ) {
                    Box(
                            modifier =
                                    Modifier.fillMaxWidth(uiState.progress / 100f)
                                            .height(8.dp)
                                            .background(PrimaryColor, CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun AvatarSection() {
    val infiniteTransition = rememberInfiniteTransition(label = "avatar")
    val floatAnim by
            infiniteTransition.animateFloat(
                    initialValue = -10f,
                    targetValue = 10f,
                    animationSpec =
                            infiniteRepeatable(
                                    animation = tween(2000, easing = LinearEasing),
                                    repeatMode = RepeatMode.Reverse
                            ),
                    label = "float"
            )

    Box(
            modifier =
                    Modifier.size(300.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFf0f4f8))
                            .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.BottomCenter
    ) {
        Image(
                painter = painterResource(R.drawable.ic_avatar_with_logos),
                contentDescription = "Avatar",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize().padding(24.dp).offset(y = floatAnim.dp)
        )
    }
}

@Composable
fun ParticleCanvas() {
    val particles = remember { List(35) { Particle() } }

    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val time by
            infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1000f,
                    animationSpec = infiniteRepeatable(tween(100000, easing = LinearEasing)),
                    label = "time"
            )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        particles.forEach { p ->
            p.update(width, height, time)
            drawCircle(
                    color = Color(0xFF006d3b).copy(alpha = p.alpha),
                    radius = p.size,
                    center = Offset(p.x, p.y)
            )
        }
    }
}

class Particle {
    var x = 0f
    var y = 0f
    var size = 0f
    var speedX = 0f
    var speedY = 0f
    var alpha = 0f
    var isInitialized = false

    fun reset(width: Float, height: Float) {
        x = Random.nextFloat() * width
        y = Random.nextFloat() * height
        size = Random.nextFloat() * 3f + 1f
        speedX = Random.nextFloat() * 0.4f - 0.2f
        speedY = Random.nextFloat() * 0.4f - 0.2f
        alpha = Random.nextFloat() * 0.3f + 0.1f
    }

    fun update(width: Float, height: Float, time: Float) {
        if (!isInitialized && width > 0f && height > 0f) {
            reset(width, height)
            isInitialized = true
        }
        if (!isInitialized) return

        x += speedX
        y += speedY
        if (x < 0 || x > width || y < 0 || y > height) {
            reset(width, height)
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(
        showBackground = true,
        uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
        name = "Dark Mode"
)
@Composable
fun SplashScreenPreview() {
    DevFolioTheme {
        SplashScreen(
                uiState = SplashUiState(progress = 75f, statusText = "Building Modules..."),
                onSplashComplete = {}
        )
    }
}
