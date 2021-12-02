package hu.bme.aut.android.squashtime.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is booking Fragment"
    }
    val text: LiveData<String> = _text
}