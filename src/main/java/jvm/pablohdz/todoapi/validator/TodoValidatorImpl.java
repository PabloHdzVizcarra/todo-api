package jvm.pablohdz.todoapi.validator;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.dto.TodoRequestWithId;
import jvm.pablohdz.todoapi.dto.TodoUpdateStateRequest;

@Component
public class TodoValidatorImpl implements TodoValidator
{
    private final String NAME_ERROR = "The name must be greater than six characters";
    private final String CATEGORY_ERROR = "The category only have letters characters";
    private final String ID_ERROR = "The id must be a valid integer";
    private final String STATUS_ERROR = "the status of the todo should only be true or false";

    private Validation<Seq<String>, TodoRequest> validateDataTodoRequest(
            String name, String category
    )
    {
        return Validation.combine(
                        validateName(name), validateCategory(category))
                .ap(TodoRequest::new);
    }

    private Validation<Seq<String>, TodoRequestWithId> validateDataTodoRequestWithId(
            Long id, String name, String category
    )
    {
        return Validation.combine(
                validateId(id),
                validateName(name),
                validateCategory(category)
        ).ap(TodoRequestWithId::new);
    }

    private Validation<String, String> validateCategory(@NotNull String category)
    {
        String invalidChars = category.replaceAll("[a-zA-Z ]", "");
        return invalidChars.isEmpty() && category.length() > 3
                ? Validation.valid(category)
                : Validation.invalid(CATEGORY_ERROR + invalidChars);
    }

    private Validation<String, String> validateName(@NotNull String name)
    {
        return name.length() > 6
                ? Validation.valid(name)
                : Validation.invalid(NAME_ERROR);
    }

    private Validation<String, Long> validateId(Long id)
    {
        return id == null || id <= 0
                ? Validation.invalid(ID_ERROR)
                : Validation.valid(id);
    }

    @Override
    public TodoRequest validateTodo(@NotNull TodoRequest data) throws Exception
    {
        Validation<Seq<String>, TodoRequest> dataValidated =
                validateDataTodoRequest(data.getName(), data.getCategory());

        if (dataValidated.isInvalid())
        {
            Seq<String> error = dataValidated.getError();
            String errorData = error.intersperse(", ")
                    .fold("", String::concat);

            throw new Exception(errorData);
        }
        return dataValidated.get();
    }

    @Override
    public TodoRequestWithId validateTodoWithID(TodoRequestWithId data) throws Exception
    {
        Long id = data.getId();
        String name = data.getName();
        String category = data.getCategory();

        Validation<Seq<String>, TodoRequestWithId> dataValidated =
                validateDataTodoRequestWithId(id, name, category);

        if (dataValidated.isInvalid())
        {
            Seq<String> errors = dataValidated.getError();
            String error = errors
                    .intersperse(", ")
                    .fold("", String::concat);
            throw new Exception(error);
        }
        return dataValidated.get();
    }

    @Override
    public TodoUpdateStateRequest validateUpdateStateRequest(TodoUpdateStateRequest data)
            throws RequestValidationException
    {
        Long id = data.getId();
        boolean state = data.isState();

        Validation<Seq<String>, TodoUpdateStateRequest> validation =
                Validation.combine(
                        validateId(id),
                        validateState(state)
                ).ap(TodoUpdateStateRequest::new);

        if (validation.isInvalid())
        {
            Seq<String> errors = validation.getError();
            String messageError = errors
                    .intersperse(", ")
                    .fold("", String::concat);

            throw new RequestValidationException(messageError);
        }

        return validation.get();
    }

    private Validation<String, Boolean> validateState(boolean state)
    {
        return Validation.valid(state);
    }

}
