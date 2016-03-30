package client;

/**
 * Created by multiangle on 2016/3/23.
 */

//=====================import from JDK ========================
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher ;
import java.util.regex.Pattern ;
import java.util.Map ;
import java.util.List ;
//----------------import from external libraries---------------
import com.sun.prism.shader.DrawCircle_LinearGradient_PAD_Loader;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.json.* ;
import com.google.gson.Gson ;
//=============================================================

public class parseMicroblogPage {
    private Pattern p_face      =   Pattern.compile("\\[.+?\\]") ;
    private Pattern p_face_i    =   Pattern.compile("<i.+?</i>") ;
    private Pattern p_user      =   Pattern.compile("<a href.+?</a>") ;
    private Pattern p_link      =   Pattern.compile("<a data-url.+?</a>") ;
    private Pattern p_img       =   Pattern.compile("<img.+?>") ;
    private Pattern p_span      =   Pattern.compile("<span.+?</span>") ;
    private Pattern p_http_png  =   Pattern.compile("http://.+?png") ;

    public static JSONArray parse_blog_page (String data_str) {
        JSONObject data_json ;
        try{
            data_json = new JSONObject(data_str) ;
        }catch (Exception e){
            throw new ValueException("Unable to parse page to json object") ;
        }

        // check if the page is empty
        String mode_type ;
        JSONObject temp ;
        try{
            JSONArray cards_array   =   data_json.getJSONArray("cards") ;
            temp                    =   cards_array.getJSONObject(0) ;
            mode_type               =   temp.getString("mod_type") ;
        }catch (Exception e ){
            throw new ValueException("The type of this page is incorrect") ;
        }
        if (mode_type.contains("empty")){
            throw new ValueException("This page is empty") ;
        }

        JSONArray data_card_group ;
        try{
            data_card_group         =  temp.getJSONArray("card_group") ;
        }catch (Exception e){
            throw new ValueException("The type of this page is incorrect") ;
        }

        JSONArray data_ret=null ;

        for ( int i=0 ; i<data_card_group.length() ; i++ ){
            JSONObject item_ori =   data_card_group.getJSONObject(i) ;
            JSONObject res      =   parse_card_group(item_ori) ;
            data_ret.put(res) ;
        }

        return data_ret ;
    }

    private static JSONObject parse_card_group(JSONObject block){
        JSONObject mblog        =   block.getJSONObject("mblog") ;
        JSONObject msg          =   parse_card_inner(mblog) ;
        return msg ;
    }

    private static JSONObject parse_card_inner(JSONObject data){
        JSONObject msg          =   null ;
        Set keys                =   msg.keySet() ;

        List<String> key_array  = Arrays.asList(
                // 基本信息--------------------------------------------------------------
                "idstr",                        //等同于id，是str形式
                "id",                           //信息id
                "created_timestamp",          //创建时间戳 ex:1448617509
                "attitudes_count",            //点赞数
                "reposts_count",              //转发数
                "comments_count",             //评论数目
                "isLongText",                  //是否是长微博（就目前来看，都是False）
                "so urce",                      //用户客户端（iphone等）
                "pid",                         //不明，但是有必要保存，不一定有值
                "bid",                         //不明，但是有必要保存，不一定有值
                "province",                     //省份id
                "city",                          //城市id
                // 图片信息--------------------------------------------------------------
                "original_pic",               //图片相关，原始图片地址
                "bmiddle_pic",                //图片地址，与original_pic相比，只是把large换位bmiddle
                "thumbnail_pic",              //地址似乎等于pic中图片地址，尺寸是thumb
                "pic_ids",                     //图片id，是个Array
                "pics"                          //如果包含图的话，有该项，是一个数组，内嵌字典，
                // 包括size,pid,geo,url等
            ) ;

        for (String item : key_array){
            if ( keys.contains(item) ){
                msg.put(item,data.get(item)) ;
            }
        }

        // 糅合 id, mid, msg_id
        if ( ! keys.contains("id")){
            if ( keys.contains("mid") ){
                msg.put( "id", data.get("mid") ) ;
            }
            else if ( keys.contains("msg_id") ){
                msg.put( "id", data.get("msg_id") );
            }
        }

        // created_at
        if ( keys.contains("created_at") ){
            String created_time = (String) data.get("created_at") ;
            if ( created_time.length()>14 ){
                msg.put( "created_at", data.get("created_at")) ;
            }
            else{
                if ( keys.contains("created_timestamp") ){
                    long stamp = (Integer) data.get("created_timestamp") ;
                    // todo 转换时间相关函数
                }
            }
        }


        // todo unfinished
        return msg ;
    }

    public static void main(String[] args){
        parseMicroblogPage x=new parseMicroblogPage() ;
        System.out.println("asdf");
    }


}
