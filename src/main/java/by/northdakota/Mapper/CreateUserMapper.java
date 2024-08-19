package by.northdakota.Mapper;

import by.northdakota.Dto.CreateUserDto;
import by.northdakota.Entity.Gender;
import by.northdakota.Entity.Role;
import by.northdakota.Entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto>{
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName())
                .birthday(LocalDate.parse(createUserDto.getBirthday()))
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Role.findRole(createUserDto.getRole()))
                .gender(Gender.findGender(createUserDto.getGender()))
                .build();
    }
}
