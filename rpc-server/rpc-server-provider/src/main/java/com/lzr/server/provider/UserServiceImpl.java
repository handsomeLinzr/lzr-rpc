package com.lzr.server.provider;

import com.lzr.rpc.api.po.IUserService;
import com.lzr.rpc.api.po.User;

public class UserServiceImpl implements IUserService {
    @Override
    public User getUser(String id) {
        return new User(id, 25, id);
    }
}
