package jvm.pablohdz.todoapi.validator;

import org.springframework.stereotype.Component;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import jvm.pablohdz.todoapi.dto.TodoRequest;

@Component
public class TodoValidatorImpl implements TodoValidator
{
    private final String NAME_ERROR = "The name must be greater than six characters";
    private final String CATEGORY_ERROR = "The category only have letters characters";

    public Validation<Seq<String>, TodoRequest> validate(
            String name, String category
    )
    {
        return Validation.combine(
                        validateName(name), validateCategory(category))
                .ap(TodoRequest::new);
    }

    private Validation<String, String> validateCategory(String category)
    {
        String invalidChars = category.replaceAll("[a-zA-Z ]", "");
        return invalidChars.isEmpty() && category.length() > 3
                ? Validation.valid(category)
                : Validation.invalid(CATEGORY_ERROR + invalidChars);
    }

    private Validation<String, String> validateName(String name)
    {
        return name.length() > 6
                ? Validation.valid(name)
                : Validation.invalid(NAME_ERROR);
    }

    @Override
    public TodoRequest validateTodo(TodoRequest data) throws Exception
    {
        Validation<Seq<String>, TodoRequest> dataValidated =
                validate(data.getName(), data.getCategory());

        if (dataValidated.isInvalid())
        {
            Seq<String> error = dataValidated.getError();
            String errorData = error.intersperse(", ")
                    .fold("", String::concat);

            throw new Exception(errorData);
        }
        return dataValidated.get();
    }
}
