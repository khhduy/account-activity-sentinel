package com.kzduy.activitysentinel.repository;


import com.kzduy.activitysentinel.domain.User;

import java.util.Collection;

public interface UserRepository<T extends User>{
    //    Basic CRUD
    T create(T data);
    //Read all with pagination
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    //Complex operations

}
