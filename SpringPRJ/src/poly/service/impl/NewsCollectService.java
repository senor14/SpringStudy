package poly.service.impl;

import org.mortbay.log.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.service.INewsCollectService;

@Service("NewsCollectService")
public class NewsCollectService implements INewsCollectService {
	
	@Override
	public String doNaverNewsContents(String url) throws Exception {
		
		// JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML 소스 저장할 변수
		Document doc = null;
		
		// 사이트 접속(http 프로토콜만 가능, https 프로토콜은 보안상 안됨)
		doc = Jsoup.connect(url).get();
		
		// 네이터 뉴스 본문 내용에 대한 div 소스를 가져옴
		// <div id = "articleBodyContent" class="_article_body_contents">
		Elements newsContent = doc.select("div.news_end");
		
		// div._article_body_contents
		
		
		// 태그 내 텍스트 문구만 가져오기
		String res = newsContent.text();
		
		Log.info(res);
		
		doc = null;
		
		return res;
		
	}

}
