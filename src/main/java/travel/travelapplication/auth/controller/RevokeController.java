package travel.travelapplication.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import travel.travelapplication.auth.dto.ResponseDto;
import travel.travelapplication.auth.service.RevokeService;

@Controller // @RestController
@RequiredArgsConstructor
public class RevokeController {

    private final RevokeService revokeService;

    @DeleteMapping("/api/oauth2/revoke/google")
    public ResponseEntity<ResponseDto<String>> revokeGoogleAccount(@RequestHeader("Authorization") String accessToken) {
//        ResponseEntity<String> responseEntity = revokeService.deleteGoogleAccount(accessToken);
        revokeService.deleteGoogleAccount(accessToken);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, HttpStatus.OK.toString(), "회원 탈퇴 성공"));
    }

    @DeleteMapping("/api/oauth2/revoke/naver")
    public ResponseEntity<ResponseDto<String>> revokeNaverAccount(@RequestHeader("Authorization") String accessToken) {
//        ResponseEntity<String> responseEntity = revokeService.deleteNaverAccount(accessToken);
        revokeService.deleteNaverAccount(accessToken);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, HttpStatus.OK.toString(), "회원 탈퇴 성공"));
    }
}
