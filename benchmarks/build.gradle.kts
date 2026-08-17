plugins {
    java
    id("me.champeau.jmh") version "0.7.2"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

sourceSets {
    main {
        java {
            // Compile the validators of the library directly, so the benchmarks
            // always measure the sources of this repository.
            srcDir("../library/src")
            // Minimal stand-ins for the few framework classes the validators
            // rely on, so they can run on a plain JVM (see src/framework/README.md).
            srcDir("src/framework/java")
            include(
                "android/**",
                "com/andreabaccega/formedittextvalidator/**"
            )
            // These two validators only pick a Pattern out of
            // android.util.Patterns that is generated from the IANA top level
            // domain list, which is framework data rather than library code.
            exclude(
                "com/andreabaccega/formedittextvalidator/DomainValidator.java",
                "com/andreabaccega/formedittextvalidator/WebUrlValidator.java"
            )
        }
    }
}

jmh {
    jmhVersion.set("0.2.0")

    benchmarkMode.set(listOf("avgt"))
    timeUnit.set("ns")
    warmupIterations.set(1)
    warmup.set("1s")
    iterations.set(3)
    timeOnIteration.set("1s")
    fork.set(2)
    resultFormat.set("JSON")

    // Force a System.gc() between iterations so the heap state is reset before
    // each measurement starts. Without this, a GC pause can land inside the
    // measurement window and show up as a spurious regression in CI.
    forceGC.set(true)

    if (System.getenv("CODSPEED_ENV") != null) {
        logger.lifecycle("CODSPEED_ENV detected — running the curated CI benchmark subset")

        // Every forked JVM is instrumented and profiled by the CodSpeed runner,
        // which costs much more than the measurement itself. Use a single fork
        // over a subset covering each family of validators to keep the CI
        // runtime reasonable. The whole suite still runs locally.
        fork.set(1)

        // me.champeau.jmh joins `includes` with commas into a single positional
        // regex passed to JMH, so multiple entries collapse into one pattern
        // with literal commas and match nothing. Use a single alternation.
        includes.set(
            listOf(
                ".*(" +
                    "PatternValidatorsBenchmark\\.(emailValid|emailInvalid|alphaNumericValid)" +
                    "|SimpleValidatorsBenchmark\\.(notEmpty|numericRangeValid)" +
                    "|CreditCardValidatorBenchmark\\.validChecksum" +
                    "|DateValidatorBenchmark\\.singleFormatValid" +
                    "|CompositeValidatorsBenchmark\\.(andAllPassing|orLastPassing)" +
                    ").*"
            )
        )
    }
}
