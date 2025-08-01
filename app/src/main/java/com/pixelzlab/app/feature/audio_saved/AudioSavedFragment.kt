package com.pixelzlab.app.feature.audio_saved

import android.content.ContentValues
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.material.textview.MaterialTextView
import com.pixelzlab.app.R
import java.io.File
import java.util.Locale
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [AudioSavedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioSavedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var btnPlay: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var tvCurrentTotal: MaterialTextView
    private lateinit var tvFileName: MaterialTextView
    private lateinit var tvFileDetails: MaterialTextView
    private lateinit var btnOpenFile: ImageButton
    private lateinit var btnSetRingtone: ImageButton
    private lateinit var btnSetNotification: ImageButton
    private lateinit var btnShare: ImageButton
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var audioFile: File
    private var isPlaying = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_saved, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AudioSavedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AudioSavedFragment().apply {

            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = arguments?.getString("saved_file_path") ?: return
        audioFile = File(path)

//        val file = File(path)
//        if (!file.exists()) {
//            Log.e("AudioSavedFragment", "File does not exist")
//            Toast.makeText(requireContext(), "Tệp âm thanh không tồn tại", Toast.LENGTH_SHORT).show()
//            return
//        }

        btnPlay = view.findViewById<ImageView>(R.id.btnPlay)
        seekBar = view.findViewById(R.id.seekBar)
        tvCurrentTotal = view.findViewById(R.id.tvDuration)
        tvFileName = view.findViewById(R.id.tvFileName)
        tvFileDetails = view.findViewById(R.id.tvFileDetails)

        btnOpenFile = view.findViewById(R.id.btnOpenFile)
        btnSetRingtone = view.findViewById(R.id.btnSetRingtone)
        btnSetNotification = view.findViewById(R.id.btnSetNotification)
        btnShare = view.findViewById(R.id.btnShare)

        btnOpenFile.setOnClickListener { openFileWith() }
        btnSetRingtone.setOnClickListener { setAsRingtone() }
        btnSetNotification.setOnClickListener { setAsNotification() }
        btnShare.setOnClickListener { shareFile() }

        tvFileName.text = audioFile.name
        val sizeKB = audioFile.length().toDouble() / 1024
        val duration = MediaMetadataRetriever().apply {
            setDataSource(audioFile.absolutePath)
        }.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull() ?: 0L
        val formattedDuration = formatTime(duration.toInt())

        tvFileDetails.text =
            String.format(Locale.getDefault(), "%.1f kB | %s", sizeKB, formattedDuration)
        tvCurrentTotal.text = "00:00 / $formattedDuration"

        isPlaying = false
        btnPlay.setOnClickListener {
            try {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer().apply {
                        setDataSource(audioFile.absolutePath)
                        prepare()
                    }
                    seekBar.max = mediaPlayer?.duration ?: 0
                    updateSeekBar()
                }
                mediaPlayer?.setOnCompletionListener {
                    btnPlay.setImageResource(R.drawable.baseline_play_arrow_24)
                    isPlaying = false
                }

                if (!isPlaying) {
                    mediaPlayer?.start()
                    btnPlay.setImageResource(R.drawable.baseline_pause_24)
                    isPlaying = true
                } else {
                    mediaPlayer?.pause()
                    btnPlay.setImageResource(R.drawable.baseline_play_arrow_24)
                    isPlaying = false
                }

            } catch (e: Exception) {
                Log.d("AudioSavedFragment", "Error playing audio: ${e.message}")
                Toast.makeText(requireContext(), "Không thể phát âm thanh", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    tvCurrentTotal.text = "${formatTime(progress)}/$formattedDuration"
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        val lifecycleOwner = viewLifecycleOwner

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    if (mediaPlayer?.isPlaying == true) {
                        mediaPlayer?.pause()
                        btnPlay.setImageResource(R.drawable.baseline_play_arrow_24)
                        isPlaying = false
                    }
                }

                Lifecycle.Event.ON_RESUME -> {  }

                Lifecycle.Event.ON_DESTROY -> {
                    mediaPlayer?.release()
                    mediaPlayer = null
                }

                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

    }

    private fun updateSeekBar() {
        handler.post(object : Runnable {
            override fun run() {
                mediaPlayer?.let { mp ->
                    if (mp.isPlaying) {
                        val pos = mp.currentPosition
                        seekBar.progress = pos
                        val total = seekBar.max
                        tvCurrentTotal.text = "${formatTime(pos)}/${formatTime(total)}"
                        handler.postDelayed(this, 500)
                    }
                }
            }
        })
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    private fun openFileWith() {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            audioFile
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "audio/*")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        startActivity(Intent.createChooser(intent, "Open audio file with..."))
    }

    private fun setAsRingtone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Toast.makeText(
                requireContext(),
                "Chức năng này không được hỗ trợ từ Android 10+",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DATA, audioFile.absolutePath)
            put(MediaStore.MediaColumns.TITLE, audioFile.nameWithoutExtension)
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/wav")
            put(MediaStore.Audio.Media.IS_RINGTONE, true)
            put(MediaStore.Audio.Media.IS_ALARM, false)
            put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
        }

        val uri = MediaStore.Audio.Media.getContentUriForPath(audioFile.absolutePath)
        requireContext().contentResolver.insert(uri!!, values)

        RingtoneManager.setActualDefaultRingtoneUri(
            requireContext(),
            RingtoneManager.TYPE_RINGTONE,
            Uri.fromFile(audioFile)
        )

        Toast.makeText(requireContext(), "Đã đặt làm nhạc chuông", Toast.LENGTH_SHORT).show()
    }

    private fun setAsNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Toast.makeText(
                requireContext(),
                "Chức năng này không được hỗ trợ từ Android 10+",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DATA, audioFile.absolutePath)
            put(MediaStore.MediaColumns.TITLE, audioFile.nameWithoutExtension)
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/wav")
            put(MediaStore.Audio.Media.IS_RINGTONE, false)
            put(MediaStore.Audio.Media.IS_ALARM, false)
            put(MediaStore.Audio.Media.IS_NOTIFICATION, true)
        }

        val uri = MediaStore.Audio.Media.getContentUriForPath(audioFile.absolutePath)
        requireContext().contentResolver.insert(uri!!, values)

        RingtoneManager.setActualDefaultRingtoneUri(
            requireContext(),
            RingtoneManager.TYPE_NOTIFICATION,
            Uri.fromFile(audioFile)
        )

        Toast.makeText(requireContext(), "Đã đặt làm âm báo", Toast.LENGTH_SHORT).show()
    }

    private fun shareFile() {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            audioFile
        )
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "audio/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        startActivity(Intent.createChooser(shareIntent, "Share audio via"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}