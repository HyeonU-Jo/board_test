package com.test.board.service;

import com.test.board.domain.entity.Board;
import com.test.board.domain.repository.BoardRepository;
import com.test.board.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private BoardRepository boardRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 수
    private static final int PAGE_POST_COUNT = 10; // 한 페이지에 존재하는 게시글 수

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /*게시글 작성*/
    @Transactional
    public Long savePost(BoardDTO boardDTO) {
        return boardRepository.save(boardDTO.toEntity(boardRepository.findLastID())).getId();
    }
    @Transactional
    public Long savePostCount(BoardDTO boardDTO) {
        return boardRepository.save(boardDTO.toEntityCount()).getId();
    }

    @Transactional
    public Long savePostAnswer(BoardDTO boardDTO, Long id, Long ono, Long indent, Long count) {
        return boardRepository.save(boardDTO.toEntityAnswer(id, ono, indent, count)).getId();
    }

    /*게시글 목록*/
    @Transactional
    public List<BoardDTO> getBoardList(Integer pageNum) {
        Page<Board> page = boardRepository
                .findAll(PageRequest
                        .of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "gno", "ono")));

        System.out.println(boardRepository.findLastID());

//        List<Board> boards = boardRepository.findAll();
        List<Board> boards = page.getContent();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Board board : boards) {
            /*BoardDTO boardDTO = BoardDTO.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .writer(board.getWriter())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();

            boardDTOList.add(boardDTO);*/

            boardDTOList.add(this.convertEntityToDto(board));
        }

        return boardDTOList;
    }

    /*게시글 페이징*/
    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        //페이지 번호 할당
        for (int val = curPageNum, i = 0; val <= blockLastPageNum; val++, i++) {
            pageList[i] = val;
        }

        return pageList;
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    /*게시글 조회*/
    @Transactional
    public BoardDTO getPost(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        BoardDTO boardDTO = BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .gno(board.getGno())
                .ono(board.getOno())
                .indent(board.getIndent())
                .count(board.getCount())
                .build();

        return boardDTO;
    }

    /*게시글 삭제*/
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    /*게시글 검색*/
    @Transactional
    public List<BoardDTO> searchPosts(String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDTO> boardDTOList = new ArrayList<>();

        if (boards.isEmpty()) {
            return boardDTOList;
        }

        for (Board board : boards) {
            boardDTOList.add(this.convertEntityToDto(board));
        }

        return boardDTOList;
    }

    private BoardDTO convertEntityToDto(Board board) {

        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .gno(board.getGno())
                .ono(board.getOno())
                .indent(board.getIndent())
                .build();
    }
}
