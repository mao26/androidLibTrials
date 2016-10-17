package com.example.nathanschwan.gyhtest;

/**
 * Created by nathanschwan on 10/17/16.
 */


import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anything;

import android.content.Intent;
import android.app.Instrumentation.ActivityResult;
import android.app.Activity;

//testing adding uname functionality to list, must uncomment SIMPLE1 in LogIn.java
//testing SignUp intent trigger -- TRIGGER SIGNUP in LogIn.java

@RunWith(AndroidJUnit4.class)
public class LogInActivityEspressoTest {

//    @Rule
//    public ActivityTestRule<LogIn> mActivityRule =
//            new ActivityTestRule<>(LogIn.class);
//    @Test
//    public void ensureTextChangesWork() {
//        // Type text and then press the button.
//        onView(withId(R.id.uname))
//                .perform(typeText("HELLO"), closeSoftKeyboard());
//
//        onView(withId(R.id.submit)).perform(click());
//
//        // Check that the text was changed.
//        onData(anything())
//                .inAdapterView(withId(R.id.unamelist))
//                .atPosition(0)
//                .check(matches(withText("HELLO")));
//    }

    //used with Espresso-Intents a Mockito like extension of Espresso.
    @Rule
    public IntentsTestRule<LogIn> mIntentsRule =
            new IntentsTestRule<>(LogIn.class);

//    @Test
//    public void testSignUpIntent(){
//
//        //Testing that SignUp received an Intent.
//
//        onView(withId(R.id.submit)).perform(click());
//
//        intended(allOf(hasComponent(hasShortClassName(".SignUp"))));
//    }

    @Test
    public void testActivityResult(){
        //mock result
        Intent result = new Intent();
        result.putExtra("uname", "uname");
        result.putExtra("pass", "pass1");
        ActivityResult receiveResult = new ActivityResult(Activity.RESULT_OK, result);

        //catches any intents seen outgoing.
        intending(anyIntent()).respondWith(receiveResult);

        onView(withId(R.id.submit)).perform(click());

        //LogIn.java on click TRIGGERSIGNUP stubbed with mock data. onActivityResult case 1.
        onView(withId(R.id.uname))
                .check(matches(withText("uname")));

    }
}
