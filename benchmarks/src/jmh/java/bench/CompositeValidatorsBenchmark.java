package bench;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.AlphaNumericValidator;
import com.andreabaccega.formedittextvalidator.AndValidator;
import com.andreabaccega.formedittextvalidator.CreditCardValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.EmptyValidator;
import com.andreabaccega.formedittextvalidator.NotValidator;
import com.andreabaccega.formedittextvalidator.NumericValidator;
import com.andreabaccega.formedittextvalidator.OrValidator;
import com.andreabaccega.formedittextvalidator.Validator;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * The composite validators, which is what a form field configured through
 * {@code FormEditText} ends up running: a chain of validators evaluated in
 * order until one decides the outcome.
 */
@State(Scope.Benchmark)
public class CompositeValidatorsBenchmark {

    private Validator and;
    private Validator or;
    private Validator not;

    private EditText validText;
    private EditText failingText;
    private EditText creditCard;

    @Setup
    public void setup() {
        // The typical "required field with a format" setup.
        and = new AndValidator(
                new EmptyValidator("this field is required"),
                new AlphaNumericValidator("only letters and digits allowed"),
                new NotValidator("digits only is not allowed", new NumericValidator(null))
        );
        // The example app ships this one: either an email or a credit card.
        or = new OrValidator(
                "not an email nor a credit card",
                new EmailValidator(null),
                new CreditCardValidator(null)
        );
        not = new NotValidator("digits only is not allowed", new NumericValidator(null));

        validText = new EditText("Order 66 shipped");
        // Fails on the last validator of the chain only.
        failingText = new EditText("123456");
        creditCard = new EditText("4012888888881881");
    }

    @Benchmark
    public boolean andAllPassing() {
        return and.isValid(validText);
    }

    @Benchmark
    public boolean andLastFailing() {
        return and.isValid(failingText);
    }

    @Benchmark
    public boolean orLastPassing() {
        return or.isValid(creditCard);
    }

    @Benchmark
    public boolean notValidator() {
        return not.isValid(validText);
    }
}
