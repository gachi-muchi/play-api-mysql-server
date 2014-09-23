package service;

import java.util.List;

import service.impl.ArticleServiceImpl;

import com.google.inject.ImplementedBy;

import entity.Article;

@ImplementedBy(ArticleServiceImpl.class)
public interface ArticleService {

	Article create(String userId, Article article);

	boolean update(String id, Article update);

	List<Article> list(String userId, int offset, int limit);

	Article read(String id);
	
	boolean delete(String id);

}