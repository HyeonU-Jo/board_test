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
    private Long count;

    public Board toEntity(Long g_no) {
        Long i = (g_no == null) ? 1L : g_no + 1L;

//        if (g_no == null) {
//            i = 1L;
//        }else{
//            i = g_no + 1L;
//        }

//        if (g_no == null) {
        Board build = Board.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .gno(i)
                .ono(0L)
                .indent(0L)
                .count(0L)
                .build();
        return build;
//        } else {
//
//            Board build = Board.builder()
//                    .id(id)
//                    .writer(writer)
//                    .title(title)
//                    .content(content)
//                    .gno(g_no + 1L)
//                    .ono(0L)
//                    .indent(0L)
//                    .count(0L)
//                    .build();
//            return build;
//        }
    }

    public Board toEntityCount() {
        Board build = Board.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .gno(gno)
                .ono(ono)
                .indent(indent)
                .count(count + 1L)
                .build();
        return build;
    }

    public Board toEntityAnswer(Long g_no, Long o_no, Long indent, Long count) {
        Long i;


//        Long i = (indent == 0L) ? count * 100L : 10L;
//        if (indent > 0L) {
//            i = 1L;
//        }

        switch (Math.toIntExact(indent)) {
            case 0:
                i = 1000L + (count * 1000L);
                break;
            case 1:
                i = 100L + (count * 100L);
                break;
            case 2:
                i = 10L + (count * 10L);
                break;
            default:
                i = 1L;
                break;
        }

//        if (indent == 0L) {
        Board build = Board.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .gno(g_no)
                .ono(o_no + (i))
                .indent(indent + 1L)
                .count(0L)
                .build();

        return build;
//        } else {
//            Board build = Board.builder()
//                    .id(id)
//                    .writer(writer)
//                    .title(title)
//                    .content(content)
//                    .gno(g_no)
//                    .ono(o_no + 1L)
//                    .indent(indent + 1L)
//                    .count(0L)
//                    .build();
//
//            return build;
//        }
    }

    @Builder
    public BoardDTO(Long id, String writer, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, Long gno, Long ono, Long indent, Long count) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.gno = gno;
        this.ono = ono;
        this.indent = indent;
        this.count = count;
    }
}
