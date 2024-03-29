package ru.hl.primaryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.primaryservice.mapper.friend.FriendMapper;
import ru.hl.primaryservice.model.dao.FriendDao;
import ru.hl.primaryservice.model.dto.response.FriendRequestsResponseDto;
import ru.hl.primaryservice.repository.FriendRepository;
import ru.hl.primaryservice.repository.UserRepository;
import ru.hl.primaryservice.service.FriendService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.hl.primaryservice.util.CurrentUserUtil.getCurrentUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

  private final UserRepository userRepository;
  private final FriendRepository friendRepository;

  private final FriendMapper friendMapper;

  @Override
  @Transactional(readOnly = true)
  public List<FriendRequestsResponseDto> getFriendsRequest() {
    User currentUser = getCurrentUser();
    log.info("Trying to get friends requests for current user: {}", currentUser.getUsername());

    int currentUserId = userRepository.getByEmail(currentUser.getUsername()).getId();
    List<FriendRequestsResponseDto> result = friendRepository.getFriendRequests(currentUserId)
        .stream()
        .map(friendMapper::toFriendRequestResponseDto)
        .collect(Collectors.toList());


    log.info("{} friends requests were received for current user: {}", result.size(), currentUser.getUsername());

    return result;
  }

  @Override
  public List<Integer> getAllFriendsIds() {
    User currentUser = getCurrentUser();
    log.info("Trying to get all friends for current user: {}", currentUser.getUsername());

    int currentUserId = userRepository.getByEmail(currentUser.getUsername()).getId();
    List<Integer> result = friendRepository.getAllFriends(currentUserId)
        .stream().map(it -> {
          if (it.getSenderId() == currentUserId) {
            return it.getReceiverId();
          } else {
            return it.getSenderId();
          }
        })
        .collect(Collectors.toList());

    log.info("{} friends were received for current user: {}", result.size(), currentUser.getUsername());

    return result;
  }

  @Override
  @Transactional
  public void addUserProfileToFriends(Integer id) {
    User currentUser = getCurrentUser();
    log.info("Trying to add user profile with id: {} to friends for current user: {}", id, currentUser.getUsername());

    int currentUserId = userRepository.getByEmail(currentUser.getUsername()).getId();

    FriendDao friendDao = null;
    try {
      friendDao = friendRepository.get(currentUserId, id);
    } catch (Exception e) {
    }

    if (friendDao == null) {
      friendDao = new FriendDao();
      friendDao.setSenderId(currentUserId);
      friendDao.setReceiverId(id);
      friendDao.setApproved(false);
      friendRepository.save(friendDao);
    } else {
      if (friendDao.getReceiverId() == currentUserId) {
        friendDao.setApproved(true);
        friendRepository.update(friendDao.getId(), friendDao);
      }
    }

    log.info("User profile with id: {} was added to friends for current user: {}", id, currentUser.getUsername());
  }

  @Override
  @Transactional
  public void removeUserProfileFromFriends(Integer id) {
    User currentUser = getCurrentUser();
    log.info("Trying to remove user profile with id: {} from friends for current user: {}", id, currentUser.getUsername());

    int currentUserId = userRepository.getByEmail(currentUser.getUsername()).getId();

    friendRepository.remove(currentUserId, id);

    log.info("User profile with id: {} was removed from friends for current user: {}", id, currentUser.getUsername());
  }
}
