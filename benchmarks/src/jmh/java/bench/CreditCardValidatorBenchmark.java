package bench;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.CreditCardValidator;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * The Luhn checksum of {@link CreditCardValidator}, which walks the number
 * digit by digit and parses each one individually.
 */
@State(Scope.Benchmark)
public class CreditCardValidatorBenchmark {

    private CreditCardValidator validator;

    private EditText validCard;
    private EditText invalidChecksum;
    private EditText notANumber;

    @Setup
    public void setup() {
        validator = new CreditCardValidator("invalid credit card number");
        validCard = new EditText("4012888888881881");
        invalidChecksum = new EditText("4012888888881882");
        // A card number with a separator: the digit parsing throws and the
        // validator falls back to reporting the field as invalid.
        notANumber = new EditText("4012 8888 8888 1881");
    }

    @Benchmark
    public boolean validChecksum() {
        return validator.isValid(validCard);
    }

    @Benchmark
    public boolean invalidChecksum() {
        return validator.isValid(invalidChecksum);
    }

    @Benchmark
    public boolean notANumber() {
        return validator.isValid(notANumber);
    }
}
