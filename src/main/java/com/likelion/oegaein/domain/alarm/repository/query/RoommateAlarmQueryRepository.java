package com.likelion.oegaein.domain.alarm.repository.query;

import com.likelion.oegaein.domain.member.entity.profile.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoommateAlarmQueryRepository {
    private final EntityManager em;

    public int deleteAllByMember(Member member){
        String jpql = "delete from RoommateAlarm ra" +
                " where ra.member = :member";
        int deletedRoommateAlarmCount = em.createQuery(jpql)
                .setParameter("member", member)
                .executeUpdate();
        em.flush();
        em.clear();
        return deletedRoommateAlarmCount;
    }
}
