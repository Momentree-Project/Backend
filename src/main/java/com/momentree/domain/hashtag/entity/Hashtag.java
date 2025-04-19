package com.momentree.domain.hashtag.entity;

import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "hashtag")
@AttributeOverride(name = "id", column = @Column(name = "hashtag_id"))
public class Hashtag extends BaseEntity {

    @Column(name="content", nullable = false)
    private String content;

}
