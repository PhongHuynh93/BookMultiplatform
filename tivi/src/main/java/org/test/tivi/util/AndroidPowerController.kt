package org.test.tivi.util

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.PowerManager
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import org.test.base.settings.TiviPreferences
import org.test.base.util.PowerController
import org.test.base.util.SaveData
import org.test.base.util.SaveDataReason

internal class AndroidPowerController constructor(
    private val context: Context,
    private val preferences: TiviPreferences
) : PowerController {

    private val powerManager: PowerManager = context.getSystemService()!!
    private val connectivityManager: ConnectivityManager = context.getSystemService()!!

    override fun observeShouldSaveData(ignorePreference: Boolean): Flow<SaveData> {
        return merge(
            context.flowBroadcasts(IntentFilter(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)),
            context.flowBroadcasts(IntentFilter(ConnectivityManager.ACTION_RESTRICT_BACKGROUND_CHANGED))
        ).map { _ ->
            shouldSaveData()
        }.onStart {
            emit(shouldSaveData())
        }
    }

    override fun shouldSaveData(): SaveData = when {
        preferences.useLessData -> {
            SaveData.Enabled(SaveDataReason.PREFERENCE)
        }
        powerManager.isPowerSaveMode -> {
            SaveData.Enabled(SaveDataReason.SYSTEM_POWER_SAVER)
        }
        Build.VERSION.SDK_INT >= 24 && isBackgroundDataRestricted() -> {
            SaveData.Enabled(SaveDataReason.SYSTEM_DATA_SAVER)
        }
        else -> SaveData.Disabled
    }

    @RequiresApi(24)
    private fun isBackgroundDataRestricted(): Boolean {
        return connectivityManager.restrictBackgroundStatus ==
            ConnectivityManager.RESTRICT_BACKGROUND_STATUS_ENABLED
    }
}
