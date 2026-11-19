package com.example.daetaserver.user;

import com.example.daetaserver.user.presentation.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTests {
    @Test
    void deleteMeWithdrawsTheAuthenticatedUser() throws Exception {
        UserService service = mock(UserService.class);
        MockMvcBuilders.standaloneSetup(new UserController(service)).build()
                .perform(delete("/user/me").header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());
        verify(service).withdraw("Bearer token");
    }
}
