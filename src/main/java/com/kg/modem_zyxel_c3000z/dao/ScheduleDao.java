package com.kg.modem_zyxel_c3000z.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kg.modem_zyxel_c3000z.entity.Schedule;
import com.kg.modem_zyxel_c3000z.entity.ScheduleId;

@Repository
public interface ScheduleDao extends JpaRepository<Schedule, ScheduleId> {
}
