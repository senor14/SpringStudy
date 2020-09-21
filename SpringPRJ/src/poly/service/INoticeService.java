package poly.service;

import java.util.List;

import poly.dto.NoticeDTO;

public interface INoticeService {
		
		//게시판 리스트
		List<NoticeDTO> getNoticeList() throws Exception;
		
		//게시판 글 등록
		void InsertNoticeInfo(NoticeDTO pDTO) throws Exception;
		
		//게시판 상세보기
		NoticeDTO getNoticeInfo(NoticeDTO pDTO) throws Exception;
		
		//게시판 조회수 업데이트
		void updateNoticeReadCnt(NoticeDTO pDTO) throws Exception;
		
		//게시판 글 수정
		void updateNoticeInfo(NoticeDTO pDTO) throws Exception;
		
		//게사판 글 삭제
		void deleteNoticeInfo(NoticeDTO pDTO) throws Exception;
}
