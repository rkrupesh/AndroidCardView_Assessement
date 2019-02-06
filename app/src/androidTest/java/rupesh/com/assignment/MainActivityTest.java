package rupesh.com.assignment;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mAcitvityTest = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mActivity = null;


    @Before
    public void setUp() throws Exception {
        mActivity = mAcitvityTest.getActivity();
    }

    @Test
    public void testLaunchbtn() {
        View view = mActivity.findViewById(R.id.pullToRefresh);
        assertNotNull(view);
    }

    @Test
    public void populateList() {
        RecyclerView view = mActivity.findViewById(R.id.recycler_view);
        assertNotNull(view);
    }

    @Test
    public void RecylerViewScrollToPosition() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(3));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}