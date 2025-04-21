package com.oops.reasonaible;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class VaultChecker {
	@Value("${VAULT_URI:NOT_SET}")
	private String vaultUri;

	@Value("${VAULT_AUTHENTICATION}")
	private String vaultAuthentication;

	@Value("${VAULT_TOKEN}")
	private String vaultToken;

	@Value("${VAULT_BACKEND}")
	private String vaultBackend;

	@PostConstruct
	public void checkVaultUri() {
		if ("NOT_SET".equals(vaultUri)) {
			System.out.println("VAULT_URI is not set.");
		} else {
			System.out.println("VAULT_URI is set to: " + vaultUri);
		}
		System.out.println("vaultAuthentication = " + vaultAuthentication);
		System.out.println("vaultToken = " + vaultToken);
		System.out.println("vaultBackend = " + vaultBackend);
	}
}
