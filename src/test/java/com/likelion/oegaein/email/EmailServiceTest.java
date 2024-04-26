package com.likelion.oegaein.email;

import com.likelion.oegaein.domain.email.dto.EmailMessage;
import com.likelion.oegaein.domain.email.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class EmailServiceTest {
//    private final StopWatch stopWatch = new StopWatch("emailTest");;
//    @Autowired
//    private EmailService emailService;
//    @Test
//    public void 이메일_전송_성능_테스트(){
//        // given
//        EmailMessage emailMessage = EmailMessage.builder()
//                .to("shg0102kr@hufs.ac.kr")
//                .subject("이메일 성능 테스트")
//                .message("테스트 제목")
//                .build();
//        // when & then
//        stopWatch.start("Async email send");
//        emailService.sendMail(emailMessage, "matchingrequest");
//        stopWatch.stop();
//
//        stopWatch.start("Sync email send");
//        emailService.noAsyncSendMail(emailMessage, "matchingrequest");
//        stopWatch.stop();
//        System.out.println(stopWatch.prettyPrint());
//    }
}
