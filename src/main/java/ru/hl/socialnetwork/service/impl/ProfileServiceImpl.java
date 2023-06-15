package ru.hl.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.socialnetwork.aop.ReadOnlyConnection;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.dto.request.profile.RegisterProfileRequestDto;
import ru.hl.socialnetwork.model.dto.request.profile.UpdateProfileRequestDto;
import ru.hl.socialnetwork.model.dto.response.ProfileResponseDto;
import ru.hl.socialnetwork.mapper.user.UserDaoMapper;
import ru.hl.socialnetwork.repository.UserRepository;
import ru.hl.socialnetwork.service.ProfileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private static final String CURRENT_PROFILE_CACHE = "currentProfileCache";

  private final UserRepository userRepository;
  private final UserDaoMapper userDaoMapper;
  private final BCryptPasswordEncoder passwordEncoder;
  private final CacheManager cacheManager;

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
  @ReadOnlyConnection
  @Transactional(readOnly = true)
  public ProfileResponseDto getCurrentProfile() {
    log.info("Trying to get current user profile");

    User currentUser = getCurrentUser();
    String username = currentUser.getUsername();

    Cache cache = cacheManager.getCache(CURRENT_PROFILE_CACHE);
    ProfileResponseDto currentProfileFromCache = Optional.ofNullable(cache)
        .map(it -> it.get(username, ProfileResponseDto.class))
        .orElse(null);

    if (currentProfileFromCache != null) {
      log.info("Current user profile with email: {} has been received successfully from cache", currentProfileFromCache.getEmail());
      return currentProfileFromCache;
    } else {
      UserDao currentUserDao = userRepository.getByEmail(username);
      ProfileResponseDto currentProfile = userDaoMapper.toProfileResponseDto(currentUserDao);
      cache.put(username, currentProfile);
      log.info("Current user profile with email: {} has been received successfully", currentProfile.getEmail());
      return currentProfile;
    }
  }

  @Override
  @Transactional
  public ProfileResponseDto updateCurrentProfile(UpdateProfileRequestDto updateProfileRequestDto) {
    log.info("Trying to update current user profile");

    User currentUser = getCurrentUser();
    String username = currentUser.getUsername();

    UserDao currentUserDao = userRepository.getByEmail(username);
    UserDao userDao = userDaoMapper.toUserDao(updateProfileRequestDto);
    userRepository.update(currentUserDao.getId(), userDao);
    currentUserDao = userRepository.getByEmail(currentUser.getUsername());
    ProfileResponseDto result = userDaoMapper.toProfileResponseDto(currentUserDao);

    Cache cache = cacheManager.getCache(CURRENT_PROFILE_CACHE);
    if (cache != null) {
      cache.put(username, result);
    }

    log.info("Current user profile with email: {} has been updated successfully", result.getEmail());

    return result;
  }

  @Override
  @ReadOnlyConnection
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
  @ReadOnlyConnection
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
  @ReadOnlyConnection
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
