package com.kg.modem_zyxel_c3000z.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleId implements Serializable {
    @Column(name = "day_of_the_week", nullable = false)
    private String day;
    @Column(name = "hour_of_the_day", nullable = false)
    private Integer hour;
    @Column(name = "minute_of_the_hour", nullable = false)
    private Integer minute;
}
