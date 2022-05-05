package org.tivi.sharedTivi.model

enum class ShowStatus(val storageKey: String) {
    ENDED("ended"),
    RETURNING("returning"),
    CANCELED("canceled"),
    IN_PRODUCTION("inproduction");
}
