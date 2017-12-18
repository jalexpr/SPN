
package MorfSdkJ;

/**
 *
 */
public class MorfSdkJavaControl {
    private static final MorfSdkJava MSJ = new MorfSdkJava();
    private static final String PATH = "MorfSdkJ_MorfSdk/";
    static {
        MSJ.StartJ(PATH + "main.dic",PATH + "end.mdl",PATH + "types.chn");
    }

    public static MorfSdkJava getMorfSdkJava(){
        return MSJ;
    }
}
