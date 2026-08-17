package android.widget;

import android.text.Editable;

/**
 * Minimal stand-in for {@code android.widget.EditText}, exposing the only part
 * of it the validators use: the text of the field. Used by the benchmarks only.
 * See {@code benchmarks/src/framework/README.md}.
 */
public class EditText {
    private Editable text = new PlainEditable("");

    public EditText() {
    }

    public EditText(CharSequence text) {
        setText(text);
    }

    public Editable getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = new PlainEditable(text == null ? "" : text.toString());
    }

    private static final class PlainEditable implements Editable {
        private final String value;

        PlainEditable(String value) {
            this.value = value;
        }

        @Override
        public int length() {
            return value.length();
        }

        @Override
        public char charAt(int index) {
            return value.charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return value.subSequence(start, end);
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
