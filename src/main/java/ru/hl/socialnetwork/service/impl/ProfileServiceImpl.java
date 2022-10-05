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
import ru.hl.socialnetwork.repository.FriendRepository;
import ru.hl.socialnetwork.repository.UserRepository;
import ru.hl.socialnetwork.service.ProfileService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final UserRepository userRepository;
  private final FriendRepository friendRepository;
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

    //TODO
//    UserDao currentUserDao = userRepository.getUserByEmail(email);
//    ProfileResponseDto result = userDaoMapper.toProfileResponseDto(currentUserDao);
//
//    log.info("Current user profile with email: {} has been received successfully", result.getEmail());
//
//    return result;

    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProfileResponseDto> getUserProfiles(String search, Integer page, Integer limit) {
    log.info("Trying to search user profiles by search: {}, page: {}, limit: {}", search, page, limit);

    List<UserDao> usersDaos = userRepository.searchUsers(search, page, limit);

    List<ProfileResponseDto> result = usersDaos.stream()
        .map(userDaoMapper::toProfileResponseDto)
        .collect(Collectors.toList());

    log.info("Found {} user profiles by search: {}, page: {}, limit: {}", result.size(), search, page, limit);

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
  public void addUserProfileToFriends(Integer id) {
    log.info("Trying to add user profile with id: {} to friends for current profile", id);

    //TODO
//    friendRepository.save(id);

    log.info("User profile with id: {} was added to friends for current profile", id);
  }

  @Override
  @Transactional
  public void removeUserProfileFromFriends(Integer id) {
    log.info("Trying to remove user profile with id: {} from friends for current profile", id);

    friendRepository.removeBySenderIdOrReceiverId(id);

    log.info("User profile with id: {} was removed from friends for current profile", id);
  }
}
