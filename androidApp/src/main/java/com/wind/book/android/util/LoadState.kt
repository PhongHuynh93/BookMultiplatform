package com.wind.book.android.util

sealed class LoadState(
    open val endOfPaginationReached: Boolean,
    open val isEmpty: Boolean
) {

    /**
     * Indicates the [PagingData] is not currently loading, and no error currently observed.
     *
     * @param endOfPaginationReached `false` if there is more data to load in the [LoadType] this
     * [LoadState] is associated with, `true` otherwise. This parameter informs [Pager] if it
     * should continue to make requests for additional data in this direction or if it should
     * halt as the end of the dataset has been reached.
     */
    abstract class NotLoading(
        endOfPaginationReached: Boolean,
        isEmpty: Boolean
    ) : LoadState(endOfPaginationReached, isEmpty) {

        data class Complete(override val isEmpty: Boolean, override val endOfPaginationReached: Boolean = true) :
            NotLoading(endOfPaginationReached = endOfPaginationReached, isEmpty = isEmpty)

        data class Incomplete(override val isEmpty: Boolean, override val endOfPaginationReached: Boolean = false) :
            NotLoading(endOfPaginationReached = endOfPaginationReached, isEmpty = isEmpty)
    }

    /**
     * Loading is in progress.
     */
    data class Loading(override val isEmpty: Boolean) : LoadState(false, isEmpty)

    /**
     * Loading hit an error.
     *
     * @param error [Throwable] that caused the load operation to generate this error state.
     *
     * @see androidx.paging.PagedList.retry
     */
    data class Error(val error: Throwable, override val isEmpty: Boolean) : LoadState(false, isEmpty)
}
