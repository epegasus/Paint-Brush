package dev.pegasus.paintbrush

import android.graphics.Color
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider
import com.raed.rasmview.brushtool.data.Brush
import com.raed.rasmview.brushtool.data.BrushesRepository
import dev.pegasus.paintbrush.databinding.ActivityMainBinding
import dev.pegasus.paintbrush.utils.ToolUtils.convertDrawableToBitmap

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val rasmContext by lazy { binding.rvBrushMain.rasmContext }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBrushView()

        binding.sliderMain.addOnChangeListener { _: Slider, value: Float, _: Boolean ->
            binding.tvValueMain.text = value.toInt().toString()
            rasmContext.brushConfig.size = value / 100
        }
        binding.ifvUndoMain.setOnClickListener {
            with(rasmContext.state) {
                if (canCallUndo())
                    undo()
            }
        }
        binding.ifvRedoMain.setOnClickListener {
            with(rasmContext.state) {
                if (canCallRedo())
                    redo()
            }
        }
    }

    private fun setBrushView() {
        val drawable = ContextCompat.getDrawable(this, R.drawable.img_dummy)
        rasmContext.setRasm(convertDrawableToBitmap(drawable!!))

        rasmContext.brushConfig = BrushesRepository(resources).get(Brush.Pen)
        rasmContext.brushColor = Color.RED
        rasmContext.rotationEnabled = false

        binding.rvBrushMain.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.rvBrushMain.viewTreeObserver.removeOnGlobalLayoutListener(this)
                binding.rvBrushMain.resetTransformation()
            }
        })
    }
}