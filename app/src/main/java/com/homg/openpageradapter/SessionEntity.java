package com.homg.openpageradapter;

/**
 * Created by homgwu on 2018/4/27 10:20.
 */
public class SessionEntity {
    public String name;
    public boolean isSelected=false;
    public int position;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SessionEntity other = (SessionEntity) obj;
        return this.name.equals(other.name);
    }
}
