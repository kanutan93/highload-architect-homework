package ru.hl.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.dto.request.RegisterProfileRequestDto;
import ru.hl.socialnetwork.model.dto.request.UpdateProfileRequestDto;
import ru.hl.socialnetwork.model.dto.response.ProfileResponseDto;
import ru.hl.socialnetwork.mapper.user.UserDaoMapper;
import ru.hl.socialnetwork.repository.UserRepository;
import ru.hl.socialnetwork.service.ProfileService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final UserRepository userRepository;
  private final UserDaoMapper userDaoMapper;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void register(RegisterProfileRequestDto registerProfileRequestDto) {
    log.info("Trying to register user with email: {}", registerProfileRequestDto.getEmail());

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

    User currentUser = getCurrentUser();
    UserDao currentUserDao = userRepository.getByEmail(currentUser.getUsername());
    ProfileResponseDto result = userDaoMapper.toProfileResponseDto(currentUserDao);

    log.info("Current user profile with email: {} has been received successfully", result.getEmail());

    return result;
  }

  @Override
  public ProfileResponseDto updateCurrentProfile(UpdateProfileRequestDto updateProfileRequestDto) {
    log.info("Trying to update current user profile");

    User currentUser = getCurrentUser();
    UserDao currentUserDao = userRepository.getByEmail(currentUser.getUsername());
    UserDao userDao = userDaoMapper.toUserDao(updateProfileRequestDto);
    userRepository.update(currentUserDao.getId(), userDao);
    currentUserDao = userRepository.getByEmail(currentUser.getUsername());
    ProfileResponseDto result = userDaoMapper.toProfileResponseDto(currentUserDao);

    log.info("Current user profile with email: {} has been updated successfully", result.getEmail());

    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProfileResponseDto> getUserProfiles(String search, Integer page, Integer limit) {
    log.info("Trying to search user profiles by search: {}, page: {}, limit: {}", search, page, limit);

    User currentUser = getCurrentUser();
    List<UserDao> usersDaos = userRepository.search(currentUser.getUsername(), search, page, limit);

    List<ProfileResponseDto> result = usersDaos.stream()
        .map(userDaoMapper::toProfileResponseDto)
        .collect(Collectors.toList());

    log.info("Found {} user profiles by search: {}, page: {}, limit: {}", result.size(), search, page, limit);

    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProfileResponseDto> getUserProfiles(String firstName, String lastName) {
    log.info("Trying to search user profiles by firstName: {}, lastName: {}", firstName, lastName);

    List<UserDao> usersDaos = userRepository.searchUsers(firstName, lastName);

    List<ProfileResponseDto> result = usersDaos.stream()
        .map(userDaoMapper::toProfileResponseDto)
        .collect(Collectors.toList());

    log.info("Found {} user profiles by firstName: {}, lastName: {}", result.size(), firstName, lastName);

    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public ProfileResponseDto getUserProfile(Integer userId) {
    log.info("Trying to get user profile with id: {}", userId);

    UserDao currentUserDao = userRepository.getById(userId);
    ProfileResponseDto result = userDaoMapper.toProfileResponseDto(currentUserDao);

    log.info("User profile with id: {} and email: {} has been received successfully", userId, result.getEmail());

    return result;
  }

  private static User getCurrentUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
