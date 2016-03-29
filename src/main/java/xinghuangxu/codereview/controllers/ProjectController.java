package xinghuangxu.codereview.controllers;

import xinghuangxu.codereview.crawler.gerrit.GerritCrawler;
import xinghuangxu.codereview.domain.Project;
import xinghuangxu.codereview.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by xinghuangxu on 11/19/15.
 */
@RestController
public class ProjectController {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GerritCrawler gerritCrawler;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public
    @ResponseBody
    Iterable<Project> findProjectsByNameContaining(@RequestParam(required = false) String q) {
        if (q == null) {
            return projectRepository.findAll();
        }
        return projectRepository.findByNameContaining(q);
    }

    @RequestMapping(value = "/projects/{project_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Project findProjectWithId(@PathVariable(value = "project_id") Integer id) {
        return projectRepository.findOne(id);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public
    @ResponseBody
    Project createProject(@RequestBody final Project project) throws Exception {
        List<Project> projects = projectRepository.findByName(project.getName());
        for(Project p : projects){
            if(p.getUrl().equals(project.getUrl())){
                throw new Exception("Project already exist!");
            }
        }
        gerritCrawler.crawl(project);
        return project;
    }

}
