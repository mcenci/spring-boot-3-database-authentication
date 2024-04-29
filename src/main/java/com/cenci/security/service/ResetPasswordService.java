package com.cenci.security.service;

import jakarta.annotation.Nonnull;

public interface ResetPasswordService {

	void resetPassword(@Nonnull String username);

}
