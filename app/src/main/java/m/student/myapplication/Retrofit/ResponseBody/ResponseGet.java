package m.student.myapplication.Retrofit.ResponseBody;

import java.util.Date;

public class ResponseGet {

    public final long id;
    public final String title;
    public final String url;
    public final Date createdAt;
    public final Date updatedAt;

    public ResponseGet(long id, String title,String url,Date createdAt , Date updatedAt) {
        this.id = id;
        this.url=url;
        this.title = title;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }

}