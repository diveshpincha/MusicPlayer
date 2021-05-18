package com.example.musicplayer.mainScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.SongAdapter
import com.example.musicplayer.database.SongDataBase
import com.example.musicplayer.databinding.MainFragmentBinding
import com.mtechviral.mplaylib.MusicFinder

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val app = requireNotNull(this.activity).application
        val dataSource = SongDataBase.getInstance(app).songDao
        val factory = MainViewModelFactory(dataSource , app)

        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)

        val binding = MainFragmentBinding.inflate(layoutInflater)

        binding.data=viewModel

        binding.lifecycleOwner=this

        val adapter = SongAdapter(SongAdapter.OnClickListener{
            viewModel.getPlaylistAll()
            this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToPlayFragment(it.id.toInt()-1))
            Log.i("id",it.id.toInt().toString())
        })

        viewModel.playList.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })

        binding.songsList.adapter = adapter

        binding.button2.setOnClickListener{
            it.visibility=View.INVISIBLE
            binding.button.visibility=View.VISIBLE
            viewModel.getPlaylistFav()
        }

        binding.button.setOnClickListener{
            it.visibility=View.INVISIBLE
            binding.button2.visibility=View.VISIBLE
            viewModel.getPlaylistAll()
        }

        val songFinder = MusicFinder(app.contentResolver)
        songFinder.prepare()


        return binding.root
    }
}