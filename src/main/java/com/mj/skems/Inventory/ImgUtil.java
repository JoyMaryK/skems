package com.mj.skems.Inventory;

import java.util.Base64;

public class ImgUtil {
    public String getImgData(byte[] byteData) {
        return Base64.getMimeEncoder().encodeToString(byteData);
    }
}
