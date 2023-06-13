package com.raysmond.blog.services;

import com.raysmond.blog.models.Like;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.User;
import com.raysmond.blog.repositories.LikeRepository;
import org.apache.lucene.util.RamUsageEstimator;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequestMapping("/LikeService")
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserService userService;


    public Integer getTotalLikesByPost(Post post) {
        return this.likeRepository.getTotalLikesByPost(post);
    }

    public Integer getTotalLikesByUserAndPost(User user, Post post) {
        return this.likeRepository.getTotalLikesByUserAndPost(user, post);
    }

    public void likePost(Post post, String clientIp) {
        User user = this.userService.currentUser();
        Integer currentSympathy = 0;

        // TODO
//        if (user != null) {
        currentSympathy = this.likeRepository.getTotalLikesByUserAndPost(user, post);
//        } else {
        currentSympathy = this.likeRepository.getTotalLikesByClientIpAndPost(clientIp, post);
//        }
        if (currentSympathy == null) currentSympathy = 0;

        Integer sympathyDelta = -currentSympathy + 1;
        this.saveSympathy(post, user, clientIp, sympathyDelta);
    }

    public void dislikePost(Post post, String clientIp) {
        User user = this.userService.currentUser();
        Integer currentSympathy = 0;

//        if (user != null) {
        currentSympathy = this.likeRepository.getTotalLikesByUserAndPost(user, post);
//        } else {
        currentSympathy = this.likeRepository.getTotalLikesByClientIpAndPost(clientIp, post);
//        }
        if (currentSympathy == null) currentSympathy = 0;

        Integer sympathyDelta = -currentSympathy - 1;
        this.saveSympathy(post, user, clientIp, sympathyDelta);
    }

    private void saveSympathy(Post post, User user, String clientIp, Integer sympathy) {
        Like like = new Like();
        like.setClientIp(clientIp);
        like.setPost(post);
        like.setUser(user);
        like.setIsAdmin(user != null ? user.isAdmin() : false);
        like.setSympathy(sympathy);

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("Like");
//        System.out.println(RamUsageEstimator.sizeOf(like));
//        System.out.println(RamUsageEstimator.shallowSizeOf(like));
//        System.out.println(ClassLayout.parseInstance(like).toPrintable());

        this.likeRepository.save(like);
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Post post = new Post();
        String clientIp = "10.60.150.56";
        User user = new User();
        Integer sympathy = 1;

        switch (method) {
            case "all":
                for (int i = 0; i < 5; i++) {
                    getTotalLikesByPost_test(post);
                }
                for (int i = 0; i < 1; i++) {
                    likePost_test(post, clientIp);
                }
                for (int i = 0; i < 1; i++) {
                    dislikePost_test(post, clientIp);
                }
                for (int i = 0; i < 2; i++) {
                    saveSympathy_test(post, user, clientIp, sympathy);
                }
                break;
            case "getTotalLikesByPost":
                getTotalLikesByPost_test(post);
                break;
            case "likePost":
                likePost_test(post, clientIp);
                break;
            case "dislikePost":
                dislikePost_test(post, clientIp);
                break;
            case "saveSympathy":
                saveSympathy_test(post, user, clientIp, sympathy);
                break;
        }

        return "test";
    }

    Integer getTotalLikesByPost_test(Post post) {
//        return this.likeRepository.getTotalLikesByPost(post);
        return 1;
    }

    void likePost_test(Post post, String clientIp) {
//        User user = this.userService.currentUser();
        User user = new User();
        Integer currentSympathy = 0;

        // TODO
//        if (user != null) {
//        currentSympathy = this.likeRepository.getTotalLikesByUserAndPost(user, post);
//        } else {
//        currentSympathy = this.likeRepository.getTotalLikesByClientIpAndPost(clientIp, post);
//        }
        if (currentSympathy == null) currentSympathy = 0;

        Integer sympathyDelta = -currentSympathy + 1;
//        this.saveSympathy(post, user, clientIp, sympathyDelta);
    }

    void dislikePost_test(Post post, String clientIp) {
//        User user = this.userService.currentUser();
        User user = new User();
        Integer currentSympathy = 0;

//        if (user != null) {
//            currentSympathy = this.likeRepository.getTotalLikesByUserAndPost(user, post);
//        } else {
//            currentSympathy = this.likeRepository.getTotalLikesByClientIpAndPost(clientIp, post);
//        }
        if (currentSympathy == null) currentSympathy = 0;

        Integer sympathyDelta = -currentSympathy - 1;
//        this.saveSympathy(post, user, clientIp, sympathyDelta);
    }

    void saveSympathy_test(Post post, User user, String clientIp, Integer sympathy) {
        Like like = new Like();
        like.setClientIp(clientIp);
        like.setPost(post);
        like.setUser(user);
        like.setIsAdmin(user != null ? user.isAdmin() : false);
        like.setSympathy(sympathy);
//        this.likeRepository.save(like);
    }

}
