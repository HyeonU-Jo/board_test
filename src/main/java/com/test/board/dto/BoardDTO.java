package com.test.board.dto;

import com.test.board.domain.entity.Board;
import com.test.board.domain.repository.BoardRepository;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    /*
     * 그룹 번호 group
     * 그룹 내 순서 order
     * 들여쓰기 indent
     * */

    private Long gno;
    private Long ono;
    private Long indent;

    public Board toEntity(){
        Board build = Board.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .gno(id)
                .ono(ono)
                .indent(indent)
                .build();

        return build;
    }

    public Board toEntityAnswer(Long gg_no){
        Board build = Board.builder()
                .id(id)
                .writer(writer)
                .title(gg_no + "번 RE : " + title)
                .content(content)
                .gno(gg_no)
                .ono(1L)
                .indent(1L)
                .build();

        return build;
    }

    @Builder
    public BoardDTO(Long id, String writer, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, Long g_no, Long o_no, Long indent) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.gno = gno;
        this.ono = ono;
        this.indent = indent;
    }
}
