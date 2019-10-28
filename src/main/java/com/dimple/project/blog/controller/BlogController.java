package com.dimple.project.blog.controller;

import com.dimple.framework.aspectj.lang.annotation.Log;
import com.dimple.framework.aspectj.lang.enums.BusinessType;
import com.dimple.framework.web.controller.BaseController;
import com.dimple.framework.web.domain.AjaxResult;
import com.dimple.framework.web.page.TableDataInfo;
import com.dimple.project.blog.domain.Blog;
import com.dimple.project.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: BlogController
 * @description: 博客Controller
 * @author: Dimple
 * @date: 2019-10-28
 */
@RestController
@RequestMapping("/blog/blog")
public class BlogController extends BaseController {

    @Autowired
    private BlogService blogService;

    @PreAuthorize("@permissionService.hasPermission('blog:blog:list')")
    @PostMapping("/list")
    public TableDataInfo list(Blog blog) {
        startPage();
        List<Blog> list = blogService.selectBlogList(blog);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('blog:blog:add')")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(Blog blog) {
        return toAjax(blogService.insertBlog(blog));
    }

    @PreAuthorize("@permissionService.hasPermission('blog:blog:edit')")
    @Log(title = "博客管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public AjaxResult edit(Blog blog) {
        return toAjax(blogService.updateBlog(blog));
    }

    @PreAuthorize("@permissionService.hasPermission('blog:blog:remove')")
    @Log(title = "博客管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids) {
        return toAjax(blogService.deleteBlogByIds(ids));
    }

    @PreAuthorize("@permissionService.hasPermission('blog:blog:remove')")
    @Log(title = "博客管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id) {
        return toAjax(blogService.deleteBlogById(id));
    }
}
