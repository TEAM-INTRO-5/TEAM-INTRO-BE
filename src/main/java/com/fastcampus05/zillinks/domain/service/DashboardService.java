package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception401;
import com.fastcampus05.zillinks.core.util.dto.excel.ExcelOutDTO;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import com.fastcampus05.zillinks.domain.dto.dashboard.DashboardRequest;
import com.fastcampus05.zillinks.domain.model.dashboard.*;
import com.fastcampus05.zillinks.domain.model.dashboard.repository.*;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageRepository;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import com.fastcampus05.zillinks.domain.model.widget.Download;
import com.fastcampus05.zillinks.domain.model.widget.Widget;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse.*;
import static com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse.FindDownloadOutDTO.toOutDTO;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final UserRepository userRepository;
    private final ContactUsLogRepository contactUsLogRepository;
    private final ContactUsLogQueryRepository contactUsLogQueryRepository;
    private final DownloadLogRepository downloadLogRepository;
    private final DownloadLogQueryRepository downloadLogQueryRepository;
    private final VisitorLogQueryRepository visitorLogQueryRepository;
    private final IntroPageRepository introPageRepository;
    private final S3UploaderFileRepository s3UploaderFileRepository;

    @Transactional
    public void saveContactUs(DashboardRequest.ContactUsLogInDTO contactUsLogInDTO) {
        IntroPage introPagePS = introPageRepository.findById(contactUsLogInDTO.getIntroPageId())
                .orElseThrow(() -> new Exception400("intro_page_id", "존재하지 않는 회사 소개 페이지입니다."));
        contactUsLogRepository.save(ContactUsLog.builder()
                .introPage(introPagePS)
                .name(contactUsLogInDTO.getName())
                .email(contactUsLogInDTO.getEmail())
                .content(contactUsLogInDTO.getContent())
                .type(contactUsLogInDTO.getType())
                .contactUsStatus(ContactUsStatus.UNCONFIRMED)
                .build());
    }

    public void download(DashboardRequest.DownloadInDTO downloadInDTO) {
        IntroPage introPagePS = introPageRepository.findById(downloadInDTO.getIntroPageId())
                .orElseThrow(() -> new Exception400("intro_page_id", "존재하지 않는 회사 소개 페이지입니다."));

        String path = null;
        List<Widget> widgets = introPagePS.getWidgets();
        for (Widget widget : widgets) {
            if (widgets instanceof Download) {
                if (downloadInDTO.getType().equals("intro_file"))
                    path = ((Download) widgets).getIntroFile();
                else if (downloadInDTO.getType().equals("media_kit_file"))
                    path = ((Download) widgets).getMediaKitFile();
                break;
            }
        }
        downloadLogRepository.save(DownloadLog.builder()
                .introPage(introPagePS)
                .downloadType(DownloadType.valueOf(downloadInDTO.getType()))
                .build());
    }


    public FindDashboardOutDTO findDashboard(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        // 주간 조회수, 주간 공유회수 생성
        List<VisitorLog> visitorLogs = visitorLogQueryRepository.findAllInWeek(introPagePS.getId());
        List<Integer> viewList = new ArrayList<>();
        List<Integer> sharingList = new ArrayList<>();
        // 주간 연락 건수
        List<ContactUsLog> contactUsLogs = contactUsLogQueryRepository.findAllInWeek(introPagePS.getId());
        List<Integer> contactUsLogList = new ArrayList<>();
        // 주간 다운로드 횟수
        List<DownloadLog> downloadLogs = downloadLogQueryRepository.findAllInWeek(introPagePS.getId());
        List<Integer> downloadLogList = new ArrayList<>();
        int size = 0;
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            size = visitorLogs.stream().filter(s -> s.getCreatedAt().toLocalDate().compareTo(date) == 0).collect(Collectors.toList()).size();
            viewList.add(size);
            size = visitorLogs.stream().filter(s -> (s.getCreatedAt().toLocalDate().compareTo(date) == 0) && (s.getSharingCode() != null)).collect(Collectors.toList()).size();
            sharingList.add(size);
            size = contactUsLogs.stream().filter(s -> s.getCreatedAt().toLocalDate().compareTo(date) == 0).collect(Collectors.toList()).size();
            contactUsLogList.add(size);
            size = downloadLogs.stream().filter(s -> s.getCreatedAt().toLocalDate().compareTo(date) == 0).collect(Collectors.toList()).size();
            downloadLogList.add(size);
        }
        return FindDashboardOutDTO.toOutDTO(introPagePS, viewList, sharingList, contactUsLogList, downloadLogList);
    }

    public FindContactUsOutDTO findContactUs(ContactUsStatus status, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Page<ContactUsLog> contactUsLogPG = contactUsLogQueryRepository.findPGAllByStatus(status, introPagePS.getId(), page);
        List<FindContactUsOutDTO.ContactUsLogOutDTO> contactUsLogOutDTOList = contactUsLogPG.stream()
                .map(s -> FindContactUsOutDTO.ContactUsLogOutDTO.builder()
                        .contactUsLogId(s.getId())
                        .name(s.getName())
                        .email(s.getEmail())
                        .content(s.getContent())
                        .type(s.getType())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return FindContactUsOutDTO.toOutDTO(introPagePS, contactUsLogPG, contactUsLogOutDTOList, status);
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
    public void updateContactUsDetail(DashboardRequest.UpdateContactUsDetailInDTO updateContactUsDetailInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        List<ContactUsLog> contactUsLogListPS = contactUsLogQueryRepository.findByContactUsList(updateContactUsDetailInDTO.getContactUsIdList());
        for (ContactUsLog contactUsLogPS : contactUsLogListPS) {
            if (!contactUsLogPS.getIntroPage().equals(introPagePS))
                throw new Exception401("해당 게시물을 열람할 권한이 없습니다.");
            contactUsLogPS.updateContactUsStatus(ContactUsStatus.valueOf(updateContactUsDetailInDTO.getStatus()));
        }
    }

    public FindDownloadOutDTO findDownload(DownloadType type, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        // 다운로드 일주인간의 내역 보여주기
        List<DownloadLog> downloadLogList = downloadLogQueryRepository.findAllInWeek(introPagePS.getId());

        // 다운로드 내역 페이징 보여주기
        Page<DownloadLog> downloadLogPG = downloadLogQueryRepository.findPGAllByType(type, introPagePS.getId(), page);
        List<FindDownloadOutDTO.DownloadOutDTO> downloadOutDTOList = downloadLogPG.stream()
                .map(s -> FindDownloadOutDTO.DownloadOutDTO.builder()
                        .downloadLogId(s.getId())
                        .type(s.getDownloadType())
                        .date(s.getCreatedAt())
                        .build()).collect(Collectors.toList());
        return toOutDTO(introPagePS, downloadLogList, downloadLogPG, downloadOutDTOList, type);
    }

    public FindVisitorOutDTO findVisitor(String type, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        Page<VisitorLog> visitorLogPG = visitorLogQueryRepository.findPGAllByType(introPagePS.getId(), type, page);
        List<FindVisitorOutDTO.VisitorOutDTO> visitorList = visitorLogPG.stream().map(
                s -> FindVisitorOutDTO.VisitorOutDTO.builder()
                        .visitorId(s.getId())
                        .keyword(type.equals("VIEW") ? ((s.getKeyword() == null) ? "(없음)" : s.getKeyword()) : null)
                        .sharingCode(type.equals("SHARING") ? s.getSharingCode() : null)
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
                        .email(s.getEmail())
                        .name(s.getName())
                        .content(s.getContent())
                        .type(s.getType())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ExcelOutDTO.DownloadOutDTO> excelDownload(DownloadType type, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<DownloadLog> downloadLogList = downloadLogQueryRepository.findAllByType(type, introPagePS.getId());
        return downloadLogList.stream()
                .map(s -> ExcelOutDTO.DownloadOutDTO.builder()
                        .type(String.valueOf(s.getDownloadType()))
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ExcelOutDTO.VisitorOutDTO> excelVisitor(DashboardRequest.ExcelVisitorInDTO excelVisitorInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<VisitorLog> visitorLogList = visitorLogQueryRepository.findAllByType(introPagePS.getId(), excelVisitorInDTO.getType());
        return visitorLogList.stream()
                .map(s -> ExcelOutDTO.VisitorOutDTO.builder()
                        .deviceType(s.getDeviceType())
                        .type(excelVisitorInDTO.getType().equals("VIEW") ? ((s.getKeyword() == null) ? "(없음)": s.getKeyword()) : s.getSharingCode())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
