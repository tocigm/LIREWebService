package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.semanticmetadata.lire.sampleapp.Searcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONY on 3/14/2017.
 */
@RestController
public class SearchService {
    public static Searcher searcher = new Searcher();


    @PostMapping("/search")
    public @ResponseBody List<Image> SearchImage(@RequestParam("file") MultipartFile mfile,
                                                          RedirectAttributes redirectAttributes) throws Exception {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(mfile.getBytes()));
        JsonArray results = searcher.Search(img);
        String PREFIX = "https://s3-us-west-2.amazonaws.com/3giks-search-images/34/";
        List<Image> images= new ArrayList<Image>();
        for (JsonElement ele : results){
            JsonObject jObj = ele.getAsJsonObject();
            String imgURL = jObj.get("url").getAsString();
            String [] parts = imgURL.split("/");
            String imgName = parts[parts.length - 1];
            imgURL = PREFIX + imgName;
            images.add(new Image(imgURL, imgName, jObj.get("score").getAsFloat()));
        }
        return images;
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
