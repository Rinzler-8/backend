package com.vti.security.service;

public interface IEmailService {

	void sendRegistrationUserConfirm(String email);

	void sendResetPassword(String email);

//	void resetPasswordViaEmail(String email);

}
