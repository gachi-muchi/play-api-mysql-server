package service.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import play.libs.Json;
import service.AuthService;
import service.UserService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import entity.AuthToken;
import entity.User;

@Singleton
public class AuthServiceImpl implements AuthService {

	private static final int AUTH_LIMIT_SECONDS = 24 * 60 * 60;

	private MemcachedClient memcachedClient;
	
	@Inject
	private UserService userService;

	public AuthServiceImpl() throws IOException {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses("10.34.49.59:11211"));
		builder.setConnectionPoolSize(10);
		memcachedClient = builder.build();
	}

	@Override
	public AuthToken createToken(String mail, String password) {
		User user = userService.read(mail, password);
		if (user == null) {
			return null;
		}
		AuthToken token = get("token.userId."
				+ user.getUserId(), AuthToken.class);
		if (token != null) {
			return token;
		}
		token = new AuthToken();
		token.setUserId(user.getUserId());
		token.setToken(Integer.toHexString(new String(user.getUserId()
				+ System.currentTimeMillis()).hashCode()));
		set("token.userId." + user.getUserId(), token);
		set("token.token." + token.getToken(), user.getUserId());
		return token;
	}

	@Override
	public AuthToken confirmToken(String token) {
		String userId = get("token.token." + token, String.class);
		if (userId == null) {
			return null;
		}
		return get("token.userId." + userId, AuthToken.class);
	}

	@Override
	public boolean deleteToken(String token) {
		String userId = get("token.token." + token, String.class);
		if (userId != null) {
			remove("token.userId." + userId);
			remove("token.token." + token);
		}
		return true;
	}

	private void set(String key, Object obj) {
		try {
			memcachedClient.set(key, AUTH_LIMIT_SECONDS, Json.stringify(Json.toJson(obj)));
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}
	
	private <T> T get(String key, Class<T> clazz) {
		try {
			String str = memcachedClient.get(key);
			if (str == null) {
				return null;
			}
			return Json.fromJson(Json.parse(str), clazz);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void remove(String key) {
		try {
			memcachedClient.delete(key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}
}
