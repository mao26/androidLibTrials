package com.example.nathanschwan.gyhtest;

/**
 * Created by nathanschwan on 10/17/16.
 */


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anything;

//testing adding uname functionality to list, must uncomment SIMPLE1 in LogIn.java

@RunWith(AndroidJUnit4.class)
public class LogInActivityEspressoTest {

    @Rule
    public ActivityTestRule<LogIn> mActivityRule =
            new ActivityTestRule<>(LogIn.class);
    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.uname))
                .perform(typeText("HELLO"), closeSoftKeyboard());

        onView(withId(R.id.submit)).perform(click());

        // Check that the text was changed.
        onData(anything())
                .inAdapterView(withId(R.id.unamelist))
                .atPosition(0)
                .check(matches(withText("HELLO")));
    }


}
