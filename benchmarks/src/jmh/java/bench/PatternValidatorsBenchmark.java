package bench;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.AlphaNumericValidator;
import com.andreabaccega.formedittextvalidator.AlphaValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.IpAddressValidator;
import com.andreabaccega.formedittextvalidator.PersonFullNameValidator;
import com.andreabaccega.formedittextvalidator.PhoneValidator;
import com.andreabaccega.formedittextvalidator.RegexpValidator;
import com.andreabaccega.formedittextvalidator.Validator;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * The regexp based validators. They run on every text change of a form field,
 * so their cost is paid on each keystroke.
 */
@State(Scope.Benchmark)
public class PatternValidatorsBenchmark {

    private Validator email;
    private Validator ipAddress;
    private Validator phone;
    private Validator alpha;
    private Validator alphaNumeric;
    private Validator personFullName;
    private Validator custom;

    private EditText validEmail;
    private EditText invalidEmail;
    private EditText validIpAddress;
    private EditText validPhone;
    private EditText alphaText;
    private EditText alphaNumericText;
    private EditText fullName;
    private EditText customText;

    @Setup
    public void setup() {
        email = new EmailValidator("invalid email");
        ipAddress = new IpAddressValidator("invalid ip address");
        phone = new PhoneValidator("invalid phone number");
        alpha = new AlphaValidator("only letters allowed");
        alphaNumeric = new AlphaNumericValidator("only letters and digits allowed");
        personFullName = new PersonFullNameValidator("invalid full name");
        custom = new RegexpValidator("invalid product code", "[A-Z]{3}-[0-9]{4}-[a-z]{2}");

        validEmail = new EditText("andrea.baccega+forms@subdomain.example-mail.com");
        // The domain part is missing its TLD, so the pattern has to backtrack
        // over the whole local part before failing.
        invalidEmail = new EditText("andrea.baccega+forms@subdomain-example-mail");
        validIpAddress = new EditText("192.168.100.254");
        validPhone = new EditText("+39 (055) 123-4567");
        alphaText = new EditText("Lorem ipsum dolor sit amet consectetur");
        alphaNumericText = new EditText("Order 66 shipped on 2019/03/15");
        fullName = new EditText("Andrea Baccega-Rossi");
        customText = new EditText("ABC-1234-xy");
    }

    @Benchmark
    public boolean emailValid() {
        return email.isValid(validEmail);
    }

    @Benchmark
    public boolean emailInvalid() {
        return email.isValid(invalidEmail);
    }

    @Benchmark
    public boolean ipAddressValid() {
        return ipAddress.isValid(validIpAddress);
    }

    @Benchmark
    public boolean phoneValid() {
        return phone.isValid(validPhone);
    }

    @Benchmark
    public boolean alphaValid() {
        return alpha.isValid(alphaText);
    }

    @Benchmark
    public boolean alphaNumericValid() {
        return alphaNumeric.isValid(alphaNumericText);
    }

    @Benchmark
    public boolean personFullNameValid() {
        return personFullName.isValid(fullName);
    }

    @Benchmark
    public boolean customRegexpValid() {
        return custom.isValid(customText);
    }
}
