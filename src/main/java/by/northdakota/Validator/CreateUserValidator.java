package by.northdakota.Validator;

import by.northdakota.Dto.CreateUserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto createUserDto) {
            var validationResult = new ValidationResult();
            if(createUserDto.getBirthday() == null){
                validationResult.add(Error.of("Invalid birthday","Birthday is invalid"));
            }
            if(createUserDto.getName() == null){
                validationResult.add(Error.of("Invalid name","name is invalid"));
            }
            if(createUserDto.getEmail() == null){
                validationResult.add(Error.of("Invalid email","email is invalid"));
            }
            if(createUserDto.getPassword() == null){
                validationResult.add(Error.of("Invalid password","password is invalid"));
            }
            if(createUserDto.getRole() == null){
                validationResult.add(Error.of("Invalid role","role is invalid"));
            }
            if(createUserDto.getGender() == null){
                validationResult.add(Error.of("Invalid gender","gender is invalid"));
            }
            return validationResult;
    }






    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
