package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostParams;
import com.raysmond.blog.common.models.RestPage;
import com.raysmond.blog.common.models.support.PostStatus;
import com.raysmond.blog.common.models.support.PostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/PostRepositoryController")
public interface PostRepositoryRealClient {

    @RequestMapping(value = "/findAllByPostTypeAndPostStatusAndDeleted_List", method = RequestMethod.POST)
    @ResponseBody
    List<Post> findAllByPostTypeAndPostStatusAndDeletedList(@RequestBody PostParams postPrams);

    @RequestMapping(value = "/findAllByDeleted", method = RequestMethod.POST)
    @ResponseBody
    RestPage<Post> findAllByDeleted(@RequestBody PostParams postPrams);
//    Page<Post> findAllByDeleted(@RequestBody Pageable pageRequest, @RequestParam("deleted") Boolean deleted);

    @RequestMapping(value = "/countPostsByTags", method = RequestMethod.POST)
    @ResponseBody
    List<Object[]> countPostsByTags(@RequestBody PostStatus status);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    void delete(@RequestBody Post post);

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    List<Post> findAll();

    @RequestMapping(value = "/findAllByPostTypeAndPostStatusAndDeleted", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    RestPage<Post> findAllByPostTypeAndPostStatusAndDeleted(@RequestBody PostParams postPrams);
//    Page<Post> findAllByPostTypeAndPostStatusAndDeleted(@RequestBody PostParams postPrams);

    @RequestMapping(value = "/findByIdAndPostStatusAndDeleted", method = RequestMethod.POST)
    @ResponseBody
    Post findByIdAndPostStatusAndDeleted(@RequestParam("postId") Long postId, @RequestBody PostStatus postStatus, @RequestParam("deleted") Boolean deleted);

    @RequestMapping(value = "/findByPermalinkAndPostStatusAndDeleted", method = RequestMethod.POST)
    @ResponseBody
    Post findByPermalinkAndPostStatusAndDeleted(@RequestParam("permalink") String permalink, @RequestBody PostStatus postStatus, @RequestParam("deleted") Boolean deleted);

    @RequestMapping(value = "/findByTag", method = RequestMethod.POST)
    @ResponseBody
    RestPage<Post> findByTag(@RequestBody PostParams postPrams);
//    Page<Post> findByTag(@RequestBody PostParams postPrams);
//    Page<Post> findByTag(@RequestParam("tag") String tag, @RequestBody Pageable pageable);

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ResponseBody
    Post findOne(@RequestParam("postId") Long postId);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    Post save(@RequestBody Post post);

}
