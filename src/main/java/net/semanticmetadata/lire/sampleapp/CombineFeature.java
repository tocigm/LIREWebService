package net.semanticmetadata.lire.sampleapp;

import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.utils.SerializationUtils;

import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by SONY on 4/5/2017.
 */
public class CombineFeature implements GlobalFeature {
    VGGFeatures vggFeature = new VGGFeatures();
    CEDD cedd = new CEDD();

    public VGGFeatures getVggFeature() {
        return vggFeature;
    }

    public void setVggFeature(VGGFeatures vggFeature) {
        this.vggFeature = vggFeature;
    }

    public CEDD getCedd() {
        return cedd;
    }

    public void setCedd(CEDD cedd) {
        this.cedd = cedd;
    }

    @Override
    public void extract(BufferedImage bufferedImage) {
        vggFeature.extract(bufferedImage);
        cedd.extract(bufferedImage);
    }

    @Override
    public String getFeatureName() {
        return "COMBINE";
    }

    @Override
    public String getFieldName() {
        return "COMBINE";
    }

    @Override
    public byte[] getByteArrayRepresentation() {
        byte[] byte1 = vggFeature.getByteArrayRepresentation();
        byte[] byte2 = cedd.getByteArrayRepresentation();
        byte[] leng1 = SerializationUtils.toBytes(byte1.length);
        byte[] r = new byte[leng1.length + byte1.length + byte2.length];
        System.arraycopy(leng1, 0, r, 0, leng1.length);
        System.arraycopy(byte1, 0, r, leng1.length, byte1.length);
        System.arraycopy(byte2, 0, r, leng1.length+ byte1.length, byte2.length);
        return r;
    }

    @Override
    public void setByteArrayRepresentation(byte[] bytes) {
        byte b1[] = Arrays.copyOfRange(bytes, 0, 4);
        int leng = new BigInteger(b1).intValue();
        byte b2[] = Arrays.copyOfRange(bytes, 4, leng + 4);
        byte b3[] = Arrays.copyOfRange(bytes, 4 + leng, bytes.length);
        vggFeature.setByteArrayRepresentation(b2);
        cedd.setByteArrayRepresentation(b3);
    }

    @Override
    public void setByteArrayRepresentation(byte[] bytes, int i, int i1) {
        setByteArrayRepresentation(bytes);
    }

    @Override
    public double getDistance(LireFeature lireFeature) {
        if (lireFeature instanceof CombineFeature) {
            CombineFeature feature = (CombineFeature) lireFeature;
            return vggFeature.getDistance(feature.vggFeature) + cedd.getDistance(feature.cedd);
        }
        else return Double.MAX_VALUE;
    }

    @Override
    public double[] getFeatureVector() {
        return vggFeature.getFeatureVector();
    }
}
