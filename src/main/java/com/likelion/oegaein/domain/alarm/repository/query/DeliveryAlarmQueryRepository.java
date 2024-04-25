package com.likelion.oegaein.domain.alarm.repository.query;

import com.likelion.oegaein.domain.alarm.entity.DeliveryAlarm;
import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryAlarmQueryRepository {
    private final EntityManager em;
    private final JdbcTemplate jdbcTemplate;

    public int deleteAllByMember(Long memberId){
        String jpql = "delete from DeliveryAlarm da" +
                " where da.member = :memberid";
        int deletedDeliveryAlarmCount = em.createQuery(jpql)
                .setParameter("memberid", memberId)
                .executeUpdate();
        em.flush();
        em.clear();
        return deletedDeliveryAlarmCount;
    }

    public List<DeliveryAlarm> findByMemberOrderByCreatedAtDesc(Long memberId){
        String jpql = "select da from DeliveryAlarm da" +
                " join fetch da.member dam" +
                " join fetch dam.profile damp" +
                // " join fetch ra.matchingPost ramap" +
                " where da.member.id = :memberid" +
                " order by da.createdAt desc";
        return em.createQuery(jpql, DeliveryAlarm.class)
                .setParameter("memberid", memberId)
                .getResultList();
    }
}
