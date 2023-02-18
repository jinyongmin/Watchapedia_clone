package com.watcha.watchapedia.controller.page;

import com.watcha.watchapedia.model.dto.*;
import com.watcha.watchapedia.dto.response.UserResponse;
import com.watcha.watchapedia.model.entity.*;
import com.watcha.watchapedia.model.entity.type.FormStatus;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.NoticeApiRequest;
import com.watcha.watchapedia.model.network.request.QnaRequest;
import com.watcha.watchapedia.model.network.response.*;
import com.watcha.watchapedia.model.repository.*;
import com.watcha.watchapedia.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PageController {
    @Autowired
    public MovieApiLogicService movieApiLogicService;

    @Autowired
    public TvApiLogicService tvApiLogicService;

    @Autowired
    public BookApiLogicService bookApiLogicService;

    @Autowired
    public WebtoonApiLogicService webtoonApiLogicService;

    @Autowired
    public AdminApiLogicService adminApiLogicService;

    @Autowired
    public NoticeApiLogicService noticeApiLogicService;

    @Autowired
    public final GlobalMethodService globalMethodService;

    @Autowired
    public QnaService qnaService;

    @Autowired
    public CharacterApiLogicService characterApiLogicService;

    @Autowired
    public AdvertiseApiLogicService advertiseApiLogicService;


    //로그인을 하지 않고 url로 관리페이지로 뚥고 들어오는 것을 방지 (로그인으로 돌려보냄)
    //* 매개변수 첫번째 : HttpServletRequest 객체
    public ModelAndView loginCheck(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            System.out.println("세션이 없습니다");
            return new ModelAndView("/0_login/Login");
        }else{
            System.out.println("로그인 세션있음");
        }
        return null;
    }

    //로그인 이후, 세션정보를 페이지 thymeleaf에 전달하는 메소드
    //* 매개변수 첫번째 : HttpServletRequest 객체
    //* 매개변수 두번째 : ModelAndView Templates 경로
    public ModelAndView loginInfo(HttpServletRequest request, String view){
        HttpSession session = request.getSession(false);
        Long adminIdx = null;
        String adminId = null;
        String adminName = null;
        String adminType = null;
        if(session != null){
            adminIdx = (Long)session.getAttribute("adminIdx");
            adminId = (String)session.getAttribute("adminId");
            adminName = (String)session.getAttribute("adminName");
            adminType = (String)session.getAttribute("adminType");
            System.out.println(adminIdx + adminId + adminName + adminType);
            return new ModelAndView(view)
                    .addObject("adminIdx",adminIdx)
                    .addObject("adminId",adminId)
                    .addObject("adminName",adminName)
                    .addObject("adminType",adminType);
        }else{
            return new ModelAndView(view);
        }
    }

    //map.addAttribute를 사용해서 넘기는 방식에 세션정보 같이 담아줌
    //* 매개변수 첫번째 : HttpServletRequest 객체
    //* 매개변수 두번째 : ModelMap 객체
    public ModelMap loginModelInfo(HttpServletRequest request, ModelMap map){
        HttpSession session = request.getSession(false);
        if(session != null){
            map.addAttribute("adminIdx",(Long)session.getAttribute("adminIdx"));
            map.addAttribute("adminId",(String)session.getAttribute("adminId"));
            map.addAttribute("adminName",(String)session.getAttribute("adminName"));
            map.addAttribute("adminType",(String)session.getAttribute("adminType"));
            return map;
        }else{
            return null;
        }
    }

    //로그인 페이지로 이동
    @PostMapping(path="/login")
    public ModelAndView login(HttpServletRequest request){
        //이미 로그인된 상태에서, 로그인 페이지로 들어오면 메인으로 돌려보냄
        HttpSession session = request.getSession(false);
        if(session != null){
            return loginInfo(request, "forward:/notice");
        }

        return new ModelAndView("/0_login/Login");
    }

    //회원인증 절차 (Servlet 방식)
    @PostMapping(path="/loginOk")
    public String loginOk(HttpServletRequest request, String adminId, String adminPw){
        //id, pw 일치하는 계정을 찾아서 Header타입 저장
        Header<AdminApiResponse> accountCheck = adminApiLogicService.read(adminId, adminPw);

        //일치하는 계정의 data가 있으면 세션 저장!
        if(accountCheck.getData() != null){
            HttpSession session = request.getSession();
            Long adminIdx = accountCheck.getData().getAdminIdx();
            String adminName = accountCheck.getData().getAdminName();
            String adminType = accountCheck.getData().getAdminType();
            session.setAttribute("adminIdx", adminIdx); //해당 관리자의 행적을 기록하기 위한 관리자Idx
            session.setAttribute("adminId", adminId);
            session.setAttribute("adminName", adminName);
            session.setAttribute("adminType", adminType);   //인사관리자, 일반관리자 기능이 달라서 필요
            //세션을 저장하고 메인화면으로 이동
            return "redirect:/";
        }else{
            //로그인 실패 => 로그인 페이지로 다시 이동
            return "forward:/login";
        }
    }

    //로그아웃 기능
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    //메인화면
    @GetMapping(path="")
    public ModelAndView index(HttpServletRequest request){
        //Login한 상태이면, 사용자 정보를 HTML로 넘겨줌
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "forward:/notice");
    }

    //공지사항
    @GetMapping(path="/notice")
    public ModelAndView notice(HttpServletRequest request){
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        List<NoticeApiResponse> notice = userService.noticeAll();
        return loginInfo(request, "/1_notice/Notice").addObject("notices",noticeApiLogicService.noticeList()).addObject("notice",notice);
    }


    @GetMapping(path="/notice_edit/{ntcIdx}")
    public ModelAndView notice_edit(@PathVariable Long ntcIdx,HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        Header<NoticeApiResponse> api = noticeApiLogicService.read(ntcIdx);
        return loginInfo(request, "/1_notice/Notice_Edit").addObject("notice",api.getData());
    }

    @GetMapping(path="/notice_view/{ntcIdx}")
    public ModelAndView notice_view(@PathVariable Long ntcIdx, HttpServletRequest request){
        Header<NoticeApiResponse> api = noticeApiLogicService.read(ntcIdx);
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/1_notice/Notice_View").addObject("notice",api.getData());
    }



    @GetMapping(path="/notice_write")
    public ModelAndView notice_write(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/1_notice/Notice_Write");
    }

    //noticeOk 시작!
//    @PostMapping(path = "/noticeOk")
//    public String noticeOk(MultipartFile file, String ntcTitle, String ntcText, String ntcBtnText, String ntcBtnColor){
//
//        String fileName = file.getOriginalFilename();
//        System.out.println("fileName : " + fileName);
//        String filePath = "C:\\image\\"+fileName;
//
//
//        try {
//            //폴더 생성
//            String folderPath = "C:\\image";
//            File folder = new File(folderPath);
//
//            if(!folder.exists()){
//                folder.mkdir();
//            }
//
//            FileOutputStream fos = new FileOutputStream(filePath);
//            InputStream is = file.getInputStream();
//            int readCount = 0;
//            byte[] buffer = new byte[1024];
//            while((readCount = is.read(buffer)) != -1){
//                fos.write(buffer, 0, readCount);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Catch문은 탈출했어...");
//
//        NoticeApiRequest noticeApiRequest = NoticeApiRequest.builder()
//                .ntcTitle(ntcTitle)
//                .ntcText(ntcText)
//                .ntcImagepath(filePath)
//                .ntcBtnColor(ntcBtnColor)
//                .ntcBtnText(ntcBtnText)
//                .build();
//
//        noticeApiLogicService.create(Header.OK(noticeApiRequest));
//
//
//
//
//
//        return "redirect:/";
//    }
    //noticeOk 끝!
















    // qna 리스트
    @GetMapping(path="/qna")
    public String qna(ModelMap map, HttpServletRequest request){
        loginModelInfo(request,map);

        map.addAttribute("qnas", qnaService.searchQnas());
        return "/2_qna/QnA";
    }

    // qna 리스트 답글 작성 부분
    final QnaRepository qnaRepository;
    @GetMapping(path="/qna/{qnaIdx}")
    public String qnadetail(@PathVariable Long qnaIdx, ModelMap map, HttpServletRequest request){
        loginModelInfo(request,map);    //세션정보가 여기 map객체에 담겨짐
        Optional<Qna> qna = qnaRepository.findById(qnaIdx);
        QnaResponse qnaResponse = QnaResponse.from(QnaDto.from(qna.get()));
        map.addAttribute("qna", qnaResponse);
        return "/2_qna/QnA_Reply";
    }

    // qna 답글 완료 부분
    @GetMapping("/qnaview")
    public String QnaView(ModelMap map, HttpServletRequest request){
        loginModelInfo(request,map);
        map.addAttribute("view" , FormStatus.CREATE);
        return "/2_qna/QnA_View";
    }

    @PostMapping ("/qnaview")
    public String postqnaview(QnaRequest qnaRequest, ModelMap map, HttpServletRequest request) {
        loginModelInfo(request,map);
        qnaService.saveQna(qnaRequest.toDto());
        return "redirect:/qna";
    }

    // qna 답글 완료 데이터 보내기
    @GetMapping("/qna/{qnaIdx}/qnaview")
    public String updateQnaVieW(@PathVariable Long qnaIdx, ModelMap map, HttpServletRequest request){
        loginModelInfo(request,map);
        QnaResponse qna = QnaResponse.from(qnaService.getQna(qnaIdx));
        map.addAttribute("qna", qna);
        map.addAttribute("formStatus", FormStatus.UPDATE);
        return "/2_qna/QnA_View";
    }

    // qna 답글 완료 데이터 보내기





    /*   @RequestParam Spring MVC에서 쿼리 스트링 정보를 쉽게 가져오는데 사용할 수 있다,
    적용된 필드가 없으면 에러가 발생할 수 있지만 @RequestParam(required = false)를 사용하여
    required 속성을 추가하면 해당 필드가 쿼리스트링에 존재하지 않아도 예외가 발생하지 않는다
    String qnaText를 html 부분 name = qnaText로 두고 해당 정보를 qnaview로 넘겨줄 수 있다
     */

    @GetMapping(path="/contents/book")
    public ModelAndView book(HttpServletRequest request){
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/book/book").addObject("books",bookApiLogicService.bookList());
    }
    @GetMapping(path="/contents/book_edit/{bookIdx}")
    public ModelAndView bookEdit(@PathVariable Long bookIdx,HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        // 로그인 Check 끝!
        Header<BookApiResponse> api = bookApiLogicService.read(bookIdx);
        return loginInfo(request, "/3_contents/book/book_edit").addObject("book",api.getData());
//        return new ModelAndView("/3_contents/book/book_edit");
    }

    @GetMapping(path="/contents/book_write")
    public ModelAndView bookWrite(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        // 로그인 Check 끝!
        return loginInfo(request, "/3_contents/book/book_write");
    }
    @GetMapping(path="/contents/book_detail/{bookIdx}")
    public ModelAndView bookDetail(@PathVariable Long bookIdx,HttpServletRequest request){
        Header<BookApiResponse> api = bookApiLogicService.read(bookIdx);
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/book/book_detail").addObject("book",api.getData());
    }



    @GetMapping(path="/contents/movie")
    public ModelAndView movie(HttpServletRequest request){
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/movie/movie").addObject("movies",movieApiLogicService.movieList());
    }
    @GetMapping(path="/contents/movie_edit/{movIdx}")
    public ModelAndView movieEdit(@PathVariable Long movIdx, HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        Header<MovieApiResponse> api = movieApiLogicService.read(movIdx);
        return loginInfo(request, "/3_contents/movie/movie_edit").addObject("movie",api.getData());
    }
    @GetMapping(path="/contents/movie_write")
    public ModelAndView movieWrite(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/movie/movie_write");
    }
    @GetMapping(path="/contents/movie_detail/{movIdx}")
    public ModelAndView movieDetail(@PathVariable Long movIdx, HttpServletRequest request){
        Header<MovieApiResponse> api = movieApiLogicService.read(movIdx);
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/movie/movie_detail").addObject("movie",api.getData());
    }


    @GetMapping(path="/contents/tv")
    public ModelAndView tv(HttpServletRequest request){
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/tv/tv").addObject("tvs",tvApiLogicService.tvList());
    }

    @GetMapping(path="/contents/tv_edit/{tvIdx}")
    public ModelAndView tvEdit(@PathVariable Long tvIdx,HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        Header<TvApiResponse> api = tvApiLogicService.read(tvIdx);
        return loginInfo(request, "/3_contents/tv/tv_edit").addObject("tv",api.getData());
    }
    @GetMapping(path="/contents/tv_write")
    public ModelAndView tvWrite(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/tv/tv_write");
    }
    @GetMapping(path="/contents/tv_detail/{tvIdx}")
    public ModelAndView tvDetail(@PathVariable Long tvIdx,HttpServletRequest request){
        Header<TvApiResponse> api = tvApiLogicService.read(tvIdx);
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/tv/tv_detail").addObject("tv",api.getData());
    }



    @GetMapping(path="/contents/webtoon")
    public ModelAndView webtoon(HttpServletRequest request){
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/webtoon/webtoon").addObject("webtoons",webtoonApiLogicService.webtoonList());

    }
    @GetMapping(path="/contents/webtoon_edit/{webIdx}")
    public ModelAndView webtoonEdit(@PathVariable Long webIdx,HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        Header<WebtoonApiResponse> api = webtoonApiLogicService.read(webIdx);
        return loginInfo(request, "/3_contents/webtoon/webtoon_edit").addObject("webtoon",api.getData());
    }
    @GetMapping(path="/contents/webtoon_write")
    public ModelAndView webtoonWrite(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/webtoon/webtoon_write");
    }
    @GetMapping(path="/contents/webtoon_detail/{webIdx}")
    public ModelAndView webtoonDetail(@PathVariable Long webIdx,HttpServletRequest request){
        Header<WebtoonApiResponse> api = webtoonApiLogicService.read(webIdx);
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/3_contents/webtoon/webtoon_detail").addObject("webtoon",api.getData());
    }



    private final ReportApiLogicService reportApiLogicService;
    private final ReportRepository reportRepository;


    @GetMapping(path="/comment/report_page")
    public ModelAndView report(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }

        List<ReportDto> reportDtos = reportApiLogicService.findAllReport();
        List<ReportResponseDto> responseDtos = new ArrayList<>();
        for(ReportDto r : reportDtos){
            if(r.reportCommType().equals("comm")){
                String comm = "댓글";
                responseDtos.add(ReportResponseDto.from(r, comm));
            }else{
                String recomm = "대댓글";
                responseDtos.add(ReportResponseDto.from(r, recomm));
            }
        }
        //대기중, 처리완료에 따른 List 생성 (th:if로 하려고 했는데 BootStrap 표 CSS가 깨짐)
        List<ReportResponseDto> waitList = new ArrayList<>();
        List<ReportResponseDto> completeList = new ArrayList<>();
        for(ReportResponseDto r : responseDtos){
            if(r.reportProcessing().equals("대기중")){
                waitList.add(r);
            }else{
                String[] status = r.reportProcessing().split(",");
                completeList.add(ReportResponseDto.from(r,status[0]));
            }
        }

        return loginInfo(request, "/4_comment/reported/report_page")
                .addObject("waitList", waitList)
                .addObject("completeList",completeList);
    }

    @GetMapping(path="/comment/reportdetail_comment/{reportId}")
    public ModelAndView reportdetailcomment(@PathVariable Long reportId, HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        Report report = reportRepository.getReferenceById(reportId);

        if(report.getReportProcessing() == null){
            report.setReportProcessing("대기중");
        }

        ReportDetailDto reportDetailDto = ReportDetailDto.from(report);

        return loginInfo(request, "/4_comment/reported/reportdetail_comment").addObject("report", reportDetailDto);
    }

    @GetMapping(path="/comment/reportdetail_reply/{reportId}")
    public ModelAndView reportdetailreply(@PathVariable Long reportId, HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }

        Report report = reportRepository.getReferenceById(reportId);

        if(report.getReportProcessing() == null){
            report.setReportProcessing("대기중");
        }

        ReportDetailDto reportDetailDto = ReportDetailDto.from(report);

        return loginInfo(request, "/4_comment/reported/reportdetail_reply").addObject("report", reportDetailDto);
    }

    // comment 리스트 출력
    private final CommentService commentService;
    @GetMapping(path="/comment/search_list")

    public String comment(HttpServletRequest request, ModelMap map){
        loginModelInfo(request,map);
        List<CommentResponse> comments = commentService.searchComments().stream().map(CommentResponse::from).toList();

        //글로벌메소드
        //매개변수 : List<CommentResponse>
        //본인 content_type(movie, tv, book, webtoon)에 해당하는 테이블에서만 select해서 제목을 빠르게 받아옴
        //List<CommentResponse> 반환
        comments = globalMethodService.getListTitle(comments);
        map.addAttribute("comments", comments);
        return "/4_comment/search/commentSearchList";
    }


    // commentDetail 출력 (내용, 이미지)
    final CommentRepository commentRepository;

    final RecommentRepository recommentRepository;

    final LikeRepository likeRepository;

    @GetMapping(path="/comment/{commentIdx}")
    public String commentdetail(@PathVariable Long commentIdx, ModelMap map, HttpServletRequest request){
        //로그인 세션정보 전달
        loginModelInfo(request,map);
        //Comment 테이블에 해당 idx 열을 받아와서 CommentResponseDto에 담음
        Optional<Comment> comment = commentRepository.findById(commentIdx);
        CommentResponse commentResponse = CommentResponse.from(CommentDto.from(comment.get()));

        //글로벌메소드
        // 매개변수 : CommentResponse
        // List<String>객체.get(0) 리턴 : postUrl (컨텐츠 포스터 이미지주소)
        // List<String>객체.get(1) 리턴 : title (컨텐츠 제목)
        List<String> str = globalMethodService.getPostUrlAndTitle(commentResponse);

        //현재일자 - 댓글 등록일자 구하기
        List<RecommentResponseDto> recommentResponseDtoList = new ArrayList<>();
        List<Recomment> recommentList = comment.get().getRecommentList();
        for(Recomment r : recommentList){
            LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
            LocalDateTime regDay = r.getRecommRegDate().truncatedTo(ChronoUnit.DAYS);
            Long sicha = ChronoUnit.DAYS.between(regDay,today);
            String dayAgo = "";
            if(sicha > 0){
                dayAgo = sicha + "일전";
            }else{
                dayAgo = "오늘";
            }
            recommentResponseDtoList.add(RecommentResponseDto.dayAgo(RecommentDto.from(r),dayAgo));
            System.out.println(RecommentResponseDto.dayAgo(RecommentDto.from(r),dayAgo));
        }

        commentResponse = CommentResponse.inserPosterUrl(commentResponse, str.get(0));  //메소드로 받아온 postUrl
        commentResponse = CommentResponse.insertTitle(commentResponse, str.get(1)); //메소드로 받아온 title
        map.addAttribute("comment", commentResponse);
        map.addAttribute("recomment", recommentResponseDtoList);

        return "/4_comment/search/commentSearchDetail";
    }
    @GetMapping(path="/character_detail/{perIdx}")
    public ModelAndView characterdetail(@PathVariable Long perIdx, HttpServletRequest request){
        Header<CharacterApiResponse> api = characterApiLogicService.read(perIdx);
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/5_character/characterdetail").addObject("character",api.getData());
    }
    @GetMapping(path="/character_manage")
    public ModelAndView charactermanage(HttpServletRequest request){
// 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        System.out.println("페이지컨트롤러에는 잘들어오는데");

        return loginInfo(request, "/5_character/charactermanage").addObject("characters",characterApiLogicService.characterList());

    }

    //인물 시작
    @GetMapping(path="/character_register")
    public ModelAndView characterregister(HttpServletRequest request){
// 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/5_character/characterregister");
    }
    @GetMapping(path="/character_modify/{perIdx}")
    public ModelAndView charactermodify(@PathVariable Long perIdx,HttpServletRequest request){
// 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        Header<CharacterApiResponse> api = characterApiLogicService.read(perIdx);
        return loginInfo(request, "/5_character/charactermodify").addObject("character",api.getData());
    }

    // 멤버 디테일
    final UserRepository userRepository;
    @GetMapping(path="/member/{userIdx}")
    public String memberdetail(@PathVariable Long userIdx, ModelMap map, HttpServletRequest request){
        loginModelInfo(request,map);
        Optional<User> user = userRepository.findById(userIdx);
        UserResponse userResponse = UserResponse.from(UserDto.from(user.get()));
        map.addAttribute("user", userResponse);
        return "/6_member/memberdetail";
    }

    // 멤버 리스트
    final UserService userService;
    private final MovieRepository movieRepository;
    private final TvRepository tvRepository;
    private final BookRepository bookRepository;
    private final WebtoonRepository webtoonRepository;

    @GetMapping(path="/member")
    public String membermanage(HttpServletRequest request, ModelMap map){
        loginModelInfo(request,map);
        List<UserResponse> users = userService.searchUsers().stream().map(UserResponse::from).toList();
        map.addAttribute("users", users);
        return "/6_member/membermanage";
    }

    // 유저 인플루언서 수정
    @GetMapping("/member/{findAllReportx}/{userType}")
    public String updateUser(
             @PathVariable Long findAllReportx,
             @PathVariable String userType,
             ModelMap map,
             HttpServletRequest request){
        loginModelInfo(request,map);
        Optional<User> user = userRepository.findById(findAllReportx);
        user.ifPresent(
                selectUser -> {
                    selectUser.setUserType(userType);
                    userRepository.save(selectUser);
                }
        );
        return "redirect:/member/"+findAllReportx;
    }


    //광고
    @GetMapping(path="/advertisement")
    public ModelAndView admain(HttpServletRequest request){
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/7_advertisement/Advertisement").addObject("advertises",advertiseApiLogicService.advertiseList());
    }

    @GetMapping(path="/advertisement_write")
    public ModelAndView adwrite(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/7_advertisement/Advertisement_Write");
    }


    @GetMapping(path="/advertisement_edit/{adIdx}")
    public ModelAndView adedit(@PathVariable Long adIdx, HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        Header<AdvertiseApiResponse> api = advertiseApiLogicService.read(adIdx);
        return loginInfo(request, "/7_advertisement/Advertisement_Edit").addObject("advertise",api.getData());
    }


    @GetMapping(path="/advertisement_view/{adIdx}")
    public ModelAndView adview(@PathVariable Long adIdx, HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        Header<AdvertiseApiResponse> api = advertiseApiLogicService.read(adIdx);
        return loginInfo(request, "/7_advertisement/Advertisement_View").addObject("advertise",api.getData());
    }


    @GetMapping(path="/hradmin/{adminIdx}")
    public String hradminaccountdetail(@PathVariable Long adminIdx, ModelMap map, HttpServletRequest request){
        loginModelInfo(request,map);
        //idx로 관리자회원 상세정부를 가져와서 map에 저장하는 내용
        Optional<AdminUser> adminUser = adminRepository.findById(adminIdx);
        AdminUserResponse adminUserResponse = AdminUserResponse.from(AdminUserDto.from(adminUser.get()));
        map.addAttribute("adminUser", adminUserResponse);
        return "/8_admin/hradmin/accountdetail";
    }
    @GetMapping(path="/hradmin/createaccount")
    public ModelAndView hradmincreateaccount(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/8_admin/hradmin/createaccount");
    }
    @GetMapping(path="/hradmin/modifyaccount")
    public ModelAndView hradminmodifyaccount(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        HttpSession session = request.getSession();
        Header<AdminApiResponse> api = adminApiLogicService.read((Long)session.getAttribute("adminIdx"));
        return loginInfo(request, "/8_admin/admin/Myinfo").addObject("myinfo",api.getData());
    }

    @Autowired
    private final AdminService adminService;
    private final AdminRepository adminRepository;

    @GetMapping(path="/hradmin/searchaccount")
    public String hradminsearchaccount(HttpServletRequest request, ModelMap map){
        //로그인 세션정보 전달!
        loginModelInfo(request,map);

        // admin계정 리스트 전달!
        List<AdminUserResponse> admins = adminService.findAllReport().stream().map(AdminUserResponse::from).toList();
        map.addAttribute("admins",admins);
        return "/8_admin/hradmin/searchaccount";
    }

    @GetMapping(path="/admin_myinfo")
    public ModelAndView myinfo(HttpServletRequest request){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        HttpSession session = request.getSession();
        Header<AdminApiResponse> api = adminApiLogicService.read((Long)session.getAttribute("adminIdx"));
        return loginInfo(request, "/8_admin/admin/Myinfo").addObject("myinfo",api.getData());
    }

    @GetMapping(path="/admin_myinfomodify/{adminIdx}")
    public ModelAndView myinfomodify(HttpServletRequest request,@PathVariable Long adminIdx){
        // 로그인 Check 시작!
        ModelAndView loginCheck = loginCheck(request);
        if(loginCheck != null){
            return loginCheck;
        }
        return loginInfo(request, "/8_admin/admin/Myinfomodify").addObject("adminIdx",adminIdx);
    }
    @GetMapping(path="/hradmin/updateaccount/{adminIdx}")
    public String updateaccount(@PathVariable Long adminIdx, HttpServletRequest request, ModelMap map){
        loginModelInfo(request,map);
        AdminUser adminUser = adminRepository.getReferenceById(adminIdx);
        AdminUserResponse adminUserResponse = AdminUserResponse.from(AdminUserDto.from(adminUser));
        map.addAttribute("adminUser", adminUserResponse);
        return "/8_admin/hradmin/updateaccount";
    }

}
