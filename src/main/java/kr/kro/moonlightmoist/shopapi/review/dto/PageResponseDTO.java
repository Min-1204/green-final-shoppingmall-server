package kr.kro.moonlightmoist.shopapi.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class PageResponseDTO<E> {
    private List<E> dtoList; //페이지에 보여줄 데이터들
    private List<Integer> pageNumList; //페이지 번호를 표시하기위한 목록
    private PageRequestDTO pageRequestDTO; //요청된 페이지, 페이지에 표시될 데이터 개수

    private boolean prevPageGroup, nextPageGroup; //이전 페이지와 다음 페이지 존재 여부
    private int totalDataCount, prevPage, nextPage, currentTotalPageCnt, currentPage;
    //totalDataCount = 총 데이터 개수
    //prevPage = 이전 페이지 , nextPage = 다음 페이지
    //currentTotalPageCnt = 페이지 버튼 개수
    //currentPage = 현재 페이지

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalDataCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalDataCount = (int) totalDataCount;

        this.currentPage = pageRequestDTO.getPage(); //현재 페이지
        int endPage = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0) * 10);
        //현재 볼 수 있는 마지막 페이지 / 1~10, 11~20, 21~30, 31~40 ...
        //현재 페이지가 3이면 10페이지까지, 현재 페이지가 15면 20페이지까지 보여줌
        int startPage = endPage - 9;

        int lastPage = (int) (Math.ceil(totalDataCount / (double) pageRequestDTO.getSize()));
        //전체 데이터를 기준으로 보여줄 실제 마지막 페이지, 전체 데이터가 105개면 11페이지로 보여줌
        endPage = Math.min(endPage, lastPage);
        //현재 페이지가 속한 페이지 번호 묶음의 끝 번호
        //만약 전체 데이터가 105개고 총 페이지가 11이면, 11은 11~20 묶음에 속하는데
        //마지막 페이지가 11인데 20페이지까지 보여줄 필요가 없으므로 11로 고정하기 위해 Math.min으로 조절

        this.prevPageGroup = startPage > 1;
        this.nextPageGroup = endPage < lastPage;
        //이전 페이지가 존재하면 true, 현재 페이지가 2면 2>1이므로 true
        //다음 페이지가 존재하면 true, 현재 페이지가 10, 마짐가 페이지가 11이면 10 < 11 true

        this.pageNumList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
        //startPage 부터 endPage 까지 페이지 번호 목록 생성

        if (prevPageGroup) this.prevPage = startPage - 1;
        if (nextPageGroup) this.nextPage = endPage + 1;
        //<, > 이전, 다음 버튼 클릭 시 이동할 페이지
        //< 이전 페이지를 누르면 현재 페이지가 3일시 3-1=2 페이지로 이동
        //> 다음 페이지를 누르면 현재 페이지가 5일시 5+1=6 페이지로 이동

        this.currentTotalPageCnt = this.pageNumList.size();
        //현재 페이지의 페이지 버튼 갯수

    }
}
