package org.test.common_model

sealed class LoadingScreen {
    data class Data<T>(
        val data: List<T> = emptyList(),
        val isRefresh: Boolean = false,
        val isEndPage: Boolean = false,
        val errorMessage: String? = null
    ) : LoadingScreen() {
        override fun toString(): String {
            return "data=${data.size} isRefresh=$isRefresh isEndPage=$isEndPage errorMessage=$errorMessage"
        }
    }

    object Loading : LoadingScreen()
    data class Error(val errorMessage: String) : LoadingScreen()
    data class NoData(val message: String) : LoadingScreen()
}
