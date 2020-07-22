package com.mohamed.halim.essa.flashcards.preferencescreen

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.timePicker
import com.mohamed.halim.essa.flashcards.R
import java.text.SimpleDateFormat
import java.util.*

class PreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey)

        if (preferenceScreen.sharedPreferences.getBoolean(
                getString(R.string.pref_practice_reminder_key),
                false
            )
        ) {
            enablePracticeReminder()
        } else {
            disablePracticeReminder()
        }
    }

    private fun showTimePickerDialog(preference: Preference): Boolean {
        MaterialDialog(requireContext()).show {
            timePicker(
                show24HoursView = false,
                currentTime = Calendar.getInstance()
            ) { dialog, datetime ->
                changeReminderPreference(preference, datetime.timeInMillis)
                dismissAlarm(requireContext())
                setAlarm(requireContext(), datetime)
            }
        }
        return true
    }

    private fun formatTime(time: Long): String {
        val dateFormat = SimpleDateFormat("hh:mm a")
        return dateFormat.format(Date(time))
    }

    private fun changeReminderPreference(
        preference: Preference,
        time: Long
    ) {
        preferenceScreen.sharedPreferences.edit().putLong(preference.key, time).apply()
    }

    private fun setPreferenceSummary(preference: Preference?, value: String?) {
        preference?.summary = value
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == getString(R.string.pref_reminder_time_key)) {
            val preference: Preference? = findPreference(key)
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 10)
            val value = sharedPreferences?.getLong(key, calendar.timeInMillis)!!
            setPreferenceSummary(preference, formatTime(value))

        } else if (key == getString(R.string.pref_practice_reminder_key)) {
            val value = sharedPreferences?.getBoolean(key, false)
            if (value!!) {
                enablePracticeReminder()
            } else {
                disablePracticeReminder()
            }
        }
    }

    private fun disablePracticeReminder() {
        val reminderPref =
            findPreference<Preference>(getString(R.string.pref_reminder_time_key))
        reminderPref?.isEnabled = false
        dismissAlarm(requireContext())
    }

    private fun enablePracticeReminder() {
        val reminderPref =
            findPreference<Preference>(getString(R.string.pref_reminder_time_key))
        reminderPref?.isEnabled = true
        reminderPref?.setOnPreferenceClickListener {
            showTimePickerDialog(it)
        }
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 10)
        val reminderPrefValue =
            preferenceScreen.sharedPreferences.getLong(
                getString(R.string.pref_reminder_time_key),
                calendar.timeInMillis
            )
        calendar.timeInMillis = reminderPrefValue
        setAlarm(requireContext(), calendar)
        setPreferenceSummary(reminderPref, formatTime(reminderPrefValue))
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


}