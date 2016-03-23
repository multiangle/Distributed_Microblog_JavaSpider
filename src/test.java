/**
 * Created by multiangle on 2016/3/23.
 */
import java.util.regex.Matcher ;
import java.util.regex.Pattern ;

public class test {
    public static void main(String[] args){
        Pattern p_face      =   Pattern.compile("\\[.+?\\]") ;
        Pattern p_face_i    =   Pattern.compile("<i.+?</i>") ;
        Pattern p_user      =   Pattern.compile("<a href.+?</a>") ;

        Pattern p_link=Pattern.compile("<a data-url.+?</a>") ;

        String test="你知道的太多啦！<a data-url=\"http://t.cn/RG8ixyS\" href=\"http://video.weibo.com/show?fid=1034:e1a7dffebdd886bc68d5d7ad8e3d65eb&ep=DlkUQjdn7%2C1852341007%2CDlkUQjdn7%2C1852341007\" class=\"\"><i class=\"iconimg iconimg-xs\"><img src=\"http://h5.sinaimg.cn/upload/2015/09/25/3/timeline_card_small_video_default.png\"></i><span class=\"surl-text\">秒拍视频</span></a>" ;
        Matcher match=p_link.matcher(test) ;
        String type=match.group(0) ;
        System.out.println();
    }

}
