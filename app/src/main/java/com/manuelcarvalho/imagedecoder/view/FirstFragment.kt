package com.manuelcarvalho.imagedecoder.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manuelcarvalho.imagedecoder.R
import com.manuelcarvalho.imagedecoder.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_first.*


private const val TAG = "FirstFragment"

class FirstFragment : Fragment() {

    //var emailString = "picture DB "
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_first, container, false)
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //var screenWidth = imageView.measuredWidth
        progressBar.isVisible = false

        progressBar.max = 200

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        seekBar.progress = 50
        txt_heading.text = "Progress:${seekBar.progress}"

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txt_heading.text = "Progress : $progress"
                viewModel.seekBarProgress.value = progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })



        observeViewModel()
    }



    fun observeViewModel() {

        viewModel.displayProgress.observe(viewLifecycleOwner, Observer { display ->
            display?.let {
                progressBar.isVisible = display

            }
        })
    }


}