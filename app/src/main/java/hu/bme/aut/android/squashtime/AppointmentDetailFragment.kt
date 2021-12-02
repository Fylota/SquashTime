package hu.bme.aut.android.squashtime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.squashtime.adapter.AppointmentsAdapter
import hu.bme.aut.android.squashtime.data.Appointment
import hu.bme.aut.android.squashtime.databinding.FragmentAppointmentDetailBinding
import java.util.*

class AppointmentDetailFragment : Fragment(), AppointmentsAdapter.AppointmentItemClickListener {

    private var selectedAppointment: Appointment? = null

    private lateinit var _binding: FragmentAppointmentDetailBinding
    private val binding get() = _binding

    companion object {

        private const val KEY_APPOINTMENT_DATE = "KEY_APPOINTMENT_DATE"

        fun newInstance(appointmentDate: Date): AppointmentDetailFragment {
            val args = Bundle()
            args.putString(KEY_APPOINTMENT_DATE, appointmentDate.toString())

            val result = AppointmentDetailFragment()
            result.arguments = args
            return result
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            selectedAppointment = Appointment(
                uid = "",
                date = Date(),
                available = true,
                booked_by = null,
                comment = ""
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAppointmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appointmentDetail.text = selectedAppointment?.date.toString()
    }

    override fun onItemClick(appointment: Appointment) {
        val fragment = newInstance(appointment.date)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_content_home, fragment)
            .commit()
    }

}
