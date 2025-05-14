# How to validation with annotation parameters

## Example how to validate age and return the message with parameter from annotation
- In ErroCode class modify the message of error code INVALID_DOB from "Invalid date of birth" to "Your age must be at least {min}"
- In GlobalExceptionHandler class add this code inside hadlingValidationException 

```java
    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation = exception.getBindingResult()
                    .getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
        } catch (IllegalArgumentException e){
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes) ?
                mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes){
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
```

## Explanation

The `handlingValidation` method extracts the annotation parameters from `attributes` and replaces placeholders in the error message (e.g., `{min}`) with actual values.

This makes it possible to return dynamic validation messages like:

- **Username must be at least `{min}` characters**
- **Password must be at least `{min}` characters**

For example, if the `min` value is `8`, the final message will be:

```
Username must be at least 8 characters
```