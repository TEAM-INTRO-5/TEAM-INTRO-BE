package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception401;
import com.fastcampus05.zillinks.core.util.dto.excel.ExcelOutDTO;
import com.fastcampus05.zillinks.domain.dto.dashboard.DashboardRequest;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.model.dashboard.*;
import com.fastcampus05.zillinks.domain.model.dashboard.repository.*;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse.*;
import static com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse.FindDownloadFileOutDTO.DownloadFile;
import static com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse.FindDownloadFileOutDTO.toOutDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final UserRepository userRepository;
    private final ContactUsLogRepository contactUsLogRepository;
    private final ContactUsLogQueryRepository contactUsLogQueryRepository;
    private final DownloadLogRepository downloadLogRepository;
    private final DownloadLogQueryRepository downloadLogQueryRepository;
    private final VisitorLogQueryRepository visitorLogQueryRepository;

    public FindContactUsOutDTO findContactUs(ContactUsStatus status, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Page<ContactUsLog> contactUsLogPG = contactUsLogQueryRepository.findPGAllByStatus(status, introPagePS.getId(), page);
        List<FindContactUsOutDTO.ContactUsOutDTO> contactUsOutDTOList = contactUsLogPG.stream()
                .map(s -> FindContactUsOutDTO.ContactUsOutDTO.builder()
                        .contactUsLogId(s.getId())
                        .name(s.getName())
                        .email(s.getEmail())
                        .content(s.getContent())
                        .type(s.getType())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return FindContactUsOutDTO.toOutDTO(introPagePS, contactUsLogPG, contactUsOutDTOList, status);
    }

    public FindContactUsDetailOutDTO findContactUsDetail(Long contactUsId, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        ContactUsLog contactUsLogPS = contactUsLogRepository.findById(contactUsId)
                .orElseThrow(() -> new Exception400("contact_us_id", "해당 게시물은 존재하지 않습니다."));
        if (!contactUsLogPS.getIntroPage().equals(introPagePS))
            throw new Exception401("해당 게시물을 열람할 권한이 없습니다.");

        // check-point 변경 요망
        String status = null;
        if (contactUsLogPS.getContactUsStatus().equals(ContactUsStatus.UNCONFIRMED))
            status = "-";
        else if (contactUsLogPS.getContactUsStatus().equals(ContactUsStatus.CONFIRM)) {
            status = "확인됨";
        } else {
            throw new Exception400("contact_us_id", "삭제된 데이터가 조회되었습니다.");
        }

        return FindContactUsDetailOutDTO.builder()
                .contactUsLogId(contactUsLogPS.getId())
                .email(contactUsLogPS.getEmail())
                .name(contactUsLogPS.getName())
                .type(contactUsLogPS.getType())
                .date(contactUsLogPS.getCreatedAt())
                .status(status)
                .content(contactUsLogPS.getContent())
                .build();
    }

    @Transactional
    public void updateContactUsDetail(Long contactUsId, IntroPageRequest.UpdateContactUsDetailInDTO updateContactUsDetailInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        ContactUsLog contactUsLogPS = contactUsLogRepository.findById(contactUsId)
                .orElseThrow(() -> new Exception400("contact_us_id", "해당 게시물은 존재하지 않습니다."));
        if (!contactUsLogPS.getIntroPage().equals(introPagePS))
            throw new Exception401("해당 게시물을 열람할 권한이 없습니다.");
        contactUsLogPS.updateContactUsStatus(ContactUsStatus.valueOf(updateContactUsDetailInDTO.getStatus()));
    }

    public FindDownloadFileOutDTO findDownloadFile(DownloadType type, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Page<DownloadLog> downloadLogPG = downloadLogQueryRepository.findAllByType(type, introPagePS.getId(), page);
        List<DownloadFile> downloadFileList = downloadLogPG.stream()
                .map(s -> DownloadFile.builder()
                        .downloadFileLogId(s.getId())
                        .type(s.getDownloadType())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return toOutDTO(introPagePS, downloadLogPG, downloadFileList, type);
    }

    public FindVisitorOutDTO findVisitor(String type, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        Page<VisitorLog> visitorLogPG = visitorLogQueryRepository.findAllByType(introPagePS.getId(), page);
        List<FindVisitorOutDTO.Visitor> visitorList = visitorLogPG.stream().map(
                s -> FindVisitorOutDTO.Visitor.builder()
                        .visitorId(s.getId())
                        .keyword(type.equals("VIEW") ? ((s.getKeyword() == null) ? "(없음)": s.getKeyword()) : null)
                        .sharingCode(type.equals("SHARING") ? ((s.getSharingCode() == null) ? "(없음)": s.getSharingCode()) : null)
                        .date(s.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());

        return FindVisitorOutDTO.toOutDTO(introPagePS, visitorLogPG, visitorList, type);
    }

    public List<ExcelOutDTO.ContactUsOutDTO> excelContactUs(DashboardRequest.ExcelContactUsInDTO excelContactUsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<ContactUsLog> contactUsLogList = contactUsLogQueryRepository.findAllByStatus(ContactUsStatus.valueOf(excelContactUsInDTO.getStatus()), introPagePS.getId());
        return contactUsLogList.stream()
                .map(s -> ExcelOutDTO.ContactUsOutDTO.builder()
                        .name(s.getName())
                        .email(s.getEmail())
                        .content(s.getContent())
                        .type(s.getType())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
