package service.impl;

import java.util.Date;

import play.db.ebean.Model.Finder;
import service.ArticleService;
import service.LikeService;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import entity.Like;

@Singleton
public class LikeServiceImpl implements LikeService {
	@Inject
	private ArticleService articleService;

	private Finder<Long, Like> likeFinder = new Finder<Long, Like>(Long.class,
			Like.class);

	@Override
	public long like(String id, String userId) {
		if (articleService.read(id) == null) {
			return -1L;
		}
		Transaction tx = Ebean.beginTransaction();
		try {
			Like like = new Like();
			like.setToId(id);
			like.setFromId(userId);
			like.setDate(new Date());
			like.save();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			tx.end();
		}
		return getCount(id);
	}

	@Override
	public long unLike(String id, String userId) {
		Transaction tx = Ebean.beginTransaction();
		try {
			Like like = likeFinder.where().eq("toId", id).eq("fromId", userId)
					.findUnique();
			if (like == null) {
				return -1L;
			}
			like.delete();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			tx.end();
		}
		return getCount(id);
	}

	@Override
	public long getCount(String id) {
		return likeFinder.where().eq("to_id", id).findRowCount();
	}

}
