package com.eduside.alfagift


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.eduside.alfagift.ui.chanelBerita.ChannelActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.eduside.alfagift.utils.DelayedResource

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{


    @Before
    fun setup(){
        ActivityScenario.launch(ChannelActivity::class.java)
    }

    @Test
    fun mainTest() {
        onView(withId(R.id.rvListBerita)).perform(click())
        onView(withId(R.id.rvListBerita)).perform(click())
        onView(withId(R.id.etLinkUrl)).perform(click())
        val resource = DelayedResource(10000)
        Espresso.registerIdlingResources(resource)
        onView(withId(R.id.webView)).perform(click())

    }




}