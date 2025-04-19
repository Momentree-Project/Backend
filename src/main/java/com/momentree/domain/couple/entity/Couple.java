package com.momentree.domain.couple.entity;


import com.momentree.domain.user.entity.User;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COUPLE")
@AuditOverride(forClass = BaseEntity.class)
@AttributeOverride(name = "id", column = @Column(name = "couple_id"))
public class Couple extends BaseEntity {

    @Column(name = "couple_started_day", nullable = false)
    private LocalDate coupleStartedDay;

    @Column(name = "couple_nickname")
    private String coupleNickname;

    @OneToMany(mappedBy = "couple", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

}
