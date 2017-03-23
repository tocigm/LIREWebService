package net.semanticmetadata.lire.sampleapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import net.semanticmetadata.lire.utils.SerializationUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SONY on 3/21/2017.
 */
public class VGGFeatures implements GlobalFeature {
    double[] feature;

    public double[] getFeature() {
        return feature;
    }

    @Override
    public String getFeatureName() {
        return "VGG";
    }

    @Override
    public String getFieldName() {
        return "VGG";
    }

    @Override
    public byte[] getByteArrayRepresentation() {
        return SerializationUtils.toByteArray(feature);
    }

    @Override
    public void setByteArrayRepresentation(byte[] bytes) {
        feature = SerializationUtils.toDoubleArray(bytes);

    }

    @Override
    public void setByteArrayRepresentation(byte[] bytes, int i, int i1) {
        double[] tmp = SerializationUtils.toDoubleArray(bytes);
        feature = new double[i+tmp.length];
        for (int index = i; index < i + i1; index++) {
            if (index < feature.length && (index - i < tmp.length)) {
                feature[index] = tmp[index-i];
            }
        }

    }

    @Override
    public double getDistance(LireFeature lireFeature) {
        if (!(lireFeature instanceof VGGFeatures))
            return Double.MAX_VALUE;
        VGGFeatures vggFeatures = (VGGFeatures) lireFeature;
        if (vggFeatures.feature == null || vggFeatures.feature.length == 0)
            return Double.MAX_VALUE;
        return distance(feature, vggFeatures.getFeature());
    }

    private double distance(double[] a, double[] b) {
        double diff_square_sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            diff_square_sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return Math.sqrt(diff_square_sum);
    }

    @Override
    public double[] getFeatureVector() {
        return new double[0];
    }

    @Override
    public void extract(BufferedImage bufferedImage) {
        feature = new double[0];
        try {
            File outputfile = new File("image.jpg");
            ImageIO.write(bufferedImage, "jpg", outputfile);
            String json = getPostJSONContent("http://127.0.0.1:5000/", outputfile);
            JsonParser parser = new JsonParser();
            JsonElement tradeElement = parser.parse(json);
            JsonArray trade = tradeElement.getAsJsonArray();
            feature = new double[trade.size()];
            for (int i = 0; i < trade.size();i++) {
                feature[i] = trade.get(i).getAsDouble();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            feature = new double[0];
        }

    }

    public static String getPostJSONContent(String url, File file) {
        try {
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod(url);
            FilePart filepart = new FilePart("file", file);
            Part[] parts = { filepart };
            post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
            client.executeMethod(post);
            String result = post.getResponseBodyAsString();
            post.releaseConnection();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void main(String args[]) throws IOException {
        File file = new File("E:\\Bagiks\\SourceCode\\LIRE\\LIREWebService\\data\\sample\\1034385_31756512.jpg");
        BufferedImage image = ImageIO.read(file );
        VGGFeatures vgg = new VGGFeatures();
        vgg.extract(image);
    }
}
