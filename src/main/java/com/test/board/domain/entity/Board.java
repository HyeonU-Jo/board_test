package com.test.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board  extends TimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /*
    * 그룹 번호 group_no
    * 그룹 내 순서 order_no
    * 들여쓰기 indent
    * 카운팅 count
    * */

    @Column
    private Long gno;

    @Column
    private Long ono;

    @Column
    private Long indent;

    @Column
    private Long count;

    @Builder
    public Board(Long id, String writer, String title, String content, Long gno, Long ono, Long indent, Long count) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.gno = gno;
        this.ono = ono;
        this.indent = indent;
        this.count = count;
    }





}
