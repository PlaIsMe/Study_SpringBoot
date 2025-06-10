package com.study.springboot.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.hibernate.annotations.ManyToAny;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    String name;

    String description;

    @ManyToAny
    Set<Permission> permissions;
}
