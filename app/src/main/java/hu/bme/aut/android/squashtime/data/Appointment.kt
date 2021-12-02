package hu.bme.aut.android.squashtime.data

import com.google.firebase.firestore.auth.User
import java.util.*

data class Appointment(
    val uid: String? = null,
    val date: Date = Date(),
    val available: Boolean? = true,
    val booked_by: User? = null,
    val comment: String? = null
)