package com.shishkindenis.locationtracker_child;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_child.activities.MainActivity;
import com.shishkindenis.locationtracker_child.activities.PhoneAuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhoneAuthUiTest {
    @Rule
    public ActivityTestRule<PhoneAuthActivity> phoneAuthActivityActivityTestRule = new ActivityTestRule<>(PhoneAuthActivity.class);
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Test
    public void instanceOfPhoneActivityIsSavedWhenRotating() {
        PhoneAuthActivity phoneAuthActivity = phoneAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.etPhoneNumber)).perform(replaceText("+79998887766"));
        phoneAuthActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        onView(withId(R.id.etPhoneNumber)).check(matches(withText("+79998887766")));
    }

    @Test
    public void invalidPhoneNumberErrorIsDisplayed() {
        PhoneAuthActivity phoneAuthActivity = phoneAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnRequestCode)).perform(click());
        onView(withId(R.id.etPhoneNumber)).check(matches(hasErrorText("Invalid phone number")));
    }

    @Test
    public void invalidCodeErrorIsDisplayed() {
        PhoneAuthActivity phoneAuthActivity = phoneAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnRequestCode)).perform(click());
        onView(withId(R.id.etVerificationCode)).check(matches(hasErrorText("Invalid code")));
    }

    @Test
    public void phoneAuthenticationSuccessful() {
        auth.signOut();
        String phoneNumber = "+75558780743";
        String verificationCode = "123007";
        ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
        MainActivity mainActivity = mainActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnPhone)).perform(click());
        onView(withId(R.id.etPhoneNumber)).perform(replaceText(phoneNumber));
        onView(withId(R.id.btnRequestCode)).perform(click());
        TestUtils.sleep();
        onView(withId(R.id.etVerificationCode)).perform(replaceText(verificationCode));
        onView(withId(R.id.btnVerifyCode)).perform(click());
        onView(withText(R.string.authentication_successful)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

    @Test
    public void phoneAuthenticationFailed() {
        auth.signOut();
        String phoneNumber = "+75558780743";
        String verificationCode = "000000";
        ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
        MainActivity mainActivity = mainActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnPhone)).perform(click());
        onView(withId(R.id.etPhoneNumber)).perform(replaceText(phoneNumber));
        onView(withId(R.id.btnRequestCode)).perform(click());
        TestUtils.sleep();
        onView(withId(R.id.etVerificationCode)).perform(replaceText(verificationCode));
        onView(withId(R.id.btnVerifyCode)).perform(click());
        onView(withText(R.string.authentication_failed)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }
}
