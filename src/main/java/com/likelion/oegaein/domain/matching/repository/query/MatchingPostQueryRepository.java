package com.likelion.oegaein.domain.matching.repository.query;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.member.entity.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchingPostQueryRepository {
    private final EntityManager em; // 엔티티 매니저
    private final int STANDARD_RATE = 70; // 베스트 룸메이트 기준

    public List<MatchingPost> findByMember(Member member){
        String jpql = "select distinct mp from MatchingPost mp"
                + " join fetch mp.matchingRequests mpmr"
                + " join fetch mr.participant mrp"
                + " join fetch p.profile pp";
        return em.createQuery(jpql, MatchingPost.class).getResultList();
    } // matching request fetch join

    public List<MatchingPost> findBestRoomMateMatchingPosts(){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mpap.rate >= :standardRate" +
                " order by mpap.rate desc";
        return em.createQuery(jpql, MatchingPost.class)
                .setParameter("standardRate", STANDARD_RATE)
                .getResultList();
    }

    public List<MatchingPost> findMatchingPostsBetweenTwoDates(LocalDateTime fromDate, LocalDateTime toDate){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mp.deadline between :fromDate and :toDate" +
                " and mp.matchingStatus = :matchingPostStatus" +
                " order by mp.createdAt asc";
        return em.createQuery(jpql, MatchingPost.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("matchingPostStatus", MatchingStatus.WAITING)
                .getResultList();
    }

    public List<MatchingPost> searchMatchingPost(String content){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mp.title like concat('%',:content,'%')" +
                " or mp.content like concat('%',:content,'%')" +
                " order by mp.createdAt desc";
        return em.createQuery(jpql, MatchingPost.class)
                .setParameter("content", content)
                .getResultList();
    }

    public List<MatchingPost> findAllExceptBlockedMember(List<Long> blockedMemberIds){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mpa.id not in :blockedmemberids" +
                " order by mp.createdAt desc";
        return em.createQuery(jpql, MatchingPost.class)
                .setParameter("blockedmemberids", blockedMemberIds)
                .getResultList();
    }
}
