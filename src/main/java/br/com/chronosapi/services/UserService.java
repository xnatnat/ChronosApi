package br.com.chronosapi.services;

import br.com.chronosapi.dtos.UserDto;
import br.com.chronosapi.models.User;
import br.com.chronosapi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;


    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto saveAndReturnDto(UserDto userDto) {
        var user = new User(
                userDto.getNome(),
                userDto.getEmail(),
                userDto.getSenha()
        );
        return mapToDto(userRepository.save(user));
    }

    public UserDto findAndReturnDto(Long id){
        return mapToDto(findById(id));
    }

    public UserDto update(UserDto UserDto){
        var UserData = findById(UserDto.getId());
        UserData.setName(UserDto.getNome());
        UserData.setEmail(UserDto.getEmail());
        UserData.setPassword(UserDto.getSenha());
        return mapToDto(userRepository.save(UserData));
    }

    public void delete(Long id){
        userRepository.delete(findById(id));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    private UserDto mapToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
