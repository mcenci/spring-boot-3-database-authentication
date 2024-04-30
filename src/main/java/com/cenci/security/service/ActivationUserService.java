package com.cenci.security.service;

import jakarta.annotation.Nonnull;

public interface ActivationUserService {

	void activate(@Nonnull String token);
}
