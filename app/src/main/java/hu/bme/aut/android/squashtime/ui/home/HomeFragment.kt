package hu.bme.aut.android.squashtime.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.squashtime.adapter.AppointmentsAdapter
import hu.bme.aut.android.squashtime.data.Appointment
import hu.bme.aut.android.squashtime.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var appointmentsAdapter: AppointmentsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        appointmentsAdapter = AppointmentsAdapter(this.context)
        binding.rvMyApps.adapter = appointmentsAdapter
        binding.rvMyApps.layoutManager = LinearLayoutManager(this.context).apply {
        }
        initAppointmentsListener()
        return root
    }

    private fun initAppointmentsListener() {
        val db = Firebase.firestore
        db.collection("appointments")
            .whereNotEqualTo("booked_by", null)
            .whereEqualTo("booked_by", FirebaseAuth.getInstance().currentUser?.email)
            .get()
            .addOnSuccessListener { snapshots ->
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> appointmentsAdapter.addAppointment(dc.document.toObject<Appointment>())
                        DocumentChange.Type.MODIFIED -> Toast.makeText(this.context, dc.document.data.toString(), Toast.LENGTH_SHORT).show()
                        DocumentChange.Type.REMOVED -> Toast.makeText(this.context, dc.document.data.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}