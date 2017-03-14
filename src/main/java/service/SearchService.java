package service;

import com.google.gson.JsonArray;
import net.semanticmetadata.lire.sampleapp.Searcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;

/**
 * Created by SONY on 3/14/2017.
 */
@RestController
public class SearchService {
    public static Searcher searcher = new Searcher();


    @PostMapping("/search")
    public String SearchImage(@RequestParam("file") MultipartFile mfile,
                              RedirectAttributes redirectAttributes) throws Exception {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(mfile.getBytes()));
        JsonArray result = searcher.Search(img);
        return result.toString();
    }

    @RequestMapping(value = "/searchURL", method = RequestMethod.POST)
    public String SearchUrl(@RequestParam("url") String urlLink) {
        try {
            URL url = new URL(urlLink);
            BufferedImage img = ImageIO.read(url);
            JsonArray result = searcher.Search(img);
            return result.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Cannot search this image";
    }
}
