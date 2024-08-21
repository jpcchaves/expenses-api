package com.expenses.app.service.impl;

import com.expenses.app.domain.dto.user.UserRequestDTO;
import com.expenses.app.domain.dto.user.UserResponseDTO;
import com.expenses.app.domain.enums.RoleType;
import com.expenses.app.domain.models.Role;
import com.expenses.app.domain.models.User;
import com.expenses.app.persistence.repository.RoleRepository;
import com.expenses.app.persistence.repository.UserRepository;
import com.expenses.app.service.UserService;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserResponseDTO create(UserRequestDTO requestDTO) {

    if (!Objects.equals(requestDTO.getPassword(), requestDTO.getConfirmPassword())) {

      throw new RuntimeException("As senhas devem ser iguais!");
    }

    Role role =
        roleRepository
            .findByName(RoleType.ROLE_TEST.roleType())
            .orElseThrow(() -> new RuntimeException(""));

    User user =
        new User(
            requestDTO.getName(), requestDTO.getEmail(), requestDTO.getPassword(), Set.of(role));

    user = userRepository.saveAndFlush(user);

    return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
  }

  @Override
  public UserResponseDTO update(Long userId, UserRequestDTO requestDTO) {

    if (!Objects.equals(requestDTO.getPassword(), requestDTO.getConfirmPassword())) {

      throw new RuntimeException("As senhas devem ser iguais!");
    }

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario inexistente!"));

    if (!Objects.equals(requestDTO.getEmail(), user.getEmail())) {

      if (userRepository.existsByEmail(requestDTO.getEmail())) {

        throw new RuntimeException("Email ja cadastrado!");
      }
    }

    user.setName(requestDTO.getName());
    user.setEmail(requestDTO.getEmail());
    user.setPassword(requestDTO.getPassword());

    user = userRepository.saveAndFlush(user);

    return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
  }
}
