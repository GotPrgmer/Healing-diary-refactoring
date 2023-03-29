package com.ssafy.healingdiary.domain.member.domain;

import com.ssafy.healingdiary.global.common.domain.BaseEntity;
import com.sun.istack.NotNull;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notice")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "notice_id"))
@AttributeOverride(name = "createdDate", column = @Column(name = "notice_created_date"))
@AttributeOverride(name = "updatedDate", column = @Column(name = "notice_updated_date"))
public class Notice extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String content;

    private String link;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "check_status")
    private CheckStatus checkStatus;

    public void changeCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    public static Notice toEntity(Member member, String content, String link) {
        return Notice.builder()
            .member(member)
            .content(content)
            .link(link)
            .checkStatus(CheckStatus.UNCHECKED)
            .build();
    }
}
