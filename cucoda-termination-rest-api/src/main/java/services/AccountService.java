package services;

import domain.Account;
import domain.User;

import java.util.Optional;
import java.util.Set;

public interface AccountService {

	Set<User> getAllUsers();
	Optional<Account> getAccount(String provider, String identifier);
	Optional<Account> createAccount(String provider, String identifier);
}
