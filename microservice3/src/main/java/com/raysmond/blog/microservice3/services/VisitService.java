package com.raysmond.blog.microservice3.services;

import com.raysmond.blog.microservice3.models.Post;
import com.raysmond.blog.microservice3.models.SeoRobotAgent;
import com.raysmond.blog.microservice3.models.User;
import com.raysmond.blog.microservice3.models.Visit;
import com.raysmond.blog.microservice3.repositories.SeoRobotAgentRepository;
import com.raysmond.blog.microservice3.repositories.VisitRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequestMapping("/VisitService")
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


        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("Visit: ");
//        System.out.println(RamUsageEstimator.sizeOf(visit));
//        System.out.println(RamUsageEstimator.shallowSizeOf(visit));
//        System.out.println(ClassLayout.parseInstance(visit).toPrintable());

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
            return ((BigInteger) result.get(0)).longValue();
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
                count.set(count.get() + 1);
            } else {
                robotsAgents.forEach(ra -> {
                    Pattern p = Pattern.compile(".*(" + ra.getUserAgent() + ").*", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher((String) v[1]);
                    if (!m.matches()) {
                        //count[0]++;
                        count.set(count.get() + 1);
                    }
                });
            }
        });

        return count.get();
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Autowired
    PostService postService;
    Post post = null;
    User user = null;

    private void init() {
        post = this.postService.getPost(1L);
        user = this.userService.getSuperUser();
    }

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        String clientIp = "clientIp";
        String userAgent = "userAgent";
        if (post == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 3; i++) {
                    saveVisit_test(post, clientIp, userAgent);
                }
                for (int i = 0; i < 1; i++) {
                    getUniqueVisitsCount_test(post);
                }
                break;
            case "saveVisit":
                saveVisit_test(post, clientIp, userAgent);
                break;
            case "getUniqueVisitsCount":
                getUniqueVisitsCount_test(post);
                break;
        }

        return "test";
    }


    void saveVisit_test(Post post, String clientIp, String userAgent) {
//        if (this.userService.currentUser().isAdmin())
//            return;

//        User user = this.userService.currentUser();
//        User user = new User();

        Visit visit = new Visit();
        visit.setClientIp(clientIp);
        visit.setPost(post);
        visit.setUser(user);
        visit.setIsAdmin(user != null ? user.isAdmin() : false);
        visit.setUserAgent(userAgent);
        this.visitRepository.save(visit);
    }

    Long getUniqueVisitsCount_test(Post post) {

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
            return ((BigInteger) result.get(0)).longValue();
        }

        return 0L;

    }

}
