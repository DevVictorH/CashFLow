package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.*;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.UserRole;
import com.devvictorh.cashflow.exceptions.BusinessException;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.security.TokenService;
import com.devvictorh.cashflow.service.EmailService;
import com.devvictorh.cashflow.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    UserEntity user;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    TokenService tokenService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserRepository repository;

    @MockitoBean
    private UserService service;

    @MockitoBean
    private EmailService emailService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@email.com");
        user.setPassword("123");
        user.setRole(UserRole.ADMIN);
    }

    @Test
    void shouldLogin() throws Exception{
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO(
                "victor@email.com","123"
        );
        var authentication = Mockito.mock(org.springframework.security.core.Authentication.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(authentication);

        Mockito.when(tokenService.generateToken(user))
                .thenReturn("fake-token");

        Mockito.when(repository.findByEmail(authenticationRequestDTO.email())).thenReturn(null);

        String json = objectMapper.writeValueAsString(authenticationRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());

    }

    @Test
    void shouldRegister() throws Exception{
        UserRequestDTO userRequestDTO = new UserRequestDTO("Victor", "victor@email.com", "123","ADMIN");

        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(null);

        String json = objectMapper.writeValueAsString(userRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldThrowErrorWhenRegister() throws Exception{
        UserRequestDTO userRequestDTO = new UserRequestDTO("Victor", "victor@email.com", "123","ADMIN");

        Mockito.when(service.saveUser(Mockito.any(UserRequestDTO.class)))
                .thenThrow(new BusinessException("Email já cadastrado"));

        String json = objectMapper.writeValueAsString(userRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }
    @Test
    void shouldRequestPasswordRecovery() throws Exception {

        RecoveryPasswordRequestDTO request =
                new RecoveryPasswordRequestDTO("victor@email.com");

        Mockito.when(repository.findByEmail("victor@email.com"))
                .thenReturn(user);

        String json = objectMapper.writeValueAsString(request);

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/recovery-password")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());

        assertNotNull(user.getCodigoRecuperacao());
        assertEquals(6, user.getCodigoRecuperacao().length());

        assertNotNull(user.getCodigoExpiracao());

        Mockito.verify(repository).save(user);

        Mockito.verify(emailService).sendEmail(
                Mockito.eq("victor@email.com"),
                Mockito.eq("Recuperação de Senha"),
                Mockito.contains(user.getCodigoRecuperacao())
        );
    }

    @Test
    void shouldReturnNotFoundWhenEmailDoesNotExist() throws Exception {

        RecoveryPasswordRequestDTO request =
                new RecoveryPasswordRequestDTO("naoexiste@email.com");

        Mockito.when(repository.findByEmail("naoexiste@email.com"))
                .thenReturn(null);

        String json = objectMapper.writeValueAsString(request);

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/recovery-password")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isNotFound());

        Mockito.verify(repository, Mockito.never())
                .save(Mockito.any());

        Mockito.verify(emailService, Mockito.never())
                .sendEmail(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldRedefinePassword() throws Exception {

        user.setCodigoRecuperacao("123456");
        user.setCodigoExpiracao(
                java.time.LocalDateTime.now().plusMinutes(10)
        );

        ChangePasswordRequestDTO request =
                new ChangePasswordRequestDTO(
                        "victor@email.com",
                        "123456",
                        "novaSenha123"
                );

        Mockito.when(repository.findByEmail("victor@email.com"))
                .thenReturn(user);

        Mockito.when(passwordEncoder.encode("novaSenha123"))
                .thenReturn("senhaCriptografada");

        String json = objectMapper.writeValueAsString(request);

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/redefine-password")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());

        assertEquals("senhaCriptografada", user.getPassword());

        assertNull(user.getCodigoRecuperacao());
        assertNull(user.getCodigoExpiracao());

        Mockito.verify(passwordEncoder)
                .encode("novaSenha123");

        Mockito.verify(repository)
                .save(user);
    }
}