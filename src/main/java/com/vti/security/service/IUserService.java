package com.vti.security.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vti.entity.User;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForUpdating;

public interface IUserService extends UserDetailsService {
	public Page<User> getAllAccounts(Pageable pageable, String search);

	public User getAccountByID(int id);

	public User findByUsername(String username);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	User findUserByEmail(String email);

	User findUserByUsername(String username);

	void activeUser(String token);

	void sendConfirmUserRegistrationViaEmail(String email);

	boolean existsUserByEmail(String email);

	boolean existsUserByUsername(String userName);

	void resetPasswordViaEmail(String email);

	void resetPassword(String token, String newPassword);

	void sendResetPasswordViaEmail(String email);

	ByteArrayResource getAvatar(int id);

	void registerUser(User user);

	public void updateAccount(int id, AccountFormForUpdating form);

	public User createAccount(AccountFormForCreating accountNewForm);

	public void deleteAccountById(int id);

	UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

}
