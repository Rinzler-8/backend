package com.vti.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.ResetDTO;
import com.vti.entity.ERole;
import com.vti.entity.RefreshToken;
import com.vti.entity.Role;
import com.vti.entity.User;
import com.vti.exceptions.AppException;
import com.vti.exceptions.TokenRefreshException;
import com.vti.payload.request.LoginRequest;
import com.vti.payload.request.RefreshTokenRequest;
import com.vti.payload.request.SignupRequest;
import com.vti.payload.response.MessageResponse;
import com.vti.payload.response.TokenRefreshResponse;
import com.vti.payload.response.UserInfoResponse;
import com.vti.repository.RoleRepository;
import com.vti.repository.UserRepository;
import com.vti.security.jwt.JwtUtils;
import com.vti.security.service.IUserService;
import com.vti.security.service.RefreshTokenService;
import com.vti.security.service.UserDetailsImpl;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Value("${GenuineDignity.app.jwtSecret}")
	private String jwtSecret;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private IUserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")

	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		UserDetailsImpl userDetails;
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			userDetails = (UserDetailsImpl) authentication.getPrincipal();

		} catch (Exception ex) {
			throw new AppException(ex);
		}
//		if (userDetails.getBlockExpDate() != null && userDetails.getBlockExpDate().compareTo(new Date()) > 0) {
//			throw new AppException(ErrorResponseBase.USER_BLOCKED);
//		}
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
		String jwt = jwtUtils.generateJwtToken(authentication);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getRefreshToken());
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		System.out.println("doSW " + jwtCookie);
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
				.body(new UserInfoResponse(jwt, refreshToken.getRefreshToken(), userDetails.getId(),
						userDetails.getUsername(), userDetails.getEmail(), roles, userDetails.getStatus()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getMobile(),
				encoder.encode(signUpRequest.getPassword()));

		List<String> strRoles = signUpRequest.getRole();
		List<Role> roles = new ArrayList<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				ERole roleName = ERole.valueOf(role.toUpperCase());
				switch (roleName) {
				case ADMIN:
					Role adminRole = roleRepository.findByName(ERole.ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.USER)
							.orElseThrow(() -> new RuntimeException("Error: User Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRole(roles);
		userRepository.save(user);

		userService.registerUser(user);

		return new ResponseEntity<>("We have sent an email. Please check email to activate your account!",
				HttpStatus.OK);
	}

//	@PostMapping("/refreshtoken")
//	public ResponseEntity<?> refreshtoken(@Valid HttpServletRequest request) {
//		String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
//
//		if ((refreshToken != null) && (refreshToken.length() > 0)) {
//			return refreshTokenService.findByRefreshToken(refreshToken).map(refreshTokenService::verifyExpiration)
//					.map(RefreshToken::getUser).map(user -> {
//						ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
//						return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//								.body(new MessageResponse("Token is refreshed successfully!"));
//					}).orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
//		}
//		return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is empty!"));
//	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByRefreshToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtils.generateTokenFromEmail(user.getEmail());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}

	@GetMapping("/activeUser")
	// validate: check exists, check not expired
	public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {
		// active user
		userService.activeUser(token);

		return new ResponseEntity<>("Activate account successfully!", HttpStatus.OK);
	}

	// resend confirm
	@GetMapping("/userRegistrationConfirmRequest")
	// validate: email exists, email not active
	public ResponseEntity<?> resendConfirmRegistrationViaEmail(@RequestParam String email) {

		userService.sendConfirmUserRegistrationViaEmail(email);

		return new ResponseEntity<>("We have sent an email. Please check email to active account!", HttpStatus.OK);
	}

	// reset password confirm
	@GetMapping("/resetPasswordRequest")
	// validate: email exists, email not active
	public ResponseEntity<?> sendResetPasswordViaEmail(@RequestParam String email) {

		userService.resetPasswordViaEmail(email);

		return new ResponseEntity<>("We have sent an email. Please check email to reset password!", HttpStatus.OK);
	}

	// resend reset password
	@GetMapping("/resendResetPassword")
	// validate: email exists, email not active
	public ResponseEntity<?> resendResetPasswordViaEmail(@RequestParam String email) {

		userService.sendResetPasswordViaEmail(email);

		return new ResponseEntity<>("We have sent an email. Please check email to reset password!", HttpStatus.OK);
	}

	@PostMapping("/resetPassword")
	// validate: check exists, check not expired
	public ResponseEntity<?> resetPasswordViaEmail(@RequestParam String token, @RequestBody ResetDTO newPassword) {
		String pass = newPassword.getPassword();
		// reset password
		userService.resetPassword(token, pass);

		return new ResponseEntity<>(newPassword, HttpStatus.OK);
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principle.toString() != "anonymousUser") {
			int userId = ((UserDetailsImpl) principle).getId();
			refreshTokenService.deleteByUserId(userId);
		}

		ResponseCookie jwtCookie = jwtUtils.clearJwtCookie();
		ResponseCookie jwtRefreshCookie = jwtUtils.clearJwtRefreshCookie();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}
}
