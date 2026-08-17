# Framework stand-ins

The validators of `com.andreabaccega.formedittextvalidator` are plain Java, but
they touch a handful of Android framework classes: `EditText.getText()`,
`TextUtils`, `Build.VERSION.SDK_INT` and `Patterns`.

JMH runs on a plain JVM, where `android.jar` is not available (and where an
`EditText` cannot be instantiated because it needs a `Context`). The classes in
`java/android` are therefore minimal stand-ins used **only** by the benchmarks:

- `android.widget.EditText` / `android.text.Editable`: a text holder exposing
  `getText()`, which is all the validators use.
- `android.text.TextUtils`: the four helpers used by the validators
  (`isEmpty`, `equals`, `getTrimmedLength`, `isDigitsOnly`), implemented like the
  framework does.
- `android.os.Build.VERSION.SDK_INT`: set to the `compileSdkVersion` of the
  library (27), so the validators take the same branch as on a real device.
- `android.util.Patterns`: the `EMAIL_ADDRESS`, `IP_ADDRESS` and `PHONE`
  expressions of the framework. They are identical to the pre API 8 fallbacks
  that the library already carries inline in `EmailValidator`,
  `IpAddressValidator` and `PhoneValidator`.

Nothing here is shipped in the library artifact: this source directory only
belongs to the `benchmarks` build, which is not part of the Android build.
