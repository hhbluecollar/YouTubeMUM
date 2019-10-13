package org.mumyoutube.controller;


import org.mumyoutube.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mumyoutube.controller.VideoUploadController.uploadingDir;


@Controller
@RequestMapping

public class VideoplayListController {

    VideoService videoService;

    @Autowired
    public VideoplayListController(VideoService videoService) {
        super();
        this.videoService = videoService;
    }

    @GetMapping(value = "/playVideo", produces = "video/mp4")
    @ResponseBody
    public ModelAndView videoSource( ) throws IOException {
        ModelAndView mv = new ModelAndView();

        //List<Video> video = videoService.getAllVideo();
       // Path videosPath = Paths.get(uploadingDir);

        List v = Files.list(Paths.get(uploadingDir)).collect(Collectors.toList());
        List vPath = new ArrayList();
        for (Object s:v ) {
            vPath.add(Paths.get(s.toString()).getFileName());
        }
        mv.addObject("videos",vPath);

        return  mv;
    }

    @GetMapping( value = "/{videoName}", produces = "video/mp4")
    @NotNull
    public String video(@PathVariable String videoName) {
        Path fullPath = Paths.get(uploadingDir +"/"+ videoName);
        ModelAndView model = new ModelAndView();
        model.addObject("path", fullPath);

        return "video";
    }
}
