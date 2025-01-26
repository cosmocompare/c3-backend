package com.c3.login.controller;

import com.c3.login.dto.OauthTokenDto;
import com.c3.login.entity.User;
import com.c3.login.service.UserService;
import com.c3.login.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 카카오 로그인
    @GetMapping("/auth/kakao/callback")
    public String kakaoLogin(@RequestParam("code") String code) { // Data를 리턴해주는 컨트롤러 함수

        // 카카오 로그인 시 받은 'code'로 액세스 토큰을 가져옴
        OauthTokenDto oauthTokenDto = userService.getToken(code);

        if (oauthTokenDto != null) {
            // 액세스 토큰으로 카카오 사용자 정보 가져오기
            String accessToken = oauthTokenDto.getAccessToken();

            log.info(accessToken);

            UserDto userDto = userService.getUserInfo(accessToken);

            // 사용자 정보를 DB에 저장 (이미 저장된 사용자가 있으면 그 정보를 반환)
            User user = userService.userSaved(userDto);

            System.out.println(user);

            // 로그인 성공 후 메인으로 리다이렉트
            return "redirect:/";
        }

        // 토큰 가져오기 실패 시 에러 메세지
        return "로그인 실패!";
    }

    // 카카오 로그아웃
    @GetMapping("/auth/kakao/logout")
    public String logout(@RequestParam("accessToken") String accessToken, Model model) {

        log.info("로그아웃 요청됨, accessToken: {}", accessToken);
        // 로그아웃을 웨한 액세스 토큰 가져오기
        OauthTokenDto oauthTokenDto = new OauthTokenDto(accessToken);

        // 로그아웃 서비스 호출
        String logout = userService.logout(oauthTokenDto);

        return "redirect:/";
    }

}
