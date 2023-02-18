package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.dto.UserDto;
import com.watcha.watchapedia.dto.response.UserResponse;
import com.watcha.watchapedia.model.entity.User;
import com.watcha.watchapedia.model.network.response.NoticeApiResponse;
import com.watcha.watchapedia.model.repository.NoticeRepository;
import com.watcha.watchapedia.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final NoticeRepository noticeRepository;
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<UserDto> searchUsers(){
        return userRepository.findAllByOrderByUserIdxDesc().stream().map(UserDto::from).toList();
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Long userIdx, String userType){
        return userRepository.findById(userIdx)
                .map(UserDto::from)
                .orElseThrow(() -> new EntityNotFoundException("유저가 없습니다 - userIdx: " + userIdx));
    }
    @Transactional(readOnly = true)
    public List<NoticeApiResponse> noticeAll(){
        List<NoticeApiResponse> noticeList = noticeRepository.findByNtcStatusOrderByNtcIdxDesc("등록").stream().map(
                notice ->{
                    return NoticeApiResponse.of(
                            notice.getNtcIdx(),
                            notice.getNtcTitle(),
                            notice.getNtcText(),
                            notice.getNtcRegBy(),
                            notice.getNtcStatus(),
                            notice.getNtcImagepath(),
                            notice.getNtcBtnColor(),
                            notice.getNtcBtnText(),
                            notice.getNtcBtnLink());
                }
        ).collect(Collectors.toList());
        return noticeList;
    }

}
