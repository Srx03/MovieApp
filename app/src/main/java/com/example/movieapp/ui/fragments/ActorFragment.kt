package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.adapter.actor.ActorKnowFromAdapter
import com.example.movieapp.databinding.FragmentActorBinding
import com.example.movieapp.ui.viewmodel.ActorViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentActorBinding? = null
    private val binding get() = _binding!!
    private lateinit var actorKnowFromAdapter: ActorKnowFromAdapter
    private val  viewModel: ActorViewModel by activityViewModels()

    private lateinit var id: String
    private var isClicked: Boolean = false

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    _binding = FragmentActorBinding.inflate(inflater, container, false)
    return binding.root


}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getOnClickData()

        viewModel.actorCreditsList.observe(viewLifecycleOwner) {
            actorKnowFromAdapter.setList(it.cast)
        }

        viewModel.actorDetailList.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + it.profile_path)
                .into(binding.imgMovie)
            binding.tvName.text = it.name
            binding.tvBirthday.text = it.birthday
            binding.tvPlace.text = it.place_of_birth
            binding.tvBiography.text = it.biography
        }
        setUpRecyclerView()
        setUpClickListeners()

    }



    private fun getOnClickData() {
        val args = this.arguments
        id = args?.getString("id").toString()
        viewModel.getActorCredits(id)
        viewModel.getActorDetail(id)
    }

    private fun setUpRecyclerView(){
        actorKnowFromAdapter = ActorKnowFromAdapter()

        binding.knowFromRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = actorKnowFromAdapter
        }

    }


    private fun setUpClickListeners()  {

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}