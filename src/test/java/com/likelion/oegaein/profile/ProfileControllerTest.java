package com.likelion.oegaein.profile;

import com.likelion.oegaein.config.TestSecurityConfig;
import com.likelion.oegaein.domain.member.controller.ProfileApiController;
import com.likelion.oegaein.domain.member.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProfileApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import(TestSecurityConfig.class)
public class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    // 테스트 코드 넘 어렵다
}