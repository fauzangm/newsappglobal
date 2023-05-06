package com.eduside.alfagift.utils

import androidx.test.espresso.IdlingResource

class DelayedResource(private val delay: Long) : IdlingResource {
    private var callback: IdlingResource.ResourceCallback? = null
    private val endTime: Long = System.currentTimeMillis() + delay

    override fun getName(): String {
        return javaClass.simpleName
    }

    override fun isIdleNow(): Boolean {
        val idle = System.currentTimeMillis() >= endTime
        if (idle && callback != null) {
            callback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }
}