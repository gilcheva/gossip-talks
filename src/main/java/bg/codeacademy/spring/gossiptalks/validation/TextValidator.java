package bg.codeacademy.spring.gossiptalks.validation;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.http.util.TextUtils;

public class TextValidator implements ConstraintValidator<ValidText, String> {
  private static final String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
  @Override
  public boolean isValid(String text, ConstraintValidatorContext context) {
    Pattern htmlValidator = TextUtils.isEmpty(HTML_TAG_PATTERN) ? null:Pattern.compile(HTML_TAG_PATTERN);
    if(htmlValidator !=null) {
      if(htmlValidator.matcher(text).find()){
        return false;
      }
    }
    return true;
  }
}
