package com.likelion.oegaein.domain.alarm.repository.query;

import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import com.likelion.oegaein.domain.member.entity.profile.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<RoommateAlarm> findByMemberOrderByCreatedAtDesc(Member member){
        String jpql = "select ra from RoommateAlarm ra" +
                " join fetch ra.member ram" +
                " join fetch ram.profile ramp" +
                " join fetch ra.matchingPost ramp" +
                " where ram.member = :member" +
                " order by ra.createdAt desc";
        return em.createQuery(jpql, RoommateAlarm.class)
                .setParameter("member", member)
                .getResultList();
    }
}
