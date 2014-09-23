package service.impl;

import play.db.ebean.Model.Finder;
import service.UserService;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import entity.User;

@Singleton
public class UserServiceImpl implements UserService {

	private Finder<Long, User> userFinder;

	@Inject
	public UserServiceImpl() {
		userFinder = new Finder<Long, User>(Long.class, User.class);
	}

	@Override
	public User create(User user) {
		return null;
	}

	@Override
	public User read(String id) {
		return userFinder.byId(Long.valueOf(id));
	}

	@Override
	public User read(String mail, String password) {
		return userFinder.where().eq("mail", mail).eq("password", password)
				.findUnique();
	}

	@Override
	public boolean update(String id, User update) {
		Transaction tx = Ebean.beginTransaction();
		try {
			User user = userFinder.byId(Long.valueOf(id));
			if (update.getName() != null) {
				user.setName(update.getName());
			}
			user.update();
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			return false;
		} finally {
			tx.end();
		}
	}

}
