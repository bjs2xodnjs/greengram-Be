package com.green.greengram.config.security;

import com.green.greengram.config.jwt.JwtTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("request.getRequestURI(): {}", request.getRequestURI());
        //토큰 처리
        Authentication authentication = jwtTokenManager.getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication); //인증 처리
        }
        filterChain.doFilter(request, response); //다음 필터에게 req, res 넘기기
    }
}

/*
## 🍪 1. 쿠키 (Cookie)

- **역할**: 브라우저에 작은 데이터(키-값)를 저장, 요청 시마다 서버로 전송해 사용자 식별.
- **저장 위치**: 클라이언트(브라우저).
- **장점**
    - 구현 간단, 초기 웹에서 사용 쉬움.
    - 서버는 별도 저장 공간 필요 없음.
- **단점**
    - 보안 취약 (위조·탈취 가능).
    - 용량 제한(약 4KB).
    - 클라이언트에서 쉽게 조작 가능.

---

## 📂 2. 세션 (Session)

- **역할**: 서버가 사용자 상태(State)를 저장.
    - 브라우저는 `session id`만 쿠키에 저장.
    - 서버는 Redis/DB/메모리에서 사용자 상태를 관리.
- **저장 위치**
    - 세션 ID: 클라이언트(쿠키).
    - 사용자 정보: 서버 저장소(DB, Redis 등).
- **장점**
    - 민감 데이터는 서버에 보관 → 보안 ↑.
    - 사용자 상태 관리 유리.
- **단점**
    - 서버 확장 시 세션 동기화 필요 (로드밸런싱 문제).
    - 유저 많으면 서버 부담 커짐.

---

## 🔑 3. JWT (JSON Web Token)

- **역할**: 로그인 시 서버가 사용자 정보 + 권한을 담은 토큰 발급.
    - 클라이언트가 요청마다 토큰 전송.
    - 서버는 검증만 하고 별도 상태 저장 없음 (Stateless).
- **저장 위치**
    - 클라이언트(LocalStorage, SessionStorage, Secure Cookie 등).
    - 서버 저장 필요 없음 (단, Refresh Token/블랙리스트는 Redis/DB 사용).
- **장점**
    - 서버 상태 저장 불필요 → 확장성 ↑.
    - MSA/클라우드/모바일 환경에 최적.
    - 권한·만료시간 등 포함 가능 → API 호출 편리.
- **단점**
    - 토큰 유출 시 만료까지 사용 가능 → 보안 위험.
    - 크기가 커서 요청 시 네트워크 비용 증가.
    - 토큰 발급 후 변경 불가 → 즉시 무효화(로그아웃 등) 어려움.

---

## 🗂 4. Redis / DB와의 관계

- **세션**:
    - 단일 서버일 땐 메모리 저장 가능.
    - 서버 여러 대 → Redis 같은 외부 스토리지로 공유.
- **JWT**:
    - 기본적으로 서버 저장 필요 없음.
    - 단, Refresh Token 관리나 블랙리스트 처리를 위해 Redis/DB 사용 가능.

---

## ⚖️ 비교 요약

| 구분 | 저장 위치 | 확장성 | 보안성 | 편리성 |
| --- | --- | --- | --- | --- |
| **쿠키** | 클라이언트 | 낮음 | 낮음 | 간단 |
| **세션** | 서버 + 클라이언트(세션ID) | 중간 (Redis 필요) | 높음 | 편리 |
| **JWT** | 클라이언트 | 높음 | 중간 (유출 시 위험) | 분산환경에 최적 |

---

👉 핵심:

- **쿠키** → 단순 식별.
- **세션** → 상태 기반 인증(서버 관리).
- **JWT** → 무상태 인증(확장성·분산환경 최적). 토큰은 문자열, 클라이언트로 전달

## <로그인 과정>

1. 아이디/비번 일치
2. 토큰에 담을 데이터A - JwtUser( 유저pk, Roles  )
    , 응답 데이터B - UserSignInRes ( 유저pk, 사진명)
3. AT/RT 발행(데이터A 포함)
4. AT/RT 보안 쿠키에 담는다.
5. 응답 데이터B 응답으로 처리

## <요청마다 - 인증 과정>

1. AT를 쿠키에서 뽑아낸다.
(AT가 null이라면  =  포함되어 있지 않다면 == 비로그인 상태)

2. 인증처리 x
(AT가 null 이 아니라면 포함되어 있다면 == 로그인 상태)

3. AT를 Head에서 뽑아낸다.
4. AT를 역직렬화로 데이터A를 뽑아낸다.
5. 데이터A로 Spring Security (SS 줄여서)에 맞게 인등처리를 한다.
6. SS는 요청URL과 인증 여부/인가에 맞는지 확인하고 문제가 없으면 계속 처리를 하고 문제가 있다면 에러를 터뜨린다.

AT - 인증/인가 처리 용도 (약 15분)

RT - AT 재발행 용도 ( 약 15일 )
 */