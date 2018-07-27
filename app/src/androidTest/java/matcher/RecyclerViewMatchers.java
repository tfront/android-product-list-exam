package matcher;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static matcher.CompareMatcher.CompareRelation.COMPARE_RELATION_EQUAL;
import static matcher.CompareMatcher.CompareRelation.COMPARE_RELATION_GREATER;


import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class RecyclerViewMatchers {
    private final Matcher<View> recyclerViewMatcher;

    public RecyclerViewMatchers(int recyclerViewId) {
        this.recyclerViewMatcher = withId(recyclerViewId);
    }

    public RecyclerViewMatchers(Matcher<View> recyclerViewMatcher) {
        this.recyclerViewMatcher = recyclerViewMatcher;
    }

    public static RecyclerViewMatchers withRecyclerView(final Matcher<View> recyclerViewMatcher){
        return new RecyclerViewMatchers(recyclerViewMatcher);
    }

    public static RecyclerViewMatchers withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatchers(recyclerViewId);
    }

    /**
     * A Matcher<ViewHolder> that matches a view holder in a recycler with its one sub view id
     * and correspondingly displaying text
     *
     * @param targetViewId   is the sub view's resources id.
     * @param targetViewText is the sub view's displaying text.
     */
    public static Matcher<ViewHolder> childWithSubviewIdAndText(final int targetViewId,
                                                                final String targetViewText) {
        return new TypeSafeMatcher<ViewHolder>() {
            Resources resources = null;

            public void describeTo(Description description) {
                String targetIdDesc = Integer.toString(targetViewId);
                if (this.resources != null) {
                    try {
                        targetIdDesc = resources.getResourceName(targetViewId);
                    } catch (Resources.NotFoundException var4) {
                        targetIdDesc = String.format("%s (resource name not found)",
                                targetIdDesc);
                    }
                }

                description.appendText("in RecyclerView with sub View id: " + targetIdDesc
                        + ", with sub View text: " + targetViewText);
            }

            @Override
            protected boolean matchesSafely(ViewHolder item) {
                View view = item.itemView;
                View subView = view.findViewById(targetViewId);
                return null != subView && subView instanceof TextView && ((TextView) subView)
                        .getText().equals(targetViewText);
            }

        };
    }

    public Matcher<View> atPosition(final int position) {
        return atPositionOnView(position, -1);
    }

    public static Matcher<View> hasChild() {
        return childCountCompareTo(0, COMPARE_RELATION_GREATER);
    }

    private static Matcher<View> childCountCompareTo(final int size, final CompareMatcher.CompareRelation
            relation) {
        return new CompareMatcher<View, Integer>(size, relation) {
            private int mCount;

            @Override
            protected Comparable<Integer> getSource(View item) {
                mCount = ((RecyclerView) item).getAdapter().getItemCount();
                return mCount;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("Adapter child count %d doesn't match %s " +
                        "%d", mCount, relation, size));
            }
        };
    }

    public static Matcher<View> childCountEqualTo(final int size) {
        return childCountCompareTo(size, COMPARE_RELATION_EQUAL);
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            RecyclerView recyclerView = null;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(targetViewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(targetViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                targetViewId);
                    }
                }
                if (targetViewId == -1) {
                    idDescription = "childview";
                }

                description.appendText("in recyclerView: ");
                recyclerViewMatcher.describeTo(description);
                description.appendText(", with subview position: " + position
                        + ", with descendant: " + idDescription);
            }

            public boolean matchesSafely(View view) {
                this.resources = view.getResources();
                if (recyclerViewMatcher.matches(view)) {
                    recyclerView = (RecyclerView)view;
                    return false;
                }
                if (recyclerView != null) {
                    View childView = recyclerView.findViewHolderForAdapterPosition(position)
                            .itemView;
                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }
                }
                return false;
            }
        };
    }
}
