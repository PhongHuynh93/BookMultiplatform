package org.test.base.util

import kotlinx.coroutines.flow.Flow

interface PowerController {
    fun observeShouldSaveData(ignorePreference: Boolean): Flow<SaveData>
    fun shouldSaveData(): SaveData
}

sealed class SaveData {
    object Disabled : SaveData()
    data class Enabled(val reason: SaveDataReason) : SaveData()
}

enum class SaveDataReason {
    PREFERENCE, SYSTEM_DATA_SAVER, SYSTEM_POWER_SAVER
}