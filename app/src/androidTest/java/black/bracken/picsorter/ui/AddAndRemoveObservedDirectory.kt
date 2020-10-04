package black.bracken.picsorter.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import black.bracken.picsorter.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddAndRemoveObservedDirectory {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SettingsActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    @Test
    fun addAndRemoveObservedDirectory() {
        val appCompatTextView = onView(
            allOf(
                withId(R.id.textDirectories), withText("監視するディレクトリを指定"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.buttonAddDirectory), withContentDescription("監視対象となるディレクトリを追加します"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val dialogActionButton2 = onView(
            allOf(
                withId(R.id.md_button_positive), withText("OK"),
                childAtPosition(
                    allOf(
                        withId(R.id.md_button_layout),
                        childAtPosition(
                            withId(R.id.md_root),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        dialogActionButton2.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.buttonRemoveDirectory), withContentDescription("監視対象からこのディレクトリを外します"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerDirectories),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val dialogActionButton3 = onView(
            allOf(
                withId(R.id.md_button_positive), withText("削除する"),
                childAtPosition(
                    allOf(
                        withId(R.id.md_button_layout),
                        childAtPosition(
                            withId(R.id.md_root),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        dialogActionButton3.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
