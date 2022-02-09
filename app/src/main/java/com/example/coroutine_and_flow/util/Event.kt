package com.example.coroutine_and_flow.util

import androidx.lifecycle.Observer


/**
 * A lifecycle-aware observable that sends only new updates after
 * subscription, used for events like navigation. This avoids a
 * common problem with events like on configuration change.
 *
 * An [Observer] for [SingleEvent]s, simplifying the pattern of
 * checking if the [SingleEvent]'s content has already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s
 * contents has not been handled.
 */
open class Event<T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}