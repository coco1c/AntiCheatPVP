package com.coco.mc.util;

import com.hakan.core.utils.ColorUtil;
import com.coco.mc.LiteMCCrystalAntiCheat;

public class ConfigUtil {

    public static String getString(String path){
        return ColorUtil.colored(LiteMCCrystalAntiCheat.instance.getConfig().getString(path));
    }
    public static int getSusSpeed(String path){
        return LiteMCCrystalAntiCheat.instance.getConfig().getInt("sus-speeds." + path);
    }
}
