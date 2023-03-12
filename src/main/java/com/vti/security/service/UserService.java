package com.vti.security.service;

import java.io.File;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.vti.entity.RegistrationUserToken;
import com.vti.entity.ResetPasswordToken;
import com.vti.entity.Status;
import com.vti.entity.User;
import com.vti.event.OnResetPasswordViaEmailEvent;
import com.vti.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.vti.exceptions.AppException;
import com.vti.exceptions.ErrorResponseBase;
import com.vti.form.AccountFormForUpdating;
import com.vti.repository.RegistrationUserTokenRepository;
import com.vti.repository.ResetPasswordTokenRepository;
import com.vti.repository.UserRepository;
import com.vti.specification.AccountSpecification;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private RegistrationUserTokenRepository registrationUserTokenRepository;

	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Account not found with username: " + username);
		} else {
			return UserDetailsImpl.build(user);
		}
	}

	@Override
	public Page<User> getAllAccounts(Pageable pageable, String search) {
		Specification<User> whereAccount = null;
		if (!StringUtils.isEmpty(search)) {
			AccountSpecification usernameSpecification = new AccountSpecification("username", "LIKE", search);
			AccountSpecification roleSpecification = new AccountSpecification("role", "LIKE", search);
			AccountSpecification emailSpecification = new AccountSpecification("email", "LIKE", search);
			whereAccount = Specification.where(usernameSpecification).or(roleSpecification).or(emailSpecification);
		}

		return userRepository.findAll(whereAccount, pageable); // findAll - phuong thuc co san cua JPA da duoc xay
																// dung san khi extends ben repository
	}

	@Override
	public void registerUser(User user) {
		createNewRegistrationUserToken(user);
		sendConfirmUserRegistrationViaEmail(user.getEmail());
	}

	@Override
	public User getAccountByID(int id) {
		return userRepository.getById(id);
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	public void createNewRegistrationUserToken(User user) {

		// create new token for confirm Registration
		final String newToken = UUID.randomUUID().toString();
		RegistrationUserToken token = new RegistrationUserToken(newToken, user);

		registrationUserTokenRepository.save(token);
	}

	@Override
	public void sendConfirmUserRegistrationViaEmail(String email) {
		eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean existsUserByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsUserByUsername(String userName) {
		return userRepository.existsByUsername(userName);
	}

	@Override
	public void activeUser(String token) {

		// get token
		RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);

		// active user
		User user = registrationUserToken.getUser();
		user.setStatus(Status.ACTIVE);
		userRepository.save(user);

		// remove Registration Account Token
		registrationUserTokenRepository.deleteById(registrationUserToken.getId());
	}

	@Override
	public void resetPasswordViaEmail(String email) {

		// find user by email
		User user = findUserByEmail(email);

		// remove token token if exists
		resetPasswordTokenRepository.deleteByUserId(user.getId());

		// create new reset password token
		createNewResetPasswordToken(user);

		// send email
		sendResetPasswordViaEmail(email);
	}

	@Override
	public void sendResetPasswordViaEmail(String email) {
		// find user by email
		User user = findUserByEmail(email);

		// remove token token if exists
		resetPasswordTokenRepository.deleteByUserId(user.getId());

		// create new reset password token
		createNewResetPasswordToken(user);
		eventPublisher.publishEvent(new OnResetPasswordViaEmailEvent(email));
	}

	private void createNewResetPasswordToken(User user) {

		// create new token for Reseting password
		final String newToken = UUID.randomUUID().toString();
		ResetPasswordToken token = new ResetPasswordToken(newToken, user);

		resetPasswordTokenRepository.save(token);
	}

	@Override
	public void resetPassword(String token, String newPassword) {
		// get token
		ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);

		// change password
		User user = resetPasswordToken.getUser();
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userRepository.save(user);

		// remove Reset Password
		resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());
	}

	@Override
	public void upBlockExpDate(int id, Integer date) {
		User user = userRepository.getById(id);
		Date dateBlock = addSeconds(new Date(), date);
		user.setBlockExpDate(dateBlock);
		userRepository.save(user);

	}

	@Override
	public void unBlockExpDate(int id) {
		User user = userRepository.getById(id);
		user.setBlockExpDate(null);
		userRepository.save(user);
	}

	@Override
	public void updateAccount(int id, AccountFormForUpdating form) {
		User account = userRepository.getById(id);
		account.setUsername(form.getUsername());
		account.setMobile(form.getMobile());
		account.setUrlAvatar(form.getUrlAvatar());
		account.setEmail(form.getEmail());
		userRepository.save(account);
	}

	@Override
	public ByteArrayResource getAvatar(int id) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file;

		Optional<User> optional = userRepository.findById(id);
		if (!optional.isPresent()) {
			throw new AppException(ErrorResponseBase.NOT_FOUND);
		}
		User user = optional.get();
		if (StringUtils.isEmpty(user.getUrlAvatar())) {
			file = new File(classLoader.getResource("avatar/default.png").getFile());
		} else {
			file = new File(classLoader.getResource(user.getUrlAvatar()).getFile());
		}
		try {
			byte[] templateContent = FileCopyUtils.copyToByteArray(file);
			return new ByteArrayResource(templateContent);
		} catch (Exception ex) {
			throw new AppException(ex);
		}
	}

	public static Date addSeconds(Date date, Integer seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
}
