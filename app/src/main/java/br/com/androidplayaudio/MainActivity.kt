package br.com.androidplayaudio

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.androidplayaudio.databinding.ActivityMainBinding
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.RawResourceDataSource

class MainActivity : AppCompatActivity(), Player.Listener {

    private lateinit var binding: ActivityMainBinding
    private val exoPlayer by lazy { ExoPlayer.Builder(this).build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTransparentStatusBar()

        setupMediaAudio()
        loadImageGif()
    }

    /**
     * Set up audio to exo player library
     */
    private fun setupMediaAudio() {
        val mediaItem = MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(R.raw.file_example))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        binding.stylePLayer.player = exoPlayer
    }

    /**
     * Load background gif of the screen
     */
    private fun loadImageGif() {
        val imageLoader = ImageLoader.Builder(this)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .crossfade(enable = true)
            .build()

        binding.videoViewFullscreen.load(data = R.drawable.smooth_ocean, imageLoader = imageLoader)
    }
}