package ir.adicom.app.mymoney.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 *
 * Created by Y.P on 03/09/2018.
 */

@Entity
public class Expense {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private Long price;
    private Long categoryId;
    @ToOne(joinProperty = "categoryId")
    private Category category;
    private Long date;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 218583319)
    private transient ExpenseDao myDao;
    @Generated(hash = 464212628)
    public Expense(Long id, String title, Long price, Long categoryId, Long date) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryId = categoryId;
        this.date = date;
    }
    @Generated(hash = 2061519627)
    public Expense() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getPrice() {
        return this.price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public Long getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public Long getDate() {
        return this.date;
    }
    public void setDate(Long date) {
        this.date = date;
    }
    @Generated(hash = 1372501278)
    private transient Long category__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 728129201)
    public Category getCategory() {
        Long __key = this.categoryId;
        if (category__resolvedKey == null || !category__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            Category categoryNew = targetDao.load(__key);
            synchronized (this) {
                category = categoryNew;
                category__resolvedKey = __key;
            }
        }
        return category;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1132018243)
    public void setCategory(Category category) {
        synchronized (this) {
            this.category = category;
            categoryId = category == null ? null : category.getId();
            category__resolvedKey = categoryId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1159305667)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getExpenseDao() : null;
    }
}
