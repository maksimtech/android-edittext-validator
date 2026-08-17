package bench;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.DigitLengthRangeValidator;
import com.andreabaccega.formedittextvalidator.EmptyValidator;
import com.andreabaccega.formedittextvalidator.FloatNumericRangeValidator;
import com.andreabaccega.formedittextvalidator.NumericRangeValidator;
import com.andreabaccega.formedittextvalidator.NumericValidator;
import com.andreabaccega.formedittextvalidator.SameValueValidator;
import com.andreabaccega.formedittextvalidator.Validator;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * The validators built on top of the text helpers and number parsing.
 */
@State(Scope.Benchmark)
public class SimpleValidatorsBenchmark {

    private Validator empty;
    private Validator numeric;
    private Validator numericRange;
    private Validator floatNumericRange;
    private Validator digitLengthRange;
    private Validator sameValue;

    private EditText paddedText;
    private EditText digits;
    private EditText numberInRange;
    private EditText notANumber;
    private EditText floatInRange;
    private EditText password;
    private EditText passwordConfirmation;

    @Setup
    public void setup() {
        empty = new EmptyValidator("this field is required");
        numeric = new NumericValidator("only digits allowed");
        numericRange = new NumericRangeValidator("must be between 1 and 65535", 1, 65535);
        floatNumericRange = new FloatNumericRangeValidator("must be between 0 and 100", 0f, 100f);
        digitLengthRange = new DigitLengthRangeValidator("must be 8 to 32 characters", 8, 32) {
        };

        passwordConfirmation = new EditText("correct horse battery staple");
        sameValue = new SameValueValidator(passwordConfirmation, "the values do not match");

        paddedText = new EditText("   \t a value typed with spaces around it   ");
        digits = new EditText("0645123987");
        numberInRange = new EditText("8080");
        notANumber = new EditText("8080a");
        floatInRange = new EditText("42.75");
        password = new EditText("correct horse battery staple");
    }

    @Benchmark
    public boolean notEmpty() {
        return empty.isValid(paddedText);
    }

    @Benchmark
    public boolean digitsOnly() {
        return numeric.isValid(digits);
    }

    @Benchmark
    public boolean numericRangeValid() {
        return numericRange.isValid(numberInRange);
    }

    @Benchmark
    public boolean numericRangeNotANumber() {
        return numericRange.isValid(notANumber);
    }

    @Benchmark
    public boolean floatNumericRangeValid() {
        return floatNumericRange.isValid(floatInRange);
    }

    @Benchmark
    public boolean digitLengthRangeValid() {
        return digitLengthRange.isValid(password);
    }

    @Benchmark
    public boolean sameValueValid() {
        return sameValue.isValid(password);
    }
}
