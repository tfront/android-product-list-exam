package matcher;

import org.hamcrest.TypeSafeMatcher;

/**
 * Created by nyi on 21/1/2016.
 */
public abstract class CompareMatcher<T, K> extends TypeSafeMatcher<T> {
    public enum CompareRelation {
        COMPARE_RELATION_EQUAL {
            @Override
            public String toString() {
                return "=";
            }
        },
        COMPARE_RELATION_LESS {
            @Override
            public String toString() {
                return "<";
            }
        },
        COMPARE_RELATION_GREATER {
            @Override
            public String toString() {
                return ">";
            }
        }
    }

    private K mTarget;
    private CompareRelation mRelation;

    public CompareMatcher(K target, CompareRelation relation) {
        mTarget = target;
        mRelation = relation;
    }

    @Override
    protected boolean matchesSafely(T item) {
        int result = getSource(item).compareTo(mTarget);
        switch (mRelation) {
            case COMPARE_RELATION_EQUAL:
                return result == 0;
            case COMPARE_RELATION_GREATER:
                return result > 0;
            case COMPARE_RELATION_LESS:
                return result < 0;
        }
        return false;
    }

    protected abstract Comparable<K> getSource(T item);
}
