package com.shishkindenis.locationtracker_child;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_child.activities.EmailAuthActivity;

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
public class EmailAuthUiTest {
    @Rule
    public ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Test
    public void signingInFailed() {
        auth.signOut();
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        String email = getRandomEmail();
        String password = getRandomPassword();
        enterEmail(email);
        enterPassword(password);
        onView(withId(R.id.btnLogin)).perform(click());
        TestUtils.sleep();
        onView(withText(R.string.authentication_failed)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

    @Test
    public void repeatedSigningUpFailed() {
        auth.signOut();
        String email = getRandomEmail();
        String password = getRandomPassword();
        auth.createUserWithEmailAndPassword(email, password);
        TestUtils.sleep();
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        enterEmail(email);
        enterPassword(password);
        onView(withId(R.id.btnRegister)).perform(click());
        onView(withText(R.string.signing_up_failed)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

    @Test
    public void instanceOfEmailActivityIsSavedWhenRotating() {
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        enterEmail("sh-kin007@mail.ru");
        emailAuthActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        onView(withId(R.id.etEmail)).check(matches(withText("sh-kin007@mail.ru")));
    }

    @Test
    public void requiredEmailErrorIsDisplayed() {
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Required email")));
    }

    @Test
    public void requiredPasswordErrorIsDisplayed() {
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Required password")));
    }

    @Test
    public void signingUpSuccessful() {
        auth.signOut();
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        String email = getRandomEmail();
        String password = getRandomPassword();
        enterEmail(email);
        enterPassword(password);
        onView(withId(R.id.btnRegister)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("User with email: " + email + " was signed up ")).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

    @Test
    public void signingInSuccessful() {
        auth.signOut();
        String email = getRandomEmail();
        String password = getRandomPassword();
        auth.createUserWithEmailAndPassword(email, password);
        TestUtils.sleep();
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        enterEmail(email);
        enterPassword(password);
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText(R.string.authentication_successful)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

    private void enterEmail(String email) {
        onView(withId(R.id.etEmail)).perform(replaceText(email));
    }

    private void enterPassword(String password) {
        onView(withId(R.id.etPassword)).perform(replaceText(password));
    }

    private String getRandomEmail(){
        return "user" + TestUtils.randomInt() + "@example.com";
    }

    private String getRandomPassword(){
        return "password" + TestUtils.randomInt();
    }
}
