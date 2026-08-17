package android.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Stand-in for {@code android.annotation.SuppressLint}. Used by the benchmarks
 * only. See {@code benchmarks/src/framework/README.md}.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface SuppressLint {
    String[] value();
}
