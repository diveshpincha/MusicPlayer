package com.example.musicplayer.playPge

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.R
import com.example.musicplayer.database.SongDataBase
import com.example.musicplayer.databinding.FragmentPlayBinding
import kotlinx.coroutines.Runnable

class PlayFragment : Fragment() {

    private lateinit var viewModel:PlayFragmentViewModel

    var handler = Handler(Looper.getMainLooper())

    lateinit var runnable: java.lang.Runnable

    var user = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlayBinding.inflate(layoutInflater)

        val songIndex = arguments?.let { PlayFragmentArgs.fromBundle(it) }

        //Toast.makeText(this.context,songIndex.toString(), Toast.LENGTH_SHORT).show()

        Log.i("argument",songIndex!!.index.toString())

        val app = requireNotNull(this.activity).application

        val dataSource = SongDataBase.getInstance(app).songDao

        val factory = PlayFragmentFactory(dataSource,app)

        viewModel = ViewModelProvider(this,factory).get(PlayFragmentViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.startMedia(songIndex.index)

        viewModel.imageUri.observe(viewLifecycleOwner , {
                binding.songImage.setImageURI(Uri.parse(it))
            if(binding.songImage.drawable == null ) binding.songImage.setImageResource(R.mipmap.music_icon)
        })

        binding.name.isSelected=true

        binding.lifecycleOwner=this

        binding.previous.setOnClickListener{
            user--
            viewModel.startMedia(songIndex.index+user)
        }

        viewModel.isFavourite.observe(viewLifecycleOwner,{
            if(it){
                binding.imageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
            }else{
                binding.imageButton.setImageResource(R.drawable.ic_baseline_star_24)
            }
        })

        viewModel.maxTime.observe(viewLifecycleOwner,{
            binding.seekBar.max = it
        })

        binding.seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) viewModel.mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.next.setOnClickListener{
            user++
            viewModel.startMedia(songIndex.index+user)
        }

        binding.playPause.setOnClickListener {
            if(viewModel.playingCheck()){
                binding.playPause.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }else{
                binding.playPause.setImageResource(R.drawable.ic_baseline_pause_24)
            }
        }

        runnable = Runnable{
            binding.seekBar.progress = viewModel.mediaPlayer!!.currentPosition
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)

        binding.imageButton.setOnClickListener {
            viewModel.setFav(songIndex.index+user)
        }

        viewModel.isFavourite.observe(viewLifecycleOwner,{
            if(it){
                binding.imageButton.setImageResource(R.drawable.ic_baseline_star_24)
            }
            else{
                binding.imageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
        })

        return binding.root
    }

}