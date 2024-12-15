package c3.login.service;

import c3.login.dto.OauthTokenDto;
import c3.login.entity.KakaoProfile;
import c3.login.entity.User;
import c3.login.dto.UserDto;
import c3.login.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${kakao.client.id}")
    private String client_id;

    @Value("${kakao.redirect.uri}")
    private String redirect_uri;

    // 토큰을 받아오는 메서드
    public OauthTokenDto getToken(String code) {

        // POST 방식으로 key=value 데이터를 요청(카카오쪽으로)

        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // Http Body 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // 토큰 발급 요청 url
        String url = "https://kauth.kakao.com/oauth/token";

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                url,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String accessToken = null;

        try{
            // 응답을 JsonNode로 파싱하여 access_token을 추출
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            accessToken = jsonNode.get("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 액세스 토큰을 OauthTokenDto로 반환
        if (accessToken != null) {
            return new OauthTokenDto(accessToken); // OauthTokenDto에 액세스 토큰만 담아서 반환
        }

        return null; // 오류 발생 시 null 반환
    }

    public UserDto getUserInfo(String accessToken) {
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + accessToken);
        headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User 오브젝트 : id, username, profile_img
        System.out.println("카카오 아이디(번호): " + kakaoProfile.getId());
        System.out.println("카카오 닉네임: " + kakaoProfile.getProperties().getNickname());
        System.out.println("카카오 프로필 이미지: " + kakaoProfile.getProperties().getProfile_image());

        Long socialId = kakaoProfile.getId();
        String profile_nickname = kakaoProfile.getProperties().getNickname();
        String profile_img = kakaoProfile.getProperties().getProfile_image();

        return new UserDto(socialId, profile_nickname, profile_img);
    }

    public User userSaved(UserDto userDto){
        Long socialId = userDto.getSocialId();
        User user = (User) userRepository.findBySocialId(socialId).orElse(null);

        if(user == null){
            user = new User(userDto.getSocialId(), userDto.getProfile_nickname(),
                    userDto.getProfile_img());
            userRepository.save(user);
        }

        System.out.println("회원 아이디: " + user.getId());

        return user;
    }

    public String logout(OauthTokenDto oauthTokenDto) {

        String accessToken = oauthTokenDto.getAccessToken();

        log.info("로그아웃 요청 시작, 액세스 토큰: {}", accessToken);
        // 카카오 로그아웃 API 호출
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // HttpHeader를 하나의 오브젝트에 담기
        HttpEntity<String> logoutRequst = new HttpEntity<>(headers);

        // 로그아웃 요청 url
        String url = "https://kapi.kakao.com/v1/user/logout";

        try {
            // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
            ResponseEntity<String> response = rt.exchange(
                    url,
                    HttpMethod.POST,
                    logoutRequst,
                    String.class
            );

            log.info("카카오 로그아웃 응답: {}", response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("로그아웃 성공!");
                return "로그아웃 성공!";
            } else {
                log.error("로그아웃 실패! 상태 코드: {}", response.getStatusCode());
                return "로그아웃 실패!";
            }
        } catch (Exception e){
            log.error("카카오 로그아웃 API 호출 중 오류 발생: {}", e.getMessage());
            return "로그아웃 실패!";
        }

    }
}
