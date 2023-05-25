package com.raysmond.blog.microservice1.services;

import com.raysmond.blog.microservice1.repositories.SeoRobotAgentRepository;
import com.raysmond.blog.microservice1.repositories.VisitRepository;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.SeoRobotAgent;
import com.raysmond.blog.common.models.User;
import com.raysmond.blog.common.models.Visit;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private SeoRobotAgentRepository seoRobotAgentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    public void saveVisit(Post post, String clientIp, String userAgent) {
//        if (this.userService.currentUser().isAdmin())
//            return;

        User user = this.userService.currentUser();

        Visit visit = new Visit();
        visit.setClientIp(clientIp);
        visit.setPost(post);
        visit.setUser(user);
        visit.setIsAdmin(user != null ? user.isAdmin() : false);
        visit.setUserAgent(userAgent);
        this.visitRepository.save(visit);
    }

    public Long getUniqueVisitsCount(Post post) {

        Session session = (Session) this.entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(
                "SELECT COUNT(DISTINCT v.clientIp) " +
                        "FROM visits AS v " +
                        "LEFT JOIN seo_robots_agents AS ra " +
                        //"ON LOWER(v.userAgent) LIKE concat('%', LOWER(ra.userAgent), '%') " +
                        "ON CASE WHEN ra.isregexp = TRUE THEN " +
                        "LOWER(v.userAgent) ~* LOWER(ra.userAgent) " +
                        "ELSE " +
                        "LOWER(v.userAgent) LIKE concat('%', LOWER(ra.userAgent), '%') " +
                        "END " +
                        "WHERE v.post_id = :post_id AND v.isAdmin = FALSE " +
                        "AND ra.id IS NULL ");
        query.setLong("post_id", post.getId());
        List<Object> result = query.list();
        if (result.size() > 0L) {
            return ((BigInteger)result.get(0)).longValue();
        }

        return 0L;

    }

    public Long getUniqueVisitsCount_old(Post post) {
        //return this.visitRepository.getUniquePostVisitsCount(post);

        // exclude queries from robots if matches by UserAgent
        List<SeoRobotAgent> robotsAgents = this.seoRobotAgentRepository.findAll();

        //final Long[] count = {0L};
        AtomicReference<Long> count = new AtomicReference<>();

        this.visitRepository.getVisitsByPostAndIsAdminIsFalse(post).forEach(vr -> {

            Object[] v = (Object[]) vr;

            if (robotsAgents.size() == 0 || v[1] == null) {
                //count[0]++;
                count.set(count.get()+1);
            } else {
                robotsAgents.forEach(ra -> {
                    Pattern p = Pattern.compile(".*("+ra.getUserAgent()+").*", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher((String) v[1]);
                    if (!m.matches()) {
                        //count[0]++;
                        count.set(count.get()+1);
                    }
                });
            }
        });

        return count.get();
    }

}
