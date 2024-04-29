package com.cenci.security.service;

import jakarta.annotation.Nonnull;

public interface ChangePassword {

	void changePassword(@Nonnull String token, @Nonnull String password);
}
