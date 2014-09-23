package service.impl;

import java.util.Date;
import java.util.List;

import play.db.ebean.Model.Finder;
import service.ArticleService;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import entity.Article;

@Singleton
public class ArticleServiceImpl implements ArticleService {

	private Finder<Long, Article> articleFinder;

	@Inject
	public ArticleServiceImpl() {
		articleFinder = new Finder<Long, Article>(Long.class, Article.class);
	}

	@Override
	public Article create(String userId, Article article) {
		Ebean.beginTransaction();
		try {
			article.setUserId(userId);
			article.setCreateTime(new Date());
			article.setUpdateTime(new Date());
			article.save();
			Ebean.commitTransaction();
			return article;
		} catch (Exception e) {
			Ebean.rollbackTransaction();
			return null;
		} finally {
			Ebean.endTransaction();
		}
	}

	@Override
	public boolean update(String id, Article update) {
		Transaction tx = Ebean.beginTransaction();
		try {
			if (articleFinder.byId(Long.valueOf(id)) == null) {
				return false;
			}
			Article article = articleFinder.setForUpdate(true).setId(id)
					.findUnique();
			if (update.getTitle() != null) {
				article.setTitle(update.getTitle());
			}
			if (update.getBody() != null) {
				article.setBody(update.getBody());
			}
			article.setUpdateTime(new Date());
			article.update();
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			return false;
		} finally {
			tx.end();
		}
	}

	@Override
	public List<Article> list(String userId, int offset, int limit) {
		List<Article> list = articleFinder.query().where().eq("userId", userId)
				.orderBy().asc("articleId").setFirstRow(offset)
				.setMaxRows(limit).findList();
		return list;

	}

	@Override
	public Article read(String id) {
		return articleFinder.byId(Long.valueOf(id));
	}

	@Override
	public boolean delete(String id) {
		Transaction tx = Ebean.beginTransaction();
		try {
			if (articleFinder.byId(Long.valueOf(id)) == null) {
				return false;
			}
			Article article = articleFinder.setForUpdate(true).setId(id)
					.findUnique();
			article.delete();
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
