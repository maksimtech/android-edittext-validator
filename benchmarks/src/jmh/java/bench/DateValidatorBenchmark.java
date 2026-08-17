package bench;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.DateValidator;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * The date validator, which builds a {@code SimpleDateFormat} and parses the
 * field content on every validation.
 */
@State(Scope.Benchmark)
public class DateValidatorBenchmark {

    private DateValidator singleFormat;
    private DateValidator multipleFormats;

    private EditText validDate;
    private EditText invalidDate;
    private EditText emptyField;

    @Setup
    public void setup() {
        singleFormat = new DateValidator("invalid date", "yyyy-MM-dd");
        multipleFormats = new DateValidator("invalid date", "yyyy-MM-dd;dd/MM/yyyy");

        validDate = new EditText("2019-03-15");
        invalidDate = new EditText("15th of March");
        emptyField = new EditText("");
    }

    @Benchmark
    public boolean singleFormatValid() {
        return singleFormat.isValid(validDate);
    }

    @Benchmark
    public boolean singleFormatInvalid() {
        return singleFormat.isValid(invalidDate);
    }

    @Benchmark
    public boolean multipleFormatsValid() {
        return multipleFormats.isValid(validDate);
    }

    @Benchmark
    public boolean emptyValueIsAccepted() {
        return singleFormat.isValid(emptyField);
    }
}
