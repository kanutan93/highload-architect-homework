package ru.hl.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.socialnetwork.dao.UserDao;
import ru.hl.socialnetwork.dto.request.RegisterProfileRequestDto;
import ru.hl.socialnetwork.dto.response.ProfileResponseDto;
import ru.hl.socialnetwork.mapper.user.UserDaoMapper;
import ru.hl.socialnetwork.repository.UserRepository;
import ru.hl.socialnetwork.service.ProfileService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final UserRepository userRepository;
  private final UserDaoMapper userDaoMapper;

  @Override
  @Transactional
  public void register(RegisterProfileRequestDto registerProfileRequestDto) {
    log.info("Trying to register user with email: {}", registerProfileRequestDto.getEmail());

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(registerProfileRequestDto.getPassword());
    registerProfileRequestDto.setPassword(encodedPassword);

    UserDao user = userDaoMapper.toUserDao(registerProfileRequestDto);

    userRepository.save(user);

    log.info("User with email: {} has been registered successfully", registerProfileRequestDto.getEmail());
  }

  @Override
  @Transactional(readOnly = true)
  public ProfileResponseDto getCurrentProfile() {
    log.info("Trying to get current user profile");

    UserDao currentUserDao = userRepository.getUserByEmail(email);
    ProfileResponseDto result = userDaoMapper.toProfileResponseDto(currentUserDao);

    log.info("Current user profile with email: {} has been received successfully", result.getEmail());

    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public ProfileResponseDto getUserProfile(Integer id) {
    log.info("Trying to get user profile with id: {}", id);

    UserDao currentUserDao = userRepository.getUserById(id);
    ProfileResponseDto result = userDaoMapper.toProfileResponseDto(currentUserDao);

    log.info("User profile with id: {} and email: {} has been received successfully", id, result.getEmail());

    return result;
  }

  @Override
  @Transactional
  public void addToUserProfileToFriends(Integer id) {
    log.info("Trying to add user profile with id: {} to friends for current profile", id);

    UserDao currentUserDao = userRepository.getUserById(id);
    ProfileResponseDto result = userDaoMapper.toProfileResponseDto(currentUserDao);

    log.info("User profile with id: {} was added to friends for current profile", id, result.getEmail());
  }
}
