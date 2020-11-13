package com.sanix.todoRedis.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ToDo {

    @NotNull
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @NotBlank
    private String description;

    @Column(insertable=true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    public ToDo(@NotNull @NotBlank String description) {
        this.description = description;
    }

    @PrePersist
    void onCreated(){
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate(){
        this.setModified(LocalDateTime.now());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getDescription() {
        return description;
    }
}
