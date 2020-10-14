package poly.service.impl;

import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.FoodDTO;
import poly.persistance.mapper.IFoodMapper;
import poly.service.IFoodService;
import poly.util.CmmUtil;
import poly.util.DateUtil;

@Service("FoodService")
public class FoodService implements IFoodService{
	
	@Resource(name="FoodMapper")
	private IFoodMapper foodMapper;
	
	// 로그 파일 생성 및 로그 출력을 위한 log4j 프레임워크 자바 객체
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public int getFoodInfoFromWEB() throws Exception {
		log.info(this.getClass().getName() + ".getFoodInfoFromWEB start!");
		
		int res = 0; // 크롤링 결과 (0보다 크면 크롤링 성공)
		
		// CGV 영화 순위 정보 가져올 사이트 주소
		String url = "http://www.kopo.ac.kr/kangseo/content.do?menu=262";
		
		// JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML소스 저장할 변수
		Document doc = null;
		
		// 사이트 접속(http프로토콜만 가능, https 프로토콜은 보안상 안됨)
		doc = Jsoup.connect(url).get();
		
		// CGV 웹페이지의 전체 소스 중 일부 태그를 선택하기 위해 사용
		// <div class="sect-movie-chart"> 이 태그 내에서 있는 HTML소스만 element에 저장됨
		Elements element = doc.select("tbody");
		
		// Iterator을 사용하여 영화 순위 정보를 가져오기
		// 영화순위는 기본적으로 1개 이상의 영화가 존재하기 때문에 태그의 반복이 존재할 수 밖에 없음
		Iterator<Element> day = element.select("tr td:eq(0)").iterator(); // 영화 순위
		Iterator<Element> food_name = element.select("tr td:eq(2)").iterator(); // 영화 제목

		FoodDTO pDTO = null;
		
		//수집된 데이터 DB에 저장하기
		while(day.hasNext()) {
			
			
			pDTO = new FoodDTO(); // 수집된 영화정보를 DTO에 저장하기 위해 메모리에 올리기
			
			//수집시간을 기본키(pk)로 사용
			pDTO.setCollect_time(DateUtil.getDateTime("yyyyMMdd24hmmss"));

			// 영화 순위(trim 함수 추가 이유: trim 함수는 글자의 앞뒤 공백 삭제 역할을 수행하여, 데이터 수집시, 홈페이지 개발자들을 앞뒤 공백 집어넣을 수 있어서 추가)
			String dday = CmmUtil.nvl(day.next().text()).trim(); //No.1들어옴
			pDTO.setDay(dday.substring(dday.length()-3, dday.length()));

			//영화 제목
			pDTO.setFood_nm(CmmUtil.nvl(food_name.next().text()).trim());

			//등록자
			pDTO.setReg_id("admin");

			if(pDTO.getFood_nm().equals("")) continue;
			
			//영화 한개씩 추가
			res += foodMapper.InsertFoodInfo(pDTO);

		}
		
		log.info(this.getClass().getName() + ".getFoodInfoFromWEB end!");
		
		return res;
	}

}
