package android.os;

/**
 * Stand-in for {@code android.os.Build}, reporting the {@code compileSdkVersion}
 * of the library so the validators take the same branch as on a device. Used by
 * the benchmarks only. See {@code benchmarks/src/framework/README.md}.
 */
public class Build {
    public static class VERSION {
        public static final int SDK_INT = 27;
    }
}
