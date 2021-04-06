package com.shishkindenis.locationtracker_child;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.schibsted.spain.barista.rule.BaristaRule;
import com.shishkindenis.locationtracker_child.activities.MainActivity;
import com.shishkindenis.locationtracker_child.activities.SendLocationActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaMenuClickInteractions.clickMenu;

@RunWith(AndroidJUnit4.class)
public class SignOutUiTest_Barista {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Test
    public void signOutSuccessful() {
        auth.signOut();
        String email = "user" + TestUtils.randomInt() + "@example.com";
        String password = "password" + TestUtils.randomInt();
        auth.createUserWithEmailAndPassword(email, password);
        BaristaRule<MainActivity> baristaRule = BaristaRule.create(MainActivity.class);
        baristaRule.launchActivity();
        clickOn(R.id.btnEmail);
        writeTo(R.id.etEmail, email);
        writeTo(R.id.etPassword, password);
        clickOn(R.id.btnLogin);
        TestUtils.sleep();
        ActivityTestRule<SendLocationActivity> sendLocationActivityActivityTestRule = new ActivityTestRule<>(SendLocationActivity.class);
        SendLocationActivity sendLocationActivity = sendLocationActivityActivityTestRule.launchActivity(new Intent());
        clickMenu(R.id.action_settings);
        onView(withText(R.string.sign_out_successful)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

}
