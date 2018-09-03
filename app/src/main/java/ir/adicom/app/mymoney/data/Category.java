package ir.adicom.app.mymoney.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 * Created by Y.P on 03/09/2018.
 */

@Entity
public class Category {
    @Id(autoincrement = true)
    private long id;
    private String title;
    @Generated(hash = 1050788042)
    public Category(long id, String title) {
        this.id = id;
        this.title = title;
    }
    @Generated(hash = 1150634039)
    public Category() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
