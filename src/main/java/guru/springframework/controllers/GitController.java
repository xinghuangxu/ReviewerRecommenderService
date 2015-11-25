package guru.springframework.controllers;

import guru.springframework.domain.*;
import guru.springframework.recommender.xfinder.RecommendedReviewer;
import guru.springframework.recommender.xfinder.ReviewTuple;
import guru.springframework.recommender.xfinder.XFinder;
import guru.springframework.repositories.FilePathRepository;
import guru.springframework.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xinghuangxu on 11/19/15.
 */
@RestController
public class GitController {

    String POST_COMMIT_FILE_PATH = "githook/post-commit";

    @RequestMapping(value = "/git/post-commit", method = RequestMethod.GET)
    public
    @ResponseBody
    void getGitPostCommitFile(HttpServletResponse response) throws IOException {
        File file = null;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        file = new File(classloader.getResource(POST_COMMIT_FILE_PATH).getFile());


        if (!file.exists()) {
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        System.out.println("mimetype : " + mimeType);

        response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
