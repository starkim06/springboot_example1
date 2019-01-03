package m.student.myapplication.Retrofit.RequestBody;


import java.util.Date;
import java.util.HashMap;

public class RequestPut {

    public final long id;
    public final String title;
    public final String url;
    public final Date createdAt;
    public final Date updatedAt;

    public RequestPut(HashMap<String, Object> param) {
        this.id = (long) param.get("id");
        this.url= (String) param.get("url");
        this.title = (String) param.get("title");
        this.createdAt= (Date) param.get("createdAt");
        this.updatedAt= (Date) param.get("updatedAt");
    }

}