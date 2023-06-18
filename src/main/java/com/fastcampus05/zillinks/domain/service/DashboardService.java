package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception401;
import com.fastcampus05.zillinks.core.util.dto.excel.ExcelOutDTO;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse.*;
import static com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse.FindDownloadOutDTO.toOutDTO;

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
    private final IntroPageRepository introPageRepository;
    private final S3UploaderFileRepository s3UploaderFileRepository;

    @Transactional
    public void saveContactUs(DashboardRequest.ContactUsInDTO contactUsInDTO) {
        IntroPage introPagePS = introPageRepository.findById(contactUsInDTO.getIntroPageId())
                .orElseThrow(() -> new Exception400("intro_page_id", "존재하지 않는 회사 소개 페이지입니다."));
        contactUsLogRepository.save(ContactUsLog.builder()
                .introPage(introPagePS)
                .name(contactUsInDTO.getName())
                .email(contactUsInDTO.getEmail())
                .content(contactUsInDTO.getContent())
                .type(contactUsInDTO.getType())
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
        S3UploaderFile s3UploaderFilePS = s3UploaderFileRepository.findByEncodingPath(path)
                .orElseThrow(() -> new Exception400("type", "존재하지 않은 파일입니다."));
        downloadLogRepository.save(DownloadLog.builder()
                .introPage(introPagePS)
                .downloadType(DownloadType.valueOf(downloadInDTO.getType()))
                .build());
    }

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
    public void updateContactUsDetail(DashboardRequest.UpdateContactUsDetailInDTO updateContactUsDetailInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        ContactUsLog contactUsLogPS = contactUsLogRepository.findById(updateContactUsDetailInDTO.getContactUsId())
                .orElseThrow(() -> new Exception400("contact_us_id", "해당 게시물은 존재하지 않습니다."));
        if (!contactUsLogPS.getIntroPage().equals(introPagePS))
            throw new Exception401("해당 게시물을 열람할 권한이 없습니다.");
        contactUsLogPS.updateContactUsStatus(ContactUsStatus.valueOf(updateContactUsDetailInDTO.getStatus()));
    }

    public FindDownloadOutDTO findDownload(DownloadType type, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Page<DownloadLog> downloadLogPG = downloadLogQueryRepository.findPGAllByType(type, introPagePS.getId(), page);
        List<FindDownloadOutDTO.Download> downloadList = downloadLogPG.stream()
                .map(s -> FindDownloadOutDTO.Download.builder()
                        .downloadLogId(s.getId())
                        .type(s.getDownloadType())
                        .date(s.getCreatedAt())
                        .build()).collect(Collectors.toList());
        return toOutDTO(introPagePS, downloadLogPG, downloadList, type);
    }

    public FindVisitorOutDTO findVisitor(String type, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        Page<VisitorLog> visitorLogPG = visitorLogQueryRepository.findPGAllByType(introPagePS.getId(), page);
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
                        .email(s.getEmail())
                        .name(s.getName())
                        .content(s.getContent())
                        .type(s.getType())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

//    public List<ExcelOutDTO.DownloadOutDTO> excelDownload(DownloadType type) {
    public List<ExcelOutDTO.DownloadOutDTO> excelDownload(DownloadType type, User user) {
//        User userPS = userRepository.findById(1L)
//                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
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
//        User userPS = userRepository.findById(1L)
//                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<VisitorLog> visitorLogList = visitorLogQueryRepository.findAllByType(introPagePS.getId());
        return visitorLogList.stream()
                .map(s -> ExcelOutDTO.VisitorOutDTO.builder()
                        .deviceType(s.getDeviceType())
                        .type(excelVisitorInDTO.getType().equals("VIEW") ? s.getKeyword() : s.getSharingCode())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
