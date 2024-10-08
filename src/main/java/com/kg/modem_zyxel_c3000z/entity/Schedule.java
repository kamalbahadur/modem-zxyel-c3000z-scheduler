package com.kg.modem_zyxel_c3000z.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "schedule")
@Data
public class Schedule {
    @EmbeddedId
    private ScheduleId id;
    @Column(name = "connect_to_wifi", nullable = false)
    private Boolean connect;
}
