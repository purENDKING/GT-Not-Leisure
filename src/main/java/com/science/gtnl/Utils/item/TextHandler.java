package com.science.gtnl.Utils.item;

import static net.minecraft.util.StatCollector.translateToLocalFormatted;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.config.MainConfig;

public class TextHandler {

    public static Map<String, String> LangMap;
    public static Map<String, String> LangMapNeedToWrite = new HashMap<>();

    public static String texter(String aTextLine, String aKey) {

        /**
         * If not in Dev mode , return vanilla forge method directly.
         */
        if (MainConfig.enableDebugMode) {
            if (LangMap.get(aKey) == null) {
                ScienceNotLeisure.LOG.info("Texter get a new key - TextLine: " + aKey + " - " + aTextLine);
                LangMapNeedToWrite.put(aKey, aTextLine);
                return aTextLine;
            } else {
                return translateToLocalFormatted(aKey);
            }
        } else if (null != translateToLocalFormatted(aKey)) {
            return translateToLocalFormatted(aKey);
        }
        return "texterError: " + aTextLine;
    }

    public static String texterButKey(String aTextLine, String aKey) {

        if (MainConfig.enableDebugMode) {
            if (LangMap.get(aKey) == null) {
                ScienceNotLeisure.LOG.info("Texter get a new key - TextLine: " + aKey + " - " + aTextLine);
                LangMapNeedToWrite.put(aKey, aTextLine);
            }
        }
        return aKey;
    }

    /**
     * Auto generate the Key of textLine
     * {@link TextHandler#texter(String aTextLine, String aKey)}
     *
     * @param aTextLine The default String to where you use.
     * @return
     */
    public static String texter(String aTextLine) {
        String aKey = "Auto." + aTextLine + ".text";
        return texter(aTextLine, aKey);
    }

    /**
     * Write the new textLines into Dev/src/main/resources/scicencenotleisure/lang/.lang
     *
     * @param isInDevMode The signal of whether in development mode.
     */
    public static void serializeLangMap(boolean isInDevMode) {

        if (isInDevMode) {

            /* If no new text need to write */
            if (LangMapNeedToWrite.isEmpty()) {
                return;
            }
            // if (LangMap.equals(LangMapBackUp)) {
            // ScienceNotLeisure.LOG.info(ScienceNotLeisure.MODID + ": No new text need to handle.");
            // /* If you need to see what the fuck in the LangMap and LangMapBackUp, remove the comment markers. */
            //
            // // for(String key : LangMapBackUp.keySet()){
            // // scicencenotleisure.LOG.info("Get LanMapBackUp at serializeLangMap() : " + key + " --- " +
            // // LangMapBackUp.get(key));
            // // }
            // // for (String key : LangMap.keySet()){
            // // scicencenotleisure.LOG.info("Get LanMap at serializeLangMap() : " + key + " --- " + LangMap.get(key));
            // // }
            //
            // return;
            // }

            // /* New a Map with new texts need to write. */
            // Map<String, String> LangMapNeedWrite = new HashMap<String, String>(LangMap);
            //
            // /* Remove texts not need to write. */
            // for (String tx : LangMapBackUp.keySet()) {
            // LangMapNeedWrite.remove(tx);
            // }

            /* Prepare the files. */
            File en_US_lang = new File(ScienceNotLeisure.DevResource + "\\assets\\sciencenotleisure\\lang\\en_US.lang");
            File zh_CN_lang = new File(ScienceNotLeisure.DevResource + "\\assets\\sciencenotleisure\\lang\\zh_CN.lang");
            ScienceNotLeisure.LOG
                .info("File finder with en_US.lang catch a file absolutePath: " + en_US_lang.getAbsolutePath());
            ScienceNotLeisure.LOG.info("File finder with en_US.lang catch a file named: " + en_US_lang.getName());

            /* Write the new textLines in the end of the lang file. */
            ScienceNotLeisure.LOG.info("Start write new text: " + en_US_lang.getAbsolutePath());

            try {
                FileWriter en_Us = new FileWriter(en_US_lang, true);
                FileWriter zh_CN = new FileWriter(zh_CN_lang, true);
                for (String key : LangMapNeedToWrite.keySet()) {
                    ScienceNotLeisure.LOG
                        .info("en_US write a Line START: " + key + "===>" + LangMapNeedToWrite.get(key));
                    en_Us.write(key);
                    en_Us.write("=");
                    en_Us.write(LangMapNeedToWrite.get(key));
                    en_Us.write("\n");
                    ScienceNotLeisure.LOG.info("en_US write a Line COMPLETE.");
                    ScienceNotLeisure.LOG
                        .info("zh_CN write a Line START: " + key + "===>" + LangMapNeedToWrite.get(key));
                    zh_CN.write(key);
                    zh_CN.write("=");
                    zh_CN.write(LangMapNeedToWrite.get(key));
                    zh_CN.write("\n");
                    ScienceNotLeisure.LOG.info("zh_CN write a Line COMPLETE.");
                }
                ScienceNotLeisure.LOG.info("Finish to write new text: " + en_US_lang.getAbsolutePath());
                en_Us.close();
                zh_CN.close();
            } catch (IOException e) {
                ScienceNotLeisure.LOG.info("Error in serializeLangMap() File Writer en_US");
                throw new RuntimeException(e);
            }

            /* Del the backup. */
            LangMapNeedToWrite.clear();

        }
    }
}
