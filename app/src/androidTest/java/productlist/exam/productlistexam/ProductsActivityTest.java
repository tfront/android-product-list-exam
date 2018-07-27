package productlist.exam.productlistexam;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import productlist.exam.productlistexam.constant.Constant;
import util.FileUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static matcher.RecyclerViewMatchers.childCountEqualTo;
import static matcher.RecyclerViewMatchers.withRecyclerView;

@RunWith(AndroidJUnit4.class)
public class ProductsActivityTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<ProductsActivity> mActivityRule =
            new ActivityTestRule<>(ProductsActivity.class, true, false);
    private static MockWebServer mMockServer = new MockWebServer();

    @BeforeClass
    public static void setUpClass() throws IOException {
        mMockServer.play();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        mMockServer.shutdown();
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        Constant.BASE_URL = mMockServer.getUrl("/").toString();
    }

    @After
    public void tearDown() {
    }

    private void enqueueMock(String filename) {
        mMockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(FileUtils.getStringFromAssetFile(getInstrumentation().getContext(), filename)));
    }

    @Test
    public void testFirstLoad() {
        enqueueMock("mock/search0.json");

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(childCountEqualTo(2)));
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.product_name))
                .check(matches(withText("MOCK - Australian Broccoli")));
    }

    @Test
    public void testRefresh() throws InterruptedException {
        enqueueMock("mock/search0.json");
        enqueueMock("mock/search0.json");

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(childCountEqualTo(2)));
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.product_name))
                .check(matches(withText("MOCK - Australian Broccoli")));

        onView(withId(R.id.recycler_view)).perform(ViewActions.swipeDown());
        Thread.sleep(1000);
        onView(withId(R.id.recycler_view)).check(matches(childCountEqualTo(2)));
    }

    @Test
    public void testLoadMore() throws InterruptedException {
        enqueueMock("mock/search0.json");
        enqueueMock("mock/search1.json");

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(childCountEqualTo(2)));
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.product_name))
                .check(matches(withText("MOCK - Australian Broccoli")));

        onView(withId(R.id.recycler_view)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        onView(withId(R.id.recycler_view)).check(matches(childCountEqualTo(4)));
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(2, R.id.product_name))
                .check(matches(withText("MOCK - Prime Cavendish Bananas")));
    }
}